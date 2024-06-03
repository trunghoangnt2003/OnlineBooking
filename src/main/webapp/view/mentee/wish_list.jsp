<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 6/1/2024
  Time: 4:43 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Wish List Process</title>
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
<jsp:include page="../common/header.jsp"></jsp:include>
<div class="container" style="margin-top: 30px">
    <h1 style="color: #179b81; display: flex; justify-content: center">Processing</h1>
    <div style="max-height: 400px; overflow-y: auto; margin-bottom: 30px;">
        <table class="table align-middle mb-0 bg-white">
            <thead class="bg-light">
            <tr style="background: #B0EDD7">
                <th style="width: 300px;">Name</th>
                <th style="width: 300px;">Skill</th>
                <th style="width: 100px;">Date</th>
                <th style="width: 100px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.wishLists}" var="w">
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <img
                                    src="${w.mentor.account.avatar}"
                                    class="rounded-circle"
                                    alt=""
                                    style="width: 45px; height: 45px"
                            />
                            <div class="ms-3">
                                <p class="fw-bold mb-1">${w.mentor.account.name}</p>
                                <p class="text-muted mb-0">${w.mentor.account.email}</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <c:forEach begin="0" end="${w.mentor.level_skills.size() - 1}" items="${w.mentor.level_skills}" varStatus="i" var="ls">
                            <span class="fw-normal mb-1">${ls.skill.name}</span>
                            <c:if test="${i.index < w.mentor.level_skills.size() - 1}">, </c:if>
                        </c:forEach>
                    </td>
                    <td>${w.timeRequest}</td>
                    <td>
                        <button
                                type="button"
                                class="btn btn-link btn-rounded btn-sm fw-bold"
                                data-mdb-ripple-color="dark"
                                onclick="confirmDelete(${w.id})"
                        >
                            Unfollow
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <h1 style="color: #179b81; display: flex; justify-content: center">Accepted</h1>
    <div style="max-height: 400px; overflow-y: auto; margin-bottom: 30px;">
        <table class="table align-middle mb-0 bg-white">
            <thead class="bg-light">
            <tr style="background: #B0EDD7">
                <th style="width: 300px;">Name</th>
                <th style="width: 300px;">Skill</th>
                <th style="width: 100px;">Date</th>
                <th style="width: 100px;">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${requestScope.wishlistA}" var="w">
                <tr>
                    <td>
                        <div class="d-flex align-items-center">
                            <img
                                    src="${w.mentor.account.avatar}"
                                    class="rounded-circle"
                                    alt=""
                                    style="width: 45px; height: 45px"
                            />
                            <div class="ms-3">
                                <p class="fw-bold mb-1">${w.mentor.account.name}</p>
                                <p class="text-muted mb-0">${w.mentor.account.email}</p>
                            </div>
                        </div>
                    </td>
                    <td>
                        <c:forEach begin="0" end="${w.mentor.level_skills.size() - 1}" items="${w.mentor.level_skills}" varStatus="i" var="ls">
                            <span class="fw-normal mb-1">${ls.skill.name}</span>
                            <c:if test="${i.index < w.mentor.level_skills.size() - 1}">, </c:if>
                        </c:forEach>
                    </td>
                    <td>${w.timeRequest}</td>
                    <td>
                        <button
                                type="button"
                                class="btn btn-link btn-rounded btn-sm fw-bold"
                                data-mdb-ripple-color="dark"
                                onclick="confirmDelete(${w.id})"
                        >
                            Unfollow
                        </button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
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
</body>
</html>
