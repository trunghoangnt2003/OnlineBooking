<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <!--Phan dau -->
    <title>Profile Mentee</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/profile.css" >
    <style>

    </style>
    <link href="https://www.figma.com/design/yIRvLUVY8WgoE9jW39M4d8/Frog?node-id=10-38&m=dev" rel="stylesheet">
</head>
<body>
<!--Phan than -->
    <div class="wrapper">
        <div  class="header1">
        </div>
        <!-- end header-->
         <div class="main-content">
             <div class= "main-content-profile">
                <div class="content-profile-left">
                    <div class="avatar-infor">
                        <div style="margin-right: 30px" class="avatar">avatar</div>
                        <div class="all-infor" style="font-size: 30px">
                            <div class="name" >
                                <div style="margin-right: 10px"><svg width="38" height="33" viewBox="0 0 38 33" fill="none" xmlns="http://www.w3.org/2000/svg">
                                    <path d="M18.7917 0C13.624 0 9.39583 4.62 9.39583 10.3125C9.39583 16.005 13.624 20.625 18.7917 20.625C23.9594 20.625 28.1875 16.005 28.1875 10.3125C28.1875 4.62 23.9594 0 18.7917 0ZM8.97302 20.625C3.99323 20.8312 0 24.42 0 28.875V33H37.5833V28.875C37.5833 24.42 33.6371 20.8312 28.6103 20.625C26.0734 23.1413 22.597 24.75 18.7917 24.75C14.9864 24.75 11.5099 23.1413 8.97302 20.625Z" fill="black"/>
                                </svg>
                                </div>
                                <div class="infor">${requestScope.mentee.account.name}</div>
                            </div>
                            <div class="name" >
                                <div style="margin-right: 10px">
                                    <svg width="41" height="33" viewBox="0 0 41 33" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M0 0V5.5L20.5 16.5L41 5.5V0H0ZM0 11V33H41V11L20.5 22L0 11Z" fill="black"/>
                                    </svg>
                                </div>
                                <div class="infor" >${requestScope.mentee.account.email}</div>
                            </div>
                            <div class="name">
                                <div  style="margin-right: 10px">
                                    <svg width="38" height="51" viewBox="0 0 38 51" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M1.40381 0C0.591076 0 0 0.509363 0 1.20974V49.7903C0 50.4906 0.591076 51 1.40381 51H35.6123C36.4251 51 37.0161 50.4906 37.0161 49.7903V1.20974C37.0161 0.509363 36.4251 0 35.6123 0L1.40381 0ZM7.38845 6.36704H29.5538V38.2022H7.38845V6.36704ZM18.4711 41.3858C20.5399 41.3858 22.1654 42.7865 22.1654 44.5693C22.1654 46.3521 20.5399 47.7528 18.4711 47.7528C16.4024 47.7528 14.7769 46.3521 14.7769 44.5693C14.7769 42.7865 16.4024 41.3858 18.4711 41.3858Z" fill="black"/>
                                    </svg>
                                </div>
                                <div class="infor" >${requestScope.mentee.account.phone}</div>
                            </div>
                            <div class="name">
                                <div  style="margin-right: 10px">
                                    <svg width="44" height="58" viewBox="0 0 44 58" fill="none" xmlns="http://www.w3.org/2000/svg">
                                        <path d="M21.75 0C9.715 0 0 9.715 0 21.75C0 36.25 21.75 58 21.75 58C21.75 58 43.5 36.25 43.5 21.75C43.5 9.715 33.785 0 21.75 0ZM21.75 7.25C29.7975 7.25 36.25 13.775 36.25 21.75C36.25 29.7975 29.7975 36.25 21.75 36.25C13.775 36.25 7.25 29.7975 7.25 21.75C7.25 13.775 13.775 7.25 21.75 7.25Z" fill="black"/>
                                    </svg>

                                </div>
                                <div class="infor" >${requestScope.mentee.account.address}</div>
                            </div>
                        </div>
                    </div>

                    <div class="function">
                        <div style="margin-right: 10px" class="function-box"><a href="/Prog/mentee/profile"> My Profile</a></div>
                        <div style="margin-right: 10px" class="function-box"><a href="/Prog/mentee/update">update profile</a></div>
                        <div style="margin-right: 10px" class="function-box">View Request</div>
                        <div style="margin-right: 10px" class="function-box">Wallet</div>
                    </div>
                </div>
                 <div class="content-profile-right">
                     <div><a>View Wish List</a></div>
                     <div><a>Chat</a></div>
                 </div>
             </div>
             <div class="main-content-table">
             </div>
         </div>
        <!-- end main-content-->
        <div id="footer">

        </div>
        <!-- end footer-->
    </div>
</body>
</html>
