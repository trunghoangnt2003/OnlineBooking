<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: trung
  Date: 5/29/2024
  Time: 4:53 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Mentor</title>
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">
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
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.min.css"
            rel="stylesheet"
    />
    <style>
        /*body{*/
        /*    margin: 0;*/
        /*    padding: 0;*/
        /*    background-color: #b2e0df;*/
        /*    height: 100vh;*/
        /*    display: flex;*/
        /*    justify-content: center;*/
        /*    align-items: center;*/
        /*}*/

        .search-container{
            background: #fff;
            height: 30px;
            border-radius: 30px;
            padding: 10px 20px;
            display: flex;
            justify-content: center;
            align-items: center;
            cursor: pointer;
            transition: 0.8s;
            /*box-shadow:inset 2px 2px 2px 0px rgba(255,255,255,.5),
            inset -7px -7px 10px 0px rgba(0,0,0,.1),
           7px 7px 20px 0px rgba(0,0,0,.1),
           4px 4px 5px 0px rgba(0,0,0,.1);
           text-shadow:  0px 0px 6px rgba(255,255,255,.3),
                      -4px -4px 6px rgba(116, 125, 136, .2);
          text-shadow: 2px 2px 3px rgba(255,255,255,0.5);*/
            box-shadow:  4px 4px 6px 0 rgba(255,255,255,.3),
            -4px -4px 6px 0 rgba(116, 125, 136, .2),
            inset -4px -4px 6px 0 rgba(255,255,255,.2),
            inset 4px 4px 6px 0 rgba(0, 0, 0, .2);
        }

        .search-container:hover > .search-input{
            width: 400px;
        }

        .search-container .search-input{
            background: transparent;
            border: none;
            outline:none;
            width: 0px;
            font-weight: 500;
            font-size: 16px;
            transition: 0.8s;

        }

        .search-container .search-btn .fas{
            color: #5cbdbb;
        }

        /*@keyframes hoverShake {*/
        /*    0% {transform: skew(0deg,0deg);}*/
        /*    25% {transform: skew(5deg, 5deg);}*/
        /*    75% {transform: skew(-5deg, -5deg);}*/
        /*    100% {transform: skew(0deg,0deg);}*/
        /*}*/

        .search-container:hover{
            animation: hoverShake 0.15s linear 3;
        }
        .custom-thead {
            background-color: rgba(176, 237, 215, 0.3);
        }

    </style>
    <link href="https://use.fontawesome.com/releases/v5.6.1/css/all.css" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">
</head>
<body>
<div class="container">
    <br>
    <div class="search-container">
        <input type="text" name="search" placeholder="Search..." class="search-input">
        <a href="#" class="search-btn">
            <i class="fas fa-search"></i>
        </a>
    </div>
    <table class="table" >
        <thead class="table-dark custom-thead" style="background-color: rgba(176, 237, 215, 0.3);">
            <td>STT</td>
            <td>ID</td>
            <td>Full Name</td>
            <td>Account Name</td>
            <td>Profession</td>
            <td>Accepted Request</td>
            <td>Percentage Completed</td>
            <td>Rate Star</td>
            <td>Active</td>
        </thead>
        <tbody style="height: 200px" >
            <c:set var="count" value="1"/>
            <c:forEach items="${requestScope.list}" var="mentor" >
                <tr>

                    <td>${count}</td>
                    <c:set var="count" value="${count+1}"/>
                    <td><span id="myInput">${mentor.account.id}</span> <!-- The button used to copy the text --></td>
                    <td>${mentor.account.name}</td>
                    <td>Account Name</td>
                    <td>Profession</td>
                    <td>Accepted Request</td>
                    <td>Percentage Completed</td>
                    <td>Rate Star</td>
                    <td>Active</td>
                </tr>
            </c:forEach>

        </tbody>
    </table>
    <nav aria-label="Page navigation example" >
        <ul class="pagination justify-content-end">
            <li class="page-item <c:if test="${requestScope.page==1}"> disabled </c:if>">
                <a class="page-link" href="?page=${requestScope.page-1}"
                        <c:if test="${requestScope.page==1}"> tabindex="-1" </c:if>
                >Previous</a>
            </li>
            <c:forEach var="count" begin="1" end="${requestScope.end_page}">
                    <c:if test="${requestScope.page == count}">
                        <li class="page-item"><a class="page-link active">${count}</a></li>
                    </c:if>
                    <c:if test="${requestScope.page != count}">
                        <li class="page-item"><a class="page-link" href="?page=${count}">${count}</a></li>
                    </c:if>
            </c:forEach>
            <li class="page-item">
                <a class="page-link <c:if test="${requestScope.page==requestScope.end_page}"> disabled </c:if>" href="?page=${requestScope.page+1}"
                        <c:if test="${requestScope.page==requestScope.end_page}"> tabindex="-1" </c:if>
                >Next</a>
            </li>
        </ul>
    </nav>
</div>

<!-- MDB -->
<script
        type="text/javascript"
        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"
></script>

<script>
    document.querySelector('.search-btn').addEventListener('click', function () {
        this.parentElement.classList.toggle('open')
        this.previousElementSibling.focus()
    })

</script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script>
</body>
</html>
