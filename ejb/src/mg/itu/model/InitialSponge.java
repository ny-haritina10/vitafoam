package mg.itu.model;

import mg.itu.base.BaseModel;
import mg.itu.database.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;

public class InitialSponge extends BaseModel<InitialSponge> {

    private int id;
    private double purchasePrice;
    private String isTransformed;
    private double dimLength;
    private double dimWidth;
    private double dimHeight;

    private Date dateCreation;
    private Machine machine;

    // Constructors
    public InitialSponge() {}

    public InitialSponge(int id, double purchasePrice, String isTransformed, double dimLength, double dimWidth, double dimHeight, Date dateCreation, Machine machine) {
        this.id = id;
        this.purchasePrice = purchasePrice;
        this.isTransformed = isTransformed;
        this.dimLength = dimLength;
        this.dimWidth = dimWidth;
        this.dimHeight = dimHeight;
        this.dateCreation = dateCreation;
        this.machine = machine;
    }

    // get blocks ref
    public InitialSponge[] getReferenceBlock(int limit) 
        throws Exception 
    {
        InitialSponge[] references = new InitialSponge[limit];
        String query = "SELECT * FROM (SELECT * FROM InitialSponge ORDER BY id ASC) WHERE ROWNUM <= ?";

        try (Connection connection = Database.getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            
            preparedStatement.setInt(1, limit);
            ResultSet resultSet = preparedStatement.executeQuery();

            int index = 0;
            while (resultSet.next() && index < limit) {
                references[index] = mapRow(resultSet);
                index++;
            }
        } 
        
        catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error retrieving the reference block: ", e); 
        }

        return references;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        if (purchasePrice < 0) {
            throw new IllegalArgumentException("Purchase price cannot be negative.");
        }
        this.purchasePrice = purchasePrice;
    }

    public String getIsTransformed() {
        return isTransformed;
    }

    public void setIsTransformed(String isTransformed) {
        this.isTransformed = isTransformed;
    }

    public double getDimLength() {
        return dimLength;
    }

    public void setDimLength(double dimLength) {
        if (dimLength < 0) {
            throw new IllegalArgumentException("Dimension length must be positive.");
        }
        this.dimLength = dimLength;
    }

    public double getDimWidth() {
        return dimWidth;
    }

    public void setDimWidth(double dimWidth) {
        if (dimWidth < 0) {
            throw new IllegalArgumentException("Dimension width must be positive.");
        }
        this.dimWidth = dimWidth;
    }

    public double getDimHeight() {
        return dimHeight;
    }

    public void setDimHeight(double dimHeight) {
        if (dimHeight < 0) {
            throw new IllegalArgumentException("Dimension height must be positive.");
        }
        this.dimHeight = dimHeight;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    // Mapping ResultSet to InitialSponge object
    @Override
    protected InitialSponge mapRow(ResultSet result) 
        throws Exception 
    {
        InitialSponge sponge = new InitialSponge();

        sponge.setId(result.getInt("id"));
        sponge.setPurchasePrice(result.getDouble("purchase_price"));
        sponge.setIsTransformed(result.getString("is_transformed"));
        sponge.setDimLength(result.getDouble("dim_length"));
        sponge.setDimWidth(result.getDouble("dim_width"));
        sponge.setDimHeight(result.getDouble("dim_height"));
        sponge.setDateCreation(result.getDate("date_creation"));
        sponge.setMachine(new Machine().getById(result.getInt("id_machine"), Machine.class, null));

        return sponge;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        
        fields.put("id", id);
        fields.put("purchase_price", purchasePrice);
        fields.put("is_transformed", isTransformed);
        fields.put("dim_length", dimLength);
        fields.put("dim_width", dimWidth);
        fields.put("dim_height", dimHeight);
        fields.put("date_creation", dateCreation);
        fields.put("id_machine", machine != null ? machine.getId() : null);

        return fields;
    }
}