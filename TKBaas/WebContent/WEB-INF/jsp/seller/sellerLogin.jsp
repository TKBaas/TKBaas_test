<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<!DOCTYPE html>
<html lang="zh_cn">
<head>
	<title>鲜果之家-商家登录</title>
	<meta charset="utf-8">
	<link href="${pageContext.request.contextPath}/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.public.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
	
	<div id="login-head">
		<div class="comWidth">
			<a href="index.html"><img src="${pageContext.request.contextPath}/images/logo.png"></a>
			<p>商家登录</p>
		</div>
	</div><!-- 登陆界面头部 -->

	<div id="login-main-content">
		<div class="comWidth">
			<div class="login-content">
				<div class="main-content-bg"></div>
				<div class="main-content-right">
					<h3>商家登录</h3>
					<form:form class="loginForm" id="loginForm" method="post" commandName="seller"
								action="${pageContext.request.contextPath}/seller/pc/login">
					 	<div class="user-name">
					 		<span><i class="fa fa-user"></i></span>
					 		<form:input type="text" name="phone" id="phone" placeholder="请输入手机号" required="required" path="phone"/>
					 	</div>
					 	<div class="user-pass">
					 		<span for="password"><i class="fa fa-lock"></i></span>
					 		<form:input type="password" name="password" id="password" placeholder="请输入密码" required="required" path="password"/>
					 	</div>
					 	<div class="user-submit">
					 		<input type="submit" name="login-submit" value="登陆">
					 	</div>
						<div class="remember-name">
							<input type="checkbox" name="autoLogin" value="remember" />
							<label for="autoLogin">记住密码</label>
						</div>
					</form:form>
					<a class="link-register" href="register.html">立即注册</a>
				</div>
			</div>
		</div>
	</div>


	

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}js/messages_zh.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}js/index.public.js"></script>

	<script type="text/javascript">
		/* $.validator.setDefaults({
		    submitHandler: function() {
		      alert("提交事件!");
		    }
		}); */
		$().ready(function() {
			// 在键盘按下并释放及提交后验证提交表单
		  $("#loginForm").validate({
		    rules: {
		      phone: "required",
		      password: "required",
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
		      }      
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
		        maxlength: "密码长度不能大于 20 个字母",
		      }
		    }
		  });
		});
	</script>
	
</body>
</html>