package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Unit extends BaseModel<Unit> {

    private int id;
    private String label;

    // Constructors
    public Unit() {}

    public Unit(int id, String label) {
        this.id = id;
        this.label = label;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    // Mapping ResultSet to Unit object
    @Override
    protected Unit mapRow(ResultSet result) throws Exception {
        Unit unit = new Unit();
        unit.setId(result.getInt("id"));
        unit.setLabel(result.getString("label"));
        return unit;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("label", label);
        return fields;
    }
}