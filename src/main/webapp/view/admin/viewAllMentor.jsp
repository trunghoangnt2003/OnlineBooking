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
    <style>
        /*body{*/
        /*    margin: 0;*/
        /*    padding: 0;*/
        /*    background-color: #b2e0df;*/
        /*    height: 100vh;*/
        /*    display: flex;*/
        /*    justify-content: center;*/
        /*    align-items: center;*/
        /*}*/


        .checked {
            color: orange;
        }
        .search-container{
            background: #fff;
            height: 30px;
            border-radius: 30px;
            padding: 10px 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            transition: 0.8s;
            /*box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
            inset -7px -7px 10px 0px rgba(0,0,0,.1),
           7px 7px 20px 0px rgba(0,0,0,.1),
           4px 4px 5px 0px rgba(0,0,0,.1);
           text-shadow:  0px 0px 6px rgba(255,255,255,.3),
                      -4px -4px 6px rgba(116, 125, 136, .2);
          text-shadow: 2px 2px 3px rgba(255,255,255,0.5);*/
            box-shadow:  4px 4px 6px 0 rgba(255,255,255,.3),
            -4px -4px 6px 0 rgba(116, 125, 136, .2),
            inset -4px -4px 6px 0 rgba(255,255,255,.2),
            inset 4px 4px 6px 0 rgba(0, 0, 0, .2);
        }

        .search-container:hover > .search-input{
            width: 400px;
        }

        .search-container .search-input{
            background: transparent;
            border: none;
            outline:none;
            width: 0px;
            font-weight: 500;
            font-size: 16px;
            transition: 0.8s;

        }

        .search-container .search-btn .fas{
            color: #5cbdbb;
        }

        /*@keyframes hoverShake {*/
        /*    0% {transform: skew(0deg,0deg);}*/
        /*    25% {transform: skew(5deg, 5deg);}*/
        /*    75% {transform: skew(-5deg, -5deg);}*/
        /*    100% {transform: skew(0deg,0deg);}*/
        /*}*/

        .search-container:hover{
            animation: hoverShake 0.15s linear 3;
        }
        .custom-thead {
            background-color: rgba(176, 237, 215, 0.3);
        }
        /* The Modal (background) */
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 999; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto; /* 15% from the top and centered */
            padding: 20px;
            border: 1px solid #888;
            width: 80%; /* Could be more or less, depending on screen size */
        }

        /* The Close Button */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .page-link{
            position: static;
        }
    </style>
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
                    <td>Profession</td>
                    <td>Accepted Request</td>
                    <td>Percentage Completed</td>
                    <td>Rate Star</td>
                    <td>Active</td>
                    </thead>
                    <tbody >
                    <c:set var="count" value="1"/>
                    <c:forEach items="${requestScope.list}" var="mentor" varStatus="loop">
                        <tr>
                            <td>${count}</td>
                            <c:set var="count" value="${count+1}"/>
                            <td><a target="_blank" href="../mentor/profile?mentorid=${mentor.account.id}">${mentor.account.id}</a> <!-- The button used to copy the text --></td>
                            <td>${mentor.account.name}</td>
                            <td>${mentor.account.userName}</td>
                            <td style="justify-items: center;">
                                <button id="myBtn${loop.index}" class="open-modal btn btn-info" style="padding: 5px; justify-content: center">
                                    <i class="menu-icon mdi mdi-account-box"></i>
                                </button>
                                <div id="myModal${loop.index}" class="modal">
                                    <div class="modal-content">
                                        <span class="close" style="text-align: end">&times;</span>
                                        <h4>
                                            List Skill Of ${mentor.account.userName}
                                        </h4>
                                        <table class="table">
                                            <thead class="table-dark custom-thead">
                                            <td>SKILL</td>
                                            <td>LEVEL</td>
                                            </thead>
                                            <c:forEach items="${mentor.level_skills}" var="skill" >
                                                <tr>
                                                    <td><p>${skill.skill.name}</p></td>
                                                    <td><p>${skill.level.name}</p></td>
                                                </tr>
                                            </c:forEach>
                                        </table>
                                    </div>
                                </div>
                            </td>

                            <td>${mentor.totalBookings}</td>
                            <td>${mentor.percentageCompleted}%</td>
                            <td>${mentor.rating}
                                <span class="fa fa-star checked"></span>
                            </td>
                            <td>
                                <div style="display: flex">
                                    <c:if test="${mentor.account.status.id == 7}">
                                        <button type="button" class="btn btn-success" style="padding: 5px; margin: 5px" value="${mentor.account.id}">ACTIVE</button>
                                    </c:if>
                                    <c:if test="${mentor.account.status.id == 8}">
                                        <button type="button" class="btn btn-warning" style="padding: 5px;margin: 5px" value="${mentor.account.id}">INACTIVE</button>
                                    </c:if>
                                    <c:if test="${mentor.account.status.id == 6}">
                                        <button type="button" class="btn btn-danger" style="padding: 5px;margin: 5px" value="${mentor.account.id} ">BAN</button>
                                    </c:if>

                                </div>
                            </td>
                        </tr>
                    </c:forEach>

                    </tbody>
                </table>
                </div>
                <br>
                <div style="display: flex; justify-content: space-between">
                    <div class="justify-content-start" style=""> Total ${requestScope.totalMentor} result </div>

                    <nav aria-label="Page navigation example" >
                        <ul class="pagination justify-content-end">
                            <li class="page-item <c:if test="${requestScope.page==1}"> disabled </c:if>">
                                <a class="page-link" href="?page=${requestScope.page-1}&name=${requestScope.name}"
                                        <c:if test="${requestScope.page==1}"> tabindex="-1" </c:if>
                                >Previous</a>
                            </li>
                            <c:forEach var="count" begin="1" end="${requestScope.end_page}">
                                <c:if test="${requestScope.page == count}">
                                    <li class="page-item"><a class="page-link active">${count}</a></li>
                                </c:if>
                                <c:if test="${requestScope.page != count}">
                                    <li class="page-item"><a class="page-link" href="?page=${count}&name=${requestScope.name}">${count}</a></li>
                                </c:if>
                            </c:forEach>
                            <li class="page-item">
                                <a class="page-link <c:if test="${requestScope.page==requestScope.end_page  or requestScope.end_page == 0}"> disabled </c:if>" href="?page=${requestScope.page+1}&name=${requestScope.name}"
                                        <c:if test="${requestScope.page==requestScope.end_page or requestScope.end_page == 0 }"> tabindex="-1" </c:if>
                                >Next</a>
                            </li>
                        </ul>
                    </nav>
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
<!-- endinject -->
<!-- Custom js for this page-->
<!-- End custom js for this page-->
<!-- MDB -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<c:if test="${requestScope.totalMentor == 0}">
    <script>
        window.onload = function () {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Don't find Mentor has username : " + '${requestScope.name}',
            }).then((result) => {
                if (result.isConfirmed) {
                    // Redirect the user to a different page
                    window.location.href = "?name=";
                }
            });
        };
    </script>
</c:if>
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
<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>
