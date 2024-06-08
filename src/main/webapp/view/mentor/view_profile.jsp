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
    <title>Profile of Mentor</title>
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
        .header {
            position: relative;
        }
        .my-account {
            position: relative;
            z-index: 1;
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            margin-top: -150px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
        }
        .taskbar {
            display: flex;
            justify-content: space-between;
        }
        .feedback {
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5);
        }
        .img {
            position: relative;
            z-index: 1;
            margin-top: -80px;
            width: 200px;
            height: 200px;
            border-radius: 50%;
        }
        .row-rate {
            display: grid;
            grid-template-columns: auto auto;
            align-items: center;
        }
        .feedback-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        .feedback-item {
            padding: 10px;
            border-bottom: 1px solid #ddd;
        }
        .feedback-item:last-child {
            border-bottom: none;
        }
        .feedback-item .name {
            font-weight: bold;
            color: #333;
        }
        .feedback-item .comment {
            margin: 5px 0;
            font-style: italic;
            color: #555;
        }
        .feedback-item .rate {
            color: #f39c12;
        }
        .feedback-item .date {
            font-size: 0.9em;
            color: #888;
        }
        .skills-container {
            display: flex;
            flex-wrap: wrap;
        }

        .skill-item {
            display: flex;
            align-items: center;
            background-color: #fff;
            border: 1px solid #ddd;
            border-radius: 8px;
            padding: 10px;
            margin: 10px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            width: 250px;
            transition: transform 0.3s;
        }

        .skill-item:hover {
            transform: translateY(-5px);
        }

        .icon-skill {
            width: 40px;
            height: 40px;
            margin-right: 10px;
        }

        .skill-name {
            font-weight: bold;
            margin-right: 5px;
        }

        .skill-level {
            color: #666;
        }
        a {
            text-decoration: none; /* Removes the underline */
            color: inherit; /* Inherits the color from the parent element */
        }

        /* Specific styles for the item */
        .item {
            font-size: 16px; /* Adjust font size as needed */
            color: #000; /* Set the text color */
        }

        .item a:hover {
            color: #007bff; /* Change color on hover if desired */
        }
        .stars-outer {
            position: relative;
            display: inline-block;
        }

        .stars-inner {
            position: absolute;
            top: 0;
            left: 0;
            white-space: nowrap;
            overflow: hidden;
            width: 0;
        }

        .stars-outer::before {
            content: "\f005 \f005 \f005 \f005 \f005";
            font-family: "Font Awesome 5 Free";
            font-weight: 900;
            color: #ccc;
        }

        .stars-inner::before {
            content: "\f005 \f005 \f005 \f005 \f005";
            font-family: "Font Awesome 5 Free";
            font-weight: 900;
            color: #f8ce0b;
        }

        .head {
            color: white;
        }
        .pop-up {
            display: none;
            position: absolute;
            top: 120%;
            left: 50%;
            transform: translate(-50%, -50%);
            z-index: 1000;
        }
        .overlay {
            display: none; /* Initially hidden */
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* Semi-transparent background */
            z-index: 1000;
        }
        .name {
            font-weight: bold;
        }
        .email {
            font-weight: bold;
        }
        .phone {
            font-weight: bold;
        }
        .gender {
            font-weight: bold;
        }
        .dob {
            font-weight: bold;
        }
        .address {
            font-weight: bold;
        }
        .img-cv {
            width: 250px;
            height: 250px;
            border-radius: 10px;
        }
        .info {
            display: flex;
            justify-content: space-between;
        }
        .left-info {
            width: 300px;
        }
        .right-info {
            width: 500px;
        }
        .close {
            position: absolute;
            top: 5px;
            width: 15px;
            height: 15px;
            margin-left: 713px;
            font-size: 15px;
            border-radius: 50%;
            border: 1px solid;
            cursor: pointer;
            text-align: center;
        }
        .footer {
            display: flex;
            justify-content: end;
            margin-right: 20px;
            margin-bottom: 15px;
            font-size: 10px;
            font-weight: bold;
        }
        .header-cv {
            text-align: center;
            color: #1BB295;
            margin-top: 10px;
            margin-bottom: 30px;
        }
        .price {
            background-color: #54ffe7;
            padding: 15px 25px;
            border-radius: 10px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            color: #333;
            font-size: 24px;
            font-weight: bold;
            display: inline-block;
            text-align: center;
            transition: transform 0.3s, box-shadow 0.3s;
            margin-left: 40px;
        }

        .price:hover {
            transform: scale(1.05);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.2);
        }

        /* Highlighting the currency and unit separately */
        .price span.currency {
            color: #ff6f61;
        }

        .price span.unit {
            font-size: 16px;
            color: #777;
        }
    </style>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="header pb-8 pt-5 pt-lg-8 d-flex align-items-center" style="min-height: 600px; background-image: url('../img/mentor.jpg'); background-size: cover; background-position: center top;">
    <!-- Mask -->
    <span class="mask bg-gradient-default opacity-8"></span>
    <!-- Header container -->
    <div class="container-fluid d-flex align-items-center">
        <div class="row">
            <div class="col-lg-7 col-md-10 head">
                <h1>Hello ${requestScope.mentor.account.name}</h1>
                <div class="stars-outer">
                    <div class="stars-inner" data-rating="${requestScope.mentor.rating}"></div>
                </div>
                <p class="text-white mt-0 mb-5">This is profile of ${requestScope.mentor.account.name}, your can see information and something about them</p>
            </div>
        </div>
    </div>
</div>

<div class="container mt-5">
    <div class="row">
        <!-- First Column -->
        <div class="col-md-6">
            <div class="card mb-4 my-account">
                <div class="card-body">
                    <div class="taskbar">
                        <h3>My account</h3>
                        <c:choose>
                            <c:when test="${requestScope.isAuthor}">
                                <button onclick="goToUpdate()" type="button" class="btn btn-info" data-mdb-ripple-init>Update Profile</button>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="btn btn-warning" data-mdb-ripple-init>Message</button>
                            </c:otherwise>
                        </c:choose>
                        <button onclick="showCV()" type="button" class="btn btn-success" data-mdb-ripple-init>View CV</button>
                    </div>
                    <hr>
                    <h5>User information</h5>
                    <div class="row">
                        <div class="col">
                            <label class="name">Full Name</label>
                            <p>${requestScope.mentor.account.name}</p>
                        </div>
                        <div class="col">
                            <label class="dob">Date of birth</label>
                            <p>${requestScope.mentor.account.dob}</p>
                        </div>
                        <div class="col">
                            <label class="gender">Gender</label>
                            <p><c:choose>
                                <c:when test="${requestScope.mentor.account.gender == 1}">
                                    Male
                                </c:when>
                                <c:otherwise>
                                    Female
                                </c:otherwise>
                            </c:choose></p>
                        </div>
                    </div>
                    <hr>
                    <h5>Contact information</h5>
                    <div class="row">
                        <div class="col">
                            <label class="email">Email</label>
                            <p>${requestScope.mentor.account.email}</p>
                        </div>
                        <div class="col">
                            <label class="phone">Phone</label>
                            <p>${requestScope.mentor.account.phone}</p>
                        </div>
                        <div class="col">
                            <label class="address">Address</label>
                            <p>${requestScope.mentor.account.address}</p>
                        </div>
                    </div>
                    <hr>
                    <h5>About me</h5>
                    <p>${requestScope.mentor.profileDetail}</p>
                    <hr>
                    <h5>My Skills</h5>
                    <div class="skills-container">
                        <c:forEach items="${requestScope.level_skills}" var="s">
                            <div class="skill-item">
                                <img class="icon-skill" src="${pageContext.request.contextPath}${s.skill.src_icon}" alt="${s.skill.name}">
                                <span class="skill-name">${s.skill.name}</span> : <span class="skill-level">${s.level.name}</span>
                            </div>
                        </c:forEach>
                    </div>
                </div>
            </div>
        </div>

        <!-- Second Column -->
        <div class="col-md-6">
            <div class="card mb-4 my-account">
                <div class="card-body text-center">
                    <div>
                        <img src="${pageContext.request.contextPath}${requestScope.mentor.account.avatar}" class="mb-3 img">
                        <div class="price">${requestScope.mentor.price} $/hour</div>
                    </div>
                    <h3>${requestScope.mentor.account.name}</h3>
                    <p>${requestScope.mentor.account.dob}</p>
                    <div class="row-rate">
                        <div class="item">
                            <div class="stars-outer">
                                <div class="stars-inner" data-rating="${requestScope.mentor.rating}"></div>
                            </div>
                        </div>
                        <div class="item">
                            <a href="view_follower?id=${requestScope.id}">${requestScope.numberFollower} <i class="fa-solid fa-user-graduate"></i></a>
                        </div>
                    </div>

                </div>
            </div>
            <div class="card mb-4 feedback">
                <div class="card-body">
                    <h5>Feedback</h5>
                    <ul class="feedback-list">
                        <c:forEach items="${requestScope.review}" var="r">
                            <li class="feedback-item">
                                <div class="name">${r.mentee.account.name}</div>
                                <div class="comment">${r.comment}</div>
                                <div class="rate">Rating:
                                    <div class="stars-outer">
                                        <div class="stars-inner" data-rating="${r.rate}"></div>
                                    </div>
                                </div>
                                <div class="date">${r.date}</div>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="overlay" class="overlay"></div>
<%--POP-UP--%>
<div id="cv-popup" class="container card col-md-6 pop-up">
    <div>
        <h1 class="header-cv">Mentor CV</h1>
        <button onclick="hideCV()" type="button" class="btn-close close" aria-label="Close"></button>
        <div class="info">
            <div class="left-info">
                <img src="${pageContext.request.contextPath}${requestScope.mentor.account.avatar}" class="mb-3 img-cv">
                <h5 class="name">Name: ${requestScope.mentor.account.name}</h5>
                <hr style="margin-right: 23px">
                <h5>Rating</h5>
                <div class="stars-outer">
                    <div class="stars-inner" data-rating="${requestScope.mentor.rating}"></div>
                </div>
                <hr style="margin-right: 23px">
                <div class="email">Email: ${requestScope.mentor.account.email}</div>
                <hr style="margin-right: 23px">
                <div class="phone">Phone: ${requestScope.mentor.account.phone}</div>
                <hr style="margin-right: 23px">
                <div class="dob">Date of birth: ${requestScope.mentor.account.dob}</div>
                <hr style="margin-right: 23px">
                <div class="gender">Gender:
                    <span><c:choose>
                        <c:when test="${requestScope.mentor.account.gender == 1}">
                            Male
                        </c:when>
                        <c:otherwise>
                            Female
                        </c:otherwise>
                    </c:choose></span>
                </div>
            </div>
            <div class="right-info">
                <div>
                    <label>Education:</label>
                    <h5>${requestScope.mentor.education}</h5>
                </div>
                <hr>
                <div>
                    <label>Experience:</label>
                    <h5>${requestScope.mentor.experience}</h5>
                </div>
                <hr>
                <div>
                    <label>About Me:</label>
                    <h5>${requestScope.mentor.profileDetail}</h5>
                </div>
                <hr>
                <h5>My Skills</h5>
                <div class="skills-container">
                    <c:forEach items="${requestScope.level_skills}" var="s">
                        <div class="skill-item">
                            <img class="icon-skill" src="${pageContext.request.contextPath}${s.skill.src_icon}" alt="${s.skill.name}">
                            <span class="skill-name">${s.skill.name}</span> : <span class="skill-level">${s.level.name}</span>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <hr>
        <h6 class="footer">CV of mentor: ${requestScope.mentor.account.name}</h6>
    </div>
</div>

<script>
    function showCV() {
        document.getElementById('cv-popup').style.display = 'block';
        document.getElementById('overlay').style.display = 'block';
    }

    function hideCV() {
        document.getElementById('cv-popup').style.display = 'none';
        document.getElementById('overlay').style.display = 'none';
    }

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
    });

    function goToUpdate() {
        window.location.href = "http://localhost:8080/Frog/mentor/update_profile";
    }
</script>
</body>
</html>
