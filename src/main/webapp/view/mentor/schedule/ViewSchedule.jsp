<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 5/16/2024
  Time: 10:12 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/ViewSchedule.css">
</head>
<body>
<!-- Form để gửi yêu cầu GET -->
<form action="schedule" method="GET" id="getForm">
    <div>
        <input type="date" name="today" value="${param.today}" onchange="document.getElementById('getForm').submit()">
    </div>
</form>

<!-- Nút mở modal -->
<button id="myBtn">Set Time</button><br/>
<!-- Nút mở modal mới -->
<button id="myBtn2">View Request</button>

<!-- The Modal -->
<div id="myModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="close">&times;</span>
        <!-- Form để gửi yêu cầu POST -->
        <form action="schedule" method="POST" id="postForm">
            <p>Set Free time</p>
            Choose date: <input type="date" name="setDate"><br/>
            Set Time Start: <input type="time" name="from"><br/>
            Set Time End: <input type="time" name="to"><br/>
            <input type="submit" value="save"><br/>
        </form>
    </div>
</div>

<!-- The Modal for Info -->
<div id="infoModal" class="modal">
    <!-- Modal content -->
    <div class="modal-content">
        <span class="closeInfo">&times;</span>
        <p>Request List </p>
        <table>
            <tr>
                <td>Number</td>
                <td>Name</td>
                <td>Mail</td>
                <td>Start Date</td>
                <td>End Date</td>
                <td>Amount</td>
                <td>Status</td>
            </tr>
            <c:forEach items="${bookings}" var="book">
                <tr>
                    <td>${count}</td>
                    <td>${book.mentee.account.name}</td>
                    <td>  ${book.mentee.account.email}</td>
                    <td>   ${book.startDate}</td>
                    <td>   ${book.endDate}</td>
                    <td>  ${book.amount}</td>
                    <td>  <c:if test="${book.status.id ==1 }"><button name="accept" value="accept">Accept</button></c:if>
                        <c:if test="${book.status.id ==1 }"><button name="reject" value="reject">Reject</button></c:if>
                        <c:if test="${book.status.id !=1 }">${book.status.type}</c:if>
                    </td>

                    <c:set var="count" value="${count= count +1}"></c:set>
                </tr>
            </c:forEach>
        </table>

    </div>
</div>


<div>
    <table border="1px">
        <tr>
            <td></td>
            <c:forEach items="${weeks}" var="week">
                <td>${week.dayOfWeek}<br/>
                        ${week.dayOfMonth}
                </td>
            </c:forEach>
        </tr>
        <c:forEach items="${freetime}" var="t">
            <tr>
                <td>${t}</td>
                <c:forEach items="${weeks}" var="week">
                    <td>
                        <c:forEach items="${schedules}" var="sche">
                            <c:if test="${week.dayOfMonth == sche.convertTimestampToString(sche.dateStart)}">
                                <c:if test="${sche.getDateStartHour() == t}">
                                    <c:if test="${sche.status}">
                                        <div class="free">${sche.dateStart}</div>
                                    </c:if>
                                    <c:if test="${!sche.status}">
                                        <div class="work">${sche.dateStart}</div>
                                    </c:if>
                                </c:if>
                                <c:if test="${sche.getDateStartHour() < t}">
                                    <c:if test="${sche.getDateEndtHour() > t}">
                                        <c:if test="${sche.status}">
                                            <div class="free">Free</div>
                                        </c:if>
                                        <c:if test="${!sche.status}">
                                            <div class="work">Work</div>
                                        </c:if>
                                    </c:if>
                                </c:if>
                                <c:if test="${sche.getDateEndtHour() == t}">
                                    <c:if test="${sche.status}">
                                        <div class="free">${sche.dateEnd}</div>
                                    </c:if>
                                    <c:if test="${!sche.status}">
                                        <div class="work">${sche.dateEnd}</div>
                                    </c:if>
                                </c:if>
                            </c:if>
                        </c:forEach>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</div>

<script>
    // Lấy modal
    var modal = document.getElementById("myModal");

    // Lấy nút mở modal
    var btn = document.getElementById("myBtn");

    // Lấy phần tử <span> (nút đóng) để đóng modal
    var span = document.getElementsByClassName("close")[0];

    // Khi người dùng nhấp vào nút, mở modal
    btn.onclick = function() {
        modal.style.display = "block";
        return false; // Ngăn chặn gửi form mặc định
    }

    // Khi người dùng nhấp vào nút đóng (x), đóng modal
    span.onclick = function() {
        modal.style.display = "none";
    }

    // Khi người dùng nhấp vào bất kỳ chỗ nào ngoài modal, đóng modal
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }

    // Lấy modal mới cho thông tin
    var infoModal = document.getElementById("infoModal");

    // Lấy nút mở modal mới
    var btn2 = document.getElementById("myBtn2");

    // Lấy phần tử <span> (nút đóng) để đóng modal mới
    var spanInfo = document.getElementsByClassName("closeInfo")[0];

    // Khi người dùng nhấp vào nút, mở modal mới
    btn2.onclick = function() {
        infoModal.style.display = "block";
        return false; // Ngăn chặn gửi form mặc định
    }

    // Khi người dùng nhấp vào nút đóng (x), đóng modal mới
    spanInfo.onclick = function() {
        infoModal.style.display = "none";
    }

    // Khi người dùng nhấp vào bất kỳ chỗ nào ngoài modal, đóng modal
    window.onclick = function(event) {
        if (event.target == infoModal) {
            infoModal.style.display = "none";
        }
    }
</script>
</body>
</html>
