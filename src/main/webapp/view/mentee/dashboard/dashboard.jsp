<%--
  Created by IntelliJ IDEA.
  User: HUY
  Date: 02-Jun-24
  Time: 10:12 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
            box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
            border-radius: 5px;
            padding: 15px;
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

        .input-comment textarea{
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

        .label_comment{
            width: 70%;
            margin: 0 auto;
            font-size: 15px;
            font-weight: bold;
            align-items: start ;
        }

        .input-reason{
            margin: 10px 0 40px;
        }

        .input-comment{
            margin: 10px 0 40px;
        }

    </style>

    <style>
        .stars-container{
            margin: 10px 0;
        }

       .stars{
           display: flex;
           justify-content: center;
           list-style: none;
           padding: 0;
           margin: 0;
       }

       .star{
           cursor: pointer;
           padding: 0 7px;
           font-size: 20px;
           color: #ccc;
           transition: color 0.2s, transform 0.2s;
       }

       .selected, .star:hover{
           color: #ffcc00;
           transform: scale(1.2);
       }


    </style>
    <script src="https://www.gstatic.com/charts/loader.js"></script>
</head>
<body>
<jsp:include page="../../common/header.jsp"></jsp:include>
<div class="rating-modal" id="rating-modal">
    <div class="blur-bg"></div>
    <div class="content-modal">
        <div class="titlemodal">Feedback Mentor</div>
        <div id="rating-to-mentor"></div>
        <div class="stars-container">
            <ul class="stars">
                <li class="star" data-value="1">
                    <i class="fas fa-star"></i>
                </li>
                <li class="star" data-value="2">
                    <i class="fas fa-star"></i>
                </li>
                <li class="star" data-value="3">
                    <i class="fas fa-star"></i>
                </li>
                <li class="star" data-value="4">
                    <i class="fas fa-star"></i>
                </li>
                <li class="star" data-value="5">
                    <i class="fas fa-star"></i>
                </li>
            </ul>
        </div>
        <div class="input-comment">
            <div class="d-flex label_comment">
                <span >Comment </span>
            </div>
            <textarea class="form-control" placeholder="Write your comment" name="comment" id="comment" rows="6"></textarea>
        </div>
        <div class="closemodal">
            <button type="button" onclick="ratingHandle()" class="btn btn-outline-dark btn-rounded" data-mdb-ripple-init  data-mdb-ripple-color="dark">Cancel</button>
            <button type="button"  onclick="sendRating()" class="btn btn-outline-primary btn-rounded" data-mdb-ripple-init  data-mdb-ripple-color="dark">Send</button>
        </div>
    </div>
</div>

<div class="report-modal" id="report-modal">
    <div class="blur-bg"></div>
    <div class="content-modal">
        <div class="titlemodal" style="color: darkred">Report Mentor </div>
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
            <button type="button" onclick="sendReport()" class="btn btn-outline-danger  btn-rounded" data-mdb-ripple-init  data-mdb-ripple-color="dark">Report</button>
        </div>

    </div>
</div>


<div class="">
    <input type="text" value="${requestScope.success}" id="success" hidden="hidden"/>
    <div class="d-flex justify-content-between top-content">
        <div class="statistic ">
            <h3 style="color: #07AD90" class="text-center">Dashboard</h3>
            <div class="d-flex justify-content-between">
                <div class="mt-4">
                    <h5 id="total-booking">Total request: ${requestScope.total_book} </h5>
                    <span id="total-slot">Total slot: ${requestScope.total_slot}</span>
                    <br/>
                    <span>Total mentor: ${requestScope.mentorList.size()}</span>
                    <br/>
                    <span >Total spend money: <fmt:formatNumber value="${requestScope.total_amount}" type="number" maxFractionDigits="0" />₫</span>
                </div>
                <div id="myChart" style="width:70%; max-width:400px; height:300px;">
                </div>
            </div>
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
                        <td> <fmt:formatNumber value="${mentor.price}" type="number" maxFractionDigits="0" />₫</td>

                        <td>
                            <div style="cursor: pointer; text-align: center" onclick="ratingHandle('${mentor.account.id}','${mentor.account.name}')"  >
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
                <th >Amount</th>
                <th >Skill </th>
                <th>From</th>
                <th>To</th>
                <th>Status</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.bookingList}" var="booking" varStatus="i">


            <tr>
                <td >${i.index+1}</td>
                <td >${booking.date}</td>
                <td >
                        ${booking.mentor.account.name}
                        <br/>
                       <i>${booking.mentor.account.email}</i>
                </td>
                <td>
                    <fmt:formatNumber value="${booking.amount}" type="number" maxFractionDigits="0" />₫
                </td>
                <td > <img width="20px" src="${pageContext.request.contextPath}/${booking.level_skills.skill.src_icon}" alt=""/>
                       &nbsp ${booking.level_skills.skill.name} for ${booking.level_skills.level.name}</td>
                <td >${booking.startDate}</td>
                <td >${booking.endDate}</td>
                <td>${booking.status.type}</td>


            </tr>
            </c:forEach>

            </tbody>
        </table>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/mentee/rating.js"></script>
<script src="${pageContext.request.contextPath}/js/mentee/report.js"></script>
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

    document.addEventListener("DOMContentLoaded", function() {
        fetch('dashboard', {
            method: 'POST',
           /* headers: {
                "Content-Type": "application/json",
            },*/
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(response.statusText);
                }

                return response.json();
            })
            .then(data => {
                const mapStatistic = new Map();

                for (const [key, value] of Object.entries(data.statistic)) {
                    mapStatistic.set(key.trim(), value);
                }

                drawChart(mapStatistic);
            })
            .catch(error => {
                console.error('Error fetching data:', error);
            });
    });

    google.charts.load('current', {'packages':['corechart']});
    google.charts.setOnLoadCallback(drawChart);

    function drawChart(statistic) {
        const data = google.visualization.arrayToDataTable([
            ['Booking', 'Status'],
            ['Done',statistic.get("Done") || 0],
            ['Reject',statistic.get("Reject") || 0],
            ['Cancel',statistic.get("Cancel") || 0],
            ['Accept',statistic.get("Accept") || 0],
            ['Processing',statistic.get("Processing") || 0]
        ]);

// Set Options
        const options = {
            title:'Statistic booking',
        };

// Draw
        const chart = new google.visualization.PieChart(document.getElementById('myChart'));
        chart.draw(data, options);

    }
</script>

<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
<%--<script src="${pageContext.request.contextPath}/js/common/rating.js"></script>--%>
</body>
</html>