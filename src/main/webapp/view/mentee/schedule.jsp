<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ IDEA. User: HUY Date: 30-May-24 Time: 3:22 PM --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule</title>

    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>

    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet"/>

    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <style>
        .main {
            margin: 10px 40px;
        }



        .schedule {
            width: 100%;
        }


        .notes-container {
            margin: 10px;
            position: relative;
        }

        .notes {
            color: #333;
            position: relative;
            width: 150px;
            height: 150px;
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
            background-color: yellowgreen;
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

        table {
            border: 2px #07AD90 solid;
            margin: 0 auto;
        }

        thead tr th {
            border-left: 1px #07AD90 solid;
            text-align: center;

        }

        thead {
            border-bottom: 2px #07AD90 solid;
        }

        tbody tr, td {
            border: 1px #07AD90 solid;
            width: 170px;
            height: 170px;
        }


        /* Note Styling */
        .notess {
            display: flex;
            margin: 20px;
            font-size: 16px;
        }

        .note p {
            display: flex;
            align-items: center;
            margin: 10px 0;
        }

        .note p i {
            margin-right: 10px;
        }


        .back{
            margin-top: 20px;
            margin-left: 60px;
            margin-bottom: 50px;
        }

        /* Optional: Styling for an icon within the list items */
        .booking-item i {
            margin-right: 10px; /* Space between icon and text */
            color: #07AD90; /* Icon color */
        }

        /* Styling for hidden input within list items */
        .booking-item input[type="text"] {
            display: none; /* Hide the input element */
        }

        /* Styling for text content within list items */
        .booking-item span {
            flex-grow: 1; /* Allow text to grow and fill the space */
        }
    </style>
</head>

<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="back"><jsp:include page="../common/backBtn.jsp"></jsp:include></div>
<form action="schedule" method="GET" id="frm">
    <div class="d-flex main">
        <div class="schedule">
            <div>
                <h1 style="text-align: center; color: #07AD90;">Schedule</h1>
                <div class="notess">
                    <div><i class="fa-solid fa-square" style="color: #FFF142FF;"></i> is a session not start</div>
                    <div><i class="fa-solid fa-square" style="color: #FAAD12FF; margin-left: 20px"></i> is attended or not attended</div>
                </div>
            </div>

            <table>
                <thead>
                <tr>
                    <th>
                        <input type="date" id="ymd" name="ymd" onchange="changeDate()" value="${requestScope.today}"/>
                    </th>
                    <c:forEach items="${requestScope.week}" var="date">
                        <th>${date}</th>
                    </c:forEach>
                </tr>
                <tr>
                    <th>Slot</th>
                    <th>Monday</th>
                    <th>Tuesday</th>
                    <th>Wednesday</th>
                    <th>Thursday</th>
                    <th>Friday</th>
                    <th>Saturday</th>
                    <th>Sunday</th>
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
                        <div class="notes-container text-center ">
                            <c:forEach items="${requestScope.bookingSchedules}" var="bs">
                            <c:if test="${ (bs.schedule.slot.id == slot.id) && (bs.schedule.date == date) }">
                                <c:if test="${bs.status.id == 3}">
                                    <i class="pin"></i>
                                    <blockquote class="notes color-note font-monospace" style="background-color: #FAAD12FF; text-align: start">
                                        <span style="font-size: 14px">${bs.booking.mentor.account.name}</span>
                                        <img width="30px" class="mt-2" src="${pageContext.request.contextPath}/${bs.booking.level_skills.skill.src_icon}">
                                        <div style="font-size: 16px">
                                            <span>${bs.booking.level_skills.level.name}</span>
                                        </div>
                                        <span style="font-size: 16px">${bs.attend ? "Attend" : "Not attend"}</span>
                                    </blockquote>
                                </c:if>

                                <c:if test="${bs.status.id == 11}">
                                    <i class="pin"></i>
                                    <blockquote class="notes color-note font-monospace" style="background-color: #FFF142FF; text-align: start">
                                        <span style="font-size: 14px">${bs.booking.mentor.account.name}</span>
                                        <img width="30px" class="mt-2" src="${pageContext.request.contextPath}/${bs.booking.level_skills.skill.src_icon}">
                                        <div style="font-size: 16px">
                                            <span>${bs.booking.level_skills.level.name}</span>
                                        </div>
                                        <span style="font-size: 16px">${bs.attend ? "Attend" : "Not attend"}</span>
                                    </blockquote>
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
    </div>
</form>
<script>


    function changeDate() {
        document.getElementById('frm').submit();
    }

</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"></script>
</body>
</html>
