<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js" integrity="sha384-0pUGZvbkm6XF6gxjEnlmuGrJXVbNuzT9qBBavbLwCsOGabYfZo0T0to5eqruptLy" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <!-- Fontawesome CDN Link For Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
</head>
<body>
<form action="login" method="post">
    <h2>Sign In</h2>
        <div class="form-group username">
            <label for="username">User Name</label>
            <input type="text" id="username" placeholder="Enter your user name" name="username">
        </div>
        <div class="form-group password">
            <label for="password">Password</label>
            <input type="password" id="password" placeholder="Enter your password" name = "password">
            <i id="pass-toggle-btn" class="fa-solid fa-eye pass-toggle-btn"></i>
        </div>
    <div class="d-flex justify-content-between align-items-center text-success">
        <!-- Checkbox -->
        <div class="form-check mb-0">
            <input class="form-check-input" type="checkbox" value="1" name="remember" id="remember"/>
            <label class="form-check-label" style="margin-top:5px;margin-left: 5px ">
                Remember me
            </label>
        </div>
        <a href="reset-pass" class="text-body">Forgot password?</a>
    </div>
    <div class="form-group submit-btn">
        <input type="submit" value="Submit">
    </div>
    <div class="text-center flex text-success">
        <p>Not a member? <a href="register">Register</a></p>
        <p class="d-inline">or sign up with:</p>

        <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/Frog/google&response_type=code
                                &client_id=640660647139-t1brdm7075p7dpeabkoqsiols7shf6cr.apps.googleusercontent.com&approval_prompt=force"><i class="fab fa-google"></i></a>


    </div>
</form>

<script src="${pageContext.request.contextPath}/js/login.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<script>
    function getCookie(cookieName) {
        const name = cookieName + "=";
        const decodedCookie = decodeURIComponent(document.cookie);
        const cookieArray = decodedCookie.split(';');

        for(let i = 0; i < cookieArray.length; i++) {
            let cookie = cookieArray[i];
            while (cookie.charAt(0) === ' ') {
                cookie = cookie.substring(1);
            }
            if (cookie.indexOf(name) === 0) {
                return cookie.substring(name.length, cookie.length);
            }
        }
        return "";
    }

    // Function to set the value of the input field with the retrieved cookie value
    function setUserNameValueToInput() {
        document.getElementById("username").value = getCookie("usernameRM");
    }
    function setPassWordValueToInput() {
        document.getElementById("password").value = getCookie("passwordRM");
    }
    function setRememberValueToInput() {
        const username = getCookie("usernameRM");
        if(username){
            document.getElementById("remember").click();
        }
    }

    // Load the cookie value when the page is fully loaded
    window.onload = function() {
        setRememberValueToInput();
        setPassWordValueToInput();
        setUserNameValueToInput();
        // You can call more functions here if needed
    };
    <c:if test="${requestScope.warningLogin!=null}">
    window.onload = function() {
            Swal.fire({
            icon: "error",
            title: "Oops...",
            text: "${requestScope.warningLogin}",
        });
    };
    </c:if>
</script>
</body>

</html>