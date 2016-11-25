<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.locale" />

<div class = "container">
    <div class="wrapper">
        <form action="" method="post" name="Login_Form" class="form-signin">
            <h3 class="form-signin-heading"><fmt:message key="login.please.register"/></h3>
            <hr class="colorgraph"><br>

            <input type="text" class="form-control" name="Username" placeholder=<fmt:message key="login.username"/> required="" autofocus="" />
            <input type="password" class="form-control" name="Password" placeholder=<fmt:message key="login.password"/> required=""/>
            <input type="text" class="form-control" name="ConfirmPasword" placeholder=<fmt:message key="login.confirm.password"/> required=""  />
            <input type="password" class="form-control" name="Email" placeholder=<fmt:message key="login.email"/> required=""/>
            <%--<input type="file" name="photo" class="form-control" />--%>
            <div class="mask-wrapper">
                <div class="mask">
                    <input style="float: left" class="fileInputText" type="text" disabled>
                    <button class="send-file"><fmt:message key="login.photoload"/> </button>
                </div>
                <input id="my_file" class="custom-file-input" type="file" name="my_file">
            </div>



            <div class="reg">
                <a href="" class="btn btn-lg btn-primary btn-block" ><fmt:message key="login.register"/> </a>
            </div>

            <%--<button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Login" type="Submit">Login</button>--%>
        </form>
    </div>
</div>

</body>
</html>