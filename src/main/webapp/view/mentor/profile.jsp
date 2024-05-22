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
        <!-- Fontawesome CDN Link -->
        <link rel="stylesheet"
              href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

        <!-- Font Awesome -->
        <link
                rel="stylesheet"
                href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"

        />
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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
        <script>
            import { Dropdown, Ripple, initMDB } from "mdb-ui-kit";

            initMDB({Dropdown, Ripple});
        </script>
</head>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css//mentor/profile.css">
<body>
<jsp:include page="../common/header.jsp"></jsp:include>

    <div class="main">
        <div class="box-left">
            <div class="bio">
                <div class="bio-image">
                    <div><img class="avatar" src="${pageContext.request.contextPath}/img/profile.jpg"></div>
                    <div class="name">${requestScope.mentor.account.name}</div>
                </div>

                <div class="bio-detail">
                    <div><h3>Biography</h3></div>
                    <div class="detail"><i class="fa-solid fa-envelope" style="color: #74C0FC;"></i> ${requestScope.mentor.account.email}</div>
                    <div class="detail"><i class="fa-solid fa-phone" style="color: #74C0FC;"></i> ${requestScope.mentor.account.phone}</div>
                    <div class="detail"><i class="fa-solid fa-location-dot" style="color: #74C0FC;"></i> ${requestScope.mentor.account.address}</div>
                    <div class="detail"><i class="fa-solid fa-calendar-days" style="color: #74C0FC;"></i> ${requestScope.mentor.account.dob}</div>
                    <div class="detail"><i class="fa-solid fa-venus-mars" style="color: #74C0FC;"></i> ${requestScope.mentor.account.gender ? "Male" : "Female"}</div>
                </div>
                <div class="price">
                    <div>Price: ${requestScope.mentor.price} $/hour</div>
                </div>
            </div>
            <hr class="line">
            <%-- =================================            --%>
            <div class="profile-detail">
                <div>Education: ${requestScope.mentor.education}</div>
                <div>Year of Experience: ${requestScope.mentor.experience} year</div>
            </div>
        </div>

        <%-- ==========================================================================       --%>
        <div class="box-right">
            <div class="small-box-on">
                <h3>About Me</h3>
                <div>Hi, I'm ${requestScope.mentor.account.name}, here some thing about me:</div>
                <div>${requestScope.mentor.profileDetail}</div>
            </div>

            <div class="small-box-under">
                <h3>My Skill</h3>
                <div>
                    <c:forEach items="${requestScope.mentor.skills}" var="skill">
                        <div>${skill.name}</div>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const starsTotal = 5;

            // Get all star rating elements
            const starContainers = document.querySelectorAll('.stars-inner');

            starContainers.forEach(starsInner => {
                // Get the rating from the data-rating attribute
                const rating = parseFloat(starsInner.getAttribute('data-rating'));
                console.log(starsInner.getAttribute('data-rating'));
                // Calculate the percentage of stars to fill
                const starPercentage = (rating / starsTotal) * 100;

                // Set the width of the stars-inner to the calculated percentage
                starsInner.style.width = starPercentage + '%';
            });
        })
    </script>

</body>

</html>
