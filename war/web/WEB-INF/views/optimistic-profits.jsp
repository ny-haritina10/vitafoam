<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.OptimisticProfits" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Optimistic Profits</h4>

    <!-- Optimistic Profits Table -->
    <div class="card">
        <h5 class="card-header">Optimistic Profits for Each Initial Sponge and Product</h5>
        <div class="table-responsive text-nowrap">
            <table class="table">
                <thead>
                    <tr>
                        <th>Initial Sponge Purchase Price</th>
                        <th>Initial Sponge Volume</th>
                        <th>Optimistic Profit</th>
                        <th>Product Label</th>
                        <th>Product Price</th>
                        <th>Product Volume</th>
                        <th>Possible Quantity</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        OptimisticProfits[] totals = (OptimisticProfits[]) request.getAttribute("totals");
                        double grandTotalProfit = 0.0; // Initialize a variable to accumulate the grand total profit
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); // Change Locale as needed

                        if (totals != null && totals.length > 0) {
                            for (OptimisticProfits profit : totals) {
                                double optimisticProfit = profit.getOptimisticProfit();
                                grandTotalProfit += optimisticProfit; // Accumulate the profit
                    %>
                    <tr>
                        <td><%= currencyFormat.format(profit.getPurchasePrice()) %></td> <!-- Formatted price -->
                        <td><%= profit.getInitialVolume() %> m³</td>
                        <td><%= currencyFormat.format(optimisticProfit) %></td> <!-- Formatted profit -->
                        <td><%= profit.getProductLabel() %></td>
                        <td><%= currencyFormat.format(profit.getProductPrice()) %></td> <!-- Formatted price -->
                        <td><%= profit.getProductVolume() %> m³</td>
                        <td><%= profit.getPossibleQuantity() %> unit(s)</td>
                    </tr>
                    <%
                            }
                    %>
                    <!-- Total Row -->
                    <tr>
                        <td colspan="2" class="text-right fw-bold">Grand Total Profit:</td>
                        <td><%= currencyFormat.format(grandTotalProfit) %></td> <!-- Formatted grand total -->
                    </tr>
                    <%
                        } else {
                    %>
                    <tr>
                        <td colspan="2" class="text-center">No Optimistic Profits found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <!--/ Optimistic Profits Table -->
</div>