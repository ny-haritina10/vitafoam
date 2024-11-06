package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class MostProfitableProduct extends BaseModel<MostProfitableProduct> {

    private int productId;
    private String productLabel;
    private double sellingPrice;
    private double dimLength;
    private double dimWidth;
    private double dimHeight;
    private double volume;
    private double priceToVolumeRatio;

    // Constructors
    public MostProfitableProduct() {}

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

    public double getPriceToVolumeRatio() {
        return priceToVolumeRatio;
    }

    public void setPriceToVolumeRatio(double priceToVolumeRatio) {
        this.priceToVolumeRatio = priceToVolumeRatio;
    }

    // Mapping ResultSet to MostProfitableProduct object
    @Override
    protected MostProfitableProduct mapRow(ResultSet result) throws Exception {
        MostProfitableProduct product = new MostProfitableProduct();

        product.setProductId(result.getInt("product_id"));
        product.setProductLabel(result.getString("product_label"));
        product.setSellingPrice(result.getDouble("selling_price"));
        product.setDimLength(result.getDouble("dim_length"));
        product.setDimWidth(result.getDouble("dim_width"));
        product.setDimHeight(result.getDouble("dim_height"));
        product.setVolume(result.getDouble("volume"));
        product.setPriceToVolumeRatio(result.getDouble("price_to_volume_ratio"));

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
        fields.put("price_to_volume_ratio", priceToVolumeRatio);

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