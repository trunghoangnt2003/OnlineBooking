
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%--<!DOCTYPE html>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">--%>
<%--    <title>JSP Page</title>--%>
<%--    <!-- Font Awesome -->--%>
<%--    <link--%>
<%--            rel="stylesheet"--%>
<%--            href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"--%>

<%--    />--%>
<%--    <!-- Google Fonts -->--%>
<%--&lt;%&ndash;    <link&ndash;%&gt;--%>
<%--&lt;%&ndash;            href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap"&ndash;%&gt;--%>
<%--&lt;%&ndash;            rel="stylesheet"&ndash;%&gt;--%>
<%--&lt;%&ndash;    />&ndash;%&gt;--%>
<%--    <!-- MDB -->--%>
<%--    <link--%>
<%--            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.min.css"--%>
<%--            rel="stylesheet"--%>
<%--    />--%>
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js" integrity="sha384-I7E8VVD/ismYTF4hNIPjVp/Zjvgyol6VFvRkX/vR+Vc4jQkC+hVqc2pM8ODewa9r" crossorigin="anonymous"></script>--%>
<%--    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js" integrity="sha384-BBtl+eGJRgqQAUMxJ7pMwbEyER4l1g+O15P+16Ep7Q9Q+zqX6gSbd85u4mG4QzX+" crossorigin="anonymous"></script>--%>
<%--    <script>--%>
<%--        import { Dropdown, Ripple, initMDB } from "mdb-ui-kit";--%>

<%--        initMDB({Dropdown, Ripple});--%>
<%--    </script>--%>
<%--    <style>--%>

<%--    </style>--%>


<%--    <!-- Custom styles for this template -->--%>
<%--    <link href="https://fonts.googleapis.com/css?family=Playfair+Display:700,900&amp;display=swap" rel="stylesheet">--%>
<%--    <!-- Custom styles for this template -->--%>
<%--    <link href="blog.css" rel="stylesheet">--%>

<%--</head>--%>
<%
    String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + request.getContextPath();
%>
<div class="        " style="
position: sticky; top:0; z-index: 999">
    <header class="border-bottom">
        <nav class="navbar navbar-expand-lg navbar-light" style="background-color: rgb(176, 237, 215)">
            <!-- Container wrapper -->
            <div class="container">
                <!-- Navbar brand -->
                <a class="navbar-brand me-2" href="#">
                    <img
                            src="${pageContext.request.contextPath}/img/Logo.png"
                            height="35"
                            alt="Logo"
                            loading="lazy"
                            style="margin-top: -1px;"
                    />
                </a>

                <!-- Toggle button -->
                <button
                        data-mdb-collapse-init
                        class="navbar-toggler"
                        type="button"
                        data-mdb-target="#navbarButtonsExample"
                        aria-controls="navbarButtonsExample"
                        aria-expanded="false"
                        aria-label="Toggle navigation"
                >
                    <i class="fas fa-bars"></i>
                </button>

                <!-- Collapsible wrapper -->
                <div class="collapse navbar-collapse" id="navbarButtonsExample">
                    <!-- Left links -->
                    <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <c:if test="${sessionScope.account.role.id == 1}">
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/Home">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/Search_Skills">Search</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/mentee/schedule">Schedule</a>
                            </li>

                        </c:if>
                        <c:if test="${sessionScope.account.role.id == 2}">
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/Home">Home</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/mentor/update_profile">Update CV</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/mentor/schedule">Schedule</a>
                            </li>


                        </c:if>
                        <c:if test="${sessionScope.account.role.id == 3}">
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/admin/dash">Dash Board</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/admin/mentor">Manage</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/Search_Skills">Search Skills</a>
                            </li>
                        </c:if>
                        <c:if test="${sessionScope.account.role.id == 4}">
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/admin/mentor">Manage</a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="<%=url%>/Search_Skills">Search Skills</a>
                            </li>
                        </c:if>
                    </ul>
                    <!-- Left links -->

                    <c:if test="${sessionScope.account==null}">
                        <div class="d-flex align-items-center">
                            <button data-mdb-ripple-init type="button" class="btn px-3 me-2" style='background-color: #07ad90;'>
                                <a href="<%=url%>/login" style="text-decoration: none">Sign in </a>
                            </button>
                            <button data-mdb-ripple-init type="button" class="btn me-3" style='background-color: #07ad90;'>
                                <a href="<%=url%>/register" style="text-decoration: none">Sign up for free</a>
                            </button>
                        </div>
                    </c:if>
                    <c:if test="${sessionScope.account!=null}">
                        <div class="d-flex align-items-center">
                            <div>
                                <span style="color: #07ad90"> ${sessionScope.account.userName}</span>
                            </div>
                            <!-- Avatar -->
                            <div class="dropdown">
                                <a
                                        data-mdb-dropdown-init
                                        class="dropdown-toggle d-flex align-items-center hidden-arrow"
                                        href="#"
                                        id="navbarDropdownMenuAvatar"
                                        role="button"
                                        aria-expanded="false"
                                >
                                <c:if test="${sessionScope.account.avatar == null}">
                                            <img
                                                    src="https://www.logolynx.com/images/logolynx/4b/4beebce89d681837ba2f4105ce43afac.png"
                                                    class="rounded-circle"
                                                    height="25"
                                                    alt="Logo user"
                                                    loading="lazy"
                                            />
                                </c:if>
                                    <c:if test="${sessionScope.account.avatar != null}">
                                        <img
                                                src="${pageContext.request.contextPath}/${sessionScope.account.avatar}"
                                                class="rounded-circle"
                                                height="25"
                                                alt="Logo user"
                                                loading="lazy"
                                        />
                                    </c:if>
                                </a>
                                <ul
                                        class="dropdown-menu dropdown-menu-end"
                                        aria-labelledby="navbarDropdownMenuAvatar"
                                >
                                    <li>
                                        <c:if test="${sessionScope.account.role.id == 1}">
                                        <a class="dropdown-item" href="<%=url%>/mentee/profile?menteeid=${sessionScope.account.id}">Information</a>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role.id == 2}">
                                            <a class="dropdown-item" href="<%=url%>/mentor/profile?mentorid=${sessionScope.account.id}">Information</a>
                                        </c:if>
                                    </li>
                                    <li>
                                        <c:if test="${sessionScope.account.role.id == 1}">
                                            <a class="dropdown-item" href="<%=url%>/mentee/update">Settings</a>
                                        </c:if>
                                        <c:if test="${sessionScope.account.role.id == 2}">
                                            <a class="dropdown-item" href="<%=url%>/mentor/update_profile">Settings</a>
                                        </c:if>
                                    </li>
                                    <c:if test="${sessionScope.account.role.id == 1}">
                                    <li>
                                        <a class="dropdown-item" href="<%=url%>/mentee/viewBooking">Bookings</a>
                                    </li>
                                        <li>
                                            <a class="dropdown-item" href="<%=url%>/wallet/view">Wallet</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="<%=url%>/changePass">Change Password</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.account.role.id == 1}">
                                        <li>
                                            <a class="dropdown-item" href="<%=url%>/mentee/dashboard">Dash Board</a>
                                        </li>
                                    </c:if>
                                    <c:if test="${sessionScope.account.role.id == 2}">
                                        <li>
                                            <a class="dropdown-item" href="<%=url%>/mentor/view_follower">Follower</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="<%=url%>/wallet/view">Wallet</a>
                                        </li>
                                        <li>
                                            <a class="dropdown-item" href="<%=url%>/changePass">Change Password</a>
                                        </li>
                                    </c:if>
                                    <li>
                                        <a class="dropdown-item" href="<%=url%>/logout">Logout</a>
                                    </li>
                                </ul>
                            </div>
                        </div>


                    </c:if>
                </div>
                <!-- Collapsible wrapper -->
            </div>
            <!-- Container wrapper -->
        </nav>
    </header>
</div>
<!-- MDB -->
<%--<script--%>
<%--        type="text/javascript"--%>
<%--        src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.1.0/mdb.umd.min.js"--%>
<%--></script>--%>
<%--</html>--%>
