<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />


<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>Movie rating</title>
    <link href="${pageContext.request.contextPath}/css/fonts.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/loadphoto.js"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/maincarousel.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/searchblock.css" rel="stylesheet" />
    <%--<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>--%>


</head>
<body class="body">
<nav class="fixed-nav-bar">
    <ul id="navbar">
        <li><a href="#"><fmt:message key="nav.home"/></a></li>
        <li><a href="#"><fmt:message key="nav.genres"/></a>
            <ul>
                <li><a href="#"><fmt:message key="nav.thriller"/></a></li>
                <li><a href="#"><fmt:message key="nav.comedy"/></a></li>
                <li><a href="#"><fmt:message key="nav.drama"/></a></li>
                <li><a href="#"><fmt:message key="nav.action"/></a></li>
                <li><a href="#"><fmt:message key="nav.documental"/></a></li>
                <li><a href="#"><fmt:message key="nav.detective"/></a></li>
                <li><a href="#"><fmt:message key="nav.biography"/></a></li>
            </ul>
        </li>
        <li><a href="#"><fmt:message key="nav.new"/></a>
        <li><a href="#"><fmt:message key="nav.cinema"/></a></li>
        <li  style="float: right;"><a href="#">L</a>
            <ul>
                <li><a href="/controller?command=change_language&language=en_EN"><fmt:message key="nav.language.en"/></a></li>
                <li><a href="/controller?command=change_language&language=ru_RU"><fmt:message key="nav.language.ru"/></a></li>
            </ul>
        </li>
        <li  style="float: right;"><a href="#"><fmt:message key="nav.login"/></a>
            <ul>
                <li><a href="#"><fmt:message key="nav.profile"/></a></li>
                <li><a href="#"><fmt:message key="nav.signout"/></a></li>
            </ul>
        </li>
    </ul>
</nav>
</body>
</html>