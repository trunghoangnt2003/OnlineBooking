<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ IDEA. User: HUY Date: 30-May-24 Time: 3:22 PM --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule</title>

    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />

    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet" />

    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <style>
        .main {
            margin: 100px 40px;
        }

        .schedule {
            width: 85%;
        }

        .booking {
            width: 15%;
            position: relative;
            justify-content: center;
        }

        .notes-container {
            margin: 10px;
            position: relative;
        }

        .notes {
            color: #333;
            position: relative;
            width: 100px;
            height: 100px;
            margin: 0 auto;
            padding: 10px;
            font-family: Satisfy;
            font-size: 16px;
            box-shadow: 0 10px 10px 2px rgba(0, 0, 0, 0.3);
        }

        .color-note {
            background: #eae672;
            transform: rotate(2deg);
        }

        .thumbtack {
            position: absolute;
            top: -7px;
            left: 50%;
            z-index: 1;
        }

        .pin {
            background-color: #aaa;
            display: block;
            height: 15px;
            width: 2px;
            position: absolute;
            left: 50%;
            top: -5px;
            z-index: 1;
        }

        .pin:after {
            background-color: gray;
            background-image: radial-gradient(25% 25%, circle, hsla(0, 0%, 100%, .3), hsla(0, 0%, 0%, .3));
            border-radius: 50%;
            box-shadow: inset 0 0 0 1px hsla(0, 0%, 0%, .1), inset 3px 3px 3px hsla(0, 0%, 100%, .2), inset -3px -3px 3px hsla(0, 0%, 0%, .2), 18px 8px 3px hsla(0, 0%, 0%, .15);
            content: '';
            height: 12px;
            left: -5px;
            position: absolute;
            top: -10px;
            width: 12px;
        }

        .pin:before {
            background-color: hsla(0, 0%, 0%, 0.1);
            box-shadow: 0 0 .25em hsla(0, 0%, 0%, .1);
            content: '';
            height: 15px;
            width: 2px;
            left: 0;
            position: absolute;
            top: 0;
            transform: rotate(57.5deg);
            transform-origin: 50% 100%;
        }

        thead th {
            border: 1px #07AD90 solid;
        }

        tbody tr, td {
            border: 1px #07AD90 solid;
            width: 122px;
            height: 122px;
        }

        .btn-book {
            position: absolute;
            bottom: 0;
            width: 100%;
            height: 30px;
            display: flex;
            justify-content: center;
        }

    </style>
</head>
<body>
<form action="schedule" method="GET" id="frm">
    <div class="d-flex main">
        <div class="schedule">
            <table>
                <thead>
                <tr>
                    <th>
                        <input type="date" id="ymd" name="ymd" onchange="changeDate()" value="${requestScope.today}" />
                    </th>
                    <th>Monday</th>
                    <th>Tuesday</th>
                    <th>Wednesday</th>
                    <th>Thursday</th>
                    <th>Friday</th>
                    <th>Saturday</th>
                    <th>Sunday</th>
                </tr>
                <tr>
                    <th></th>
                    <c:forEach items="${requestScope.week}" var="date">
                        <th>${date.toLocalDate().getDayOfMonth()}/${date.toLocalDate().getMonthValue()}</th>
                    </c:forEach>
                </tr>
                </thead>
                <tbody style="border: 1px #07AD90 solid">
                <c:forEach items="${requestScope.slots}" var="slot">
                    <tr>
                        <td>
                            <span>Slots ${slot.id}</span>
                            <br/>
                                ${slot.start_at}-${slot.end_at}
                        </td>
                        <c:forEach items="${requestScope.week}" var="date">
                            <td>
                                <c:forEach items="${requestScope.bookingSchedules}" var="bs">
                                    <c:if test="${ (bs.schedule.slot.id == slot.id) && (bs.schedule.date == date) }">
                                        <div class="notes-container">
                                            <i class="pin"></i>
                                            <c:if test="${bs.status.id == 0}">
                                                <blockquote class="notes color-note" style="background-color: #32cd32">
                                                    <span>Free</span>
                                                    <input type="checkbox"
                                                            <c:forEach items="${requestScope.bookingList}" var="booking">
                                                                ${booking.schedule.id == bs.schedule.id ? 'checked' : ''}
                                                            </c:forEach>
                                                           onchange="handleCheckboxChange(this, ${bs.schedule.id}, ${slot.id}, '${date}')"
                                                    /> <span>book</span>
                                                        ${bs.schedule.id}
                                                </blockquote>
                                            </c:if>
                                            <c:if test="${bs.status.id != 0}">
                                                <c:if test="${bs.booking.mentee.account.id eq requestScope.mentee_id}">
                                                    <blockquote class="notes color-note" style="background-color: #F4E0B9">
                                                        <span>Your Book</span>
                                                        <img width="20px"  src="${pageContext.request.contextPath}${bs.booking.level_skills.skill.src_icon}">
                                                        <span>${bs.booking.level_skills.skill.name} for ${bs.booking.level_skills.level.name}</span>
                                                        <span>${bs.status.id}</span>
                                                    </blockquote>
                                                </c:if>
                                                <c:if test="${bs.booking.mentee.account.id ne requestScope.mentee_id}">
                                                    <blockquote class="notes color-note" style="background-color: #FF6347">
                                                        <span>Booked by other</span>
                                                    </blockquote>
                                                </c:if>
                                            </c:if>
                                        </div>
                                    </c:if>
                                </c:forEach>
                            </td>
                        </c:forEach>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>

        <div class="booking">
            <form action="schedule" id="bookingForm" method="post" style="height: 100%; width: 100%">
                <h5 style="text-align: center">Your Booking</h5>

                <div id="bookingList1">
                    <c:forEach items="${requestScope.bookingList}" var="booking">
                        <input type="text" hidden="hidden"
                               value="${booking.schedule.id}_${booking.schedule.slot.id}_${booking.schedule.date}" />
                    </c:forEach>
                </div>
                <ul id="bookingList">

                </ul>
                <div class="btn-book">
                    <input type="submit" value="Book">
                </div>
            </form>
        </div>
    </div>
</form>
<script>
    let bookingArray = [];

    document.addEventListener("DOMContentLoaded", function() {
        const bookingList1 = document.getElementById('bookingList1');
        const inputs = bookingList1.getElementsByTagName('input');
        for (let i = 0; i < inputs.length; i++) {
            const input = inputs[i];
            const value = input.value.split('_');
            const scheduleId = parseInt(value[0]);
            const slotId = parseInt(value[1]);
            const date = value[2];
            addToBookingArray(scheduleId, slotId, date);
        }

        // console.log('Initial booking array:', bookingArray);
        updateBookingList();
    });

    function changeDate() {
        document.getElementById('frm').submit();
    }

    function handleCheckboxChange(checkbox, scheduleId, slotId, date) {
        if (checkbox.checked) {
            addToBookingArray(scheduleId, slotId, date);
        } else {
            removeFromBookingArray(scheduleId);
        }
    }

    function addToBookingArray(scheduleId, slotId, date) {
        const bookingItem = { scheduleId, slotId, date: new Date(date) };
        if (!bookingArray.some(item => item.scheduleId === scheduleId)) {
            bookingArray.push(bookingItem);
            bookingArray.sort((a, b) => a.date - b.date);
            updateBookingList();
            // console.log('Added booking:', bookingItem);
            // console.log('Current booking array:', bookingArray);
        }
        // else {
        //     console.log('Booking already in array:', bookingItem);
        // }
    }

    function removeFromBookingArray(scheduleId) {
        bookingArray = bookingArray.filter(item => item.scheduleId !== scheduleId);
        updateBookingList();
        // console.log('Removed booking with scheduleId:', scheduleId);
        // console.log('Current booking array:', bookingArray);
    }

    function updateBookingList() {
        const bookingList = document.getElementById('bookingList');
        bookingList.innerHTML = '';
        bookingArray.forEach(booking => {
            const listItem = document.createElement('li');
            const input = document.createElement('input');
            input.type = 'text';
            input.name = 'booking-schedule';
            input.value = booking.scheduleId + "_" + booking.slotId + "_" + booking.date.toISOString().split('T')[0];
            input.style.display = 'none';

            listItem.style.fontSize = '14px';
            listItem.style.textDecoration = 'none';
            listItem.textContent = booking.date.toISOString().split('T')[0] + ' - Slot: ' + booking.slotId;
            bookingList.appendChild(input);
            bookingList.appendChild(listItem);
        });
    }
</script>
</body>
</html>
