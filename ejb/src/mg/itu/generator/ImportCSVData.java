package mg.itu.generator;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import mg.itu.database.Database;
import mg.itu.model.RawMaterialFifoEntry;
import mg.itu.model.RawMaterialStockExit;
import mg.itu.service.MachineService;
import mg.itu.service.RawMaterialRequirementService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

// refactorisation: https://claude.ai/chat/c64d9edf-8598-4e3a-92aa-52df48178ae7
 
public class ImportCSVData {

    private static final int BATCH_SIZE = 1000;
    private static RawMaterialFifoEntry[] entries;

    public static void importCSVToDatabase(String csvFilePath) 
        throws Exception 
    {
        // preload entries from the start
        entries = new RawMaterialFifoEntry().getAll(RawMaterialFifoEntry.class, null, "V_RAW_MATERIAL_FIFO_ENTRY");

        String insertSpongeSQL = "INSERT INTO InitialSponge " +
                "(id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine, purchase_price_theorical) " +
                "VALUES (seq_initial_sponge.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmtSponge = conn.prepareStatement(insertSpongeSQL);
             BufferedReader br = new BufferedReader(new FileReader(csvFilePath))) {
    
            conn.setAutoCommit(false);
            br.readLine(); 
    
            String line;
            int count = 0;
    
            // tracking stock usage and sponge counts
            Map<Integer, Queue<StockLot>> stockLotsMap = new HashMap<>();
            Map<Integer, BigDecimal> machineStockUsageMap = new HashMap<>();
            Map<Integer, Integer> machineSpongeCountMap = new HashMap<>();
    
            // preload raw material multipliers at the start
            HashMap<Integer, BigDecimal> multipliers = RawMaterialRequirementService.preloadRawMaterialMultipliers();
    
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
    
                // parse csv data
                String dateStr = values[0];
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Date dateCreation = Date.valueOf(LocalDate.parse(dateStr, formatter));
    
                BigDecimal length = new BigDecimal(values[1]);
                BigDecimal width = new BigDecimal(values[2]);
                BigDecimal height = new BigDecimal(values[3]);

                BigDecimal purchasePrice = new BigDecimal(values[4]);
                int machineId = Integer.parseInt(values[5].replace("M", ""));
    
                BigDecimal volume = length.multiply(width).multiply(height);
    
                // get raw material requirements using preloaded multipliers
                HashMap<Integer, BigDecimal> requirements = RawMaterialRequirementService.getRawMaterialRequirements(multipliers, volume);
    
                BigDecimal theoreticalPrice = BigDecimal.ZERO;
                boolean hasEnoughStock = true;
    
                for (Map.Entry<Integer, BigDecimal> requirement : requirements.entrySet()) {
                    int rawMaterialId = requirement.getKey();
                    BigDecimal requiredQty = requirement.getValue();
    
                    // initialize stock lots for this raw material if not already done
                    if (!stockLotsMap.containsKey(rawMaterialId)) {
                        Queue<StockLot> lots = new LinkedList<>();
    
                        for (RawMaterialFifoEntry entry : entries) {
                            if (entry.getRawMaterialId() == rawMaterialId && entry.getAvailableQuantity().compareTo(BigDecimal.ZERO) > 0) 
                            { lots.add(new StockLot(entry)); }
                        }
    
                        stockLotsMap.put(rawMaterialId, lots);
                    }
    
                    // fulfill requirement from available lots
                    Queue<StockLot> lots = stockLotsMap.get(rawMaterialId);
                    BigDecimal remainingQtyNeeded = requiredQty;
    
                    while (remainingQtyNeeded.compareTo(BigDecimal.ZERO) > 0 && !lots.isEmpty()) {
                        StockLot currentLot = lots.peek();
                        BigDecimal qtyFromLot = currentLot.remainingQty.min(remainingQtyNeeded);
    
                        if (qtyFromLot.compareTo(BigDecimal.ZERO) > 0) {
                            BigDecimal cost = qtyFromLot.multiply(currentLot.unitPrice);
                            theoreticalPrice = theoreticalPrice.add(cost); 
    
                            machineStockUsageMap.merge(machineId, cost, BigDecimal::add);
    
                            currentLot.remainingQty = currentLot.remainingQty.subtract(qtyFromLot);
                            remainingQtyNeeded = remainingQtyNeeded.subtract(qtyFromLot);
                            
                            // remove empty lots
                            if (currentLot.remainingQty.compareTo(BigDecimal.ZERO) <= 0) 
                            { lots.poll(); }
                        }
                    }
    
                    if (remainingQtyNeeded.compareTo(BigDecimal.ZERO) > 0) {
                        hasEnoughStock = false;
                        break;
                    }
                }
    
                if (hasEnoughStock) {
                    // insert sponge data
                    pstmtSponge.setBigDecimal(1, purchasePrice);
                    pstmtSponge.setString(2, "FALSE");
                    pstmtSponge.setBigDecimal(3, length);
                    pstmtSponge.setBigDecimal(4, width);
                    pstmtSponge.setBigDecimal(5, height);
                    pstmtSponge.setDate(6, dateCreation);
                    pstmtSponge.setInt(7, machineId);
                    pstmtSponge.setBigDecimal(8, theoreticalPrice); 
                    pstmtSponge.addBatch();
    
                    // increment sponge count for the machine
                    machineSpongeCountMap.merge(machineId, 1, Integer::sum);

                    // bacth counter
                    count++;        
    
                    if (count % BATCH_SIZE == 0) {
                        pstmtSponge.executeBatch();
                        conn.commit();
                        System.out.println("Processed " + count + " rows");
                    }
                } 
                
                else 
                { throw new RuntimeException("Insufficient stock for raw materials to create sponge at row " + (count + 2)); }
            }
    
            pstmtSponge.executeBatch();
            conn.commit();
    
            MachineService.insertMachineTheoreticalPriceAndSpongeCount(machineStockUsageMap, machineSpongeCountMap);
    
        } 
        
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error occurred during CSV import: " + e.getMessage());
        }
    }
    

    private static class StockLot {
        int entryId;
        
        BigDecimal remainingQty;
        BigDecimal unitPrice;

        StockLot(RawMaterialFifoEntry entry) {
            this.entryId = entry.getRawMaterialId();
            this.remainingQty = entry.getAvailableQuantity();
            this.unitPrice = entry.getUnitPrice();
        }
    }
}