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
    <style>
        .cv {
            margin-top: 3vh;
        }

        .right h5 {
            word-wrap: break-word;
        }
    </style>
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
                        <td>Status</td>
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
                                            <div>
                                                <h3 class="header-cv">Mentor CV</h3>
                                                <div class="cv" style="display:flex;">
                                                    <div class="col-md-5 card left">
                                                        <img src="${pageContext.request.contextPath}/${m.account.avatar}" class="mb-3 img-cv">
                                                        <h5 class="name">Name: ${m.account.name}</h5>
                                                        <hr style="margin-right: 23px">
                                                        <div class="email">Email: ${m.account.email}</div>
                                                        <hr style="margin-right: 23px">
                                                        <div class="phone">Phone: ${m.account.phone}</div>
                                                        <hr style="margin-right: 23px">
                                                        <div class="dob">Date of birth: ${m.account.dob}</div>
                                                        <hr style="margin-right: 23px">
                                                        <div class="gender">Gender:
                                                            <span><c:choose>
                                                                <c:when test="${m.account.gender == 1}">
                                                                    Male
                                                                </c:when>
                                                                <c:otherwise>
                                                                    Female
                                                                </c:otherwise>
                                                            </c:choose></span>
                                                        </div>
                                                    </div>
                                                    <div class="col-md-5 card right">
                                                        <div>
                                                            <label>Education:</label>
                                                            <h5>${m.education}</h5>
                                                        </div>
                                                        <hr>
                                                        <div>
                                                            <label>Experience:</label>
                                                            <h5>${m.experience}</h5>
                                                        </div>
                                                        <hr>
                                                        <div>
                                                            <label>About Me:</label>
                                                            <p class="text-wrap">${m.profileDetail}</p>
                                                        </div>
                                                        <hr>
                                                        <div>
                                                            <label>Price:</label>
                                                            <h5>${m.price} đ/hour</h5>
                                                        </div>
                                                        <hr>
                                                        <h5>Skills</h5>
                                                        <div class="skills-container">
                                                            <c:forEach items="${m.level_skills}" var="s">
                                                                <div class="skill-item">
                                                                    <span class="skill-name">${s.skill.name}</span> : <span class="skill-level">${s.level.name}</span>
                                                                </div>
                                                            </c:forEach>
                                                        </div>
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="footer">
                                                    <span>CV of mentor: ${m.account.name}</span>
                                                    <form action="manageCV" method="post">
                                                        <span>
                                                            <c:if test="${m.status.id == 1}">
                                                                <button type="submit" name="action" class="btn btn-success" style="padding: 5px;margin: 5px" value="APPROVE" id="approveButton">APPROVE</button>
                                                                <button type="submit" name="action" class="btn btn-danger" style="padding: 5px;margin: 5px" value="REJECT" id="rejectButton">REJECT</button>
                                                            </c:if>
                                                            <c:if test="${m.status.id != 1}">

                                                            </c:if>
                                                        </span>
                                                        <input type="hidden" name="accountId" value="${m.account.id}" />
                                                    </form>

                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    <div style="display: flex">
                                        <c:if test="${m.status.id == 14}">
                                            <button type="button" class="btn btn-success" style="padding: 5px;margin: 5px" value="${m.account.id} ">APPROVE</button>
                                        </c:if>
                                        <c:if test="${m.status.id == 1}">
                                            <button type="button" class="btn btn-warning" style="padding: 5px; margin: 5px" value="${m.account.id}">PROCESSING</button>
                                        </c:if>
                                        <c:if test="${m.status.id == 2}">
                                            <button type="button" class="btn btn-danger" style="padding: 5px;margin: 5px" value="${m.account.id} ">REJECT</button>
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
<%--                    <nav aria-label="Page navigation example" >--%>
<%--                        <ul class="pagination justify-content-end">--%>
<%--                            <li class="page-item <c:if test="${requestScope.page==1}"> disabled </c:if>">--%>
<%--                                <a class="page-link" href="?page=${requestScope.page-1}&name=${requestScope.name}"--%>
<%--                                        <c:if test="${requestScope.page==1}"> tabindex="-1" </c:if>--%>
<%--                                >Previous</a>--%>
<%--                            </li>--%>
<%--                            <c:forEach var="count" begin="1" end="${requestScope.end_page}">--%>
<%--                                <c:if test="${requestScope.page == count}">--%>
<%--                                    <li class="page-item"><a class="page-link active">${count}</a></li>--%>
<%--                                </c:if>--%>
<%--                                <c:if test="${requestScope.page != count}">--%>
<%--                                    <li class="page-item"><a class="page-link" href="?page=${count}&name=${requestScope.name}">${count}</a></li>--%>
<%--                                </c:if>--%>
<%--                            </c:forEach>--%>
<%--                            <li class="page-item">--%>
<%--                                <a class="page-link <c:if test="${requestScope.page==requestScope.end_page  or requestScope.end_page == 0}"> disabled </c:if>" href="?page=${requestScope.page+1}&name=${requestScope.name}"--%>
<%--                                        <c:if test="${requestScope.page==requestScope.end_page or requestScope.end_page == 0 }"> tabindex="-1" </c:if>--%>
<%--                                >Next</a>--%>
<%--                            </li>--%>
<%--                        </ul>--%>
<%--                    </nav>--%>
                </div>
            </div>
        </div>
    </div>

</div>
<div id="overlay" class="overlay"></div>

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
    });

    document.addEventListener('DOMContentLoaded', () => {
        const starsTotal = 5;

        // Get all star rating elements
        const starContainers = document.querySelectorAll('.stars-inner');

        starContainers.forEach(starsInner => {
            // Get the rating from the data-rating attribute
            const rating = parseFloat(starsInner.getAttribute('data-rating'));
            console.log(starsInner.getAttribute('data-rating'));
            // Calculate the percentage of stars to fill
            const starPercentage = (rating / starsTotal) * 100;

            // Set the width of the stars-inner to the calculated percentage
            starsInner.style.width = starPercentage + '%';
        });
    });

    document.addEventListener('DOMContentLoaded', () => {
        const overlay = document.getElementById('overlay');
        const modals = document.querySelectorAll('.modal');
        const openButtons = document.querySelectorAll('.open-modal');
        const closeButtons = document.querySelectorAll('.close');

        // Loop through all open buttons and add a click event listener
        openButtons.forEach((button, index) => {
            button.addEventListener('click', () => {
                modals[index].style.display = "block";
                overlay.style.display = "block";
            });
        });

        // Loop through all close buttons and add a click event listener
        closeButtons.forEach((button) => {
            button.addEventListener('click', () => {
                const modal = button.closest(".modal");
                modal.style.display = "none";
                overlay.style.display = "none";
            });
        });

        // When the user clicks anywhere outside of the modal, close it
        window.addEventListener('click', (event) => {
            modals.forEach((modal) => {
                if (event.target === modal) {
                    modal.style.display = "none";
                    overlay.style.display = "none";
                }
            });
        });
    });

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


    // // Lấy tất cả các nút có class "ban-btn"
    // var banButtons = document.querySelectorAll('.btn-success');
    // var inactiveButtons = document.querySelectorAll('.btn-warning');
    // var unBanButtons = document.querySelectorAll('.btn-danger');
    // // Lặp qua từng nút và thêm sự kiện click
    // banButtons.forEach(function (button) {
    //     button.addEventListener('click', function () {
    //         // Lấy giá trị value của nút
    //         var userId = this.getAttribute('value');
    //
    //         swal({
    //             title: "Are you sure for BAN?",
    //             text: "This will ban the user with ID: " + userId + "!",
    //             icon: "warning",
    //             buttons: true,
    //             dangerMode: true,
    //         })
    //             .then((willBan) => {
    //                 if (willBan) {
    //                     // Gửi request POST tới Servlet để ban tài khoản
    //                     banUser(userId);
    //                 } else {
    //                     swal("The user is safe for now.");
    //                 }
    //             });
    //     });
    // });
    // unBanButtons.forEach(function (button) {
    //     button.addEventListener('click', function () {
    //         // Lấy giá trị value của nút
    //         var userId = this.getAttribute('value');
    //
    //         swal({
    //             title: "Are you sure for UNBAN?",
    //             text: "This will unban the user with ID: " + userId + "!",
    //             icon: "warning",
    //             buttons: true,
    //             dangerMode: true,
    //         })
    //             .then((willBan) => {
    //                 if (willBan) {
    //                     // Gửi request POST tới Servlet để ban tài khoản
    //                     unbanUser(userId);
    //                 } else {
    //                     swal("Don't unban account");
    //                 }
    //             });
    //     });
    // });
    // inactiveButtons.forEach(function (button) {
    //     button.addEventListener('click', function () {
    //         // Lấy giá trị value của nút
    //         var userId = this.getAttribute('value');
    //
    //         swal({
    //             title: "Are you sure for ACTIVE?",
    //             text: "This will active the user with ID: " + userId + "!",
    //             icon: "success",
    //             buttons: true,
    //             dangerMode: false,
    //         })
    //             .then((willBan) => {
    //                 if (willBan) {
    //                     // Gửi request POST tới Servlet để ban tài khoản
    //                     active(userId);
    //                 } else {
    //                     swal("Don't active account");
    //                 }
    //             });
    //
    //     });
    // });
    //
    // function banUser(userId) {
    //     fetch('../admin/mentor', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/x-www-form-urlencoded'
    //         },
    //         body: 'userId=' + encodeURIComponent(userId) + "&action=ban"
    //     })
    //         .then(response => {
    //             if (response.ok) {
    //                 swal("User banned successfully!").then(() => {
    //                     // Reload the page
    //                     location.reload();
    //                 });
    //             } else {
    //                 swal("Failed to ban user. Please try again later.");
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error banning user:', error);
    //             swal("An error occurred while banning the user. Please try again later.");
    //         });
    // }
    //
    // function unbanUser(userId) {
    //     // Send POST request to Servlet
    //     fetch('../admin/mentor', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/x-www-form-urlencoded'
    //         },
    //         body: 'userId=' + encodeURIComponent(userId) + '&action=unban'
    //     })
    //         .then(response => {
    //             if (response.ok) {
    //                 swal("User unbanned successfully!").then(() => {
    //                     // Reload the page
    //                     location.reload();
    //                 });
    //             } else {
    //                 swal("Failed to unban user. Please try again later.");
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error unbanning user:', error);
    //             swal("An error occurred while unbanning the user. Please try again later.");
    //         });
    // }
    // function active(userId) {
    //     // Send POST request to Servlet
    //     fetch('../admin/mentor', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/x-www-form-urlencoded'
    //         },
    //         body: 'userId=' + encodeURIComponent(userId) + '&action=active'
    //     })
    //         .then(response => {
    //             if (response.ok) {
    //                 swal("User active successfully!").then(() => {
    //                     // Reload the page
    //                     location.reload();
    //                 });
    //             } else {
    //                 swal("Failed to active user. Please try again later.");
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error unbanning user:', error);
    //             swal("Failed to active user. Please try again later.");
    //         });
    // }
    function updateUserTable() {
        location.reload();
    }


</script>
</body>
</html>
