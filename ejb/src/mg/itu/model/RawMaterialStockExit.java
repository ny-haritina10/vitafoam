package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class RawMaterialStockExit extends BaseModel<RawMaterialStockExit> {

    private int id;
    private int rawMaterialId;
    private Date dateSession;
    private double quantityOut;
    private double unitPrice;
    private double amount;

    // Constructors
    public RawMaterialStockExit() {}

    // Getters and Setters
    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public Date getDateSession() {
        return dateSession;
    }

    public void setDateSession(Date dateSession) {
        this.dateSession = dateSession;
    }

    public double getQuantityOut() {
        return quantityOut;
    }

    public void setQuantityOut(double quantityOut) {
        this.quantityOut = quantityOut;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Mapping ResultSet to RawMaterialStockExit object
    @Override
    protected RawMaterialStockExit mapRow(ResultSet result) throws Exception {
        RawMaterialStockExit stockExit = new RawMaterialStockExit();

        stockExit.setId(result.getInt("id"));
        stockExit.setRawMaterialId(result.getInt("id_raw_materiel"));
        stockExit.setDateSession(Date.valueOf(result.getString("date_session")));
        stockExit.setQuantityOut(result.getDouble("qte_out"));
        stockExit.setUnitPrice(result.getDouble("unit_price"));
        stockExit.setAmount(result.getDouble("amount"));

        return stockExit;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();

        fields.put("id", id);
        fields.put("id_raw_materiel", rawMaterialId);
        fields.put("date_session", dateSession);
        fields.put("qte_out", quantityOut);
        fields.put("unit_price", unitPrice);
        fields.put("amount", amount);

        return fields;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    protected void setId(int id) {
        this.id = id;
    }
}