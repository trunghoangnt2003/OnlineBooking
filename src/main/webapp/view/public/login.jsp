<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 5/13/2024
  Time: 10:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Font Awesome CDN  -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css"
          integrity="sha512-1PKOgIY59xJ8Co8+NE6FZ+LOAZKjy+KY8iq0G4B3CyeY6wYHN3yt9PW0XpSriVlkMXe40PTKnXrLnZ9+fkDaog=="
          crossorigin="anonymous" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" >
</head>
<style>
    .button {
        display: inline-block;
        padding: 10px 20px;
        background-color: #eaeaea;
        border: none;
        cursor: pointer;
    }

    .button.selected {
        background-color: #07ad90;
        color: #ffffff;
    }
</style>
<body>
<ul class="nav">
    <li onclick="showLogin()">Login</li>
    <li onclick="showSignup()">Sign up</li>
    <li onclick="showForgotPassword()">Forgot password</li>
    <li onclick="showSubscribe()">Subscribe</li>
    <li onclick="showContactUs()">Contact us</li>
</ul>
<div class="wrapper">
    <div class="rec-prism">
        <div class="face face-top">
            <div class="content">
                <h2>Subscribe</h2>
                <small>Enter your email so we can send you the latest updates!</small>
                <form >
                    <div class="field-wrapper">
                        <input type="text" name="email" placeholder="email">
                        <label>e-mail</label>
                    </div>

                    <div class="field-wrapper">
                        <input type="submit" onclick="showThankYou()">
                    </div>
                </form>
            </div>
        </div>
        <div class="face face-front">
            <div class="content">
                <h2>Sign in</h2>
                <form action="login" method="post">
                    <div class="field-wrapper">
                        <input type="text" name="userName" placeholder="username" REQUIRED>
                        <label>username</label>
                    </div>
                    <div class="field-wrapper">
                        <input type="password" name="passWord" placeholder="password" autocomplete="new-password" REQUIRED>
                        <label>password</label>
                    </div>
                        <p style="color: red;font-size: 10px">${requestScope.warningLogin}</p>
                    <div class="field-wrapper">
                        <input type="submit" >
                    </div>
                    <span class="psw">or sign in with:
                    <a href="https://accounts.google.com/o/oauth2/auth?scope=email&redirect_uri=http://localhost:8080/Frog/google&response_type=code
                                &client_id=640660647139-t1brdm7075p7dpeabkoqsiols7shf6cr.apps.googleusercontent.com&approval_prompt=force"><i class="fab fa-google"></i></a>
                    </span>


                    <span class="psw" onclick="showForgotPassword()">Forgot Password? </span>
                    <span class="signup" onclick="showSignup()">Not a user? Sign up</span>

                </form>
            </div>
        </div>
        <div class="face face-back">
            <div class="content">
                <h2>Forgot your password?</h2>
                <small>Enter your email so we can send you a reset link for your password</small>
                <form action="reset-pass" method="post">
                    <div class="field-wrapper">
                        <input type="text" name="email" placeholder="email" REQUIRED>
                        <label>e-mail</label>
                    </div>
                    <p style="color: red;font-size: 10px">${requestScope.warningRP}</p>
                    <div class="field-wrapper" >
                        <input type="submit">
                    </div>
                </form>
            </div>
        </div>
        <div class="face face-right">
            <div class="content">
                <h2>Sign up</h2>
                <form action="register" method="post">
                    <div class="field-wrapper">
                        <input type="text" name="email" placeholder="email" REQUIRED>
                        <label>e-mail</label>
                    </div>
                    <div class="field-wrapper">
                        <input type="password" name="passWord" placeholder="password" autocomplete="new-password" REQUIRED>
                        <label>password</label>
                    </div>
                    <div class="field-wrapper">
                        <input type="password"  placeholder="password" autocomplete="new-password" REQUIRED>
                        <label>re-enter password</label>
                    </div>
                    <br>
                    <button class="button" type="button" onclick="selectButton(1)" value="1">Mentee</button>
                    <button class="button" type="button" onclick="selectButton(2)" value="2">Mentor</button>
                    <input type="hidden" id="selectedButton" name="role" value="1">
                    <p style="color: red;font-size: 10px">${requestScope.warningRegister}</p>
                    <div class="field-wrapper">
                        <input type="submit" onclick="submitForm()">
                    </div>
                    <span class="singin" onclick="showLogin()">Already a user? Sign in</span>
                </form>
            </div>
        </div>
        <div class="face face-left">
            <div class="content">
                <h2>Contact us</h2>
                <form>
                    <div class="field-wrapper">
                        <input type="text" name="name" placeholder="name" REQUIRED>
                        <label>Name</label>
                    </div>
                    <div class="field-wrapper">
                        <input type="text" name="email" placeholder="email" REQUIRED>
                        <label>e-mail</label>
                    </div>
                    <div class="field-wrapper">
                        <textarea placeholder="your message"></textarea>
                        <label>your message</label>
                    </div>
                    <div class="field-wrapper">
                        <input type="submit">
                    </div>
                </form>
            </div>
        </div>
        <div class="face face-bottom">
            <div class="content">
                <div class="thank-you-msg">
                    Thank you!
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/js/login.js">
</script>
<script>
    function selectButton(buttonNumber) {
        var buttons = document.getElementsByClassName("button");
        for (var i = 0; i < buttons.length; i++) {
            buttons[i].classList.remove("selected");
        }
        buttons[buttonNumber - 1].classList.add("selected");
        selectedButtonValue = buttons[buttonNumber - 1].value;
        document.getElementById("selectedButton").value = selectedButtonValue;
    }
    function submitForm() {
        if (selectedButtonValue === "") {
            alert("Please select a button.");
            return;
        }

        var form = document.getElementById("myForm");
        form.submit();
    }
</script>
</body>
</html>
