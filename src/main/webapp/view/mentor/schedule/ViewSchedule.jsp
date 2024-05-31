<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 5/16/2024
  Time: 10:12 AM
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

</head>
<body>
<jsp:include page="/view/common/header.jsp"></jsp:include>
<c:if test="${sessionScope.existError != null}">
    <div class="error-message">
            ${sessionScope.existError}
    </div>
</c:if>


<div class="container">


    <div class="time-settings">
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

        <!-- The Modal -->
        <div id="myModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content" style="width: 70%">
                <span class="close">&times;</span>
                <!-- Form để gửi yêu cầu POST -->
                <p style="font-weight: bold;">SET FREE DAY</p>
                <form action="schedule" method="POST" id="postForm" onsubmit="return validateForm()">
                    <div>
                        Choose date: <input type="date" name="setDate" id="setDate"><br/>
                        Set Time Start: <input type="time" name="from" id="from"><br/>
                        Set Time End: <input type="time" name="to" id="to"><br/>
                        <input type="submit" value="save"><br/>
                    </div>
                </form>
            </div>
        </div>

        <!-- The Modal for Info -->
        <div id="infoModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content" style="width: 90%">
                <span class="closeInfo">&times;</span>
                <p>Request List </p>
                <table class="request-list">
                    <tr>
                        <td>Number</td>
                        <td>Create Date</td>
                        <td>Amount</td>
                        <td>Date Waiting</td>
                        <td>Description</td>
                        <td>Skill Name</td>
                        <td>Skill Level</td>
                        <td>Description Skill</td>
                        <td>Start Date</td>
                        <td>End Date</td>
                        <td>Name</td>
                        <td>Mail</td>
                        <td>Status</td>
                    </tr>
                    <c:forEach items="${bookings}" var="book">
                        <tr>
                            <td>${count}</td>
                            <td>${book.booking.date}</td>
                            <td> ${book.booking.amount}</td>
                            <td>   ${book.booking.startDate} - ${book.booking.endDate}</td>
                            <td> ${book.booking.description} </td>
                            <td> ${book.skill.skill.name}</td>
                            <td> ${book.skill.level.name}</td>
                            <td> ${book.skill.description}</td>
                            <td>${book.schedule.dateStart}</td>
                            <td>${book.schedule.dateEnd}</td>
                            <td>${book.account.name}</td>
                            <td>${book.account.email}</td>
                            <td><c:if test="${book.status.id ==1 }">
                                <button name="accept" value="accept">
                                    <a href="schedule/update?start=${book.schedule.dateStart}&&end=${book.schedule.dateEnd}&&id=${requestScope.mentorID}&&option=true&&menteeID=${book.account.id}"
                                       class="button">Accept</a></button>
                            </c:if>

                                <c:if test="${book.status.id ==1 }">
                                    <button name="reject" value="reject"><a
                                            href="schedule/update?start=${book.schedule.dateStart}&&end=${book.schedule.dateEnd}&&id=${requestScope.mentorID}&&option=false&&menteeID=${book.account.id}"
                                            class="button"> Reject</a>
                                    </button>
                                </c:if>
                                <c:if test="${book.status.id !=1 }">${book.status.type}</c:if>
                            </td>

                            <c:set var="count" value="${count= count +1}"></c:set>
                        </tr>
                    </c:forEach>
                </table>

            </div>
        </div>
    </div>
    <div class="table-settings">
        <table border="1px">
            <tr>
                <td></td>
                <c:forEach items="${weeks}" var="week">
                    <td>${week.dayOfWeek}<br/>
                            ${week.dayOfMonth}
                    </td>
                </c:forEach>
            </tr>
            <c:forEach items="${slots}" var="t">
                <tr>
                    <td>
                        <div style="display: flex; text-align: center">Slot${t.id}
                        </div>
                    </td>

                    <c:forEach items="${weeks}" var="week">
                        <c:set var="countCheck" value="${count = 0}"></c:set>
                        <c:set var="foundBusy" value="false"></c:set>
                        <td>
                            <c:forEach items="${schedules}" var="sche">
                                <c:if test="${sche.schedule.date == week.convertStringToDateByDay(week.dayOfMonth) && sche.schedule.slot.id == t.id}">
                                    <c:if test="${sche.status.id == 11}">
                                    <a href="#" class="info-link" data-modal-id="tdModal" style="text-decoration: none"
                                       data-book-id="${week.dayOfMonth}">
                                    </a>
                                        <c:set var="countCheck" value="${count = 1}"></c:set>
                                        <c:set var="foundBusy" value="true"></c:set>
                                    </c:if>
                                    <c:if test="${(sche.status.id == 0 || sche.status.id==2) && !foundBusy}">
                                        <c:set var="countCheck" value="${count = 2}"></c:set>
                                    </c:if>
                                </c:if>
                            </c:forEach>
                            <c:choose>
                                <c:when test="${countCheck == 1 }">
                                    <button id="button-schedule-td" class="info-link" data-book-id="${week.dayOfMonth}_${t.id}" style="background-color: red">

                                    </button>
                                </c:when>
                                <c:when test="${countCheck == 2 }">
                                    <button id="button-schedule-td" class="button-schedule-td2"  data-schedule-id="${week.dayOfMonth}_${t.id}" style="background-color: green">

                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button id="button-schedule-td" class="button-schedule-td"
                                            data-schedule-id="${week.dayOfMonth}_${t.id}">
                                    </button>
                                </c:otherwise>

                            </c:choose>


                        </td>

                    </c:forEach>
                </tr>
            </c:forEach>
        </table>

        <div id="tdModal" class="modal">
            <!-- Modal content -->
            <div class="modal-content" style="width: 90%">
                <span class="close">&times;</span>
                <!-- Your modal content here -->

                <p>Schedule details</p>
                <p>Schedule details</p>
                <table class="table-info">
                    <tr>
                        <td>
                            Number
                        </td>
                        <td>Name</td>
                        <td>Mail</td>
                        <td>Skill</td>
                        <td>Level</td>
                        <td>Start</td>
                        <td>End</td>
                        <td>URL</td>
                    </tr>
                    <c:set var="count" value="${count= 1}"></c:set>

                    <c:forEach items="${menteeInfo}" var="info">
                        <tr>
                            <td>${count}</td>
                            <td>${info.account.name}</td>
                            <td>${info.account.email}</td>
                            <td> ${info.skill.skill.name}</td>
                            <td> ${info.skill.level.name}</td>
                            <td>${info.schedule.dateStart}</td>
                            <td>${info.schedule.dateEnd}</td>
                            <td><a href=""></a></td>
                        </tr>
                        <c:set var="count" value="${count= count +1}"></c:set>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>

    <div id="tdDayModal" class="modal">
        <div class="modal-content" style="width: 40%">
            <span class="close">&times;</span>
            <div style="display: flex">
                <div><p>Do you want to set free day ?</p></div>
                <div><input type="button" value="Confirm" id="confirmButton">
                    <input type="button" value="Cancel"
                           onclick="document.getElementById('tdDayModal').style.display='none'"></div>
            </div>
        </div>
    </div>
    <div id="tdDayModal2" class="modal">
        <div class="modal-content" style="width: 40%">
            <span class="close">&times;</span>
            <div style="display: flex">
                <div><p>Do you want to DELETE this day ?</p></div>
                <div><input type="button" value="Confirm" id="confirmButton2">
                    <input type="button" value="Cancel"
                           onclick="document.getElementById('tdDayModal').style.display='none'"></div>
            </div>
        </div>
    </div>
</div>
<script>
    function validateForm() {
        var setDate = document.getElementById("setDate").value;
        var from = document.getElementById("from").value;
        var to = document.getElementById("to").value;
        if (setDate === "" || from === "" || to === "") {
            alert("Please fill in all fields.");
            return false;
        }
        var currentDate = new Date();
        var currentDateString = currentDate.toISOString().split('T')[0]; // Lấy ngày hiện tại

        if (setDate === currentDateString) {
            // Ngày được chọn là ngày hiện tại
            // Tiếp tục kiểm tra giờ
            var currentTime = currentDate.getHours() + ":" + currentDate.getMinutes();
            if (from <= currentTime || to <= currentTime) {
                // Thời gian đã qua
                alert("Please select a time in the future.");
                return false; // Ngăn chặn nộp form
            }
        } else if (setDate < currentDateString) {
            // Ngày đã qua
            alert("Please select a date in the future.");
            return false; // Ngăn chặn nộp form
        }

        // Nếu không có vấn đề gì, cho phép nộp form
        return true;
    }

    //-------------------------
    // set free day
    var newURL = "";
    var tdLinks = document.getElementsByClassName("button-schedule-td");
    for (let i = 0; i < tdLinks.length; i++) {
        tdLinks[i].onclick = function (event) {
            event.preventDefault();
            var tdDayModal = document.getElementById("tdDayModal");
            tdDayModal.style.display = "block";

            var slotID = this.getAttribute('data-schedule-id');
            newURL = "schedule?slotID=" + slotID;
        };
    }
    document.getElementById("confirmButton").onclick = function () {
        if (newURL) {
            window.location.href = newURL;
        }
    };
    // delete free day
    var newURL = "";
    var tdLinks = document.getElementsByClassName("button-schedule-td2");
    for (let i = 0; i < tdLinks.length; i++) {
        tdLinks[i].onclick = function (event) {
            event.preventDefault();
            var tdDayModal = document.getElementById("tdDayModal2");
            tdDayModal.style.display = "block";

            var slotID = this.getAttribute('data-schedule-id');
            newURL = "schedule/delete?slotID=" + slotID;
        };
    }
    document.getElementById("confirmButton2").onclick = function () {
        if (newURL) {
            window.location.href = newURL;
        }
    };
    //-------------------------
    // Lấy modal
    var modal = document.getElementById("myModal");
    // Lấy nút mở modal
    var btn = document.getElementById("myBtn");
    // Lấy phần tử <span> (nút đóng) để đóng modal
    var span = document.getElementsByClassName("close")[0];
    // Khi người dùng nhấp vào nút, mở modal
    btn.onclick = function () {
        modal.style.display = "block";
        return false; // Ngăn chặn gửi form mặc định
    }
    // Khi người dùng nhấp vào nút đóng (x), đóng modal
    span.onclick = function () {
        modal.style.display = "none";
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
    //----
    // Trước khi chuyển hướng trang
    var links = document.getElementsByClassName("info-link");
    for (var i = 0; i < links.length; i++) {
        links[i].onclick = function (event) {
            event.preventDefault(); // Ngăn chặn hành vi mặc định của thẻ a
            // Lấy giá trị data-book-id từ phần tử a được click
            var bookID = this.getAttribute('data-book-id');
            // Tạo URL mới dựa trên bookID
            var newURL = "schedule?dayID=" + bookID;
            // Lưu trạng thái của modal và ID của modal vào localStorage
            localStorage.setItem('modalToOpen', 'tdModal');
            // Lưu bookID vào localStorage để sử dụng sau khi trang được tải lại (nếu cần)
            localStorage.setItem('bookID', bookID);
            // Chuyển hướng đến URL mới
            window.location.href = newURL;
        };
    }
    // Sau khi trang được tải lại
    window.onload = function () {
        // Kiểm tra xem trạng thái của modal có được lưu trong localStorage không
        var modalToOpen = localStorage.getItem('modalToOpen');

        // Nếu modal được lưu là mở, mở modal
        if (modalToOpen) {
            var modal = document.getElementById(modalToOpen);
            modal.style.display = "block";
            // Xóa trạng thái modal ra khỏi localStorage (để không mở lại khi tải trang lần tiếp theo)
            localStorage.removeItem('modalToOpen');
            // Lấy bookID từ localStorage
            var bookID = localStorage.getItem('bookID');
            // Hiển thị bookID trong modal (nếu cần)
            var bookIDDisplay = modal.querySelector("p");
            bookIDDisplay.innerHTML = "Book ID: " + bookID;
            // Xóa bookID ra khỏi localStorage (để không sử dụng lại khi tải trang lần tiếp theo)
            localStorage.removeItem('bookID');
        }
    };
    //--------------------
    // Lấy tất cả các phần tử <span> (nút đóng) và gán sự kiện click
    var spans = document.getElementsByClassName("close");
    for (var i = 0; i < spans.length; i++) {
        spans[i].onclick = function () {
            var modal = this.parentElement.parentElement;
            modal.style.display = "none";
        }
    }

    // Khi người dùng nhấp vào bất kỳ chỗ nào ngoài modal, đóng modal
    window.onclick = function (event) {
        for (var i = 0; i < modals.length; i++) {
            if (event.target == modals[i]) {
                modals[i].style.display = "none";
            }
        }
    };
</script>
</body>
</html>
