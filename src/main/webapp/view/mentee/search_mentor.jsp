<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ page contentType="text/html;charset=UTF-8" language="java" %><html><head>    <title>Search Mentor</title>    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/search_mentor/search_mentor.css">    <link rel="stylesheet"  href="${pageContext.request.contextPath}/css/search_skill/side_select.css">    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/search_mentor/pagination.css">    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/common/rating.css">    <!-- Fontawesome CDN Link -->    <link rel="stylesheet"          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css">    <!-- Google Fonts -->    <link            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"            rel="stylesheet"    />    <!-- MDB -->    <link            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css"            rel="stylesheet"    />    <!-- SweetAlert2 -->    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>    <!-- Bootstrap CSS -->    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous"></head><body>    <jsp:include page="../common/header.jsp"></jsp:include><div class="main" style="background-color: rgb(246, 255, 253)">    <div class="image">        <img src="${pageContext.request.contextPath}/img/Banner.png" alt="Logo"/>    </div>    <div class="body">        <div style="margin: 0 0  70px 100px ">            <a href="${pageContext.request.contextPath}/Search_Skills" style="text-decoration: none">                <i class="fa-solid fa-arrow-right-to-bracket fa-rotate-180 fa-xl" style="color: #07ad90"></i>                &nbsp;<span style="color: #07ad90; font-weight: 500; font-size: 20px; margin-top: 10px" >Search skills</span>            </a>        </div>        <div class="container">            <div class="content_header">                <div class="search_order">                    <div class="search">                        <div class="search-box">                            <label>                                <input type="text" id="search-bar" class="search-input" placeholder="Search..." />                            </label>                            <button class="search-btn" onclick="searchHandle()">                                <i class="fa-solid fa-magnifying-glass fa-2xs" style="color: #179b81"></i>                            </button>                        </div>                    </div>                    <div class="order-content">                        <label>                            <select name="order" id="order" onchange="orderHandle()">                                <option value="0" disabled selected >Order</option>                                <option value="1" ${requestScope.order.equals('1')? 'selected' : ''} >Rate</option>                                <option value="2" ${requestScope.order.equals('2')? 'selected' : ''}> Popular</option>                            </select>                        </label>                    </div>                </div>            </div>            <div style="margin-left: 10px ">                <div style="display: flex; align-items: center">                    <img src="${pageContext.request.contextPath}/${requestScope.skill.src_icon}" style="width: 40px; height: 40px" alt="">                    <h5 style="color: #07ad90; margin: 0 0 0 10px" >                        ${requestScope.skill.name} for ${requestScope.level}</h5>                </div>                <span style="opacity: 50%" >${requestScope.total_mentor} Mentors</span>                <c:if test="${requestScope.search != null && requestScope.search != ''}">                    <span style="opacity: 50%"  >                        with name ${requestScope.search}                    </span>                    <span data-mdb-tooltip-init title="reload" >                        <i class="fa-solid fa-rotate-right fa-lg" style="color: #07AD90;                         margin-left: 5px; cursor: pointer;" onclick="clearSearch()" ></i>                    </span>                </c:if>            </div>            <div class="content_body">                <c:forEach items="${requestScope.list_mentor}" var="mentor">                    <div class="cart">                        <div class="image-content">                            <span class="overlay"></span>                            <div class="card-image">                                <img src="${pageContext.request.contextPath}/img/Logo.png" alt="" class="card-img">                                <!--<img src="images/profile3.jpg" alt="" class="card-img">-->                            </div>                        </div>                        <div class="card-content">                            <h2 class="name">${mentor.account.name}</h2>                            <div class="stars-outer">                                <div class="stars-inner" data-rating="${mentor.rating}"></div>                            </div>                            <div class="number-booking">                                <span>${mentor.totalBookings}</span>                                <i class="fa-solid fa-user-graduate"></i>                            </div>                            <div>                                <span style="font-size: 13px; margin: 5px 0" class="dobSpan" data-dob="${mentor.account.dob}" ></span>                            </div>                            <div class="btn-action">                                <div class="btn-wish" onclick="wishHandle('${mentor.account.id}')" style="cursor: pointer">                                    <c:set var="isWish" value="0"/>                                    <c:forEach items="${requestScope.wishList}" var="wl">                                        <c:if test="${wl.mentor.account.id == mentor.account.id}">                                            <i class="fa-solid fa-heart fa-xl" style="color: #ff0000;"></i>                                            <c:set var="isWish" value="1"/>                                        </c:if>                                    </c:forEach>                                    <c:if test="${isWish == 0}">                                        <i class="fa-regular fa-heart fa-xl"></i>                                    </c:if>                                </div>                                <button type="button" onclick="bookingHandle('${mentor.account.id}', '${requestScope.skill.name}', '${requestScope.level}')" class="btn btn-outline-success btn-rounded booking" data-mdb-ripple-init  data-mdb-ripple-color="dark">Booking</button>                            </div>                        </div>                    </div>                </c:forEach>            </div>            <div class="content_pagination" ${requestScope.end_page > 1 ? '' : 'style="display: none"'}>                <nav aria-label="Page navigation">                    <ul class="pagination" >                            <li class="page-item" >                                <div class="page-link" <c:if test="${requestScope.page > 1}">                                                            onclick="prevPage()"                                                    </c:if>                                   aria-label="Previous" style="cursor: pointer ">                                    <span aria-hidden="true">&laquo;</span>                                    <span class="sr-only">Previous</span>                                </div>                            </li>                        <c:forEach begin="1" end="${requestScope.end_page}" var="i">                            <li class="page-item ${requestScope.page == i ? 'active' : ''}">                                <a class="page-link" onclick="toPage(${i})" style="cursor: pointer">${i}</a>                            </li>                        </c:forEach>                            <li class="page-item">                                <div class="page-link" <c:if test="${requestScope.page < requestScope.end_page}">                                                            onclick="nextPage()"                                                        </c:if>                                   aria-label="Next" style="cursor: pointer">                                    <span aria-hidden="true">&raquo;</span>                                    <span class="sr-only">Next</span>                                </div>                            </li>                    </ul>                </nav>            </div>        </div>    </div></div>    <script src="${pageContext.request.contextPath}/js/common/rating.js"></script>    <script>    function ageCalc(dateString) {        // Parse the date string into a Date object        const dob = new Date(dateString);        // Calculate the current date        const today = new Date();        // Calculate the age        let age = today.getFullYear() - dob.getFullYear();        // Check if the birthday has occurred this year        if (today.getMonth() < dob.getMonth() ||            (today.getMonth() === dob.getMonth() && today.getDate() < dob.getDate())) {            age--;        }        return age;    }    document.addEventListener('DOMContentLoaded', () => {        // Get all elements with the class "dobSpan"        const dobSpans = document.querySelectorAll('.dobSpan');        // Loop through each span element and calculate the age        dobSpans.forEach(function(span) {            // Get the date of birth from the data-dob attribute            const dob = span.getAttribute('data-dob');            // Calculate the age using the ageCalc() function            const age = ageCalc(dob);            // Set the calculated age as the content of the span            span.textContent = age + " years old";        });    });    document.querySelector('.search-btn').addEventListener('click', function () {        this.parentElement.classList.toggle('open')        this.previousElementSibling.focus()    })    function nextPage() {        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const nextPage = ${requestScope.page + 1};        const order = document.getElementById("order").value;        let search;        if ("${requestScope.search}" !== "") {            search = encodeURIComponent("${requestScope.search}");        } else {            search = document.getElementById("search-bar").value;        }        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+ '&order='+ order+ '&search='+search+'&page='+nextPage;    }    function prevPage() {        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const prevPage = ${requestScope.page - 1};        const order = document.getElementById("order").value;        let search;        if ("${requestScope.search}" !== "") {            search = encodeURIComponent("${requestScope.search}");        } else {            search = document.getElementById("search-bar").value;        }        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+ '&order='+ order+ '&search='+search+'&page='+prevPage;    }    function toPage(page) {        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const order = document.getElementById("order").value;        let search;        if ("${requestScope.search}" !== "") {            search = encodeURIComponent("${requestScope.search}");        } else {            search = document.getElementById("search-bar").value;        }       window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+'&order='+ order+'&search='+search+'&page='+page;    }    function orderHandle(){        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const order = document.getElementById("order").value;        let search;        if ("${requestScope.search}" !== "") {            search = encodeURIComponent("${requestScope.search}");        } else {            search = document.getElementById("search-bar").value;        }        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+'&order='+ order+'&search='+search;    }    let idOpen = false;    function searchHandle(){        if (!idOpen) {            idOpen=true;        }else {        const name = document.getElementById("search-bar").value;        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const order = document.getElementById("order").value;        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+'&order='+ order+'&search='+name;        }    }    function clearSearch(){        const skill = encodeURIComponent("${requestScope.skill.name}");        const level = encodeURIComponent("${requestScope.level}");        const order = document.getElementById("order").value;        window.location.href = 'SearchMentor?skill='+ skill +'&level='+level+'&order='+ order;    }    function bookingHandle(mentorId, skill_name, level) {        console.log(mentorId);        const skill = encodeURIComponent(skill_name);        window.location.href = 'mentee/booking-schedule?mentorId='+ mentorId +'&skill='+ skill +'&level='+ level;    }    function wishHandle(mentorId) {        fetch("../mentee/wish", {            method: "POST",            headers: {                "Content-Type": "application/json",            },            body: JSON.stringify({mentorId: mentorId}),        }).then( response => {            if(response.ok) {                location.reload();            } else {                throw new Error('Network response was not ok.');            }        }).catch(            error => {                console.error('There has been a problem with your fetch operation:', error);            }        )        // window.location.href = 'mentee/wish?mentorId='+ mentorId;    }</script>    <!-- MDB -->    <script            type="text/javascript"            src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.3.0/mdb.umd.min.js"    ></script>    <!-- Separate Popper and Bootstrap JS -->    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js" integrity="sha384-IQsoLXl5PILFhosVNubq5LC7Qb9DXgDA9i+tQ8Zj3iwWAwPtgFTxbJ8NT4GN1R8p" crossorigin="anonymous"></script>    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.min.js" integrity="sha384-cVKIPhGWiC2Al4u+LWgxfKTRIcfu0JTxR+EQDz/bgldoEyl4H0zUF0QKbrJ0EcQF" crossorigin="anonymous"></script></body>