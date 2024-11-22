package mg.itu.generator;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class InitialSpongeCSVGenerator {

    public static void generateCSV(int numberOfRows, String folderPath) {
        // Ensure folder path ends with a separator
        String csvPath = folderPath + (folderPath.endsWith("/") ? "" : "/") + "initial_sponge_data.csv";
        
        try (FileWriter csvWriter = new FileWriter(csvPath)) {
            // Write CSV header
            csvWriter.append("purchase_price,is_transformed,dim_length,dim_width,dim_height,date_creation,id_machine\n");
            
            Random random = new Random();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            
            for (int i = 1; i <= numberOfRows; i++) {
                // Generate random date between 2022-01-01 and 2024-01-01
                LocalDateTime randomDate = generateRandomDate();
                
                // Generate CSV row
                csvWriter.append(String.format("0,FALSE,%.2f,%.2f,%.2f,%s,%d\n", 
                    20.0 + random.nextDouble() * 5,  // dim_length (20-25)
                    5.0 + random.nextDouble() * 2,   // dim_width (5-7)
                    10.0 + random.nextDouble() * 5,  // dim_height (10-15)
                    randomDate.format(formatter),    // date_creation
                    random.nextInt(4) + 1            // id_machine (1-4)
                ));
            }
            
            System.out.println("CSV file generated successfully at: " + csvPath);
        } catch (IOException e) {
            System.err.println("Error generating CSV file: " + e.getMessage());
        }
    }
    
    private static LocalDateTime generateRandomDate() {
        long minDate = LocalDateTime.of(2022, 1, 1, 0, 0).toEpochSecond(java.time.ZoneOffset.UTC);
        long maxDate = LocalDateTime.of(2024, 1, 1, 0, 0).toEpochSecond(java.time.ZoneOffset.UTC);
        
        long randomEpochSecond = minDate + (long)(Math.random() * (maxDate - minDate));
        
        return LocalDateTime.ofEpochSecond(randomEpochSecond, 0, java.time.ZoneOffset.UTC);
    }
}