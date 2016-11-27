<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.locale" />

<div class = "container">
    <div class="wrapper">
        <form action="" method="post" name="Login_Form" class="form-signin">
            <h3 class="form-signin-heading"><fmt:message key="login.please.signin"/></h3>
            <hr class="colorgraph"><br>

            <input type="text" class="form-control" name="Username" placeholder=<fmt:message key="login.username"/> required="" autofocus="" />
            <input type="password" class="form-control" name="Password" style="margin-bottom: 10px" placeholder=<fmt:message key="login.password"/> required=""/>

            <div class="col-xs-6 col-sm-6 col-md-6">
                <input type="submit" class="btn btn-lg btn-success btn-block" value=<fmt:message key="nav.login"/>>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-6">
                <a href="${pageContext.request.contextPath}/jsp/login/reg.jsp" class="btn btn-lg btn-primary btn-block"><fmt:message key="login.register"/> </a>
            </div>

            <%--<button class="btn btn-lg btn-primary btn-block"  name="Submit" value="Login" type="Submit">Login</button>--%>
        </form>
    </div>
</div>

</body>
</html>