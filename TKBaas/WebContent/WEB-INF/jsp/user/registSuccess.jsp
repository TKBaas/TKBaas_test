<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>

<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh_cn">
<head>
	<title>鲜果之家-注册成功</title>
	<meta charset="utf-8">
	<link href="/TKBaas/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="/TKBaas/css/bootstrap.min.css">
	<link rel="stylesheet" href="/TKBaas/css/font-awesome.min.css">
	<link rel="stylesheet" href="/TKBaas/css/style.public.css">
	<link rel="stylesheet" href="/TKBaas/css/register.css">
</head>
<body>
	
	<div id="register-head">
		<div class="comWidth">
			<a href="index.html"><img src="/TKBaas/images/logo.png"></a>
			<p>注册成功</p>
		</div>
	</div><!-- 注册界面头部 -->

	<div id="register-main-content">
		<div class="comWidth">
			<div class="register-content">
				<div class="main-content-bg"></div>
				<div class="main-content-right">
					<h3>会员注册</h3>
					<form class="registerForm" id="registerForm" method="" action="/TKBaas/user/pc/loginForm">
					 	<p style="text-align:center;font-size:20px;padding:50px;">注册成功！</p>
					 	<div class="register-submit" style="margin-bottom:90px;">
					 		<input type="submit" name="register-submit" value="立即登陆">
					 	</div>
					</form>
				</div>
			</div>
		</div>
	</div>


	

	<script type="text/javascript" src="/TKBaas/js/jquery.min.js"></script>
	<script type="text/javascript" src="/TKBaas/js/index.public.js"></script>


	
</body>
</html>
