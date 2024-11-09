<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.ProductCostPrice" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Product Cost Prices</h4>

    <!-- Product Cost Price Table -->
    <div class="card">
        <h5 class="card-header">Cost Price Calculations for Each Product</h5>
        <div class="table-responsive text-nowrap">
            <table class="table">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Label</th>
                        <th>Product Selling Price</th>
                        <th>Product Cost Price</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        ProductCostPrice[] totals = (ProductCostPrice[]) request.getAttribute("totals");
                        double grandTotalCostPrice = 0.0; // Initialize a variable to accumulate the grand total cost price
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); // Adjust Locale as needed

                        if (totals != null && totals.length > 0) {
                            for (ProductCostPrice costPrice : totals) {
                                double productCost = costPrice.getProductCostPrice();
                                grandTotalCostPrice += productCost; // Accumulate the cost price
                    %>
                    <tr>
                        <td><%= costPrice.getProductId() %></td>
                        <td><%= costPrice.getProductLabel() %></td>
                        <td><%= currencyFormat.format(costPrice.getProductSellingPrice()) %></td> <!-- Formatted selling price -->
                        <td><%= currencyFormat.format(productCost) %></td> <!-- Formatted cost price -->
                    </tr>
                    <%
                            }
                    %>
                    <!-- Total Row -->
                    <tr>
                        <td colspan="5" class="text-right fw-bold">Grand Total Cost Price:</td>
                        <td><%= currencyFormat.format(grandTotalCostPrice) %></td> <!-- Formatted grand total -->
                    </tr>
                    <%
                        } else {
                    %>
                    <tr>
                        <td colspan="6" class="text-center">No Product Cost Prices found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <!--/ Product Cost Price Table -->
</div>