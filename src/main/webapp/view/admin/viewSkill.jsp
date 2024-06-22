<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 6/6/2024
  Time: 1:43 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <title>Admin </title>
        <!-- plugins:css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/feather/feather.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/mdi/css/materialdesignicons.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/ti-icons/css/themify-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/font-awesome/css/font-awesome.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/typicons/typicons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/simple-line-icons/css/simple-line-icons.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/css/vendor.bundle.base.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.css">
        <!-- endinject -->
        <!-- Plugin css for this page -->
        <!-- End plugin css for this page -->
        <!-- inject:css -->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/view/admin/assets/css/style.css">
        <!-- endinject -->
        <link rel="shortcut icon" href="${pageContext.request.contextPath}/view/admin/assets/images/favicon.png" />

        <!-- Font Awesome -->
        <!-- Google Fonts -->
        <link
                href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
                rel="stylesheet"
        />
        <!-- MDB -->

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
        <!-- Fontawesome CDN Link For Icons -->
        <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
        <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>


    </head>
</head>
<body>
<div class="container-scroller">
    <!-- partial:partials/_navbar.jsp -->
    <jsp:include page="partials/_navbar.jsp"></jsp:include>
    <!-- partial -->
    <div class="container-fluid page-body-wrapper">
        <!-- partial:partials/_sidebar.jsp -->
        <jsp:include page="partials/_sidebar.jsp"></jsp:include>
        <!-- partial -->
        <div class="main-panel">
            <div class="content-wrapper">
                <!-- Nút kích hoạt modal -->
                <div class="row">
                    <div class="col-md">
                        <button type="button" style="margin-bottom: 20px" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#skillModal">
                            ADD NEW SKILL
                        </button>
                    </div>
                    <div class="col-md">
                        <select id="mySelect" class="form-select" aria-label="Default select example" onchange="redirectToPage()">
                            <option value="all"
                                    <c:if test="${requestScope.cate == 0}"> selected </c:if>
                            >All Category</option>
                            <c:forEach items="${requestScope.categories}" var="c">
                                <option value="${c.id}"
                                        <c:if test="${requestScope.cate == c.id}"> selected </c:if>
                                >${c.name}</option>
                            </c:forEach>


                        </select>
                    </div>

                </div>

                <table class="table align-middle mb-0 bg-white table-hover">
                    <thead class="bg-light">
                    <tr>

                        <th>Skill</th>
                        <th>Level</th>
                        <th>Status</th>
                        <th>Category</th>
                        <th>Update</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${requestScope.list}" var="list" varStatus="loop">
                    <tr>

                        <td>
                            <div class="d-flex align-items-center">
                                <img
                                        src="${pageContext.request.contextPath}/${list.skill.src_icon}"
                                        alt=""
                                        style="width: 45px; height: 45px"
                                        class="rounded-circle"
                                />
                                <div class="ms-3">
                                    <p class="fw-bold mb-1">${list.skill.name}</p>
<%--                                    <p class="text-muted mb-0">ID : ${list.skill.id}</p>--%>
                                </div>
                            </div>
                        </td>
                        <td>
                            <p class="fw-normal mb-1">${list.level.name}</p>
<%--                            <p class="text-muted mb-0">ID :${list.level.id}</p>--%>
                        </td>
                        <td>
                            <c:if test="${list.status == 7}">
                                <span class="badge badge-success rounded-pill d-inline">ENABLE</span>
                            </c:if>
                            <c:if test="${list.status == 8}">
                                <span class="badge badge-warning rounded-pill d-inline">HIDDEN</span>
                            </c:if>

                        </td>
                        <td>${list.skill.category.name}</td>
                        <td>
<%--                            <button type="button" class="btn btn-success" style="padding: 5px; margin: 5px" value="${list.id}">ACTIVE</button>--%>
<%--                            <button type="button" class="btn btn-warning" style="padding: 5px;margin: 5px" value="${list.id}" data-bs-toggle="modal" data-bs-target="#updateModal">EDIT</button>--%>
                                <div class="form-check form-switch">

                                    <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked" onchange="handleCheckboxChange(this)"
                                    <c:if test="${list.status == 7}">
                                        checked
                                    </c:if>
                                            value="${list.id}"
                                    />
                                    <label class="form-check-label" for="flexSwitchCheckChecked">
                                        ENABLE
                                    </label>
                                </div>

                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <br>
                <div style="display: flex; justify-content: space-between">
                    <div class="justify-content-start" style=""> Total ${requestScope.total} result </div>

                    <nav aria-label="Page navigation example" >
                        <ul class="pagination justify-content-end">
                            <li class="page-item <c:if test="${requestScope.page==1}"> disabled </c:if>">
                                <a class="page-link" href="?page=${requestScope.page-1}&cate=${requestScope.cate}"
                                        <c:if test="${requestScope.page==1}"> tabindex="-1" </c:if>
                                >Previous</a>
                            </li>
                            <c:forEach var="count" begin="1" end="${requestScope.end_page}">
                                <c:if test="${requestScope.page == count}">
                                    <li class="page-item"><a class="page-link active">${count}</a></li>
                                </c:if>
                                <c:if test="${requestScope.page != count}">
                                    <li class="page-item"><a class="page-link" href="?page=${count}&cate=${requestScope.cate}">${count}</a></li>
                                </c:if>
                            </c:forEach>
                            <li class="page-item">
                                <a class="page-link <c:if test="${requestScope.page==requestScope.end_page  or requestScope.end_page == 0}"> disabled </c:if>" href="?page=${requestScope.page+1}&cate=${requestScope.cate}"
                                        <c:if test="${requestScope.page==requestScope.end_page or requestScope.end_page == 0 }"> tabindex="-1" </c:if>
                                >Next</a>
                            </li>
                        </ul>
                    </nav>
                </div>
            </div>
            <!-- content-wrapper ends -->
            <!-- partial:partials/_footer.html -->

            <!-- partial -->
        </div>
        <!-- main-panel ends -->
    </div>
    <!-- page-body-wrapper ends -->
</div>


<!-- Modal -->
<div class="modal fade" id="skillModal" tabindex="-1" aria-labelledby="skillModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="skillModalLabel">Thêm kỹ năng mới</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>

            <div class="modal-body">
                <form method="post" enctype="multipart/form-data">
                    <input type="hidden" name="action" value="add">
                    <div class="mb-3">
                        <label for="imageInput" class="form-label">Image for skill</label>
                        <div class="form-group">
                            <input type="file" name="img" class="form-control" id="imageInput" onchange="previewImage()">

                            <div id="imagePreviewContainer" style="display: none; text-align: center;">
                                <br>
                                <img id="imagePreview" src="#" alt="Ảnh xem trước" style="max-width: 50%; height: auto;">
                                <div id="skillImageHelp" class="form-text">Skill Image</div>
                            </div>
                        </div>

                    </div>

                        <div class="form-group">
                            <label for="skillInput">Skill Name:</label>
                            <input type="text" id="skillInput" class="form-control" name="name">
                            <div id="skillCheckResult"></div>
                        </div>

                    <div class="mb-3">
                        <label for="skillCategory" class="form-label">Category</label>
                        <select class="form-select" id="skillCategory" name="skillCategory">
                            <option disabled selected>Category</option>
                            <c:forEach items="${requestScope.categories}" var="c">
                                <option value="${c.id}">${c.name}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="mb-3">
                        <table class="table">
                            <thead>
                                <tr>
                                    <th class="col">Level</th>
                                    <th class="col">Description</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${requestScope.levels}" var="c">
                                    <tr>
                                        <td>
                                           ${c.name}
                                        </td>
                                        <td>
                                            <textarea class="form-control" id="skillDescription_${c.id}" name="skillDescription_${c.id}" rows="3" placeholder="Enter skill description"></textarea>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>

                    </div>

                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Đóng</button>
                        <button type="submit" class="btn btn-primary">Lưu thay đổi</button>
                    </div>
                </form>
            </div>

        </div>
    </div>
</div>
<!-- Modal -->
<%--<div class="modal fade" id="updateModal" tabindex="-1" aria-labelledby="updateModalLabel" aria-hidden="true">--%>
<%--    <div class="modal-dialog">--%>
<%--        <div class="modal-content">--%>
<%--            <div class="modal-header">--%>
<%--                <h5 class="modal-title" id="updateModalLabel">Update Skill</h5>--%>
<%--                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>--%>
<%--            </div>--%>

<%--            <div class="modal-body">--%>
<%--                <form>--%>
<%--                    <div class="">--%>
<%--                        <div class="form-check form-switch">--%>
<%--                            <label class="form-check-label" for="flexSwitchCheckChecked">Enable Skill : </label>--%>
<%--                            <input class="form-check-input" type="checkbox" role="switch" id="flexSwitchCheckChecked" checked />--%>

<%--                        </div>--%>
<%--                    </div>--%>

<%--                    <div>--%>
<%--                        <label for="description" class="form-label">Description</label>--%>
<%--                        <textarea class="form-control" id="description" rows="3">--%>
<%--                            ${list.}--%>
<%--                        </textarea>--%>
<%--                    </div>--%>
<%--                    <div class="modal-footer">--%>
<%--                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>--%>
<%--                        <button type="button" class="btn btn-primary">Save</button>--%>
<%--                    </div>--%>
<%--                </form>--%>
<%--            </div>--%>

<%--        </div>--%>
<%--    </div>--%>
<%--</div>--%>
<!-- container-scroller -->
<!-- plugins:js -->
<script src="${pageContext.request.contextPath}/view/admin/assets/vendors/js/vendor.bundle.base.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/vendors/bootstrap-datepicker/bootstrap-datepicker.min.js"></script>
<!-- endinject -->
<!-- Plugin js for this page -->
<!-- End plugin js for this page -->
<!-- inject:js -->
<script src="${pageContext.request.contextPath}/view/admin/assets/js/off-canvas.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/template.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/settings.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/hoverable-collapse.js"></script>
<script src="${pageContext.request.contextPath}/view/admin/assets/js/todolist.js"></script>
<!-- endinject -->
<!-- Custom js for this page-->
<!-- End custom js for this page-->
<!-- MDB -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script src="https://unpkg.com/sweetalert/dist/sweetalert.min.js"></script>
<script>
    // var inactiveButtons = document.querySelectorAll('.btn-warning');
    // // Lặp qua từng nút và thêm sự kiện click
    // inactiveButtons.forEach(function (button) {
    //     button.addEventListener('click', function () {
    //         // Lấy giá trị value của nút
    //         var id = this.getAttribute('value');
    //
    //         swal({
    //             title: "Are you sure for INACTIVE?",
    //             text: "This will inactive with ID: " + id + "!",
    //             icon: "warning",
    //             buttons: true,
    //             dangerMode: true,
    //         })
    //             .then((willBan) => {
    //                 if (willBan) {
    //                     // Gửi request POST tới Servlet để ban tài khoản
    //                     inactive(id);
    //                 } else {
    //                     swal("The skill is safe for now.");
    //                 }
    //             });
    //     });
    // });
    // function inactive(id) {
    //     fetch('../admin/skill', {
    //         method: 'POST',
    //         headers: {
    //             'Content-Type': 'application/x-www-form-urlencoded'
    //         },
    //         body: 'id=' + encodeURIComponent(id) + "&action=inactive"
    //     })
    //         .then(response => {
    //             if (response.ok) {
    //                 swal("Skill is inactive successfully!").then(() => {
    //                     // Reload the page
    //                     location.reload();
    //                 });
    //             } else {
    //                 swal("Failed to inactive. Please try again later.");
    //             }
    //         })
    //         .catch(error => {
    //             console.error('Error inactive:', error);
    //             swal("An error occurred while inactive. Please try again later.");
    //         });
    // }
    function redirectToPage() {
        var selectedValue = document.getElementById("mySelect").value;
        if (selectedValue) {
            window.location.href = "?cate="+selectedValue;
        }
    }
    function previewImage() {
        var input = document.getElementById("imageInput");
        var previewContainer = document.getElementById("imagePreviewContainer");
        var preview = document.getElementById("imagePreview");

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                preview.src = e.target.result;
                previewContainer.style.display = "block";
            }

            reader.readAsDataURL(input.files[0]);
        } else {
            previewContainer.style.display = "none";
            preview.src = "#";
        }
    }
    function handleCheckboxChange(checkbox) {
        const isChecked = checkbox.checked;
        const value = checkbox.value;
        sendRequestToServlet(isChecked, value);
    }

    async function sendRequestToServlet(isChecked, value) {
        try {
            const response = await fetch('../admin/skill', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                },
                body: 'action=update&isChecked='+isChecked+'&value='+value
            });

            if (response.ok) {
                location.reload();
            } else {
                swal(" Please try again later.");
            }
        } catch (error) {
            console.error('Lỗi khi thực hiện request:', error);
        }
    }



    // Lắng nghe sự kiện submit của biểu mẫu
    document.querySelector('form').addEventListener('submit', async (event) => {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của biểu mẫu

        // Lấy dữ liệu từ các trường input
        const formData = new FormData(event.target);

        try {
            const response = await fetch('../admin/skill', {
                method: 'POST',
                body: formData
            });

            if (response.ok) {
                swal("Add skill successfully!").then(() => {
                    // Reload the page
                    location.reload();
                });
            } else {
                Swal.fire({
                    icon: "error",
                    title: "Oops...",
                    text: "Skill Name Exits In System !",
                })
            }
        } catch (error) {
            // Xử lý lỗi kết nối
            console.error('Lỗi kết nối:', error);
        }
    });



</script>
</body>
</html>
