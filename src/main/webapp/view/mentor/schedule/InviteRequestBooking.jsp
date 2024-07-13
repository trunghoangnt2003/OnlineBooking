<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 6/22/2024
  Time: 4:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of Following Requests</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentor/view_follower.css">

</head>
<body>
<jsp:include page="/view/common/header.jsp"></jsp:include>
<div class="container mt-5">
    <div style="margin: 0 0  70px 50px ">
        <a href="${pageContext.request.contextPath}/mentor/schedule" style="text-decoration: none">
            <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>
            &nbsp;<span style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px" >Back to Schedule</span>
        </a>
    </div>
    <h1 class="mb-4">List of Invite Requests</h1>
    <table class="table table-striped">
        <thead class="table-dark">
        <tr>
            <th>Number</th>
            <th>Create Date</th>
            <th>Amount</th>
            <th>Description</th>
            <th>Skill Name</th>
            <th>Skill Level</th>
            <th>Start Date</th>
            <th>End Date</th>
            <th>Name</th>
            <th>Status</th>
            <th>View Slot</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.bookings}" var="book">
            <tr>
                <td>${requestScope.count}</td>
                <td>${book.date}</td>
                <td>
                    <fmt:formatNumber value="${book.amount}" type="number" maxFractionDigits="0" />₫
                </td>
                <td>${book.description} </td>
                <td><img width="20px"
                         src="${pageContext.request.contextPath}/${book.level_skills.skill.src_icon}">
                        ${book.level_skills.skill.name}</td>
                <td>${book.level_skills.level.name} </td>
                <td>${book.startDate}</td>
                <td>${book.endDate}</td>
                <td>${book.mentee.account.name}</td>
                <td><c:if test="${book.status.id ==1 }">
                    <button name="accept"  style="background-color: #1BB295;border-radius: 5px;padding: 5px;color: whitesmoke" id="btnAccept_${book.id}"
                            data-id="${book.id}_${book.startDate}" onclick="updateStatus(${book.id})">
                        Accept
                    </button>
                </c:if>
                    <c:if test="${book.status.id ==1 }">
                        <button name="reject"  style="background-color: #ff2222;border-radius: 5px;padding: 5px;color: whitesmoke" id="btnReject_${book.id}"
                                data-id="${book.id}_${book.startDate}"  onclick="rejectStatus(${book.id})">
                            Reject
                        </button>
                    </c:if>
                </td>
                <td>
                    <button name="view" value="view" style="background-color: #f8ce0b;border-radius: 5px;padding: 5px;">
                        <a href="/Frog/mentor/schedule?viewID=${book.id}&today=${book.startDate}" style="text-decoration: none;color: whitesmoke"
                           class="button">
                            View</a></button>
                </td>
                <c:set var="count" value="${count= count +1}"></c:set>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p>Total: ${bookings.size()}</p>
</div>
<!-- MDB -->
<script>
    const updateStatus = (id) => {
        var btnOnclick = document.getElementById("btnAccept_"+id);
        var newURL = "";
        btnOnclick.onclick = function (event) {
            event.preventDefault();
            Swal.fire({
                title: "Are you want to accept booking",
                icon: "question",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes!"
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: "Successfully",
                        icon: "success",
                        confirmButtonColor: false
                    });
                    var bookId = this.getAttribute('data-id').split("_")[0];
                    var today =  this.getAttribute('data-id').split("_")[1];
                    newURL = "/Frog/mentor/schedule/update?bookingID="+bookId+"&option=true&today="+today;
                    window.location.href = newURL;
                }
            });
        };
    }
    const rejectStatus = (id) => {
        var btnOnclick = document.getElementById("btnReject_"+id);

        btnOnclick.onclick = function (event) {
            event.preventDefault();

            Swal.fire({
                title: "Are you sure you want to reject the booking?",
                icon: "question",
                input: "textarea",
                inputLabel: "Message",
                inputPlaceholder: "Type your message here...",
                inputAttributes: {
                    "aria-label": "Type your message here"
                },
                confirmButtonText: "Yes",
                showCancelButton: true
            }).then((result) => {
                if (result.isConfirmed) {
                    Swal.fire({
                        title: "Successfully",
                        text: "Booking has been rejected",
                        icon: "success"
                    }).then(() => {
                        var bookId = this.getAttribute('data-id').split("_")[0];
                        var today =  this.getAttribute('data-id').split("_")[1];
                        var newURL = "/Frog/mentor/schedule/update?bookingID=" + bookId + "&option=false&description=" + encodeURIComponent(result.value)+"&today="+today;
                        window.location.href = newURL;
                    });
                }
            });
        };
    };
</script>
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
</body>
</html>
