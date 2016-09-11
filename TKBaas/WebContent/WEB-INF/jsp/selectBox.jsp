<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'selectBox.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  
  <body>
     <!-- 
     <form action="order/pc/recieveSelectBox">
         <input type="text" name="list"  value="1"/>
         <input type="text" name="list"  value="2"/>
         <input type="submit" value="submit">  
     </form> 
      -->
     <!-- 
      <form:form commandName="productForm" action="order/pc/recieveSelectBox">
        <jstl:forEach items="${list}" var="product" varStatus="posi">
          <input type="text" name="product[${posi.index}].id" value="${product.id}" />
          <input type="text" name="product[${posi.index}].name" value="${product.name}" />
        </jstl:forEach>
         <input type="submit" value="submit"> 
      </form:form>  
      -->     
       <form:form commandName="productForm" action="order/pc/recieveSelectBox">
        <jstl:forEach items="${cart.sellerItem}" var="sellerItem" varStatus="sIndex">
          <jstl:forEach items="${sellerItem.proItem}" var="proItem" varStatus="pIndex">
            <input type="text" name="" value="${sellerItem.sellerId}" />
            <input type="text" name="product[${pIndex.index}].id"   value="${proItem.product.id}" />
            <input type="text" name="product[${pIndex.index}].name" value="${proItem.product.name}" />
        </jstl:forEach>
       </jstl:forEach>
         <input type="submit" value="submit"> 
      </form:form>      
  </body>
</html>
