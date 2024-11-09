package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ProductCostPrice extends BaseModel<ProductCostPrice> {

    private int productId;
    private String productLabel;
    private double productSellingPrice;
    private double productCostPrice;

    // Constructors
    public ProductCostPrice() {}

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

    public double getProductSellingPrice() {
        return productSellingPrice;
    }

    public void setProductSellingPrice(double productSellingPrice) {
        this.productSellingPrice = productSellingPrice;
    }

    public double getProductCostPrice() {
        return productCostPrice;
    }

    public void setProductCostPrice(double productCostPrice) {
        this.productCostPrice = productCostPrice;
    }

    // Mapping ResultSet to ProductCostPrice object
    @Override
    protected ProductCostPrice mapRow(ResultSet result) throws Exception {
        ProductCostPrice costPrice = new ProductCostPrice();

        costPrice.setProductId(result.getInt("product_id"));
        costPrice.setProductLabel(result.getString("product_label"));
        costPrice.setProductSellingPrice(result.getDouble("product_selling_price"));
        costPrice.setProductCostPrice(result.getDouble("product_cost_price"));

        return costPrice;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        
        fields.put("product_id", productId);
        fields.put("product_label", productLabel);
        fields.put("product_selling_price", productSellingPrice);
        fields.put("product_cost_price", productCostPrice);

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
