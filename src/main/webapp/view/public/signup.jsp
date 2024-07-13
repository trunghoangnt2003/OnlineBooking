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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/signup.css">
    <!-- Fontawesome CDN Link For Icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.1/css/all.min.css" />
  </head>
  <body>
    <form action="register" method="post">
      <h2>Sign Up</h2>
      <div class="row">
        <div class="form-group fullname col-6">
          <label for="fullname">Full Name</label>
          <input type="text" id="fullname" placeholder="Enter your full name" name="name">
        </div>
        <div class="form-group fullname col-6">
          <label for="username">User Name</label>
          <input type="text" id="username" placeholder="Enter your user name" name="userName">
        </div>
      </div>
      <div class="row">
        <div class="form-group email col">
          <label for="email">Email Address</label>
          <input type="email" id="email" placeholder="Enter your email address" name="mail">
        </div>
        <div class="form-group email col">
          <label for="phone">Phone Address</label>
          <input type="tel" id="phone" placeholder="Enter your email address" name="phone">
        </div>

      </div>
      <div class="row">
        <div class="form-group password col">
          <label for="password">Password</label>
          <input type="password" id="password" placeholder="Enter your password" name = "password">
          <i id="pass-toggle-btn" class="fa-solid fa-eye pass-toggle-btn"></i>
        </div>
        <div class="form-group password col">
          <label for="repassword">Confirm Password</label>
          <input type="password" id="repassword" placeholder="Enter your re-password">
          <i id="repass-toggle" class="fa-solid fa-eye pass-toggle-btn"></i>
        </div>
      </div>
      <div class="row">
        <div class="form-group date col">
          <label for="date">Birth Date</label>
          <input type="date" id="date" placeholder="Select your date" name="dob">
        </div>
        <div class="form-group date col">
          <label for="address">Address</label>
          <input type="text" id="address" placeholder="Enter your address" name="address">
        </div>
      </div>
      <div class="row">
        <div class="form-group gender col">
          <label for="gender">Gender</label>
          <select id="gender" name="gender">
            <option value="" selected disabled>Select your gender</option>
            <option value="1">Male</option>
            <option value="0">Female</option>
          </select>
        </div>
        <div class="form-group gender col">
          <label for="role">Role</label>
          <select id="role" name="role">
            <option value="" selected disabled>Select your role</option>
            <option value="1">Mentee</option>
            <option value="2">Mentor</option>
          </select>
        </div>
      </div>
      <div class="form-group submit-btn">
        <input type="submit" value="Submit">
      </div>
      <div class="form-group text-center text-success">
        <p>You have account? <a href="login">Login</a></p>
      </div>

    </form>


    <script src="${pageContext.request.contextPath}/js/signup.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
    <script>
      <c:if test="${requestScope.warningRegister!=null}">
      window.onload = function() {
        Swal.fire({
          icon: "error",
          title: "Oops...",
          text: "${requestScope.warningRegister}",
        });
      };
      </c:if>
      <c:if test="${requestScope.done!=null}">
      let timerInterval;
      Swal.fire({
        title: "Sign up success!",
        html: "${requestScope.done} <br>I will close in <b></b> milliseconds.",
        timer: 5000,
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

            window.location="login";

        }
      });
      </c:if>
    </script>
  </body>

</html>