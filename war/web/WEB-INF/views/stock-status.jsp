<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.TransformationTotalAmount" %>
<%@ page import="mg.itu.model.MinimalisticProfits" %>
<%@ page import="mg.itu.model.OptimisticProfits" %>
<%@ page import="mg.itu.model.ProductCostPrice" %>
<%@ page import="mg.itu.model.ProductCostPricePonderee" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<%
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); 
%>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Stock Status Overview</h4>

    <!-- Transformation Total Amount Table -->
    <div class="card mb-4">
        <h5 class="card-header">Total Manufactured Amount per Transformation</h5>
        <div class="table-responsive text-nowrap">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Transformation ID</th>
                        <th>Date Transformation</th>
                        <th>Total Manufactured Amount</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        TransformationTotalAmount[] totalsI = (TransformationTotalAmount[]) request.getAttribute("total-amount");
                        double grandTotalI = 0.0; 

                        if (totalsI != null && totalsI.length > 0) {
                            for (TransformationTotalAmount total : totalsI) {
                                double amount = total.getTotalManufacturedAmount();
                                grandTotalI += amount;
                    %>
                    <tr>
                        <td class="px-3"><%= total.getTransformationId() %></td>
                        <td class="px-3"><%= total.getDateTransformation() %></td>
                        <td class="px-3"><%= currencyFormat.format(amount) %></td>
                    </tr>
                    <%
                            }
                    %>
                    <tr>
                        <td colspan="2" class="text-end fw-bold">Total:</td>
                        <td class="px-3"><%= currencyFormat.format(grandTotalI) %></td>
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

    <!-- Minimalistic Profits Table -->
    <div class="card mb-4">
        <h5 class="card-header">Minimalistic Profits for Each Initial Sponge and Product</h5>
        <div class="table-responsive text-nowrap">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Initial Sponge Purchase Price</th>
                        <th>Initial Sponge Volume</th>
                        <th>Minimalistic Profit</th>
                        <th>Product Label</th>
                        <th>Product Price</th>
                        <th>Product Volume</th>
                        <th>Possible Quantity</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        MinimalisticProfits[] totalsII = (MinimalisticProfits[]) request.getAttribute("minimim-profit");
                        double grandTotalProfitII = 0.0;

                        if (totalsII != null && totalsII.length > 0) {
                            for (MinimalisticProfits profit : totalsII) {
                                double minimalisticProfit = profit.getMinimalisticProfit();
                                grandTotalProfitII += minimalisticProfit;
                    %>
                    <tr>
                        <td class="px-3"><%= currencyFormat.format(profit.getPurchasePrice()) %></td>
                        <td class="px-3"><%= profit.getInitialVolume() %> m³</td>
                        <td class="px-3"><%= currencyFormat.format(minimalisticProfit) %></td>
                        <td class="px-3"><%= profit.getProductLabel() %></td>
                        <td class="px-3"><%= currencyFormat.format(profit.getProductPrice()) %></td>
                        <td class="px-3"><%= profit.getProductVolume() %> m³</td>
                        <td class="px-3"><%= profit.getPossibleQuantity() %> unit(s)</td>
                    </tr>
                    <%
                            }
                    %>
                    <tr>
                        <td colspan="2" class="text-end fw-bold">Grand Total Profit:</td>
                        <td class="px-3"><%= currencyFormat.format(grandTotalProfitII) %></td>
                    </tr>
                    <%
                        } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center">No Minimalistic Profits found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Optimistic Profits Table -->
    <div class="card mb-4">
        <h5 class="card-header">Optimistic Profits for Each Initial Sponge and Product</h5>
        <div class="table-responsive text-nowrap">
            <table class="table table-striped table-bordered table-hover">
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
                        OptimisticProfits[] totalsIII = (OptimisticProfits[]) request.getAttribute("optimistic-profit");
                        double grandTotalProfitIII = 0.0;

                        if (totalsIII != null && totalsIII.length > 0) {
                            for (OptimisticProfits profit : totalsIII) {
                                double optimisticProfit = profit.getOptimisticProfit();
                                grandTotalProfitIII += optimisticProfit;
                    %>
                    <tr>
                        <td class="px-3"><%= currencyFormat.format(profit.getPurchasePrice()) %></td>
                        <td class="px-3"><%= profit.getInitialVolume() %> m³</td>
                        <td class="px-3"><%= currencyFormat.format(optimisticProfit) %></td>
                        <td class="px-3"><%= profit.getProductLabel() %></td>
                        <td class="px-3"><%= currencyFormat.format(profit.getProductPrice()) %></td>
                        <td class="px-3"><%= profit.getProductVolume() %> m³</td>
                        <td class="px-3"><%= profit.getPossibleQuantity() %> unit(s)</td>
                    </tr>
                    <%
                            }
                    %>
                    <tr>
                        <td colspan="2" class="text-end fw-bold">Grand Total Profit:</td>
                        <td class="px-3"><%= currencyFormat.format(grandTotalProfitIII) %></td>
                    </tr>
                    <%
                        } else {
                    %>
                    <tr>
                        <td colspan="7" class="text-center">No Optimistic Profits found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Product Cost Price Table -->
    <div class="card mb-4">
        <h5 class="card-header">Cost Price Calculations for Each Product</h5>
        <div class="table-responsive text-nowrap">
            <table class="table table-striped table-bordered table-hover">
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
                        ProductCostPrice[] totalsIV = (ProductCostPrice[]) request.getAttribute("cost-price");
                        double grandTotalCostPrice = 0.0;

                        if (totalsIV != null && totalsIV.length > 0) {
                            for (ProductCostPrice costPrice : totalsIV) {
                                double productCost = costPrice.getProductCostPrice();
                                grandTotalCostPrice += productCost;
                    %>
                    <tr>
                        <td class="px-3"><%= costPrice.getProductId() %></td>
                        <td class="px-3"><%= costPrice.getProductLabel() %></td>
                        <td class="px-3"><%= currencyFormat.format(costPrice.getProductSellingPrice()) %></td>
                        <td class="px-3"><%= currencyFormat.format(productCost) %></td>
                    </tr>
                    <%
                            }
                    %>
                    <tr>
                        <td colspan="3" class="text-end fw-bold">Grand Total Cost Price:</td>
                        <td class="px-3"><%= currencyFormat.format(grandTotalCostPrice) %></td>
                    </tr>
                    <%
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">No Product Cost Prices found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>

    <!-- Product Cost Price Ponderee Table -->
    <div class="card mb-4">
        <h5 class="card-header">Ponderee Cost Price for Each Product</h5>
        <div class="table-responsive text-nowrap">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Product ID</th>
                        <th>Product Cost Price Ponderee</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        ProductCostPricePonderee[] totalsV = (ProductCostPricePonderee[]) request.getAttribute("cost-price-ponderee");
                        double grandTotalPondereeCost = 0.0;

                        if (totalsV != null && totalsV.length > 0) {
                            for (ProductCostPricePonderee costPonderee : totalsV) {
                                double pondereeCost = costPonderee.getProductCostPrice();
                                grandTotalPondereeCost += pondereeCost;
                    %>
                    <tr>
                        <td><%= costPonderee.getProductId() %></td>
                        <td><%= currencyFormat.format(pondereeCost) %></td>
                    </tr>
                    <%
                            }
                    %>
                    <tr>
                        <td class="text-right fw-bold">Grand Total Ponderee Cost:</td>
                        <td><%= currencyFormat.format(grandTotalPondereeCost) %></td>
                    </tr>
                    <%
                        } else {
                    %>
                    <tr>
                        <td colspan="2" class="text-center">No Ponderee Cost Prices found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>