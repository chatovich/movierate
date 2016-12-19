<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />


<nav id="admin-nav">
    <ul id="navbar">
        <c:if test="${showAnotherUser}">
            <li><a href="#" type="button" onclick="another_user_info()"><fmt:message key="user.menu.info"/></a></li>
            <li><a href="${pageContext.request.contextPath}/controller?command=get_info_for_user_rating&id=${anotherUser.id}"><fmt:message key="user.menu.activity"/></a></li>
            <c:if test="${signedUser.role.roleName eq 'admin'}">
                <c:if test="${!anotherUser.isBanned}">
                <li><a href="${pageContext.request.contextPath}/controller?command=change_user_status&ban=true&id=${anotherUser.login}"><fmt:message key="user.menu.ban"/></a></li>
                </c:if>
                <c:if test="${anotherUser.isBanned}">
                    <li><a href="${pageContext.request.contextPath}/controller?command=change_user_status&ban=false&id=${anotherUser.login}"><fmt:message key="user.menu.unban"/></a></li>
                </c:if>

            </c:if>
        </c:if>
        <c:if test="${!showAnotherUser}">
            <li><a href="#" type="button" onclick="signed_user_info()"><fmt:message key="user.menu.info"/></a></li>
            <li><a href="${pageContext.request.contextPath}/controller?command=get_info_for_user_rating&id=${signedUser.id}"><fmt:message key="user.menu.activity"/></a></li>
            <li><a href="#"><fmt:message key="nav.cinema"/></a></li>
        </c:if>
    </ul>
</nav>

<script>
    <%@include file="../../js/add_input.js"%>
</script>