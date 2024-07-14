<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 7/14/2024
  Time: 2:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Change Password</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
  <style>
    .success-message {
      color: green;
    }
    .error-message {
      color: red;
    }
    .form .newPassword i, .form .ConfirmPassword i {
      position: absolute;
      right: 0px;
      height: 45px;
      top: 28px;
      font-size: 13px;
      line-height: 45px;
      width: 45px;
      cursor: pointer;
      color: #939393;
      text-align: center;
    }
  </style>
</head>
<body>
<form id="changePassForm" action="changePass" method="post">
  <h2>Change Password</h2>
  <div class="form-group password">
    <label for="password">Password</label>
    <input type="password" id="password" placeholder="Enter your old password" name="password">
    <i id="pass-toggle-btn" class="fa-solid fa-eye pass-toggle-btn"></i>
  </div>
  <div class="form-group password">
    <label for="newPassword">New Password</label>
    <input type="password" id="newPassword" placeholder="Enter your new password" name="newPassword">
    <i id="pass-toggle-btn1" class="fa-solid fa-eye pass-toggle-btn"></i>
  </div>
  <div class="form-group password">
    <label for="ConfirmPassword">Confirm New Password</label>
    <input type="password" id="ConfirmPassword" placeholder="Enter your new password" name="confirmPassword">
    <i id="pass-toggle-btn2" class="fa-solid fa-eye pass-toggle-btn"></i>
  </div>
  <div class="form-group submit-btn">
    <input type="submit" value="Submit">
  </div>
  <div class="error-message">
    ${requestScope.error}
  </div>
  <div class="success-message">
    ${requestScope.success}
  </div>
</form>
<script>
  const showError = (field, errorText) => {
    field.classList.add("error");
    const errorElement = document.createElement("small");
    errorElement.classList.add("error-text");
    errorElement.innerText = errorText;
    field.closest(".form-group").appendChild(errorElement);
  }

  const togglePasswordVisibility = (inputId, toggleBtnId) => {
    const passwordInput = document.getElementById(inputId);
    const passToggleBtn = document.getElementById(toggleBtnId);
    passToggleBtn.addEventListener('click', () => {
      passToggleBtn.className = passwordInput.type === "password" ? "fa-solid fa-eye-slash" : "fa-solid fa-eye";
      passwordInput.type = passwordInput.type === "password" ? "text" : "password";
    });
  }

  togglePasswordVisibility("password", "pass-toggle-btn");
  togglePasswordVisibility("newPassword", "pass-toggle-btn1");
  togglePasswordVisibility("ConfirmPassword", "pass-toggle-btn2");

  document.getElementById("changePassForm").addEventListener("submit", (event) => {
    const newPassword = document.getElementById("newPassword");
    const confirmPassword = document.getElementById("ConfirmPassword");
    let isValid = true;

    // Clear previous errors
    document.querySelectorAll(".error-text").forEach(e => e.remove());
    newPassword.classList.remove("error");
    confirmPassword.classList.remove("error");

    if (newPassword.value.trim() === "") {
      showError(newPassword, "Enter your new password");
      isValid = false;
    } else if (newPassword.value.trim().length < 8) {
      showError(newPassword, "At least 8 characters long");
      isValid = false;
    }

    if (confirmPassword.value.trim() === "") {
      showError(confirmPassword, "Confirm your new password");
      isValid = false;
    } else if (confirmPassword.value.trim().length < 8) {
      showError(confirmPassword, "At least 8 characters long");
      isValid = false;
    } else if (newPassword.value.trim() !== confirmPassword.value.trim()) {
      showError(confirmPassword, "Passwords do not match");
      isValid = false;
    }

    if (!isValid) {
      event.preventDefault();
    }
  });
</script>
</body>
</html>
