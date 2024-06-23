<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 6/22/2024
  Time: 11:11 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <style>

        .search-container .form-control {
            margin-right: 15px;
        }
        .search-container .form-group {
            flex-grow: 1;
            margin-right: 15px;
        }
        .table-containers {
            display: flex;
            justify-content: center;
        }
        .table-containers {
            width: 99%;
            border-radius: 5px;
        }
        .pagination {
            position: relative;
            list-style-type: none;
            padding: 0;
            overflow: hidden;
        }

        .pagination li {
            display: inline;
        }

        .pagination li a {
            display: inline-block;
            padding: 8px 16px;
            text-decoration: none;
            color: black;
        }

        .pagination li.active a {
            background-color: #4CAF50;
            color: white;
        }

        .pagination li a:hover:not(.active) {
            background-color: #ddd;
        }
        .modebar-container{
            display: none;
        }
        #confirmBooking{
            background-color: #07AD90; /* Green background */
            border: none; /* Remove borders */
            color: white; /* White text */
            padding: 15px 49px; /* Add some padding */
            text-align: center; /* Center the text */
            text-decoration: none; /* Remove underline */
            display: inline-block; /* Make the container inline */
            font-size: 16px; /* Increase font size */
            margin: 4px 2px; /* Add some margin */
            transition-duration: 0.4s; /* Add transition effect */
            cursor: pointer; /* Add a pointer cursor on hover */
            border-radius: 12px; /* Rounded corners */
        }
    </style>

</head>
<body>
<div class="container-scroller">
    <!-- partial:partials/_navbar.jsp -->
    <jsp:include page="../admin/partials/_navbar.jsp"></jsp:include>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial:partials/_sidebar.jsp -->
        <jsp:include page="../admin/partials/_sidebar.jsp"></jsp:include>
        <!-- partial -->
        <div style="width: 100%">
        <h2 style="font-weight: bold; margin-bottom: 5px;position: relative">Manage Payment Booking </h2>
            <div class="table-containers">
                <c:if test="${param.page >=2 }">
                    <c:set var="count" value="${(param.page - 1) * 20 + 1}"/>
                </c:if>

                <table class="table table-striped">
                    <thead class="table-dark">
                    <tr>
                        <th>Name</th>
                        <th>Create Date</th>
                        <th>Amount</th>
                        <th>Description</th>
                        <th>Skill Name</th>
                        <th>Skill Level</th>
                        <th>Start Date</th>
                        <th>End Date</th>
                        <th>Name</th>
                        <th>Status</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${bookingsHistory}" var="book">
                        <tr>
                            <td>${count}</td>
                            <td>${book.date}</td>
                            <td>${book.amount}</td>
                            <td>${book.description} </td>
                            <td><img width="20px"
                                     src="${pageContext.request.contextPath}/${book.level_skills.skill.src_icon}">
                                    ${book.level_skills.skill.name}</td>
                            <td>${book.level_skills.level.name} </td>
                            <td>${book.startDate}</td>
                            <td>${book.endDate}</td>
                            <td>${book.mentee.account.name}</td>
                            <td><button id="confirmBooking" data-id="${book.id}" onclick="confirmBooking()">
                                Confirm
                            </button>
                                </td>

                            <c:set var="count" value="${count=  1 + count}"></c:set>
                        </tr>

                    </c:forEach>
                    </tbody>
                    <p>Total: ${bookingsHistory.size()}</p>
                </table>


            </div>
            <c:if test="${not empty bookingsHistory}">
                <ul class="pagination">
                    <c:set var="total" value="${totalBookings}"/>
                    <c:set var="totalPages" value="${(total / 20) + (total % 20 == 0 ? 0 : 1)}"/>
                    <li class="<c:if test="${param.page==1 || param.page == null}">active</c:if>">
                        <a href="history?page=1">1</a>
                    </li>
                    <c:forEach begin="${2}" end="${totalPages}" step="${1}" var="i">
                        <li class="<c:if test="${param.page==i }">active</c:if>">
                            <a href="history?page=${i}">${i}</a>
                        </li>
                    </c:forEach>

                </ul>

            </c:if>
        </div>
        </div>

</body>
<!-- container-scroller -->
<!-- plugins:js -->
<script>
    const confirmBooking = () => {
        var btnOnclick = document.getElementById("confirmBooking");
        var newURL = "";
        btnOnclick.onclick = function (event) {
            event.preventDefault();
            Swal.fire({
                title: "Are you want to confirm booking",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes!"
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: "confirm Successfully",
                        text: "Transaction added",
                        icon: "success"
                    });
                    var id = this.getAttribute('data-id');
                    newURL = "/Frog/confirmBooking?id="+id;
                    window.location.href = newURL;
                }
            });
        };
    }
</script>

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

<!-- endinject -->
<!-- Custom js for this page-->
<!-- End custom js for this page-->

<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>

