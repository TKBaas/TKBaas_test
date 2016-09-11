<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html lang="zh_cn">
<head>
	<title>鲜果-商品详情</title>
	<meta charset="utf-8">
	<link href="images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.public.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/item.detail.css">
	<style type="text/css">

</style>
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>
	
	<div id="search-nav">
		<div class="comWidth clearfix">
      <div class="nav-brand fl"><a href="index.html"></a></div>
			<form method="post" action="">
				<input type="text" name="search-input" placeholder="搜索商品" id="search-input">
				<input type="submit" name="search-btn" value="搜索" id="search-btn">
			</form>
		</div>
	</div><!-- 搜索框结束 -->

	<div id="shop-nav">
		<div class="nav-menu-layout">
			<div class="nav-menu-bg">
				<div class="comWidth">
					<div class="shop-nav-container">
						<div class="container-box">
							<div class="container-box-title fl"><a href="${pageContext.request.contextPath}/product/pc/home">首页</a></div><!-- 标题 -->
							<div class="container-item-box fl">
								<div class="menu-item-brand menu-item">
									<a href="#"><i class="fa fa-fire"></i> 种类</a>
									<div class="menu-content">
										<ul class="menu-sub-list">
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=苹果">苹果</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=橙子">橙子</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=车厘子/樱桃">车厘子/樱桃</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=芒果">芒果</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=柠檬">柠檬</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=柚">柚</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=石榴">石榴</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=山竹">山竹</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=哈密瓜">哈密瓜</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=蓝莓">蓝莓</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=西瓜">西瓜</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=梨">梨</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=水蜜桃">水蜜桃</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=牛油果">牛油果</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=火龙果">火龙果</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=百香果">百香果</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=热带水果">热带水果</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=奇异果/猕猴桃">奇异果/猕猴桃</a></li>
										</ul>
									</div>
								</div>
								<div class="menu-item-fresh menu-item">
									<a href="#"><i class="fa fa-plane"></i> 产地</a>
									<div class="menu-content">
										<ul class="menu-sub-list">
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=south">南果缤纷</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=west">西域果情</a></li>
											<li class="menu-sub-item-op"><a href="/TKBaas/product/pc/getListByKey?key=north">北果风情</a></li>
										</ul>
									</div>
								</div>
							</div><!-- 导航栏其他导航 -->
						</div>
					</div>
				</div>
			</div>
		</div>
	</div><!-- 导引框结束 -->

	<div id="items-box">
		<div class="comWidth">
			<div class="items-box-list clearfix">
				<div class="item-box-left fl">
					<!--产品参数开始-->
				  <div>
				    <div id="preview" class="spec-preview"> 
					    <span class="jqzoom">
					    	<c:forEach items="${product.picture }" var="picture" begin="0" end="0">
					    	<img jqimg="${pageContext.request.contextPath}/imgs/${picture.pictureUrl }" 
					    	src="${pageContext.request.contextPath}/imgs/${picture.tinyPictureUrl }" />
							</c:forEach>
					    </span> 
				    </div>
				    <!--缩图开始-->
				    <div class="spec-scroll">
				      <div class="items">
				        <ul>
				            <c:forEach items="${product.picture }" var="picture" begin="0" end="4">
					          <li><img alt="${product.name }" bimg="${pageContext.request.contextPath}/imgs/${picture.pictureUrl }" 
					          src="${pageContext.request.contextPath}/imgs/${picture.tinyPictureUrl }" onmousemove="preview(this);"></li>
							</c:forEach>
				        </ul>
				      </div>
				    </div>
				    <!--缩图结束-->
				  </div>
				  <!--产品参数结束-->
				</div>
				<div class="item-box-center fl">
					<div class="item-center-box">
						<h1>${product.name }</h1>
						<div class="summary">
							<div class="item-price">鲜 果 价： <span>￥${product.price }</span></div>
							<div class="item-sales">已销售：<span>${product.sales }</span></div>
							<div class="item-leave">剩余：<span>${product.store }</span></div>
						</div>
						<div class="choose-btn">
							<form class="choose-amount" action="/TKBaas/cart/pc/addInCart"> 
								<input type="text" name="num" value="1" class="item-num">
								<input type="hidden" name="productId" value="${product.id }" />
								<input type="submit" value="加入购物车" class="item-sub">
							</form>
						</div>
					</div>
				</div>
				<div class="item-box-right fl">
					<div class="shop-detail">
						<div class="shop-img">
							<a href="##" href="#" target="_blank"><!-- 进入商店 -->
								<img src="${pageContext.request.contextPath}/imgs/${product.seller.shopPicture}" style="width:200;height:100px;">
							</a>
						</div>
						<div class="shop-name">
							店铺名称：<span class="shop-name">${product.seller.shopName }</span>
						</div>
						<div class="shop-level">
							店铺星级：<span>
							<c:choose>
								<c:when test="${product.seller.grade == 5}">
								<img src="/TKBaas/images/commit-level/level5.png">
								</c:when>
								<c:when test="${product.seller['grade'] == 2}">
								<img src="/TKBaas/images/commit-level/level2.png">
								</c:when>
								<c:when test="${product.seller['grade'] == 3}">
								<img src="/TKBaas/images/commit-level/level3.png">
								</c:when>
								<c:when test="${product.seller['grade'] == 4}">
								<img src="/TKBaas/images/commit-level/level4.png">
								</c:when>
								<c:otherwise>
								<img src="/TKBaas/images/commit-level/level1.png">
								</c:otherwise>
							</c:choose>
							</span>
						</div>
						<div class="shop-phone">
							联系商家：<span>${product.seller.phone }</span></div>
					</div>
				</div>
			</div>
		</div>
	</div><!-- 中间商品详情结束 -->

	<div id="shop-recommend">
		<div class="comWidth">
			<div class="recommend-top">
				<a href="#none">人气单品</a>
			</div>
			<div class="recommend-shop-list">
				<ul class="shop-list-center clearfix">
					<c:forEach items="${popularList }" var="tem">
					<li>
						<a href="${pageContext.request.contextPath}/product/pc/getInfo?productId=${tem.id }" target="_blank" class="recommend-img">
							<c:forEach items="${tem.picture }" var="picture" begin="0" end="0">
								<img src="${pageContext.request.contextPath}/imgs/${picture.pictureUrl}">
							</c:forEach>
						</a>
						<a href="${pageContext.request.contextPath}/product/pc/getInfo?productId=${tem.id }" class="recommend-name">${tem.name }</a>
						<p class="recommend-price">￥<span>${tem.price }</span></p>
					</li>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>

	<div id="shop-judge">
		<div class="comWidth">
			<ul class="judge-tab">
				<li class="tab-item-text active">商品介绍</li>
				<li class="tab-item-commit ">商品评价</li>
			</ul>
			<div class="item-decoration-box tabcon">
				<div class="item-decoration-text"><p>${product.description }</p></div>
				<div class="item-decoration-img">
					
					<c:forEach items="${product.detailPicture }" var="ProDetailPicture">
						<div class="decoration-img-item">
							<img src="${pageContext.request.contextPath}/imgs/${ProDetailPicture.detailPicUrl}" >
						</div>
					</c:forEach>
				</div>
			</div>
			<div class="item-commit-box tabcon clearfix">
				
				<c:forEach items="${commenResultPage.result }" var="comment">
			        <dl class="commit-item">
			          <dt class="user-info">
			          		<img src="${pageContext.request.contextPath}/imgs/${comment.userPicture}">
			          		<b>${comment.username }</b>
			          </dt>
			          <dd class="detail-commit">
			            <div class="level-commit">
			            	<c:choose>
								<c:when test="${comment.grade == 5}">
								<img src="${pageContext.request.contextPath}/images/commit-level/level5.png">
								</c:when>
								<c:when test="${comment.grade == 4}">
								<img src="${pageContext.request.contextPath}/images/commit-level/level4.png">
								</c:when>
								<c:when test="${comment.grade == 3}">
								<img src="${pageContext.request.contextPath}/images/commit-level/level3.png">
								</c:when>
								<c:when test="${comment.grade == 2}">
								<img src="${pageContext.request.contextPath}/images/commit-level/level2.png">
								</c:when>
								<c:otherwise>
								<img src="${pageContext.request.contextPath}/images/commit-level/level1.png">
								</c:otherwise>
							</c:choose>
			            </div>
			            <div class="date-commit">
			            	<fmt:formatDate value="${comment.date }" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			            </div>
			          </dd>
			          <dd class="content-commit">${comment.comment }</dd>
			        </dl>
				</c:forEach>
		        
			</div>
		</div>
	</div>

	<jsp:include page="/WEB-INF/jsp/share/footer.jsp"></jsp:include>


	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<!-- 引入略缩图插件 -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.jqzoom.js"></script>	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.public.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/item.detail.js"></script>

	<script type="text/javascript">
		$(function(){


		});
	</script>
</body>
</html>