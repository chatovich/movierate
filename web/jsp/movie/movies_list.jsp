<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />

<!DOCTYPE html>
<html>
<c:import url="../fragment/header.jsp"/>

<body class="body">

<c:import url="../fragment/top_menu.jsp"/>

<div id="radius" class="col-lg-8 col-sm-8" style="float: left;margin-left: 100px;" >

    <div style="display:inline-block;text-align: justify">
    <c:forEach var="movie" items="${movies}">
        <div style="float: left; margin-left: 50px; margin-top: 10px;">
            <a href="${pageContext.request.contextPath}/controller?command=get_movie_page&id_movie=${movie.id}"><img src="${pageContext.request.contextPath}${movie.poster}" alt="img" border="0" height="314" width="220"></a>
        </div>
    </c:forEach>
    </div>

    <div align="center" style="clear: both;padding-top: 20px">
        <c:if test="${emptyMoviesList}">
                <span class="admin-success"><fmt:message key="movie.empty.list"/></span>
        </c:if>
        <c:if test="${currentPage!=1}">
            <a href="${pageContext.request.contextPath}/controller?page=${currentPage-1}&command=filtered_movie_search&genre=${genre}&participant=${participant}&country=${country}&year=${year}"><fmt:message key="movie.previous"/> </a>
        </c:if>
        <c:if test="${pageQuantity>1}">
        ${currentPage}
        </c:if>
        <c:if test="${currentPage lt pageQuantity}">
            <a href="${pageContext.request.contextPath}/controller?page=${currentPage+1}&command=filtered_movie_search&genre=${genre}&participant=${participant}&country=${country}&year=${year}"><fmt:message key="movie.next"/></a>
        </c:if>
    </div>
    
</div>

<c:import url="../fragment/right_block.jsp"/>

</body>
</html>