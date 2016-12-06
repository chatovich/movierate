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
    <c:import url="../fragment/admin_menu.jsp"/>
    <c:if test="${add_movie}">
        <c:import url="../fragment/add_movie_form.jsp"/>
    </c:if>
</div>

<c:import url="../fragment/right_block.jsp"/>
<script>
    <%@include file="../../js/add_input.js"%>
    <%--<c:import url="../../js/add_input.js"/>--%>
</script>
</body>
</html>