<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
    <!-- Font Awesome -->
    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
    <!-- Fontawesome CDN Link For Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
    <style>
        .search-container {
            margin: 20px 50px !important;
            display: flex !important;
            align-items: center !important;
            justify-content: space-between !important;
        }
        .search-container .form-control {
            margin-right: 15px;
        }
        .search-container .form-group {
            flex-grow: 1;
            margin-right: 15px;
        }
        .table-containers {
            display: flex;
            justify-content: center;
        }
        .table-containers {
            width: 100%;
            border-radius: 5px;
            margin-left: 50px;
        }
    </style>
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
            width:600px;
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
            font-size:20px;
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
        .green-background {
            background-color: green;
        }

        .red-background {
            background-color: red;
        }

        .request-id-link {
            color: blue;
            cursor: pointer;
            text-decoration: underline;
        }


    </style>
</head>
<body>
<div class="container-scroller">
    <!-- partial:partials/_navbar.jsp -->
    <jsp:include page="../admin/partials/_navbar.jsp"></jsp:include>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial:partials/_sidebar.jsp -->
        <jsp:include page="../admin/partials/_sidebar.jsp"></jsp:include>
        <!-- partial -->
        <div class="nenmodal" id="nenmodal-1">
            <div class="nenmodal2"></div>
            <div class="ndmodal">
                <div class="closemodal"><button onclick="momodal()">×</button></div>
                <div class="titlemodal">Slot Booking Detail</div>

                <div class="contentmodal">
                    <c:forEach items="${requestScope.listSchedule}" var="listsch">
                        <c:if test="${listsch.booking.status.id == 1 || listsch.booking.status.id == 11 || listsch.booking.status.id == 3}">
                            <c:if test="${listsch.attend}">
                        <div style="border-radius: 8px; margin-top: 20px; padding: 20px 0; box-shadow: 1px 2px 4px rgba(0, 0, 0, 0.2); background-color: #66cdbb">
                              Slot: ${listsch.schedule.slot.id}&ensp;
                            ${listsch.schedule.slot.start_at}
                               ${listsch.schedule.slot.end_at})&ensp;
                            Date: ${listsch.schedule.date}&ensp;
                            <img style="width: 25px" src="${pageContext.request.contextPath}/${listsch.booking.level_skills.skill.src_icon}" >
                             ${listsch.booking.level_skills.level.name}
                            Status: attended
                        </div>
                            </c:if>
                            <c:if test="${!listsch.attend}">
                        <div style="border-radius: 8px; margin-top: 20px; padding: 20px 0; box-shadow: 1px 2px 4px rgba(0, 0, 0, 0.2); background-color: #e37f5a">
                            Slot: ${listsch.schedule.slot.id}&ensp;
                            (${listsch.schedule.slot.start_at}
                                ${listsch.schedule.slot.end_at})&ensp;
                            Date: ${listsch.schedule.date}&ensp;
                            <img style="width: 25px" src="${pageContext.request.contextPath}/${listsch.booking.level_skills.skill.src_icon}"> ${listsch.booking.level_skills.level.name}
                            Status: absent
                        </div>
                            </c:if>
                        </c:if>
                        <c:if test="${listsch.booking.status.id == 2 || listsch.booking.status.id == 12}">
                            <div style="border-radius: 8px; margin-top: 20px; padding: 20px 0; box-shadow: 1px 2px 4px rgba(0, 0, 0, 0.2); background-color: #ffcc00">
                                Slot: ${listsch.schedule.slot.id}&ensp;
                                (${listsch.schedule.slot.start_at}
                                    ${listsch.schedule.slot.end_at})&ensp;
                                Date: ${listsch.schedule.date}&ensp;
                                <img style="width: 25px" src="${pageContext.request.contextPath}/${listsch.booking.level_skills.skill.src_icon}"> ${listsch.booking.level_skills.level.name}
                            </div>
                        </c:if>
                    </c:forEach>

                </div>
            </div>
        </div>
        <input id="isOpenModal" value="${requestScope.isOpenModal}" type="hidden" />

        <div>
            <form action="request" method="get">
                <div class="search-container">
                    <div class="form-group">
                        <input name="name" id="name" type="text" value="${requestScope.name}" class="form-control" placeholder="Search by username" />
                    </div>
                    <div class="form-group">
                        <select name="status" class="form-control" id="filter-status">
                            <option <c:if test="${requestScope.stId == 0}">selected</c:if> value="0">All Statuses</option>
                            <option <c:if test="${requestScope.stId == 12}">selected</c:if> value="12">Cancel</option>
                            <option <c:if test="${requestScope.stId == 2}">selected</c:if> value="2">Reject</option>
                            <option <c:if test="${requestScope.stId == 1}">selected</c:if> value="1">Process</option>
                            <option <c:if test="${requestScope.stId == 11}">selected</c:if>    value="11">Accept</option>
                            <option <c:if test="${requestScope.stId == 3}">selected</c:if> value="3">Done</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <input name="stDate" value="${requestScope.stDate}" type="date" class="form-control" id="start-date">
                    </div>
                    <div class="form-group">
                        <input name="endDate" value="${requestScope.endDate}" type="date" class="form-control" id="end-date">
                    </div>
                    <div class="form-group">
                        <button type="submit" class="btn btn-primary" id="search-button">Search</button>
                    </div>
                </div>

            </form>
            <div class="table-containers">
                <table class="table table-hover" id="user-table" style="width: 100%; border-radius: 5px">
                    <thead class="table-dark">
                    <tr>
                        <th scope="col">STT</th>
                        <th scope="col">Booking ID</th>
                        <th scope="col">Name Mentee</th>
                        <th scope="col">Status</th>
                        <th scope="col">Title Request</th>
                        <th scope="col">Amount</th>
                        <th scope="col">Create Date</th>
                        <th scope="col">Name Mentor</th>
                        <th scope="col">Skill</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="count" value="1"/>
                    <c:forEach items="${requestScope.list}" var="request">
                        <tr>
                            <td>${count}</td>
                            <c:set var="count" value="${count+1}"/>
                            <td><a  class="request-id-link" onclick="momodal(${page}, ${request.status.id},  ${request.id})">${request.id}</a></td>
                            <td>${request.mentee.account.name}</td>
                            <td>${request.status.type}</td>
                            <td>${request.description}</td>
                            <td>${request.amount}</td>
                            <td>${request.date}</td>
                            <td>${request.mentor.account.name}</td>
                            <td>${request.level_skills.skill.name} for ${request.level_skills.level.name}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <div style="display: flex; justify-content: end">
                <nav aria-label="Page navigation example">
                    <ul class="pagination justify-content-end">
                        <li class="page-item ${page == 1 ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${page - 1}&name=${name}&status=${status}&stDate=${stDate}&endDate=${endDate}" ${page == 1 ? 'tabindex="-1"' : ''}>Previous</a>
                        </li>
                        <c:forEach var="count" begin="1" end="${end_page}">
                            <li class="page-item ${page == count ? 'active' : ''}">
                                <a class="page-link" href="?page=${count}&name=${name}&status=${status}&stDate=${stDate}&endDate=${endDate}">${count}</a>
                            </li>
                        </c:forEach>
                        <li class="page-item ${page == end_page || end_page == 0 ? 'disabled' : ''}">
                            <a class="page-link" href="?page=${page + 1}&name=${name}&status=${status}&stDate=${stDate}&endDate=${endDate}" ${page == end_page || end_page == 0 ? 'tabindex="-1"' : ''}>Next</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>

</body>
<!-- container-scroller -->
<!-- plugins:js -->
<script>
    var statusModel = false;

    document.addEventListener("DOMContentLoaded", function() {
        const isOpenModal = document.getElementById('isOpenModal').value;
        console.log(isOpenModal);

        if(isOpenModal === "true"){


            document.getElementById("nenmodal-1").classList.toggle("active");

            statusModel = true;
        }
    });

    function momodal(page,stId,  bkId){
        if(statusModel == false){
            const stdate =  document.getElementById('start-date').value;
            const  name = document.getElementById('name').value;
            const endate =  document.getElementById('end-date').value;
            document.getElementById("nenmodal-1").classList.toggle("active");
            statusModel = true;
            //write code here
            const mainStatus = document.getElementById('filter-status').value;

            window.location.href = "request?page=" + page + "&name=" + name + "&status=" + mainStatus + "&stDate=" + stdate + "&endDate=" + endate +"&book_status=" + stId  +"&bkId="  + bkId + "&isOpenModal=true";

        }else{
            document.getElementById("nenmodal-1").classList.toggle("active");
            statusModel = false;
        }
    }
</script>
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

<%--<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"--%>
<%--        integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p"--%>
<%--        crossorigin="anonymous"></script>--%>
<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>--%>
</body>
</html>

