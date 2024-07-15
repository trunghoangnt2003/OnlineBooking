<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
                <form action="mentee" method="get">
                    <div class="input-group rounded">
                        <input id="search" type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" name="searchname"/>
                        <span class="input-group-text border-0" id="search-addon" >
                     <button id="search-link" style="border: none; outline: none" class="search-btn" href="#">
                        <i class="fas fa-search"></i>
                    </button>
                    </span>
                        <a href="?name=" class="close" style="display: flex;justify-content: center;font-size: 30px; width: 20px; text-decoration: none">&times;</a>
                    </div>
                </form>


                <div class="table-responsive" style="border: 1px solid black;border-radius: 10px; margin-top: 15px">
                    <table class=" table table-hover"  id="user-table" style="width: 100%; border-radius: 5px">
                        <thead class="table-dark">
                        <td>STT</td>
                        <td>ID</td>
                        <td>Name</td>
                        <td>Username</td>
                        <td>Total Skills</td>
                        <td>Total Slots</td>
                        <td>Status</td>
                        </thead>
                        <tbody >
                        <c:set var="count" value="1"/>
                        <c:forEach var="entry" items="${requestScope.menteeMap}">
                            <c:set var="mentee" value="${entry.key}" />
                            <c:set var="stats" value="${entry.value}" />
                            <tr>
                                <td>${count}</td>
                                <c:set var="count" value="${count+1}"/>
                                <td>${mentee.account.id}</td>
                                <td>${mentee.account.name}</td>
                                <td>${mentee.account.userName}</td>
                                <td>${stats.get("TotalSkill")}</td>
                                <td>${stats.get("TotalSlots")}</td>
                                <td>
                                    <div style="display: flex">
                                        <c:if test="${mentee.account.status.id == 7}">
                                            <button type="button" class="btn btn-success" style="padding: 5px; margin: 5px" value="${mentee.account.id}">ACTIVE</button>
                                        </c:if>
                                        <c:if test="${mentee.account.status.id == 8}">
                                            <button type="button" class="btn btn-warning" style="padding: 5px;margin: 5px" value="${mentee.account.id}">INACTIVE</button>
                                        </c:if>
                                        <c:if test="${mentee.account.status.id == 6}">
                                            <button type="button" class="btn btn-danger" style="padding: 5px;margin: 5px" value="${mentee.account.id} ">BAN</button>
                                        </c:if>

                                    </div>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
    <!-- page-body-wrapper ends -->
</div>
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
<script src="${pageContext.request.contextPath}/view/admin/assets/js/todolist.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
    <!-- Custom js for this page-->
    // Lấy tất cả các nút có class "ban-btn"
    var banButtons = document.querySelectorAll('.btn-success');
    var inactiveButtons = document.querySelectorAll('.btn-warning');
    var unBanButtons = document.querySelectorAll('.btn-danger');
    // Lặp qua từng nút và thêm sự kiện click
    banButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            // Lấy giá trị value của nút
            var userId = this.getAttribute('value');

            swal({
                title: "Are you sure for BAN?",
                text: "This will ban the user with ID: " + userId + "!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            })
                .then((willBan) => {
                    if (willBan) {
                        // Gửi request POST tới Servlet để ban tài khoản
                        banUser(userId);
                    } else {
                        swal("The user is safe for now.");
                    }
                });
        });
    });
    unBanButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            // Lấy giá trị value của nút
            var userId = this.getAttribute('value');

            swal({
                title: "Are you sure for UNBAN?",
                text: "This will unban the user with ID: " + userId + "!",
                icon: "warning",
                buttons: true,
                dangerMode: true,
            })
                .then((willBan) => {
                    if (willBan) {
                        // Gửi request POST tới Servlet để ban tài khoản
                        unbanUser(userId);
                    } else {
                        swal("Don't unban account");
                    }
                });
        });
    });
    inactiveButtons.forEach(function (button) {
        button.addEventListener('click', function () {
            // Lấy giá trị value của nút
            var userId = this.getAttribute('value');

            swal({
                title: "Are you sure for ACTIVE?",
                text: "This will active the user with ID: " + userId + "!",
                icon: "success",
                buttons: true,
                dangerMode: false,
            })
                .then((willBan) => {
                    if (willBan) {
                        // Gửi request POST tới Servlet để ban tài khoản
                        active(userId);
                    } else {
                        swal("Don't active account");
                    }
                });

        });
    });

    function banUser(userId) {
        fetch('../admin/mentor', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'userId=' + encodeURIComponent(userId) + "&action=ban"
        })
            .then(response => {
                if (response.ok) {
                    swal("User banned successfully!").then(() => {
                        // Reload the page
                        location.reload();
                    });
                } else {
                    swal("Failed to ban user. Please try again later.");
                }
            })
            .catch(error => {
                console.error('Error banning user:', error);
                swal("An error occurred while banning the user. Please try again later.");
            });
    }

    function unbanUser(userId) {
        // Send POST request to Servlet
        fetch('../admin/mentor', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'userId=' + encodeURIComponent(userId) + '&action=unban'
        })
            .then(response => {
                if (response.ok) {
                    swal("User unbanned successfully!").then(() => {
                        // Reload the page
                        location.reload();
                    });
                } else {
                    swal("Failed to unban user. Please try again later.");
                }
            })
            .catch(error => {
                console.error('Error unbanning user:', error);
                swal("An error occurred while unbanning the user. Please try again later.");
            });
    }
    function active(userId) {
        // Send POST request to Servlet
        fetch('../admin/mentor', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded'
            },
            body: 'userId=' + encodeURIComponent(userId) + '&action=active'
        })
            .then(response => {
                if (response.ok) {
                    swal("User active successfully!").then(() => {
                        // Reload the page
                        location.reload();
                    });
                } else {
                    swal("Failed to active user. Please try again later.");
                }
            })
            .catch(error => {
                console.error('Error unbanning user:', error);
                swal("Failed to active user. Please try again later.");
            });
    }
</script>
<!-- endinject -->
<!-- Custom js for this page-->
<!-- End custom js for this page-->

<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>
