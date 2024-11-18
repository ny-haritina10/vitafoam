package mg.itu.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mg.itu.database.Database;
import mg.itu.model.InitialSponge;
import mg.itu.model.LossTreshold;

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
        
        catch (SQLException e) 
        { e.printStackTrace(); } 
        
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

        // update purchase price when inserting the remaining 
        // new purchase price of remaining is based on volume
        double pricePerCubicMeter = getPurchasePricePerCubicMeter(initialBlock);
        double newPurchasePrice = pricePerCubicMeter * (remainingHeight * remainingLength * remainingWidth);

        remainingSponge.setPurchasePrice(newPurchasePrice);
        remainingSponge.setIsTransformed("FALSE");
        remainingSponge.setDimHeight(remainingHeight);
        remainingSponge.setDimLength(remainingLength);
        remainingSponge.setDimWidth(remainingWidth);

        remainingSponge.save(null, "InitialSponge", "seq_initial_sponge.NEXTVAL");
    }

    public static InitialSponge getLastInitialSpongeInserted() 
        throws Exception
    {
        int maxId = new InitialSponge().getMaxId(null, "InitialSponge");
        return new InitialSponge().getById(maxId, InitialSponge.class, null);
    }

    public static double getVolume(InitialSponge block) 
    { return block.getDimHeight() * block.getDimLength() * block.getDimWidth(); }

    public static double getAcceptedLoss(InitialSponge block, LossTreshold loss) 
        throws Exception
    {
        double volumeBlock = getVolume(block);
        return (loss.getThetha() * volumeBlock) / 100;
    }

    public static void updatePurchasePriceRecursive(int idInitialSponge, double newPurchasePrice, Connection connection) 
        throws Exception
    {
        if (connection == null) {
            connection = Database.getConnection();
        }

        // udpate purchase price of source block
        updatePurchasePrice(idInitialSponge, newPurchasePrice, null);
        int idSpongeFille = getSourceFilleId(idInitialSponge, null);

        if (idSpongeFille > 0) {
            // calculate new purchase price from child block
            double newPurchasePriceForFille = InitialSpongeService.getNewPurchasePrice(
                new InitialSponge().getById(idInitialSponge, InitialSponge.class, null),
                new InitialSponge().getById(idSpongeFille, InitialSponge.class, null)
            );

            // update child purchase price recursively
            updatePurchasePriceRecursive(idSpongeFille, newPurchasePriceForFille, null);
        }
    }

    public static void updatePurchasePrice(int idSpongeFille, double newPurchasePrice, Connection connection) {
        if (connection == null) {
            connection = Database.getConnection();
        }

        String sql = "UPDATE InitialSponge SET purchase_price = ? WHERE id = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newPurchasePrice);
            preparedStatement.setInt(2, idSpongeFille);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Successfully updated purchase_price for ID: " + idSpongeFille);
            } else {
                System.out.println("No record found with ID: " + idSpongeFille);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

    public static int getSourceFilleId(int idSpongeSource, Connection connection) {
        int id = 0;

        if (connection == null) {
            connection = Database.getConnection();
        }

        String sql = "SELECT id_sponge_fille FROM v_source_fille WHERE id_sponge_source = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idSpongeSource);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id += resultSet.getInt("id_sponge_fille");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
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

        return id;
    }

    public static double getNewPurchasePrice(InitialSponge blockSource, InitialSponge blockFille) {
        double pricePerCubicMeter = getPurchasePricePerCubicMeter(blockSource);
        double newPurchasePrice = pricePerCubicMeter * (
            blockFille.getDimHeight() * 
            blockFille.getDimLength() * 
            blockFille.getDimWidth()
        );

        return newPurchasePrice;
    }

    public static double getPurchasePricePerCubicMeter(InitialSponge block) {
        return (block.getPurchasePrice() / (
            block.getDimHeight() * 
            block.getDimLength() * 
            block.getDimWidth()
        ));
    }
}