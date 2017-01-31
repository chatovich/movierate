<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />

<div id="radius" class="col-lg-2 col-sm-2" style="float:right;margin-right: 90px" >

    <form action="${pageContext.request.contextPath}/controller" name="filtered_search" method="post">
        <input type="hidden" name="command" value="filtered_movie_search">
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
            <button class="searchbutton"><fmt:message key="movie.search"/></button>
        </div>
    </form>

    <div style="height: 150px"></div>
    <form action="${pageContext.request.contextPath}/controller" name="movie_title_search" method="get">
        <input type="hidden" name="command" value="movie_title_search">
        <label><input type="text" name="title" class="form-control input-md" placeholder="<fmt:message key="main.search.title"/>" ></label>
        <br>
        <div align="center">
            <button class="searchbutton"><fmt:message key="main.search"/> </button>
        </div>
    </form>

</div>
