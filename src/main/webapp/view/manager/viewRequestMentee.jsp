<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"
            rel="stylesheet"
    />
    <!-- Custom CSS -->
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
        .search-container .text-input-container {
            flex-grow: 3;
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
            width: 95%;
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
    </style>
    <style></style>
</head>
<body>
<div class="nenmodal" id="nenmodal-1">
    <div class="nenmodal2"></div>
    <div class="ndmodal">
        <div class="closemodal"><button onclick="momodal()">×</button></div>
        <div class="titlemodal">Slot Booking Detail</div>
        <div class="contentmodal">
            <c:forEach items="${requestScope.listSchedule}" var="listsch">
                <div style="border-radius: 8px; margin-top: 20px; padding: 20px 0; box-shadow: 1px 2px 4px rgba(0, 0, 0, 0.2); background-color: #66cdbb">
                   Slot: ${listsch.schedule.slot.id}&ensp;
                   (${listsch.schedule.slot.start_at}
                        ${listsch.schedule.slot.end_at})&ensp;
                    Date: ${listsch.schedule.date}&ensp;
                    <img style="width: 25px" src="${pageContext.request.contextPath}/${listsch.booking.level_skills.skill.src_icon}"> ${listsch.booking.level_skills.level.name}
                </div>
            </c:forEach>
        </div>
    </div>
</div>

<input id="isOpenModal" value="${requestScope.isOpenModal}" type="hidden" />

<div>
<form action="request" method="get">
<div class="search-container">
        <div class="text-input-container">
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
                <td><a onclick="momodal(${page}, ${request.status.id},  ${request.id})">${request.id}</a></td>
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
</body>
</html>
