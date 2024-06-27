<%--
  Created by IntelliJ IDEA.
  User: ACER
  Date: 6/25/2024
  Time: 1:31 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://fonts.googleapis.com/css?family=Nunito+Sans:400,400i,700,900&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap" rel="stylesheet">
    <!-- MDB -->
    <link href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css" rel="stylesheet">
    <!-- Bootstrap -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <script src="https://cdn.plot.ly/plotly-latest.min.js"></script>
</head>
<style>
    body {
        text-align: center;
        padding: 40px 0;
        background: #EBF0F5;
    }
    h1 {
        color: #88B04B;
        font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
        font-weight: 900;
        font-size: 40px;
        margin-bottom: 10px;
    }
    p {
        color: #404F5E;
        font-family: "Nunito Sans", "Helvetica Neue", sans-serif;
        font-size:20px;
        margin: 0;
    }
    i {
        color: #9ABC66;
        font-size: 100px;
        line-height: 200px;
        margin-left:-15px;
    }
    .card {
        background: white;
        padding: 120px 200px;
        border-radius: 4px;
        box-shadow: 0 2px 3px #C8D0D8;
        display: inline-block;
        margin: 0 auto;
    }
</style>
<body>
<jsp:include page="/view/common/header.jsp"></jsp:include>

<div class="card">
    <div style="border-radius:200px; height:200px; width:200px; background: #F8FAF5; margin:0 auto;">
        <i class="checkmark">✓</i>
    </div>
    <h1>Success</h1>
    <p>We received your purchase request;<br/> Hope see you in another class</p>
    <br>-----------------------------------------------------------------------------------------------------</br>
    <div style="text-align: left;font-weight: bold">
    <h3 style="text-align: center;font-weight: bold;">Booking</h3>
        <p style="font-weight: bold"><span style="font-weight: bold">Mentee: </span> ${book.mentee.account.name}</p>
    <p>Amount: ${book.amount}</p>
    <p>Created date: ${book.date}</p>
    <p>Skill: ${book.level_skills.skill.name}</p>
    <p>Level: ${book.level_skills.level.name}</p>
    <p>Start Date: ${book.startDate}</p>
    <p>End Date: ${book.endDate}</p>
        <br/>
    <p style="text-align: center;margin-top: 10px;color: #1BB295">Thank you for confirming your booking</p>
    <p style="text-align: center;color: #1BB295">If you have any question please contact us at Frog Communication</p>
    </div>
</div>
</body>
</html>
