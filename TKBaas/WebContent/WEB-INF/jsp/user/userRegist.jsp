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
	<title>鲜果之家-欢迎注册</title>
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
			<p>欢迎注册</p>
		</div>
	</div><!-- 注册界面头部 -->

	<div id="register-main-content">
		<div class="comWidth">
			<div class="register-content">
				<div class="main-content-bg"></div>
				<div class="main-content-right">
					<h3>会员注册</h3>
					<form:form commandName="user"  comclass="registerForm" id="registerForm" method="post" action="/TKBaas/user/pc/regist">
					 	<div class="user-name">
					 		<span for="phone"><i class="fa fa-user"></i></span>
					 		<form:input type="text" name="phone" id="phone"  path="phone" placeholder="请输入手机号" />
					 	</div>
					 	<div class="user-pass">
					 		<span for="password"><i class="fa fa-lock"></i></span>
					 		<form:input type="password" name="password" id="password" path="password" placeholder="请输入密码" />
					 	</div>
					 	<div class="conf-pass">
					 		<span for="confword"><i class="fa fa-lock"></i></span>
					 		<input type="password" name="confword" id="confword" placeholder="请确认密码">
					 	</div>
					 	<div class="remember-name">
					 								<label for="service"> <a href="##" target="_blank">点击表示您同意商城《服务协议》</a></label>
							<input type="checkbox" name="service">

						</div>
					 	<div class="register-submit">
					 		<input type="submit" name="register-submit" value="注册">
					 	</div>

					</form:form>
					<a class="link-login" href="<%=basePath%>user/pc/loginForm"><span>已有账号？</span>立即登陆</a>
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
		  $("#registerForm").validate({
		    rules: {
		    	phone: "required",
		      password: "required",
		      confword:"required",
		      phone: {
		        required: true,
		        minlength:11,
		        maxlength:11,
		        number:true
		      },
		      password: {
		        required: true,
		        minlength: 5,
		        maxlength:20,
		      },
		      confword: {
		        required: true,
		        minlength: 5,
		        maxlength:20,
		        equalTo: "#password"
		      },
		      service: "required"   
		    },
		    messages: {
		    	phone: {
		        required: "请输入用户名",
		        minlength: "手机号必需由11位组成",
		        maxlength: "手机号必需由11位组成"
		      },
		      password: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 5 个字母",
		        maxlength:"密码长度不能大于 20 个字母",
		      },
		      confword: {
		        required: "请输入密码",
		        minlength: "密码长度不能小于 5 个字母",
		        maxlength:"密码长度不能大于 20 个字母",
		        equalTo: "两次密码输入不一致"
		      },
		      service: "请接受我们的声明",
		    }
		  });
		});
	</script>


	
</body>
</html>