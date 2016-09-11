<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html class="root61">
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta name="format-detection" content="telephone=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商品已成功加入购物车</title>
<link href="/TKBaas/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="/TKBaas/css/bootstrap.min.css">
	<link rel="stylesheet" href="/TKBaas/css/font-awesome.min.css">
	<link rel="stylesheet" href="/TKBaas/css/style.public.css">
	<link rel="stylesheet" href="/TKBaas/css/home.css">
	<link rel="stylesheet" type="text/css" href="/TKBaas/css/addSuccess.css">
	<link type="text/css" rel="stylesheet" href="/TKBaas/css/addSuccess_002.css">
</head>
<body>

<jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>
<div class="w">
	<span class="clr" style="height: 30px;">&nbsp;</span>
</div>

<div class="main">
	<div class="success-wrap">
		<div class="w" id="result">
			<div class="m succeed-box">
				<div class="mc success-cont">
					<div class="success-lcol">
						<div class="success-top">
							<h3 class="ftx-02">商品已成功加入购物车！</h3>
						</div>
						
					</div>

					<div class="success-btns">
						<div class="success-ad">
							<a href="#none"></a>
						</div>
						<div class="clr">
						</div>
						<div>
							<a class="btn-tobback" href="#" onclick="window.history.back();return false;">返回</a>
							<a class="btn-addtocart" href="/TKBaas/cart/pc/getUserProduct" id="GotoShoppingCart">去购物车结算</a>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>
	<footer id="footer" style = "opacity:1">
		<div class="comWidth">
			<ul class="footer-instruction clearfix">
				<li class="instruction">
					<i class="fa fa-check fa-3x"></i>
					<div class="instruction-text">
						<p class="text1">食品安全</p>
						<p class="text2">精选生鲜 严格质检</p>
					</div>
				</li>
				<li class="instruction">
					<i class="fa fa-truck fa-3x"></i>
					<div class="instruction-text">
						<p class="text1">全程冷链</p>
						<p class="text2">自营物流 安全可控</p>
					</div>
				</li>
				<li class="instruction">
					<i class="fa fa-leaf fa-3x"></i>
					<div class="instruction-text">
						<p class="text1">鲜活天然</p>
						<p class="text2">绿色生态 TK精选</p>
					</div>
				</li>
				<li class="instruction instruction-last">
					<i class="fa fa-map-pin fa-3x"></i>
					<div class="instruction-text">
						<p class="text1">产地直采</p>
						<p class="text2">限定产源 质量保证</p>
					</div>
				</li>
			</ul><!-- 底部头部结束 -->
			<div class="footer-main">
				<div class="footer-main-info fl"><img src="/TKBaas/images/qr-code.png"></div>
				<div class="footer-main-content clearfix">
					<dl class="content-lk">
						<dd>购物指南</dd>
						<dt><a href="#">购物流程</a></dt>
						<dt><a href="#">售后政策</a></dt>
						<dt><a href="#">支付方式</a></dt>
					</dl>
					<dl class="content-lk">
						<dd>配送方式</dd>
						<dt><a href="#">配送运费</a></dt>
						<dt><a href="#">配送时效</a></dt>
						<dt><a href="#">配送服务查询</a></dt>
					</dl>
					<dl class="content-lk">
						<dd>商家服务</dd>
						<dt><a href="#">商家入驻</a></dt>
						<dt><a href="#">平台规则</a></dt>
						<dt><a href="#">联系我们</a></dt>
					</dl>
					<dl class="content-lk">
						<dd>联系客服</dd>
						<dt><a href="#">客服时间0:00-24:00</a></dt>
						<dt><a href="#">在线客服</a></dt>
					</dl>
				</div>
			</div><!-- 底部主要部分结束 -->
			<div class="footer-footer">Copyright ? 2015-2016  拓科TK.com 版权所有</div>
		</div>
	</footer><!-- 底部导航结束 -->
	<script type="text/javascript" src="/TKBaas/js/jquery.min.js"></script>
	<script type="text/javascript" src="/TKBaas/js/index.public.js"></script>
	<script type="text/javascript" src="/TKBaas/js/home.js"></script>
</body>
</html>