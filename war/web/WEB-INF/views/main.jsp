<div class="container-xxl flex-grow-1 container-p-y">
  <h4 class="fw-bold py-3 mb-4"><span class="text-muted fw-light">VitaFoam</span> Matelas</h4>

  <% String messageType = (String) request.getAttribute("messageType"); %>
    <!-- Message Display -->
    <% if (request.getAttribute("message") != null) { %>
      <div class="alert alert-<%= messageType != null ? messageType : "info" %> alert-dismissible fade show" role="alert">        
        <%= request.getAttribute("message") %>
      </div>
    <% } %>
</div>