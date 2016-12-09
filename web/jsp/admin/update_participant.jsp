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

<%--<div id="radius" class="col-lg-8 col-sm-8" style="float: left;margin-left: 90px">--%>
    <c:import url="../fragment/admin_menu.jsp"/>
    <div class="well" align="center" >
        <div class="tab-pane fade in active" id="add_movi" style="width: 600px" align="center">
    <div id="for-insert">

        <form action="/controller" role="form" class="form-horizontal" method="post" name="update-participant">

            <input type="hidden" name="command" value="update_participant">
            <input type="hidden" name="id_participant" value="${participant.id}">
            <div class="form-group">
                <input name="name" value="${participant.name}" class="form-control-static" type="text" required style="width: 300px; color: black">
            </div>
                <div class="form-group">
                <select name="profession" class="form-control-static" style="width: 300px; color: black;margin-top: 10px" required>
                    <option>${participant.profession}</option>
                    <option value="actor"><fmt:message key="admin.actor"/></option>
                    <option value="director"><fmt:message key="admin.director"/></option>
                </select>
            </div>
            <div>
                <button class="btn btn-primary"><fmt:message key="admin.add.movie.submit"/></button>
            </div>
        </form>

    </div>
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