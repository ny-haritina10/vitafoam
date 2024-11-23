package mg.itu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mg.itu.database.Database;

public class MachinePriceResult {

    private int idMachine;
    private double totalPratiquePrice;
    private double totalTheoriquePrice;
    private double ecart;

    public MachinePriceResult() {}

    // Constructor with all fields
    public MachinePriceResult(int idMachine, double totalPratiquePrice, double totalTheoriquePrice, double ecart) {
        this.idMachine = idMachine;
        this.totalPratiquePrice = totalPratiquePrice;
        this.totalTheoriquePrice = totalTheoriquePrice;
        this.ecart = ecart;
    }

    // Getters and Setters
    public int getIdMachine() {
        return idMachine;
    }

    public void setIdMachine(int idMachine) {
        this.idMachine = idMachine;
    }

    public double getTotalPratiquePrice() {
        return totalPratiquePrice;
    }

    public void setTotalPratiquePrice(double totalPratiquePrice) {
        this.totalPratiquePrice = totalPratiquePrice;
    }

    public double getTotalTheoriquePrice() {
        return totalTheoriquePrice;
    }

    public void setTotalTheoriquePrice(double totalTheoriquePrice) {
        this.totalTheoriquePrice = totalTheoriquePrice;
    }

    public double getEcart() {
        return ecart;
    }

    public void setEcart(double ecart) {
        this.ecart = ecart;
    }

    // Method to map ResultSet to MachinePriceResult
    public static MachinePriceResult fromResultSet(ResultSet rs) throws Exception {
        return new MachinePriceResult(
            rs.getInt("id_machine"),
            rs.getDouble("SUM_PRACTICAL_PRICE"),
            rs.getDouble("SUM_THEORICAL_PRICE"),
            rs.getDouble("ecart")
        );
    }

    // Method to fetch all records from the view
    public static List<MachinePriceResult> getAll() throws Exception {
        List<MachinePriceResult> results = new ArrayList<>();
        String sql = "SELECT * FROM v_machine_price_comparison";

        try (Connection conn = Database.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            
            while (rs.next()) {
                results.add(MachinePriceResult.fromResultSet(rs));
            }
        }

        return results;
    }
}
