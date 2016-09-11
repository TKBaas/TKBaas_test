<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<!-- saved from url=(0024)http://cart.jd.com/#none -->
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>我的购物车</title>
	<link href="${pageContext.request.contextPath}/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"> --%>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.public.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/cart-style0.css">
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/cart-style1.css">
</head>
<body>

	<jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>

	<div id="search-nav">
		<div class="comWidth clearfix">
			<div class="nav-brand fl"><a></a></div>
			<form method="post" action="">
				<input type="text" name="search-input" placeholder="搜索商品" id="search-input">
				<input type="submit" name="search-btn" value="搜索" id="search-btn">
			</form>
		</div>
	</div><!-- 搜索框结束 -->


<br>
<br>



<!-- main -->
<div id="container" class="cart">
	<div class="cart-warp">
		<div class="w">
			<form method="post" action="${pageContext.request.contextPath}/order/pc/fillOrder">
				<div id="jd-cart">
					<div class="cart-main cart-main-new">
						<!-- 表头 -->
						<div class="cart-thead">
							<div class="column t-checkbox"><!-- 勾选全部商品框 -->
								<div class="cart-checkbox">
									<input type="checkbox" name="toggle-checkboxes" id="toggle-checkboxes" class="jdcheckbox">
								</div>
								<label for="toggle-checkboxes">全选</label>
							</div>
							<div class="column t-goods">商品</div>
							<div class="column t-props"></div><!-- 道具 -->
							<div class="column t-price">单价(元)</div>
							<div class="column t-quantity">数量</div>
							<div class="column t-sum">小计(元)</div>
							<div class="column t-action">操作</div>
						</div><!-- /表头 -->
						
						<div id="cart-list">
							<!-- <input type="hidden" id="allSkuIds" value="10482025562,1566455774,10524347076">
							<input type="hidden" id="outSkus" value="">
							<input type="hidden" id="isLogin" value="1">
							<input type="hidden" id="isNoSearchStockState" value="0">
							<input type="hidden" id="isNoDD" value="0">
							<input type="hidden" id="isNoCoupon" value="0">
							<input type="hidden" id="isFavoriteDowngrade" value="0">
							<input type="hidden" id="isPriceNoticeDowngrade" value="0">
							<input type="hidden" id="isNoZYCoupon" value="0">
							<input type="hidden" id="isNoVenderFreight" value="0">
							<input type="hidden" id="hiddenLocationId">
							<input type="hidden" id="hiddenLocation">
							<input type="hidden" id="ids" value="">
							<input type="hidden" id="isNgsdg" value="0">
							修改数量之前的值
							<input type="hidden" id="changeBeforeValue" value="">
							<input type="hidden" id="changeBeforeId" value="">
							<input type="hidden" value="4" id="checkedCartState">
							<input type="hidden" value="93662,555429" id="venderIds">
							<input type="hidden" value="" id="zySkuCid">
							<input type="hidden" value="93662,555429" id="venderFreightIds">
							<input type="hidden" value="0,0" id="venderTotals">
							<input type="hidden" value="2" id="uclass"> -->
							
							<c:forEach items="${cart.sellerItem }" var="sellerItem"><!-- 循环店铺 -->
								<div class="cart-item-list" id="cart-item-list-01"><!-- 店铺1 -->
									<div class="cart-tbody">
										<div class="shop"><!-- 店铺 -->
		
											<div class="cart-checkbox"><!-- 勾选店铺内全部商品 -->
												<input type="checkbox" name="checkShop" class="jdcheckbox">
											</div>
		
											<span class="shop-txt"><!-- 商店信息 -->
											<a class="shop-name" href="#店铺主页" target="_blank" >${sellerItem.shopName }</a>
											</span>
										</div>
									
										<div class="item-list"><!-- 店铺单品列表 -->
											<c:forEach items="${sellerItem.proItem }" var="proItem" varStatus="itemIndex">
												<!-- 单品1-->
												<div class="item-single item-item " id="${proItem.product.id}">
													<div class="item-form">
														<!-- 勾选商品框 -->
														<div class="cell p-checkbox">
															<div class="cart-checkbox">
																<input type="checkbox" name="proItemIds" value="${proItem.id }" class="jdcheckbox" >
															</div>
														</div>
														<!-- 商品详细 -->
														<div class="cell p-goods">
															<div class="goods-item">
																<!-- 图片 -->	
																<div class="p-img">
																	<a href="#商品详情页" target="_blank" class="J_zyyhq_10482025562">
																		<c:forEach items="${proItem.product.picture }" var="pictureOne" begin="0" end="0">
																			<img width="80px" height="80px" alt="${proItem.product.name }" src="${pageContext.request.contextPath}/imgs/${pictureOne.tinyPictureUrl }">
																		</c:forEach>
																	</a>
																</div>
																<!-- 文字信息 -->
																<div class="item-msg">
																	<div class="p-name">
																		<a href="#商品详情页" target="_blank">${proItem.product.name }</a>
																	</div>
																</div>
															</div>
														</div>
														<!-- 道具格 （不能删）-->
														<div class="cell p-props p-props-new"></div>
														<!-- 单价 -->
														<div class="cell p-price">
															<span >${proItem.product.price }</span >
														</div>
														<!-- 数量与库存 -->
														<div class="cell p-quantity">
															<div class="quantity-form">
																<a href="javascript:decreProNum('${proItem.product.id}')"  class="decrement disabled">-</a><!-- 递减控件 -->
																<input autocomplete="off" type="text" class="itxt" value="${proItem.num}" >
																<a href="javascript:increProNum('${proItem.product.id}')"  class="increment">+</a><!-- 递增控件 -->
															</div>
															<div class="ac ftx-03 quantity-txt">
																有货
															</div>
														</div>
														<!-- 小计 -->
														<div class="cell p-sum">
															<span >00.00</span >
														</div>
														<!-- 删除 -->
														<div class="cell p-ops">
															<a class="cart-remove" href="javascript:deletePro('${proItem.product.id}');">删除</a>
														</div>
													</div>
												</div>
											</c:forEach>
										</div><!--/店铺单品列表 -->
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>

				<!-- 购物车底部弹窗 -->
				<div id="cart-floatbar">
					<div class="ui-ceilinglamp-1" style="width: 100%; height: 52px;">
						<div class="cart-toolbar fixed-bottom" style="width: 100%; height: 50px;">
							<div class="toolbar-wrap">
									<div  id="ShowList" class="selected-item-list " style="display: none;">
										<div class="selected-list normal-selected-list">
											<div class="selected-inner">
											<div class="selected-num">商品共<em><span class="allthecount">0</span></em>件</div>
											<div class="selected-cont">
												<a href="#none" class="prev disabled"></a>
												<a href="#none" class="next disabled"></a>
												<div class="cont ui-switchable-body" style="position: relative;">
													<ul class="ui-switchable-panel-main" style=" left: 0px; position: absolute;">
													
													</ul>
												</div>
											</div>
										</div>
										<a href="#none" class="arr" style="right: 368.531px;"></a>
									</div>


									</div>

									<div class="options-box">
									<div class="select-all"><!-- 勾选全部商品框 -->
										<div class="cart-checkbox">
											<input type="checkbox" id="toggle-checkboxes_down" name="toggle-checkboxes" class="jdcheckbox" >
										</div>
										<label for="toggle-checkboxes_down">全选</label>
									</div>
									<div class="operation">
										<a href="#" class="remove-batch">删除选中的商品</a>
									</div>
									<div class="clr">
									</div>

									<div class="toolbar-right">
										<div class="normal">
											<div class="comm-right">
												<!-- 结算按钮 -->
												<div class="btn-area">
													<!--
													<a href="javascript:toAccount(0)" onclick="return false;"  
													class="submit-btn" >去结算<b></b></a>
													-->
													<input type="submit" value="去结算" class="submit-btn"/>
												</div>

												<!-- 总价 -->
												<div class="price-sum">
													<div>
														<span class="txt">总价（不含运费）：</span>
														<span class="price sumPrice"><em>¥<span id="sumPrice">0.00</span></em></span>
														<br>
													</div>
												</div>

												<!-- 所选商品 -->
												<div class="amount-sum">
													已选择
													<em><span id="amountSum">0</span></em>件商品<b class="down" > ^ </b>
												</div>
												<div class="clr">
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div><!-- /购物车底部 -->
			</form><!-- form表单结束 -->
		</div>
	</div>
</div>

<!-- /main -->
<!-- 弹窗 1-->
<!-- <div id='deleteDIV'>
	<div class='deleteCon'>
			<div class='addCon-title'>删除</div>
			<div id='goodsClose'>&times;</div>
			<div class=deleteText>删除商品？</div>
			<form action=' method='post' >
				<input id='deleteAddressSure' value=删除>
			</form>
	</div> 
	-->
<!-- 弹窗2 -->
<!-- <div id='deleteDIV'>
	<div class='deleteCon'>
			<div class='addCon-title'>删除</div>
			<div id='goodsClose'>&times;</div>
			<div class=deleteText>删除所选商品？</div>
			<form action=' method='' >
				<input id='deleteAddressSure' value=删除>
			</form>
	</div>  -->


<br>

	<jsp:include page="/WEB-INF/jsp/share/footer.jsp"></jsp:include>	
</body>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/car.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/index.public.js"></script>
    <script type="text/javascript" src="/TKBaas/js/prototype-1.7.js"></script>
    <script type="text/javascript">
	   
	   function decreProNum(divId){
    	   
    	   var goodi = document.getElementById(divId);
     	   var num = Number(goodi.getElementsByTagName("input")[1].value); 
     	   
     	   var url = "/TKBaas/cart/pc/updateCart";
     	   var param = "json={'proId':'" + divId + "'"
     		         + ",'num':'"+ num + "'}";

     	   sendRequest(param , url);
       }
       
       function increProNum(divId){
    	   
    	  var goodi = document.getElementById(divId);
    	  var num = Number(goodi.getElementsByTagName("input")[1].value);

    	  var url = "/TKBaas/cart/pc/updateCart";
     	  var param = "json={'proId':'" + divId + "'"
     		         + ",'num':'"+ num + "'}";

     	   sendRequest(param , url);
       }
       
       function deletePro(proId){
    	   
    	   //alert(proId);
    	   var url = "/TKBaas/cart/pc/delIncart";
      	   var param = "json={'proId':'" + proId + "'}";
      	   sendRequest(param , url);
       }
       
       function sendRequest(param , url){
    	   new Ajax.Request(url,
    			   {
    				   method:"post",
    				   parameters:param,
    				   onSuccess:jsonResponse,
    				   onFailure:function(){
    					   alert("fail");
    				   }
    			   });
        }
       
       function jsonResponse(request){}	  
    </script>
</html>