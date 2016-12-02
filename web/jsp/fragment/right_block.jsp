<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div id="radius" style="float:right;width: 20%;margin-right: 100px; margin-left: 20px" >

    <%--<div class="radius">--%>
    <%--<h2>Movie Search</h2>--%>
    <form action="#" name="search" method="post">
        <%--<div class="selects">--%>
        <label>
            <select>
                <option>All genres</option>
                <option>thriller</option>
                <option>comedy</option>
                <option>drams</option>
                <option>action</option>
                <option>documental</option>
                <option>biography</option>
                <option>detective</option>
            </select>

        </label>
        <label>
            <select>
                <option>All countries</option>
                <option>USA</option>
                <option>Russia</option>
                <option>France</option>
                <option>Italy</option>
                <option>Great Britain</option>
                <option>New Zealand</option>
            </select>
        </label>
        <label>
            <select>
                <option>All years</option>
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
            <a href="#" class="searchbutton">Search</a>
            <%--<button id="searchbutton" name="searchbutton">Search</button>--%>
        </div>
    </form>
    <%--</div>--%>

</div>