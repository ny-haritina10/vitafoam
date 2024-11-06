package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

public class InitialSponge extends BaseModel<InitialSponge> {

    private int id;
    private double purchasePrice;
    private String isTransformed;
    private double dimLength;
    private double dimWidth;
    private double dimHeight;

    // Constructors
    public InitialSponge() {}

    public InitialSponge(int id, double purchasePrice, String isTransformed, double dimLength, double dimWidth, double dimHeight) {
        this.id = id;
        this.purchasePrice = purchasePrice;
        this.isTransformed = isTransformed;
        this.dimLength = dimLength;
        this.dimWidth = dimWidth;
        this.dimHeight = dimHeight;
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
        if (dimLength <= 0) {
            throw new IllegalArgumentException("Dimension length must be positive.");
        }
        this.dimLength = dimLength;
    }

    public double getDimWidth() {
        return dimWidth;
    }

    public void setDimWidth(double dimWidth) {
        if (dimWidth <= 0) {
            throw new IllegalArgumentException("Dimension width must be positive.");
        }
        this.dimWidth = dimWidth;
    }

    public double getDimHeight() {
        return dimHeight;
    }

    public void setDimHeight(double dimHeight) {
        if (dimHeight <= 0) {
            throw new IllegalArgumentException("Dimension height must be positive.");
        }
        this.dimHeight = dimHeight;
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

        return fields;
    }
}