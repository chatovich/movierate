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

<div class="body">
<c:import url="../fragment/top_menu.jsp"/>

<div id="radius" class="col-lg-8 col-sm-8" style="float: left;margin-left: 90px">
    <c:import url="../fragment/admin_menu.jsp"/><br>
<div id="for-insert" align="center">
    <c:if test="${genreAdded}">
        <span class="admin-success"><fmt:message key="admin.genre.added"/> </span>
    </c:if>
    <c:if test="${movieAdded}">
        <span class="admin-success"><fmt:message key="admin.movie.added"/> </span>
    </c:if>
    <c:if test="${movieUpdated}">
        <span class="admin-success"><fmt:message key="admin.movie.updated"/> </span>
    </c:if>
    <c:if test="${participantUpdated}">
        <span class="admin-success"><fmt:message key="admin.participant.updated"/> </span>
    </c:if>
    <c:if test="${movieExists}">
        <span class="validation-msg"><fmt:message key="admin.movie.exists"/> </span>
    </c:if>
    <c:if test="${emptyField}">
        <span class="validation-msg"><fmt:message key="admin.movie.empty.fields"/> </span>
    </c:if>
    <c:if test="${participantExists}">
        <span class="validation-msg"><fmt:message key="admin.participant.exists"/> </span>
    </c:if>
    <c:if test="${participantAdded}">
        <span class="admin-success"><fmt:message key="admin.participant.added"/> </span>
    </c:if>

    <c:if test="${chooseMovie}">
        <form action="/controller" role="form" method="post" name="choose-film">
        <input type="hidden" name="command" value="get_movie_info_for_update">
        <div class="form-group">
            <select name="id_movie" class="form-control-static" style="width: 350px; color: black;" required>
            <option value="" disabled selected><fmt:message key="admin.update.movie.choosemovie"/> </option>
            <c:forEach var="movie" items="${movies}">
                <option value="${movie.id}">${movie.title}</option>
            </c:forEach>
        </select>
            </div>
            <div>
            <button class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button>
        </div>
        </form>
    </c:if>

    <c:if test="${chooseParticipant}">
        <form action="/controller" role="form" method="post" name="choose-participant">
            <input type="hidden" name="command" value="get_participant_for_update">
            <div class="form-group">
                <select name="id_participant" class="form-control-static" style="width: 350px; color: black;" required>
                    <option value="" disabled selected><fmt:message key="admin.update.chooseparticipant"/> </option>
                    <c:forEach var="participant" items="${participants}">
                        <option value="${participant.id}">${participant.name} - ${participant.profession}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <button class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button>
            </div>
        </form>
    </c:if>
</div>
</div>
</div>


<c:import url="../fragment/right_block.jsp"/>
<script>
    <%@include file="../../js/add_input.js"%>
    <%--<c:import url="../../js/add_input.js"/>--%>
</script>
</body>
</html>