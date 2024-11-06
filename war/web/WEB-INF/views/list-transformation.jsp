<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.TransformationDetail" %>
<%@ page import="java.util.List" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Transformation Details</h4>

    <!-- Transformation Details Table -->
    <div class="card">
        <h5 class="card-header">Transformation Details</h5>
        <div class="table-responsive text-nowrap">
            <table class="table">
                <thead>
                    <tr>
                        <th>Date Transformation</th>
                        <th>Initial Purchase Price</th>
                        <th>Initial Volume</th>
                        <th>Product Label</th>
                        <th>Product Selling Price</th>
                        <th>Product Volume </th>
                        <th>Product Quantity</th>
                        <th>Remaining Initial Sponge ID</th>
                    </tr>
                </thead>
                <tbody class="table-border-bottom-0">
                    <%
                        TransformationDetail[] details = (TransformationDetail[]) request.getAttribute("details");
                        if (details != null && details.length > 0) {
                            for (TransformationDetail detail : details) {
                    %>
                    <tr>
                        <td><%= detail.getDateTransformation() %></td>
                        <td>$<%= detail.getInitialPurchasePrice() %></td>
                        <td><%= detail.getInitialLength() %>m³</td>
                        <td><%= detail.getProductLabel() %></td>
                        <td>$<%= detail.getProductSellingPrice() %></td>
                        <td><%= (detail.getProductLength() * detail.getProductWidth() * detail.getProductHeight()) %>m³</td>
                        <td><%= detail.getProductQuantity() %></td>
                        <td><%= detail.getRemainingInitialSpongeId() %></td>
                    </tr>
                    <%
                            }
                        } else {
                    %>
                    <tr>
                        <td colspan="12" class="text-center">No Transformation Details found.</td>
                    </tr>
                    <% } %>
                </tbody>
            </table>
        </div>
    </div>
    <!--/ Transformation Details Table -->
</div>