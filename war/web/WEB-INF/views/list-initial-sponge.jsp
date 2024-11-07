<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.InitialSponge" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.NumberFormat" %>
<%@ page import="java.util.Locale" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Initial Sponge List</h4>

    <!-- Basic Bootstrap Table -->
    <div class="card">
        <h5 class="card-header">Initial Sponges</h5>
        <div class="table-responsive text-nowrap">
            <table class="table">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Purchase Price</th>
                        <th>Is Transformed</th>
                        <th>Volume</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        InitialSponge[] sponges = (InitialSponge[]) request.getAttribute("sponges");
                        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.US);

                        if (sponges != null && sponges.length > 0) {
                            for (InitialSponge sponge : sponges) {
                    %>
                    <tr>
                        <td><%= sponge.getId() %></td>
                        <td><%= currencyFormat.format(sponge.getPurchasePrice()) %></td>
                        <td><%= sponge.getIsTransformed() %></td>
                        <td><%= (sponge.getDimHeight() * sponge.getDimLength() * sponge.getDimWidth()) %> m³</td>
                        <td>
                            <a href="InitialSpongeController?mode=edit&id=<%= sponge.getId() %>" 
                               class="btn btn-primary btn-sm">Edit</a>
                        </td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="5" class="text-center">No Initial Sponges found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>