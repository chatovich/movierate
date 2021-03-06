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

<div id="radius" class="col-lg-8 col-sm-8" style="float: left;margin-left: 90px">
    <div style="margin: 10px">
    <img id="poster" src="${pageContext.request.contextPath}${movie.poster}" alt="No poster available" width="350px" height="500px" border="0" >
    <p id="title" style="color: #ff8900"><strong>${movie.title}</strong></p>
    <p><b><fmt:message key="movie.page.year"/>: </b>
        <a href="${pageContext.request.contextPath}/controller?command=filtered_movie_search&year=${movie.year}">${movie.year}</a></p>
    <p><b><fmt:message key="movie.page.duration"/>:</b> ${movie.duration} <fmt:message key="movie.page.minutes"/> </p>
    <p><b><fmt:message key="movie.page.genre"/>:</b>
        <c:forEach var="genre" items="${movie.movieGenres}">
        <a href="${pageContext.request.contextPath}/controller?command=filtered_movie_search&genre=${genre.genreName}">${genre.genreName}</a>
        </c:forEach>
    </p>
    <p><b><fmt:message key="movie.page.country"/>:</b>
        <c:forEach var="country" items="${movie.movieCountries}">
        <a href="${pageContext.request.contextPath}/controller?command=filtered_movie_search&country=${country.countryName}">${country.countryName}</a>
        </c:forEach>
    </p>
        <p><b><fmt:message key="movie.page.director"/>:</b>
            <c:forEach var="participant" items="${movie.movieParticipants}">
            <c:if test="${participant.profession eq 'DIRECTOR'}">
            <a href="${pageContext.request.contextPath}/controller?command=filtered_movie_search&participant=${participant.name}">${participant.name}</a>
            </c:if>
        </c:forEach>
        </p>
    <p><b><fmt:message key="movie.page.actors"/>:</b>
        <c:forEach var="participant" items="${movie.movieParticipants}">
        <c:if test="${participant.profession eq 'ACTOR'}">
        <a href="${pageContext.request.contextPath}/controller?command=filtered_movie_search&participant=${participant.name}">${participant.name}</a>
        </c:if>
        </c:forEach>
    </p>
        <p><b><fmt:message key="movie.page.rating"/>:</b> ${movie.rating}</p>

    <div style="clear:both;">
        <p>${movie.plot}</p>
        <c:if test="${feedbackAdded}">
            <p style="color: #ff8900;"><fmt:message key="movie.feedback.added"/> </p>
        </c:if>
        <c:if test="${userSignedIn}">
            <c:if test="${signedUser.role.roleName eq 'user'}">
                <c:if test="${!signedUser.isBanned}">

        <form action="${pageContext.request.contextPath}/controller" method="post" name="add_feedback">
            <input type="hidden" name="command" value="add_feedback">
            <input type="hidden" name="id_movie" value=${movie.id}>

            <p style="margin-top: 20px"><textarea id="feedback-textarea" name="feedback" rows="5" cols="50" placeholder=<fmt:message key="movie.write.comment"/>></textarea></p>
            <div class="star-rating">
                <div class="star-rating__wrap">
                    <input class="star-rating__input" id="star-rating-10" type="radio" name="mark" value="10">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-10" title="10 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-9" type="radio" name="mark" value="9">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-9" title="9 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-8" type="radio" name="mark" value="8">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-8" title="8 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-7" type="radio" name="mark" value="7">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-7" title="7 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-6" type="radio" name="mark" value="6">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-6" title="6 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-5" type="radio" name="mark" value="5">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-5" title="5 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-4" type="radio" name="mark" value="4">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-4" title="4 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-3" type="radio" name="mark" value="3">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-3" title="3 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-2" type="radio" name="mark" value="2">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-2" title="2 out of 10 stars"></label>
                    <input class="star-rating__input" id="star-rating-1" type="radio" name="mark" value="1">
                    <label class="star-rating__ico fa fa-star-o fa-lg" for="star-rating-1" title="1 out of 10 stars"></label>

                </div>
            </div>
            <p style="margin-top: 20px"><input type="submit" value=<fmt:message key="movie.send.comment"/> style="color:black">
                <label id="users-only"><fmt:message key="movie.comment.onlyusers"/> </label>
            </p>
        </form>
            </c:if>
            </c:if>
        </c:if>
        <c:if test="${signedUser.isBanned}">
            <hr>
            <p class="ban-message" style="color: red"><b>
                <fmt:message key="movie.page.user.banned"/></b>
            </p>
        </c:if>
        <c:forEach var="feedback" items="${movie.movieFeedbacks}">
            <c:if test="${feedback.status eq 'PUBLISHED'}">
            <hr>
            <img id="user-icon" src="${pageContext.request.contextPath}${feedback.user.photo}" alt="img" border="0" height="90px" width="90px">
            <span style="color: #ff8900"><b><a href="${pageContext.request.contextPath}/controller?command=get_another_user_page&login=${feedback.user.login}">${feedback.user.login}</a></b></span>
            <br>
            <span>${feedback.creatingDate}</span>
            <br>
                <p style="font-size: medium;">
                    <c:forEach begin="0" end="9" varStatus="loop">
                                                <span style="color: ${loop.index < feedback.mark ? "gold" : "lightgrey"}">
                                                    <c:out value="★"/>
                                                </span>
                    </c:forEach>
                </p>
            <p style="clear: both">${feedback.text}</p>
            <c:if test="${userSignedIn}">
            <c:if test="${signedUser.role.roleName eq 'user'}">
                <c:if test="${!signedUser.isBanned}">
                <button id="add_like" onclick="addLike(${signedUser.id}, ${feedback.id}, ${feedback.likes})"><img src="../../img/icon/like.jpg"> </button>
                <b><span class="like" id="like_count${feedback.id}">${feedback.likes}</span></b>
                </c:if>
                </c:if>
            </c:if>
            </c:if>
        </c:forEach>
    </div>

    </div>
</div>

<c:import url="../fragment/right_block.jsp"/>

</body>
</html>