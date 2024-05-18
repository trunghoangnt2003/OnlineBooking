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
    <title>Update My Profile</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/update.css">
</head>
<body>
        <div>This is page update profile</div>
        <form action="update" method="post">

            <div><input type="text" name="id" hidden="" value=" ${requestScope.mentee.account.id}"><br/></div>

           <div> Name: <input type="text" name="name" value="${requestScope.mentee.account.name}" /> <br/> </div>

            <div>Gender: <input type="radio" name="gender" value="1" <c:if test="${requestScope.mentee.account.gender ==1}">checked</c:if> mail </div>
            <div> <input type="radio" name="gender" value="0" <c:if test="${requestScope.mentee.account.gender ==0}">checked</c:if> femail <br/> </div>
            <div> Email: <input type="text" name="email" value="${requestScope.mentee.account.email}" /> <br/> </div>
           <div>Phone:  <input type="number" name="phone" value="${requestScope.mentee.account.phone}" /> <br/> </div>
           <div>Address:  <input type="text" name="address" value="${requestScope.mentee.account.address}" /> <br/> </div>
            <div>
                <button type="submit" > Save </button></div>
        </form>
</body>
</html>
