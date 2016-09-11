<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh_cn">
<head>
	<title>TK商城首页</title>
	<meta charset="utf-8">
	<link href="/TKBaas/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="/TKBaas/css/bootstrap.min.css">
	<link rel="stylesheet" href="/TKBaas/css/font-awesome.min.css">
	<link rel="stylesheet" href="/TKBaas/css/style.public.css">
	<link rel="stylesheet" href="/TKBaas/css/home.css">
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>

	<div id="search-nav">
		<div class="comWidth clearfix">
			<div class="nav-brand fl"><a></a></div>
		    <form:form commandName="productSearchForm" method="post" action="/TKBaas/product/pc/getListByAll">
				<form:input type="text" name="key" id="key" placeholder="搜索商品" path="key"/>
				<form:input type="hidden" name="sort" id="sort" path="key" value="sales_desc"/>
				<form:input type="hidden" name="region" id="region" path="region" value=""/>
				<form:input type="hidden" name="left" id="left" path="left" value=""/>
				<form:input type="hidden" name="right" id="right" path="right" value=""/>
				<form:input type="hidden" name="currentPage" id="currentPage" path="currentPage" value="1"/>
				<form:input type="hidden" name="pageSize" id="pageSize" path="pageSize" value="30"/>
				<input type="submit" id="search-btn" value="搜索" name="search-btn">
			</form:form> 
			<%-- <form method="post" action="">
				<input type="text" name="search-input" placeholder="搜索商品" id="search-input">
				<input type="submit" name="search-btn" value="搜索" id="search-btn">
			</form>  --%>
		</div>
	</div><!-- 搜索框结束 -->

	<div id="shop-nav">
		<div class="nav-menu-layout">
			<div class="nav-menu-bg">
				<div class="comWidth">
					<div class="shop-nav-container">
						<div class="container-box">
							<div class="container-box-title fl"><a href="${pageContext.request.contextPath }/product/pc/home">首页</a></div><!-- 标题 -->
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

	<div id="carousel">
		<div class="comWidth clearfix">
		
			<div class="nav fl">
				<ul class="img">
					<li><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb01567971d388000b" target="_blank"><img src="/TKBaas/images/ad/1.jpg"></a></li>
					<li><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb015679772f550016" target="_blank" target="_blank"><img src="/TKBaas/images/ad/2.jpg"></a></li>
					<li><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb0156797a62110021" target="_blank" target="_blank"><img src="/TKBaas/images/ad/3.jpg"></a></li>
					<li><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb0156797f2f66002c" target="_blank" target="_blank"><img src="/TKBaas/images/ad/4.jpg"></a></li>
					<li><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb0156796a5ab80000" target="_blank" target="_blank"><img src="/TKBaas/images/ad/5.jpg"></a></li>
				</ul>
				<ul class="num">
				</ul>
				<div class="btn btn_l">&lt;</div>
				<div class="btn btn_r">&gt;</div>
			</div><!-- 轮播效果结束 -->
			<div class="ad-right">
				<ul class="right-ad-list">
					<li class="right-list-item first"><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb0156798e24630042" class="right-list-item-one"></a></li>
					<li class="right-list-item "><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb01567989989d0037"  class="right-list-item-two"></a></li>
					<li class="right-list-item last"><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb01567971d388000b"  class="right-list-item-three"></a></li>
				</ul>
			</div><!-- 导航右侧广告结束 -->
		</div>
	</div><!-- 轮播结束 -->

	<div id="main-content-one" class="main-content">
		<div class="comWidth">
			<div class="main-content-title">
				<h2>
					<i class="two-title-icon"></i>
					<span class="sub-title">热销水果</span>
					<span class="sub-describe"> / 应时而采，新鲜特供 / </span>
				</h2>
			</div>
			<div class="main-content-center-box">
				
				<div class="content-box-left"><a href="" target="_blank"><img src="/TKBaas/images/home-item-md/conten-large.jpg"></a></div>
				<div class="content-box-right">
					<div class="right-item-list">
						<c:forEach items="${hotSalesList }" var="product">
							
						<div class="item-list-model">
							<div class="item-list-content">
								<c:forEach items="${product.picture }" var="picture" begin="0" end="0">
								<a href="${pageContext.request.contextPath }/product/pc/getInfo?productId=${product.id }" target="_blank"><img src="${pageContext.request.contextPath}/imgs/${picture.pictureUrl}"></a>
								</c:forEach>
								<div>
									<h4>${product.name }</h4>
									<p><i>￥</i>${product.price }</p>
									<p>销量:<span>${product.sales }</span></p>
								</div>
							</div>
							<a href="${pageContext.request.contextPath }/cart/pc/addInCart?num=1&productId=${product.id }" target="_blank">
								<i class="fa fa-shopping-cart"></i>
								<span>加入购物车</span>
							</a>	
						</div>
						</c:forEach>
						
					</div>
				</div>
			</div>
		</div>
	</div><!-- 主要内容区域结束 -->

	<div id="main-content-activity">
		<div class="comWidth clearfix">
			<div class="content-activity-title">
				<h1>商城推荐<span> / 集市尖货，热卖抢手 / </span></h1>
			</div>
			<div class="content-activity-box">
				<c:forEach items="${recommendList }" var="product">
				<div class="content-box-item">
					<div class="content-activity-text">
						<h2><a href="${pageContext.request.contextPath }/product/pc/getInfo?productId=${product.id }">${product.name }</a></h2>
						<p>单果约400-510g 自营水果</p>
						<p class="money"><span>￥${product.price }</span></p>
						<a href="${pageContext.request.contextPath }/cart/pc/addInCart?num=1&productId=${product.id }" target="_blank">
							<i class="fa fa-shopping-cart"></i>
							<span>加入购物车</span>
						</a>
						<p class="text-commit">已评论<i>xxx</i></p>
					</div>
					<div class="activity-item-img">
						<c:forEach items="${product.picture }" var="picture" begin="0" end="0">
						<a href="${pageContext.request.contextPath }/product/pc/getInfo?productId=${product.id }" target="_blank" ><img src="${pageContext.request.contextPath}/imgs/${picture.pictureUrl}"></a>
						</c:forEach>
					</div>
				</div>
				</c:forEach>
			</div>
		</div>
	</div><!-- 主要内容部分一结束 -->

	<div id="main-content-two" class="main-content">
		<div class="comWidth">
			<div class="main-content-title">
				<h2>
					<i class="two-title-icon"></i>
					<span class="sub-title">南果缤纷</span>
					<span class="sub-describe"> / 应时而采，新鲜特供 / </span>
				</h2>
			</div>
			<div class="main-content-center-box">
				<div class="content-box-left"><a href="" target="_blank"><img src="/TKBaas/images/home-item-md/conten-large.jpg"></a></div>
				<div class="content-box-right">
					<div class="right-item-list">
						<c:forEach items="${southFruitList }" var="product">
						<div class="item-list-model">
							<div class="item-list-content">
								<c:forEach items="${product.picture}" var="picture" begin="0" end="0">
								<a href="${pageContext.request.contextPath }/product/pc/getInfo?productId=${product.id }" target="_blank"><img src="${pageContext.request.contextPath}/imgs/${picture.pictureUrl}"></a>
								</c:forEach>
								<div>
									<h4>${product.name }</h4>
									<p><i>￥</i>${product.price }</p>
									<p>销量:<span>${product.sales }</span></p>
								</div>
							</div>
							<a href="${pageContext.request.contextPath }/cart/pc/addInCart?num=1&productId=${product.id }" target="_blank">
								<i class="fa fa-shopping-cart"></i>
								<span>加入购物车</span>
							</a>	
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div><!-- 主要内容区域结束 -->

	<div id="main-content-three" class="main-content">
		<div class="comWidth">
			<div class="main-content-title">
				<h2>
					<i class="two-title-icon"></i>
					<span class="sub-title">西域果情</span>
					<span class="sub-describe"> / 应时而采，新鲜特供 / </span>
				</h2>
			</div>
			<div class="main-content-center-box">
				<div class="content-box-left"><a href="/TKBaas/product/pc/getInfo?productId=40289181567927cb015679e037f8004d" target="_blank"><img src="/TKBaas/images/home-item-md/conten-large.jpg"></a></div>
				<div class="content-box-right">
					<div class="right-item-list">
						<c:forEach items="${westFruitList}" var="product">
						<div class="item-list-model">
							<div class="item-list-content">
								<c:forEach items="${product.picture }" var="picture" begin="0" end="0">
								<a href="${pageContext.request.contextPath }/product/pc/getInfo?productId=${product.id }" target="_blank"><img src="${pageContext.request.contextPath}/imgs/${picture.pictureUrl}"></a>
								</c:forEach>
								<div>
									<h4>${product.name }</h4>
									<p><i>￥</i>${product.price }</p>
									<p>销量:<span>${product.sales }</span></p>
								</div>
							</div>
							<a href="${pageContext.request.contextPath }/cart/pc/addInCart?num=1&productId=${product.id }" target="_blank">
								<i class="fa fa-shopping-cart"></i>
								<span>加入购物车</span>
							</a>	
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div><!-- 主要内容区域结束 -->

	<div id="main-content-four" class="main-content">
		<div class="comWidth">
			<div class="main-content-title">
				<h2>
					<i class="two-title-icon"></i>
					<span class="sub-title">北果风光</span>
					<span class="sub-describe"> / 应时而采，新鲜特供 / </span>
				</h2>
			</div>
			<div class="main-content-center-box">
				<div class="content-box-left"><a href="" target="_blank"><img src="/TKBaas/images/home-item-md/conten-large.jpg"></a></div>
				<div class="content-box-right">
					<div class="right-item-list">
						<c:forEach items="${northFruitList }" var="product">
						<div class="item-list-model">
							<div class="item-list-content">
								<c:forEach items="${product.picture }" var="picture" begin="0" end="0">
								<a href="${pageContext.request.contextPath }/product/pc/getInfo?productId=${product.id }" target="_blank"><img src="${pageContext.request.contextPath}/imgs/${picture.pictureUrl}"></a>
								</c:forEach>
								<div>
									<h4>${product.name }</h4>
									<p><i>￥</i>${product.price }</p>
									<p>销量:<span>${product.sales }</span></p>
								</div>
							</div>
							<a href="##" target="_blank">
								<i class="fa fa-shopping-cart"></i>
								<span>加入购物车</span>
							</a>	
						</div>
						</c:forEach>
					</div>
				</div>
			</div>
		</div>
	</div><!-- 主要内容区域结束 -->

	<jsp:include page="/WEB-INF/jsp/share/footer.jsp"></jsp:include>


	<script type="text/javascript" src="/TKBaas/js/jquery.min.js"></script>
	<script type="text/javascript" src="/TKBaas/js/index.public.js"></script>
	<script type="text/javascript" src="/TKBaas/js/home.js"></script>
</body>
</html>