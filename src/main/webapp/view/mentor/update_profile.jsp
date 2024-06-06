<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 5/23/2024
  Time: 11:18 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Update CV of Mentor</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/update.css">
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">
    <h2>Update Information</h2>
    <form action="update_profile" method="post">
        <div>
            <div class="img">
                <img id="avatar-preview" src="${pageContext.request.contextPath}/${requestScope.mentor.account.avatar}" alt="avatar">
            </div>
            <div class="round">
                <input type="file" name="photo" id="file-input" onchange="previewImage();"/>
            </div>
            <div class="form-group">
                <label for="name">Email:</label>
                <input type="text" id="email" value="${requestScope.mentor.account.email}" disabled>
            </div>
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" value="${requestScope.mentor.account.name}" required>
            </div>
            <div class="form-group">
                <label for="phone">Phone:</label>
                <input type="tel" id="phone" name="phone" value="${requestScope.mentor.account.phone}" required>
            </div>
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" value="${requestScope.mentor.account.address}" required>
            </div>
            <div class="form-group">
                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dob" value="${requestScope.mentor.account.dob}" required>
            </div>
            <div class="form-group">

                <label>Gender:</label>
                <div class="form-check gender-r">
                    <input class="form-check-input" type="radio" name="gender" id="male" value="1"
                            <c:if test="${requestScope.mentor.account.gender == 1}">
                        checked
                    </c:if> />
                    <label class="form-check-label" for="male">Male</label>
                </div>
                <div class="form-check gender-l">
                    <input class="form-check-input" type="radio" name="gender" id="female" value="0" <c:if test="${requestScope.mentor.account.gender == 0}">checked</c:if> />
                    <label class="form-check-label" for="female">Female</label>
                </div>
            </div>
        </div>
        <div>
            <div>
                <label for="edu">Education:</label>
                <input type="text" id="edu" name="edu" value="${requestScope.mentor.education}" required>
            </div>
            <div>
                <label for="price">Price per hour:</label>
                <input type="number" id="price" name="price" value="${requestScope.mentor.price}" required>
            </div>
            <div>
                <label for="exp">Experience:</label>
                <input type="text" id="exp" name="exp" value="${requestScope.mentor.experience}" required>
            </div>
            <div>
                <label for="detail">Profile Detail:</label>
                <textarea id="detail" name="detail" rows="8" placeholder="Detail about yourself" required></textarea>
            </div>
        </div>
        <div class="form-group">
            <input type="submit" value="Save">
        </div>
    </form>
</div>
</body>
</html>
