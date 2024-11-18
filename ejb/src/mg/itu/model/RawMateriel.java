package mg.itu.model;

import mg.itu.base.BaseModel;
import mg.itu.database.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class RawMateriel extends BaseModel<RawMateriel> {

    private int id;
    private Unit unit;
    private String label;

    // Constructors
    public RawMateriel() {}

    public RawMateriel(int id, Unit unit, String label) {
        setId(id);
        setUnit(unit);
        setLabel(label);
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID must be positive.");
        }
        this.id = id;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("Unit cannot be null.");
        }
        this.unit = unit;
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

    // Mapping ResultSet to RawMateriel object
    @Override
    protected RawMateriel mapRow(ResultSet result) throws Exception {
        RawMateriel rawMateriel = new RawMateriel();
        rawMateriel.setId(result.getInt("id"));

        Unit unit = new Unit().getById(result.getInt("id_unit"), Unit.class, null, "Unit");
        rawMateriel.setUnit(unit);
        rawMateriel.setLabel(result.getString("label"));

        return rawMateriel;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("id_unit", unit != null ? unit.getId() : null);
        fields.put("label", label);
        return fields;
    }
}