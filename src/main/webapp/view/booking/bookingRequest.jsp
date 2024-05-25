<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
</head>
<body class="body">
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container">

    <table id="example" class="table table-striped table-bordered" cellspacing="0" width="100%">
        <thead>
        <tr>
            <th>Mentor</th>
            <th>From Date</th>
            <th>To Date</th>
            <th>Description</th>
            <th>Skill</th>
            <th>Create Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.bookingList}" var="booking">
            <tr>
                <td>${booking.mentor.account.name}</td>
                <td>${booking.startDate}</td>
                <td>${booking.endDate}</td>
                <td>${booking.description}</td>
                <td>${booking.level_skills.skill.name} for ${booking.level_skills.level.name}</td>
                <td>${booking.date}</td>
                <td>
                    <i class="fa-solid fa-file-pen fa-2xl"  onclick="location.href='updateBooking?id=${booking.id}'" style="color: #74C0FC;"></i>
                    <i class="fa-solid fa-delete-left fa-rotate-180 fa-2xl"  onclick="confirmDelete(${booking.id})" style="color: #ff0000;"></i>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
    <div class="modal-dialog">

        <!-- Modal content-->
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">&times;</button>
                <h4 class="modal-title">Row information</h4>
            </div>
            <div class="modal-body">

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
            </div>
        </div>

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
