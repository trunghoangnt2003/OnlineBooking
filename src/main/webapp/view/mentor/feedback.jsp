<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 6/15/2024
  Time: 5:59 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>FeedBack</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <%--    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/view_profile.css">--%>
    <style>
        .feedback {
            width: 90%;
            margin: auto;
            margin-top: 4vh;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
        }

        .name {
            color: #00AAB7;
            font-weight: bold;
        }

        .auth {
            padding-left: 5vw;
            padding-top: 4vh;
            font-size: 20px;
        }
        .thead {
            background: black;
            color: white;
        }
    </style>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
    <div style="margin: 2vh 0 0 5vw ">
        <a href="${pageContext.request.contextPath}/mentor/profile?mentorid=${requestScope.id}" style="text-decoration: none">
            <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>
            &nbsp;<span style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px" >Back to profile</span>
        </a>
    </div>
    <div class="auth">
        <span>View FeedBack of</span>
        <span class="name">${requestScope.mentor.account.name}</span>
    </div>
    <div class="card feedback">
        <table class="table table-striped">
            <thead class="thead-dark">
                <tr class="thead">
                    <th scope="col">STT</th>
                    <th scope="col">Name</th>
                    <th scope="col">Date</th>
                    <th scope="col">Comment</th>
                    <th scope="col">Rate</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.reviews}" var="r" varStatus="stt">
                <tr>
                    <th scope="row">${stt.index + 1}</th>
                    <td>${r.mentee.account.name}</td>
                    <td>${r.date}</td>
                    <td>${r.comment}</td>
                    <td>${r.rate}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
</body>
</html>
