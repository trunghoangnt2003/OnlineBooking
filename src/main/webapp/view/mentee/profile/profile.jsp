<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 5/21/2024
  Time: 10:08 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/mentee/profile.css" type="text/css" media="screen, project">
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"/>
    <!-- Google Fonts -->
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"/>
    <!-- MDB -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>
    <style>
        .wishlist-container {
            max-height: 200px; /* Adjust height as needed */
            overflow-y: auto;
            margin-bottom: 20px;
        }
        .table thead th {
            position: sticky;
            top: 0;
            z-index: 1;
            background: #B0EDD7; /* Adjust to match your table header color */
        }
        .back{
            margin-top: 20px;
            margin-left: 100px;
            margin-bottom: 50px;
        }
        .user-profile{
            border-top-left-radius: 5px;
            border-bottom-left-radius: 5px;
        }
    </style>

</head>
<body>
<jsp:include page="../../common/header.jsp"></jsp:include>
<div class="back">
    <jsp:include page="../../common/backBtn.jsp"></jsp:include>
</div>
<div class="page-content page-container" id="page-content">
    <div class="padding" style="display: flex; justify-content: center; margin-top: 30px; border-radius: 50px">
        <div class="row container border-10">
            <div class="body-content">
                <div class="card user-card-full">
                    <div class="row m-l-0 m-r-0">
                        <div class="col-sm-4 bg-c-lite-green user-profile" style="background: #07ad90">
                            <div class="card-block text-center text-white">
                                <div class="m-b-25">
                                    <img class="avatar" src="${pageContext.request.contextPath}/${requestScope.mentee.account.avatar}" alt="">
                                </div>
                                <h4 class="f-w-600" style="margin-top: 10px">${requestScope.mentee.account.name}</h4>
                                <c:if test="${requestScope.acc_id == requestScope.url_id}">
                                    <a href="update" style="color: white"><i class="fa-solid fa-pen-to-square fa-xl" style="margin-top: 10px"></i></a>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-sm-8" style="height: 500px; padding: 50px">
                            <div class="card-block">
                                <h1 class="m-b-20 p-b-5 b-b-default f-w-600" style="color: #07ad90;font-weight: bold;">Information</h1>
                                <hr style="color: #07ad90; opacity: 100%">
                                <div class="row">
                                    <div class="col-sm-6">
                                        <span class="m-b-10 f-w-600" style="color: #07AD90;  font-size: 20px">Email</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.email}</h6>
                                    </div>
                                    <div class="col-sm-6">
                                        <span class="m-b-10 f-w-600" style="color: #07AD90;  font-size: 20px">Phone</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.phone}</h6>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <span class=" f-w-600" style="color: #07AD90;  font-size: 20px">Gender</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.gender == 1 ? "Male" : "Female"}</h6>
                                    </div>
                                    <div class="col-sm-6">
                                        <div style="color: #07AD90;  font-size: 20px"> Address </div>
                                        <span class="m-b-10 f-w-600">${requestScope.mentee.account.address}</span>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-sm-6">
                                        <span class="m-b-10 f-w-600" style="color: #07AD90;  font-size: 20px">Date of Birth</span>
                                        <h6 class="text-muted f-w-400">${requestScope.mentee.account.dob}</h6>
                                    </div>
                                </div>
                                <div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <c:if test="${requestScope.acc_id == requestScope.url_id}">
        <div class="container" style="margin-top: 30px">
            <h3 style="color: #179b81; display: flex; justify-content: center">My Wish List</h3>
            <div class="wishlist-container">
                <table class="table align-middle mb-0 bg-white">
                    <thead class="bg-light">
                    <tr style="background: #B0EDD7">
                        <th style="width: 300px; background: #B0EDD7">Name</th>
                        <th style="width: 300px; background: #B0EDD7">Skill</th>
                        <th style="width: 100px; background: #B0EDD7">Date</th>
                        <th style="width: 100px; background: #B0EDD7">Actions</th>
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
                                        <a class="fw-bold mb-1" href="../mentor/profile?mentorid=${w.mentor.account.id}">${w.mentor.account.name}</a>
                                        <p class="text-muted mb-0">${w.mentor.account.email}</p>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <c:forEach begin="0" end="${w.mentor.level_skills.size() - 1}" items="${w.mentor.level_skills}" varStatus="i" var="ls">
                                    <span class="fw-normal mb-1">${ls.skill.name} for level ${ls.level.name}</span>
                                    <c:if test="${i.index < w.mentor.level_skills.size() - 1}">, </c:if>
                                </c:forEach>
                            </td>
                            <td>${w.timeRequest}</td>
                            <td>
                                <button
                                        type="button"
                                        class="btn btn-link btn-rounded btn-sm fw-bold"
                                        data-mdb-ripple-color="dark"
                                        onclick="confirmDelete(${w.id},'${requestScope.url_id}')"
                                >
                                    <i class="fa-solid fa-user-slash fa-xl"></i>
                                </button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </c:if>
</div>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function confirmDelete(wId, menteeid) {
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
                    text: "Mentor has been remove from your wish list.",
                    icon: "success",
                    timer: 2000,
                    showConfirmButton: false
                });
                setTimeout(() => {
                    location.href = "profile?idwish=" + wId + "&menteeid=" + menteeid;
                }, 2000);
            }
        });
    }

</script>
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.umd.min.js"
></script>
</body>
</html>
