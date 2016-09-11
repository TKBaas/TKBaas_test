<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<header id="header">
	<div class="comWidth clearfix">
		<ul class="fl">
			<li>欢迎 <c:if test="${user != null && user.username != null}"> <a href="/TKBaas/user/pc/getInfo">${user.username } </a></c:if> 来<a href="/TKBaas/product/pc/home">TK商城</a>!</li>
			<li id="login">
			<c:choose>
				<c:when test="${user == null }">
					<a href="/TKBaas/user/pc/loginForm">登陆</a>
				</c:when>
				<c:otherwise>
					<a href="/TKBaas/user/pc/loginout">退出</a>
				</c:otherwise>
			</c:choose>
			</li>
			<li id="sign-in"><a href="/TKBaas/user/pc/registForm">免费注册</a></li>
		</ul>
		<ul class="fr">
			<li><a href="/TKBaas/product/pc/home"><i class="fa fa-home"></i> 商城首页</a></li>
			<li><a href="/TKBaas/user/pc/getInfo"><i class="fa fa-user"></i> 个人中心</a></li>
			<li><a href="/TKBaas/cart/pc/getUserProduct"><i class="fa fa-shopping-cart"></i> 我的购物车</a></li>
		</ul>
	</div>
</header><!-- 头部导航结束 -->