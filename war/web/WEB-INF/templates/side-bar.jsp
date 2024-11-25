<ul class="menu-inner py-1">
  <!-- Dashboard -->
  <li class="menu-item active">
    <a href="MainController" class="menu-link">
      <i class="menu-icon tf-icons bx bx-home"></i>
      <div data-i18n="Analytics">Dashboard</div>
    </a>
  </li>

  <li class="menu-header small text-uppercase">
    <span class="menu-header-text">Sujet I</span>
  </li>

  <!-- Initial Sponge -->
  <li class="menu-item">
    <a href="javascript:void(0);" class="menu-link menu-toggle">
      <i class="menu-icon tf-icons bx bx-droplet"></i>
      <div data-i18n="Layouts">Initial Sponge</div>
    </a>
    <ul class="menu-sub">
      <li class="menu-item">
        <a href="InitialSpongeController?mode=create" class="menu-link">
          <div data-i18n="Without menu">Create</div>
        </a>
      </li>
      <li class="menu-item">
        <a href="InitialSpongeController" class="menu-link">
          <div data-i18n="Without navbar">List</div>
        </a>
      </li>
    </ul>
  </li>

  <!-- Transformation -->
  <li class="menu-item">
    <a href="javascript:void(0);" class="menu-link menu-toggle">
      <i class="menu-icon tf-icons bx bx-cog"></i>
      <div data-i18n="Layouts">Transformation</div>
    </a>
    <ul class="menu-sub">
      <li class="menu-item">
        <a href="TransformationController?mode=create" class="menu-link">
          <div data-i18n="Without menu">Create</div>
        </a>
      </li>
      <li class="menu-item">
        <a href="TransformationController" class="menu-link">
          <div data-i18n="Without navbar">List</div>
        </a>
      </li>
    </ul>
  </li>

  <!-- Stock Status -->
  <li class="menu-item">
    <a href="javascript:void(0);" class="menu-link menu-toggle">
      <i class="menu-icon tf-icons bx bx-archive"></i>
      <div data-i18n="Layouts">Stock Status</div>
    </a>
    <ul class="menu-sub">
      <li class="menu-item">
        <a href="StockStatusController" class="menu-link">
          <div data-i18n="Without menu">List</div>
        </a>
      </li>
    </ul>
  </li>

  <li class="menu-header small text-uppercase">
    <span class="menu-header-text">Sujet II</span>
  </li>

  <li class="menu-item">
    <a href="javascript:void(0);" class="menu-link menu-toggle">
      <i class="menu-icon tf-icons bx bx-data"></i>
      <div data-i18n="Layouts">Machine Fabrication</div>
    </a>

    <ul class="menu-sub">
      <li class="menu-item">
        <a href="MachineController" class="menu-link">
          <div data-i18n="Without menu">Machine result</div>
        </a>
      </li>
    </ul>
  </li>


  <li class="menu-item"> 
    <a href="javascript:void(0);" class="menu-link menu-toggle">
      <i class="menu-icon tf-icons bx bx-file"></i> <!-- Updated icon -->
      <div data-i18n="Layouts">CSV Data</div>
    </a>
  
    <ul class="menu-sub">
      <li class="menu-item">
        <a href="ImportController?mode=import_csv" class="menu-link">
          <div data-i18n="Without menu">Import CSV data</div>
        </a>
      </li>
  
      <li class="menu-item">
        <a href="ImportController?mode=generate_csv" class="menu-link">
          <div data-i18n="Without menu">Generate CSV data</div>
        </a>
      </li>
    </ul>
  </li>  
</ul>  