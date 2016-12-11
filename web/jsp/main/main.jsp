<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="yul" uri="/WEB-INF/tld/custom.tld" %>
<%@ page session="true" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}"/>
<fmt:setBundle basename="resource.locale" />

<%--<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>--%>

<!DOCTYPE html>
<html>
<c:import url="../fragment/header.jsp"/>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>--%>

<body class="body">

<c:import url="../fragment/top_menu.jsp"/>
<div class="container">
    <div class="row">
        <!-- Carousel -->
        <div id="carousel-example-generic" class="carousel slide" data-ride="carousel">
            <!-- Indicators -->
            <ol class="carousel-indicators">
                <li data-target="#carousel-example-generic" data-slide-to="0" class="active"></li>
                <li data-target="#carousel-example-generic" data-slide-to="1"></li>
                <li data-target="#carousel-example-generic" data-slide-to="2"></li>
            </ol>
            <!-- Wrapper for slides -->
            <div class="carousel-inner">
                <div class="item active">
                    <img src="${pageContext.request.contextPath}/img/titanicwide.jpg" alt="First slide">
                    <!-- Static Header -->
                    <div class="header-text hidden-xs">
                        <div class="col-md-12 text-center">
                            <h2>
                                <span><fmt:message key="main.welcome"/> <strong style="font-family:Sofia;"><fmt:message key="main.movierate"/><yul:login/></strong></span>
                            </h2>
                            <br>
                            <h3>
                                <span><fmt:message key="main.first.image"/></span>
                            </h3>

                        </div>
                    </div><!-- /header-text -->
                </div>
                <div class="item">
                    <img src="${pageContext.request.contextPath}/img/smithwide.jpg" alt="Second slide">
                    <!-- Static Header -->
                    <div class="header-text hidden-xs">
                        <div class="col-md-12 text-center">
                            <h2>
                                <span><fmt:message key="main.second.image1"/></span>
                            </h2>
                            <br>
                            <h3>
                                <span><fmt:message key="main.second.image2"/></span>
                            </h3>
                            <br>
                        </div>
                    </div><!-- /header-text -->
                </div>
                <div class="item">
                    <img src="${pageContext.request.contextPath}/img/lordwide.jpg" alt="Third slide">
                    <!-- Static Header -->
                    <div class="header-text hidden-xs">
                        <div class="col-md-12 text-center">
                            <h2>
                                <span><fmt:message key="main.third.image1"/></span>
                            </h2>
                            <br>
                            <h3>
                                <span><fmt:message key="main.third.image2"/></span>
                            </h3>
                            <br>
                        </div>
                    </div><!-- /header-text -->
                </div>
            </div>
            <!-- Controls -->

        </div><!-- /carousel -->
    </div>
</div>
<div style="width:100%; height:15px; clear:both;">.</div>

</body>
</html>
