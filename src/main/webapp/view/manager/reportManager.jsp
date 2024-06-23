<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 5/29/2024
  Time: 4:53 PM
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
            z-index:1;
            display:none;
        }
        /* CSS bảng nội dung Modal */
        .nenmodal .ndmodal {
            position:absolute;
            top:50%;
            left:50%;
            transform:translate(-50%,-50%) scale(0);
            background:#fff;
           /*width:600px;*/
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

        .btn-accept:hover{
            background-color: #0056b3; /* Darker blue on hover */
        }
    </style>
</head>
<body>
<div class="nenmodal" id="nenmodal-1">
    <div class="nenmodal2"></div>
    <div class="ndmodal">
        <div class="closemodal"><button onclick="momodal()">X</button></div>
        <div class="titlemodal">Reason of report</div>
        <div style="text-align: start; margin-left: 20px">
            <span id="mentee" ></span><br/>
            <span id="mentor" ></span>
        </div>
        <form id="frm" method="POST" action="report">
            <input name="report_id" id="report_id" hidden/>
        </form>
        <div class="d-block">
            <div>
                <textarea readonly id="reason" cols="30" rows="10" style="width: 500px"></textarea>
            </div>

            <span style="font-size: 12px">(Please check carefully before making decisions)</span>
        </div>
        <div class="m-3">
            <button onclick="handleAccepted()" type="button" class="btn-accept"  >Accepted</button>
        </div>
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
                <h2>Report Manager</h2>
                <form method="get" action="report">
                    <div class="d-flex justify-content-between">
                        <div class="d-flex align-items-center">
                            <div >
                                <input id="mentorInput" class="mb-1" type="text" name="mentor"  placeholder="Mentor name" value="${requestScope.mentor}" style="width: 200px" />
                                <input id="menteeInput" class="mt-1"type="text" name="mentee" placeholder="Mentee name" value="${requestScope.mentee}" style="width: 200px" />
                            </div>
                            <div class="mx-4 date-range">
                                 From <input id="from" name="from" type="date" value="${requestScope.from}" /> - <input id="to" name="to" type="date" value="${requestScope.to}" />
                            </div>
                            <div class="d-flex">
                                <button type="submit"  class=" d-flex align-items-center btn-blue" style="height: 30px"  >
                                    Search <i class="fa-solid fa-magnifying-glass fa-xs"  style="margin-left: 10px"></i>
                                </button>
                                <button type="button" onclick="clearHandler()" class="d-flex align-items-center btn-red" style="height: 30px ; margin-left: 10px" >
                                    Clear
                                </button>
                            </div>
                        </div>

                    </div>
                    <div class="select d-flex align-items-end justify-content-end mt-3">
                        <select id="slStatus" name="status" onchange="changeHandle()">
                            <option value="" disabled selected>Status</option>
                            <option value="0" ${requestScope.status == 0 ? "selected" : ""}>All</option>
                            <option value="1" ${requestScope.status == 1 ? "selected" : ""}>Processing</option>
                            <option value="3" ${requestScope.status == 3 ? "selected" : ""}>Done</option>
                        </select>
                    </div>
                </form>
                <div class="table-responsive" style="border: 1px solid black;border-radius: 10px; margin-top: 15px">
                    <table class=" table table-hover"  id="user-table" style="width: 100%; border-radius: 5px">
                        <thead class="table-dark">
                        <td>ID</td>
                        <td>Date</td>
                        <td>Mentee ID</td>
                        <td>Mentee Name</td>
                        <td>Mentor ID</td>
                        <td>Mentor Name</td>
                        <td>Status</td>
                        </thead>
                        <tbody >

                        <c:forEach items="${requestScope.reports}" var="report">
                            <tr>
                                <c:if test="${report.status.id == 1}">
                                    <td style="cursor: pointer" onclick="momodal('${report.id}','${report.reason}','${report.mentee.account.name}','${report.mentor.account.name}')" >
                                        <i class="text-decoration-underline text-primary" >${report.id}</i>
                                    </td>
                                </c:if>
                                <c:if test="${report.status.id == 3}">
                                    <td>${report.id}</td>
                                </c:if>
                                <td>${report.date}</td>
                                <td>${report.mentee.account.id}</td>
                                <td>${report.mentee.account.name}</td>
                                <td>${report.mentor.account.id}</td>
                                <td>${report.mentor.account.name}</td>
                                <td>${report.status.type}</td>
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
                                    <a class="page-link" href="?page=${requestScope.page-1}&mentor=${requestScope.mentor}&mentee=${requestScope.mentee}&from=${requestScope.from}&to=${requestScope.to}&status=${requestScope.status}"
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
                                               href="?page=${count}&mentor=${requestScope.mentor}&mentee=${requestScope.mentee}&from=${requestScope.from}&to=${requestScope.to}&status=${requestScope.status}">${count}
                                            </a>
                                        </li>
                                    </c:if>
                                </c:forEach>
                                <li class="page-item">
                                    <a class="page-link <c:if test="${requestScope.page==requestScope.end_page  or requestScope.end_page == 0}"> disabled </c:if>"
                                       href="?page=${requestScope.page+1}&mentor=${requestScope.mentor}&mentee=${requestScope.mentee}&from=${requestScope.from}&to=${requestScope.to}&status=${requestScope.status}"
                                            <c:if test="${requestScope.page==requestScope.end_page or requestScope.end_page == 0 }"> tabindex="-1" </c:if>
                                    >Next</a>
                                </li>
                            </ul>
                        </nav>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- page-body-wrapper ends -->
</div>

<script>

    var isOpen = false;
    function momodal(id,reason,mentee,mentor) {
        if(isOpen == false){
            document.getElementById('report_id').value= id;
            document.getElementById('reason').value=reason;
            document.getElementById('mentee').innerHTML = "Reporter: " +  mentee;
            document.getElementById('mentor').innerHTML = "Person being reported: " +  mentor;
            document.getElementById("nenmodal-1").classList.toggle("active");
            isOpen = true;
        }
        else{
            document.getElementById('mentee').innerHTML = "";
            document.getElementById('mentor').innerHTML = "";
            document.getElementById("nenmodal-1").classList.toggle("active");
            isOpen = false;
        }

    }

    function handleAccepted() {
        const  id =  document.getElementById('report_id').value;

        const  form = document.getElementById('frm');

        const action = document.createElement('input');
        action.type = 'hidden';
        action.name = 'action';
        action.value = 'accept';

        form.appendChild(action);
        console.log(form);
        form.submit();
    }

    function changeHandle(){
        const mentee = document.getElementById('menteeInput').value;
        const mentor = document.getElementById('mentorInput').value;
        const from = document.getElementById('from').value;
        const to = document.getElementById('to').value;
        const status = document.getElementById('slStatus').value;
        window.location.href = "report?mentor=" + mentor + "&mentee=" + mentee + "&from=" + from + "&to=" + to + "&status=" + status;
    }

    function clearHandler(){
        window.location.href = "report";
    }
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
<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>
