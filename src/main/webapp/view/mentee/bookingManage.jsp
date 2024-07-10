<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of requests</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />

    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
    <style>
        .table-container {
            max-height: 400px;
            overflow-y: auto;
            margin-top: 20px;
        }
        thead th {
            position: sticky;
            top: 0;
            background-color: #f8f9fa;
            z-index: 1;
        }
        .back{
            margin-top: 20px;
            margin-left: 100px;
            margin-bottom: 50px;
        }
    </style>
</head>
<body class="body">
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="back">
    <jsp:include page="../common/backBtn.jsp"></jsp:include>
</div>
<div class="container">
    <div style="height: 70%">
        <h1 style="color: #179b81; display: flex; justify-content: center">Processing</h1>
        <div class="table-container">
            <table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th style="background:  #179b81">Mentor</th>
                    <th style="background:  #179b81">Price</th>
                    <th style="background:  #179b81">From Date</th>
                    <th style="background:  #179b81">To Date</th>
                    <th style="background:  #179b81">Description</th>
                    <th style="background:  #179b81">Skill</th>
                    <th style="background: #179b81">Create Date</th>
                    <th style="background: #179b81">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.bookingList}" var="booking">
                    <tr>
                        <td>${booking.mentor.account.name}</td>
                        <td><fmt:formatNumber value="${booking.amount}" type="number" maxFractionDigits="0" />₫</td>
                        <td>${booking.startDate}</td>
                        <td>${booking.endDate}</td>
                        <td>${booking.description}</td>
                        <td>${booking.level_skills.skill.name} for ${booking.level_skills.level.name}</td>
                        <td>${booking.date}</td>
                        <td style="display: flex; justify-content: center; margin-top: 8px">
                            <i class="fa-solid fa-delete-left fa-rotate-180 fa-2xl" onclick="confirmDelete(${booking.id})" style="color: #ff0000;"></i>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div style="height: 70%">
        <h1 style="color: #179b81; display: flex; justify-content: center">Cancel And Reject History</h1>
        <div class="table-container">
            <table id="example2" class="table table-striped table-bordered" cellspacing="0" width="100%">
                <thead>
                <tr>
                    <th style="background:  #179b81">Mentor</th>
                    <th style="background:  #179b81">Price</th>
                    <th style="background:  #179b81">From Date</th>
                    <th style="background:  #179b81">To Date</th>
                    <th style="background:  #179b81">Description</th>
                    <th style="background:  #179b81">Skill</th>
                    <th style="background:  #179b81">Status</th>
                    <th style="background: #179b81">Create Date</th>
                    <th style="background: #179b81">Re-Booking</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.bookingListC}" var="booking">
                    <tr>
                        <td>${booking.mentor.account.name}</td>
                        <td><fmt:formatNumber value="${booking.amount}" type="number" maxFractionDigits="0" />₫</td>
                        <td>${booking.startDate}</td>
                        <td>${booking.endDate}</td>
                        <td>${booking.description}</td>
                        <td>${booking.level_skills.skill.name} for ${booking.level_skills.level.name}</td>
                        <td>${booking.status.type}</td>
                        <td>${booking.date}</td>
                        <td>
                            <button type="button" class="btn btn-outline-primary btn-sm"
                                    onclick="editHandle('${booking.mentor.account.id}','${booking.level_skills.skill.name}','${booking.level_skills.level.name}', '${booking.id}')"
                            >Re-Book</button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function confirmDelete(bookingId) {
        Swal.fire({
            title: "Are you sure?",
            text: "Do you want to cancel this booking?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes",
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Deleted!",
                    text: "Your booking has been deleted.",
                    icon: "success",
                    timer: 2000,
                    showConfirmButton: false
                });
                setTimeout(() => {
                    location.href = "viewBooking?idb=" + bookingId;
                }, 2000);
            }
        });
    }

    function editHandle(mentorId, skill_name, level, bookId) {
        console.log(mentorId);
        const skill = encodeURIComponent(skill_name);
        window.location.href = 'booking-schedule?mentorId='+ mentorId +'&skill='+ skill +'&level='+ level+'&bookId=' + bookId ;
    }
</script>
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.umd.min.js"
></script>
</body>
</html>
