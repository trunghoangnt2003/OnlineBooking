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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentee/profile.css" type="text/css" media="screen, project">
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
<jsp:include page="../../common/header.jsp"></jsp:include>
<div class="page-content page-container" id="page-content">
    <div class="padding" style="display: flex; justify-content: center; margin-top: 30px; border-radius: 50px">
        <div class="row container border-10 ">
            <div class = "body-content" style="">
                <div class="card user-card-full">
                    <div class="row m-l-0 m-r-0">
                        <div class="col-sm-4 bg-c-lite-green user-profile " style="background: #07ad90">
                            <div class="card-block text-center text-white ">
                                <div class="m-b-25">
                                    <img class="avatar" src="${pageContext.request.contextPath}/${requestScope.mentee.account.avatar}" alt="">
                                </div>
                                <h4 class="f-w-600 " style="margin-top: 10px">${requestScope.mentee.account.name}</h4>
                                <a href="update" style="color: white"><i class="fa-solid fa-pen-to-square fa-xl" style="margin-top: 10px"></i></a>
                            </div>
                        </div>
                        <div class="col-sm-8" style="height: 500px; padding: 50px">
                            <div class="card-block">
                                <h1 class="m-b-20 p-b-5 b-b-default f-w-600" style="color: #07ad90;font-weight: bold;">Information</h1>
                                <hr style="color: #07ad90; opacity: 100%">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <span class="m-b-10 f-w-600" style="color: #07AD90;  font-size: 20px">Email</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.email}</h6>
                                    </div>
                                    <div class="col-sm-6">
                                        <span class="m-b-10 f-w-600" style="color: #07AD90;  font-size: 20px">Phone</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.phone}</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <span class=" f-w-600" style="color: #07AD90;  font-size: 20px">Gender</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.gender == 1 ? "Male" : "Female"}</h6>
                                    </div>
                                    <div class="col-sm-6">
                                        <div style="color: #07AD90;  font-size: 20px"> Address </div>
                                        <span class="m-b-10 f-w-600">${requestScope.mentee.account.address}</span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <span class="m-b-10 f-w-600" style="color: #07AD90;  font-size: 20px">Date of Birth</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.dob}</h6>
                                    </div>
                                </div>
                                <div>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
