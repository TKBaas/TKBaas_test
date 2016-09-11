<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>测试mvc 表单的数组 </title>
</head>
<body>
	<c:forEach var="tem" items="${list}">
		${tem}<br>	
	</c:forEach>
	
	<% 
		pageContext.setAttribute("index", 1);
	%>	<!-- 先定义一个变量 -->
	
	<form:form commandName="modelTT" method="post" action="${pageContext.request.contextPath}/test/test1">
		<c:forEach var="tem" items="${list}">
			<form:input type="text" path="names[${index}]" value="${tem }"/>	
			<% 
				pageContext.setAttribute("index", (Integer)pageContext.getAttribute("index")+1);
			%>
		</c:forEach>
		<input value="测试mvc" type="submit">
	</form:form>
</body>
</html>