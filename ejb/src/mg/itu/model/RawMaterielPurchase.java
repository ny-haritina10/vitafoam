package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class RawMaterielPurchase extends BaseModel<RawMaterielPurchase> {

    private int id;
    private RawMateriel rawMateriel;
    private double qte;
    private Date datePurchase;

    // Constructors
    public RawMaterielPurchase() {}

    public RawMaterielPurchase(int id, RawMateriel rawMateriel, double qte, Date datePurchase) {
        setId(id);
        setRawMateriel(rawMateriel);
        setQte(qte);
        setDatePurchase(datePurchase);
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

    public Date getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(Date datePurchase) {
        if (datePurchase == null) {
            throw new IllegalArgumentException("Date of purchase cannot be null.");
        }
        this.datePurchase = datePurchase;
    }

    // Mapping ResultSet to RawMaterielPurchase object
    @Override
    protected RawMaterielPurchase mapRow(ResultSet result) throws Exception {
        RawMaterielPurchase purchase = new RawMaterielPurchase();
        purchase.setId(result.getInt("id"));

        RawMateriel rawMateriel = new RawMateriel().getById(result.getInt("id_raw_materiel"), RawMateriel.class, null, "RawMateriel");
        purchase.setRawMateriel(rawMateriel);

        purchase.setQte(result.getDouble("qte"));
        purchase.setDatePurchase(result.getDate("date_purchase"));

        return purchase;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("id", id);
        fields.put("id_raw_materiel", rawMateriel != null ? rawMateriel.getId() : null);
        fields.put("qte", qte);
        fields.put("date_purchase", datePurchase);
        return fields;
    }
}