<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="yul" uri="customtags" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />

<!DOCTYPE html>
<html>
<c:import url="../fragment/header.jsp"/>

<body class="body">

<c:import url="../fragment/top_menu.jsp"/>

<div class="col-lg-8 col-sm-8" style="margin-top: 80px;margin-left: 70px">
    <div class="card hovercard">
        <div class="card-background">
            <img class="card-bkimg" alt="no photo" src="http://lorempixel.com/100/100/people/9/">
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
                    <c:if test="${movieDeleted}">
                        <span class="admin-success"><fmt:message key="admin.movie.deleted"/> </span>
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
                    <c:if test="${(signedUser.isBanned)&&(!showAnotherUser)}">
                        <span class="validation-msg"><fmt:message key="user.banned"/> ${signedUser.banStart} </span>
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
                        <form action="${pageContext.request.contextPath}/controller" role="form" method="post" name="choose-film">
                            <input type="hidden" name="command" value="get_movie_info_for_update">
                            <div class="form-group">
                                <select name="id_movie" class="form-control-static" style="width: 350px; color: black;" required>
                                    <option value="" disabled selected><fmt:message key="admin.update.movie.choosemovie"/> </option>
                                    <c:forEach var="movie" items="${movies}">
                                        <option value="${movie.id}">${movie.title}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div id="sure"></div>
                            <div>
                                <span id="del"><a href="#" type="button" class="btn btn-primary" onclick="sure_delete()"><fmt:message key="admin.add.movie.delete"/></a></span>
                                <button class="btn btn-primary" name="action" value="update"><fmt:message key="admin.add.movie.update"/></button>
                            </div>
                        </form>
                    </c:if>

                    <c:if test="${chooseUser}">
                        <form action="${pageContext.request.contextPath}/controller" role="form" method="post" name="choose-user">
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
                        <form action="${pageContext.request.contextPath}/controller" role="form" method="post" name="choose-participant">
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
    <%--<%@include file="../../js/validation.js"%>--%>
    <%@include file="../../js/add_input.js"%>

    function validateEditForm() {
        PASSWORDS_NOMATCH = <fmt:message key="reg.passwords.nomatch"/>;
        PATTERN_PASSWORD = <fmt:message key="login.pattern"/>;
        PATTERN_EMAIL = <fmt:message key="reg.wrong.email"/>;
        var result = true;
        //get variables from the form
        password1 = document.forms[0]["new_password"].value;
        password2 = document.forms[0]["confirm_password"].value;
        email = document.forms[0]["email"].value;
        pattern_password = new RegExp("[A-z0-9_]{5,}");
        pattern_email = new RegExp("[0-9a-z_]+@[0-9a-z_]+\.[a-z]{2,5}");
        //specify error variables
        err_pass1 = document.getElementById("error_password1");
        err_pass2 = document.getElementById("error_password2");
        err_email = document.getElementById("error_email");
        //clear error messages
        err_pass1.innerHTML = "";

        err_email.innerHTML = "";

        if (!pattern_email.test(email)) {
            result = false;
            err_email.innerHTML = PATTERN_EMAIL;
        }
        if (password1 !== password2) {
            err_pass1.innerHTML = PASSWORDS_NOMATCH;
            err_pass2.innerHTML = PASSWORDS_NOMATCH;
            document.forms[0]["new_password"].value = "";
            document.forms[0]["confirm_password"].value = "";
            result = false;
        }
        if (password1!==""){
            if (password2!=="") {
                if (!pattern_password.test(password1)) {
                    result = false;
                    err_pass1.innerHTML = PATTERN_PASSWORD;
                    err_pass2.innerHTML = "";
                }
            }
        }
        return result;}
</script>

</body>
</html>