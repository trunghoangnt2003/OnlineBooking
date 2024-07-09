<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 5/29/2024
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <title>Admin </title>
  <!-- plugins:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/feather/feather.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/mdi/css/materialdesignicons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/ti-icons/css/themify-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/font-awesome/css/font-awesome.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/typicons/typicons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/simple-line-icons/css/simple-line-icons.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/css/vendor.bundle.base.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css">
  <!-- endinject -->
  <!-- Plugin css for this page -->
  <!-- End plugin css for this page -->
  <!-- inject:css -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/css/style.css">
  <!-- endinject -->
  <link rel="shortcut icon" href="${pageContext.request.contextPath}/view/admin/assets/images/favicon.png" />
  <link rel="stylesheet"
        href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
  <!-- Font Awesome -->
  <!-- Google Fonts -->
  <link
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
          rel="stylesheet"
  />
  <!-- MDB -->

  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
  <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
  <!-- Fontawesome CDN Link For Icons -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
</head>
<body>
<div class="container-scroller">
  <!-- partial:partials/_navbar.jsp -->
  <jsp:include page="partials/_navbar.jsp"></jsp:include>
  <!-- partial -->
  <div class="container-fluid page-body-wrapper">
    <!-- partial:partials/_sidebar.jsp -->
    <jsp:include page="partials/_sidebar.jsp"></jsp:include>
    <!-- partial -->
    <div class="main-panel">
      <div class="content-wrapper">
        <div class="tab-content tab-content-basic">
          <div class="tab-pane fade show active" id="overview" role="tabpanel" aria-labelledby="overview">
            <div class="row">
              <div class="col-sm-12">
                <div class="statistics-details d-flex align-items-center justify-content-start">
                  <div>
                    <p class="statistics-title">Revenue</p>
                    <h3 class="rate-percentage">${requestScope.total}</h3>
                  </div>
                  <div class="d-none d-md-block" style="margin-left: 20px">
                    <p class="statistics-title">Percentage Booking</p>
                    <h3 class="rate-percentage">5%</h3>

                  </div>
                </div>
              </div>
            </div>
            <div class="row">
              <div class="col-lg-8 d-flex flex-column">
                <div class="row flex-grow">
                  <div class="col-12 col-lg-4 col-lg-12 grid-margin stretch-card">
                    <div class="card card-rounded">
                      <div class="card-body">
                        <div class="d-sm-flex justify-content-between align-items-start">
                          <div>
                            <h4 class="card-title card-title-dash">Expected Revenue Line Chart</h4>
                            <h5 class="card-subtitle card-subtitle-dash">Revenue is calculated as 5% of the total payments.</h5>
                          </div>
                          <div id="performanceLine-legend"></div>
                        </div>
                        <div class="chartjs-wrapper mt-4">
                          <canvas id="performanceLine" style="height: 90%"></canvas>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row flex-grow">
                  <div class="col-12 grid-margin stretch-card">
                    <div class="card card-rounded">
                      <div class="card-body">
                        <div class="row">
                          <div class="col-lg-12">

                              <div>
                                <h4 class="card-title card-title-dash">Favored Skill  (Based on the number of bookings)</h4>
                              </div>


                            <div class="mt-3">
                              <canvas id="leaveReport" style="height: 100%"></canvas>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
              <div class="col-lg-4 d-flex flex-column">
                <div class="row flex-grow">
                  <div class="col-12 grid-margin stretch-card">
                    <div class="card card-rounded">
                      <div class="card-body">
                        <div class="row">
                          <div class="col-lg-12">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                              <h4 class="card-title card-title-dash">Booking Status</h4>
                            </div>
                            <div>
                              <canvas class="my-auto" id="doughnutChart"></canvas>
                            </div>
                            <div id="doughnutChart-legend" class="mt-5 text-center"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="row flex-grow">
                  <div class="col-12 grid-margin stretch-card">
                    <div class="card card-rounded">
                      <div class="card-body">
                        <div class="row">
                          <div class="col-lg-12">
                            <div class="d-flex justify-content-between align-items-center mb-3">
                              <h4 class="card-title card-title-dash">Type of Account</h4>
                            </div>
                            <div>
                              <canvas class="my-auto" id="doughnutChartPurple"></canvas>
                            </div>
                            <div id="doughnutChartPurple-legend" class="text-center mt-5"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>

              </div>
            </div>

          </div>


        </div>
      </div>
    </div>
    <!-- page-body-wrapper ends -->
  </div>
  <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script>

</script>
  <!-- container-scroller -->
  <!-- plugins:js -->
  <script src="${pageContext.request.contextPath}/view/admin/assets/vendors/js/vendor.bundle.base.js"></script>
  <script src="${pageContext.request.contextPath}/view/admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
  <!-- endinject -->
  <!-- Plugin js for this page -->
  <!-- End plugin js for this page -->
  <!-- inject:js -->
  <script src="${pageContext.request.contextPath}/view/admin/assets/js/off-canvas.js"></script>
  <script src="${pageContext.request.contextPath}/view/admin/assets/js/template.js"></script>
  <script src="${pageContext.request.contextPath}/view/admin/assets/js/settings.js"></script>
  <script src="${pageContext.request.contextPath}/view/admin/assets/js/hoverable-collapse.js"></script>
  <script src="${pageContext.request.contextPath}/view/admin/assets/js/dashboard.js"></script>
  <!-- endinject -->
  <!-- Custom js for this page-->
  <!-- End custom js for this page-->

  <%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
  <%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
  <%--        crossorigin="anonymous"></script>--%>
  <%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>
