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

    </style>
</head>
<body>
<jsp:include page="/view/common/header.jsp"></jsp:include>
<div class="container">
    <div>
        <form action="schedule" method="GET" id="getForm">
            <div>
                <label for="today">Select Date:</label>
                <input type="date" class="styled-date" name="today" id="today" value="${param.today}"
                       onchange="document.getElementById('getForm').submit()">
            </div>
        </form>

        <button id="myBtn">Set Time</button>
        <br/>
        <!-- Nút mở modal mới -->
        <button id="myBtn2">View Request</button>
        <button id="myBtn3" onclick="history_request()">History Request</button>

        <div id="infoModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content" style="width: 80%">
                <span class="closeInfo">&times;</span>
                <p>Request List </p>
                <table class="request-list" style="width: 100%">
                    <tr>
                        <td>Number</td>
                        <td>Create Date</td>
                        <td>Amount</td>
                        <td>Description</td>
                        <td>Skill Name</td>
                        <td>Skill Level</td>
                        <td>Start Date</td>
                        <td>End Date</td>
                        <td>Name</td>
                        <td>Status</td>
                    </tr>
                    <c:forEach items="${bookings}" var="book">
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
                            <td><c:if test="${book.status.id ==1 }">
                                <button name="accept" value="accept">
                                    <a href="schedule/update?bookingID=${book.id}&&option=true"
                                       class="button" onclick="updateStatus('${sessionScope.updateStatus}')">Accept</a>
                                </button>
                            </c:if>
                                <c:if test="${book.status.id ==1 }">
                                    <button name="reject" value="reject">
                                        <a href="schedule/update?bookingID=${book.id}&&option=false"
                                           class="button"
                                           onclick="rejectStatus('${sessionScope.rejectStatus}')">Reject</a></button>
                                    </button>
                                </c:if>
                            </td>
                            <c:set var="count" value="${count= count +1}"></c:set>
                        </tr>
                    </c:forEach>
                </table>

            </div>
        </div>


    </div>


    <div>
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
                        <div style="display: flex; text-align: center">Slot${t.id}
                        </div>
                    </td>

                    <c:forEach items="${weeks}" var="week">
                        <c:set var="countCheck" value="${count = 0}"></c:set>
                        <c:set var="foundBusy" value="false"></c:set>
                        <c:set var="icon" value="false"></c:set>

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
                                        <c:set var="countCheck" value="${count = 3}"></c:set>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${countCheck == 1 }">

                                    <div class="notes-container">
                                        <i class="pin"></i>
                                        <blockquote class="notes red">
                                            <img width="30px" src="${pageContext.request.contextPath}${avatarSkill}">
                                            <c:if test="${present.equals('present') && icon}">
                                                <i class="fa-solid fa-frog fa-xl" style="color: #63E6BE;"></i>
                                            </c:if>
                                            <c:if test="${present.equals('absent') && icon }">
                                                <i class="fa-solid fa-face-sad-tear fa-xl" style="color: #FFFFFF;"></i>

                                            </c:if>

                                            <button id="button-schedule-td1" class="info-link"
                                                    data-book-id="${week.dayOfMonth}_${t.id}"
                                                    data-today-id="${week.dayOfMonth}"
                                                    style="background: transparent" onclick="informationMentee()">
                                                    ${nameSkill}
                                                    ${nameType}
                                            </button>

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
                                                    style=" background: transparent; " onclick="deleteFreeDay()">

                                            </button>

                                        </blockquote>
                                    </div>
                                </c:when>
                                <c:when test="${countCheck == 3 }">

                                    <div class="notes-container">
                                        <i class="pin"></i>
                                        <blockquote class="notes yellow">
                                                <%--                                            <button id="button-schedule-td4" class="button-schedule-td2"  data-schedule-id="${week.dayOfMonth}_${t.id}" style="background-color: yellow">--%>

                                                <%--                                            </button>--%>
                                            Waiting...
                                        </blockquote>
                                    </div>
                                </c:when>
                                <c:otherwise>

                                    <button id="button-schedule-td" style="background: white" class="button-schedule-td"
                                            data-schedule-id="${week.dayOfMonth}_${t.id}"
                                            data-today-id="${week.dayOfMonth}"
                                            onclick="setFreeDay()">

                                    </button>
                                </c:otherwise>

                            </c:choose>


                        </td>

                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div id="tdModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content2" style="width: 70%">
                <span class="close">&times;</span>
                <!-- Your modal content here -->
                <p style=" margin-left: -150px;">Schedule details</p>
                <p> (Note: If the lesson has been finished,please confirm slot)</p>
                <div style="display: block;order: 1;margin-left: -550px;">

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
                                                                                src="${pageContext.request.contextPath}${bookInfo[0].booking.level_skills.skill.src_icon}">
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


                    <div class="info-card1" style="margin-left: 850px; margin-top: -370px ">

                        <ul>
                            <li style="list-style-type: none">Created date :  ${bookInfo[0].booking.date}</li>
                            <li style="list-style-type: none">Total amount request : ${bookInfo[0].booking.amount}</li>


                            <li style="list-style-type: none">Status payment :
                                <span style="color: #f8ce0b"> <c:if
                                        test="${bookInfo[0].booking.status.id == 11 }">Payment Not Confirm Yet</c:if> </span>
                                <span style="color: #28a745"><c:if
                                        test="${bookInfo[0].booking.status.id == 3 }">Payment Confirmed</c:if></span>
                            </li>
                        </ul>
                        <ul>
                            <li style="list-style-type: none">Confirm slot:</li>
                            <c:if test="${bookInfo[0].status.id == 3 }">
                                <li style="pointer-events: none;list-style-type: none;display: inline">
                                    <input type="button" value="Present" id="confirmBtn2" style=" background: #919aa3">
                                </li>
                                <li style="pointer-events: none;list-style-type: none;display: inline">
                                    <input type="button" value="Absent" id="absentBtn2" style=" background: #919aa3">
                                </li>
                                <li style="list-style-type: none">(Request was sent, money will unlock after finish all slot)</li>

                            </c:if>
                            <c:if test="${bookInfo[0].status.id == 11 }">
                                <li style="list-style-type: none; display: inline">
                                    <input type="button" value="Present" id="confirmBtn"
                                           data-id=" ${bookInfo[0].id}"
                                    onclick="confirmSlot()" >
                                </li>
                                <li style="list-style-type: none;display: inline" >
                                    <input type="button" value="Absent" id="absentBtn"
                                           data-id=" ${bookInfo[0].id}"
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
                        newURL = "schedule?slotID=" +slotID + "&today=" + today;
                        localStorage.setItem('isSuccess', 'yes');

                        // Redirect to the new URL
                        window.location.href = newURL;
                    }
                });
            };

        }

        // Show success message


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
                        newURL = "schedule/delete?slotID=" +slotID + "&today=" + today;
                        localStorage.setItem('isDelete', 'yes');

                        // Redirect to the new URL
                        window.location.href = newURL;
                    }
                });
            };

        }
        ;
    }
    const updateStatus = (details) => {
        localStorage.setItem('update_status', details);

    }
    const rejectStatus = (details) => {
        localStorage.setItem('reject_status', details);

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
        var btnOnclick = document.getElementById("confirmBtn");
        var newURL = "";
        btnOnclick.onclick = function (event) {
                event.preventDefault();
                Swal.fire({
                    title: "Are you want to CONFIRM a slot ?",
                    text: "Ensure that the slot has happened",
                    icon: "question",
                    showCancelButton: true,
                    confirmButtonColor: "#3085d6",
                    cancelButtonColor: "#d33",
                    confirmButtonText: "Yes, I confirm it!"
                }).then((result) => {
                    if (result.isConfirmed) {
                        var ID = this.getAttribute('data-id');
                        newURL = 'schedule/confirm?ID='+ID+'&option=present';
                        localStorage.setItem('isConfirmSlot','yes');
                        // Redirect to the new URL
                        window.location.href = newURL;
                    }
                });
            };
    }
    const absentSlot = () => {
        var btnOnclick = document.getElementById("absentBtn");
        var newURL = "";
        btnOnclick.onclick = function (event) {
            event.preventDefault();
            Swal.fire({
                title: "is the mentee ABSENT a slot ?",
                text: "Ensure that the slot has happened",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, absent !"
            }).then((result) => {
                if (result.isConfirmed) {
                    var ID = this.getAttribute('data-id');
                    newURL = 'schedule/confirm?ID='+ID+'&option=absent';
                    localStorage.setItem('isAbsent','yes');
                    // Redirect to the new URL
                    window.location.href = newURL;
                }
            });
        };
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
    };
    const history_request = () =>{
        window.location.href = 'schedule/history';
    }

</script>

</div>
</body>
</html>
