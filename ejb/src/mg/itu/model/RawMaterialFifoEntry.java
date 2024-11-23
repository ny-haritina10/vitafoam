package mg.itu.model;

import mg.itu.base.BaseModel;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class RawMaterialFifoEntry extends BaseModel<RawMaterialFifoEntry> {

    private int rawMaterialId;
    private String datePurchase;
    private double unitPrice;
    private int availableQuantity;
    private int lotNumber;

    // Constructors
    public RawMaterialFifoEntry() {}

    // Getters and Setters
    public int getRawMaterialId() {
        return rawMaterialId;
    }

    public void setRawMaterialId(int rawMaterialId) {
        this.rawMaterialId = rawMaterialId;
    }

    public String getDatePurchase() {
        return datePurchase;
    }

    public void setDatePurchase(String datePurchase) {
        this.datePurchase = datePurchase;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public int getLotNumber() {
        return lotNumber;
    }

    public void setLotNumber(int lotNumber) {
        this.lotNumber = lotNumber;
    }

    // Mapping ResultSet to RawMaterialFifoEntry object
    @Override
    protected RawMaterialFifoEntry mapRow(ResultSet result) throws Exception {
        RawMaterialFifoEntry fifoEntry = new RawMaterialFifoEntry();

        fifoEntry.setRawMaterialId(result.getInt("id_raw_materiel"));
        fifoEntry.setDatePurchase(result.getString("date_purchase"));
        fifoEntry.setUnitPrice(result.getDouble("unit_price"));
        fifoEntry.setAvailableQuantity(result.getInt("available_quantity"));
        fifoEntry.setLotNumber(result.getInt("lot_number"));

        return fifoEntry;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();

        fields.put("id_raw_materiel", rawMaterialId);
        fields.put("date_purchase", datePurchase);
        fields.put("unit_price", unitPrice);
        fields.put("available_quantity", availableQuantity);
        fields.put("lot_number", lotNumber);

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