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
        <%--<div class="tab-content">--%>
            <div class="tab-pane fade in active" id="add_movi" style="width: 600px" align="center">

    <div id="for-insert">

        <div id="add_movie" align="center">
            <h3><fmt:message key="admin.update.movie.heading"/></h3><br>

            <form action="${pageContext.request.contextPath}/controller" method="post" enctype="multipart/form-data" name="updatemovie_form">

                <input type="hidden" name="command" value="update_movie">
                <input type="hidden" name="id_movie" value="${movie.id}">
                <div style="display: inline">
                    <span class="star">* </span><input name="title" value="${movie.title}" style="display: inline" class="form-control" type="text" required placeholder=<fmt:message key="admin.movie.title"/> >
                </div>
                <div>
                    <span class="star">*  </span><input name="year" value="${movie.year}" class="form-control" type="number" min="1920" required placeholder=<fmt:message key="admin.movie.year"/>>
                </div>

                <div id="genres">
                    <div >
                        <span id="star">*</span><select name="genre" class="form-control">
                        <option disabled selected><fmt:message key="admin.add.movie.choosegenre"/> </option>
                        <c:forEach var="genre" items="${genres}">
                            <option>${genre.genreName}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="add_genre(this.parentNode)"><img src="../../img/icon/add.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                    <c:forEach var="movieGenre" items="${movie.movieGenres}">
                    <div >
                        <span id="star">*</span><select name="genre" class="form-control">
                        <option>${movieGenre.genreName}</option>
                        <c:forEach var="genre" items="${genres}">
                            <option>${genre.genreName}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="del_genre(this.parentNode)"><img src="../../img/icon/delete.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                    </c:forEach>
                </div>


                <div id="countries">
                    <div >
                        <span id="star">*</span><select name="country" class="form-control">
                        <option disabled selected><fmt:message key="admin.add.movie.choosecountry"/> </option>
                        <c:forEach var="country" items="${countries}">
                            <option>${country.countryName}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="add_country(this.parentNode)"><img src="../../img/icon/add.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                <c:forEach var="movieCountry" items="${movie.movieCountries}">
                    <div >
                        <span id="star">*</span><select name="country" class="form-control">
                        <option>${movieCountry.countryName}</option>
                        <c:forEach var="country" items="${countries}">
                            <option>${country.countryName}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="del_country(this.parentNode)"><img src="../../img/icon/delete.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                </c:forEach>
                </div>


                <div id="actors">
                    <div >
                        <span id="star">*</span><select name="actor" class="form-control">
                        <option disabled selected><fmt:message key="admin.add.movie.chooseactor"/> </option>
                        <c:forEach var="actor" items="${actors}">
                            <option>${actor.name}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="add_actor(this.parentNode)"><img src="../../img/icon/add.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                    <c:forEach var="movieActor" items="${movieActors}">
                    <div >
                        <span id="star">*</span><select name="actor" class="form-control">
                        <option>${movieActor.name}</option>
                        <c:forEach var="actor" items="${actors}">
                            <option>${actor.name}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="del_actor(this.parentNode)"><img src="../../img/icon/delete.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                    </c:forEach>
                </div>

                <div id="directors">
                    <div >
                        <span id="star">*</span><select name="director" class="form-control">
                        <option disabled selected><fmt:message key="admin.add.movie.choosedirector"/> </option>
                        <c:forEach var="director" items="${directors}">
                            <option>${director.name}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="add_director(this.parentNode)"><img src="../../img/icon/add.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                    <c:forEach var="movieDirector" items="${movieDirectors}">
                    <div >
                        <span id="star">*</span><select name="director" class="form-control">
                        <option>${movieDirector.name}</option>
                        <c:forEach var="director" items="${directors}">
                            <option>${director.name}</option>
                        </c:forEach>
                    </select>
                        <button type="button" onclick="del_director(this.parentNode)"><img src="../../img/icon/delete.png"></button>
                        <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                    </div>
                    </c:forEach>
                </div>

                <div>
                    <span class="star">*  </span><input name="duration" value="${movie.duration}" class="form-control" type="number" required placeholder=<fmt:message key="admin.add.movie.duration"/>>
                </div>
                <div>
                    <span class="star">*  </span><textarea name="plot" class="form-control" required placeholder=<fmt:message key="admin.add.movie.plot"/>>${movie.plot}</textarea>
                </div>
                <img src="${pageContext.request.contextPath}${movie.poster}" height="200px" width="140px" alt="no poster"><br>
                <span><fmt:message key="admin.update.poster"/> </span>
                <div>
                    <span class="star">* </span><input id="add_poster" value="${movie.poster}" class="form-control" type="file" name="poster">
                </div>

                <span id="oblig_field">*  <fmt:message key="obligatory.field"/></span>
                <br>
                <div>
                    <button class="btn btn-primary admin-button"><fmt:message key="admin.add.movie.submit"/> </button>
                </div>

            </form>
        </div>
</div>
</div>
        </div>
    </div>

<c:import url="../fragment/right_block.jsp"/>
<script type="text/javascript">

    function add_actor(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<span id="star">*</span><select name="actor" class="form-control" required><option value="" disabled selected>' +
                '<fmt:message key="admin.add.movie.chooseactor"/> </option> <c:forEach var="actor" items="${actors}">' +
                '<option>${actor.name}</option> </c:forEach>';
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_actor(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
        if (obj.nextSibling)
            document.getElementById('actors').insertBefore(new_input,obj.nextSibling);
        else document.getElementById('actors').appendChild(new_input);
    }
    function del_actor(obj)
    {
        document.getElementById('actors').removeChild(obj)
    }
    function add_director(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<span id="star">*</span><select name="director" class="form-control" required><option value="" disabled selected>' +
                '<fmt:message key="admin.add.movie.choosedirector"/></option><c:forEach var="director" items="${directors}">' +
                '<option>${director.name}</option> </c:forEach>';
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_director(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
        if (obj.nextSibling)
            document.getElementById('directors').insertBefore(new_input,obj.nextSibling);
        else document.getElementById('directors').appendChild(new_input);
    }
    function del_director(obj)
    {
        document.getElementById('directors').removeChild(obj)
    }
    function add_genre(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<span id="star">*</span><select name="genre" class="form-control" required><option value="" disabled selected>' +
                '<fmt:message key="admin.add.movie.choosegenre"/></option><c:forEach var="genre" items="${genres}">' +
                '<option>${genre.genreName}</option></c:forEach></select>';
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_genre(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
        if (obj.nextSibling)
            document.getElementById('genres').insertBefore(new_input,obj.nextSibling);
        else document.getElementById('genres').appendChild(new_input);
    }
    function del_genre(obj)
    {
        document.getElementById('genres').removeChild(obj)
    }
</script>
</body>
</html>