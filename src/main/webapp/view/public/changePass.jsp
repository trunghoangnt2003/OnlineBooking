<%--
    Document   : ChangePassWord
    Created on : Mar 9, 2024, 12:46:38 AM
    Author     : trung
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<link href="//netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
<script src="//netdna.bootstrapcdn.com/bootstrap/3.1.0/js/bootstrap.min.js"></script>
<script src="//code.jquery.com/jquery-1.11.1.min.js"></script>
<!------ Include the above in your HEAD tag ---------->
<div style="background: rgb(176, 237, 215); height: 50vmax">
<div class="container" style="padding-top: 100px">
    <div class="row">
        <div class="col-sm-12 text-center">
            <h1>Change Password</h1>
        </div>
    </div>
    <div class="row">
        <div class="col-sm-6 col-sm-offset-3">
            <p class="text-center">Use the form below to change your password. Your password cannot be the same as your username.</p>
            <form method="post" id="passwordForm" action="change-pass">
                <label for="email">Email</label>
                <input name="email" value="${param.token}" type="hidden">
                <input id="email" type="text" class="input-lg form-control"  value="${requestScope.email}" readonly>
                <br>
                <input type="password" class="input-lg form-control" name="password1" id="password1" placeholder="New Password" autocomplete="off">
                <div class="row">
                    <div class="col-sm-6">
                        <span id="8char" class="glyphicon glyphicon-remove" style="color:#FF0004;"></span> 8 Characters Long<br>
                    </div>
                </div>
                <input type="password" class="input-lg form-control" name="password2" id="password2" placeholder="Repeat Password" autocomplete="off">
                <div class="row">
                    <div class="col-sm-12">
                        <span id="pwmatch" class="glyphicon glyphicon-remove" style="color:#FF0004;"></span> Passwords Match
                    </div>
                </div>
                <input type="submit" class="col-xs-12 btn btn-primary btn-load btn-lg" data-loading-text="Changing Password..." value="Change Password">
            </form>
            <a href="login"> Back to login </a>
        </div><!--/col-sm-6-->
    </div><!--/row-->
</div>
</div>
<script>
$("input[type=password]").keyup(function () {

if ($("#password1").val().length >= 8) {
$("#8char").removeClass("glyphicon-remove");
$("#8char").addClass("glyphicon-ok");
$("#8char").css("color", "#00A41E");
} else {
$("#8char").removeClass("glyphicon-ok");
$("#8char").addClass("glyphicon-remove");
$("#8char").css("color", "#FF0004");
}



if ($("#password1").val() == $("#password2").val()) {
$("#pwmatch").removeClass("glyphicon-remove");
$("#pwmatch").addClass("glyphicon-ok");
$("#pwmatch").css("color", "#00A41E");
} else {
$("#pwmatch").removeClass("glyphicon-ok");
$("#pwmatch").addClass("glyphicon-remove");
$("#pwmatch").css("color", "#FF0004");
}
});
</script>
