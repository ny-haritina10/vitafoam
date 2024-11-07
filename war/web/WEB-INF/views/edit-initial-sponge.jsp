<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.InitialSponge" %>

<div class="container-xxl flex-grow-1 container-p-y">
    <h4 class="fw-bold py-3 mb-4">Edit Initial Sponge</h4>

    <div class="row">
        <div class="col-xl">
            <div class="card mb-4">
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h5 class="mb-0">Edit Sponge Details</h5>
                </div>
                <div class="card-body">
                    <% 
                        InitialSponge sponge = (InitialSponge) request.getAttribute("sponge");
                        String message = (String) request.getAttribute("message");
                        if (message != null) {
                    %>
                        <div class="alert alert-info"><%= message %></div>
                    <% } %>
                    
                    <form method="post" action="InitialSpongeController">
                        <input type="hidden" name="mode" value="update">
                        <input type="hidden" name="id" value="<%= sponge.getId() %>">
                        
                        <div class="mb-3">
                            <label class="form-label" for="purchasePrice">Purchase Price</label>
                            <input type="number" step="0.01" class="form-control" id="purchasePrice" 
                                   name="purchasePrice" value="<%= sponge.getPurchasePrice() %>" required />
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label" for="isTransformed">Is Transformed</label>
                            <select class="form-select" id="isTransformed" name="isTransformed" required>
                                <option value="TRUE" <%= "TRUE".equals(sponge.getIsTransformed()) ? "selected" : "" %>>TRUE</option>
                                <option value="FALSE" <%= "FALSE".equals(sponge.getIsTransformed()) ? "selected" : "" %>>FALSE</option>
                            </select>
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label" for="dimLength">Length (m)</label>
                            <input type="number" step="0.01" class="form-control" id="dimLength" 
                                   name="dimLength" value="<%= sponge.getDimLength() %>" required />
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label" for="dimWidth">Width (m)</label>
                            <input type="number" step="0.01" class="form-control" id="dimWidth" 
                                   name="dimWidth" value="<%= sponge.getDimWidth() %>" required />
                        </div>
                        
                        <div class="mb-3">
                            <label class="form-label" for="dimHeight">Height (m)</label>
                            <input type="number" step="0.01" class="form-control" id="dimHeight" 
                                   name="dimHeight" value="<%= sponge.getDimHeight() %>" required />
                        </div>
                        
                        <button type="submit" class="btn btn-primary">Update Sponge</button>
                        <a href="InitialSpongeController" class="btn btn-secondary">Cancel</a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>