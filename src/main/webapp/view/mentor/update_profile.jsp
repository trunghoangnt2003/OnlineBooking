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
    <title>Update profile of Mentor</title>
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
<c:if test="${requestScope.mentorLog.status.id == 1}">
    <div class="alert alert-danger text-center" role="alert">
        <b>Please waiting Manager approve your CV. CV will show more detail after approve!</b>
    </div>
</c:if>
<c:if test="${requestScope.mentorLog.status.id == 2}">
    <div class="alert alert-danger text-center" role="alert">
        <b>Your CV has been rejected. Please update your CV again!</b>
    </div>
</c:if>

<div class="container">
    <div style="margin-top: 30px">
        <jsp:include page="../common/backBtn.jsp"></jsp:include>
    </div>
    <h2>Update Information</h2>
    <form action="update_profile" method="post" enctype="multipart/form-data">
        <div class="image">
            <div class="img-profile">
                <img id="avatar-preview" src="${pageContext.request.contextPath}/${requestScope.mentor.account.avatar}" alt="avatar">
            </div>
        </div>
        <div class="round">
            <input type="file" name="photo" id="file-input" onchange="previewImage();"/>
        </div>
        <div class="row">
            <div class="col-md-5 card info">
                <div class="body-info">
                    <div class="form-group">
                        <label for="email">Email:</label>
                        <input type="text" id="email" class="form-control" value="${requestScope.mentor.account.email}" disabled>
                    </div>
                    <div class="form-group">
                        <label for="name">Name:</label>
                        <input type="text" id="name" name="name" class="form-control" value="${requestScope.mentor.account.name}" required>
                    </div>
                    <div class="form-group">
                        <label for="phone">Phone:</label>
                        <input type="tel" id="phone" name="phone" class="form-control" value="${requestScope.mentor.account.phone}" required>
                    </div>
                    <div class="form-group">
                        <label for="address">Address:</label>
                        <input type="text" id="address" name="address" class="form-control" value="${requestScope.mentor.account.address}" required>
                    </div>
                    <div class="form-group">
                        <label for="dob">Date of Birth:</label>
                        <input type="date" id="dob" name="dob" class="form-control" value="${requestScope.mentor.account.dob}" required>
                    </div>
                    <div class="form-group">
                        <label>Gender:</label>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="gender" id="male" value="1" <c:if test="${requestScope.mentor.account.gender == 1}">checked</c:if> />
                            <label class="form-check-label" for="male">Male</label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="gender" id="female" value="0" <c:if test="${requestScope.mentor.account.gender == 0}">checked</c:if> />
                            <label class="form-check-label" for="female">Female</label>
                        </div>
                    </div>
                </div>
            </div>
            <div class="col-md-5 card info">
                <div class="form-group">
                    <label for="edu">Education:</label>
                    <input type="text" id="edu" name="edu" class="form-control" value="${requestScope.mentorLog.education}" required>
                </div>
                <div class="form-group">
                    <label for="exp">Experience:</label>
                    <input type="text" id="exp" name="exp" class="form-control" value="${requestScope.mentorLog.experience}" required>
                </div>
                <div class="form-group">
                    <label for="exp">Price:</label>
                    <input type="text" id="price" name="price" class="form-control" value="${requestScope.mentorLog.price}" required>
                </div>
                <div class="form-group detail">
                    <label for="detail">Profile Detail:</label>
                    <textarea id="detail" name="detail" class="form-control" rows="8" placeholder="Detail about yourself" required>${requestScope.mentorLog.profileDetail}</textarea>
                </div>
            </div>
        </div>

        <div class="card skill">
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
        <div class="form-group btn-save">
            <button name="action" value="save" style="margin-right: 20px" type="submit" class="btn btn-info" data-mdb-ripple-init>Save</button>
            <button name="action" value="submit" style="margin-left: 20px" type="submit" class="btn btn-success" data-mdb-ripple-init>Submit</button>
        </div>
    </form>
</div>
<script>
    function previewImage() {
        const file = document.getElementById('file-input').files[0];
        const preview = document.getElementById('avatar-preview');
        const reader = new FileReader();

        reader.addEventListener("load", function () {
            preview.src = reader.result;
        }, false);

        if (file) {
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
