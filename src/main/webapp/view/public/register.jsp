<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 5/13/2024
  Time: 11:39 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Register</title>
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
        <h2>Register</h2>
        <form action="register" method="post">
            <div class="user-box">
                <input type="text" name="userName" required="required"/>
                <label>Username</label>
            </div>
            <div class="user-box">
                <input type="password" name="passWord" required="required"/>
                <label>Password</label>
            </div>
            <div class="user-box">
                <input type="password" name="rePassWord" required="required"/>
                <label>Confirm password</label>
            </div>
            <div class="user-box">

                <input type="email" name="email" required="required"/>
                <label>Email</label>
            </div>
            <div class="user-box">

                <input type="tel" name="phone" required="required"/>
                <label>Phone</label>
            </div>
            <div class="user-box">

                <input type="date" name="dob" placeholder="mm/dd/yyyy"/>
                <label>Birthdate</label>
            </div>

            <button class="submit" type="submit">
                <span></span>
                <span></span>
                <span></span>
                <span></span>
                Submit
            </button>
            <br>
            <br>
            <p>You have account? <a href="login">Login</a></p>
        </form>
    </div>
    <script
            type="text/javascript"
            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"
    ></script>
</body>
</html>
