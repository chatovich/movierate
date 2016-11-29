<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>

<div class="radius"  style="float: left;margin-left: 100px;" >
    <img src="${pageContext.request.contextPath}${movie.poster}" alt="img" border="0">
    ${movie.title}
    ${movie.duration}
    <c:forEach var="genre" items="${movie.movieGenres}">
        ${genre.genreName}
    </c:forEach>
    <br>
        <c:forEach var="country" items="${movie.movieCountries}">
            ${country.countryName}
        </c:forEach>
        <br>
        <c:forEach var="participant" items="${movie.movieParticipants}">
            ${participant.name} - ${participant.profession}
        </c:forEach>
        <br>
    <c:forEach var="mark" items="${movie.movieMarks}">
        ${mark.mark} - ${mark.user.id}
    </c:forEach>
    <br>
        <c:forEach var="feedback" items="${movie.movieFeedbacks}">
            ${feedback.text}
            ${feedback.creatingDate}
        </c:forEach>

    </div>

<jsp:include page = "${pageContext.request.contextPath}/jsp/header/right_block.jsp"/>

</body>
</html>