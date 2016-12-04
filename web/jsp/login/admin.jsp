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
        <div class="card-info"> <span class="card-title">Pamela Anderson</span>

        </div>
    </div>
    <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group" aria-label="...">
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-primary" href="#add_movie" data-toggle="tab">
                <div class="hidden-xs"><fmt:message key="admin.add.movie"/> </div>
            </button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" href="#tab2" data-toggle="tab">
                <div class="hidden-xs"><fmt:message key="admin.update.movie"/> </div>
            </button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" class="btn btn-default" href="#tab3" data-toggle="tab">
                <div class="hidden-xs"><fmt:message key="admin.new.comments"/> </div>
            </button>
        </div>
    </div>

    <div class="well" align="center" >
        <div class="tab-content">
            <div class="tab-pane fade in active" id="add_movie" style="width: 600px">
                <h3><fmt:message key="admin.add.movie.heading"/></h3><br>
                <form action="/controller" method="post" enctype="multipart/form-data" name="addmovie_form" style="margin: 0 auto">

                    <input type="hidden" name="command" value="add_movie">
                    <div>
                        <input name="title" class="form-control" type="text" required placeholder=<fmt:message key="admin.movie.title"/> >
                    </div>
                    <div>
                        <input name="year" class="form-control" type="number" min="1920" required placeholder=<fmt:message key="admin.movie.year"/>>
                    </div>
                    <div id="genres">
                        <div >
                    <select name="genre" class="form-control">
                        <option>thriller</option>
                        <option>comedy</option>
                        <option>drams</option>
                        <option>action</option>
                        <option>documental</option>
                        <option>biography</option>
                        <option>detective</option>
                    </select>
                            <button type="button" onclick="add_input(this.parentNode)"><img src="../../img/icon/add.png"></button>
                            <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                        </div>
                    </div>


                    <div id="countries">
                        <div >
                            <select name="country" class="form-control">
                                <option>france</option>
                                <option>usa</option>
                                <option>russia</option>
                            </select>
                            <button type="button" onclick="add_country(this.parentNode)"><img src="../../img/icon/add.png"></button>
                            <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                        </div>
                    </div>

                    <div id="actors">
                        <div >
                            <select name="actor" class="form-control">
                                <option>Brad Pitt</option>
                                <option>2</option>
                                <option>3</option>
                            </select>
                            <button type="button" onclick="add_input(this.parentNode)"><img src="../../img/icon/add.png"></button>
                            <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                        </div>
                    </div>

                    <div id="directors">
                        <div >
                            <select name="director" class="form-control">
                                <option>David Fincher</option>
                                <option>2</option>
                                <option>3</option>
                            </select>
                            <button type="button" onclick="add_input(this.parentNode)"><img src="../../img/icon/add.png"></button>
                            <%--<input type="button" value=<img src="../../img/icon/add.png"> onclick="add_input(this.parentNode)" >--%>
                        </div>
                    </div>

                    <div class="form-group">

                            <textarea class="form-control" id="plot" name="plot" required placeholder=<fmt:message key="admin.add.movie.plot"/> ></textarea>

                    </div>

                    <div>
                    <input id="duration" name="duration" type="text" class="form-control" required placeholder=<fmt:message key="admin.add.movie.duration"/> >
                    </div>

                    <div>
                    <input id="poster" type="file" name="poster" required>
                    </div>

                    <div>
                    <button id="singlebutton" name="singlebutton" class="btn btn-primary">Button</button>
                    </div>

                </form>
            </div>

            <div class="tab-pane fade in" id="tab2">
                <h3>This is tab 2</h3>
            </div>

            <div class="tab-pane fade in" id="tab3">
                <h3>This is tab 3</h3>
            </div>
        </div>
    </div>

</div>

<c:import url="../fragment/right_block.jsp"/>
<%--<jsp:include page = "${pageContext.request.contextPath}/jsp/header/right_block.jsp"/>--%>
<script language="javascript">
    // Теперь эта функция будет принимать указатель на объект, после которого нужно осуществить вставку
    function add_input(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<select name="genre" class="form-control"><option>thriller</option><option>comedy</option><option>drama</option></select>';
// Дописываем рядом с input-ом кнопку, она будет добовлять элемент именно под input, рядом с которым она находится
//        new_input.innerHTML=new_input.innerHTML+'<input type="button" value="+" onclick="add_input(this.parentNode)">';
// И еще одна кнопочка для его удаления.
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_input(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
//Ищем присутствует ли следующий узел в структуре DOM-а
        if (obj.nextSibling)
        // если да - то создаем после него
            document.getElementById('genres').insertBefore(new_input,obj.nextSibling)
//если такого не нашлось то просто добавляем в конец
        else document.getElementById('genres').appendChild(new_input);
    }
    function del_input(obj)
    {
        document.getElementById('genres').removeChild(obj)
    }


    function add_country(obj)
    {
        var new_input=document.createElement('div');
        new_input.innerHTML='<select name="country" class="form-control"><option>france</option><option>usa</option><option>russia</option></select>';
        new_input.innerHTML=new_input.innerHTML+' <button type="button" onclick="del_country(this.parentNode)"><img src="../../img/icon/delete.png"></button>';
        if (obj.nextSibling)
            document.getElementById('countries').insertBefore(new_input,obj.nextSibling)
        else document.getElementById('countries').appendChild(new_input);
    }
    function del_country(obj)
    {
        document.getElementById('countries').removeChild(obj)
    }
    </script>
</body>
</html>