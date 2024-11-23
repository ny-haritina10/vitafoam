package mg.itu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import mg.itu.database.Database;

public class RawMaterialRequirementService {

    /**
     * Retrieves raw material requirements for a given sponge volume.
     *
     * @param connection the database connection
     * @param spongeVolume the volume of the sponge
     * @return a HashMap with raw material ID as key and required quantity as value
     * @throws Exception if a database error occurs
     */
    public static HashMap<Integer, Double> getRawMaterialRequirementsByVolume(Connection connection, double spongeVolume) 
        throws Exception 
    {

        if (connection == null)
        { connection = Database.getConnection(); }

        HashMap<Integer, Double> rawMaterialMap = new HashMap<>();

        String query = """
                SELECT 
                    cmf.id_raw_materiel, 
                    cmf.qte * ? AS required_quantity
                FROM CubicMeterFormula cmf
                """;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, spongeVolume);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int rawMaterialId = resultSet.getInt("id_raw_materiel");
                    double requiredQuantity = resultSet.getDouble("required_quantity");

                    rawMaterialMap.put(rawMaterialId, requiredQuantity);
                }
            }
        } 
        
        catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Error fetching raw material requirements by volume", e);
        }

        finally {
            if (connection != null) 
            { connection.close(); }
        }

        return rawMaterialMap;
    }
}