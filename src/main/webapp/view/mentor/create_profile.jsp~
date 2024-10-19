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

    <style>
        .img {
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<c:if test="${requestScope.mentorLog.status.id == 1}">
    <div class="alert alert-danger text-center" role="alert">
        <b>Please waiting Manager approve your CV. CV will show more detail after approve</b>
    </div>
</c:if>
<c:if test="${requestScope.mentorLog.status.id == 16}">
    <div class="alert alert-danger text-center" role="alert">
        <b>Please submit CV for manager check</b>
    </div>
</c:if>

<c:if test="${requestScope.mentorLog.status.id == 2}">
    <div class="alert alert-danger text-center" role="alert">
        <b>Your CV has been rejected. Please update your CV again!</b>
    </div>
</c:if>
<div class="container mt-4">
    <div class="box frame row">
        <div class="form col-md-9">
            <div class="title">Create CV of Mentor</div>
            <form action="create_profile" method="post" onsubmit="return validateForm()" enctype="multipart/form-data">
                <div class="col-md-3">
                    <div class="upload">
                        <div class="img">
                            <img style="width: 200px; height: 200px; border-radius: 50%" id="avatar-preview" src="${pageContext.request.contextPath}/${requestScope.mentor.account.avatar}" alt="">
                        </div>
                        <div class="round">
                            <input type="file" name="photo" id="file-input" onchange="previewImage();"/>
                            <div class="camera">
                                                        <label for="file-input" class="camera-icon">
                                                            <i class="fa fa-camera fa-sm" style="color: white;"></i>
                                                        </label>
                            </div>
                        </div>
                    </div>
                </div>
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
                            <input type="text" class="form-control" id="edu" name="edu" value="${requestScope.mentorLog.education}" placeholder="Enter your education" required>
                        </div>
                        <div class="mb-3">
                            <label for="price" class="form-label">Price per hour:</label>
                            <input type="number" class="form-control" id="price" name="price" value="${requestScope.mentorLog.price}" placeholder="Enter price per hour ($)" required>
                        </div>
                        <div class="mb-3">
                            <label for="exp" class="form-label">Experience:</label>
                            <input type="text" class="form-control" id="exp" name="exp" value="${requestScope.mentorLog.experience}" placeholder="Number of years of experience" required>
                        </div>
                        <div class="mb-3">
                            <label for="detail" class="form-label">Profile Detail:</label>
                            <textarea class="form-control box-detail" id="detail" name="detail" rows="8" placeholder="Detail about yourself" required>${requestScope.mentorLog.profileDetail}</textarea>
                        </div>

                    </div>
                    <div class="skill">
                        <div class="row">
                            <c:forEach items="${requestScope.levels}" var="level">
                                <div class="col-md-4">
                                    <div class="skill-name">${level.name}</div>
                                    <c:forEach items="${requestScope.level_skills}" var="levelSkill">
                                        <c:if test="${levelSkill.level.name == level.name}">
                                            <div class="form-check">
                                                <input class="form-check-input" type="checkbox" id="skill-${levelSkill.id}" name="level_skill" value="${levelSkill.id}"
                                                        <c:forEach items="${requestScope.mentorSkill}" var="mentorSkill">
                                                            <c:if test="${mentorSkill.id == levelSkill.id}">checked</c:if>
                                                        </c:forEach>
                                                />
                                                <label class="form-check-label" for="skill-${levelSkill.id}">${levelSkill.skill.name}</label>
                                            </div>
                                        </c:if>
                                    </c:forEach>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
                <div class="button">
                    <button name="action" value="save" style="margin-right: 20px" type="submit" class="btn btn-info" data-mdb-ripple-init>Save</button>
                    <button name="action" value="submit" style="margin-left: 20px" type="submit" class="btn btn-success" data-mdb-ripple-init>Submit</button>
                </div>
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
        if (edu === '' || price === '' || exp === '' || detail === '' || image === '') {
            alert('Please fill out all required fields.');
            return false;
        }
        return true;
    }

    function previewImage() {
        const file = document.getElementById('file-input').files[0];
        if (file) {
            const reader = new FileReader();
            reader.onload = function(event) {
                document.getElementById('avatar-preview').src = event.target.result;
            };
            reader.readAsDataURL(file);
        }
    }
</script>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
</body>
</html>
