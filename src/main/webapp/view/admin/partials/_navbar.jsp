<%
  String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
          + request.getContextPath();
%>
<nav class="navbar default-layout col-lg-12 col-12 p-0 fixed-top d-flex align-items-top flex-row">
  <div class="text-center navbar-brand-wrapper d-flex align-items-center justify-content-start">
    <div class="me-3">
      <button class="navbar-toggler navbar-toggler align-self-center" type="button" data-bs-toggle="minimize">
        <span class="icon-menu"></span>
      </button>
    </div>
    <div>
      <a class="navbar-brand brand-logo" href="<%=url%>/Home">
        <span > ADMIN </span>
        <img style="margin-bottom: 10px" src="${pageContext.request.contextPath}/view/admin/assets/images/Logo.png" alt="logo" />
      </a>
      <a class="navbar-brand brand-logo-mini" href="">
        <img style="margin-bottom: 10px" src="${pageContext.request.contextPath}/view/admin/assets/images/Logo.png" alt="logo" />
      </a>
    </div>
  </div>
  <div class="navbar-menu-wrapper d-flex align-items-top">
    <ul class="navbar-nav">
      <li class="nav-item fw-semibold d-none d-lg-block ms-0">
        <h1 class="welcome-text">Good Morning, <span class="text-black fw-bold">John Doe</span></h1>
        <h3 class="welcome-sub-text">Your performance summary this week </h3>
      </li>
    </ul>
    <ul class="navbar-nav ms-auto">
      <li class="nav-item dropdown d-none d-lg-block user-dropdown">
        <a class="nav-link" id="UserDropdown" href="#" data-bs-toggle="dropdown" aria-expanded="false">
          <img class="img-xs rounded-circle" src="https://www.logolynx.com/images/logolynx/4b/4beebce89d681837ba2f4105ce43afac.png" alt="Profile image">
        </a>
        <div class="dropdown-menu dropdown-menu-right navbar-dropdown" aria-labelledby="UserDropdown">
          <div class="dropdown-header text-center">

            <p class="mb-1 mt-3 fw-semibold">${sessionScope.account.userName}</p>
            <p class="fw-light text-muted mb-0">${sessionScope.account.email}</p>
          </div>
          <a href="<%=url%>/Home" class="dropdown-item"><i class="dropdown-item-icon mdi mdi-home text-primary me-2"></i> Back To Frog</a>
          <a href="<%=url%>/logout" class="dropdown-item"><i class="dropdown-item-icon mdi mdi-power text-primary me-2"></i>Sign Out</a>
        </div>
      </li>
    </ul>
    <button class="navbar-toggler navbar-toggler-right d-lg-none align-self-center" type="button" data-bs-toggle="offcanvas">
      <span class="mdi mdi-menu"></span>
    </button>
  </div>
</nav>