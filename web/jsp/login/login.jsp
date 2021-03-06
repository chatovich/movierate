<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/fragment/header.jsp"/>--%>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.locale" />

<!DOCTYPE html>
<html>
<c:import url="../fragment/header.jsp"/>

<body class="body">

<c:import url="../fragment/top_menu.jsp"/>

<div class = "container">
    <div class="wrapper">

        <form action="${pageContext.request.contextPath}/controller" method="post" name="login" class="form-signin">
            <h3 class="form-signin-heading"><fmt:message key="login.please.signin"/></h3>
            <hr class="colorgraph"><br>

            <c:if test="${emptyFields}">
                <p align="center" style="color: red"><fmt:message key="admin.movie.empty.fields"/></p>
            </c:if>
            <c:if test="${loginFailed}">
            <p align="center" style="color: red"><fmt:message key="login.login.failed"/></p>
            </c:if>

            <input type="hidden" name="command" value="login">
            <input type="text" class="form-control" name="login" placeholder=<fmt:message key="login.username"/> required autofocus="" >
            <input type="password" class="form-control" name="password" style="margin-bottom: 10px" placeholder=<fmt:message key="login.password"/> required>

            <div class="col-xs-6 col-sm-6 col-md-6">
                <input type="submit" class="btn btn-lg btn-success btn-block" value=<fmt:message key="nav.login"/>>
            </div>
            <div class="col-xs-6 col-sm-6 col-md-6">
                <a href="${pageContext.request.contextPath}/jsp/login/reg.jsp" class="btn btn-lg btn-primary btn-block"><fmt:message key="login.register"/> </a>
            </div>
        </form>
    </div>
</div>


</body>
</html>