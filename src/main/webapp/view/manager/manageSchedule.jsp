
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 24-Jun-24
  Time: 2:43 PM
  To change this template use File | Settings | File Templates.
--%>ss

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
            font-size: 16px;
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

        .btn-accept{
            display: inline-block;
            padding: 5px 15px;
            font-size: 12px;
            color: white; /* White text color */
            background-color: #32cd32; /* Blue background color */
            border: none; /* No border */
            border-radius: 20px; /* Rounded edges */
            text-align: center;
            cursor: pointer;
            outline: none;
        }

        .btn-accept:hover{
            background-color: #35b835; /* Darker blue on hover */
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
                        <td>No</td>
                        <td>Mentor Name</td>
                        <td>Mentor Id</td>
                        <td>Slot Waiting</td>
                        <td>Schedule</td>
                        </thead>
                        <tbody >
                            <c:forEach items="${requestScope.mentors}" var="entry" varStatus="i">
                                <tr>
                                    <td>${i.index+1}</td>
                                    <td>${entry.key.account.name}</td>
                                    <td>${entry.key.account.id}</td>
                                    <td>${entry.value}</td>
                                    <td ><i class="fa-regular fa-calendar-days fa-2xl" style="color: #74C0FC; cursor: pointer" onclick="scheduleHandle('${entry.key.account.id}','${requestScope.page}','${entry.key.account.name}')"></i></td>
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
                    <h3>Schedule of Mentor ${requestScope.name}</h3>
                    <input type="hidden" id="name" value="${requestScope.name}"/>
                    <input type="hidden" id="mentorId" value="${requestScope.mentorId}"/>
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
                                            <c:if test="${ (s.slot.id == slot.id) && (s.date == date) }">
                                                <div class="notes-container text-center ">
                                                    <i class="pin"></i>
                                                    <c:if test="${s.status.id == 1}">
                                                        <blockquote class="notes color-note font-monospace " style="background-color: #F4E0B9">

                                                            <div class="text-center fw-semibold">
                                                                <span>${s.status.type}</span>
                                                            </div>
                                                            <div class="d-flex justify-content-center mt-4">
                                                                <button class="btn-accept" style="margin: 0 auto">
                                                                    Accept
                                                                </button>
                                                            </div>
                                                        </blockquote>
                                                    </c:if>

                                                    <c:if test="${s.status.id == 2}">
                                                        <blockquote class="notes color-note font-monospace" style="background-color: #FF6347">

                                                            <div class="text-center fw-bold">
                                                                <span>${s.status.type}</span>
                                                            </div>

                                                        </blockquote>
                                                    </c:if>

                                                    <c:if test="${s.status.id == 11}">
                                                        <blockquote class="notes color-note font-monospace" style="background-color: #faad12">

                                                            <div class="text-center fw-bold">
                                                                <span>${s.status.type}</span>
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
</script>
<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>
