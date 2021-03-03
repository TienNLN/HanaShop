<%-- 
    Document   : loginFail
    Created on : 06-Jan-2021, 22:16:54
    Author     : ADMIN
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <meta name="google-signin-client_id" content="154810970511-re6vslhed4s3dm115n8m1mcfu7g79ga1.apps.googleusercontent.com">
        
        <title>Hana Shop</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
    </head>
    <body> 
        <h1 style="margin: 20px">Login Page</h1>
        <form action="DispatchServlet" method="POST">
            <div class="form-group col-md-3" style="margin-left: 20px">
                <label for="txtUsername">Username</label>
                <input type="text" 
                       <c:if test="${not empty requestScope.USERNAME_EMPTY_ERR}">
                           class="form-control is-invalid"
                       </c:if>
                       <c:if test="${empty requestScope.USERNAME_EMPTY_ERR}">
                           class="form-control"
                       </c:if>
                       name="txtUsername" placeholder="Enter Username">
                <div class="invalid-feedback">
                    ${requestScope.USERNAME_EMPTY_ERR}
                </div>
            </div><br/>
            <div class="form-group col-md-3" style="margin-left: 20px">
                <label for="txtPassword">Password</label>
                <input type="password" 
                       <c:if test="${not empty requestScope.PASSWORD_EMPTY_ERR}">
                           class="form-control is-invalid"
                       </c:if>
                       <c:if test="${empty requestScope.PASSWORD_EMPTY_ERR}">
                           class="form-control"
                       </c:if>
                       name="txtPassword" placeholder="Enter Password">
                <div class="invalid-feedback">
                    ${requestScope.PASSWORD_EMPTY_ERR}
                </div>
            </div>
            <br/>
            <button type="submit" class="btn btn-outline-primary" value="Login" name="btnAction" style="margin-left: 20px">Login</button>
            <button type="reset" class="btn btn-outline-primary" style="margin-left: 20px">Reset</button><br/>

            <c:if test="${not empty requestScope.WRONG_INFO_ERR}">
                <div class="alert alert-warning col-md-3" style="margin: 20px" role="alert">
                    ${requestScope.WRONG_INFO_ERR}
                </div>
            </c:if>

            <hr class="col-md-3" style="margin-left: 20px" align="left">
            <div class="g-signin2" style="margin-left: 20px" data-width="240" data-height="50" data-longtitle="true" data-onsuccess="onSignIn" data-theme="dark"></div>
            <!--            <a class="btn  btn-outline-primary m-3" href="#" role="button"><img src="https://i.pinimg.com/originals/74/65/f3/7465f30319191e2729668875e7a557f2.png" width="40" height="40">  Login With Google</a><br>-->

            <hr class="col-md-3" style="margin-left: 20px" align="left">
        </form>

        <script>
            function onSignIn(googleUser) {
                // var profile = googleUser.getBasicProfile();
                var token_id = googleUser.getAuthResponse().id_token;
                var xhr = new XMLHttpRequest();
                xhr.open('POST', 'http://localhost:8084/HanaShop/DispatchServlet');
                xhr.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
                xhr.onload = function () {
                    signOut();
                    window.location.href = xhr.responseText;
                };
                xhr.send('btnAction=googleLogin&token_id=' + token_id);
            }
            function signOut() {
                var auth2 = gapi.auth2.getAuthInstance();
                auth2.signOut().then(function () {
                    // call logout from server
                });
            }

            function init() {
                gapi.load('auth2', function () {
                    gapi.auth2.init().then(onInit, onError);
                });
            }

            function onInit() {
                $(".g-signin2").show();
            }

            function onError(response) {
                if (response.error === 'idpiframe_initialization_failed') {
                    $(".g-signin2").hide();
//        showErrorMessage("Google Login is not supported in private browser.");
                }
            }
        </script>

        <script src="https://apis.google.com/js/platform.js?onload=init" async defer></script>

        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
    </body>
</html>
