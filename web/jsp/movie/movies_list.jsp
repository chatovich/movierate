<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>

<div class="radius"  style="float: left;margin-left: 100px;" >

    <div>
    <c:forEach var="movie" items="${movies}">
        <div style="float: left; margin-left: 10px; margin-top: 10px;">
        <%--<a href="#"><img src="${movie.poster}" alt="img" border="0" height="314" width="220"></a>--%>
            <a href="#"><img src="${pageContext.request.contextPath}${movie.poster}" alt="img" border="0" height="314" width="220"></a>
        </div>
    </c:forEach>
    </div>

    <div>
        <c:if test="${currentPage!=1}">
            <a href="/controller?page=${currentPage-1}&command=get_movies_by_genre&genre=${genre}">prev</a>
        </c:if>
        ${currentPage}
        <c:if test="${currentPage lt pageQuantity}">
            <a href="/controller?page=${currentPage+1}&command=get_movies_by_genre&genre=${genre}">next</a>
        </c:if>
    </div>
    
</div>
<div class="radius" style="float:right;width: 20%;margin-right: 100px; margin-left: 20px" >

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




</body>
</html>