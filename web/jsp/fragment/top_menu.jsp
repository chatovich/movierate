<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yul" uri="/WEB-INF/tld/custom.tld" %>
<%@ page session="true" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />


<nav class="fixed-nav-bar">
    <ul id="navbar">
        <li><a href="${pageContext.request.contextPath}/controller?command=load_main_page"><fmt:message key="nav.home"/></a></li>
        <li><a href="#"><fmt:message key="nav.genres"/></a>
            <ul>
                <%--pagecontext works at both tomcat configuration options (/ and /movierate), without - only with/--%>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=thriller"><fmt:message key="nav.thriller"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=comedy"><fmt:message key="nav.comedy"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=drama"><fmt:message key="nav.drama"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=action"><fmt:message key="nav.action"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=documental"><fmt:message key="nav.documental"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=detective"><fmt:message key="nav.detective"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=biography"><fmt:message key="nav.biography"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=historic"><fmt:message key="nav.historic"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=fantasy"><fmt:message key="nav.fantasy"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=get_movies_by_genre&genre=criminal"><fmt:message key="nav.criminal"/></a></li>
            </ul>
        </li>
        <li><a href="#"><fmt:message key="nav.new"/></a>
        <li><a href="#"><fmt:message key="nav.cinema"/></a></li>
        <li  style="float: right;"><a href="#"><fmt:message key="nav.language"/> </a>
            <ul>
                <li><a href="${pageContext.request.contextPath}/controller?command=change_language&language=en_EN"><fmt:message key="nav.language.en"/></a></li>
                <li><a href="${pageContext.request.contextPath}/controller?command=change_language&language=ru_RU"><fmt:message key="nav.language.ru"/></a></li>
            </ul>
        </li>
        <li style="float: right;">
            <c:if test="${!userSignedIn}">
                <a href="${pageContext.request.contextPath}/jsp/login/login.jsp"><fmt:message key="nav.login"/></a>
            </c:if>
            <c:if test="${userSignedIn}">
                <a href="#">${signedUser.login}</a>
                <ul>
                    <li>
                        <a href="${pageContext.request.contextPath}/jsp/main/user.jsp"><fmt:message key="nav.profile"/></a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="nav.signout"/></a>
                    </li>
                </ul>
            </c:if>
        </li>

    </ul>
</nav>