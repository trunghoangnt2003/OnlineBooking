<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 02-Jun-24
  Time: 10:12 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Fixed Header Table</title>


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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentee/dashboard/dashboard.css">


    <style>

        .top-content{
            width: 90%;
            margin: 30px auto;
        }

        .statistic{
            width: 60%;
            border: 1px solid #07AD90;
        }
        .list-mentor{
            width: 35%;
            border: 1px solid #07AD90;
        }


        .table-rating {
            width: 100%;
            margin: 0 auto;
            /*background-color: #f3f3f3;*/
            border: 1px solid rgb(176, 237, 215);
        }

        .table-rating thead {
            display: block;
            width: 100%;
        }

        .table-rating thead th {
            float: left;
            background-color: rgb(176, 237, 215);
            /*border: 1px solid #07AD90;*/
            padding: 8px;
            text-align: left;
            border-bottom-style: hidden;
        }

        .table-rating tbody {
            display: block;
            height: 350px;
            overflow-y: auto;
            width: 100%;
            scrollbar-width: thin;
            scrollbar-color: rgb(148, 232, 204) #f3f3f3;
        }



        .table-rating tbody tr {
            display: flex;
            width: 100%;
        }

        .table-rating tbody td {
            /*border: 1px solid #07AD90;*/
            background-color: rgb(246, 255, 253);
            padding: 8px;
            text-align: left;
            flex: 1;
        }

        .table-rating thead tr {
            display: flex;
            width: 100%;
        }

        .table-rating thead th {
            flex: 1;
        }

        .table-rating thead th:nth-child(1),
        .table-rating tbody td:nth-child(1) {
            flex: 0 0 10%;
        }

        .table-rating thead th:nth-child(2),
        .table-rating tbody td:nth-child(2) {
            flex: 0 0 45%;
        }
    </style>

    <style>
        /* CSS nền hiển thị Modal */
        .report-modal .blur-bg,
        .rating-modal .blur-bg {
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
        .report-modal .content-modal,
        .rating-modal .content-modal {
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
            .rating-modal .content-modal {width:90%;}
        }
        /* CSS bao bọc của nút tắt Modal */
        /*.rating-modal .closemodal {*/
        /*    text-align:center;*/
        /*    margin-top:-40px;*/
        /*    margin-bottom:10px;*/
        /*}*/
        /* CSS tieu de của Modal */
        .titlemodal{
            font-weight:bold;
            font-size:30px;
            margin-bottom:10px;
        }
        /* CSS nút tắt modal */
        /*.closemodal button{*/
        /*    width:40px;*/
        /*    height:40px;*/
        /*    font-size:30px;*/
        /*    padding:0px;*/
        /*    border-radius:100%;*/
        /*    background:#333;*/
        /*    color:#fff;*/
        /*    border:none;*/
        /*}*/
        .report-modal.active .blur-bg ,
        .rating-modal.active .blur-bg {
            display:block;
        }
        /* CSS hiệu ứng hiển thị Modal */
        .report-modal.active .content-modal ,
        .rating-modal.active .content-modal {
            transition:all 300ms ease-in-out;
            transform:translate(-50%,-50%) scale(1);
        }

        .input-reason textarea{
            width: 70%;
            margin: 0 auto;
        }

        .label_reason{
            width: 70%;
            margin: 0 auto;
            font-size: 15px;
            font-weight: bold;
            align-items: start ;
        }

        .input-reason{
            margin: 10px 0 40px;
        }
    </style>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/rating.css"/>
</head>
<body>
<jsp:include page="../../common/header.jsp"></jsp:include>
<div class="rating-modal" id="rating-modal">
    <div class="blur-bg"></div>
    <div class="content-modal">

        <div class="titlemodal">Feedback Mentor</div>
        Nội dung hiển thị trong Modal của bạn
        <div class="closemodal">
            <button type="button" onclick="ratingHandle()" class="btn btn-outline-dark btn-rounded" data-mdb-ripple-init  data-mdb-ripple-color="dark">Cancel</button>
            <button type="button" class="btn btn-outline-primary btn-rounded" data-mdb-ripple-init  data-mdb-ripple-color="dark">Send</button>
        </div>
    </div>
</div>

<div class="report-modal" id="report-modal">
    <div class="blur-bg"></div>
    <div class="content-modal">

        <div class="titlemodal" style="color: darkred">Report Mentor </div>
        <form action="dashboard" method="POST" id="frm-report">
            <div class="to-mentor" id="to-mentor"></div>
            <div class="input-reason">
                   <div class="d-flex label_reason">
                       <span >Reason </span>
                   </div>
                    <textarea class="form-control" placeholder="Write your reason" name="reason" id="reason" rows="6"></textarea>
                    <span style="font-size: 12px">(The system will check your report and take action)</span>
            </div>



        <div class="closemodal">
            <button type="button" onclick="reportHandle()" class="btn btn-outline-dark btn-rounded" data-mdb-ripple-init  data-mdb-ripple-color="dark">Cancel</button>
            <button type="button" onclick="submitHandle()" class="btn btn-outline-danger  btn-rounded" data-mdb-ripple-init  data-mdb-ripple-color="dark">Report</button>
        </div>
        </form>
    </div>
</div>


<div class="">
    <input type="text" value="${requestScope.success}" id="success" hidden="hidden"/>
    <div class="d-flex justify-content-between top-content">
        <div class="statistic">
        </div>
        <div class="list-mentor">
            <h4 style="color: #07AD90" class="text-center">Rating mentor</h4>
            <table class="table table-rating">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Name</th>
<%--                        <th>Email</th>--%>
                        <th>Price</th>
                        <th>Feedback</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${requestScope.mentorList}" var="mentor" varStatus="i">
                    <tr>
                        <td>${i.index+1}</td>
                        <td>${mentor.account.name}</td>
<%--                        <td>${mentor.account.email}</td>--%>
                        <td>${mentor.price} $</td>

                        <td>
                            <div style="cursor: pointer; text-align: center" onclick="ratingHandle()"  >
                                <i class="fa-regular fa-face-smile" style="color: #0CA4F6;"></i>
                            </div>
                        </td>
                        <td>
                            <div style="cursor: pointer; text-align: center"    onclick="reportHandle('${mentor.account.id}','${mentor.account.name}')">
                                <i class="fa-solid fa-triangle-exclamation" style="color: #dd0808;"></i>
                            </div>

                        </td>
                    </tr>
                     </c:forEach>

                </tbody>
            </table>
        </div>
    </div>
    <div class="table-history">
        <h3 style="color: #07AD90" class="text-center">Booking history</h3>
        <table class="table table-fixed" >
            <thead>
            <tr>
                <th>No</th>
                <th>Create Time</th>
                <th >Mentor</th>
                <th>Email</th>
                <th >Amount</th>
                <th >Skill </th>
                <th>From</th>
                <th>To</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.bookingList}" var="booking" varStatus="i">


            <tr>
                <td >${i.index+1}</td>
                <td >${booking.date}</td>
                <td >${booking.mentor.account.name}</td>
                <td>${booking.mentor.account.email}</td>
                <td >${booking.amount} $</td>
                <td > <img width="20px" src="${pageContext.request.contextPath}/${booking.level_skills.skill.src_icon}" alt=""/>
                        ${booking.level_skills.skill.name} for ${booking.level_skills.level.name}</td>
                <td >${booking.startDate}</td>
                <td >${booking.endDate}</td>


            </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>
<script>

    document.addEventListener("DOMContentLoaded", function() {
        const success = document.getElementById("success").value;
        if (success) {
            Swal.fire({
                position: "top-end",
                icon: "success",
                // title: "Your work has been saved",
                showConfirmButton: false,
                timer: 1500
            });
        }
    })

    function ratingHandle(){
        document.getElementById("rating-modal").classList.toggle("active");
    }
    function reportHandle(mentor_id,mentor){
        document.getElementById("report-modal").classList.toggle("active");
        const to = document.getElementById("to-mentor");
        const reason = document.getElementById('reason');
        reason.value = "";
        to.innerHTML = "<span class='text-center'>Report mentor: <b>"+mentor+"</b></span>";
        const input_id = document.createElement('input');
        input_id.type = "hidden";
        input_id.name = "mentor_id";
        input_id.value = mentor_id;

        const isReason = document.createElement('input');
        isReason.type = 'text';
        isReason.name = 'isReason';
        isReason.value = "reason";
        isReason.style.display = 'none';

        to.append(isReason);
        to.appendChild(input_id);
    }

    function submitHandle(){

        Swal.fire({
            title: "Are you sure?",
            text: "Do you want to report this mentor?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes"
        }).then((result) => {
            if (result.isConfirmed) {
                const reason = document.getElementById("reason").value;
                if(reason == ""){
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: " Please input reason!",
                    });
                }else{
                    document.getElementById("frm-report").submit();
                }

            }
        });


    }
</script>
<script src="${pageContext.request.contextPath}/js/common/rating.js"></script>
</body>
</html>