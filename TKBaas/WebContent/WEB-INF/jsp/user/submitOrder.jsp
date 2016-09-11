<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
    
    <head>
        <meta charset="UTF-8" />
        <title>
            订单结算页 -京东商城
        </title>
        <!--结算页面样式-->
        <link rel="stylesheet" href="/TKBaas/css/style.public.css">
	<link rel="stylesheet" href="/TKBaas/css/home.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bankList.css" charset="utf-8">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/a.css" source="widget">
        <link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/css/a_002.css">
        <link href="${pageContext.request.contextPath}/css/tips.css" rel="stylesheet" charset="utf-8">
        <link href="${pageContext.request.contextPath}/css/scrollbar.css" rel="stylesheet" charset="utf-8">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/cartcheck.css" charset="utf-8">
    </head>
    
    <body id="mainframe">
    	<jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>
    
        <form:form commandName="orderForm" method="post" action="${pageContext.request.contextPath}/order/pc/submitOrder"><!-- 表单 -->
        <div class="w w1 header clearfix">
            <div id="logo">
                <a href="http://www.jd.com/" class="link1" target="_blank">
                    <img src="${pageContext.request.contextPath}/images/cartcheck/logo-201305.png" alt="京东商城">
                </a>
            </div>

        </div>

   
        <!-- main -->
        <div id="container">
            <div id="content" class="w">
                <div class="checkout-tit">
                    <span class="tit-txt">
                        填写并核对订单信息
                    </span>
                </div>
                <!-- 地址信息 -->
                <div class="checkout-steps">

                    <div class="step-tit">
                        <h3>
                            收货人信息
                        </h3>
                        <div class="extra-r">
                            <a href="#none" class="ftx-05" >
                                新增收货地址
                            </a>
                        </div>
                    </div>
    <!-- 588//42 -->
                    <div class="step-cont">
                        <div id="consignee-addr" class="consignee-content">
                            <div style="position: relative; overflow: hidden; width: 938px; height: 42px; z-index: 10;" class="ui-scrollbar-wrap">
                                <div style="position: absolute; left: 0px; top: 0px; height: auto; "class="consignee-scrollbar">
                                    <div class="ui-scrollbar-main">
                                        <div class="consignee-scroll">
                                            <div style="height: auto; " class="consignee-cont consignee-off" id="consignee1" >
                                                <ul id="consignee-list">
                                                	<c:forEach items="${listAddress }" var="address">
	                                                    <li class="ui-switchable-panel ui-switchable-panel-selected" style="display: none;" >
	                                                        <div class="consignee-item" title="${address.receiver }" id="${address.id }">${address.receiver }
	                                                        </div>
	                                                        <div class="addr-detail">
	                                                            <span title="${address.province }&nbsp;${address.city }&nbsp;${address.countyTown }&nbsp;${address.street }&nbsp;${address.detailsAddress }" class="addr-info" >
	                                                                <b>${address.province }</b> <b>${address.city }</b> <b>${address.countyTown }</b> <b>${address.street }</b> <b>${address.detailsAddress }</b>
	                                                            </span>
	                                                            <span class="addr-tel">
	                                                                ${address.phone }
	                                                            </span>
	                                                            <span class="addr-default"></span>                                                        
	                                                        </div>
	                                                        <div class="op-btns">
	                                                            <a href="#none" class="ftx-05 setdefault-consignee" >
	                                                                设为默认地址
	                                                            </a>
	                                                            <a href="#none" class="ftx-05 edit-consignee" >
	                                                                编辑
	                                                            </a>
	                                                            <a href="#none" class="ftx-05 del-consignee" >
	                                                                删除
	                                                            </a>
	                                                        </div>
	                                                    </li>
													</c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </div>



                            </div>
                        </div>
                        <div class="displaceDIV item-selected" style="display:none;">
                                <a href="#">你还没没设置收货地址，请点击新增收货地址</a >
                        </div>
                        <div class="addr-switch switch-on" ><span>更多地址 》</span></div>
                        <div class="addr-switch switch-off hide" ><span>收起地址 《 </span></div>
                        <!--end -->
                    </div>

                    <div class="hr"></div>

                    <div id="shipAndSkuInfo">
                        <div id="payShipAndSkuInfo">
                            <!-- 支付方式 -->
                            <div class="step-tit">
                                <h3>
                                    支付方式
                                </h3>
                            </div>
                            <div class="step-cont">
                                <div class="payment-list" >
                                    <div class="list-cont">
                                        <ul id="payment-list">
                                            <input id="instalmentPlan" value="false" type="hidden">
                                            <li style="cursor: pointer;"></li>
                                            <li style="cursor: pointer;" ></li>
                                            <li style="cursor: pointer;" >
                                            </li>
                                            <li style="cursor: pointer;" >
                                                <div class="payment-item item-selected online-payment" >在线支付</div>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="hr"></div>

                            <div class="step-tit">
                                <h3>
                                    送货清单
                                </h3>
                                <div class="extra-r">
                                    <a href="${pageContext.request.contextPath}/cart/pc/getUserProduct" id="cartRetureUrl" class="return-edit ftx-05">返回修改购物车</a>
                                </div>
                            </div>


                          <!-- 店铺>单品（图片》名字》单价》数量》有货）>配送方式         -->
                            <div class="step-cont" id="skuPayAndShipment-cont">
                                <div class="shopping-lists" id="shopping-lists">
                                	<%
                                		pageContext.setAttribute("proIndex", 0);
                                	%>
                                	<c:forEach items="${listProduct.sellerItem }" var="sellerItem" ><!-- 一个商家项 -->
	                                    <div class="shopping-list ABTest">
	                                        <div class="goods-list">
	                                            <div class="goods-tit">
	                                                <h4 class="vendor_name_h" id="93662">
	                                            		商家：${sellerItem.shopName }
	                                                </h4>
	                                            </div>
	                                            <c:forEach items="${sellerItem.proItem }" var="proItem"><!-- 商家的一个商品项 -->
		                                            <form:input type="hidden" value="${proItem.id }" path="proItemIds[${proIndex}]"/>
		                                            <form:input type="hidden" value="${proItem.product.id }" path="proIds[${proIndex}]"/>
		                                            <!--单品开始-->
		                                            <div class="goods-items">
		                                                <!-- 单品1 -->
		                                                <div class="goods-item">
		                                                    <div class="p-img">
		                                                        <a target="_blank" href="http://item.jd.com/1566455774.html">
		                                                        	<c:forEach items="${proItem.product.picture }" var="pictureOne" begin="0" end="0">
																		<img width="80px" height="80px" alt="${proItem.product.name }" 
																		src="${pageContext.request.contextPath}/imgs/${pictureOne.tinyPictureUrl }">
																	</c:forEach>
		                                                        </a>
		                                                    </div>
		                                                    <div class="goods-msg">
		                                                        <div class="goods-msg-gel">
		                                                            <div class="p-name">
		                                                                <a href="http://item.jd.com/1566455774.html" target="_blank">
		                                                                	${proItem.product.name }
		                                                                </a>
		                                                            </div>
		                                                            <div class="p-price">
		                                                                 <strong class="jd-price">
		                                                                    	￥<span class="priceii">${proItem.product.price }</span>
		                                                                 </strong>
		                                                                 <span class="p-num">
		                                                                      x<span class="quantityi">${proItem.num }</span>
		                                                                 </span>
		                                                                 <span id="pre-state" class="p-state">
		                                                                      有货
		                                                                 </span>
		                                                            </div>
		                                                        </div>
		                                                    </div>
		                                                    <div class="clr">
		                                                    </div>
		                                                </div>
		                                                
		                                            </div>
		                                            <%
				                                		pageContext.setAttribute("proIndex", (Integer)pageContext.getAttribute("proIndex")+1);
				                                	%>
	                                            	<!--单品结束-->
		                                        </c:forEach>
	                                        </div>
	                                        <!--goods-list 结束-->
	
	                                        <!-- 配送方式 -->
	                                        <div class="dis-modes">
	                                            <div class="mode-item mode-tab">
	                                                <h4>配送方式</h4>
	                                                <div class="mode-tab-nav">
	                                                    <ul>
	                                                        <li class="mode-tab-item curr tips-hover">
	                                                            <span class="m-txt">
	                                                                快递运输
	                                                            </span>
	                                                            <!-- <b>
	                                                            </b> -->
	                                                        </li>
	                                                    </ul>
	                                                </div>
	                                            </div>
	                                        </div>
	                                        <div class="clr"></div>
	                                    </div>
	                                    <!--shopping-list 结束-->
	                                </c:forEach>

                                    <!--shopping-list 结束-->
                                    <br>
                                    <br>                                  
              
                                    <div class="clr">
                                    </div>
                                </div>
                                <!--shopping-lists 结束-->
                            </div>
                        </div>
                    </div>

                    <div class="hr">
                    </div>

                    <!-- 发票信息 -->
                    <div class="step-tit" id="invoice-step">
                        <h3>
                            发票信息
                        </h3>
                    </div>
                    <div class="step-content">
                        <div id="part-inv" class="invoice-cont">
                            <span class="mr10">
                                普通发票（纸质） &nbsp;
                            </span>
                            <span class="mr10">
                                个人 &nbsp;
                            </span>
                            <span class="mr10">
                                明细 &nbsp;
                            </span>
                            <a href="#none" class="ftx-05 invoice-edit" onclick="edit_Invoice()" clstag="pageclick|keycount|trade_201602181|16">
                                修改
                            </a>
                        </div>
                    </div>
                    <div class="clr">
                    </div>
                    <br />
                    <!-- /发票信息 -->

                </div>

                <div class="order-summary">
                    <div class="statistic fr">
                        <div class="list">
                            <span>
                                <em class="ftx-01" id="goodsCount">
                                    
                                </em>
                                件商品，总商品金额：
                            </span>
                            <em class="price" id="warePriceId"></em>
                        </div>
                        <div class="list">
                            <span>
                                <i class="freight-icon">
                                </i>
                                运费：
                            </span>
                            <em class="price" id="freightPriceId">
                                <font color="#000000" id="freightPrice">
                                    ￥0.00
                                </font>
                            </em>
                        </div>
                    </div>
                    <div class="clr">
                    </div>
                </div>

                <div class="trade-foot">
                    <div class="trade-foot-detail-com">
                        <div class="fc-price-info">
                            <span class="price-tit">
                                应付总额：
                            </span>
                            <span class="price-num" id="sumPayPriceId">
                            </span>
                        </div>

                        <div class="fc-consignee-info">
                            <span class="mr20" id="sendAddr">
                            </span>
                            <span id="sendMobile">
                            </span>
                        </div>
                    </div>

                    <!-- 支付密码 -->
                    <div id="checkout-floatbar" class="group">
                        <div class="ui-ceilinglamp checkout-buttons">
                            <div class="sticky-placeholder hide" style="display: none;">
                            </div>
                            <div class="sticky-wrap">
                                <div class="inner">
                                		<form:input type="hidden" value="" path="addressId" id="input1"/>
                                		<form:input type="hidden" value="" path="totalMoney" id="input2"/>
	                                    <input type="submit" class="checkout-submit" id="order-submit" value=" 提交订单">
                                    <!-- <div class="checkout-submit-tip" id="changeAreaAndPrice" style="display: none;">
                                        由于价格可能发生变化，请核对后再提交订单
                                    </div> -->
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
        </div>
        <!-- /main -->
        </div>
        </form:form>
        
    </body>
    <script type="text/javascript" src="/TKBaas/js/jquery.min.js"></script>
    <script src = "${pageContext.request.contextPath}/js/cartcheck.js"></script>
    <script>
	    $(function(){
	    	//获取到地址选中的div
    		var oDiv = $(".step-cont .consignee-scroll #consignee-list .consignee-item");
	   		var input1 = $("#checkout-floatbar .sticky-wrap #input1");
	   		var input2 = $("#checkout-floatbar .sticky-wrap #input2");
	   		
	   		//获取总数的价格
        	var totalMonentAll = $(".trade-foot .trade-foot-detail-com .fc-price-info span#sumPayPriceId")[0].innerHTML;
	   		var totalMonent = totalMonentAll.substring(1);
        	//获取选中地址的ID
        	var address = $(".step-cont .consignee-scroll #consignee-list .item-selected").attr("id");
        	
        	oDiv.on("click", function(){
        		address = $(".step-cont .consignee-scroll #consignee-list .item-selected").attr("id");
        		input1.val(address);
        	});
        	
       /*  	alert(address); */
        /* 	alert(totalMonent); */
        	
        	input2.val(totalMonent);
        	input1.val(address);
        	
        	/* console.log(input2.val());
        	console.log(input1.val()); */

        	
	    });
    	/* window.onload = function(){
    		//获取到地址选中的div
    		var oDiv = document.getElementById("checkout-floatbar").getElementsByClassName("consignee-item");
    		var input1 = document.getElementById("checkout-floatbar").getElementsByClassName("sticky-wrap")[0];
    		var input1Value = input1.getElementById("input1");
        	var input2 = document.getElementById("checkout-floatbar").getElementsByClassName("sticky-wrap")[0].getElementById("input2");
        	
        	//获取总数的价格
        	var totalMonent = document.getElementsByClassName("trade-foot").getElementById("sumPayPriceId")[0].value();
        	var address = document.getElementsByClassName("consignee-list").getElementsByClassName("item-selected")[0].id;
        	//获取选中地址的ID
        	oDiv.onclick = function(){
        		address = document.getElementsByClassName("consignee-list").getElementsByClassName("item-selected")[0].id;
        		input1.value = address;
        	};
        	
        	input2.value = totalMonent;
        	input1.value = address;
        	console.log(input2.value);
        	console.log(input1Value);
        	console.log(input1.value);
    	}; */
    	
    </script>
</html>