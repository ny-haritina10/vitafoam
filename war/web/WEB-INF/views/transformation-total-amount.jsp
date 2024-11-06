<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.TransformationTotalAmount" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Transformation Total Amounts</h4>

    <!-- Transformation Total Amounts Table -->
    <div class="card">
        <h5 class="card-header">Total Manufactured Amount per Transformation</h5>
        <div class="table-responsive text-nowrap">
            <table class="table">
                <thead>
                    <tr>
                        <th>Transformation ID</th>
                        <th>Date Transformation</th>
                        <th>Total Manufactured Amount</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        TransformationTotalAmount[] totals = (TransformationTotalAmount[]) request.getAttribute("totals");
                        double grandTotal = 0.0; 
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); // Change Locale as needed

                        if (totals != null && totals.length > 0) {
                            for (TransformationTotalAmount total : totals) {
                                double amount = total.getTotalManufacturedAmount();
                                grandTotal += amount; // Accumulate the total amount
                    %>
                    <tr>
                        <td><%= total.getTransformationId() %></td>
                        <td><%= total.getDateTransformation() %></td>
                        <td><%= currencyFormat.format(amount) %></td> <!-- Formatted amount -->
                    </tr>
                    <%
                            }
                    %>
                    <!-- Total Row -->
                    <tr>
                        <td colspan="2" class="text-right fw-bold">Total:</td>
                        <td><%= currencyFormat.format(grandTotal) %></td> <!-- Formatted grand total -->
                    </tr>
                    <%
                        } else {
                    %>
                    <tr>
                        <td colspan="3" class="text-center">No Transformation Totals found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <!--/ Transformation Total Amounts Table -->
</div>