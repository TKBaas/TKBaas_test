.<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh_cn">
<head>
	<title>信息设置</title>
	<meta charset="utf-8">
	<link href="/TKBaas/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="/TKBaas/css/bootstrap.min.css">
	<link rel="stylesheet" href="/TKBaas/css/font-awesome.min.css">
	<link rel="stylesheet" href="/TKBaas/css/style.public.css">
	<link rel="stylesheet" href="/TKBaas/css/personalcenter.css">

</head>
<body>
<div class="top-bg">
	<jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>
	
<!-- 	<div id="search-nav">
		<div class="comWidth clearfix">
			<div class="nav-brand fl"><a></a></div>
			<form method="post" action="">
				<input type="type" name="search-input" placeholder="搜索商品" id="search-input">
				<input type="submit" name="search-btn" value="搜索" id="search-btn">
			</form>
		</div>
	</div><!-- 搜索框结束 -->

	<div id="main">
	    <div class="user-info comWidth clearfix">
	    	<div class="user-pic-div" >
	            <img class='user-pic' src="/TKBaas/images/head-portrait/1229.jpg" alt=""/>
	    	</div><!-- .user-pic end -->
	    	<div class="info-1">
		        <h3 class="username clearfix"><span><i class="fa fa-user"></i> 华华try</span>  </h3>
		        <span class='phonenumber' title=' 手机号码 '><i class="fa fa-phone"></i> 手机号码：<em>00000000000</em></span> 
	        </div>
	    	<div class="info-2">
		        <h3 class="wallet clearfix"><span title=' 钱包 '><i class="fa fa-credit-card"></i> 钱包：<em>1000</em> 元 </span>  </h3>
		        <span class='Recharge' title=' 充值 '><i class="fa fa-plus-circle"></i><a href="#"> 我要充值</a></span> 
	        </div>
	        <div id="info-detial">
	        	<a href="#"><h4><i class="fa fa-list"></i> 我的订单</h4></a>
	        	<a href="/TKBaas/address/pc/getList"><h4><i class="fa fa-send"></i> 收货地址</h4></a>
	        	<a href="/TKBaas/user/pc/getInfo"><h4><i class="fa fa-cog"></i> 个人设置</h4></a>
	        </div>
	    </div><!-- .user-info end -->
	</div><!-- #main end -->
</div>


	<div class="info-setting comWidth" id="info-setting" >
		<ul id="tabsul">
			<li class="info-on" ><h4> 基本信息</h4></li>
			<li ><h4> 账户信息</h4></li>
		</ul>
		<br>
		<br>
		
		<div id="tabsdiv">
			<div class="info-appear" id="base-info-setting">
			
				<form:form  commandName="user" action="/TKBaas/user/pc/changeInfo" method="post" enctype="multipart/form-data" >
				<div id="setting-portrait"  class="setting-portrait">
			      <label class="info-label" for="portrait" >头像： &nbsp;</label>
	              <img id='portrait-proview' src="/TKBaas/imgs/${user.displayPicture}" width="150px" height="150px" class="">
			    
			      <div>
			         <input type="file" title="上传头像" name="picture" id="upload" style="width:123px; height: 33px;  opacity:0;">
			         <div style="margin-top:-20px; margin-left: 40px; width:123px; height:33px;border: 1px solid #ccc; text-align: center;line-height: 33px; ">上传图片</div>				    
				  </div>
				</div>
			    <!-- 用户名 -->
			    <p>
			        <label class="info-label" for="username" > 用户名： &nbsp;</label>
			        <form:input type="text" name="username" path="username" id="username"  value="${user.username}" class="input-text" 
			        oninvalid="this.setCustomValidity('这里还没填写呢.');" /> 	
			    </p> 
			    <!-- 性别 -->
				<p>
				<label class="Gender info-label" >性别： &nbsp;</label>
		        	<div class="Gender-group">
		                <label  class="lh16"><input type="radio" hidefocus="true" value="保密"   name="sex" /> 保密</label>
		                <label  class="lh16"><input type="radio" hidefocus="true" value="男" checked="checked" name="sex" /> 男</label>
		                <label  class="lh16"><input type="radio" hidefocus="true" value="女"  name="sex" /> 女</label>
		            </div>
		        </p>
		        <p>
		        <input type="submit" value="确认更改" id="submit1" class="clearfix submit" />  
		        </p> 
		        </form:form>
			</div>

			<div  id="count-info-setting" class="info-hide">
				<!-- 手机号 -->
		        <p> 
		        <label class="info-label" for="userphonenum" > 手机号：</label>
		        <span class="info-label">${user.phone}</span>
		        </p>
				<!-- 用户密码 -->
		        <p> 
		        <label class="info-label" for="usermail" > 原密码：</label>
		        <input class="input-text"  type="password"  name="old-password" id="oldPassword" placeholder="旧密码" />
		        </p>
		        <!-- 新密码 -->
		        <p> 
		        <label class="info-label" for="usermail" > 新的密码： </label>
				<input class="input-text"  type="password" name="newPassword" id="newPassword" placeholder="新密码" />
		        </p>	
                
				<!-- 新密码确认 -->
		        <p> 
		        <label class="info-label" for="usermail" > 确认密码： </label>
				<input class="input-text"  type="password" name="confword" id="confword" placeholder="请确认密码" >
		        </p>
                
                <div id="message">
                
                </div>		        
	
		        <p> 
		        <input type="button" value="确认更改" id="submit2"  onclick="changePassword()" class="submit" />  
		        </p>	

      		</div>
		</div>
	</div>


	<jsp:include page="/WEB-INF/jsp/share/footer.jsp"></jsp:include>
	
</body>
	<script type="text/javascript" src="/TKBaas/js/setting.js"></script>
    <script type="text/javascript" src="/TKBaas/js/prototype-1.7.js"></script>
</html>