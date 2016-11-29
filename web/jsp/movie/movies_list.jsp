<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<jsp:include page = "${pageContext.request.contextPath}/jsp/header/header.jsp"/>

<div class="radius"  style="float: left;margin-left: 100px;" >

    <div>
    <c:forEach var="movie" items="${movies}">
        <div style="float: left; margin-left: 10px; margin-top: 10px;">
        <%--<a href="#"><img src="${movie.poster}" alt="img" border="0" height="314" width="220"></a>--%>
            <a href="/controller?command=get_movie_page&id=${movie.id}"><img src="${pageContext.request.contextPath}${movie.poster}" alt="img" border="0" height="314" width="220"></a>
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

<jsp:include page = "${pageContext.request.contextPath}/jsp/header/right_block.jsp"/>




</body>
</html>