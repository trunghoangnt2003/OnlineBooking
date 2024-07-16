<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 6/15/2024
  Time: 10:51 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
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
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<style>
    body {
        color: #2e3e50;
        background: #FFFFFF;
    }

    /*.X {*/
    /*    margin-top: 25px;*/
    /*    padding: 1% 2%;*/
    /*    max-width: 1440px;*/
    /*    border-radius: 5px;*/
    /*    background: #ecf0f1;*/
    /*    box-shadow: 0 2px 6px 0 rgba(0, 0, 0, .3);*/
    /*}*/

    h2 {
        text-align: center;
        /*font-family: 'Baloo Tamma', cursive;*/
    }

    h3 {
        text-align: start;
        text-decoration: underline;
    }

    li {
        margin-right: 10px;

    }


    /*.SG {*/
    /*    margin: 0;*/
    /*    padding: 60px;*/
    /*    text-align: center;*/
    /*}*/

    /*.SG .sgLi {*/
    /*    min-width: 24%;*/
    /*    margin: 2% .35%;*/
    /*    display: inline-flex;*/
    /*    transition: box-shadow 0.2s ease;*/
    /*}*/

    /*.SG .sgLi:hover {*/
    /*    box-shadow: 4px 4px 4px 2px rgba(144, 238, 144, .5);*/
    /*}*/

    /*.SG .box {*/
    /*    width: 100%;*/
    /*    height: 100vh;*/
    /*    padding: 1% 2%;*/
    /*    background: #fff;*/
    /*    min-height: 200px;*/
    /*    max-height: 220px;*/
    /*    box-sizing: border-box;*/
    /*}*/



    /*.s18 li:before {*/
    /*    content: '';*/
    /*    width: 20px;*/
    /*    height: 20px;*/
    /*    margin-right: 15px;*/
    /*    display: inline-block;*/
    /*    background: url(//goo.gl/lcPSVD);*/
    /*    background-position: 50%;*/
    /*}*/


    /*.s19 li:before {*/
    /*    content: '\f0a9';*/
    /*    margin-right: 15px;*/

    /*}*/

    /*!* responsive grid*!*/
    /*@media (max-width: 970px) {*/
    /*    .SG .sgLi {*/
    /*        width: 180px;*/
    /*    }*/
    /*}*/

    /*@media (max-width: 425px) {*/
    /*    .SG .sgLi {*/
    /*        width: 100%;*/
    /*    }*/
    /*}*/
    .custom-table {
        width: 100%;
        border-collapse: collapse;
    }

    .custom-table th {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 5px 5px;
    }
    .custom-table td {
        border: 1px solid #dddddd;
        text-align: left;
        padding: 2px 5px;
        height: 100%;
    }
    .custom-table th {
        background-color: #f2f2f2;
    }
    .custom-table .narrow-column {
        width: 1%;
        padding: 0 40px;
    }
</style>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/ManageSchedule.css">

<body>
<jsp:include page="/view/common/header.jsp"></jsp:include>
<div class="container">
    <c:if test="${param.id == null}">
        <div style="margin: 25px 10px  20px 50px ">
            <a href="${pageContext.request.contextPath}/mentor/schedule" style="text-decoration: none">
                <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>
                &nbsp;<span
                    style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px">Back to schedule</span>
            </a>
        </div>
    </c:if>
    <c:if test="${param.id != null}">
        <div style="margin: 25px 10px  20px 50px ">
            <a href="${pageContext.request.contextPath}/mentor/schedule/manage" style="text-decoration: none">
                <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>
                &nbsp;<span
                    style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px">Back to manage booking </span>
            </a>
        </div>
    </c:if>
    <c:if test="${param.id != null}">
        <div class="row" style="margin-top: 25px;">
            <div class="col-4 ">
                <h3 style="text-decoration: none;color: #07ad90;">Manage Request</h3>
                <div class="list-group" id="list-tab" role="tablist" style="width: 220px">
                    <a class="list-group-item list-group-item-action <c:if test="${param.action == 1}">list-group-item-success</c:if>"
                       href="/Frog/mentor/schedule/manage?id=${param.id}&action=1"
                       aria-controls="home">Schedule</a>
                    <a class="list-group-item list-group-item-action  <c:if test="${param.action == 2}">list-group-item-success</c:if>"
                       href="/Frog/mentor/schedule/manage?id=${param.id}&action=2"
                       aria-controls="profile">Attendance</a>
                    <a class="list-group-item list-group-item-action  <c:if test="${param.action == 3}">list-group-item-success</c:if>"
                       href="/Frog/mentor/schedule/manage?id=${param.id}&action=3" aria-controls="messages">Payment</a>
                    <a class="list-group-item list-group-item-action  <c:if test="${param.action == 4}">list-group-item-success</c:if>"
                       href="/Frog/mentor/schedule/manage?id=${param.id}&action=4" aria-controls="settings">Booking
                        Detail</a>
                </div>
            </div>
            <div class="col-8">
                <c:if test="${param.action == 1}">
                    <div style="display: inline">
                        <div><h3 style="text-decoration: none;color: #07ad90;">Table</h3></div>
                        <div><input type="date" class="styled-date" name="today" id="today" value="${today}"
                                    data-viewID="${param.viewID}" style="width: 20%"
                                    onchange="updateURL()"></div>
                    </div>
                    <table>
                        <thead>
                        <tr>
                            <th></th>
                            <c:forEach items="${weeks}" var="week">
                                <th>${week.dayOfWeek}<br/>
                                        ${week.dayOfMonth}
                                </th>
                            </c:forEach>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${slots}" var="t">
                            <tr>
                                <td>
                                    <div style="display: flex; text-align: center">Slot ${t.id}<br/>
                                            ${t.start_at} - ${t.end_at}
                                    </div>
                                </td>

                                <c:forEach items="${weeks}" var="week">
                                    <c:set var="countCheck" value="${count = 0}"/>
                                    <c:set var="foundBusy" value="false"/>
                                    <c:set var="icon" value="false"/>
                                    <c:set var="waitingIcon" value="false"/>
                                    <td>
                                        <c:forEach items="${schedules}" var="sche">
                                            <c:if test="${sche.schedule.date == week.convertStringToDateByDay(week.dayOfMonth) && sche.schedule.slot.id == t.id}">
                                                <c:if test="${sche.status.id == 11 && param.id == sche.booking.id}">
                                                    <c:set var="countCheck" value="${count = 1}"/>
                                                    <c:set var="foundBusy" value="true"/>
                                                    <c:set var="nameSkill"
                                                           value="${sche.booking.level_skills.skill.name}"/>
                                                    <c:set var="avatarSkill"
                                                           value="${sche.booking.level_skills.skill.src_icon}"/>
                                                    <c:set var="nameType"
                                                           value="${sche.booking.level_skills.level.name}"/>
                                                </c:if>
                                                <c:if test="${sche.status.id == 3 && param.id == sche.booking.id}">
                                                    <c:set var="countCheck" value="${count = 2}"/>
                                                    <c:set var="foundBusy" value="true"/>
                                                    <c:set var="nameSkill"
                                                           value="${sche.booking.level_skills.skill.name}"/>
                                                    <c:set var="avatarSkill"
                                                           value="${sche.booking.level_skills.skill.src_icon}"/>
                                                    <c:set var="nameType"
                                                           value="${sche.booking.level_skills.level.name}"/>
                                                    <c:if test="${sche.attend}">
                                                        <c:set var="present" value="present"></c:set>
                                                        <c:set var="icon" value="true"></c:set>
                                                    </c:if>
                                                    <c:if test="${!sche.attend}">
                                                        <c:set var="present" value="absent"></c:set>
                                                        <c:set var="icon" value="true"></c:set>
                                                    </c:if>
                                                </c:if>
                                            </c:if>
                                        </c:forEach>
                                        <c:if test="${countCheck == 1}">
                                            <div class="notes-container">
                                                <i class="pin"></i>
                                                <blockquote class="notes red">
                                                    <img width="30px"
                                                         src="${pageContext.request.contextPath}/${avatarSkill}"
                                                         style="margin-bottom: 10px;"/>
                                                        ${nameType}

                                                </blockquote>

                                            </div>
                                        </c:if>
                                        <c:if test="${countCheck == 2}">
                                            <div class="notes-container">
                                                <i class="pin"></i>
                                                <blockquote class="notes red">
                                                    <img width="30px"
                                                         src="${pageContext.request.contextPath}/${avatarSkill}"
                                                         style="margin-bottom: 10px;"/>
                                                        ${nameType}
                                                    <c:if test="${present.equals('present') && icon}">
                                                        <i class="fa-solid fa-frog fa-xl"
                                                           style="color: #02b23c; margin-top: -25px; margin-left: 40px"></i>
                                                    </c:if>
                                                    <c:if test="${present.equals('absent') && icon}">
                                                        <i class="fa-solid fa-face-sad-tear fa-xl"
                                                           style="color: #f25452; margin-top: -25px; margin-left: 40px"></i>
                                                    </c:if>
                                                </blockquote>

                                            </div>
                                        </c:if>
                                    </td>
                                </c:forEach>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                </c:if>
                <c:if test="${param.action == 2}">
                    <c:forEach items="${bookingSlots}" var="bookSlot">
                        <div class="card">
                            <div class="card-body">
                                <h5 class="card-title"> Day: ${bookSlot.schedule.date}
                                    Slot ${bookSlot.schedule.slot.id}
                                    (${bookSlot.schedule.slot.start_at}-${bookSlot.schedule.slot.end_at})
                                </h5>
                                <p class="card-text">
                                    <img width="30px"
                                         src="${pageContext.request.contextPath}/${bookSlot.booking.level_skills.skill.src_icon}"
                                         style="margin-bottom: 10px;"/>
                                        ${bookSlot.booking.level_skills.skill.name} ${bookSlot.booking.level_skills.level.name}
                                </p>
                                <c:if test="${bookSlot.status.id == 3}">
                                    <c:if test="${bookSlot.attend}">
                                        <p><input type="button" value="Present" id="confirmBtn2"
                                                  style=" background: #28a745;display: inline"></p>
                                    </c:if>
                                    <c:if test="${!bookSlot.attend}">
                                        <p><input type="button" value="Absent" id="absentBtn2"
                                                  style=" background: #ff2222;display: inline">
                                        </p>
                                    </c:if>
                                </c:if>
                                <c:if test="${bookSlot.status.id == 11 }">
                                    <li style="list-style-type: none; display: inline">
                                        <input type="button" value="Present" id="confirmBtn_${bookSlot.id}"
                                               class="BtnButton"
                                               data-id=" ${bookSlot.id}"
                                               data-time="${bookSlot.schedule.date}_${bookSlot.schedule.slot.start_at}"
                                               data-slot="${param.id}_${param.action}"
                                               onclick="confirmSlot(${bookSlot.id})">
                                    </li>
                                    <li style="list-style-type: none;display: inline">
                                        <input type="button" value="Absent" id="absentBtn_${bookSlot.id}"
                                               class="BtnButton"
                                               data-id=" ${bookSlot.id}"
                                               data-time="${bookSlot.schedule.date}_${bookSlot.schedule.slot.start_at}"
                                               data-slot="${param.id}_${param.action}"
                                               onclick="absentSlot(${bookSlot.id})">
                                    </li>
                                </c:if>

                            </div>
                        </div>

                    </c:forEach>

                </c:if>

                <c:if test="${param.action == 4}">
                    <div class="list-container">
                        <div class="list-title">Booking Details</div>
                        <ul style=" list-style: none;
    padding: 0;
    margin: 0;
    font-family: Arial, sans-serif;">
                            <li class="liSub"><span>Name : </span> ${bookingSlots[0].booking.mentee.account.name}</li>
                            <li class="liSub"><span>Mail : </span> ${bookingSlots[0].booking.mentee.account.email}</li>
                            <li class="liSub"><span>Phone : </span> ${bookingSlots[0].booking.mentee.account.phone}</li>
                            <li class="liSub">
                                <span>Skill name:</span>
                                <img width="30px"
                                     src="${pageContext.request.contextPath}/${bookingSlots[0].booking.level_skills.skill.src_icon}">
                                    ${bookingSlots[0].booking.level_skills.skill.name}
                            </li>
                            <li class="liSub">
                                <span>Skill level : </span> ${bookingSlots[0].booking.level_skills.level.name}</li>
                            <li class="liSub"><span>Day Start : </span> ${bookingSlots[0].booking.startDate}</li>
                            <li class="liSub"><span>Day End : </span> ${bookingSlots[0].booking.endDate}</li>
                            <li class="liSub"><span>Slot Booked : </span> ${bookingSlots[0].schedule.slot.id}</li>
                            <li class="liSub"><span>Created date : </span> ${bookingSlots[0].booking.date}</li>
                            <li class="liSub"><span>Total amount request : </span> ${bookingSlots[0].booking.amount}
                            </li>
                        </ul>
                    </div>

                </c:if>
                <c:if test="${param.action == 3}">
                    <ul>
                        <li style="color: red">(Note: If the lesson has been finished,please confirm slot)</li>
                        <li><span
                                style="font-weight: bold">Number of Slots confirm: ${slotConfirmedNumber}/${bookingSlotsNumber}</span>
                        </li>
                        <c:if test="${slotConfirmedNumber == bookingSlotsNumber}">
                            <c:if test="${bookingSlots[0].booking.status.id != 13 }">
                                <li><span
                                        style="font-weight: bold">All lessons are finished,wait ${bookingSlots[0].booking.mentee.account.name} confirms...</span>
                                </li>
                                <input type="button" class="btn" id="btn" value="Finish"
                                       data-id="${bookingSlots[0].booking.mentee.account.id}"
                                       data-booking-id="${bookingSlots[0].booking.id}" onclick="sendMail()">

                            </c:if>
                        </c:if>
                    </ul>
                    <ul style="margin-top: 20px">
                        <li style="list-style-type: none">Status payment :
                            <span style="color: #f8ce0b"> <c:if
                                    test="${bookingSlots[0].booking.status.id != 13  }">Payment Not Confirm Yet</c:if> </span>
                            <span style="color: #28a745"><c:if
                                    test="${bookingSlots[0].booking.status.id == 13 }">Payment Confirmed</c:if></span>
                        </li>

                    </ul>
                </c:if>
            </div>
        </div>
    </c:if>

    <%--    view id--%>
    <c:if test="${param.id == null}">
        <%--        <div>--%>
        <%--            <div class="X">--%>
        <h2>Manage Request booking</h2>
        <h3>My Mentee booking</h3>
        <%--                <ul class="SG">--%>

        <%--                    <c:forEach items="${menteeBooking}" var="book">--%>
        <%--                        <li class="sgLi" onclick="openMenteeBookings(${book.id})">--%>
        <%--                            <div class="box">--%>
        <%--                                <h4>--%>
        <%--                                    <img width="30px"--%>
        <%--                                         src="${pageContext.request.contextPath}/${book.level_skills.skill.src_icon}">--%>
        <%--                                        ${book.level_skills.skill.name}--%>
        <%--                                </h4>--%>
        <%--                                <h5>--%>
        <%--                                        ${book.level_skills.level.name}--%>
        <%--                                </h5>--%>
        <%--                                <ul class="df">--%>
        <%--                                    <li style="list-style-type: none">Name: ${book.mentee.account.name}</li>--%>
        <%--                                    <li style="list-style-type: none">Start Date: ${book.startDate}</li>--%>
        <%--                                    <li style="list-style-type: none">End Date: ${book.endDate}</li>--%>
        <%--                                </ul>--%>
        <%--                            </div>--%>
        <%--                        </li>--%>
        <%--                    </c:forEach>--%>
        <%--                </ul>--%>
        <%--            </div>--%>
        <%--        </div>--%>
        <table class="custom-table">
            <thead>
            <tr>
                <th>Number</th>
                <th>Skill Name</th>
                <th>Skill Level</th>
                <th>Start Date</th>
                <th>End Date</th>
                <th>Name</th>
                <th>Details</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${menteeBooking}" var="book">
                <tr>
                    <td class="narrow-column">${count}</td>
                    <td>
                        <img width="30px" src="${pageContext.request.contextPath}/${book.level_skills.skill.src_icon}">
                            ${book.level_skills.skill.name}
                    </td>
                    <td>${book.level_skills.level.name}</td>
                    <td>${book.startDate}</td>
                    <td>${book.endDate}</td>
                    <td>${book.mentee.account.name}</td>
                    <td class="narrow-column"><i class="fa-solid fa-eye fa-xl" style="color: #63E6BE" onclick="openMenteeBookings(${book.id})"></i></td>
                </tr>
                <c:set var="count" value="${count + 1}"></c:set>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>
<script>
    const sendMail = () => {
        var btnOnclick = document.getElementById("btn");
        var newURL = "";
        btnOnclick.onclick = function (event) {
            event.preventDefault();
            Swal.fire({
                title: "Are you want to send mail ?",
                text: "Ensure that all slot has happened",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes!"
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: "Successfully",
                        text: "Mail sent",
                        icon: "success"
                    });
                    var menteeId = document.getElementById('btn').getAttribute('data-id');
                    var bookingId = document.getElementById('btn').getAttribute('data-booking-id');
                    var url = '/Frog/confirmMail?menteeId=' + menteeId + '&bookingId=' + bookingId;
                    window.location.href = url;
                }
            });
        };
    }

    const updateURL = () => {
        var currentURL = window.location.href;
        var url = new URL(currentURL);
        selectedDate = document.getElementById('today').value;
        // Cập nhật hoặc thêm tham số 'today' vào URL
        url.searchParams.set('today', selectedDate);
        window.location.href = url.toString();

    }
    const openMenteeBookings = (id) => {
        var currentURL = window.location.href;
        var newURL = currentURL + "?id=" + id + "&action=1";
        window.location.href = newURL;
    }

    const absentSlot = (id) => {
        var dataTime = document.getElementById("absentBtn_" + id).getAttribute('data-time');
        var date = dataTime.split("_")[0];
        var time = dataTime.split("_")[1];
        var currentDate = new Date();
        var dateTimeString = date + " " + time;
        var dateTimeValue = new Date(dateTimeString);
        if (currentDate < dateTimeValue) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Slot has not occurred yet.",
            });
        } else {
            var btnOnclick = document.getElementById("absentBtn_" + id);
            var newURL = "";
            btnOnclick.onclick = function (event) {
                event.preventDefault();
                Swal.fire({
                    title: "Is the mentee ABSENT a slot ?",
                    text: "Ensure that the slot has happened",
                    icon: "question",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Yes, absent !"
                }).then((result) => {
                    if (result.isConfirmed) {
                        var ID = this.getAttribute('data-id');
                        var dataSlot = this.getAttribute('data-slot');
                        newURL = '/Frog/mentor/schedule/confirm?ID=' + ID + '&option=absent&manage=true&dataSlot=' + dataSlot;
                        localStorage.setItem('isAbsentManage', 'yes');
                        // Redirect to the new URL
                        window.location.href = newURL;
                    }
                });
            };
        }

    }
    const confirmSlot = (id) => {
        var dataTime = document.getElementById("confirmBtn_" + id).getAttribute('data-time');
        var date = dataTime.split("_")[0];
        var time = dataTime.split("_")[1];
        var currentDate = new Date();
        var dateTimeString = date + " " + time;
        var dateTimeValue = new Date(dateTimeString);
        console.log(dateTimeValue);
        console.log(currentDate);
        if (currentDate < dateTimeValue) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Slot has not occurred yet.",
            });
        } else {
            Swal.fire({
                title: "Are you sure you want to PRESENT slot?",
                text: "Ensure that the slot has happened.",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, I confirm it!"
            }).then((result) => {
                if (result.isConfirmed) {
                    var ID = document.getElementById("confirmBtn_" + id).getAttribute('data-id');
                    var dataSlot = document.getElementById("confirmBtn_" + id).getAttribute('data-slot');
                    var newURL = '/Frog/mentor/schedule/confirm?ID=' + ID + '&option=present&manage=true&dataSlot=' + dataSlot;
                    localStorage.setItem('isConfirmSlotManage', 'yes');
                    // Redirect to the new URL
                    window.location.href = newURL;
                }
            });
        }
    };
    const Toast = Swal.mixin({
        toast: true,
        position: "top-end",
        showConfirmButton: false,
        timer: 3000,
        timerProgressBar: true,
        didOpen: (toast) => {
            toast.onmouseenter = Swal.stopTimer;
            toast.onmouseleave = Swal.resumeTimer;
        }
    });
    window.onload = function () {
        var isConfirmSlotManage = localStorage.getItem('isConfirmSlotManage');
        var isAbsentManage = localStorage.getItem('isAbsentManage');
        if (isConfirmSlotManage) {
            Toast.fire({
                icon: "success",
                title: " take present successfully  "
            });
            localStorage.removeItem('isConfirmSlotManage');
        }
        if (isAbsentManage) {
            Toast.fire({
                icon: "success",
                title:  "take absent successfully "
            });
            localStorage.removeItem('isAbsentManage');
        }

    }

</script>
<!-- Bootstrap JS and dependencies -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

</body>
</html>
