package mg.itu.model;

import mg.itu.base.BaseModel;

import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

public class ReferenceBlockSummary extends BaseModel<ReferenceBlockSummary> {

    private double totalPurchasePrice;
    private double totalVolume;
    private double pricePerCubicMeter;

    // Constructors
    public ReferenceBlockSummary() {}

    public ReferenceBlockSummary(double totalPurchasePrice, double totalVolume, double pricePerCubicMeter) {
        setTotalPurchasePrice(totalPurchasePrice);
        setTotalVolume(totalVolume);
        setPricePerCubicMeter(pricePerCubicMeter);
    }

    // Getters and Setters
    public double getTotalPurchasePrice() {
        return totalPurchasePrice;
    }

    public void setTotalPurchasePrice(double totalPurchasePrice) {
        if (totalPurchasePrice < 0) {
            throw new IllegalArgumentException("Total purchase price cannot be negative.");
        }
        this.totalPurchasePrice = totalPurchasePrice;
    }

    public double getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(double totalVolume) {
        if (totalVolume < 0) {
            throw new IllegalArgumentException("Total volume cannot be negative.");
        }
        this.totalVolume = totalVolume;
    }

    public double getPricePerCubicMeter() {
        return pricePerCubicMeter;
    }

    public void setPricePerCubicMeter(double pricePerCubicMeter) {
        if (pricePerCubicMeter < 0) {
            throw new IllegalArgumentException("Price per cubic meter cannot be negative.");
        }
        this.pricePerCubicMeter = pricePerCubicMeter;
    }

    // Mapping ResultSet to ReferenceBlockSummary object
    @Override
    protected ReferenceBlockSummary mapRow(ResultSet result) throws Exception {
        ReferenceBlockSummary summary = new ReferenceBlockSummary();
        summary.setTotalPurchasePrice(result.getDouble("total_purchase_price"));
        summary.setTotalVolume(result.getDouble("total_volume"));
        summary.setPricePerCubicMeter(result.getDouble("price_per_cubic_meter"));
        return summary;
    }

    // Getting fields and values for SQL operations
    @Override
    protected Map<String, Object> getFieldsMap() {
        Map<String, Object> fields = new HashMap<>();
        fields.put("total_purchase_price", totalPurchasePrice);
        fields.put("total_volume", totalVolume);
        fields.put("price_per_cubic_meter", pricePerCubicMeter);
        return fields;
    }

    @Override
    public int getId() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getId'");
    }

    @Override
    public void setId(int arg0) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setId'");
    }
}
