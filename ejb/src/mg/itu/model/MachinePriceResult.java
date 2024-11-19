package mg.itu.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import mg.itu.database.Database;

public class MachinePriceResult {
    private int idMachine;
    private String machineName;
    private double totalPratiquePrice;
    private double totalTheoriquePrice;
    private double ecart;

    public MachinePriceResult() {}

    // Constructor with all fields
    public MachinePriceResult(int idMachine, String machineName, double totalPratiquePrice, double totalTheoriquePrice, double ecart) {
        this.idMachine = idMachine;
        this.machineName = machineName;
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

    public String getMachineName() {
        return machineName;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
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
            rs.getString("machine_name"),
            rs.getDouble("total_pratique_price"),
            rs.getDouble("total_theorique_price"),
            rs.getDouble("ecart")
        );
    }

    public static List<MachinePriceResult> getAll(int limitRef, int randMin, int randMax) 
        throws Exception 
    {
        List<MachinePriceResult> results = new ArrayList<>();
        String sql = "SELECT * FROM TABLE(get_final_result(?, ?, ?))";
        
        try (Connection conn = Database.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, limitRef);
            pstmt.setInt(2, randMin);
            pstmt.setInt(3, randMax);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) 
                { results.add(MachinePriceResult.fromResultSet(rs)); }
            }
        }
        
        return results;
    }
}