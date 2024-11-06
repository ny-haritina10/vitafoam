package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class TransformationTotalAmount extends BaseModel<TransformationTotalAmount> {

    private int transformationId;
    private String dateTransformation;
    private double totalManufacturedAmount;

    // Constructors
    public TransformationTotalAmount() {}

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

    public double getTotalManufacturedAmount() {
        return totalManufacturedAmount;
    }

    public void setTotalManufacturedAmount(double totalManufacturedAmount) {
        this.totalManufacturedAmount = totalManufacturedAmount;
    }

    // Mapping ResultSet to TransformationTotalAmount object
    @Override
    protected TransformationTotalAmount mapRow(ResultSet result) throws Exception {
        TransformationTotalAmount totalAmount = new TransformationTotalAmount();

        totalAmount.setTransformationId(result.getInt("transformation_id"));
        totalAmount.setDateTransformation(result.getString("date_transformation"));
        totalAmount.setTotalManufacturedAmount(result.getDouble("total_manufactured_amount"));

        return totalAmount;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        
        fields.put("transformation_id", transformationId);
        fields.put("date_transformation", dateTransformation);
        fields.put("total_manufactured_amount", totalManufacturedAmount);

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