package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TransformationDetail extends BaseModel<TransformationDetail> {

    private int transformationId;
    private String dateTransformation;
    
    private int initialSpongeId;
    private double initialPurchasePrice;
    private double initialLength;
    private double initialWidth;
    private double initialHeight;
    private String isTransformed;
    
    private int productTransformationId;
    private int productId;
    private String productLabel;
    private double productSellingPrice;
    private double productLength;
    private double productWidth;
    private double productHeight;
    private int productQuantity;
    
    private int remainingTransformationId;
    private int remainingInitialSpongeId;

    // Constructors
    public TransformationDetail() {}

    // Getters and Setters
    public int getTransformationId() {
        return transformationId;
    }

    public void setTransformationId(int transformationId) {
        this.transformationId = transformationId;
    }

    public String getDateTransformation() {
        return dateTransformation;
    }

    public void setDateTransformation(String dateTransformation) {
        this.dateTransformation = dateTransformation;
    }

    public int getInitialSpongeId() {
        return initialSpongeId;
    }

    public void setInitialSpongeId(int initialSpongeId) {
        this.initialSpongeId = initialSpongeId;
    }

    public double getInitialPurchasePrice() {
        return initialPurchasePrice;
    }

    public void setInitialPurchasePrice(double initialPurchasePrice) {
        this.initialPurchasePrice = initialPurchasePrice;
    }

    public double getInitialLength() {
        return initialLength;
    }

    public void setInitialLength(double initialLength) {
        this.initialLength = initialLength;
    }

    public double getInitialWidth() {
        return initialWidth;
    }

    public void setInitialWidth(double initialWidth) {
        this.initialWidth = initialWidth;
    }

    public double getInitialHeight() {
        return initialHeight;
    }

    public void setInitialHeight(double initialHeight) {
        this.initialHeight = initialHeight;
    }

    public String getIsTransformed() {
        return isTransformed;
    }

    public void setIsTransformed(String isTransformed) {
        this.isTransformed = isTransformed;
    }

    public int getProductTransformationId() {
        return productTransformationId;
    }

    public void setProductTransformationId(int productTransformationId) {
        this.productTransformationId = productTransformationId;
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

    public double getProductSellingPrice() {
        return productSellingPrice;
    }

    public void setProductSellingPrice(double productSellingPrice) {
        this.productSellingPrice = productSellingPrice;
    }

    public double getProductLength() {
        return productLength;
    }

    public void setProductLength(double productLength) {
        this.productLength = productLength;
    }

    public double getProductWidth() {
        return productWidth;
    }

    public void setProductWidth(double productWidth) {
        this.productWidth = productWidth;
    }

    public double getProductHeight() {
        return productHeight;
    }

    public void setProductHeight(double productHeight) {
        this.productHeight = productHeight;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getRemainingTransformationId() {
        return remainingTransformationId;
    }

    public void setRemainingTransformationId(int remainingTransformationId) {
        this.remainingTransformationId = remainingTransformationId;
    }

    public int getRemainingInitialSpongeId() {
        return remainingInitialSpongeId;
    }

    public void setRemainingInitialSpongeId(int remainingInitialSpongeId) {
        this.remainingInitialSpongeId = remainingInitialSpongeId;
    }

    // Mapping ResultSet to TransformationDetail object
    @Override
    protected TransformationDetail mapRow(ResultSet result) throws Exception {
        TransformationDetail detail = new TransformationDetail();

        detail.setTransformationId(result.getInt("transformation_id"));
        detail.setDateTransformation(result.getString("date_transformation"));

        detail.setInitialSpongeId(result.getInt("initial_sponge_id"));
        detail.setInitialPurchasePrice(result.getDouble("initial_purchase_price"));
        detail.setInitialLength(result.getDouble("initial_length"));
        detail.setInitialWidth(result.getDouble("initial_width"));
        detail.setInitialHeight(result.getDouble("initial_height"));
        detail.setIsTransformed(result.getString("is_transformed"));

        detail.setProductTransformationId(result.getInt("product_transformation_id"));
        detail.setProductId(result.getInt("product_id"));
        detail.setProductLabel(result.getString("product_label"));
        detail.setProductSellingPrice(result.getDouble("product_selling_price"));
        detail.setProductLength(result.getDouble("product_length"));
        detail.setProductWidth(result.getDouble("product_width"));
        detail.setProductHeight(result.getDouble("product_height"));
        detail.setProductQuantity(result.getInt("product_quantity"));

        detail.setRemainingTransformationId(result.getInt("remaining_transformation_id"));
        detail.setRemainingInitialSpongeId(result.getInt("remaining_initial_sponge_id"));

        return detail;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        
        fields.put("transformation_id", transformationId);
        fields.put("date_transformation", dateTransformation);
        
        fields.put("initial_sponge_id", initialSpongeId);
        fields.put("initial_purchase_price", initialPurchasePrice);
        fields.put("initial_length", initialLength);
        fields.put("initial_width", initialWidth);
        fields.put("initial_height", initialHeight);
        fields.put("is_transformed", isTransformed);
        
        fields.put("product_transformation_id", productTransformationId);
        fields.put("product_id", productId);
        fields.put("product_label", productLabel);
        fields.put("product_selling_price", productSellingPrice);
        fields.put("product_length", productLength);
        fields.put("product_width", productWidth);
        fields.put("product_height", productHeight);
        fields.put("product_quantity", productQuantity);

        fields.put("remaining_transformation_id", remainingTransformationId);
        fields.put("remaining_initial_sponge_id", remainingInitialSpongeId);

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