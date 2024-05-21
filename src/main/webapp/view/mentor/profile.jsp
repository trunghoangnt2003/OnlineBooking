<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 5/21/2024
  Time: 7:51 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/profile.css">
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>

    <div class="container">
        <div class="avatar">
            <img class="avatar-detail" style="width: 100%; height: 100%" src="${pageContext.request.contextPath}/image/profile.jpg">

            <div class="infor">
                <div>Name: ${requestScope.mentor.account.name}</div>
                <div>Dob: ${requestScope.mentor.account.dob}</div>
                <div>Gender: ${requestScope.mentor.account.gender ? "Male" : "Female"}</div>
                <div>Email: ${requestScope.mentor.account.email}</div>
                <div>Phone: ${requestScope.mentor.account.phone}</div>
                <div>Address: ${requestScope.mentor.account.address}</div>
            </div>
        </div>

        <div class="detail">
            <div class="profile-detail">Detail</div>
            <div>Level of Education: ${requestScope.mentor.education}</div>
            <div>Number of year of experience: ${requestScope.mentor.experience} year</div>
            <div>Price: ${requestScope.mentor.price}</div>
        </div>


    </div>

</body>
</html>
