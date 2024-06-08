<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Wish List</title>
    <!-- FontAwesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet"/>
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css" rel="stylesheet"/>
    <!-- SweetAlert2 -->
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
</head>
<body>
<jsp:include page="../../common/header.jsp"></jsp:include>
<div style="width: 100%; height: 100%; display: flex; justify-content: space-around; margin-top: 40px;">
    <div style="width: 600px; height: 500px; border: 1px solid black; border-radius: 8px; box-shadow: 0 0 20px rgba(0, 0, 0, 0.05); background-color: #fff;">
        <h1 style="display: flex; justify-content: center; background: #07AD90; border-radius: 8px; color: white; padding: 10px;">Wish List Process</h1>
        <div>
            <form>
                <table style="width: 100%; border-collapse: collapse; margin: 20px 0;">
                    <tr>
                        <th style="border: 1px solid black; padding: 10px; text-align: center;">Avatar</th>
                        <th style="border: 1px solid black; padding: 10px; text-align: center;">Name</th>
                        <th style="border: 1px solid black; padding: 10px; text-align: center;">Type</th>
                        <th style="border: 1px solid black; padding: 10px; text-align: center;">Date</th>
                        <th style="border: 1px solid black; padding: 10px; text-align: center;">Action</th>
                    </tr>
                    <c:forEach items="${requestScope.wishLists}" var="w">
                        <tr>
                            <td style="border: 1px solid black; padding: 10px; text-align: center;"><img src="${w.mentor.account.avatar}" alt="Avatar" style="width: 50px; height: 50px; border-radius: 50%;"></td>
                            <td style="border: 1px solid black; padding: 10px; text-align: center;">${w.mentor.account.name}</td>
                            <td style="border: 1px solid black; padding: 10px; text-align: center;  color: #038edc">${w.status.type}</td>
                            <td style="border: 1px solid black; padding: 10px; text-align: center;">${w.timeRequest}</td>
                            <td style="border: 1px solid black; padding: 10px; text-align: center;">
                                <i class="fa-solid fa-delete-left fa-rotate-180 fa-2xl" onclick="confirmDelete(${w.id})" style="color: #ff0000;"></i>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </form>
        </div>
    </div>
    <div style="width: 600px; height: 500px; border: 1px solid black; border-radius: 8px; box-shadow: 0 0 20px rgba(0, 0, 0, 0.05); background-color: #fff;">
        <h1 style="display: flex; justify-content: center; background: #07AD90; border-radius: 8px; color: white; padding: 10px;">Wish List Accept</h1>
        <table style="width: 100%; border-collapse: collapse; margin: 20px 0;">
            <tr>
                <th style="border: 1px solid black; padding: 10px; text-align: center;">Avatar</th>
                <th style="border: 1px solid black; padding: 10px; text-align: center;">Name</th>
                <th style="border: 1px solid black; padding: 10px; text-align: center;">Type</th>
                <th style="border: 1px solid black; padding: 10px; text-align: center;">Date</th>
                <th style="border: 1px solid black; padding: 10px; text-align: center;">Action</th>
            </tr>
            <c:forEach items="${requestScope.wishlistA}" var="w">
                <tr>
                    <td style="border: 1px solid black; padding: 10px; text-align: center;"><img src="${w.mentor.account.avatar}" alt="Avatar" style="width: 50px; height: 50px; border-radius: 50%;"></td>
                    <td style="border: 1px solid black; padding: 10px; text-align: center;">${w.mentor.account.name}</td>
                    <td style="border: 1px solid black; padding: 10px; text-align: center; color: #038edc">${w.status.type}</td>
                    <td style="border: 1px solid black; padding: 10px; text-align: center;">${w.timeRequest}</td>
                    <td style="border: 1px solid black; padding: 10px; text-align: center;">
                        <i class="fa-solid fa-delete-left fa-rotate-180 fa-2xl" onclick="confirmDelete(${w.id})" style="color: #ff0000;"></i>
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
            text: "Do you want to delete this mentor?",
            icon: "warning",
            showCancelButton: true,
            confirmButtonColor: "#3085d6",
            cancelButtonColor: "#d33",
            confirmButtonText: "Yes, delete it!",
        }).then((result) => {
            if (result.isConfirmed) {
                Swal.fire({
                    title: "Deleted!",
                    text: "Your mentor has been deleted.",
                    icon: "success",
                    timer: 2000,
                    showConfirmButton: false
                });
                setTimeout(() => {
                    location.href = "wishlist?idwish=" + bookingId;
                }, 2000);
            }
        });
    }
</script>
<script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"></script>
</body>
</html>
