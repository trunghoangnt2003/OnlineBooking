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
    <div class="sidebar">
        <h2><i class="fa-solid fa-bars symbol"></i>Menu</h2>
        <a href="#home" class="active"><i class="fa-solid fa-house symbol"></i>Home</a>
        <a href="#chat"><i class="fa-solid fa-comments symbol"></i>Chat</a>
        <a href="#wallet"><i class="fa-solid fa-wallet symbol"></i>Wallet</a>
        <a href="#setting"><i class="fa-solid fa-gear symbol"></i>Setting</a>
    </div>

    <div class="content" id="home">
        <h1>Welcome to Frog</h1>
        <p>This is the main content area.</p>
    </div>
</body>
<script>
    // JavaScript to toggle active class
    document.querySelectorAll('.sidebar a').forEach(function(link) {
        link.addEventListener('click', function() {
            // Remove active class from all links
            document.querySelectorAll('.sidebar a').forEach(function(a) {
                a.classList.remove('active');
            });
            // Add active class to the clicked link
            this.classList.add('active');
        });
    });
</script>
</html>
