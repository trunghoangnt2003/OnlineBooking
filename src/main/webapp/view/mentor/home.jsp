<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 5/15/2024
  Time: 11:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="../css/mentor/homementor.css">
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
            rel="stylesheet"/>
</head>
<body>
    <div class="control">
        <div class="author">
            <i class="fa-solid fa-bell bell"></i>
            Author Name
            <button>Logout</button>
        </div>

    </div>

    <div class="image profile">
        <div>
            <img class="image1~" src="../image/profile.jpg">
        </div>

        <div class="text-infor">
            <div><i class="fa-solid fa-user symbol"></i>Kiều Thanh Thế Anh</div>
            <div><i class="fa-solid fa-envelope symbol"></i>kttanh03@gmail.com</div>
            <div><i class="fa-solid fa-phone symbol"></i>0969405887</div>
            <div><i class="fa-solid fa-location-dot symbol"></i>Hà Nội</div>
        </div>

        <div class="price">
            <div class="perhour"><i class="fa-solid fa-money-bill detail"></i>Price: xx$/hour</div>
            <div class="number-mentee"><i class="fa-solid fa-user-group detail"></i>Number of mentee: 30</div>
            <div class="rating"><i class="fa-solid fa-star detail"></i>Rate: </div>
            <div class="chat"><i class="fa-sharp fa-solid fa-comments detail"></i>Chat</div>
        </div>
    </div>

    <div class="taskbar">
        <button type="button" class="btn btn-light" onclick="setActive(this)">My Profile</button>
        <button type="button" class="btn btn-light" onclick="setActive(this)">Edit Profile</button>
        <button type="button" class="btn btn-light" onclick="setActive(this)">View Request</button>
        <button type="button" class="btn btn-light" onclick="setActive(this)">Schedule</button>
        <button type="button" class="btn btn-light" onclick="setActive(this)">Wallet</button>
    </div>

    <div>

    </div>
</body>

<script>
    function setActive(button) {
        // Remove active class from all buttons
        var buttons = document.querySelectorAll('.taskbar .btn');
        buttons.forEach(function(btn) {
            btn.classList.remove('active');
        });

        // Add active class to the clicked button
        button.classList.add('active');
    }
</script>
</html>
