package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class MinimumVolumeProduct extends BaseModel<MinimumVolumeProduct> {

    private int productId;
    private String productLabel;
    private double sellingPrice;
    private double dimLength;
    private double dimWidth;
    private double dimHeight;
    private double volume;

    // Constructors
    public MinimumVolumeProduct() {}

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public double getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(double sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public double getDimLength() {
        return dimLength;
    }

    public void setDimLength(double dimLength) {
        this.dimLength = dimLength;
    }

    public double getDimWidth() {
        return dimWidth;
    }

    public void setDimWidth(double dimWidth) {
        this.dimWidth = dimWidth;
    }

    public double getDimHeight() {
        return dimHeight;
    }

    public void setDimHeight(double dimHeight) {
        this.dimHeight = dimHeight;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    // Mapping ResultSet to MinimumVolumeProduct object
    @Override
    protected MinimumVolumeProduct mapRow(ResultSet result) throws Exception {
        MinimumVolumeProduct product = new MinimumVolumeProduct();

        product.setProductId(result.getInt("product_id"));
        product.setProductLabel(result.getString("product_label"));
        product.setSellingPrice(result.getDouble("selling_price"));
        product.setDimLength(result.getDouble("dim_length"));
        product.setDimWidth(result.getDouble("dim_width"));
        product.setDimHeight(result.getDouble("dim_height"));
        product.setVolume(result.getDouble("volume"));

        return product;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        
        fields.put("product_id", productId);
        fields.put("product_label", productLabel);
        fields.put("selling_price", sellingPrice);
        fields.put("dim_length", dimLength);
        fields.put("dim_width", dimWidth);
        fields.put("dim_height", dimHeight);
        fields.put("volume", volume);

        return fields;
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    protected void setId(int arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}