<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: kttan
  Date: 6/19/2024
  Time: 10:30 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Wallet</title>
    <!-- Fontawesome CDN Link -->
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"
            rel="stylesheet"
    />
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- Custom CSS -->
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }

        .dashboard {
            display: flex;
        }

        .sidebar {
            width: 250px;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .profile {
            text-align: center;
            margin-bottom: 20px;
        }

        .profile img {
            width: 100px;
            border-radius: 50%;
        }

        .balance, .help {
            text-align: center;
            margin-bottom: 20px;
        }

        .balance h3 {
            font-size: 24px;
            margin: 0;
        }

        .balance p {
            color: #888;
        }
        .balance button,
        .help button {
            display: block;
            width: 100%;
            padding: 10px;
            margin: 10px 0;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 16px;
        }

        .balance button:first-of-type {
            background-color: #4CAF50; /* Green */
            color: white;
        }

        .btn2 {
            background-color: yellowgreen;
            color: white;
        }

        .balance button:last-of-type {
            background-color: #008CBA; /* Blue */
            color: white;
        }

        .help {
            background-color: #f9f9f9;
            padding: 20px;
            border-radius: 10px;
        }

        .main-content {
            flex: 1;
            padding: 20px;
        }

        .profile-completeness {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 20px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .profile-completeness h3 {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .profile-completeness .steps {
            display: flex;
            justify-content: space-around;
            margin-top: 20px;
        }

        .profile-completeness .step {
            text-align: center;
            width: 100px;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
        }

        .recent-activity {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        .recent-activity table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }

        .recent-activity th, .recent-activity td {
            padding: 10px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        .recent-activity th {
            background-color: #f4f4f4;
        }

        .recent-activity .status {
            display: inline-block;
            width: 10px;
            height: 10px;
            border-radius: 50%;
        }

        .recent-activity .status.green {
            background-color: #4CAF50;
        }

        .recent-activity .status.yellow {
            background-color: #FFEB3B;
        }

        .recent-activity .status.red {
            background-color: #F44336;
        }

        .recent-activity a {
            display: block;
            text-align: center;
            color: #008CBA;
            text-decoration: none;
        }
        .status-processing {
            color: rgba(255,214,47,0.6);
        }

        .status-done {
            color: green;
        }

        .status-reject {
            color: red;
        }

    </style>
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>

<div class="dashboard">
    <div class="sidebar">
        <div class="profile">
            <img src="${pageContext.request.contextPath}/${requestScope.account.avatar}" alt="Profile Picture">
            <h2>${requestScope.account.name}</h2>
        </div>
        <div class="balance">
            <h3 style="color: #07AD90; font-weight: bold">
                Available <fmt:formatNumber value="${requestScope.wallet.balance - requestScope.wallet.hold}" type="number" maxFractionDigits="0" />₫
            </h3>
            <p style="margin-bottom: 0">Total: ${requestScope.balance}</p>
            <p>Hold: ${requestScope.hold}</p>
            <c:if test="${requestScope.role == 1}">
                <button>Withdraw</button>
                <button onclick="payment()" class="btn2">Deposit</button>
            </c:if>
            <c:if test="${requestScope.role == 2}">
                <button>Withdraw</button>
                <button class="btn2">Deposit</button>
            </c:if>
        </div>
        <div class="help">
            <p>Need Help?</p>
            <p>Have questions or concerns regarding your account?</p>
            <button>Chat with Us</button>
        </div>
    </div>
    <div class="main-content">
<%--        <div class="profile-completeness">--%>
<%--            <div class="steps">--%>
<%--                <div class="step">Add Card</div>--%>
<%--                <div class="step">Add Bank Account</div>--%>
<%--            </div>--%>
<%--        </div>--%>
        <div class="recent-activity">
            <h3>Recent Activity</h3>
            <table>
                <thead>
                <tr>
                    <th>ID Transaction</th>
                    <th>Amount</th>
                    <c:if test="${requestScope.role == 2}">
                        <th>Fee</th>
                    </c:if>
                    <th>Date</th>
                    <th>Type</th>
                    <th>Sender</th>
                    <th>Receiver</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${requestScope.transactions}" var="t">
                    <tr>
                        <td>${t.id}</td>
                        <td>
                            <c:if test="${t.typeTransaction.name == 'Deposit'}">
                                <span><i class="fa-solid fa-download"></i></span>
                            </c:if>
                            <c:if test="${t.typeTransaction.name == 'Withdrawl'}">
                                <span><i class="fa-solid fa-upload"></i></span>
                            </c:if>
                            <c:if test="${t.typeTransaction.name == 'Payment'}">
                                <span><i class="fa-solid fa-money-bill-trend-up"></i></span>
                            </c:if>

                            <span>
                                <fmt:formatNumber value="${t.amount}" type="number" maxFractionDigits="0" />₫
                            </span>
                        </td>
                        <c:if test="${requestScope.role == 2}">
                            <td>
                                <c:if test="${t.fee == null}">
                                    -
                                </c:if>
                                <c:if test="${t.fee != null}">
                                    ${t.fee}
                                </c:if>
                            </td>
                        </c:if>
                        <td>${t.date}</td>
                        <td>
                            <c:if test="${t.typeTransaction.id == 1}">
                                Deposit
                            </c:if>
                            <c:if test="${t.typeTransaction.id == 2}">
                                Withdraw
                            </c:if>
                            <c:if test="${t.typeTransaction.id == 3}">
                                Payment
                            </c:if>
                        </td>
                        <td>${t.sender.name}</td>
                        <td>
                            <c:if test="${t.walletOpposite.id == null}">
                                -
                            </c:if>
                            <c:if test="${t.walletOpposite.id != null}">
                                ${t.receiver.name}
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <a href="#">View all</a>
        </div>
    </div>
</div>
<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>
<!-- Bootstrap Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.7/dist/umd/popper.min.js" integrity="sha384-oBqDVmMz4fnFO9gyb2OmO3B6DiY2Xpm8bn5MoZp9lFELYvgvEptxU8IjW/2y2Ek7" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-Zenh87qX5JnK2JlAK3uFnjH41BPrp12GO5aazZ1yXtW0NQm6QU5VVnY5Sv6M4M/a" crossorigin="anonymous"></script>

<script>
    function toggleTransactionDetails() {
        const transactionDetails = document.getElementById('transactionDetails');
        const showMoreBtn = document.getElementById('showMoreBtn');
        if (transactionDetails.classList.contains('show')) {
            transactionDetails.classList.remove('show');
            showMoreBtn.textContent = 'Show More';
        } else {
            transactionDetails.classList.add('show');
            showMoreBtn.textContent = 'Show Less';
        }
    }
    document.addEventListener("DOMContentLoaded", function() {
        // Example: Update profile completeness
        const profileCompleteness = document.querySelector('.profile-completeness h3 span');
        let completeness = 50; // Example percentage


        // Example: Handle chat button click
        const chatButton = document.querySelector('.help button');
        chatButton.addEventListener('click', function() {
            alert('Chat with Us feature coming soon!');
        });

        // Example usage: update profile completeness
        updateProfileCompleteness(75); // Update to 75% for demonstration
    });

    function payment() {
        window.location.href = "http://localhost:8080/Frog/payment";
    }
</script>

</body>
</html>
