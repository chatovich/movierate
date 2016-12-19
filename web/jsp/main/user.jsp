<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="yul" uri="customtags" %>
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

<div class="col-lg-8 col-sm-8" style="margin-top: 80px;margin-left: 70px">
    <div class="card hovercard">
        <div class="card-background">
            <img class="card-bkimg" alt="" src="http://lorempixel.com/100/100/people/9/">
            <!-- http://lorempixel.com/850/280/people/9/ -->
        </div>
        <c:if test="${showAnotherUser}">
            <div class="useravatar"><img alt="no photo" src="${pageContext.request.contextPath}${anotherUser.photo}"></div>
            <div class="card-info"> <span class="card-title">${anotherUser.login}</span></div>
        </c:if>
        <c:if test="${!showAnotherUser}">
            <div class="useravatar"><img alt="no photo" src="${pageContext.request.contextPath}${signedUser.photo}"></div>
            <div class="card-info"> <span class="card-title">${signedUser.login}</span></div>
        </c:if>

    </div>

    <yul:menu role="${signedUser.role}"/>
    <c:if test="${showAnotherUser}">
        <jsp:include page="${pageContext.request.contextPath}/jsp/fragment/user_menu.jsp"/>
    </c:if>
    <c:if test="${!showAnotherUser}">
    <jsp:include page="${pageContext.request.contextPath}/${includeMenu}"/>
    </c:if>
    <%--<c:import url="../fragment/admin_menu.jsp"/>--%>

    <%--<div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group" aria-label="...">--%>
        <%--<div class="btn-group" role="group">--%>
            <%--<button type="button" class="btn btn-primary" href="#add_movie" data-toggle="tab">--%>
                <%--<div class="hidden-xs"><fmt:message key="admin.add.movie"/> </div>--%>
            <%--</button>--%>
        <%--</div>--%>
        <%--<div class="btn-group" role="group">--%>
            <%--<button type="button" class="btn btn-default" href="#tab2" data-toggle="tab">--%>
                <%--<div class="hidden-xs"><fmt:message key="admin.update.movie"/> </div>--%>
            <%--</button>--%>
        <%--</div>--%>
        <%--<div class="btn-group" role="group">--%>
            <%--<button type="button" class="btn btn-default" href="#tab3" data-toggle="tab">--%>
                <%--<div class="hidden-xs"><fmt:message key="admin.new.comments"/> </div>--%>
            <%--</button>--%>
        <%--</div>--%>
    <%--</div>--%>

    <div class="well">
        <div class="tab-content">
            <div class="tab-pane fade in active">

                <div id="for-insert">
                    <c:if test="${emptyFields}">
                        <fmt:message key="admin.movie.empty.fields"/>
                    </c:if>
                    <c:if test="${userUpdated}">
                    <span class="admin-success"><fmt:message key="user.updated"/></span>
                    </c:if>
                    <c:if test="${loginFailed}">
                        <fmt:message key="user.wrong.password"/>
                    </c:if>
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

                    <c:if test="${chooseUser}">
                        <form action="/controller" role="form" method="post" name="choose-user">
                            <input type="hidden" name="command" value="get_another_user_page">
                            <div class="form-group">
                                <select name="login" class="form-control-static" style="width: 350px; color: black;" required>
                                    <option value="" disabled selected><fmt:message key="admin.update.movie.chooseuser"/> </option>
                                    <c:forEach var="user" items="${users}">
                                        <option value="${user.login}">${user.login}</option>
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
    </div>

</div>

<c:import url="../fragment/right_block.jsp"/>
<script>
    <%@include file="../../js/validation.js"%>
    <%--<c:import url="../../js/add_input.js"/>--%>
</script>

</body>
</html>