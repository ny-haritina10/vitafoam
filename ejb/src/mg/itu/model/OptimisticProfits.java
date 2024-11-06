package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class OptimisticProfits extends BaseModel<OptimisticProfits> {

    private int initialSpongeId;
    private double purchasePrice;
    private double dimLength;
    private double dimWidth;
    private double dimHeight;
    private double initialVolume;
    private double optimisticProfit;
    private int productId;
    private String productLabel;
    private double productPrice;
    private double productVolume;
    private int possibleQuantity;

    // Constructors
    public OptimisticProfits() {}

    // Getters and Setters
    public int getInitialSpongeId() {
        return initialSpongeId;
    }

    public void setInitialSpongeId(int initialSpongeId) {
        this.initialSpongeId = initialSpongeId;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public double getInitialVolume() {
        return initialVolume;
    }

    public void setInitialVolume(double initialVolume) {
        this.initialVolume = initialVolume;
    }

    public double getOptimisticProfit() {
        return optimisticProfit;
    }

    public void setOptimisticProfit(double optimisticProfit) {
        this.optimisticProfit = optimisticProfit;
    }

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

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductVolume() {
        return productVolume;
    }

    public void setProductVolume(double productVolume) {
        this.productVolume = productVolume;
    }

    public int getPossibleQuantity() {
        return possibleQuantity;
    }

    public void setPossibleQuantity(int possibleQuantity) {
        this.possibleQuantity = possibleQuantity;
    }

    // Mapping ResultSet to OptimisticProfits object
    @Override
    protected OptimisticProfits mapRow(ResultSet result) throws Exception {
        OptimisticProfits profits = new OptimisticProfits();

        profits.setInitialSpongeId(result.getInt("initial_sponge_id"));
        profits.setPurchasePrice(result.getDouble("purchase_price"));
        profits.setDimLength(result.getDouble("dim_length"));
        profits.setDimWidth(result.getDouble("dim_width"));
        profits.setDimHeight(result.getDouble("dim_height"));
        profits.setInitialVolume(result.getDouble("initial_volume"));
        profits.setOptimisticProfit(result.getDouble("optimistic_profit"));
        profits.setProductId(result.getInt("product_id"));
        profits.setProductLabel(result.getString("product_label"));
        profits.setProductPrice(result.getDouble("product_price"));
        profits.setProductVolume(result.getDouble("product_volume"));
        profits.setPossibleQuantity(result.getInt("possible_quantity"));

        return profits;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        
        fields.put("initial_sponge_id", initialSpongeId);
        fields.put("purchase_price", purchasePrice);
        fields.put("dim_length", dimLength);
        fields.put("dim_width", dimWidth);
        fields.put("dim_height", dimHeight);
        fields.put("initial_volume", initialVolume);
        fields.put("optimistic_profit", optimisticProfit);
        fields.put("product_id", productId);
        fields.put("product_label", productLabel);
        fields.put("product_price", productPrice);
        fields.put("product_volume", productVolume);
        fields.put("possible_quantity", possibleQuantity);

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