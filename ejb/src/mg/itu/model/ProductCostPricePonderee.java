package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

public class ProductCostPricePonderee extends BaseModel<ProductCostPricePonderee> {

    private int productId;
    private double productCostPrice;

    // Constructors
    public ProductCostPricePonderee() {}

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public double getProductCostPrice() {
        return productCostPrice;
    }

    public void setProductCostPrice(double productCostPrice) {
        this.productCostPrice = productCostPrice;
    }

    // Mapping ResultSet to ProductCostPricePonderee object
    @Override
    protected ProductCostPricePonderee mapRow(ResultSet result) throws Exception {
        ProductCostPricePonderee productCost = new ProductCostPricePonderee();

        productCost.setProductId(result.getInt("id_product"));
        productCost.setProductCostPrice(result.getDouble("product_cost_price"));

        return productCost;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        
        fields.put("id_product", productId);
        fields.put("product_cost_price", productCostPrice);

        return fields;
    }

    @Override
    public int getId() {
        return productId;
    }

    @Override
    protected void setId(int id) {
        this.productId = id;
    }
}