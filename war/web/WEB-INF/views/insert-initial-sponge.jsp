<div class="container-xxl flex-grow-1 container-p-y">
  <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">Forms /</span> Insert Initial Sponge</h4>

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
        <h5 class="card-header">Initial Sponge Details</h5>
        <div class="card-body">
          <form action="InitialSpongeController" method="post">
            
            <!-- Purchase Price -->
            <div class="mb-3">
              <label for="purchasePrice" class="form-label">Purchase Price</label>
              <input
                type="number"
                step="0.01"
                class="form-control"
                id="purchasePrice"
                name="purchasePrice"
                placeholder="Enter purchase price"
                required
              />
            </div>
            
            <!-- Is Transformed -->
            <div class="mb-3">
              <label for="isTransformed" class="form-label">Is Transformed</label>
              <select class="form-select" id="isTransformed" name="isTransformed" required>
                <option value="" selected disabled>Choose...</option>
                <option value="TRUE">Yes</option>
                <option value="FALSE">No</option>
              </select>
            </div>
            
            <!-- VOLUME -->
            <div class="mb-3">
              <label for="dimLength" class="form-label">Volume (m³)</label>
              <input
                type="number"
                step="0.1"
                class="form-control"
                id="dimLength"
                name="dimLength"
                placeholder="Enter length"
                required
              />
            </div>

            <!-- HIDDEN WITH VALUE 1 -->
            <div class="mb-3">
              <!-- <label for="dimWidth" class="form-label">Width (m)</label> -->
              <input
                type="hidden"
                step="0.1"
                class="form-control"
                id="dimWidth"
                name="dimWidth"
                placeholder="Enter width"
                value="1"
                required
              />
            </div>
            <!-- HIDDEN WITH VALUE 1 -->
            <div class="mb-3">
              <!-- <label for="dimHeight" class="form-label">Height (m)</label> -->
              <input
                type="hidden"
                step="0.1"
                class="form-control"
                id="dimHeight"
                name="dimHeight"
                placeholder="Enter height"
                value="1"
                required
              />
            </div>
            
            <!-- Submit Button -->
            <button type="submit" class="btn btn-primary">Save Initial Sponge</button>
          </form>
        </div>
      </div>
    </div>
  </div>
</div>