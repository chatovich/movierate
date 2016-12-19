<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resource.locale" />

<div id="radius" class="col-lg-2 col-sm-2" style="float:right;margin-right: 90px" >

    <%--<div class="radius">--%>
    <%--<h2>Movie Search</h2>--%>
    <form action="/controller" name="filtered_search" method="post">
        <input type="hidden" name="command" value="filtered_movie_search">
        <%--<div class="selects">--%>
        <label>
            <select id="genresRight" class="select-right" name="genre" >
                <option disabled selected><fmt:message key="admin.add.movie.choosegenre"/> </option>
                <%--<option>thriller</option>--%>
                <%--<option>comedy</option>--%>
                <%--<option>drams</option>--%>
                <%--<option>action</option>--%>
                <%--<option>documental</option>--%>
                <%--<option>biography</option>--%>
                <%--<option>detective</option>--%>
            </select>

        </label>
        <label>
            <select id="countriesRight"  class="select-right" name="country">
                <option disabled selected><fmt:message key="admin.add.movie.choosecountry"/> </option>
                <%--<option>USA</option>--%>
                <%--<option>Russia</option>--%>
                <%--<option>France</option>--%>
                <%--<option>Italy</option>--%>
                <%--<option>Great Britain</option>--%>
                <%--<option>New Zealand</option>--%>
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
            <button class="searchbutton">Search</button>
            <%--<button id="searchbutton" name="searchbutton">Search</button>--%>
        </div>
    </form>

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