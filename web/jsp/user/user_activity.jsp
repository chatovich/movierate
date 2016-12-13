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
    <jsp:include page="${pageContext.request.contextPath}/${includeMenu}"/>

    <div class="well">
        <div class="tab-content">
            <div class="tab-pane fade in active">
                <div id="for-insert">
                <p id="user-rating"><fmt:message key="user.rating"/>: ${userRating}</p>
                <c:forEach var="feedback" items="${showAnotherUser? anotherUser.userFeedbacks : signedUser.userFeedbacks}">
                    <hr color="black">
                    <p><strong>${feedback.movie.title}</strong></p>
                    <p style="font-size: medium;">
                        <c:forEach begin="0" end="9" varStatus="loop">
                                                <span style="color: ${loop.index < feedback.mark ? "gold" : "lightgrey"}">
                                                    <c:out value="★"/>
                                                </span>
                        </c:forEach>
                    </p>
                    <p align="justify">${feedback.text}</p>
                    <p align="right">${feedback.creatingDate}</p>
                </c:forEach>
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