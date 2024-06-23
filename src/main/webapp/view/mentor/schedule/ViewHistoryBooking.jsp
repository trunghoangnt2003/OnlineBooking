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
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/ViewHistoryBoking.css">
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
        <h1 class="mb-4">List of History Request </h1>

        <div>
            <button id="filterButton" style="background: transparent;border: none;outline: none; cursor: pointer;">

                <i class="fa-solid fa-filter fa-xl"></i>
            </button>

            <button id="statisticButton" style="background: transparent;border: none;outline: none; cursor: pointer;"
                    onclick="openStatisticBtn()">
                <i class="fa-solid fa-square-poll-horizontal fa-xl"></i>
            </button>
            <div id="filterBox" class="filter-box">
                <button id="closeButton" class="close-button">X</button>
                <div class="content" style="    display: flex;
                    margin: 5px 235px;
                    margin-left: -10px;
                    justify-content: space-around;">
                    <!-- Thêm nội dung tùy ý vào đây -->
                    <p>
                        <button style="background: transparent;border: none;outline: none; cursor: pointer;">
                            <i class="fa-solid fa-arrow-up-wide-short">

                            </i></button>
                    </p>
                    <p>
                        <button style="background: transparent;border: none;outline: none; cursor: pointer;"><i
                                class="fa-solid fa-arrow-down-wide-short"></i></button>
                    </p>
                    <p>
                        <button style="background: transparent;border: none;outline: none; cursor: pointer;"
                                id="filterBtn" value="money" onclick="filterByAmount()">
                            <i class="fa-solid fa-filter-circle-dollar"></i>
                        </button>
                    </p>

                </div>
            </div>

            <div id="myModal" class="modal">
                <div class="modal-content">
                    <span class="close">&times;</span>
                    <div style="display: flex">

                        <div class="statistic-card">
                            <p style="margin-left: -130px;
                                     margin-top: -25px; text-decoration: underline;font-weight: bold;">Statistic
                                request</p>
                            <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 20px;">
                                <i class="fa-solid fa-circle-info fa-sm"></i> Details</p>

                            <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 20px;">
                                <i class="fa-solid fa-user-pen fa-sm"></i> Total Invited : ${numberOfInvited}</p>
                            <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 0px;">
                                <i class="fa-solid fa-circle-check fa-sm"></i> Accepted : ${numberOfAccepted}</p>
                            <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: -15px;">
                                <i class="fa-solid fa-ban fa-sm"></i> Cancel : ${numberOfRejected}</p>
                            <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: -15px;">
                                <i class="fa-solid fa-envelope fa-sm"></i> Waiting : ${numberOfWaiting}</p>
                            <p style=" text-decoration: none;text-transform: uppercase;font-weight: bold;    margin-left: -130px;
                                     margin-top: 15px;">

                                Star: ${rating}   </p>
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
    <c:if test="${param.page >=2 }">
        <c:set var="count" value="${(param.page - 1) * 20 + 1}"/>
    </c:if>

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
                             src="${pageContext.request.contextPath}/${book.level_skills.skill.src_icon}">
                            ${book.level_skills.skill.name}</td>
                    <td>${book.level_skills.level.name} </td>
                    <td>${book.startDate}</td>
                    <td>${book.endDate}</td>
                    <td>${book.mentee.account.name}</td>
                    <c:if test="${book.status.id == 2}">
                        <td>${book.status.type }
                        </td>
                    </c:if>
                    <c:if test="${book.status.id == 13}">
                        <td> <a href="/Frog/mentor/schedule/manage?id=${book.id}&action=1" style="text-decoration: none">${book.status.type }<a/>
                        </td>
                    </c:if>

                    <c:set var="count" value="${count=  1 + count}"></c:set>
                </tr>

        </c:forEach>
        </tbody>
    </table>

    <c:if test="${not empty bookingsHistory}">
        <ul class="pagination">
            <c:set var="total" value="${totalBookings}"/>
            <c:set var="totalPages" value="${(total / 20) + (total % 20 == 0 ? 0 : 1)}"/>
            <li class="<c:if test="${param.page==1 || param.page == null}">active</c:if>">
                <a href="history?page=1">1</a>
            </li>
            <c:forEach begin="${2}" end="${totalPages}" step="${1}" var="i">
                <li class="<c:if test="${param.page==i }">active</c:if>">
                    <a href="history?page=${i}">${i}</a>
                </li>
            </c:forEach>
            <li><h6 style="right: 0;position: absolute;">
                Total bookings: ${numberOfInvited}</h6></li>
        </ul>

    </c:if>
</div>
<script>
    document.getElementById('filterButton').addEventListener('click', function () {
        // Hiển thị thanh khung hình chữ nhật
        document.getElementById('filterBox').style.display = 'block';
    });

    document.getElementById('closeButton').addEventListener('click', function () {
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
    const filterByAmount = () => {
        // var filterValue = document.getElementById('filterBtn').value;
        // const data = { filter: 'money' };
        //
        // fetch('../mentor/schedule/history', {
        //     method: 'POST',
        //     headers: {
        //         'Content-Type': 'application/json'
        //     },
        //     body: JSON.stringify(data),
        // })
        //     .then(response => {
        //         if (!response.ok) {
        //             throw new Error('Network response was not ok');
        //         }
        //         // return response.json();
        //     })
        //     .then(data => {
        //         // Kiểm tra nội dung phản hồi (nếu cần thiết)
        //         console.log('Phản hồi từ server:', data);
        //
        //         // Chuyển hướng đến JSP nếu phản hồi đúng như mong đợi
        //         window.location.href = 'ViewHistoryBooking.jsp';
        //     })
        //     .catch(error => {
        //         console.error('Lỗi:', error);
        //     });
    }
    var accept = document.getElementById("myPlot").getAttribute("data-accept");
    var reject = document.getElementById("myPlot").getAttribute("data-reject");
    var waiting = document.getElementById("myPlot").getAttribute("data-waiting");
    const xArray = ["Accepted", "Canceled", "waiting"];
    const yArray = [accept, reject, waiting];

    const layout = {title: "Statistic Booking Requests"};

    const data = [{labels: xArray, values: yArray, type: "pie"}];

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
