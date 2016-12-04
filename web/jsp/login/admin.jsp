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

    <div class="well" align="center">
        <div class="tab-content">
            <div class="tab-pane fade in active" id="add_movie">
                <h3><fmt:message key="admin.add.movie.heading"/></h3><br>
                <form action="/controller" method="post" enctype="multipart/form-data" style="margin: 0 auto; display: inline-block">

                    <input name="title" type="text" class="form-control input-md" placeholder=<fmt:message key="admin.movie.title"/> >
                    <input name="year" type="number" min="1920" class="form-control input-md" placeholder=<fmt:message key="admin.movie.year"/>>

                    <select name="genres" multiple="multiple">
                                <option value="1">Option one</option>
                                <option value="2">Option two</option>
                    </select>


                    <!-- Select Multiple -->
                    <div class="form-group">

                            <select id="selectmultile" name="selectmultiple" class="form-control" multiple="multiple">
                                <option value="1">Option one</option>
                                <option value="2">Option two</option>
                            </select>

                    </div>

                    <!-- Select Multiple -->
                    <div class="form-group">

                            <select id="selectmultipl" name="selectmultiple" class="form-control" multiple="multiple">
                                <option value="1">Option one</option>
                                <option value="2">Option two</option>
                            </select>

                    </div>

                    <!-- Textarea -->
                    <div class="form-group">

                            <textarea class="form-control" id="textarea" name="textarea">default text</textarea>

                    </div>

                    <!-- Text input-->
                    <div class="form-group">

                            <input id="textinpu" name="textinput" type="text" placeholder="placeholder" class="form-control input-md">
                            <span class="help-block">help</span>

                    </div>

                    <!-- Button -->
                    <div class="form-group">

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

</body>
</html>