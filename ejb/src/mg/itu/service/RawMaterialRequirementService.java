package mg.itu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.math.BigDecimal;

import mg.itu.database.Database;

public class RawMaterialRequirementService {

    /**
     * Preloads raw material multipliers for all cubic meter formulas from the database.
     *
     * @return a HashMap with raw material ID as key and quantity per cubic meter as value
     * @throws Exception if a database error occurs
     */
    public static HashMap<Integer, BigDecimal> preloadRawMaterialMultipliers() throws Exception {
        HashMap<Integer, BigDecimal> rawMaterialMultipliers = new HashMap<>();

        String query = "SELECT id_raw_materiel, qte FROM CubicMeterFormula";

        try (Connection connection = Database.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int rawMaterialId = resultSet.getInt("id_raw_materiel");
                BigDecimal quantityPerCubicMeter = resultSet.getBigDecimal("qte");

                rawMaterialMultipliers.put(rawMaterialId, quantityPerCubicMeter);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error preloading raw material multipliers", e);
        }

        return rawMaterialMultipliers;
    }

    /**
     * Calculates raw material requirements for a given sponge volume using preloaded multipliers.
     *
     * @param multipliers a preloaded map of raw material multipliers (qte per cubic meter)
     * @param spongeVolume the volume of the sponge
     * @return a HashMap with raw material ID as key and required quantity as value
     */
    public static HashMap<Integer, BigDecimal> getRawMaterialRequirements(
            HashMap<Integer, BigDecimal> multipliers, BigDecimal spongeVolume) {
        HashMap<Integer, BigDecimal> rawMaterialMap = new HashMap<>();

        for (var entry : multipliers.entrySet()) {
            int rawMaterialId = entry.getKey();
            BigDecimal quantityPerCubicMeter = entry.getValue();

            // Calculate required quantity for the given volume
            BigDecimal requiredQuantity = quantityPerCubicMeter.multiply(spongeVolume);

            rawMaterialMap.put(rawMaterialId, requiredQuantity);
        }

        return rawMaterialMap;
    }
}