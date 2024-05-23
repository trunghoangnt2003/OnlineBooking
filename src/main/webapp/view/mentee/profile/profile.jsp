<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 5/21/2024
  Time: 10:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css" type="text/css" media="screen, project">
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
    <!-- Google Fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"/>
    <!-- MDB -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</head>
<body>
<h1></h1>
<h1></h1>
<div class="main">
    <div class="box-image">
        <div>
            <img class="avatar" src="${pageContext.request.contextPath}/${requestScope.mentee.account.avatar}" alt="">
        </div>
        <div class="infor-basic">
            <div class="name">${requestScope.mentee.account.name}</div>
            <div><i class="fa-solid fa-envelope"></i> ${requestScope.mentee.account.email}</div>
            <div><i class="fa-solid fa-phone"></i> ${requestScope.mentee.account.phone}</div>
            <div><i class="fa-solid fa-venus-mars"></i> ${requestScope.mentee.account.gender ? "Male" : "Female"}</div>
            <div><i class="fa-solid fa-location-dot"></i> ${requestScope.mentee.account.address}</div>
            <div><i class="fa-solid fa-calendar-days"></i> ${requestScope.mentee.account.dob}</div>
        </div>
    </div>
    <div class="box-detail">
        <div class="function">
            <div style="margin-right: 10px" class="function-box"><a href="/Prog/mentee/update">Update Profile</a></div>
            <div style="margin-right: 10px" class="function-box">View Request</div>
        </div>
    </div>
</div>
</body>
</html>
