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
	<title>鲜果之家-欢迎登陆</title>
	<meta charset="utf-8">
	<link href="/TKBaas/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="/TKBaas/css/bootstrap.min.css">
	<link rel="stylesheet" href="/TKBaas/css/font-awesome.min.css">
	<link rel="stylesheet" href="/TKBaas/css/style.public.css">
	<link rel="stylesheet" href="/TKBaas/css/login.css">
</head>
<body>
	
	<div id="login-head">
		<div class="comWidth">
			<a href="index.html"><img src="/TKBaas/images/logo.png"></a>
			<p>欢迎登陆</p>
		</div>
	</div><!-- 登陆界面头部 -->

	<div id="login-main-content">
		<div class="comWidth">
			<div class="login-content">
				<div class="main-content-bg"></div>
				<div class="main-content-right">
					<h3>登陆商城</h3>
					<form:form commandName="user" class="loginForm" id="loginForm" method="post" action="/TKBaas/user/pc/login">
					 	<div class="user-name">
					 		<span><i class="fa fa-user"></i></span>
					 		<form:input type="text" name="username" id="username" placeholder="请输入手机号" path="phone" />
					 	</div>
					 	<div class="user-pass">
					 		<span for="password"><i class="fa fa-lock"></i></span>
					 		<form:input type="password" name="password" id="password" placeholder="请输入密码" path="password" />
					 	</div>
					 	<div class="user-submit">
					 		<input type="submit" name="login-submit" value="登陆">
					 	</div>
					</form:form>
					<div class="remember-name">
						<input type="checkbox" name="auto-login">
						<label for="auto-login">自动登陆</label>
					</div>
					<a class="link-register" href="/TKBaas/user/pc/registForm">立即注册</a>
				</div>
			</div>
		</div>
	</div>


	

	<script type="text/javascript" src="/TKBaas/js/jquery.min.js"></script>
	<script type="text/javascript" src="/TKBaas/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="/TKBaas/js/messages_zh.js"></script>
	<script type="text/javascript" src="/TKBaas/js/index.public.js"></script>

	<script type="text/javascript">
		$().ready(function() {
			// 在键盘按下并释放及提交后验证提交表单
		  $("#loginForm").validate({
		    rules: {
		      username: "required",
		      password: "required",
		      username: {
		        required: true,
		        minlength:11,
		        maxlength:11,
		        number:true
		      },
		      password: {
		        required: true,
		        minlength: 5,
		        maxlength:20,
		      }      
		    },
		    messages: {
		      username: {
		        required: "请输入用户名",
		        minlength: "手机号必需由11位组成",
		        maxlength: "手机号必需由11位组成"
		      },
		      password: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 5 个字母",
		        maxlength: "密码长度不能大于 20 个字母",
		      }
		    }
		  });
		});
	</script>
	
</body>
</html>