package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Product extends BaseModel<Product> {

    private int id;
    private String label;
    private double sellingPrice;
    private double dimLength;
    private double dimWidth;
    private double dimHeight;

    // Constructors
    public Product() {}

    public Product(int id, String label, double sellingPrice, double dimLength, double dimWidth, double dimHeight) {
        setId(id);
        setLabel(label);
        setSellingPrice(sellingPrice);
        setDimLength(dimLength);
        setDimWidth(dimWidth);
        setDimHeight(dimHeight);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label == null || label.trim().isEmpty()) {
            throw new IllegalArgumentException("Label cannot be null or empty.");
        }
        this.label = label;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        if (sellingPrice < 0) {
            throw new IllegalArgumentException("Selling price cannot be negative.");
        }
        this.sellingPrice = sellingPrice;
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

    // Mapping ResultSet to Product object
    @Override
    protected Product mapRow(ResultSet result) 
        throws Exception 
    {
        Product product = new Product();

        product.setId(result.getInt("id"));
        product.setLabel(result.getString("label"));
        product.setSellingPrice(result.getDouble("selling_price"));
        product.setDimLength(result.getDouble("dim_length"));
        product.setDimWidth(result.getDouble("dim_width"));
        product.setDimHeight(result.getDouble("dim_height"));
        
        return product;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("label", label);
        fields.put("selling_price", sellingPrice);
        fields.put("dim_length", dimLength);
        fields.put("dim_width", dimWidth);
        fields.put("dim_height", dimHeight);

        return fields;
    }
}