<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 6/5/2024
  Time: 12:37 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <title>List of History Requests</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/rating.css">

    <style>
        .filter-box {
            display: none;
            position: absolute;
            right: 270px;
            margin-top: -25px;
            width: 600px;
            height: 30px;
            border: 1px solid #000;
            padding: 2px;
            background-color: #fff;
            border-radius: 5px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
        }

        /* Định dạng nút đóng */
        .close-button {
            position: absolute;
            top: 0px;
            right: 2px;
            background: none;
            border: none;
            font-size: 16px;
            cursor: pointer;
        }
        .modal {
            display: none; /* Hidden by default */
            position: fixed; /* Stay in place */
            z-index: 1; /* Sit on top */
            left: 0;
            top: 0;
            width: 100%; /* Full width */
            height: 100%; /* Full height */
            overflow: auto; /* Enable scroll if needed */
            background-color: rgb(0,0,0); /* Fallback color */
            background-color: rgba(0,0,0,0.4); /* Black w/ opacity */
        }

        /* Modal Content/Box */
        .modal-content {
            background-color: #fefefe;
            margin: 15% auto;
            padding: 170px;
            border: 1px solid #888;
            width: 70%;
        }

        /* The Close Button */
        .close {
            color: #aaa;
            float: right;
            font-size: 28px;
            font-weight: bold;
            display: flex;
            justify-content: end;
            margin-top: -160px;
            margin-right: -150px;
        }

        .close:hover,
        .close:focus {
            color: black;
            text-decoration: none;
            cursor: pointer;
        }
        .statistic-card{
            border: 2px solid #000;
            border-radius: 3px;
            padding: 40px 150px;
            background-color: #f9f9f9;
            width: fit-content;
            margin: 10px auto;
            margin-top:40px;
            margin-left: -130px;
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
        }
        .formatted-text {
            text-decoration: underline; /* Gạch chân */
            text-transform: uppercase; /* Viết hoa */
            font-weight: bold; /* In đậm */
            margin: 0; /* Xóa khoảng cách */
        }
    </style>
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
</head>
<body>
<jsp:include page="/view/common/header.jsp"></jsp:include>
<div class="container mt-5">
    <div style="margin: 0 0  70px 50px ">
        <a href="${pageContext.request.contextPath}/mentor/schedule" style="text-decoration: none">
            <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>
            &nbsp;<span
                style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px">Back to schedule</span>
        </a>
    </div>
    <div style="justify-content: space-between; display: flex;align-items: center;">
        <h1 class="mb-4">List of History Requests </h1>
        <div>
            <button id="filterButton" style="background: transparent;border: none;outline: none; cursor: pointer;">

                <i class="fa-solid fa-filter fa-xl"></i>
            </button>

            <button id="statisticButton" style="background: transparent;border: none;outline: none; cursor: pointer;" onclick="openStatisticBtn()">
            <i class="fa-solid fa-square-poll-horizontal fa-xl"></i>
            </button>
                 <div id="filterBox" class="filter-box">
                        <button id="closeButton" class="close-button">X</button>
                            <div class="content" style="display: flex;margin: 2px 480px;
                                 margin-left: 0px;justify-content: space-around;">

                                <!-- Thêm nội dung tùy ý vào đây -->
                                <p><i class="fa-solid fa-arrow-up-wide-short"></i></p>
                                <p><i class="fa-solid fa-arrow-down-wide-short"></i></p>
                                <p><i class="fa-solid fa-filter-circle-dollar"></i></p>

                            </div>
                </div>

                 <div id="myModal" class="modal">
                        <div class="modal-content">
                            <span class="close">&times;</span>
                            <div style="display: flex">

                                <div class="statistic-card">
                                    <p style="margin-left: -130px;
                                     margin-top: -25px; text-decoration: underline;font-weight: bold;">Statistic request</p>
                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 20px;">
                                        <i class="fa-solid fa-circle-info fa-sm"></i>          Details</p>

                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 20px;">
                                        <i class="fa-solid fa-user-pen fa-sm"></i>  Total Invited  :   ${numberOfInvited}</p>
                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 0px;">
                                       <i class="fa-solid fa-circle-check fa-sm"></i> Accepted  :   ${numberOfAccepted}</p>
                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: -15px;">
                                        <i class="fa-solid fa-ban fa-sm"></i>  Cancel  :   ${numberOfRejected}</p>
                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: -15px;">
                                        <i class="fa-solid fa-envelope fa-sm"></i>   Waiting  :   ${numberOfWaiting}</p>
                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 15px;">
                                        <i class="fa-solid fa-face-smile fa-sm"></i>  Accepted (%) :   ${(numberOfAccepted/numberOfInvited)*100}%</p>
                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: -15px;">
                                        <i class="fa-solid fa-face-frown fa-sm"></i>  Cancel (%) :   ${(numberOfRejected/numberOfInvited)*100}%</p>
                                    <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: -15px;">
                                        Star:  ${rating}   </p>
                                    <div class="stars-outer">
                                        <div class="stars-inner" data-rating="${rating}"></div>
                                    </div>
                                </div>
                                <div id="myPlot" style="width:70%;max-width:600px" data-accept="${numberOfAccepted}"
                                 data-reject="${numberOfRejected}" data-waiting="${numberOfWaiting}"></div>
                            </div>
                        </div>
                </div>
        </div>
    </div>
    <table class="table table-striped">
        <thead class="table-dark">
        <tr>
            <th>Name</th>
            <th>Create Date</th>
            <th>Amount</th>
            <th>Description</th>
            <th>Skill Name</th>
            <th>Skill Level</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Name</th>
            <th>Status</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${bookingsHistory}" var="book">
            <tr>
                <td>${count}</td>
                <td>${book.date}</td>
                <td>${book.amount}</td>
                <td>${book.description} </td>
                <td><img width="20px"
                         src="${pageContext.request.contextPath}${book.level_skills.skill.src_icon}">
                        ${book.level_skills.skill.name}</td>
                <td>${book.level_skills.level.name} </td>
                <td>${book.startDate}</td>
                <td>${book.endDate}</td>
                <td>${book.mentee.account.name}</td>
                <td>${book.status.type }
                </td>
                <c:set var="count" value="${count= count +1}"></c:set>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<script>
    document.getElementById('filterButton').addEventListener('click', function() {
        // Hiển thị thanh khung hình chữ nhật
        document.getElementById('filterBox').style.display = 'block';
    });

    document.getElementById('closeButton').addEventListener('click', function() {
        // Ẩn thanh khung hình chữ nhật
        document.getElementById('filterBox').style.display = 'none';
    });
    const openStatisticBtn = () => {
        var statisticBtn = document.getElementById("statisticButton");
        var modal = document.getElementById("myModal");
        statisticBtn.onclick = function () {
            modal.style.display = "block";
            return false; // Ngăn chặn gửi form mặc định
        }
        var span = document.getElementsByClassName("close")[0];
        span.onclick = function () {
            modal.style.display = "none";
        }
    }
    var accept = document.getElementById("myPlot").getAttribute("data-accept");
    var reject = document.getElementById("myPlot").getAttribute("data-reject");
    var waiting = document.getElementById("myPlot").getAttribute(" data-waiting");
    const xArray = ["Accepted", "Canceled","waiting"];
    const yArray = [accept,reject,waiting];

    const layout = {title:"Statistic Booking Requests"};

    const data = [{labels:xArray, values:yArray, type:"pie"}];

    Plotly.newPlot("myPlot", data, layout);
</script>
<!-- Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"
        integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"
        integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+"
        crossorigin="anonymous"></script>
<script src="${pageContext.request.contextPath}/js/common/rating.js"></script>
</body>
</html>
