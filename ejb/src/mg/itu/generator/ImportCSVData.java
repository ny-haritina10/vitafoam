package mg.itu.generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import mg.itu.database.Database;
import mg.itu.model.RawMaterialFifoEntry;
import mg.itu.model.RawMaterialStockExit;
import mg.itu.service.RawMaterialRequirementService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportCSVData {

    private static final int BATCH_SIZE = 1000;

    public static void importCSVToDatabase(String csvFilePath) 
        throws Exception 
    {
        String insertSpongeSQL = "INSERT INTO InitialSponge " +
            "(id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine) " +
            "VALUES (seq_initial_sponge.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";

        // Add separate query to get the sequence value
        String getLastIdSQL = "SELECT seq_initial_sponge.CURRVAL FROM DUAL";

        String insertStockExitSQL = "INSERT INTO RawMaterialStockExit " +
            "(id, id_raw_materiel, date_session, qte_out, unit_price, amount, id_block) " +
            "VALUES (seq_raw_materiel_stock_exit.NEXTVAL, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Database.getConnection();
                PreparedStatement pstmtSponge = conn.prepareStatement(insertSpongeSQL);
                PreparedStatement pstmtGetLastId = conn.prepareStatement(getLastIdSQL);
                PreparedStatement pstmtStockExit = conn.prepareStatement(insertStockExitSQL);
                BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            ) {
   
            conn.setAutoCommit(false);
            br.readLine(); // Skip header
    
            String line;
            int count = 0;
            
            // Keep track of stock lots for each raw material
            Map<Integer, Queue<StockLot>> stockLotsMap = new HashMap<>();
            
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                // Parse sponge data
                double purchasePrice = Double.parseDouble(values[0]);
                String isTransformed = values[1];
                double length = Double.parseDouble(values[2]);
                double width = Double.parseDouble(values[3]);
                double height = Double.parseDouble(values[4]);
                Timestamp dateCreation = Timestamp.valueOf(values[5]);
                int machineId = Integer.parseInt(values[6]);

                // Calculate volume
                double volume = length * width * height;

                // Get raw material requirements for this volume
                HashMap<Integer, Double> requirements = RawMaterialRequirementService.getRawMaterialRequirementsByVolume(null, volume);

                // Process stock requirements
                boolean hasEnoughStock = true;
                Map<Integer, List<RawMaterialStockExit>> stockExits = new HashMap<>();

                // Check stock availability for each required raw material
                for (Map.Entry<Integer, Double> requirement : requirements.entrySet()) {
                    int rawMaterialId = requirement.getKey();
                    double requiredQty = requirement.getValue();

                    // Initialize stock lots for this raw material if not already done
                    if (!stockLotsMap.containsKey(rawMaterialId)) {
                        Queue<StockLot> lots = new LinkedList<>();
                        RawMaterialFifoEntry[] entries = 
                            new RawMaterialFifoEntry().getAll(RawMaterialFifoEntry.class, null, "V_RAW_MATERIAL_FIFO_ENTRY");
                        
                        for (RawMaterialFifoEntry entry : entries) {
                            if (entry.getRawMaterialId() == rawMaterialId && entry.getAvailableQuantity() > 0) 
                            { lots.add(new StockLot(entry)); }
                        }
                        
                        stockLotsMap.put(rawMaterialId, lots);
                    }

                    // Try to fulfill requirement from available lots
                    Queue<StockLot> lots = stockLotsMap.get(rawMaterialId);
                    double remainingQtyNeeded = requiredQty;
                    List<RawMaterialStockExit> materialExits = new ArrayList<>();

                    while (remainingQtyNeeded > 0 && !lots.isEmpty()) {
                        StockLot currentLot = lots.peek();
                        double qtyFromLot = Math.min(currentLot.remainingQty, remainingQtyNeeded);
                        
                        if (qtyFromLot > 0) {
                            RawMaterialStockExit exit = new RawMaterialStockExit();

                            exit.setRawMaterialId(rawMaterialId);
                            exit.setDateSession(new Date(dateCreation.getTime()));
                            exit.setQuantityOut(qtyFromLot);
                            exit.setUnitPrice(currentLot.unitPrice);
                            exit.setAmount(qtyFromLot * currentLot.unitPrice);
                            
                            materialExits.add(exit);
                            
                            currentLot.remainingQty -= qtyFromLot;
                            remainingQtyNeeded -= qtyFromLot;
                            
                            if (currentLot.remainingQty <= 0) 
                            { lots.poll(); } // Remove empty lot 
                        }
                    }

                    if (remainingQtyNeeded > 0) {
                        hasEnoughStock = false;
                        break;
                    }

                    stockExits.put(rawMaterialId, materialExits);
                }

                if (hasEnoughStock) {
                    // Insert sponge record
                    pstmtSponge.setDouble(1, purchasePrice);
                    pstmtSponge.setString(2, isTransformed);
                    pstmtSponge.setDouble(3, length);
                    pstmtSponge.setDouble(4, width);
                    pstmtSponge.setDouble(5, height);
                    pstmtSponge.setTimestamp(6, dateCreation);
                    pstmtSponge.setInt(7, machineId);
                    pstmtSponge.executeUpdate();

                    // Get the generated ID using CURRVAL
                    ResultSet rs = pstmtGetLastId.executeQuery();
                    int generatedSpongeId = 0;
                    if (rs.next()) {
                        generatedSpongeId = rs.getInt(1);
                    }
                    rs.close();

                    // Use generatedSpongeId for stock exits
                    for (List<RawMaterialStockExit> exits : stockExits.values()) {
                        for (RawMaterialStockExit exit : exits) {
                            pstmtStockExit.setInt(1, exit.getRawMaterialId());
                            pstmtStockExit.setDate(2, new java.sql.Date(exit.getDateSession().getTime()));
                            pstmtStockExit.setDouble(3, exit.getQuantityOut());
                            pstmtStockExit.setDouble(4, exit.getUnitPrice());
                            pstmtStockExit.setDouble(5, exit.getAmount());
                            pstmtStockExit.setInt(6, generatedSpongeId);
                            pstmtStockExit.addBatch();
                        }
                    }

                    count++;

                    if (count % BATCH_SIZE == 0) {
                        pstmtSponge.executeBatch();
                        pstmtStockExit.executeBatch();
                        conn.commit();
                        System.out.println("Processed " + count + " rows");
                    }
                } 
                
                else {
                    throw new Exception("Insufficient stock for raw materials to create sponge at row " + (count + 2));
                }
            }

            // Process remaining batches
            pstmtSponge.executeBatch();
            pstmtStockExit.executeBatch();
            conn.commit();

            br.close();
            System.out.println("Total rows processed: " + count);
        }
    }

    private static class StockLot {

        int entryId;
        double remainingQty;
        double unitPrice;
        
        StockLot(RawMaterialFifoEntry entry) {
            this.entryId = entry.getRawMaterialId();
            this.remainingQty = entry.getAvailableQuantity();
            this.unitPrice = entry.getUnitPrice();
        }
    }
}