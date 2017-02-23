<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page session="true" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />


<nav id="admin-nav">
    <ul id="navbar">
        <li><a href="#"><fmt:message key="admin.menu.movies"/></a>
            <ul>
                <%--pagecontext works at both tomcat configuration options (/ and /movierate), without - only with/--%>
                <li><a style="padding: 5px 5px" href="${pageContext.request.contextPath}/controller?command=get_info_for_movie_adding"><fmt:message key="admin.add.movie"/></a></li>
                <li><a style="padding: 5px 5px" href="${pageContext.request.contextPath}/controller?command=get_all_movies"><fmt:message key="admin.update.movie"/></a></li>
                <li><a style="padding: 5px 5px" href="#" type="button" onclick="add_new_participant()"><fmt:message key="admin.add.participant"/></a></li>
                    <li><a style="padding: 5px 5px" href="${pageContext.request.contextPath}/controller?command=get_all_participants"><fmt:message key="admin.update.participant"/></a></li>
            </ul>
        </li>
        <li><a href="${pageContext.request.contextPath}/controller?command=get_new_feedbacks"><fmt:message key="admin.new.comments"/></a>
        </li>
        <li><a href="${pageContext.request.contextPath}/controller?command=get_all_users"><fmt:message key="admin.menu.users"/></a>
        </li>
        <li><a href="#" type="button" onclick="signed_user_info()"><fmt:message key="user.menu.info"/></a></li>

    </ul>
</nav>

<script>
    <%@include file="../../js/add_input.js"%>
</script>