package mg.itu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Map;
import java.math.BigDecimal;

import mg.itu.database.Database;

public class MachineService {
    
    public static void insertMachineTheoreticalPriceAndSpongeCount(Map<Integer, BigDecimal> machineStockUsageMap, Map<Integer, Integer> machineSpongeCountMap) 
        throws Exception 
    {

        String insertSQL = """
                INSERT INTO MachineTheoricalPrice (id, id_machine, theorical_amount, manufactured_sponge_count) 
                VALUES (seq_machine_amount.NEXTVAL, ?, ?, ?)
                """;

        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            conn.setAutoCommit(false);

            for (Map.Entry<Integer, BigDecimal> entry : machineStockUsageMap.entrySet()) {
                int machineId = entry.getKey();
                int spongeCount = machineSpongeCountMap.getOrDefault(machineId, 0);
                
                BigDecimal theoricalAmount = entry.getValue();

                pstmt.setInt(1, machineId);
                pstmt.setBigDecimal(2, theoricalAmount);
                pstmt.setInt(3, spongeCount);

                pstmt.addBatch();
            }

            pstmt.executeBatch();
            conn.commit();
            System.out.println("Inserted data into MachineTheoricalPrice table successfully.");

        } catch (Exception e) {
            throw new Exception("Error inserting data into MachineTheoricalPrice", e);
        }
    }

}