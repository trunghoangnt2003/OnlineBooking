<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/wallet.css">
</head>
<body>
<jsp:include page="../common/header.jsp"></jsp:include>

<div class="container mt-5">
    <div class="row mb-4">
        <div class="col">
            <h2>Hello, ${requestScope.name}</h2>
        </div>
    </div>

    <div class="row">
        <!-- Sidebar Menu -->
        <div class="col-md-3">
            <div class="list-group">
                <a href="wallet.jsp" class="list-group-item list-group-item-action">
                    <i class="fa-solid fa-chart-pie me-2"></i>Dashboard
                </a>
                <a href="dashboard.jsp" class="list-group-item list-group-item-action active">
                    <i class="fa-solid fa-wallet me-2"></i>My Wallet
                </a>
            </div>
        </div>

        <div class="col-md-5">
            <div class="card history">
                <div class="card-body">
                    <h3 class="card-title">My Wallet</h3>
                    <div class="row">
                        <div class="col-md-6">
                            <p class="card-text balance">Balance: ${requestScope.wallet.balance} <i class="fa-solid fa-dollar-sign"></i></p>
                            <p class="card-text available">Available: ${requestScope.wallet.available} <i class="fa-solid fa-dollar-sign"></i></p>
                        </div>
                        <div class="col-md-6">
                            <button type="button" class="btn btn-primary" data-mdb-ripple-init>Deposit</button>
                            <button type="button" class="btn btn-primary" data-mdb-ripple-init>Withdraw</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- Transaction History -->
        <div class="col-md-4">
            <div class="card history">
                <div class="card-body">
                    <h3 class="card-title">Transaction History</h3>

                    <div id="transactionDetails" class="collapse">
                        <c:forEach items="${requestScope.transactions}" var="t">
                            <div class="transaction mb-3">
                                <p><strong>Receiver:</strong> ${t.account.name}</p>
                                <p><strong>Amount:</strong> ${t.amount}</p>
                                <p><strong>Date:</strong> ${t.date}</p>
                                <p><strong>Type:</strong> ${t.typeTransaction.name}</p>
                                <p><strong>Fee:</strong> ${t.fee}</p>
                                <hr>
                            </div>
                        </c:forEach>
                    </div>
                    <button class="btn btn-primary" id="showMoreBtn" onclick="toggleTransactionDetails()">Show More</button>
                </div>
            </div>
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
</script>

</body>
</html>
