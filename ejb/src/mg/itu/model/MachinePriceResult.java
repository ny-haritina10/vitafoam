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
    private int count;

    public MachinePriceResult() {}

    // Constructor with all fields
    public MachinePriceResult(int idMachine, double totalPratiquePrice, double totalTheoriquePrice, double ecart, int count) {
        this.idMachine = idMachine;
        this.totalPratiquePrice = totalPratiquePrice;
        this.totalTheoriquePrice = totalTheoriquePrice;
        this.ecart = ecart;
        this.count = count;
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
            rs.getInt("machine"),
            rs.getDouble("somme_prix_revient_pratique"),
            rs.getDouble("somme_prix_revient_theorique"),
            rs.getDouble("ecart"),
            rs.getInt("qte_fabrique")
        );
    }

    public static List<MachinePriceResult> getAll(int year) 
        throws Exception 
    {
        List<MachinePriceResult> results = new ArrayList<>();

        StringBuilder sqlBuilder = new StringBuilder("""
            SELECT 
                sm.id AS machine,
                SUM(COALESCE(se.purchase_price_theorical, 0)) AS somme_prix_revient_theorique, 
                SUM(COALESCE(se.purchase_price, 0)) AS somme_prix_revient_pratique,
                ABS(SUM(COALESCE(se.purchase_price, 0)) - SUM(COALESCE(se.purchase_price_theorical, 0))) AS ecart,
                COUNT(se.id) AS qte_fabrique
            FROM 
                Machine sm 
            LEFT JOIN 
                InitialSponge se ON sm.id = se.id_machine
        """);

        if (year != 0) {
            sqlBuilder.append(" AND EXTRACT(YEAR FROM se.date_creation) = ? ");
        }

        sqlBuilder.append(" GROUP BY sm.id, sm.label ORDER BY ecart ASC");

        String sql = sqlBuilder.toString();

        try (Connection conn = Database.getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (year != 0) {
                pstmt.setInt(1, year);
            }

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) { 
                    results.add(MachinePriceResult.fromResultSet(rs)); 
                }
            }
        }

        return results;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }    
}