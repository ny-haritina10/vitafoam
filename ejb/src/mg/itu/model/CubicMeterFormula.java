package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class CubicMeterFormula extends BaseModel<CubicMeterFormula> {

    private int id;
    private RawMateriel rawMateriel;
    private double qte;

    // Constructors
    public CubicMeterFormula() {}

    public CubicMeterFormula(int id, RawMateriel rawMateriel, double qte) {
        setId(id);
        setRawMateriel(rawMateriel);
        setQte(qte);
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

    public RawMateriel getRawMateriel() {
        return rawMateriel;
    }

    public void setRawMateriel(RawMateriel rawMateriel) {
        if (rawMateriel == null) {
            throw new IllegalArgumentException("RawMateriel cannot be null.");
        }
        this.rawMateriel = rawMateriel;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) {
        if (qte < 0) {
            throw new IllegalArgumentException("Quantity (qte) must be non-negative.");
        }
        this.qte = qte;
    }

    // Mapping ResultSet to CubicMeterFormula object
    @Override
    protected CubicMeterFormula mapRow(ResultSet result) throws Exception {
        CubicMeterFormula formula = new CubicMeterFormula();
        formula.setId(result.getInt("id"));

        RawMateriel rawMateriel = new RawMateriel().getById(result.getInt("id_raw_materiel"), RawMateriel.class, null, "RawMateriel");
        formula.setRawMateriel(rawMateriel);

        formula.setQte(result.getDouble("qte"));

        return formula;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("id_raw_materiel", rawMateriel != null ? rawMateriel.getId() : null);
        fields.put("qte", qte);
        return fields;
    }
}