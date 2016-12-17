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
        <div class="useravatar">
            <img alt="" src="http://lorempixel.com/100/100/people/9/">
        </div>
        <div class="card-info"> <span class="card-title">admin</span>

        </div>
    </div>

    <yul:menu role="${signedUser.role}"/>
    <jsp:include page="${pageContext.request.contextPath}/${includeMenu}"/>
    <div class="well">
        <div class="tab-content">
            <div class="tab-pane fade in active" id="add_movie">

                <div id="for-insert" style="text-align:left">
                    <c:if test="${noFeedback}">
                        <fmt:message key="admin.no.feedback"/>
                    </c:if>
                    <c:if test="${statusUpdated}">
                    <span class="admin-success"><fmt:message key="admin.feedback.status.updated"/></span>
                    </c:if>
                    <ul>
                        <c:forEach var="feedback" items="${newFeedbacks}">
                            <li style="font-size: 14px"><a href="${pageContext.request.contextPath}/controller?command=get_feedback&id_feedback=${feedback.id}"> <b>${feedback.user.login}</b> left comment to movie <b>"${feedback.movie.title}"</b> on ${feedback.creatingDate}</a></li>
                        </c:forEach>
                    </ul>
                    <c:if test="${showFeedback}">
                        <table  width="100%">
                            <tr><td>
                                <strong>${feedback.movie.title}</strong></td>
                            </tr>
                            <tr><td>
                                    ${feedback.text}</td>
                            </tr>
                            <tr><td>
                                    ${feedback.mark} <fmt:message key="user.mark"/></td>
                            </tr>
                            <tr><td align="right">
                                <strong><a href="${pageContext.request.contextPath}/controller?command=get_another_user_page&login=${feedback.user.login}">${feedback.user.login}</a> </strong></td>
                            </tr>
                            <tr><td align="right">
                                    ${feedback.creatingDate}</td>
                            </tr>
                            <tr>
                                <td>
                                    <form action="/controller" method="get">
                                        <input type="hidden" name="command" value="accept_feedback">
                                        <input type="hidden" name="id_feedback" value="${feedback.id}">
                                        <input style="width:25px"  type="radio" name="feedback_action" value="false"><fmt:message key="admin.feedback.reject"/><br>
                                        <input style="width:25px" type="radio" name="feedback_action" value="true" checked><fmt:message key="admin.feedback.accept"/><br>
                                        <button id="singlebutton" name="singlebutton" class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button>
                                    </form>
                                </td>
                            </tr>
                        </table>

                    </c:if>


                </div>
            </div>
        </div>
    </div>

</div>

<c:import url="../fragment/right_block.jsp"/>

</body>
</html>