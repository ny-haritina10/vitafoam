package mg.itu.generator;

import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.Random;

import mg.itu.model.ReferenceBlockSummary;

public class InitialSpongeCSVGenerator {

    /**
     * Generates a CSV file with adjusted Revient based on V_REFERENCE_BLOCK_SUMMARY
     * 
     * @param numberOfRows Number of rows to generate in the CSV
     * @param folderPath Path to the folder where the CSV will be saved
     * @throws Exception if there is an error retrieving data or writing to the file
     */
    public static void generateCSV(int numberOfRows, String folderPath) 
        throws Exception 
    {
        // Retrieve the first record from V_REFERENCE_BLOCK_SUMMARY
        ReferenceBlockSummary ref = new ReferenceBlockSummary().getAll(ReferenceBlockSummary.class, null, "V_REFERENCE_BLOCK_SUMMARY")[0];
        double basePricePerCubicMeter = ref.getPricePerCubicMeter();

        // Ensure folder path ends with a separator
        String csvPath = folderPath + (folderPath.endsWith("/") ? "" : "/") + "initial_sponge_data.csv";

        try (FileWriter csvWriter = new FileWriter(csvPath)) {
            // Write CSV header
            csvWriter.append("Date,Long,Larg,Haut,Revient,Machine\n");

            Random random = new Random();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (int i = 1; i <= numberOfRows; i++) {
                // Generate random dimensions
                double dimLong = 20.0 + random.nextDouble() * 5; // Long (20-25)
                double dimLarg = 5.0 + random.nextDouble() * 2;   // Larg (5-7)
                double dimHaut = 10.0 + random.nextDouble() * 5; // Haut (10-15)

                // Calculate volume
                double volume = dimLong * dimLarg * dimHaut;

                // Apply random percentage adjustment (-10% to +10%)
                double percentageAdjustment = (random.nextDouble() * 20 - 10) / 100.0;
                double revient = basePricePerCubicMeter * volume * (1 + percentageAdjustment);

                // Generate random date
                LocalDate randomDate = generateRandomDate();

                // Write CSV row
                csvWriter.append(String.format("%s,%.2f,%.2f,%.2f,%.2f,M%d\n",
                    randomDate.format(dateFormatter),       // Date
                    dimLong,                                // Long
                    dimLarg,                                // Larg
                    dimHaut,                                // Haut
                    revient,                                // Revient
                    random.nextInt(4) + 1                  // Machine (M1-M4)
                ));
            }

            System.out.println("REF cubic per meter : " + ref.getPricePerCubicMeter());
            System.out.println("REF total purchase price: " + ref.getTotalPurchasePrice());
            System.out.println("REF volume: " + ref.getTotalVolume());

            System.out.println("CSV file generated successfully at: " + csvPath);
        } 
        
        catch (IOException e) 
        { System.err.println("Error generating CSV file: " + e.getMessage()); }
    }

    /**
     * Generates a random date between 2022-01-01 and 2024-12-31
     * 
     * @return LocalDate object representing the random date
     */
    private static LocalDate generateRandomDate() {
        long minDay = LocalDate.of(2022, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2024, 12, 31).toEpochDay();

        long randomDay = minDay + (long) (Math.random() * (maxDay - minDay));

        return LocalDate.ofEpochDay(randomDay);
    }
}