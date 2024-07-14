<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 5/24/2024
  Time: 11:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of Following Requests</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/view_follower.css">
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container mt-5">
    <div style="margin: 0 0  70px 50px ">
        <a href="${pageContext.request.contextPath}/mentor/profile?mentorid=${requestScope.id}" style="text-decoration: none">
            <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>
            &nbsp;<span style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px" >Back to profile</span>
        </a>
    </div>
    <h1 class="mb-4">List of students who have been training</h1>
    <table class="table table-striped">
        <thead class="table-dark">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Address</th>
            <th>Time of request</th>
            <th>Time training end</th>
            <th>Total slot booking</th>

            <td></td>
<%--            <th>Status</th>--%>
<%--            <th>Action</th>--%>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.mentees}" var="m">

                <tr>
                    <td>${m.account.name}</td>
                    <td>${m.account.email}</td>
                    <td>${m.account.address}</td>
                    <td>${m.booking.startDate}</td>
                    <td>${m.booking.endDate}</td>
                    <td>${m.booking.totalSlot}</td>
                    <td><button onclick="viewMenteeProfle('${m.account.id}')" type="button" class="btn btn-info" data-mdb-ripple-init>Info</button></td>
                </tr>

        </c:forEach>
        </tbody>
    </table>
</div>

<script>
    function viewMenteeProfle(id) {
        console.log(id);
        console.log("${pageContext.request.contextPath}/mentee/profile?menteeid=" + id);
       window.location.href = "${pageContext.request.contextPath}/mentee/profile?menteeid=" + id;
    }
</script>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
<!-- Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>
