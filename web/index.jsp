<%@ page language="java" contentType="text/html;charset=utf-8"  pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
  <head>
    <title>index</title>
  </head>
  <body>
  <h2>hi</h2>
  <%--<jsp:forward page="${pageContext.request.contextPath}/jsp/main/main.jsp"/>--%>
  <c:redirect url="${pageContext.request.contextPath}/controller?command=load_main_page"/>
  </body>
</html>
