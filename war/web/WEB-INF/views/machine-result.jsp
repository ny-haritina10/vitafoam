<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.MachinePriceResult" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>

<%
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); 
%>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Machine Price Results</h4>

    <!-- Machine Price Results Table -->
    <div class="card mb-4">
        <h5 class="card-header">Machine Price Overview</h5>
        <div class="table-responsive text-nowrap">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Machine ID</th>
                        <th>Machine Name</th>
                        <th>Total Practical Price</th>
                        <th>Total Theoretical Price</th>
                        <th>Price Difference (Ecart)</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        // Retrieve the list of machine results from the request attribute
                        List<MachinePriceResult> results = (List<MachinePriceResult>) request.getAttribute("result");
                        if (results != null && !results.isEmpty()) {
                            for (MachinePriceResult machine : results) {
                                double totalPratiquePrice = machine.getTotalPratiquePrice();
                                double totalTheoriquePrice = machine.getTotalTheoriquePrice();
                                double ecart = machine.getEcart();
                    %>
                    <tr>
                        <td class="px-3"><%= machine.getIdMachine() %></td>
                        <td class="px-3"><%= machine.getMachineName() %></td>
                        <td class="px-3"><%= currencyFormat.format(totalPratiquePrice) %></td>
                        <td class="px-3"><%= currencyFormat.format(totalTheoriquePrice) %></td>
                        <td class="px-3"><%= currencyFormat.format(ecart) %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">No Machine Price Results found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>