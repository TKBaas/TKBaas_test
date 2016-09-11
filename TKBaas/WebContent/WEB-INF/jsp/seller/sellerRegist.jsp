<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<!DOCTYPE html>
<html lang="zh_cn">
<head>
	<title>商家注册</title>
	<meta charset="utf-8">
	<link href="${pageContext.request.contextPath}/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<style type="text/css">
		body,ul,ol,li,p,h1,h2,h3,h4,h5,h6,form,fieldset,table,td,img,div,dl,dt,dd,input{margin:0;padding: 0;border: 0;}
		body{font-size: 14px;overflow-x: hidden;min-width: 1197px;font-family: "Helvetica Neue", Helvetica, Microsoft Yahei, Hiragino Sans GB, WenQuanYi Micro Hei, sans-serif;}
		img{border:none;}
		ul,ol{list-style: none;}
		input,select,textarea{outline:none;}
		textarea{resize:none;}
		#seller-register-head .comWidth{
			height: 70px;
			display: flex;
			flex-flow: row nowrap;
			align-items: center;
		}
		#seller-register-head .comWidth span{
			padding-left: 30px;
			font-size: 30px;
		}

		.seller-register-content{
			overflow: hidden;
			background: #e93854 url(images/login/seller-login_bg.png) no-repeat right top;
			width: 100%;
			height: 600px;
		}

		.seller-register-content>div{
			width: 660px;
			height: 560px;
			background: #f8f8f8;
			margin-top: 30px;
			margin-left: 75px;
			border-radius: 6px;
			padding: 30px;
			box-sizing: border-box;
		}	
		.seller-register-content>div>form>label{
			display: block;
			margin-bottom: 20px;
			font-size: 20px;
			display: flex;
			align-items: center;
			justify-content: space-between;
			width: 420px;
		}	
		.seller-register-content>div>form>label>div{
			width: 140px;
			text-align: right;
		}
		.seller-register-content>div>form>label>div>span{
			color: red;
			padding-top: 8px;
			padding-right: 4px;
		}
		.seller-register-content>div>form>label>textarea{
			border: 1px solid #ccc;
			text-indent: 6px;
			padding:4px; 
			box-sizing: border-box;
		}
		.seller-register-content>div>form>label>input{
			border: 1px solid #ccc;
			width: 270px;
			height: 38px;
			line-height: 38px;
			text-indent: 6px;
			padding: 4px;
			box-sizing: border-box;
		}
		.seller-register-content>div>form>button{
			width: 120px;
			height: 40px;
			background: #e93854;
			outline: none;
			border: none;
			color: #fff;
			font-size: 20px;
			margin-left: 180px;
		}
	</style>
</head>
<body>
	
	<div id="seller-register-head">
		<div class="comWidth">
			<a href="index.html"><img src="${pageContext.request.contextPath}/images/logo.png"></a>
			<span>商家注册</span>
		</div>
	</div><!-- 注册界面头部 -->

	<section class="seller-register-content">
		<div>
			<form:form commandName="seller" method="post" action="${pageContext.request.contextPath}/seller/pc/regist">
				<label>
					<div><span class="red">*</span>店铺名称：</div>
					<form:input type="text" name="shopName" path="shopName" />
				</label>
				<label>
					<div><span class="red">*</span>店铺描述：</div>
					<form:textarea rows="5" cols="36" name="shopDescription" path="shopDescription" />
				</label>
				<label>
					<div><span class="red">*</span>身份证：</div>
					<form:input type="text" name="chinaID" path="chinaID" />
				</label>
				<label>
					<div><span class="red">*</span>真实姓名：</div>
					<form:input type="text" name="name" path="name" />
				</label>
				<label>
					<div><span class="red">*</span>手机号：</div>
					<form:input type="text" name="phone" path="phone" />
				</label>
				<label>
					<div><span class="red">*</span>登录密码：</div>
					<form:input type="password" name="password" path="password" />
				</label>
				<label>
					<div><span class="red">*</span>确认密码：</div>
					<input type="password" />
				</label>
				<button>立即注册</button>
			</form:form>
			<span>
			已有帐号，<a href="${pageContext.request.contextPath}/seller/pc/loginForm">直接登录</a>
			</span>
		</div>
		
	</section>

	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/messages_zh.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.public.js"></script>
	
</body>
</html>