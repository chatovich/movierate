<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />


<nav id="admin-nav">
    <ul id="navbar">
        <li><a href="#" type="button" onclick="user_info()"><fmt:message key="user.menu.info"/></a>

        </li>
        <li><a href="${pageContext.request.contextPath}/controller?command=get_info_for_user_rating"><fmt:message key="user.menu.activity"/></a>

        </li>
        <li><a href="#"><fmt:message key="nav.cinema"/></a>

        </li>
    </ul>
</nav>

<script>
    <%@include file="../../js/add_input.js"%>
</script>