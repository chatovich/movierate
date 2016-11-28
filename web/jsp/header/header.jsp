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
    <link href="${pageContext.request.contextPath}/css/movies_list.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/searchblock.css" rel="stylesheet" />
    <%--<script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>--%>


</head>
<body class="body">
<nav class="fixed-nav-bar">
    <ul id="navbar">
        <li><a href="${pageContext.request.contextPath}/jsp/main/main.jsp"><fmt:message key="nav.home"/></a></li>
        <li><a href="#"><fmt:message key="nav.genres"/></a>
            <ul>
                <li><a href="/controller?command=get_movies_by_genre&genre=thriller"><fmt:message key="nav.thriller"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=comedy"><fmt:message key="nav.comedy"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=drama"><fmt:message key="nav.drama"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=action"><fmt:message key="nav.action"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=documental"><fmt:message key="nav.documental"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=detective"><fmt:message key="nav.detective"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=biography"><fmt:message key="nav.biography"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=historic"><fmt:message key="nav.historic"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=fantasy"><fmt:message key="nav.fantasy"/></a></li>
                <li><a href="/controller?command=get_movies_by_genre&genre=criminal"><fmt:message key="nav.criminal"/></a></li>
            </ul>
        </li>
        <li><a href="#"><fmt:message key="nav.new"/></a>
        <li><a href="#"><fmt:message key="nav.cinema"/></a></li>
        <li  style="float: right;"><a href="#"><fmt:message key="nav.language"/> </a>
            <ul>
                <li><a href="/controller?command=change_language&language=en_EN"><fmt:message key="nav.language.en"/></a></li>
                <li><a href="/controller?command=change_language&language=ru_RU"><fmt:message key="nav.language.ru"/></a></li>
            </ul>
        </li>
        <li style="float: right;">
            <a href="${pageContext.request.contextPath}/jsp/login/login.jsp"><fmt:message key="nav.login"/></a>
        </li>
        <%--<li  style="float: right;"><a href="#"><fmt:message key="nav.login"/></a>--%>
            <%--<ul>--%>
                <%--<li><a href="#"><fmt:message key="nav.profile"/></a></li>--%>
                <%--<li><a href="#"><fmt:message key="nav.signout"/></a></li>--%>
            <%--</ul>--%>
        <%--</li>--%>
    </ul>
</nav>
</body>
</html>