<nav class="sidebar sidebar-offcanvas" id="sidebar">
  <ul class="nav">
    <li class="nav-item">
      <a class="nav-link" href="../dash.jsp">
        <i class="mdi mdi-grid-large menu-icon"></i>
        <span class="menu-title">Dashboard</span>
      </a>
    </li>

    <li class="nav-item">
      <a class="nav-link" data-bs-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
        <i class="menu-icon mdi mdi-account-circle-outline"></i>
        <span class="menu-title">Accounts</span>
        <i class="menu-arrow"></i>
      </a>
      <div class="collapse" id="auth">
        <ul class="nav flex-column sub-menu">
          <li class="nav-item"> <a class="nav-link" href="../admin/mentor"> Mentor Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="#"> Mentee Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="#"> Request Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="/Frog/manager/paymentBooking"> Booking Payment</a></li>
        </ul>
      </div>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-bs-toggle="collapse" href="#auth1" aria-expanded="false" aria-controls="auth">
        <i class="menu-icon mdi mdi-auto-fix"></i>
        <span class="menu-title">Content</span>
        <i class="menu-arrow"></i>
      </a>
      <div class="collapse" id="auth1">
        <ul class="nav flex-column sub-menu">
          <li class="nav-item"> <a class="nav-link" href="#">Banner Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="../admin/skill">Skills Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="../admin/report">Report Management</a></li>
        </ul>
      </div>
    </li>
    <li class="nav-item">
      <a class="nav-link" data-bs-toggle="collapse" href="#auth2" aria-expanded="false" aria-controls="auth">
        <i class="menu-icon mdi mdi-check-circle-outline"></i>
        <span class="menu-title">Mentor CV</span>
        <i class="menu-arrow"></i>
      </a>
      <div class="collapse" id="auth2">
        <ul class="nav flex-column sub-menu">
          <li class="nav-item"> <a class="nav-link" href="#">CV Management</a></li>
        </ul>
      </div>
    </li>

  </ul>
</nav>