<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="sidebar sidebar-offcanvas" id="sidebar">
  <ul class="nav">
    <c:if test="${sessionScope.account.role.id == 3}">
      <li class="nav-item">
        <a class="nav-link" href="../admin/dash">
          <i class="mdi mdi-grid-large menu-icon"></i>
          <span class="menu-title">Dashboard</span>
        </a>
      </li>
    </c:if>

    <li class="nav-item">
      <a class="nav-link" data-bs-toggle="collapse" href="#auth" aria-expanded="false" aria-controls="auth">
        <i class="menu-icon mdi mdi-account-circle-outline"></i>
        <span class="menu-title">Accounts</span>
        <i class="menu-arrow"></i>
      </a>
      <div class="collapse" id="auth">
        <ul class="nav flex-column sub-menu">
          <li class="nav-item"> <a class="nav-link" href="../admin/mentor"> Mentor Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="../admin/mentee"> Mentee Management</a></li>
          <c:if test="${sessionScope.account.role.id == 3}">

            <li class="nav-item"> <a class="nav-link" href="../admin/manageManager"> Manager Management</a></li>
          </c:if>
          <li class="nav-item"> <a class="nav-link" href="../manager/request"> Request Management</a></li>

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

          <li class="nav-item"> <a class="nav-link" href="../admin/skill">Skills Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="../admin/report">Report Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="../manager/paymentBooking">Payment Management</a></li>
          <li class="nav-item"> <a class="nav-link" href="../admin/manageSchedule">Schedule Management</a></li>
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
          <li class="nav-item"> <a class="nav-link" href="../admin/manageCV">CV Management</a></li>
        </ul>
      </div>
    </li>

  </ul>
</nav>