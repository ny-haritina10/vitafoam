package mg.itu.generator;
import java.sql.*;

import mg.itu.database.Database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ImportCSVData {

    private static final int BATCH_SIZE = 1000;

    public static void importCSVToDatabase(String csvFilePath) {
        String sqlQuery = "INSERT INTO InitialSponge " +
        "(id, purchase_price, is_transformed, dim_length, dim_width, dim_height, date_creation, id_machine) " + "VALUES (seq_initial_sponge.NEXTVAL, ?, ?, ?, ?, ?, ?, ?)";


        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sqlQuery)) {

            conn.setAutoCommit(false);  // Disable auto-commit for batch processing
            BufferedReader br = new BufferedReader(new FileReader(csvFilePath));
            
            // Skip header
            br.readLine();

            String line;
            int count = 0;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                
                pstmt.setDouble(1, Double.parseDouble(values[0]));
                pstmt.setString(2, values[1]);
                pstmt.setDouble(3, Double.parseDouble(values[2]));
                pstmt.setDouble(4, Double.parseDouble(values[3]));
                pstmt.setDouble(5, Double.parseDouble(values[4]));
                pstmt.setTimestamp(6, Timestamp.valueOf(values[5]));
                pstmt.setInt(7, Integer.parseInt(values[6]));


                pstmt.addBatch();
                count++;

                if (count % BATCH_SIZE == 0) {
                    pstmt.executeBatch();
                    conn.commit();
                    System.out.println("Processed " + count + " rows");
                }
            }

            // Process remaining rows
            pstmt.executeBatch();
            conn.commit();

            br.close();
            System.out.println("Total rows processed: " + count);

        } 
        
        catch (SQLException | IOException e) 
        { e.printStackTrace(); }
    }
}