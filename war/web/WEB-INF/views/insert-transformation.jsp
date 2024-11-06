<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="mg.itu.model.InitialSponge" %>
<%@ page import="mg.itu.model.Product" %>
<%@ page import="java.util.List" %>

<div class="container-xxl flex-grow-1 container-p-y">
  <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Forms /</span> Insert Transformation</h4>

  <!-- Message Display -->
  <% if (request.getAttribute("message") != null) { %>
    <div class="alert alert-info" role="alert">
      <%= request.getAttribute("message") %>
    </div>
  <% } %>

  <div class="row">
    <!-- Form controls -->
    <div class="col-md-12">
      <div class="card mb-4">
        <h5 class="card-header">Transformation Details</h5>
        <div class="card-body">
          <form action="TransformationController" method="post">

            <!-- Initial Sponge Selection -->
            <div class="mb-3">
              <label for="initialSponge" class="form-label">Select Initial Sponge</label>
              <select class="form-select" id="initialSponge" name="initialSponge" required>
                <option value="" selected disabled>Choose an initial sponge...</option>
                <% 
                  InitialSponge[] sponges = (InitialSponge[]) request.getAttribute("sponges");
                  if (sponges != null) {
                    for (InitialSponge sponge : sponges) { 
                      if (sponge.getIsTransformed().equals("FALSE")) {
                %>
                  <option value="<%= sponge.getId() %>">
                    <%= "Sponge ID: " + sponge.getId() + " - Volume: " + sponge.getDimLength() + "m³" %>
                  </option>
                <% 
                      }
                    } 
                  } 
                %>
              </select>
            </div>

            <!-- Transformation Date -->
            <div class="mb-3">
              <label for="transformationDate" class="form-label">Transformation Date</label>
              <input
                type="date"
                class="form-control"
                id="transformationDate"
                name="transformationDate"
                required
              />
            </div>

            <!-- Products and Quantity Input -->
            <h5 class="mt-4">Products</h5>
            <% 
              Product[] products = (Product[]) request.getAttribute("products");
              if (products != null) {
                for (Product product : products) { 
            %>
              <div class="mb-3">
                <label for="product_<%= product.getId() %>" class="form-label"><%= product.getLabel() %> Quantity</label>
                <input
                  type="number"
                  step="0.01"
                  class="form-control"
                  id="product_<%= product.getId() %>"
                  name="productQuantity_<%= product.getId() %>"
                  placeholder="Enter quantity for <%= product.getLabel() %>"
                  required
                />
              </div>
            <% 
                } 
              } 
            %>

            <!-- Remaining Dimensions -->
            <h5 class="mt-4">Remaining Volume</h5>
            <div class="mb-3">
              <label for="remainingLength" class="form-label">Volume (m³)</label>
              <input
                type="number"
                step="0.1"
                class="form-control"
                id="remainingLength"
                name="remainingLength"
                placeholder="Enter remaining length"
                required
              />
            </div>
            <div class="mb-3">
              <label for="remainingWidth" class="form-label">Width (m)</label>
              <input
                type="hidden"
                step="0.1"
                class="form-control"
                id="remainingWidth"
                name="remainingWidth"
                placeholder="Enter remaining width"
                value="1"
                required
              />
            </div>
            <div class="mb-3">
              <label for="remainingHeight" class="form-label">Height (m)</label>
              <input
                type="hidden"
                step="0.1"
                class="form-control"
                id="remainingHeight"
                name="remainingHeight"
                placeholder="Enter remaining height"
                value="1"
                required
              />
            </div>

            <!-- Submit Button -->
            <button type="submit" class="btn btn-primary">Save Transformation</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>