<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />


<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>Movie rating</title>
    <link href="${pageContext.request.contextPath}/css/fonts.css" rel="stylesheet" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome-4.6.3/css/font-awesome.min.css">
    <script src="${pageContext.request.contextPath}/js/jquery-3.1.1.js"></script>
    <script src="${pageContext.request.contextPath}/js/loadphoto.js"></script>
    <script src="${pageContext.request.contextPath}/js/admin.js"></script>
    <script src="${pageContext.request.contextPath}/js/add_input.js"></script>
    <script src="${pageContext.request.contextPath}/js/validation.js"></script>
    <script src="${pageContext.request.contextPath}/js/likes.js"></script>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/header.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/star_rating.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/maincarousel.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/login.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/movies_list.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/searchblock.css" rel="stylesheet" />
    <link href="${pageContext.request.contextPath}/css/admin.css" rel="stylesheet" />
    <script src="${pageContext.request.contextPath}/js/bootstrap.js"></script>


</head>
