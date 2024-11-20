<div class="container-xxl flex-grow-1 container-p-y">
  <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">VitaFoam</span> Matelas</h4>

    <!-- Message Display -->
    <% if (request.getAttribute("message") != null) { %>
      <div class="alert alert-info" role="alert">
        <%= request.getAttribute("message") %>
      </div>
    <% } %>
</div>