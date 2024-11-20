package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class Machine extends BaseModel<Machine> {

    private int id;
    private String label;

    // Constructors
    public Machine() {}

    public Machine(int id, String label) {
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

    // Mapping ResultSet to Machine object
    @Override
    protected Machine mapRow(ResultSet result) throws Exception {
        Machine machine = new Machine();
        machine.setId(result.getInt("id"));
        machine.setLabel(result.getString("label"));
        return machine;
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