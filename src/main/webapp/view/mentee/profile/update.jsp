<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/17/2024
  Time: 8:46 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
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
            href="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.min.css"
            rel="stylesheet"
    />
    <title>Update My Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/update.css">
</head>
<body>
        <div class="main-container">
            <form action="update" method="post" enctype="multipart/form-data">
            <div class="row gutters">
                <div class="col-xl-3 col-lg-3 col-md-12 col-sm-12 col-12">
                    <div class="card h-100">
                        <div class="card-body">
                            <div class="account-settings">
                                <div class="user-profile d-flex justify-content-center">
                                    <div class="user-avatar" style="width: 200px; height: 200px ">
                                        <div class="upload">
                                            <div class="img">
                                            <img src="${pageContext.request.contextPath}/${requestScope.mentee.account.avatar}" alt="">
                                            </div>
                                               <div class="round">
                                                   <input type="file" name="photo" id="file-input" />
                                                   <div class="camera">
                                                   <label for="file-input" class="camera-icon">
                                                       <i class="fa fa-camera fa-sm" style="color: white;"></i>
                                                   </label>
                                                   </div>
                                           </div>
                                        </div>

                                        <div style="margin-bottom: 10px; margin-top: 10px">
                                                <span>${requestScope.mentee.account.email}</span>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-xl-9 col-lg-9 col-md-12 col-sm-12 col-12">
                    <div class="card h-100">
                        <div class="card-body">
                                <div class="row gutters information" >
                                    <div class="information-detail">
                                        <div>
                                            <h6 class="mb-3 text-primary">Personal Details</h6>
                                        </div>
                                        <div>
                                            <input type="hidden" name="id" value="${requestScope.mentee.account.id}">
                                        </div>
                                        <div style="margin-bottom: 10px">
                                            <div class="form-outline" data-mdb-input-init>
                                                <input type="text"  class="form-control"  id="username" name="username" value="${requestScope.mentee.account.userName}" required  />
                                                <label class="form-label" for="username">User Name</label>
                                            </div>
                                        </div>
                                        <div style="margin-bottom: 10px">
                                            <div class="form-outline" data-mdb-input-init >
                                                <input type="text"  class="form-control"  id="fullName" name="name" value="${requestScope.mentee.account.name}" required  />
                                                <label class="form-label" for="fullName">Full Name</label>
                                            </div>
                                        </div>
                                        <div style="margin-bottom: 10px">
                                            <div class="form-outline" data-mdb-input-init >
                                                <input type="date"  class="form-control"  id="dob" name="dob" value="${requestScope.mentee.account.dob}" />
                                                <label class="form-label" for="dob">Date Of Birth</label>
                                            </div>
                                        </div>
                                        <div style="margin-bottom: 10px">
                                            <div class="form-outline" data-mdb-input-init>
                                                <input type="text"  class="form-control"  id="phone" name="phone" value="${requestScope.mentee.account.phone}" required  />
                                                <label class="form-label" for="phone">Phone</label>
                                            </div>
                                        </div>
                                        <div style="margin-bottom: 10px">
                                            <div class="form-outline" data-mdb-input-init>
                                                <input type="text"  class="form-control"  id="Street" name="address" value="${requestScope.mentee.account.address}" required  />
                                                <label class="form-label" for="Street">Address</label>
                                            </div>
                                        </div>

                                        <div style="margin-bottom: 10px">
                                            <div class="form-group d-flex justify-content-around" >
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="gender" id="male" value="1" <c:if test="${requestScope.mentee.account.gender == true}">checked</c:if>>
                                                    <label class="form-check-label" for="male">Male</label>
                                                </div>
                                                <div class="form-check">
                                                    <input class="form-check-input" type="radio" name="gender" id="female" value="0" <c:if test="${requestScope.mentee.account.gender == false}">checked</c:if>>
                                                    <label class="form-check-label" for="female">Female</label>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row gutters">
                                    <div class="col-xl-12 col-lg-12 col-md-12 col-sm-12 col-12">
                                        <div class="text-right">
                                            <button type="button" name="cancel" class="btn btn-secondary"><a href="profile">Cancel</a></button>
                                            <button type="submit" name="submit" class="btn btn-primary">Update</button>
                                        </div>
                                    </div>
                                </div>
                        </div>
                    </div>

                </div>
            </div>
        </form>
        </div>


        <!-- MDB -->
        <script
                type="text/javascript"
                src="https://cdnjs.cloudflare.com/ajax/libs/mdb-ui-kit/7.2.0/mdb.umd.min.js"
        ></script>
</body>
</html>
