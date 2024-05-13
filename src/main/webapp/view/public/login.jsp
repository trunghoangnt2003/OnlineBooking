<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 5/13/2024
  Time: 10:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" >
    <!-- Font Awesome -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
            rel="stylesheet"
    />
    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css"
            rel="stylesheet"
    />
</head>
<body>
    <div class="login-box">
        <h2>Login</h2>
        <form>
            <div class="user-box">

                <input type="text" name="userName" required="required"/>
                <label>Username</label>
            </div>
            <div class="user-box">

                <input type="password" name="passWord" required="required"/>
                <label>Password</label>
            </div>
            <div class="d-flex justify-content-between align-items-center">
                <!-- Checkbox -->
                <div class="form-check mb-0">
                    <input class="form-check-input me-2" type="checkbox" value="" id="form2Example3" />
                    <label class="form-check-label" for="form2Example3">
                        Remember me
                    </label>
                </div>
                <a href="#!" class="text-body">Forgot password?</a>
            </div>
            <button class="submit" type="submit">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                Submit
            </button>
        </form>
        <div class="text-center flex">
            <p>Not a member? <a href="register">Register</a></p>
            <p class="d-inline">or sign up with:</p>
            <button  type="button" data-mdb-button-init data-mdb-ripple-init class="btn btn-link btn-floating mx-1 d-inline">
                <i class="fab fa-facebook-f"></i>
            </button>
        </div>

    </div>




    <!-- MDB -->
    <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"
    ></script>
</body>
</html>
