<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.MachinePriceResult" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>

<%
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US); 
    String yearParam = request.getParameter("year"); // Retrieve the year parameter
    int selectedYear = (yearParam != null) ? Integer.parseInt(yearParam) : 0; // Default to 0 (All)
%>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Machine Price Results</h4>

    <!-- Year Filter -->
    <div class="mb-3">
        <label for="yearFilter" class="form-label">Filter by Year:</label>
        <form method="get" action="<%= request.getContextPath() %>/controller/MachineController">
            <select name="year" class="form-select" onchange="this.form.submit()">
                <option value="0" <%= (selectedYear == 0) ? "selected" : "" %>>All</option>
                <option value="2024" <%= (selectedYear == 2024) ? "selected" : "" %>>2024</option>
                <option value="2023" <%= (selectedYear == 2023) ? "selected" : "" %>>2023</option>
                <option value="2022" <%= (selectedYear == 2022) ? "selected" : "" %>>2022</option>
            </select>
        </form>
    </div>

    <!-- Machine Price Results Table -->
    <div class="card mb-4">
        <h5 class="card-header">Machine Price Overview</h5>
        <div class="table-responsive text-nowrap">
            <table class="table table-striped table-bordered table-hover">
                <thead>
                    <tr>
                        <th>Machine ID</th>
                        <th>Total Practical Price</th>
                        <th>Total Theoretical Price</th>
                        <th>Manufactured Sponge</th>
                        <th>Price Difference (Ecart)</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0" id="resultTable">
                    <%
                        List<MachinePriceResult> results = (List<MachinePriceResult>) request.getAttribute("result");
                        if (results != null && !results.isEmpty()) {
                            for (MachinePriceResult machine : results) {
                                double totalPratiquePrice = machine.getTotalPratiquePrice();
                                double totalTheoriquePrice = machine.getTotalTheoriquePrice();
                                double ecart = machine.getEcart();
                    %>
                    <tr>
                        <td class="px-3"><%= machine.getIdMachine() %></td>
                        <td class="px-3"><%= currencyFormat.format(totalPratiquePrice) %></td>
                        <td class="px-3"><%= currencyFormat.format(totalTheoriquePrice) %></td>
                        <td class="px-3"><%= machine.getCount() %></td>
                        <td class="px-3"><%= currencyFormat.format(ecart) %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="4" class="text-center">No Machine Price Results found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>