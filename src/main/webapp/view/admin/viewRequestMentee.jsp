<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">

    <!-- Google Fonts -->
    <link
            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"
            rel="stylesheet"
    />
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
    <!-- MDB -->
    <link
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"
            rel="stylesheet"
    />
    <!-- Custom CSS -->
    <style>
        .search-container {
            margin: 20px 100px;
            display: flex;
            align-items: center;
            justify-content: space-between;
        }
        .search-container .text-input-container {
            flex-grow: 1;
            margin-right: 15px;
        }
        .search-container .form-group {
            margin-right: 15px;
        }
        .table-containers {
            display: flex;
            justify-content: center;
        }
        .table-containers{
            width: 95%;
            border-radius: 5px;
            margin-left: 50px;
        }
    </style>
</head>
<body>
<div class="search-container">
    <div class="text-input-container">
        <input type="text" class="form-control" placeholder="Search by username" id="search-username">
    </div>
    <div class="form-group">
        <select class="form-control" id="filter-status">
            <option value="">All Statuses</option>
            <option value="pending">Pending</option>
            <option value="approved">Approved</option>
            <option value="rejected">Rejected</option>
        </select>
    </div>
    <div class="form-group">
        <input type="date" class="form-control" id="start-date">
    </div>
    <div class="form-group">
        <input type="date" class="form-control" id="end-date">
    </div>
    <div class="form-group">
        <button class="btn btn-primary" id="search-button">Search</button>
    </div>
</div>

<div class="table-containers">
    <table class="table table-hover" id="user-table" style="width: 100%; border-radius: 5px">
        <thead class="table-dark">
        <tr>
            <th scope="col">STT</th>
            <th scope="col">Booking ID</th>
            <th scope="col">Name Mentee</th>
            <th scope="col">Status</th>
            <th scope="col">Title Request</th>
            <th scope="col">Amount</th>
            <th scope="col">Create Date</th>
            <th scope="col">Name Mentor</th>
            <th scope="col">Skill and level</th>
        </tr>
        </thead>
        <tbody>
        <c:set var="count" value="1"/>
        <c:forEach items="${requestScope.list}" var="request">
            <tr>
                <td>${count}</td>
                <c:set var="count" value="${count+1}"/>
                <td>${request.id}</td>
                <td>${request.mentee.account.name}</td>
                <td>${request.status.type}</td>
                <td>${request.description}</td>
                <td>${request.amount}</td>
                <td>${request.date}</td>
                <td>${request.mentor.account.name}</td>
                <td>${request.level_skills.skill.name} +" "+ ${request.level_skills.level.name}"</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div style="display: flex; justify-content: space-between">
    <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-end">
            <li class="page-item ${page == 1 ? 'disabled' : ''}">
                <a class="page-link" href="?page=${page - 1}" ${page == 1 ? 'tabindex="-1"' : ''}>Previous</a>
            </li>
            <c:forEach var="count" begin="1" end="${end_page}">
                <li class="page-item ${page == count ? 'active' : ''}">
                    <a class="page-link" href="?page=${count}">${count}</a>
                </li>
            </c:forEach>
            <li class="page-item ${page == end_page || end_page == 0 ? 'disabled' : ''}">
                <a class="page-link" href="?page=${page + 1}" ${page == end_page || end_page == 0 ? 'tabindex="-1"' : ''}>Next</a>
            </li>
        </ul>
    </nav>
</div>


</body>
</html>
