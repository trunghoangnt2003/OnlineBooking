<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 5/22/2024
  Time: 10:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create CV of Mentor</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"
            rel="stylesheet"
    />
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/create_profile.css">
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container mt-4">
    <div class="box frame row">
        <div class="avatar col-md-3"><img src="${pageContext.request.contextPath}/img/profile.jpg" alt="image avatar"></div>
        <div class="form col-md-9">
            <div class="title">Create CV of Mentor</div>
            <form action="createcv" method="post" onsubmit="return validateForm()">
                <div class="row">
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="email" class="form-label">Email:</label>
                            <input type="text" class="form-control" id="email" value="${requestScope.account.email}" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="username" class="form-label">User Name:</label>
                            <input type="text" class="form-control" id="username" value="${requestScope.account.userName}" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <input type="text" class="form-control" id="name" value="${requestScope.account.name}" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="dob" class="form-label">Date of Birth:</label>
                            <input type="text" class="form-control" id="dob" value="${requestScope.account.dob}" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone:</label>
                            <input type="text" class="form-control" id="phone" value="${requestScope.account.phone}" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="gender" class="form-label">Gender:</label>
                            <input type="text" class="form-control" id="gender" value="<c:choose><c:when test='${requestScope.account.gender == 1}'>Male</c:when><c:otherwise>Female</c:otherwise></c:choose>" disabled>
                        </div>
                        <div class="mb-3">
                            <label for="address" class="form-label">Address:</label>
                            <input type="text" class="form-control" id="address" value="${requestScope.account.address}" disabled>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="mb-3">
                            <label for="edu" class="form-label">Education:</label>
                            <input type="text" class="form-control" id="edu" name="edu" placeholder="Enter your education" required>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price per hour:</label>
                            <input type="number" class="form-control" id="price" name="price" placeholder="Enter price per hour ($)" required>
                        </div>
                        <div class="mb-3">
                            <label for="exp" class="form-label">Experience:</label>
                            <input type="text" class="form-control" id="exp" name="exp" placeholder="Number of years of experience" required>
                        </div>
                        <div class="mb-3">
                            <label for="detail" class="form-label">Profile Detail:</label>
                            <textarea class="form-control" id="detail" name="detail" rows="4" placeholder="Detail about yourself" required></textarea>
                        </div>
                        <div class="mb-3">
                            <label for="category" class="form-label">Category:</label>
                            <select class="form-select" id="category" name="category" required>
                                <c:forEach items="${requestScope.cate}" var="c">
                                    <option value="${c.id}" ${c.id == param.category ? 'selected' : ''}>${c.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="mb-3">
                            <label for="skill" class="form-label">Skill:</label>
                            <select class="form-select" id="skill" name="skill" required>
                                <c:forEach items="${requestScope.skill}" var="s">
                                    <option>${s.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </div>
                <button type="submit" class="btn btn-primary">Save</button>
            </form>
        </div>
    </div>
</div>
<script>
    function validateForm() {
        let edu = document.getElementById('edu').value.trim();
        let price = document.getElementById('price').value.trim();
        let exp = document.getElementById('exp').value.trim();
        let detail = document.getElementById('detail').value.trim();
        if (edu === '' || price === '' || exp === '' || detail === '') {
            alert('Please fill out all required fields.');
            return false;
        }
        return true;
    }
</script>
</body>
</html>
