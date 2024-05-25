<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 5/21/2024
  Time: 10:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Reset Password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <!-- Fontawesome CDN Link For Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
</head>
<body>
<form action="reset-pass" method="post">
    <h2>Reset Password</h2>
    <div class="form-group email">
        <label for="email">Email</label>
        <input type="email" id="email" placeholder="Enter your email" name="email">
    </div>
    <div class="form-group email">
        <label for="username">User Name</label>
        <input type="text" id="username" placeholder="Enter your user name" name="username">
    </div>

    <div class="form-group submit-btn">
        <input type="submit" value="Submit">
    </div>
    <div class="form-group text-center text-success">
        <p>Back to <a href="login">Login</a></p>
    </div>
</form>
<script src="${pageContext.request.contextPath}/js/resetPassword.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    <c:if test="${requestScope.warningRP!=null}">
    window.onload = function () {
        Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "${requestScope.warningRP}",
        });
    };
    </c:if>
    <c:if test="${requestScope.done!=null}">
    let timerInterval;
    Swal.fire({
        title: "Send mail success!",
        html: "${requestScope.done} <br>I will close in <b></b> milliseconds.",
        timer: 4000,
        timerProgressBar: true,
        didOpen: () => {
            Swal.showLoading();
            const timer = Swal.getPopup().querySelector("b");
            timerInterval = setInterval(() => {
                timer.textContent = `${Swal.getTimerLeft()}`;
            }, 100);
        },
        willClose: () => {
            clearInterval(timerInterval);
        }
    }).then((result) => {
        /* Read more about handling dismissals below */
        if (result.dismiss === Swal.DismissReason.timer) {

            window.location = "login";

        }
    });
    </c:if>
</script>
</body>
</html>
