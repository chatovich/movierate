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
        <li><a  href="${pageContext.request.contextPath}/controller?command=load_main_page"><fmt:message key="nav.home"/></a></li>
        <li><a class="top-href" href="#"><fmt:message key="nav.genres"/></a>
            <ul id="topGenres">
            </ul>
        </li>
        <li><a class="top-href" href="${pageContext.request.contextPath}/controller?command=filtered_movie_search"><fmt:message key="nav.new"/></a>
        <li  style="float: right;"><a href="#"><fmt:message key="nav.language"/> </a>
            <ul>
                <li><a style="padding: 5px 5px" href="${pageContext.request.contextPath}/controller?command=change_language&language=en_EN"><fmt:message key="nav.language.en"/></a></li>
                <li><a style="padding: 5px 5px" href="${pageContext.request.contextPath}/controller?command=change_language&language=ru_RU"><fmt:message key="nav.language.ru"/></a></li>
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
                        <a style="padding: 5px 5px" href="${pageContext.request.contextPath}/jsp/main/user.jsp"><fmt:message key="nav.profile"/></a>
                    </li>
                    <li>
                        <a style="padding: 5px 5px" href="${pageContext.request.contextPath}/controller?command=logout"><fmt:message key="nav.signout"/></a>
                    </li>
                </ul>
            </c:if>
        </li>

    </ul>
</nav>
<c:if test="${language eq 'en_EN'}">
    <script type="text/javascript">
        <%@include file="../../js/select_genres_en.js"%>
    </script>
</c:if>
<c:if test="${language eq 'ru_RU'}">
    <script type="text/javascript">
        <%@include file="../../js/select_genres_ru.js"%>
    </script>
</c:if>

<script type="text/javascript">
    <%@include file="../../js/select_countries.js"%>
</script>



