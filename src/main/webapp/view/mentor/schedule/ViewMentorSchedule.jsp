<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 6/1/2024
  Time: 11:05 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/ViewSchedule.css">

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
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css"
            rel="stylesheet"
    />
    <!-- SweetAlert2 -->

    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
    <style>
        .body{
            display: flex;
            margin: 60px 60px;
            justify-content: space-between;

        }
        .left-side{
            width: 16%;
            margin-top: 10px;
        }
        .right-side{
            width: 83%;
        }

    </style>
</head>
<body>
<jsp:include page="/view/common/header.jsp"></jsp:include>
<div class="body">
    <div class="left-side">
        <form action="schedule" method="GET" id="getForm">
            <div>
                <label for="today" style="color:#07AD90;">Select Date:</label>
                <input type="date" class="styled-date" name="today" id="today" value="${param.today}"
                       data-viewID="${param.viewID}"
                       onchange="updateURL()">
            </div>
        </form>

        <button id="myBtn" onclick="openSetTime()">Set Time</button>
        <br/>
        <!-- Nút mở modal mới -->
        <button id="myBtn3" onclick="invited_request()" style="padding: 15px 31px">View Request</button>
        <button id="myBtn3" onclick="openMyRequestWork()" style="padding: 15px 20px">Manage Request</button>
        <button id="myBtn3" onclick="history_request()">History Request</button>
        <c:if test="${param.viewID != null}">
            <div>
                <h5 style="text-align: center; color: #07AD90;width: 50%;">Slot Detail </h5>
                <hr style="margin: 10px 0; border: 1px #1BB295 solid; opacity: 100%;width: 50%">
                <ul id="bookingList" class="booking-list">
                <c:forEach items="${BookingSlots}" var="sche">
                    <c:if test="${param.viewID == sche.booking.id }">
                        <li class="booking-item">  Slot ${sche.schedule.slot.id} - Day ${sche.schedule.date}</li>
                    </c:if>

                </c:forEach>
                </ul>
            </div>
        </c:if>
        <div id="myModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content-time" style="width: 60%">
                <span class="close" style="margin-left: 750px">&times;</span>
                <!-- Form để gửi yêu cầu POST -->
                <h4 style="color:#07AD90;">Set free day</h4>

                <div class="container-card">
                    <div class="form-container">
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="selectWeek" value="1" checked>
                            <label class="form-check-label">
                                One Week
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="selectWeek" value="2">
                            <label class="form-check-label">
                                Two Week
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="selectWeek" value="3">
                            <label class="form-check-label">
                                Three Week
                            </label>
                        </div>
                        <div class="form-check">
                            <input class="form-check-input" type="radio" name="selectWeek" value="4">
                            <label class="form-check-label">
                                Four Week
                            </label>
                        </div>


                        <span>Select Day Of Week </span>
                        <select id="selectDOW" class="select-container">
                            <option value="Monday">Monday</option>
                            <option value="Tuesday">Tuesday</option>
                            <option value="Wednesday">Wednesday</option>
                            <option value="Thursday">Thursday</option>
                            <option value="Friday">Friday</option>
                            <option value="Saturday">Saturday</option>
                            <option value="Sunday">Sunday</option>
                        </select><br/>
                        <span>Select Slot:</span>
                        <div class="select-container">
                            <input type="checkbox" name="selectSlot" value="1"> Slot 1 (08:00 - 10:00)
                        </div>
                        <div class="select-container">
                            <input type="checkbox" name="selectSlot" value="2"> Slot 2 (10:00 - 12:00)
                        </div>
                        <div class="select-container">
                            <input type="checkbox" name="selectSlot" value="3"> Slot 3 (13:00 - 15:00)
                        </div>
                        <div class="select-container">
                            <input type="checkbox" name="selectSlot" value="4"> Slot 4 (15:00 - 17:00)
                        </div>
                        <div class="select-container">
                            <input type="checkbox" name="selectSlot" value="5"> Slot 5 (17:00 - 19:00)
                        </div>
                        <div class="select-container">
                            <input type="checkbox" name="selectSlot" value="6"> Slot 6 (19:00 - 21:00)
                        </div>
                        <div class="select-container">
                            <input type="checkbox" name="selectSlot" value="7"> Slot 7 (21:00 - 23:00)
                        </div>
                        <br/>
                        <button type="button" class="submit-button2"
                                onclick="addListBooks()">Add Slot
                        </button>

                    </div>
                    <div class="filter-box ">
                        <div>List of book
                            <div id="showCount" class="count-container"></div>
                        </div>
                        <div id="checkboxContainer"></div>

                    </div>
                    <div><input type="button" value="SAVE" class="submit-button"
                                data-receive="${sessionScope.numberUpdateSuccess}" id="btnSetFreeTime"
                                onclick="sendSetTimeData()">
                    </div>
                </div>

            </div>
        </div>

        <div id="infoModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content" style="width: 40%">
                <span class="closeInfo">&times;</span>
                <h4 style="color: #07AD90" >Tutorial Use </h4>
                <p><i class="fa-solid fa-envelope fa-xl"></i> is waiting for decision with booking</p>
                <p> <i class="fa-solid fa-frog fa-xl" style="color: #02b23c;"></i>  is present slot</p>
                <p><i class="fa-solid fa-face-sad-tear fa-xl" style="color: #f25452;"></i> is absent slot</p>
                <p><i class="fa-solid fa-square fa-xl" style="color: #63E6BE;"></i> is slot you set free</p>
                <p><i class="fa-solid fa-square fa-xl" style="color: #FFD43B;"></i>is slot waiting for decision with booking</p>
                <p><i class="fa-solid fa-square fa-xl" style="color: #ed1b0c;"></i>is slot work with mentee</p>
                <p><i class="fa-solid fa-plus fa-flip-vertical fa-lg" style="color: #b3dfd2;"></i> is allow you set slot free when click on</p>
            </div>
        </div>


    </div>


    <div class="right-side">
        <h1 style="color: #07AD90" class="text-center">Time table <i class="fa-solid fa-circle-info fa-2xs" id="myBtn2"></i> </h1>
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
                        <div style="display: flex; text-align: center">Slot${t.id}<br/>
                                ${t.start_at}- ${t.end_at}
                        </div>
                    </td>

                    <c:forEach items="${weeks}" var="week">
                        <c:set var="countCheck" value="${count = 0}"></c:set>
                        <c:set var="foundBusy" value="false"></c:set>
                        <c:set var="icon" value="false"></c:set>
                        <c:set var="waitingIcon" value="false"></c:set>
                        <td>
                            <c:forEach items="${schedules}" var="sche">
                                <c:if test="${sche.schedule.date == week.convertStringToDateByDay(week.dayOfMonth) && sche.schedule.slot.id == t.id}">
                                    <c:if test="${sche.status.id == 11}">
                                        <c:set var="countCheck" value="${count = 1}"></c:set>
                                        <c:set var="foundBusy" value="true"></c:set>
                                        <c:set var="nameSkill" value="${sche.booking.level_skills.skill.name}"></c:set>
                                        <c:set var="avatarSkill"
                                               value="${sche.booking.level_skills.skill.src_icon}"></c:set>
                                        <c:set var="nameType" value="${sche.booking.level_skills.level.name}"></c:set>

                                    </c:if>
                                    <c:if test="${sche.status.id == 3}">
                                        <c:set var="countCheck" value="${count = 1}"></c:set>
                                        <c:set var="nameSkill" value="${sche.booking.level_skills.skill.name}"></c:set>
                                        <c:set var="avatarSkill"
                                               value="${sche.booking.level_skills.skill.src_icon}"></c:set>
                                        <c:set var="nameType" value="${sche.booking.level_skills.level. name}"></c:set>
                                        <c:if test="${sche.attend}">
                                            <c:set var="present" value="present"></c:set>
                                            <c:set var="icon" value="true"></c:set>
                                        </c:if>
                                        <c:if test="${!sche.attend}">
                                            <c:set var="present" value="absent"></c:set>
                                            <c:set var="icon" value="true"></c:set>
                                        </c:if>
                                    </c:if>
                                    <c:if test="${(sche.status.id == 0 || sche.status.id==2) && !foundBusy}">
                                        <c:set var="countCheck" value="${count = 2}"></c:set>
                                    </c:if>
                                    <c:if test="${(sche.status.id == 1 ) && !foundBusy}">
                                        <c:if test="${param.viewID == sche.booking.id }">
                                            <c:set var="waitingIcon" value="true"></c:set>
                                        </c:if>
                                        <c:set var="countCheck" value="${count = 3}"></c:set>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${countCheck == 1 }">

                                    <div class="notes-container">
                                        <i class="pin"></i>
                                        <blockquote class="notes red">
                                            <img width="30px" src="${pageContext.request.contextPath}/${avatarSkill}" style="margin-bottom: 10px;">


                                            <button id="button-schedule-td1" class="info-link"
                                                    data-book-id="${week.dayOfMonth}_${t.id}"
                                                    data-today-id="${week.dayOfMonth}"
                                                    style="background: transparent" onclick="informationMentee()">
                                                    <%--                                                    ${nameSkill}--%>
                                                    ${nameType}
                                            </button>
                                            <c:if test="${present.equals('present') && icon}">
                                                <i class="fa-solid fa-frog fa-xl" style="color: #02b23c; margin-top:-20px;margin-left: 40px"></i>
                                            </c:if>
                                            <c:if test="${present.equals('absent') && icon }">
                                                <i class="fa-solid fa-face-sad-tear fa-xl" style="color: #f25452; margin-top:-20px;margin-left: 40px"></i>

                                            </c:if>
                                        </blockquote>
                                    </div>

                                </c:when>
                                <c:when test="${countCheck == 2 }">

                                    <div class="notes-container" onclick="">
                                        <i class="pin"></i>
                                        <blockquote class="notes green">

                                            <button id="button-schedule-td3" class="button-schedule-td3"
                                                    data-schedule-id="${week.dayOfMonth}_${t.id}"
                                                    data-today-id="${week.dayOfMonth}"
                                                    style=" background: transparent; "
                                                    onclick="deleteFreeDay()">

                                            </button>
                                        </blockquote>
                                    </div>
                                </c:when>
                                <c:when test="${countCheck == 3 }">

                                    <div class="notes-container">
                                        <i class="pin"></i>
                                        <blockquote class="notes yellow">
                                            Waiting...

                                            <c:if test="${waitingIcon}">
                                                <div style="    margin-top: 20px;
                                            margin-left: 10px;">
                                                    <i class="fa-solid fa-envelope fa-xl"></i>
                                                </div>
                                            </c:if>


                                        </blockquote>
                                    </div>
                                </c:when>
                                <c:otherwise>

                                    <button id="button-schedule-td" style="background: white" class="button-schedule-td"
                                            data-schedule-id="${week.dayOfMonth}_${t.id}"
                                            data-today-id="${week.dayOfMonth}"
                                            data-set-error="${sessionScope.AddSlotError}"
                                            onclick="setFreeDay()">
                                        <i class="fa-solid fa-plus fa-flip-vertical fa-lg" style="color: #b3dfd2;"></i>
                                    </button>
                                </c:otherwise>

                            </c:choose>


                        </td>

                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>
            <%--lay ra session cho delete--%>
        <input type="hidden" id="deleteButtonSession" value="${sessionScope.DeleteSlotError}">



        <div id="tdModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content2">
                <span class="close">&times;</span>
                <!-- Your modal content here -->
                <h4 style="color: #07AD90">Schedule details</h4>
                <span style="color: red; font-size: 12px"> (Note: If the lesson has been finished,please confirm slot)</span>
                <div class="d-flex justify-content-around">
                    <div>
                        <div class="info-card">
                            <ul>
                                <li style="list-style-type: none">Name : ${bookInfo[0].booking.mentee.account.name}</li>
                                <li style="list-style-type: none">Mail : ${bookInfo[0].booking.mentee.account.email}</li>
                                <li style="list-style-type: none">Phone : ${bookInfo[0].booking.mentee.account.phone}</li>
                            </ul>
                        </div>
                        <div class="info-card">

                            <ul>

                                <li style="list-style-type: none">Skill name : <img width="30px"
                                                                                    src="${pageContext.request.contextPath}/${bookInfo[0].booking.level_skills.skill.src_icon}">
                                    ${bookInfo[0].booking.level_skills.skill.name}</li>
                                <li style="list-style-type: none">Skill level
                                    : ${bookInfo[0].booking.level_skills.level.name}</li>
                            </ul>
                            <ul>
                                <li style="list-style-type: none">Day:${bookInfo[0].schedule.date} </li>
                                <li style="list-style-type: none">Time
                                    : ${bookInfo[0].schedule.slot.start_at}-${bookInfo[0].schedule.slot.end_at}</li>
                                <li style="list-style-type: none">Slot Booked : ${bookInfo[0].schedule.slot.id}</li>
                            </ul>
                        </div>
                    </div>

                    <div class="info-card1" >

                        <ul>
                            <li style="list-style-type: none">Created date : ${bookInfo[0].booking.date}</li>
                            <li style="list-style-type: none">Total amount request : ${bookInfo[0].booking.amount}</li>


                            <li style="list-style-type: none">Status payment :
                                <span style="color: #f8ce0b"> <c:if
                                        test="${bookInfo[0].booking.status.id == 11 || bookInfo[0].booking.status.id == 3 }">Payment Not Confirm Yet</c:if> </span>
                                <span style="color: #28a745"><c:if
                                        test="${bookInfo[0].booking.status.id == 13 }">Payment Confirmed</c:if></span>
                            </li>
                        </ul>
                        <ul>
                            <li style="list-style-type: none">Confirm slot:</li>
                            <c:if test="${bookInfo[0].status.id == 3 }">
                                <c:if test="${bookInfo[0].attend ==  true }">
                                    <li style="pointer-events: none;list-style-type: none;display: inline">
                                        <input type="button" value="Present" id="confirmBtn2" style=" background: #1BB295">
                                    </li>
                                </c:if>
                                <c:if test="${bookInfo[0].attend ==  false }">
                                    <li style="pointer-events: none;list-style-type: none;display: inline">
                                        <input type="button" value="Absent" id="absentBtn2" style=" background: #ff2222">
                                    </li>
                                </c:if>

                                <li style="list-style-type: none">(Request was sent, money will unlock after finish all
                                    slot)
                                </li>

                            </c:if>
                            <c:if test="${bookInfo[0].status.id == 11 }">
                                <li style="list-style-type: none; display: inline">
                                    <input type="button" value="Present" id="confirmBtn"
                                           data-id=" ${bookInfo[0].id}" data-time="${bookInfo[0].schedule.date}_${bookInfo[0].schedule.slot.start_at}"
                                           onclick="confirmSlot()">
                                </li>
                                <li style="list-style-type: none;display: inline">
                                    <input type="button" value="Absent" id="absentBtn"
                                           data-id=" ${bookInfo[0].id}" data-time="${bookInfo[0].schedule.date}_${bookInfo[0].schedule.slot.start_at}"
                                           onclick="absentSlot()">
                                </li>
                            </c:if>
                        </ul>
                    </div>
                </div>

            </div>


        </div>
    </div>

</div>

<script>
    const openMyRequestWork = () => {
        window.location.href = "schedule/manage"
    }
    function updateURL() {
        var todayElement = document.getElementById('today');
        var selectedDate = todayElement.value;
        var viewID = todayElement.getAttribute('data-viewID');
        var currentURL = window.location.href;
        var baseURL = currentURL.split('?')[0];
        var newParams = "today=" + selectedDate;

        if (viewID) {
            newParams += "&viewID=" + viewID;
        }

        var newURL = baseURL + "?" + newParams;
        window.location.href = newURL;
    }

    function removeSession() {
        var xhr = new XMLHttpRequest();
        xhr.open("GET", "schedule/deleteSession", true);
        xhr.send();
    }

    // Lấy modal mới cho thông tin
    var infoModal = document.getElementById("infoModal");
    // Lấy nút mở modal mới
    var btn2 = document.getElementById("myBtn2");
    // Lấy phần tử <span> (nút đóng) để đóng modal mới
    var spanInfo = document.getElementsByClassName("closeInfo")[0];
    // Khi người dùng nhấp vào nút, mở modal mới
    btn2.onclick = function () {
        infoModal.style.display = "block";
        return false; // Ngăn chặn gửi form mặc định
    }
    // Khi người dùng nhấp vào nút đóng (x), đóng modal mới
    spanInfo.onclick = function () {
        infoModal.style.display = "none";
    }
    var spans = document.getElementsByClassName("close");
    for (var i = 0; i < spans.length; i++) {
        spans[i].onclick = function () {
            var modal = this.parentElement.parentElement;
            modal.style.display = "none";
        }
    }
    const openSetTime = () => {
        var modal = document.getElementById("myModal");
        var span = document.getElementsByClassName("close")[0];
        var btnOnclick = document.getElementById("myBtn");
        btnOnclick.onclick = function (event) {

            modal.style.display = "block";
            return false;

        };
    }

    // set free day
    const setFreeDay = () => {
        var tdLinks = document.getElementsByClassName("button-schedule-td");
        var newURL = "";
        // Loop through each element and set the click event handler
        for (let i = 0; i < tdLinks.length; i++) {
            tdLinks[i].onclick = function (event) {
                event.preventDefault();
                Swal.fire({
                    title: "Are you want to set a free day?",
                    text: "",
                    icon: "question",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Yes, I set it!"
                }).then((result) => {
                    if (result.isConfirmed) {
                        var slotID = this.getAttribute('data-schedule-id');
                        var today = this.getAttribute('data-today-id');
                        localStorage.setItem('isSuccess', 'yes');

                        newURL = "schedule/insert?slotID=" + slotID + "&today=" + today;


                        // Redirect to the new URL
                        window.location.href = newURL;
                    }
                });
            };

        }
    };
    var error = document.getElementById('button-schedule-td').getAttribute('data-set-error');
    if (error) {
        localStorage.removeItem('isSuccess');
        localStorage.setItem('failToSet', error);
    }


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
    //delete free day
    const deleteFreeDay = () => {
        var tdLinks = document.getElementsByClassName("button-schedule-td3");
        var newURL = "";
        // Loop through each element and set the click event handler
        for (let i = 0; i < tdLinks.length; i++) {
            tdLinks[i].onclick = function (event) {
                event.preventDefault();
                Swal.fire({
                    title: "Are you want to DELETE a free day?",
                    text: "",
                    icon: "warning",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Yes, I DELETE it!"
                }).then((result) => {
                    if (result.isConfirmed) {
                        var slotID = this.getAttribute('data-schedule-id');
                        var today = this.getAttribute('data-today-id');
                        newURL = "schedule/delete?slotID=" + slotID + "&today=" + today;
                        localStorage.setItem('isDelete', 'yes');

                        // Redirect to the new URL
                        window.location.href = newURL;
                    }
                });
            };

        }
        ;
    }
    var errorDelete = document.getElementById('deleteButtonSession').value;
    if (errorDelete) {
        localStorage.removeItem('isDelete');
        localStorage.setItem('failToDelete', errorDelete);
    }
    var numberFreeTime = document.getElementById('btnSetFreeTime').getAttribute('data-receive');
    if (numberFreeTime) {
        localStorage.setItem('numberFreeTime', numberFreeTime);
    }

    const informationMentee = () => {
        var links = document.getElementsByClassName("info-link");
        for (var i = 0; i < links.length; i++) {
            links[i].onclick = function (event) {
                event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ a

                // Lấy giá trị data-book-id từ phần tử a được click
                var bookID = this.getAttribute('data-book-id');
                var today = this.getAttribute('data-today-id');
                // Tạo URL mới dựa trên bookID
                var newURL = "schedule?dayID=" + bookID + "&today=" + today;

                // Lưu trạng thái của modal và ID của modal vào localStorage
                localStorage.setItem('modalToOpen', 'tdModal');

                // Lưu bookID vào localStorage để sử dụng sau khi trang được tải lại (nếu cần)
                localStorage.setItem('bookID', bookID);

                // Chuyển hướng đến URL mới
                window.location.href = newURL;
            };
        }


    }
    const confirmSlot = () => {
        var dataTime = document.getElementById("confirmBtn").getAttribute('data-time');
        var date = dataTime.split("_")[0];
        var time = dataTime.split("_")[1];
        var currentDate = new Date();
        var dateTimeString= date + " " + time;
        var dateTimeValue = new Date(dateTimeString);
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
                    var ID = document.getElementById("confirmBtn").getAttribute('data-id');
                    var newURL = '/Frog/mentor/schedule/confirm?ID=' + ID + '&option=present';
                    localStorage.setItem('isConfirmSlot', 'yes');
                    // Redirect to the new URL
                    window.location.href = newURL;
                }
            });
        }
    };
    const absentSlot = () => {
        var dataTime = document.getElementById("absentBtn").getAttribute('data-time');
        var date = dataTime.split("_")[0];
        var time = dataTime.split("_")[1];
        var currentDate = new Date();
        var dateTimeString= date + " " + time;
        var dateTimeValue = new Date(dateTimeString);
        if (currentDate < dateTimeValue) {
            Swal.fire({
                icon: "error",
                title: "Oops...",
                text: "Slot has not occurred yet.",
            });
        }
        else{
            var btnOnclick = document.getElementById("absentBtn");
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
                        newURL = '/Frog/mentor/schedule/confirm?ID=' + ID + '&option=absent';
                        localStorage.setItem('isAbsent', 'yes');
                        // Redirect to the new URL
                        window.location.href = newURL;
                    }
                });
            };
        }

    }
    let counter = 0;
    let countSlots = 0;
    const addListBooks = () => {
        if (counter >= 7) {
            Toast.fire({
                icon: "error",
                title: "Add list limit in 7 times"
            });
            return;
        }
        var dayOfWeek = document.getElementById('selectDOW').value;
        var slots = document.getElementsByName('selectSlot');
        var container = document.getElementById('checkboxContainer');
        var showCount = document.getElementById('showCount');
        for (var i = 0; i < slots.length; i++) {
            if (slots[i].checked) {
                var checkbox = document.createElement('input');
                checkbox.type = 'checkbox';
                checkbox.value = dayOfWeek + "_" + slots[i].value;
                checkbox.name = 'setTimeData';
                checkbox.checked = true;
                dayOfWeek = dayOfWeek + "_" + slots[i].value;
                countSlots++;
                checkbox.addEventListener('change', function () {
                    if (!this.checked) {
                        //---------- giam count
                        counter--;
                        var down = this.value.split("_");
                        for (let i = 1; i < down.length; i++) {
                            countSlots = countSlots - 1;
                        }
                        var label = document.createElement('label');
                        label.appendChild(document.createTextNode(dayOfWeek));
                        var countElement = document.createElement("span");
                        countElement.textContent = "total booked: " + countSlots;
                        var showCount = document.getElementById("showCount");
                        showCount.innerHTML = "";
                        showCount.appendChild(countElement);
                        //-----------------

                        var parent = this.parentNode;
                        parent.removeChild(this.nextSibling); // Xóa label
                        parent.removeChild(this.nextSibling); // Xóa <br>
                        parent.removeChild(this); // Xóa checkbox
                    }
                });
            }
        }
        var label = document.createElement('label');
        label.appendChild(document.createTextNode(dayOfWeek));
        var countElement = document.createElement("span");
        countElement.textContent = "total booked: " + countSlots;
        var showCount = document.getElementById("showCount");
        showCount.innerHTML = "";
        showCount.appendChild(countElement);
        container.appendChild(checkbox);
        container.appendChild(label);
        container.appendChild(document.createElement('br'));

        counter++;

        for (var i = 0; i < slots.length; i++) {
            slots[i].checked = false;
        }

    }
    const sendSetTimeData = () => {
        var weeks = document.getElementsByName('selectWeek');
        var dataSlots = document.getElementsByName('setTimeData');
        let selectedWeek;
        for (const weekElement of weeks) {
            if (weekElement.checked) {
                selectedWeek = weekElement.value;
                break;
            }
        }
        if (dataSlots.length == 0) {
            Toast.fire({
                icon: "error",
                title: "Must add slot to save"
            });
            return;
        }
        let selectedData = [];
        for (const dataElement of dataSlots) {
            if (dataElement.checked) {
                selectedData.push(dataElement.value);
            }
        }

        window.location.href = 'schedule/set?week=' + selectedWeek + '&data=' + selectedData.join(",")
    }
    // set free day
    window.onload = function () {

        var isSuccess = localStorage.getItem('isSuccess');
        // Nếu modal được lưu là mở, mở modal
        var isDelete = localStorage.getItem('isDelete');
        var update_status = localStorage.getItem('update_status');
        var reject_status = localStorage.getItem('reject_status');
        var modalToOpen = localStorage.getItem('modalToOpen');
        var isConfirmSlot = localStorage.getItem('isConfirmSlot');
        var isAbsent = localStorage.getItem('isAbsent');
        var failToSet = localStorage.getItem('failToSet');
        var failToDelete = localStorage.getItem('failToDelete');
        var numberFreeTime = localStorage.getItem('numberFreeTime');
        if (numberFreeTime) {
            Toast.fire({
                icon: "success",
                title: numberFreeTime + " slots added "
            });
            localStorage.removeItem('numberFreeTime');
        }
        if (failToSet) {
            Toast.fire({
                icon: "error",
                title: failToSet
            });
            localStorage.removeItem('failToSet');
        }
        if (failToDelete) {
            Toast.fire({
                icon: "error",
                title: failToDelete
            });
            localStorage.removeItem('failToDelete');
        }
        if (isSuccess) {
            Toast.fire({
                icon: "success",
                title: "set day in successfully"
            });
            localStorage.removeItem('isSuccess');
        }
        if (isDelete) {
            Toast.fire({
                icon: "success",
                title: "Delete in successfully"
            });
            localStorage.removeItem('isDelete');
        }
        if (update_status) {
            let icon;
            if (update_status === 'schedule confirmed') {
                icon = "success";
            } else {
                icon = "error";
            }

            Toast.fire({
                icon: icon,
                title: update_status
            });

            localStorage.removeItem('update_status');
        }
        if (reject_status) {
            let icon;
            if (reject_status === 'schedule rejected') {
                icon = "success";
            } else {
                icon = "error";
            }

            Toast.fire({
                icon: icon,
                title: reject_status
            });

            localStorage.removeItem('reject_status');
        }
        if (modalToOpen) {
            var modal = document.getElementById(modalToOpen);
            modal.style.display = "block";

            // Xóa trạng thái modal ra khỏi localStorage (để không mở lại khi tải trang lần tiếp theo)
            localStorage.removeItem('modalToOpen');

        }
        if (isConfirmSlot) {
            Toast.fire({
                icon: "success",
                title: "Take present in successfully"
            });
            localStorage.removeItem('isConfirmSlot');
        }
        if (isAbsent) {
            Toast.fire({
                icon: "success",
                title: "Take absent in successfully"
            });
            localStorage.removeItem('isAbsent');
        }
        removeSession();
    };
    const history_request = () => {
        window.location.href = 'schedule/history';
    }
    const invited_request = () => {
        window.location.href = 'schedule/invite';
    }

</script>
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
</div>
</body>
</html>
