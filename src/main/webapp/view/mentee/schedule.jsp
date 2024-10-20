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


        table {
            border: 2px #07AD90 solid;
            margin: 0 auto;
        }

        thead tr th {
            border-left: 2px #00714b solid;
            text-align: center;
            background: #00B074;
            color: white;
        }
        thead{
            border-bottom: 2px #00714b solid;
        }

        tbody tr, td {
            border: 2px #00714b dashed;
            width: 170px;
            height: 100px;
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

        input[type="date"] {
            background-color: #f0f8ff; /* Light background color */
            border: 2px solid #00714b; /* Blue border */
            border-radius: 5px; /* Rounded corners */
            padding: 5px; /* Spacing inside the input */
            font-size: 14px; /* Font size */
            color: #333; /* Text color */
            outline: none; /* Remove outline on focus */
            transition: border-color 0.3s ease; /* Smooth transition */
        }

        /* Change border color when the input is focused */
        input[type="date"]:focus {
            border-color: #07ba80; /* Darker blue on focus */
        }

        /* Style the input on hover */
        input[type="date"]:hover {
            border-color: #07ba80; /* Lighter blue on hover */
        }
    </style>
</head>

<body>
<jsp:include page="../common/header.jsp"></jsp:include>
<%--<div class="back"><jsp:include page="../common/backBtn.jsp"></jsp:include></div>--%>
<form action="schedule" method="GET" id="frm">
    <div class="d-flex main">
        <div class="schedule">
            <div>
                <h1 style="text-align: center; color: rgb(43, 57, 64);">Time Table</h1>
               <%-- <div class="notess">
                    <div><i class="fa-solid fa-square" style="color: #FFF142FF;"></i> is a session not start</div>
                    <div><i class="fa-solid fa-square" style="color: #FAAD12FF; margin-left: 20px"></i> is attended or not attended</div>
                </div>--%>
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
                    <th></th>
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
                   <td class="text-center" style="background: #00B074; color: white">
                       <h5>Slot ${slot.id}</h5>
                           <%--${slot.start_at}-${slot.end_at}--%>
                   </td>
                    <c:forEach items="${requestScope.week}" var="date">
                    <td>
                        <div class="notes-container text-center ">
                            <c:forEach items="${requestScope.bookingSchedules}" var="bs">
                            <c:if test="${ (bs.schedule.slot.id == slot.id) && (bs.schedule.date == date) }">
                                <c:if test="${bs.status.id == 3}">
                                    <div>
                                        <div class="d-flex justify-content-end">
                                            <img width="23px" class="mb-2"  src="${pageContext.request.contextPath}/${bs.booking.level_skills.skill.src_icon}">
                                        </div>
                                        <span style="font-size: 14px">${bs.booking.mentor.account.name}</span>
                                        <div style="background-color: #FAAD12FF; font-size: 14px; margin-bottom: 4px">
                                                ${slot.start_at}-${slot.end_at}
                                        </div>
                                        <div style="background-color: #FAAD12FF">
                                            <span>${bs.booking.level_skills.level.name}</span>
                                        </div>
                                        <span style="font-size: 16px">${bs.attend ? "Attend" : "Absent"}</span>
                                    </div>
                                </c:if>

                                <c:if test="${bs.status.id == 11}">
                                    <div>
                                        <div class="d-flex justify-content-end">
                                            <img width="23px" class="mb-2"  src="${pageContext.request.contextPath}/${bs.booking.level_skills.skill.src_icon}">
                                        </div>
                                        <div style="background-color: #FFF142FF; font-size: 14px; margin-bottom: 4px">
                                            <span style="font-size: 14px">${bs.booking.mentor.account.name}</span>
                                        </div>
                                        <div style="background-color: #FFF142FF; font-size: 14px; margin-bottom: 4px">
                                           <%-- <span >${bs.attend ? "Attend" : "Not attend"}</span>--%>
                                             Not start   ${slot.start_at}-${slot.end_at}
                                        </div>
                                        <div style="background-color: #FFF142FF; font-size: 14px; margin-bottom: 4px">
                                            <span>${bs.booking.level_skills.level.name}</span>
                                        </div>
                                    </div>
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
