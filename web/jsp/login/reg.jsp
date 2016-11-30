<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>--%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.locale" />

<!DOCTYPE html>
<html>
<c:import url="../fragment/header.jsp"/>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>--%>

<body class="body">

<c:import url="../fragment/top_menu.jsp"/>


<div class = "container">
    <div class="wrapper">
        <form onsubmit="return validateForm()" action="/controller" method="post" enctype="multipart/form-data" name="register_form" class="form-signin">
            <h3 class="form-signin-heading"><fmt:message key="login.please.register"/></h3>
            <hr class="colorgraph"><br>

            <c:if test="${registrFailed}">
                <%--wrong field format (${usernameWrong}, ${passwordWrong}, ${emailWrong})--%>
                <p align="center" style="color: red" ><fmt:message key="reg.wronginput"/> </p>
                <p align="center" style="color: red" ><fmt:message key="reg.tryagain"/> </p>
            </c:if>
            <c:if test="${passwordsNoMatch}">
                <p align="center" style="color: red" ><fmt:message key="reg.passwords.nomatch"/> </p>
            </c:if>
            <c:if test="${loginExists}">
                <p align="center" style="color: red" ><fmt:message key="reg.loginExists"/> </p>
            </c:if>

            <input type="hidden" name="command" value="registration">

            <input type="text" class="form-control" name="username" autofocus="" placeholder=<fmt:message key="login.username"/> title=<fmt:message key="login.pattern"/>/>
            <span style="color: red" id="error_login"></span>

            <input type="password" class="form-control" name="password" placeholder=<fmt:message key="login.password"/> title=<fmt:message key="login.pattern"/>  />
            <span style="color: red" id="error_password1"></span>


            <input type="password" class="form-control" name="confirm_password" placeholder=<fmt:message key="login.confirm.password"/>   />
            <span style="color: red" id="error_password2"></span>


            <input type="text" class="form-control" name="email" placeholder=<fmt:message key="login.email"/> title=<fmt:message key="reg.wrong.email"/>  />
            <span style="color: red" id="error_email"></span>

            <%--<input type="file" name="photo" class="form-control" />--%>
            <div class="mask-wrapper">
                <div class="mask">
                    <input style="float: left" class="fileInputText" type="text" disabled>
                    <button class="send-file"><fmt:message key="login.photoload"/> </button>
                </div>
                <input id="photo" class="custom-file-input" type="file" name="photo">
            </div>


            <div class="reg">
                <input type="submit" class="btn btn-lg btn-success btn-block" value=<fmt:message key="login.register"/>/>
            </div>

            <%--<button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Login" type="Submit">Login</button>--%>
        </form>
    </div>
</div>

<script>
    function validateForm() {
        var FILL_FIELD = <fmt:message key="reg.fill.field"/>
                PASSWORDS_NOMATCH = <fmt:message key="reg.passwords.nomatch"/>
                        PATTERN_LOGIN = <fmt:message key="login.pattern"/>
                                PATTERN_EMAIL = <fmt:message key="reg.wrong.email"/>
        var result = true;
        //get variables from the form
        login = document.forms[0]["username"].value;
        password1 = document.forms[0]["password"].value;
        password2 = document.forms[0]["confirm_password"].value;
        email = document.forms[0]["email"].value;
        pattern_login = new RegExp("[A-z0-9_]{5,}");
        pattern_email = new RegExp("[0-9a-z_]+@[0-9a-z_]+\.[a-z]{2,5}");
//        var pattern = new RegExp("\w+");
        //specify error variables
        var err_login = document.getElementById("error_login");
        err_pass1 = document.getElementById("error_password1");
        err_pass2 = document.getElementById("error_password2");
        err_email = document.getElementById("error_email");
        //clear error messages
        err_login.innerHTML = "";
        err_pass1.innerHTML = "";
        err_pass2.innerHTML = "";
        err_email.innerHTML = "";

//        if (!login){
//            err_login.innerHTML = "not valid";
//            result = false;
//        }
        if (!pattern_login.test(login)){
            result = false;
            err_login.innerHTML = PATTERN_LOGIN;
        }
        if (!pattern_login.test(password1)){
            result = false;
            err_pass1.innerHTML = PATTERN_LOGIN;
        }
        if (!pattern_email.test(email)){
            result = false;
            err_email.innerHTML = PATTERN_EMAIL;
        }
        if(password1!==password2){
            err_pass1.innerHTML = PASSWORDS_NOMATCH;
            err_pass2.innerHTML = PASSWORDS_NOMATCH;
            document.forms[0]["password"].value = "";
            document.forms[0]["confirm_password"].value = "";
            result = false;
        }

        return result;
    }
</script>
</body>
</html>