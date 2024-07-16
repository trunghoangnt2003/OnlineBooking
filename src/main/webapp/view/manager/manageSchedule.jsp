
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 24-Jun-24
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin </title>
    <!-- plugins:css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/feather/feather.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/mdi/css/materialdesignicons.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/ti-icons/css/themify-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/font-awesome/css/font-awesome.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/typicons/typicons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/simple-line-icons/css/simple-line-icons.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/css/vendor.bundle.base.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css">
    <!-- endinject -->
    <!-- Plugin css for this page -->
    <!-- End plugin css for this page -->
    <!-- inject:css -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/css/style.css">
    <!-- endinject -->
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/view/admin/assets/images/favicon.png" />
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->

    <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
    <!-- Fontawesome CDN Link For Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />

    <style>
        /* CSS nền hiển thị Modal */
        .nenmodal .nenmodal2 {
            position:fixed;
            top:0px;
            left:0px;
            width:100vw;
            height:100vh;
            background:rgba(0,0,0,0.7);
            z-index:2;
            display:none;
        }
        /* CSS bảng nội dung Modal */
        .nenmodal .ndmodal {
            position:absolute;
            top:50%;
            left:50%;
            transform:translate(-50%,-50%) scale(0);
            background:#fff;
            width:500px;
            z-index:2;
            text-align:center;
            padding:20px;
            box-sizing:border-box;
            font-family:"Open Sans",sans-serif;
            border-radius:20px;
            display: block;
            position: fixed;
            box-shadow:0px 0px 10px #111;
        }
        @media (max-width: 700px) {
            .nenmodal .ndmodal {width:90%;}
        }
        /* CSS bao bọc của nút tắt Modal */
        .nenmodal .closemodal {
            text-align:center;
            margin-top:-40px;
            margin-bottom:10px;
        }
        /* CSS tieu de của Modal */
        .titlemodal{
            font-weight:bold;
            font-size:25px;
            margin-bottom:10px;
        }
        /* CSS nút tắt modal */
        .closemodal button{
            width:40px;
            height:40px;
            font-size:30px;
            padding:0px;
            border-radius:100%;
            background:#333;
            color:#fff;
            border:none;
        }
        .nenmodal.active .nenmodal2 {
            display:block;
        }
        /* CSS hiệu ứng hiển thị Modal */
        .nenmodal.active .ndmodal {
            transition:all 300ms ease-in-out;
            transform:translate(-50%,-50%) scale(1);
        }

        .schedule-slot {
            background-color: #b2e7db; /* Light green background */
            padding: 13px 20px; /* Padding around the text */
            margin: 5px 0;
            border-radius: 5px; /* Rounded corners */
            display: inline-block; /* Keep it inline */
            font-family: Arial, sans-serif; /* Font family */
            font-size: 16px; /* Font size */
            color: #333; /* Text color */
        }
        input[type="checkbox"] {
            margin-right: 10px; /* Space between checkbox and text */
        }

    </style>
    <style>
        input[type="text"] {

            width: 100%;
            color: rgb(36, 35, 42);
            font-size: 16px;
            line-height: 20px;
            min-height: 28px;
            border-radius: 4px;
            padding: 3px 5px;
            border: 2px solid transparent;
            box-shadow: rgb(0 0 0 / 12%) 0px 1px 3px, rgb(0 0 0 / 24%) 0px 1px 2px;
            background: rgb(251, 251, 251);
            transition: all 0.1s ease 0s;
            :focus{
                border: 2px solid rgb(124, 138, 255);
            }

        }

        .select {
            display: inline-block;
            margin-right: 10px;
            font-family: Arial, sans-serif;
        }

        .select label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }

        .select select {
            width: 100px;
            padding: 2px;
            font-size: 14px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: #f9f9f9;
            color: #333;
            outline: none;
            transition: border-color 0.3s ease;
        }

        .select option {
            padding: 8px;
        }

        .select select:focus {
            border-color: #007bff;
        }

        .select option:hover {
            background-color: #f1f1f1;
        }

        .select option:disabled {
            color: #999;
            background-color: #eee;
        }

        .date-range {
            display: flex;
            align-items: center;
            gap: 10px;
        }

        .date-range input[type="date"] {
            padding: 2px;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 14px;
        }

        .date-range label {
            margin-right: 10px;
            font-weight: bold;
        }

        .btn-blue{
            display: inline-block;
            padding: 10px 20px;
            font-size: 16px;
            color: white; /* White text color */
            background-color: #007bff; /* Blue background color */
            border: none; /* No border */
            border-radius: 20px; /* Rounded edges */
            text-align: center;
            cursor: pointer;
            outline: none;
        }

        .btn-blue:hover {
            background-color: #0056b3; /* Darker blue on hover */
        }

        .btn-red {
            display: inline-block;
            padding: 10px 20px;
            font-size: 14px;
            color: rgba(255, 0, 0, 0.3); /* Light red color */
            background-color: transparent;
            border: 2px solid rgba(255, 0, 0, 0.5); /* Red border */
            border-radius: 20px; /* Rounded edges */
            text-align: center;
            cursor: pointer;
            outline: none;
        }

        .btn-red:hover {
            background-color: rgba(255, 0, 0, 0.1); /* Slightly red background on hover */
        }

        /*.btn-accept{*/
        /*    display: inline-block;*/
        /*    padding: 5px 15px;*/
        /*    font-size: 12px;*/
        /*    color: white; !* White text color *!*/
        /*    background-color: #32cd32; !* Blue background color *!*/
        /*    border: none; !* No border *!*/
        /*    border-radius: 20px; !* Rounded edges *!*/
        /*    text-align: center;*/
        /*    cursor: pointer;*/
        /*    outline: none;*/
        /*}*/

        /*.btn-accept:hover{*/
        /*    background-color: #35b835; !* Darker blue on hover *!*/
        /*}*/

        /*.btn-accept-lg{*/
        /*    display: inline-block;*/
        /*    padding: 10px 20px;*/
        /*    font-size: 16px;*/
        /*    color: white; !* White text color *!*/
        /*    background-color: #32cd32; !* Blue background color *!*/
        /*    border: none; !* No border *!*/
        /*    border-radius: 20px; !* Rounded edges *!*/
        /*    text-align: center;*/
        /*    cursor: pointer;*/
        /*    outline: none;*/
        /*}*/

        /*.btn-accept-lg:hover{*/
        /*    background-color: #35b835; !* Darker blue on hover *!*/
        /*}*/
        .btn-view{
            display: inline-block;
            padding: 7px 15px;
            font-size: 14px;
            font-weight: bold;
            width: 100px;
            color: #ffffff; /* White text color */
            background-color: rgb(250, 235, 58); /* Blue background color */
            border: none; /* No border */
            border-radius: 20px; /* Rounded edges */
            box-shadow: 1px 1px 4px 1px rgba(0, 0, 0, 0.1);
            text-align: center;
            cursor: pointer;
            outline: none;
        }
        .btn-view:hover{
            background-color: rgb(243, 221, 35); /* Darker blue on hover */
        }

        .btn-accept{
            display: inline-block;
            padding: 7px 15px;
            font-size: 14px;
            font-weight: bold;
            width: 100px;
            color: #07AD90; /* White text color */
            background-color: rgb(176, 237, 215); /* Blue background color */
            border: none; /* No border */
            border-radius: 20px; /* Rounded edges */
            box-shadow: 1px 1px 4px 1px rgba(0, 0, 0, 0.1);
            text-align: center;
            cursor: pointer;
            outline: none;
        }
        .btn-accept:hover{
            background-color: rgb(155, 234, 203); /* Darker blue on hover */
        }

        .btn-reject{
            display: inline-block;
            padding: 7px 15px;
            font-size: 14px;
            font-weight: bold;
            width: 100px;
            color: #ffffff; /* White text color */
            background-color: #f44040; /* Blue background color */
            border: none; /* No border */
            border-radius: 20px; /* Rounded edges */
            box-shadow: 1px 1px 4px 1px rgba(0, 0, 0, 0.1);
            text-align: center;
            cursor: pointer;
            outline: none;
        }

        .btn-reject:hover{
            background-color: #f82828; /* Darker blue on hover */
        }


    </style>
    <style>
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

        .time-table{
            border: 2px #07AD90 solid;

        }

        .table-head {
            border-left: 1px #07AD90 solid;
            text-align: center;

        }
        .time-table-head{
            border-bottom: 2px #07AD90 solid;
        }

        .table-data {
            border: 1px #07AD90 solid;
            width: 122px;
            height: 122px;
        }
    </style>
</head>
<body>
<div class="nenmodal" id="nenmodal-1">
    <div class="nenmodal2"></div>
    <div class="ndmodal">
            <div class="closemodal"><button onclick="schedulePopup()">X</button></div>
            <div class="titlemodal mb-2">Request Schedule</div>
        <form action="manageSchedule" method="Post" id="frm">
            <div style="height: 400px; overflow-y: auto ">
               <c:forEach items="${requestScope.allSchedule}" var="s">
                       <div class="schedule-slot">
                           <%--<input type="checkbox" class="schedule-checkbox"  name="schedule" value="${s.id}"/>--%>
                               Slot ${s.slot.id} ( ${s.slot.start_at}-${s.slot.end_at} ) on ${s.date}
                       </div>
               </c:forEach>
            </div>
        </form>
    </div>
</div>

<div class="nenmodal" id="modal_message">
    <div class="nenmodal2"></div>
    <div class="ndmodal">
        <div class="closemodal"><button onclick="messagePopup()">X</button></div>
        <div class="titlemodal mb-2">Message for Reject</div>
        <div class="mt-5">
            <textarea id="message" name="message" rows="6" cols="45" placeholder="Enter your message"></textarea>
        </div>
        <button  class="btn-reject w-auto mx-2" onclick="handleSlot(${requestScope.mentor_schedule.id},'reject','new')" style="margin-top: 10px">Reject</button>

    </div>
</div>

<div class="container-scroller">
    <!-- partial:partials/_navbar.jsp -->
    <jsp:include page="../admin/partials/_navbar.jsp"></jsp:include>
    <!-- partial -->
    <div class="page-body-wrapper">
        <!-- partial:partials/_sidebar.jsp -->
        <jsp:include page="../admin/partials/_sidebar.jsp"></jsp:include>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <h2>Schedule of Mentor Manager</h2>
                <form method="get" action="">
                    <div class="d-flex justify-content-between">
                        <div class="d-flex align-items-center">
                            <div >
                                <input id="mentorInput" class="mb-1" type="text" name="mentor"  placeholder="Mentor name" value="${requestScope.mentor}" style="width: 400px" />
                            </div>
                            <div class="d-flex mx-4">
                                <button type="button" onclick="searchHandle()"  class=" d-flex align-items-center btn-blue" style="height: 30px"  >
                                    Search <i class="fa-solid fa-magnifying-glass fa-xs"  style="margin-left: 10px"></i>
                                </button>
                                <button type="button" onclick="clearHandler()" class="d-flex align-items-center btn-red" style="height: 30px ; margin-left: 10px" >
                                    Clear
                                </button>
                            </div>
                        </div>

                    </div>
                </form>
                <div class="table-responsive" style="border: 1px solid black;border-radius: 10px; margin-top: 15px; height: 300px">
                    <table class=" table table-hover"  id="user-table" style="width: 100%; border-radius: 5px">
                        <thead class="table-dark">
                        <td>Schedule</td>
                        <td>Mentor Name</td>
                        <td>Mentor Id</td>
                        <td>Start</td>
                        <td>End</td>
                        <td>New Slot</td>
                        <td>Remove Slot</td>
                        <td>Schedule</td>
                        </thead>
                        <tbody >
                            <c:forEach items="${requestScope.mentorSchedule}" var="entry">
                                <tr>
                                    <td>${entry.key.id}</td>
                                    <td>${entry.key.mentor.account.name}</td>
                                    <td>${entry.key.mentor.account.id}</td>
                                    <td>${entry.key.start_date}</td>
                                    <td>${entry.key.end_date}</td>
                                    <td>${entry.value.get("waiting")}</td>
                                    <td>${entry.value.get("remove")}</td>
                                    <td ><i class="fa-regular fa-calendar-days fa-2xl" style="color: #74C0FC; cursor: pointer" onclick="scheduleHandle('${entry.key.mentor.account.id}','${requestScope.page}','${entry.key.mentor.account.name}')"></i></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                <br>
                <div class="">
                    <div style="display: flex; justify-content: space-between">
                        <div class="justify-content-start" style=""> Total ${requestScope.total} result </div>
                        <nav aria-label="Page navigation example" >
                            <ul class="pagination justify-content-end">
                                <li class="page-item <c:if test="${requestScope.page==1}"> disabled </c:if>">
                                    <a class="page-link" href="?page=${requestScope.page-1}&mentor=${requestScope.mentor}&mentorId=${requestScope.mentorId}&name=${requestScope.name}&ymd=${requestScope.today}"
                                            <c:if test="${requestScope.page==1}"> tabindex="-1" </c:if>
                                    >Previous</a>
                                </li>
                                <c:forEach var="count" begin="1" end="${requestScope.end_page}">
                                    <c:if test="${requestScope.page == count}">
                                        <li class="page-item"><a class="page-link active page">${count}</a></li>
                                    </c:if>
                                    <c:if test="${requestScope.page != count}">
                                        <li class="page-item">
                                            <a class="page-link"
                                               href="?page=${count}&mentor=${requestScope.mentor}&mentorId=${requestScope.mentorId}&name=${requestScope.name}&ymd=${requestScope.today}">${count}
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <li class="page-item">
                                    <a class="page-link <c:if test="${requestScope.page==requestScope.end_page  or requestScope.end_page == 0}"> disabled </c:if>"
                                       href="?page=${requestScope.page+1}&mentor=${requestScope.mentor}&mentorId=${requestScope.mentorId}&name=${requestScope.name}&ymd=${requestScope.today}"
                                            <c:if test="${requestScope.page==requestScope.end_page or requestScope.end_page == 0 }"> tabindex="-1" </c:if>
                                    >Next</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>

                <div class="schedule" id="schedule">
                        <div class="d-flex justify-content-between">
                            <div>
                                <h3>Schedule of Mentor </h3>
                                <input type="hidden" id="name" value="${requestScope.name}"/>
                                <input type="hidden" id="mentorId" value="${requestScope.mentorId}"/>
                            </div>
                        </div>

                    <div class="">
                        <div class="d-flex " style="margin: 20px 65px" >
                            <c:if test="${requestScope.mentor_schedule != null}">
                                <span class="d-flex align-items-end" style="font-size: 1.5rem; width: 200px">${requestScope.name} #${requestScope.mentor_schedule.id}</span>
                                <button  id="closeButton" class="btn-view w-auto mx-2" onclick="schedulePopup()" style="margin-top: 10px">View All</button>
                                <button  class="btn-accept w-auto mx-2" onclick="handleSlot(${requestScope.mentor_schedule.id},'accept','new')" style="margin-top: 10px"  >Accept new slot</button>
                                <button  class="btn-accept w-auto mx-2" onclick="handleSlot(${requestScope.mentor_schedule.id},'accept','remove')" style="margin-top: 10px"  >Accept remove slot</button>
                                <button  class="btn-reject w-auto mx-2" onclick="messagePopup()" style="margin-top: 10px"  >Reject new slot</button>
                                <button  class="btn-reject w-auto mx-2" onclick="handleSlot(${requestScope.mentor_schedule.id},'reject','remove')" style="margin-top: 10px"  >Reject remove slot</button>
                            </c:if>
                        </div>
                        <div class="d-flex justify-content-center " style="width: 100%">
                            <table class="time-table">
                                <thead class="time-table-head">
                                <tr class="">
                                    <th class="table-head">
                                        <input type="date" id="ymd" name="ymd" onchange="changeDate()" value="${requestScope.today}" />
                                    </th>
                                    <c:forEach items="${requestScope.week}" var="date">
                                        <th class="table-head">${date}</th>
                                    </c:forEach>
                                </tr>
                                <tr>
                                    <th class="table-head" >Slot</th>
                                    <th class="table-head">Monday</th>
                                    <th class="table-head">Tuesday</th>
                                    <th class="table-head">Wednesday</th>
                                    <th class="table-head">Thursday</th>
                                    <th class="table-head" >Friday</th>
                                    <th class="table-head">Saturday</th>
                                    <th class="table-head" >Sunday</th>
                                </tr>
                                </thead>
                                <tbody class="time-table-body" style="border: 1px #07AD90 solid">
                                <c:forEach items="${requestScope.slots}" var="slot">
                                    <tr>
                                        <td class="text-center table-data">
                                            <h6>Slot ${slot.id}</h6>
                                                ${slot.start_at}-${slot.end_at}
                                        </td>
                                        <c:forEach items="${requestScope.week}" var="date">
                                            <td class="table-data" >
                                                <c:forEach items="${requestScope.schedules}" var="s">
                                                    <c:if test="${ (s.slot.id == slot.id) && (s.date == date) and s.status.id != 16 }">
                                                        <div class="notes-container text-center ">
                                                            <i class="pin"></i>
                                                            <c:if test="${s.status.id == 1}">
                                                                <blockquote class="notes color-note font-monospace d-flex align-items-center " style="background-color: #F4E0B9">
                                                                    <div class="text-center w-100 fw-semibold">
                                                                        <span>New Slot</span>
                                                                    </div>
                                                                    <%--<div class="d-flex justify-content-center mt-4">
                                                                        <button class="btn-accept" style="margin: 0 auto" onclick="btnAccept(${s.id})">
                                                                            Accept
                                                                        </button>
                                                                    </div>--%>
                                                                </blockquote>
                                                            </c:if>
                                                            <c:if test="${s.status.id == 12}">
                                                                <blockquote class="notes color-note font-monospace d-flex align-items-center" style="background-color: #FF6347">
                                                                    <div class="text-center w-100 fw-bold">
                                                                        <span>${s.status.type}</span>
                                                                    </div>

                                                                </blockquote>
                                                            </c:if>
                                                            <c:if test="${s.status.id == 11}">
                                                                <blockquote class="notes color-note font-monospace d-flex align-items-center" style="background-color: #faad12">
                                                                    <div class="text-center w-100 fw-bold">
                                                                        <span>${s.status.type}</span>
                                                                    </div>
                                                                </blockquote>
                                                            </c:if>
                                                            <c:if test="${s.status.id == 15}">
                                                                <blockquote class="notes color-note font-monospace d-flex align-items-center" style="background-color: #f87517">
                                                                    <div class="text-center  fw-bold">
                                                                        <span>Waiting Remove</span>
                                                                    </div>
                                                                </blockquote>
                                                            </c:if>
                                                            <c:if test="${s.status.id == 9}">
                                                                <blockquote class="notes color-note font-monospace d-flex align-items-center" style="background-color: #9d9d9d">
                                                                    <div class="text-center  fw-bold">
                                                                        <span>Slot Booked</span>
                                                                    </div>
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
                </div>
            </div>
        </div>
    </div>
    <!-- page-body-wrapper ends -->
</div>
</script>
<!-- container-scroller -->
<!-- plugins:js -->
<script src="${pageContext.request.contextPath}/view/admin/assets/vendors/js/vendor.bundle.base.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<!-- endinject -->
<!-- Plugin js for this page -->
<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="${pageContext.request.contextPath}/view/admin/assets/js/off-canvas.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/template.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/settings.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/hoverable-collapse.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/todolist.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->
<!-- End custom js for this page-->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.2/mdb.umd.min.js"
></script>

<script>

    // function handleAccepted(){
    //     const form = document.getElementById("frm");
    //     form.submit();
    // }


     var isOpen = false;
    function schedulePopup() {
        if(isOpen == false){
            document.getElementById("nenmodal-1").classList.toggle("active");
            isOpen = true;
         }
         else{
            document.getElementById("nenmodal-1").classList.toggle("active");
           isOpen = false;
        }
    }

    var isOpenMessage = false;
    function messagePopup() {
        if(isOpenMessage == false){
            document.getElementById("modal_message").classList.toggle("active");
            isOpenMessage = true;
        }
        else{
            document.getElementById("modal_message").classList.toggle("active");
            isOpenMessage = false;
        }
    }
    //
    // document.getElementById('selectAll').addEventListener('change', function() {
    //     var checkboxes = document.querySelectorAll('.schedule-checkbox');
    //     for (var checkbox of checkboxes) {
    //         checkbox.checked = this.checked;
    //     }
    // });

    function scheduleHandle(mentorId,page,name){
        const mentor = document.getElementById("mentorInput").value;
        window.location.href = "manageSchedule?mentor=" + mentor + "&mentorId=" + mentorId + "&name=" + name + "&page=" + page
    }

    function searchHandle(){
        const mentor = document.getElementById("mentorInput").value;
        if(mentor == null || mentor == ""){
            window.location.href = "manageSchedule"
        }else{
            window.location.href = "manageSchedule?mentor=" + mentor
        }
    }
    function changeDate() {
        const ymd = document.getElementById("ymd").value;
        const mentor = document.getElementById("mentorInput").value;
        const name = document.getElementById("name").value;
        const mentorId = document.getElementById("mentorId").value;
        window.location.href = "manageSchedule?ymd=" + ymd + "&mentor=" + mentor +"&mentorId=" + mentorId + "&name=" + name
    }
    function clearHandler(){
        window.location.href = "manageSchedule"
    }

    function btnAccept(id){
        window.location.href = "manageSchedule?schedule=" + id
    }

    function  handleSlot(id,action,type){
        var message = "";
        if(type == "new" && action == "reject"){
            message = document.getElementById("message").value;
        }
        fetch("manageSchedule", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({mentorSchedule: id,
                                action:action,
                                type:type,
                                message:message}),
        }).then( response => {
            if(response.ok) {
                location.reload();
            } else {
                throw new Error('Network response was not ok.');
            }

        }).catch(
            error => {
                console.error('There has been a problem with your fetch operation:', error);
            }
        )
    }
</script>
<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>
