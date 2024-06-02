<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ page contentType="text/html;charset=UTF-8" language="java" %><html><head>    <title>Search Mentor</title>    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/search_mentor/search_mentor.css">    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/search_skill/side_select.css">    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search_mentor/pagination.css">    <!-- Fontawesome CDN Link -->    <link rel="stylesheet"          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">    <!-- Google Fonts -->    <link            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"            rel="stylesheet"    />    <!-- MDB -->    <link            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css"            rel="stylesheet"    />    <!-- SweetAlert2 -->    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>    <!-- Bootstrap CSS -->    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous"></head><body>    <jsp:include page="../common/header.jsp"></jsp:include><div class="main" style="background-color: rgb(246, 255, 253)">    <div class="image">        <img src="${pageContext.request.contextPath}/img/Banner.png" alt="Logo"/>    </div>    <div class="body">        <div style="margin: 0 0  70px 100px ">            <a href="${pageContext.request.contextPath}/Search_Skills" style="text-decoration: none">                <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>                &nbsp;<span style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px" >Search skills</span>            </a>        </div>        <div class="container">            <div class="content_header">                <div class="search_order">                    <div class="search">                        <div class="search-box">                            <input type="text" class="search-input" placeholder="Search..." />                            <button class="search-btn">                                <i class="fa-solid fa-magnifying-glass fa-2xs" style="color: #179b81"></i>                            </button>                        </div>                    </div>                    <div class="order-content">                        <label>                            <select>                                <option disabled selected >Order</option>                                <option>Rate</option>                                <option>Popular</option>                            </select>                        </label>                    </div>                </div>            </div>            <div style="margin-left: 90px ">                <div style="display: flex; align-items: center">                    <img src="${pageContext.request.contextPath}/${requestScope.skill.src_icon}" style="width: 40px; height: 40px">                    <h5 style="color: #07ad90; margin: 0 0 0 10px" >                        ${requestScope.skill.name} for ${requestScope.level}</h5>                </div>                <span style="opacity: 50%" >${requestScope.total_mentor} Mentors</span>            </div>            <div class="content_body">                <c:forEach items="${requestScope.list_mentor}" var="mentor">                    <div class="cart">                        <div class="image-content">                            <span class="overlay"></span>                            <div class="card-image">                                <img src="${pageContext.request.contextPath}/img/Logo.png" alt="" class="card-img">                                <!--<img src="images/profile3.jpg" alt="" class="card-img">-->                            </div>                        </div>                        <div class="card-content">                            <h2 class="name">${mentor.account.name}</h2>                            <div class="stars-outer">                                <div class="stars-inner" data-rating="${mentor.rating}"></div>                            </div>                            <div class="number-booking">                                <span>${mentor.totalBookings}</span>                                <i class="fa-solid fa-user-graduate"></i>                            </div>                            <div>                                <span style="font-size: 13px; margin: 5px 0" class="dobSpan" data-dob="${mentor.account.dob}" ></span>                            </div>                            <div class="btn-action">                                <button type="button" class="btn btn-outline-success btn-rounded booking" data-mdb-ripple-init  data-mdb-ripple-color="dark">Booking</button>                            </div>                        </div>                    </div>                </c:forEach>            </div>            <div class="content_pagination" ${requestScope.end_page > 1 ? '' : 'style="display: none"'}>                <nav aria-label="Page navigation">                    <ul class="pagination" >                            <li class="page-item" >                                <div class="page-link" <c:if test="${requestScope.page > 1}">                                                            onclick="prevPage()"                                                    </c:if>                                   aria-label="Previous" style="cursor: pointer ">                                    <span aria-hidden="true">&laquo;</span>                                    <span class="sr-only">Previous</span>                                </div>                            </li>                        <c:forEach begin="1" end="${requestScope.end_page}" var="i">                            <li class="page-item ${requestScope.page == i ? 'active' : ''}">                                <a class="page-link" onclick="toPage(${i})" style="cursor: pointer">${i}</a>                            </li>                        </c:forEach>                            <li class="page-item">                                <div class="page-link" <c:if test="${requestScope.page < requestScope.end_page}">                                                            onclick="nextPage()"                                                        </c:if>                                   aria-label="Next" style="cursor: pointer">                                    <span aria-hidden="true">&raquo;</span>                                    <span class="sr-only">Next</span>                                </div>                            </li>                    </ul>                </nav>            </div>        </div>    </div></div>    <script>    document.addEventListener('DOMContentLoaded', () => {        // Total number of stars        const starsTotal = 5;        // Get all star rating elemen   ts        const starContainers = document.querySelectorAll('.stars-inner');        starContainers.forEach(starsInner => {            // Get the rating from the data-rating attribute            const rating = parseFloat(starsInner.getAttribute('data-rating'));            // Calculate the percentage of stars to fill            const starPercentage = (rating / starsTotal) * 100;            // Set the width of the stars-inner to the calculated percentage            starsInner.style.width = starPercentage + '%';        });    });    function ageCalc(dateString) {        // Parse the date string into a Date object        const dob = new Date(dateString);        // Calculate the current date        const today = new Date();        // Calculate the age        let age = today.getFullYear() - dob.getFullYear();        // Check if the birthday has occurred this year        if (today.getMonth() < dob.getMonth() ||            (today.getMonth() === dob.getMonth() && today.getDate() < dob.getDate())) {            age--;        }        return age;    }    document.addEventListener('DOMContentLoaded', () => {        // Get all elements with the class "dobSpan"        const dobSpans = document.querySelectorAll('.dobSpan');        // Loop through each span element and calculate the age        dobSpans.forEach(function(span) {            // Get the date of birth from the data-dob attribute            const dob = span.getAttribute('data-dob');            // Calculate the age using the ageCalc() function            const age = ageCalc(dob);            // Set the calculated age as the content of the span            span.textContent = age + " years old";        });    });    document.querySelector('.search-btn').addEventListener('click', function () {        this.parentElement.classList.toggle('open')        this.previousElementSibling.focus()    })    function nextPage() {        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const nextPage = ${requestScope.page + 1};        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+'&page='+nextPage;    }    function prevPage() {        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const prevPage = ${requestScope.page - 1};        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+'&page='+prevPage;    }    function toPage(page) {        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+'&page='+page;    }</script>    <!-- Separate Popper and Bootstrap JS -->    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script></body>