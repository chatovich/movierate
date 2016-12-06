<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>--%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />

<!DOCTYPE html>
<html>
<c:import url="../fragment/header.jsp"/>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>--%>

<body class="body">

<c:import url="../fragment/top_menu.jsp"/>

<div id="radius" class="col-lg-8 col-sm-8" style="float: left;margin-left: 90px">
    <div style="margin: 10px">
    <img id="poster" src="${pageContext.request.contextPath}${movie.poster}" alt="No poster available" border="0" >
    <p id="title" style="color: #ff8900"><strong>${movie.title}</strong></p>
    <p><b><fmt:message key="movie.page.year"/>: </b>
        <a href="/controller?command=get_movie_page&id=${movie.year}">${movie.year}</a></p>
    <p><b><fmt:message key="movie.page.duration"/>:</b> ${movie.duration} <fmt:message key="movie.page.minutes"/> </p>
    <p><b><fmt:message key="movie.page.genre"/>:</b>
        <c:forEach var="genre" items="${movie.movieGenres}">
        <a href="/controller?command=get_movie_page&id=${genre.genreName}">${genre.genreName}</a>
        </c:forEach>
    </p>
    <p><b><fmt:message key="movie.page.country"/>:</b>
        <c:forEach var="country" items="${movie.movieCountries}">
        <a href="/controller?command=get_movie_page&id=${genre.countryName}">${country.countryName}</a>
        </c:forEach>
    </p>
        <p><b><fmt:message key="movie.page.director"/>:</b>
            <c:forEach var="participant" items="${movie.movieParticipants}">
            <c:if test="${participant.profession eq 'DIRECTOR'}">
            <a href="/controller?command=get_movie_page&id=${participant.name}">${participant.name}</a>
            </c:if>
        </c:forEach>
        </p>
    <p><b><fmt:message key="movie.page.actors"/>:</b>
        <c:forEach var="participant" items="${movie.movieParticipants}">
        <c:if test="${participant.profession eq 'ACTOR'}">
        <a href="/controller?command=get_movie_page&id=${participant.name}">${participant.name}</a>
        </c:if>
        </c:forEach>
    </p>
        <p><b><fmt:message key="movie.page.rating"/>:</b> ${movie.rating}</p>


    <%--<c:forEach var="mark" items="${movie.movieMarks}">--%>
        <%--${mark.mark} - ${mark.user.id}--%>
    <%--</c:forEach>--%>
    <%--<br>--%>
    <div style="clear:both;">

        <form action="/controller" method="post" name="add_feedback">
            <input type="hidden" name="command" value="add_feedback">
            <input type="hidden" name="id_movie" value=${movie.id}>
            <p><textarea id="feedback-textarea" name="feedback" rows="5" cols="50" placeholder=<fmt:message key="movie.write.comment"/>></textarea></p>
            <p><input type="submit" value=<fmt:message key="movie.send.comment"/> style="color:black">
                <label><fmt:message key="movie.comment.onlyusers"/> </label>

            </p>
        </form>
        <c:forEach var="feedback" items="${movie.movieFeedbacks}">
            <c:if test="${feedback.status eq 'PUBLISHED'}">
            <hr>
            <img id="user-icon" src="${pageContext.request.contextPath}${feedback.user.photo}" alt="img" border="0" height="90px" width="90px">
            <span style="color: #ff8900"><b>${feedback.user.login}</b></span>
            <br>
            <span>${feedback.creatingDate}</span>
            <br>
            <p style="clear: both">${feedback.text}</p>
            </c:if>
        </c:forEach>
    </div>

    </div>
</div>


<c:import url="../fragment/right_block.jsp"/>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/header/right_block.jsp"/>--%>

</body>
</html>