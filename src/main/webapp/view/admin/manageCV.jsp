<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 6/22/2024
  Time: 11:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Manage CV</title>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/admin/manageCV.css">
</head>
<body>
<div class="container-scroller">
    <!-- partial:partials/_navbar.jsp -->
    <jsp:include page="partials/_navbar.jsp"></jsp:include>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial:partials/_sidebar.jsp -->
        <jsp:include page="partials/_sidebar.jsp"></jsp:include>
        <div class="main-panel">
            <div class="content-wrapper">
                <div class="input-group rounded">
                    <input id="search" type="search" class="form-control rounded" placeholder="Search" aria-label="Search" aria-describedby="search-addon" onkeyup="updateSearchLink()" value="${requestScope.name}"/>
                    <span class="input-group-text border-0" id="search-addon" >
                     <a id="search-link" class="search-btn" href="#">
                        <i class="fas fa-search"></i>
                    </a>
                    </span>
                    <a href="?name=" class="close" style="text-align: end; width: 20px; text-decoration: none">&times;</a>
                </div>

                <div class="table-responsive" style="border: 1px solid black;border-radius: 10px; margin-top: 15px">
                    <table class=" table table-hover"  id="user-table" style="width: 100%; border-radius: 5px">
                        <thead class="table-dark">
                        <td>STT</td>
                        <td>ID</td>
                        <td>Full Name</td>
                        <td>Account Name</td>
                        <td>CV</td>
                        <td>Active</td>
                        </thead>
                        <tbody >
                        <c:set var="count" value="1"/>
                        <c:forEach items="${requestScope.mentors}" var="m" varStatus="loop">
                            <tr>
                                <td>${count}</td>
                                <c:set var="count" value="${count+1}"/>
                                <td>${m.account.id}</td>
                                <td>${m.account.name}</td>
                                <td>${m.account.userName}</td>
                                <td style="justify-items: center;">
                                    <button id="myBtn${loop.index}" class="open-modal btn btn-info" style="padding: 5px; justify-content: center">
                                        <i class="menu-icon mdi mdi-account-box"></i>
                                    </button>
                                    <div id="myModal${loop.index}" class="modal">
                                        <div class="modal-content">
                                            <span class="close" style="text-align: end">&times;</span>
                                            <h4>
                                                CV of ${m.account.name}
                                            </h4>
                                        </div>
                                    </div>
                                </td>
                                <td></td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
                <br>
                <div style="display: flex; justify-content: space-between">
                    <div class="justify-content-start" style=""> Total ${requestScope.totalMentor} result </div>
                </div>
            </div>
        </div>
    </div>

</div>




<script src="${pageContext.request.contextPath}/view/admin/assets/vendors/js/vendor.bundle.base.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/off-canvas.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/template.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/settings.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/hoverable-collapse.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/todolist.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>


    function updateSearchLink() {
        var searchInput = document.getElementById('search');
        var searchLink = document.getElementById('search-link');
        searchLink.href = '?name=' + encodeURIComponent(searchInput.value);
    }

    document.querySelector('.search-btn').addEventListener('click', function () {
        this.parentElement.classList.toggle('open')
        this.previousElementSibling.focus()
    })
    // Get all the buttons that open the modal
    var btns = document.getElementsByClassName("open-modal");

    // Get all the modals
    var modals = document.getElementsByClassName("modal");

    // Get all the close buttons
    var closeButtons = document.getElementsByClassName("close");

    // Loop through all the buttons and add a click event listener
    for (var i = 0; i < btns.length; i++) {
        btns[i].onclick = function () {
            var modal = document.getElementById("myModal" + this.id.slice(-1));
            modal.style.display = "block";
        }
    }

    // Loop through all the close buttons and add a click event listener
    for (var i = 0; i < closeButtons.length; i++) {
        closeButtons[i].onclick = function () {
            var modal = this.closest(".modal");
            modal.style.display = "none";
        }
    }

    // When the user clicks anywhere outside of the modal, close it
    window.onclick = function (event) {
        for (var i = 0; i < modals.length; i++) {
            if (event.target == modals[i]) {
                modals[i].style.display = "none";
            }
        }
    }
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
    function updateUserTable() {
        location.reload();
    }


</script>
</body>
</html>
