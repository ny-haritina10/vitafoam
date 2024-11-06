package mg.itu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mg.itu.database.Database;
import mg.itu.model.InitialSponge;
import mg.itu.model.LossTreshold;
import mg.itu.model.Product;
import mg.itu.model.SpongeTransformation;

public class InitialSpongeService {
    
    public static void setIsTransformedFlag(String flag, int idInitialSponge, Connection connection) {
        if (connection == null) 
        { connection = Database.getConnection(); }

        String sql = "UPDATE InitialSponge SET is_transformed = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, flag); 
            preparedStatement.setInt(2, idInitialSponge); 

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) 
            { System.out.println("Successfully updated is_transformed for ID: " + idInitialSponge); } 
            
            else 
            { System.out.println("No record found with ID: " + idInitialSponge); }
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
        } 
        
        finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close PreparedStatement: " + e.getMessage());
                }
            }

            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Failed to close Connection: " + e.getMessage());
                }
            }
        }
    }  

    public static void insertRemaining (
        InitialSponge initialBlock, 
        double remainingHeight, 
        double remainingLength, 
        double remainingWidth
    ) 
        throws Exception
    {
        InitialSponge remainingSponge = new InitialSponge();

        remainingSponge.setPurchasePrice(initialBlock.getPurchasePrice());
        remainingSponge.setIsTransformed("FALSE");
        remainingSponge.setDimHeight(remainingHeight);
        remainingSponge.setDimLength(remainingLength);
        remainingSponge.setDimWidth(remainingWidth);

        remainingSponge.save(null, "InitialSponge", "seq_initial_sponge.NEXTVAL");
    }

    public static InitialSponge getLastInitialSpongeInserted() 
        throws Exception
    {
        return new InitialSponge().getById(new InitialSponge().getMaxId(null, "InitialSponge"), InitialSponge.class, null);
    }

    public static double getVolume(InitialSponge block) 
    { return block.getDimHeight() * block.getDimLength() * block.getDimWidth(); }


    public static double getAcceptedLoss(InitialSponge block, LossTreshold loss) 
        throws Exception
    {
        double volumeBlock = getVolume(block);
        System.out.println(loss.getThetha() + "*" + volumeBlock + "/100");
        return (loss.getThetha() * volumeBlock) / 100;
    }
}