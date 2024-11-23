package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class RawMaterialRequirement extends BaseModel<RawMaterialRequirement> {

    private int spongeId;
    private String dateCreation;
    private double spongeVolume;
    private int rawMaterialId;
    private double requiredQuantity;

    // Constructors
    public RawMaterialRequirement() {}

    // Getters and Setters
    public int getSpongeId() {
        return spongeId;
    }

    public void setSpongeId(int spongeId) {
        this.spongeId = spongeId;
    }

    public String getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(String dateCreation) {
        this.dateCreation = dateCreation;
    }

    public double getSpongeVolume() {
        return spongeVolume;
    }

    public void setSpongeVolume(double spongeVolume) {
        this.spongeVolume = spongeVolume;
    }

    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public double getRequiredQuantity() {
        return requiredQuantity;
    }

    public void setRequiredQuantity(double requiredQuantity) {
        this.requiredQuantity = requiredQuantity;
    }

    // Mapping ResultSet to RawMaterialRequirement object
    @Override
    protected RawMaterialRequirement mapRow(ResultSet result) throws Exception {
        RawMaterialRequirement requirement = new RawMaterialRequirement();

        requirement.setSpongeId(result.getInt("sponge_id"));
        requirement.setDateCreation(result.getString("date_creation"));
        requirement.setSpongeVolume(result.getDouble("sponge_volume"));
        requirement.setRawMaterialId(result.getInt("id_raw_materiel"));
        requirement.setRequiredQuantity(result.getDouble("required_quantity"));

        return requirement;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();

        fields.put("sponge_id", spongeId);
        fields.put("date_creation", dateCreation);
        fields.put("sponge_volume", spongeVolume);
        fields.put("id_raw_materiel", rawMaterialId);
        fields.put("required_quantity", requiredQuantity);

        return fields;
    }

    @Override
    public int getId() {
        // Implement if this model has an ID
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    protected void setId(int id) {
        // Implement if this model has an ID
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}