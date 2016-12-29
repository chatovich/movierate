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
<div class="container" style="margin-bottom: 20px">
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
                    <img src="${pageContext.request.contextPath}/img/fantanimals.jpg" alt="First slide">
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
                    <img src="${pageContext.request.contextPath}/img/potterwide.jpg" alt="Third slide">
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


<div style="width:100%; height:1px; clear:both;">.</div>

<div class="main-search" >
    <h2 id="search-header"><fmt:message key="main.search.header"/> </h2>
    <form action="/controller" name="filtered_search" method="post">
        <input type="hidden" name="command" value="filtered_movie_search">
        <%--<div class="selects">--%>
        <label>
            <select id="genresRight" class="select-right" name="genre" >
                <option disabled selected><fmt:message key="admin.add.movie.choosegenre"/> </option>
            </select>

        </label>
        <label>
            <select id="countriesRight"  class="select-right" name="country">
                <option disabled selected><fmt:message key="admin.add.movie.choosecountry"/> </option>
            </select>
        </label>
        <label>
            <select class="select-right" name="year">
                <option disabled selected><fmt:message key="admin.add.movie.chooseyear"/> </option>
                <option>1960s</option>
                <option>1970s</option>
                <option>1980s</option>
                <option>1990s</option>
                <option>2000s</option>
                <option>2010s</option>
            </select>
        </label>
        <%--</div>--%>
        <br>
        <div align="center">
            <button class="searchbutton"><fmt:message key="main.search"/> </button>
            <%--<button id="searchbutton" name="searchbutton">Search</button>--%>
        </div>
    </form>
</div>
<div id="main-bottom">
<div class="blocks" style="margin-left: 90px">
    <p align="left" class="main-p"><b><fmt:message key="main.latest.feedbacks"/></b> </p><br>
    <c:forEach var="feedback" items="${latestFeedbacks}">
        <p align="left"><a href="${pageContext.request.contextPath}/controller?command=get_movie_page&id=${feedback.movie.id}" ><b>${feedback.movie.title}</b></a></p>
        <p align="left" class="main-text">${feedback.text}</p>
        <br>
    </c:forEach>
</div>
<div class="blocks">
    <p align="left" class="main-p"><b><fmt:message key="main.top.movies"/></b> </p><br>
    <table>
    <c:forEach var="movie" items="${topMovies}">
        <tr>
            <td>
        <p align="left"><a href="${pageContext.request.contextPath}/controller?command=get_movie_page&id=${movie.id}">${movie.title}</a></p>
        </td>
            <td valign="bottom">
                <p class="main-text">${movie.rating}</p>
            </td>
        </tr>
    </c:forEach>
    </table>
</div>
<div class="blocks" style="margin-right: 90px">
    <p><fmt:message key="main.top.movies"/> </p>
</div>
</div>

<c:if test="${language eq 'en_EN'}">
    <script type="text/javascript">
        <jsp:include page="${pageContext.request.contextPath}/js/select_genres_en.js"/>
    </script>
</c:if>
<c:if test="${language eq 'ru_RU'}">
    <script type="text/javascript">
        <jsp:include page="${pageContext.request.contextPath}/js/select_genres_ru.js"/>
    </script>
</c:if>

<script type="text/javascript">
    $(function(){
        $.getJSON('../../json/countries.json', function(data) {
            for(var i=0;i<data.countries.length;i++){
                $('#countriesRight').append('<option>' + data.countries[i].name + '</option>');
            }
        });
    });
</script>

</body>
</html>
