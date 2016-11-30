<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>

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
                                <span>Welcome to <strong style="font-family:Sofia;">MOVIERATE</strong></span>
                            </h2>
                            <br>
                            <h3>
                                <span>Place where you can learn about movies and share opinions</span>
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
                                <span>Easy movie search</span>
                            </h2>
                            <br>
                            <h3>
                                <span>Search movies by genre, country, release year</span>
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
                                <span>Be active!</span>
                            </h2>
                            <br>
                            <h3>
                                <span>Leave marks and feedbacks about movies, earn points and go to the cinema for free! </span>
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
