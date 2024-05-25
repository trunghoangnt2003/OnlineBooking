<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentee/viewbooking.css">
    <title>List of requests</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet" />

    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet" />

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="body">
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="containers">
    <h1 class="h1">List of requests</h1>
    <div>
        <table>
            <tr>
                <th>Mentor</th>
                <th>From Date</th>
                <th>To Date</th>
                <th>Description</th>
                <th>Skill</th>
                <th>Create Date</th>
                <th>Actions</th>
            </tr>
            <c:forEach items="${requestScope.bookingList}" var="booking">
                <tr>
                    <td>${booking.mentor.account.name}</td>
                    <td>${booking.startDate}</td>
                    <td>${booking.endDate}</td>
                    <td>${booking.description}</td>
                    <td>${booking.level_skills.skill.name} for ${booking.level_skills.level.name}</td>
                    <td>${booking.date}</td>
                    <td>
                        <button class="btn-update" onclick="location.href='updateBooking?id=${booking.id}'">Update</button>
                        <button class="btn-delete" onclick="confirmDelete(${booking.id})">Delete</button>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function confirmDelete(bookingId) {
        Swal.fire({
            title: "Are you sure?",
            text: "Do you want to delete this booking?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!",
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
                    location.href = "deleteBooking?id=" + bookingId;
                }, 2000);
            }
        });
    }
</script>
</body>
</html>
