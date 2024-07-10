<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%-- Created by IntelliJ IDEA. User: HUY Date: 30-May-24 Time: 3:22 PM --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Schedule</title>

    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />

    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">

    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet" />
    <style>
        .main {
            margin: 10px 40px;
        }

        .info-container{
            width:78%;
            margin: 20px 0;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 5px 7px 2px rgba(0, 0, 0, 0.05);
        }

        .wallet{
            width: 20%;
            margin: 20px 0;
            border-radius: 10px;
            padding: 25px;
            box-shadow: 0 5px 7px 2px rgba(0, 0, 0, 0.05);
        }

        .wallet-top {
            color: #07AD90;
            text-align: center;
            display: flex;
            justify-content: center;
            margin-bottom: 1rem;
            cursor: pointer;
        }
        .wallet-top i {

            margin-right: 0.5rem;
        }
        .wallet-top:hover {
            color: #04e8bf;
        }


        .category{
            height: 50px;
        }
        .category_name{
            box-shadow: 0 5px 7px 2px rgba(0, 0, 0, 0.2);
            border-radius: 10px;;
            background: #07AD90;
            color: white;
            font-size: 20px;
            padding: 10px;
            text-align: center;
        }

        .schedule {
            width: 85%;
        }

        .booking {
            margin-top: 45px;
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
            text-align: start;
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
            background-color: yellowgreen;
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

        table{
            border: 2px #07AD90 solid;

        }

        thead tr th {
            border-left: 1px #07AD90 solid;
            text-align: center;

        }
        thead{
            border-bottom: 2px #07AD90 solid;
        }

        tbody tr, td {
            border: 1px #07AD90 solid;
            width: 122px;
            height: 122px;
        }

        .footer-booking{
            position: absolute;
            bottom: 0;
        }

        .amount{
            bottom: 0;
            width: 100%;
            text-align: center;
        }
        .btn-book {
            bottom: 0;
            width: 100%;

            display: flex;
            justify-content: center;
        }


        .checkbox__input {
            position: absolute;
            width: 1.375em;
            height: 1.375em;
            opacity: 0;
            cursor: pointer;
        }

        .checkbox__input:checked + .checkbox__icon .tick {
            stroke-dashoffset: 0;
        }

        .checkbox__icon {
            width: 1.375em;
            height: 1.375em;
            flex-shrink: 0;
            overflow: visible;
        }

        .checkbox__icon .tick {
            stroke-dasharray: 20px;
            stroke-dashoffset: 20px;
            transition: stroke-dashoffset .2s ease-out;
        }


        /* Styling for the unordered list */
        .booking-list {
            list-style-type: none;
            padding: 0;
            margin: 0;
            font-family: 'Roboto', sans-serif; 
            color: #333;
        }

        /* Styling for the list items */
        .booking-item {
            background-color: rgb(176, 237, 215); /* Light background color */
            margin-bottom: 10px; /* Space between items */
            padding: 10px; /* Padding inside the list items */
            border-radius: 5px; /* Rounded corners */
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2); /* Subtle shadow for depth */
            display: flex; /* Flexbox for potential icon/text alignment */
            align-items: center; /* Center items vertically */
            font-size: 14px; /* Font size */
        }

        .booking-item:last-child {
            margin-bottom: 0; /* Remove margin from the last item */
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


        .notetip{
            width: 20px;
            height: 20px;
            border-radius: 10%;
            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2)
        }


        .booking-list-conflict {
            list-style-type: none;
            padding: 0;
            margin: 0 0 10px;
            font-family: 'Roboto', sans-serif;
            color: #333;
        }
    </style>
</head>

<body>
<jsp:include page="../../common/header.jsp"></jsp:include>
<form action="booking-schedule" method="GET" id="frm">
    <div style="margin: 20px 0 40px 40px;">
        <jsp:include page="../../common/backBtn.jsp"></jsp:include>
    </div>
    <div class="d-flex justify-content-between" style="margin: 0 40px" >
        <div class="info-container">
            <c:set var="level_skill" value="${requestScope.level_skills}"></c:set>
            <input type="text" hidden="hidden" name="mentorId" value="${requestScope.mentor.account.id}"/>
            <input type="text" hidden="hidden" name="skill" value="${level_skill.skill.name}"/>
            <input type="text" hidden="hidden" name="level" value="${level_skill.level.name}">
            <div>
                <div class="category">
                    <span class="category_name">${level_skill.skill.category.name} </span>
                </div>
                <div class="d-flex align-items-center mt-4">
                    <img style="width: 40px" src="${pageContext.request.contextPath}/${level_skill.skill.src_icon}">
                    <h5 style="color: #07ad90; margin: 0 0 0 10px">${level_skill.skill.name} for ${level_skill.level.name} </h5>
                </div>
            </div>
            <div  class="d-flex justify-content-between mt-3">
                <span class="fs-4"> Mentor: ${requestScope.mentor.account.name}</span>
                <span class="fs-5">
                    <fmt:formatNumber value="${requestScope.mentor.price}" type="number" maxFractionDigits="0" />₫/per slot
                </span>

            </div>
        </div>
        <div class="wallet">
            <div class="wallet-top" onclick="waletHandle()">
                <i class="fa-solid fa-wallet fa-2x"></i>
                <h3>Wallet</h3>
            </div>
            <h6>Total:
                <fmt:formatNumber value="${requestScope.wallet.balance}" type="number" maxFractionDigits="0" />₫
            </h6>
            <h6>Available:
                <fmt:formatNumber value="${requestScope.wallet.balance-requestScope.wallet.hold}" type="number" maxFractionDigits="0" />₫
            </h6>
            <input id="available" type="text" hidden="hidden" name="available" value="${requestScope.wallet.balance-requestScope.wallet.hold}"/>

        </div>
    </div>
    <div class="d-flex main">
        <div class="schedule">
            <div>
                <h1 style="text-align: center; color: #07AD90;">Schedule</h1>
            </div>
            <table>
                <thead>
                <tr>
                    <th>
                        <input type="date" id="ymd" name="ymd" onchange="changeDate()" value="${requestScope.today}" />
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
                        <td class="text-center">
                            <h6>Slot ${slot.id}</h6>
                                ${slot.start_at}-${slot.end_at}
                        </td>
                        <c:forEach items="${requestScope.week}" var="date">
                            <td >
                                <c:forEach items="${requestScope.bookingSchedules}" var="bs">
                                    <c:if test="${ (bs.schedule.slot.id == slot.id) && (bs.schedule.date == date) }">
                                        <div class="notes-container text-center ">
                                            <i class="pin"></i>
                                            <%--Free And Proccessing                                      --%>
                                            <c:if test="${bs.status.id == 0}">
                                                <c:set var="time_start_at" value="${bs.schedule.date}T${slot.start_at}:00" />
                                                <fmt:formatDate var="now" value="${requestScope.now }" pattern="yyyy-MM-dd'T'HH:mm:ss"/>
                                                <%--Check if the time is in the future                                     --%>
                                                <c:if test="${time_start_at > now}">
                                                    <blockquote class="notes color-note font-monospace" style="background-color: #32cd32; text-align: center">
                                                        <div class="text-center fw-bold fs-4  ">
                                                            <span > Free</span>
                                                        </div>
                                                        <div class="mt-1" style="font-size: 14px">
                                                            <span>book here </span>
                                                        </div>
                                                        <input type="checkbox" class="checkbox__input"
                                                                <c:forEach items="${requestScope.bookingList}" var="booking">
                                                                    ${booking.schedule.id == bs.schedule.id ? 'checked' : ''}
                                                                </c:forEach>
                                                               onchange="handleCheckboxChange(this, ${bs.schedule.id}, ${slot.id}, '${date}','${slot.end_at}')"
                                                        />
                                                        <svg class="checkbox__icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 22 22">
                                                            <rect width="21" height="21" x=".5" y=".5" fill="#FFF" stroke="#006F94" rx="3" />
                                                            <path class="tick" stroke="#6EA340" fill="none" stroke-linecap="round" stroke-width="4" d="M4 10l5 5 9-9" />
                                                        </svg>
                                                    </blockquote>
                                                </c:if>
                                                <%--Check if the time is in the past                                      --%>
                                                <c:if test="${time_start_at <= now}">
                                                    <blockquote class="notes color-note font-monospace" style="background-color: #989797; text-align: center">
                                                        <div class="text-center fw-bold fs-4  ">
                                                            <span > Free</span>
                                                        </div>
                                                        <div class="mt-3" style="font-size: 14px">
                                                            <span>out date </span>
                                                        </div>
                                                      <%--  <input type="checkbox" class="checkbox__input"
                                                                <c:forEach items="${requestScope.bookingList}" var="booking">
                                                                    ${booking.schedule.id == bs.schedule.id ? 'checked' : ''}
                                                                </c:forEach>
                                                               onchange="handleCheckboxChange(this, ${bs.schedule.id}, ${slot.id}, '${date}','${slot.end_at}')"
                                                        />--%>
                                                       <%-- <svg class="checkbox__icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 22 22">
                                                            <rect width="21" height="21" x=".5" y=".5" fill="#FFF" stroke="#006F94" rx="3" />
                                                            <path class="tick" stroke="#6EA340" fill="none" stroke-linecap="round" stroke-width="4" d="M4 10l5 5 9-9" />
                                                        </svg>--%>
                                                    </blockquote>
                                                </c:if>

                                            </c:if>
                                            <%--Not Free                                           --%>
                                            <c:if test="${bs.status.id != 0}">
                                                <%--My book                                         --%>
                                                <c:if test="${bs.booking.mentee.account.id.equals(requestScope.mentee_id)}">
                                                    <%--Processing                                           --%>
                                                    <c:if test="${bs.status.id == 1}">

                                                        <blockquote class="notes color-note font-monospace" style="background-color: #F4E0B9">

                                                            <div class="text-center fw-bold">
                                                                <span>Your Book</span>
                                                            </div>
                                                            <img width="20px" class="mt-2"  src="${pageContext.request.contextPath}/${bs.booking.level_skills.skill.src_icon}">
                                                            <div style="font-size: 14px">
                                                                <span>${bs.booking.level_skills.level.name}</span>
                                                                <span style="font-size: 10px">Processing...</span>
                                                            </div>
                                                        </blockquote>
                                                    </c:if>
                                                    <%--Accepted                                           --%>
                                                    <c:if test="${bs.status.id == 11}">
                                                        <blockquote class="notes color-note font-monospace" style="background-color: #fff142">

                                                            <div class="text-center fw-bold">
                                                                <span>Your Book</span>
                                                            </div>
                                                            <img width="20px" class="mt-2"  src="${pageContext.request.contextPath}/${bs.booking.level_skills.skill.src_icon}">
                                                            <div style="font-size: 14px">
                                                                <span>${bs.booking.level_skills.level.name}</span>

                                                                <span style="font-size: 10px">Accepted</span>
                                                            </div>
                                                        </blockquote>
                                                    </c:if>
                                                    <%--Done                                           --%>
                                                    <c:if test="${bs.status.id == 3}">
                                                        <blockquote class="notes color-note font-monospace" style="background-color: #faad12">

                                                            <div class="text-center fw-bold">
                                                                <span>Your Book</span>
                                                            </div>
                                                            <img width="20px" class="mt-2"  src="${pageContext.request.contextPath}/${bs.booking.level_skills.skill.src_icon}">
                                                            <div style="font-size: 12px">
                                                                <span>${bs.booking.level_skills.level.name}</span>
                                                                <span style="font-size: 10px">Done</span>
                                                            </div>
                                                        </blockquote>
                                                    </c:if>
                                                </c:if>
                                                <%--Not My book                                           --%>
                                                <c:if test="${bs.booking.mentee.account.id ne requestScope.mentee_id}">
                                                    <%--Done or Accepted                                           --%>
                                                    <c:if test="${bs.status.id != 1}">
                                                        <blockquote class="notes color-note font-monospace" style="background-color: #FF6347">
                                                            <div class="text-center fw-bold fs-5">
                                                                <span >Busy </span>
                                                            </div>
                                                            <div class="text-center mt-2" style="font-size: 12px">
                                                                <span> Booking are not possible</span>
                                                            </div>
                                                        </blockquote>
                                                    </c:if>

                                                    <%--Processing Can book                                           --%>
                                                    <c:if test="${bs.status.id == 1}">
                                                        <blockquote class="notes color-note font-monospace" style="background-color: #32cd32; text-align: center">
                                                            <div class="text-center fw-bold fs-4  ">
                                                                <span > Free</span>
                                                            </div>
                                                            <div class="mt-1" style="font-size: 14px">
                                                                <span>book here</span>
                                                            </div>
                                                            <input type="checkbox" class="checkbox__input"
                                                                    <c:forEach items="${requestScope.bookingList}" var="booking">
                                                                        ${booking.schedule.id == bs.schedule.id ? 'checked' : ''}
                                                                    </c:forEach>
                                                                   onchange="handleCheckboxChange(this, ${bs.schedule.id}, ${slot.id}, '${date}','${slot.end_at}')"
                                                            />
                                                            <svg class="checkbox__icon" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 22 22">
                                                                <rect width="21" height="21" x=".5" y=".5" fill="#FFF" stroke="#006F94" rx="3" />
                                                                <path class="tick" stroke="#6EA340" fill="none" stroke-linecap="round" stroke-width="4" d="M4 10l5 5 9-9" />
                                                            </svg>
                                                        </blockquote>
                                                    </c:if>
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
            <div class="d-flex justify-content-between mt-4" style="width: 70%" >
                <div class="d-flex">
                    <div class="notetip" style="background-color: #FF6347FF;" ></div> <span class="text-danger ms-2">Busy</span>
                </div>
                <div class="d-flex">
                    <div class="notetip" style="background-color: #FAAD12FF " ></div> <span class="text-warning ms-2">Done</span>
                </div>
                <div class="d-flex">
                    <div class="notetip" style="background-color: #FFF142FF " ></div> <span class="ms-2" style="color: #FFF142FF">Accepted</span>
                </div>
                <div class="d-flex">
                    <div class="notetip" style="background-color: #F4E0B9 " ></div> <span class="ms-2" style="color: #F4E0B9">Processing</span>
                </div>
                <div class="d-flex">
                    <div class="notetip" style="background-color: #32CD32FF " ></div> <span class="ms-2" style="color: #32CD32FF">Free</span>
                </div>
                <div class="d-flex">
                    <div class="notetip" style="background-color: #989797FF " ></div> <span class="ms-2" style="color: #989797FF">Free but out date</span>
                </div>

            </div>
        </div>

        <div class="booking">

                <h4 style="text-align: center; color: #07AD90">Your Booking</h4>
                <hr style="margin: 10px 0; border: 1px #1BB295 solid; opacity: 100%"/>
                <c:if test="${requestScope.bookingConflict.size() gt 0}">

                    <div style="text-align: center">
                        <ul class="booking-list-conflict">
                            <c:forEach items="${requestScope.bookingConflict}" var="bcf">
                                <input type="text" hidden="hidden" name="booking-conflict" value="${bcf.schedule.id}_${bcf.schedule.slot.id}_${bcf.schedule.date}"/>
                                <li class="booking-item" style="font-size: 14px; background-color: #FF6347">${bcf.schedule.date} - Slot: ${bcf.schedule.slot.id}</li>
                            </c:forEach>

                        </ul>
                        <i style="font-size: 10px; color: #FF6347">< Your bookings above have conflict! ></i>
                        <i style="font-size: 10px; color: #FF6347">< ${requestScope.slotNotExist} slots not exist ></i>
                        <hr style="margin: 5px 0; color: #FF6347; opacity: 80%; border: none;">
                    </div>
                </c:if>
            <c:if test="${slotNotExist > 0 && requestScope.bookingConflict.size() le 0}">
                <div style="text-align: center">  <i style="font-size: 10px; color: #FF6347" >< ${requestScope.slotNotExist} slots not exist ></i></div>
                <hr style="margin: 5px 0; color: #FF6347; opacity: 80%; border: none;">
            </c:if>
                <div id="bookingList1">
                    <c:forEach items="${requestScope.bookingList}" var="booking">
                        <input type="text" hidden="hidden"
                               value="${booking.schedule.id}_${booking.schedule.slot.id}_${booking.schedule.date}" />
                    </c:forEach>
                </div>
                <ul id="bookingList" class="booking-list">

                </ul>
                <div class="footer-booking">
                    <div>
                        <textarea id="notes" name="message" placeholder="write your sort message" rows="6" ></textarea>
                    </div>
                    <div class="amount">
                        <input id="price" type="number" value="${requestScope.mentor.price}" hidden="hidden"/>
                        <h5 id="number_booking"></h5>
                        <h5 id="amount" ></h5>

                    </div>
                    <div class="btn-book">
                        <button type="button" class="btn btn-outline-success"  onclick="bookHandle()"
                                data-mdb-ripple-init data-mdb-ripple-color="dark">Book</button>
                    </div>
                </div>

        </div>
    </div>
</form>
<script>
    let bookingArray = [];
    let number_booking = 0;

    function backHandle(){
        console.log(window.history);
        window.history.back();
    }


    //auto load booking list from controller ( select before )
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

    function handleCheckboxChange(checkbox, scheduleId, slotId, date, time) {
        const datetime = new Date(date + " " + time);
        if(datetime < new Date()){
            checkbox.checked = false;
            Swal.fire({
                icon: "error",
                title: "This slot is outdated !",
                text: "Please choose another slot from the future.",
                showConfirmButton: false,
                timer: 2000
            });
        }else{
            if (checkbox.checked) {
                addToBookingArray(scheduleId, slotId, date);
            } else {
                removeFromBookingArray(scheduleId);
            }
        }

    }

    function addToBookingArray(scheduleId, slotId, date) {
        const bookingItem = { scheduleId, slotId, date: new Date(date) };
        if (!bookingArray.some(item => item.scheduleId === scheduleId)) {
            bookingArray.push(bookingItem);
            bookingArray.sort((a, b) => a.date - b.date);
            number_booking += 1;
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
        number_booking -= 1;
        updateBookingList();
        // console.log('Removed booking with scheduleId:', scheduleId);
        // console.log('Current booking array:', bookingArray);
    }

    function updateBookingList() {
        const bookingList = document.getElementById('bookingList');
        const numbook = document.getElementById('number_booking');
        const amount = document.getElementById('amount');
        const price = document.getElementById('price').value;



        numbook.innerHTML = 'Number of booking: ' + number_booking;
        var totalAmount = number_booking * price;
        var formattedAmount = totalAmount.toLocaleString('en-US', { maximumFractionDigits: 0 });

        amount.innerHTML =  'Total amount: ' + formattedAmount + '₫';
        const total_amount = document.createElement('input');
        total_amount.type = 'text';
        total_amount.name = 'total_amount';
        total_amount.id = 'total_amount';
        total_amount.value =  (number_booking * price);
        total_amount.style.display = 'none';
        amount.appendChild(total_amount);

        bookingList.innerHTML = '';
        bookingArray.forEach(booking => {
            const listItem = document.createElement('li');
            listItem.className = 'booking-item';
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


    function bookHandle() {
        Swal.fire({
            title: "Are you sure?",
            text: "You won't be able to revert this!",
            icon: "question",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, book it!",
        }).then((result) => {
            if (result.isConfirmed ) {
                if(number_booking > 0){
                    const price = document.getElementById('price').value
                    const total_amount = price * number_booking;
                    const  available = document.getElementById('available').value;
                    console.log(total_amount > available);
                    if( total_amount > available) {
                        Swal.fire({
                            title: "Error!",
                            text: "Insufficient balance.",
                            icon: "error",
                            showConfirmButton: false,
                            footer:'<a href="../payment">Recharge wallet now</a>'
                        })
                        console.log("ádadasd");
                    }else {
                        Swal.fire({
                            title: "Done!",
                            text: "Your booking has been booked.",
                            icon: "success",
                            showConfirmButton: false,
                        });
                        setTimeout(() => {
                            const form = document.getElementById('frm');
                            form.method = 'POST';
                            form.submit();
                        }, 1500);
                    }
                }else{
                    Swal.fire({
                        title: "Error!",
                        text: "Please select at least one booking.",
                        icon: "error",
                        showConfirmButton: false,
                        timer: 2000
                    });
                }



            }
        });
    }

    function waletHandle(){
        window.location.href = "../wallet/view";
    }
</script>

<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
</body>
</html>
