<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    
        <title>
            我的京东--我的订单
        </title>
        <link type="text/css" rel="stylesheet" href="/TKBaas/css/myTips-css/a_002.css">
        <link type="text/css" rel="stylesheet" href="/TKBaas/css/myTips-css/Mytips.css">
        <link type="text/css" rel="stylesheet" href="/TKBaas/css/myTips-css/a.css" source="widget">
        <link type="text/css" rel="stylesheet" href="/TKBaas/css/myTips-css/common.css" source="widget">
        <link href="/TKBaas/css/myTips-css/tips.css" rel="stylesheet" charset="utf-8">
        <link rel="stylesheet" href="/TKBaas/css/search.css">
    </head>
    
    <body onload="getOnLoadPage()">
        
      
        
        <input type="hidden" id="userId" value="${userId}">
        
        <link type="text/css" rel="stylesheet" href="/TKBaas/css/myTips-css/myjd.css" source="combo">
        <div id="container">
            <div class="w">
                <div id="content">

                    <div id="main">
                        <!-- 大标题 -->
                        <div class="mod-main mod-comm mod-order" id="order01">
                            <div class="mt">
                                <h3>
                                    我的订单
                                </h3>
                                <div class="extra-r">
                                </div>
                            </div>
                        </div>

                        <div class="mod-main mod-comm lefta-box" id="order02">
                            <!-- 订单头 分类-->
                            <div class="mt">
                                <ul class="extra-l">
                                    <li class="fore1">
                                        <a href="javascript:getSelect('')" class="txt curr">
                                            全部订单
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:getSelect('unpaid')" id="ordertoPay""
                                        class="txt">
                                            待付款
                                        </a>
                                    </li>
                                    <li>
                                        <a href="javascript:getSelect('unreceived')"  class="txt">
                                            待收货
                                        </a>                                   
                                    </li>
                                    <li>
                                        <a href="javascript:getSelect('uncomment')" id="ordertoComment" target="_blank"
                                        class="txt" >
                                            待评价
                                        </a>
                                    </li>
                                    <li class="fore2 ">
                                    </li>
                                    <li class="fore2">
                                    </li>
                                </ul>
                                <div class="extra-r">
                                    <div class="search">
                                        <form method="post" action="/TKBaas/order/pc/searchUserOrder">
                                         <input type="text" name="key" placeholder="搜索商品" id="key" value="${key}">
                                         <input type="submit" name="search-btn" value="搜索" id="search-btn">
                                        </form>
                                    </div>
                                </div>
                            </div><!-- /订单头 分类-->


                            <div class="mc">

                                

                                    <!-- 订单一 -->
                                    <div id="order-list">
                                    </div>
                                     
                                     <div id="pageNum">
                                     </div>
                                 
                                
                                
                            </div>
                        </div>
                    </div>
                </div>

            </div>
        </div>

 <!-- unpaid\unsent\unreceived\uncommented\commented\canceled-->
<!-- <h2>操作弹窗</h2>
 弹窗 1

<div id='deleteDIV'>
    <div class='deleteCon'>
            <div class='addCon-title'>取消订单</div>
            <div id='goodsClose'>&times;</div>
            <div class=deleteText>取消订单？</div>
            <form action=' method='post' >
                    <input type='hidden' id='IDsubmit' />               

                <input id='deleteAddressSure' value="确认" type="submit">
            </form>
    </div> 
</div>
 弹窗 2
<div id='deleteDIV'>
    <div class='deleteCon'>
            <div class='addCon-title'>删除</div>
            <div id='goodsClose'>&times;</div>
            <div class=deleteText>确认删除？</div>
            <form action=' method='post' >
                    <input type='hidden' id='IDsubmit' />               
                <input id='deleteAddressSure' value="删除" type="submit">
            </form>
    </div> 
</div> -->
<style>
            
    #goodsClose{ 
      width:30px; 
      height:30px; 
      cursor:pointer; 
      position:absolute; 
      right:10px; 
      top:10px; 
      font-size: 25px;
      z-index: 1002
     }

    #deleteDIV{
      position:fixed;
      z-index:1001;
      width:300px;
      height: 220px; 
    }
    .deleteText{
        line-height: 60px;
        text-align: center;
        font-size: 25px;
        font-weight: 500;
        color: #ff6c00;
    }
    .addCon-title{
      background-color: #F3F3F3;
      height: 35px;
      line-height: 35px;
      font-size: 16px;
      padding-left:20px; 
      text-align: left;
    }
    .deleteCon{
        position: relative;
        width: 230px;
        border: 1px solid #CCC;
        padding-bottom: 30px;
        text-align: center;
        background-color: #FFF;
    }

    #mask{ 
      background-color:#ccc;
      opacity:0.5;
      filter: alpha(opacity=50); 
      position:absolute; 
      left:0;
      top:0;
      z-index:1000;
      }
    .deleteCon input{
        display: inline-block;
        width: 80px;
        height: 25px;
        line-height: 25px;
        border: 1px solid #CCC;
        text-align: center;
        font-size: 16px;
        color: #666;
        background-color: #DDD;
        cursor: pointer;
    }




</style>
<!-- 弹窗2 -->
<!-- <div id='deleteDIV'>
    <div class='deleteCon'>
            <div class='addCon-title'>取消订单</div>
            <div id='goodsClose'>&times;</div>
            <div class=deleteText>取消订单？</div>
            <form action=' method='' >
                <input id='deleteAddressSure' value=确认>
            </form>
    </div>  -->

    <script type="text/javascript" src = "/TKBaas/js/MyTips.js"></script>
    <script type="text/javascript" src="/TKBaas/js/prototype-1.7.js"></script>
    <script type="text/javascript" src="/TKBaas/js/json2.js"></script>
    
    <script type="text/javascript">
      
    function getOnLoadPage(){
    	
       var userId = document.getElementById("userId").value;	
       var key = document.getElementById("key").value;
  	   var url = "/TKBaas/order/pc/getSearchUserOrder";
  	   var param = "json={'pageSize':'30'"
  		         + ",'key':'" + key + "'"
  		         + ",'sort':''"
  		         + ",'region':''"
  		         + ",'left':''"
  		         + ",'right':''"
  		         + ",'currentPage':'1'"
  		         + ",'userId':'" + userId + "'"
  		         + "}";

  	   sendRequest(param , url);
     }
    
    function getSelect(select){
    	
       var userId = document.getElementById("userId").value;	
   	   var url = "/TKBaas/order/pc/getSearchUserOrder";
   	   var param = "json={'pageSize':'30'"
   		         + ",'key':'" + select + "'"
   		         + ",'sort':''"
   		         + ",'region':''"
   		         + ",'left':''"
   		         + ",'right':''"
   		         + ",'currentPage':'1'"
   		         + ",'userId':'" + userId + "'"
   		         + "}";

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
    
    function jsonResponse(request){
		  
	    var obj = request.responseText.evalJSON();
	    var result = obj.result;
        var el = '<table class="td-void order-tb"><colgroup><col class="number-col"><col class="consignee-col">'
               + '<col class="amount-col"><col class="status-col"><co l class="operate-col"></colgroup><thead>'
               + '<tr><th><div class="order-detail-txt ac">订单详情</div></th><th>收货人</th><th> 金额</th><th><div class="deal-state-cont" id="orderState">'
               + '<div class="state-txt">订单状态</div></div></th><th>操作</th></tr></thead>';
        
	      for(var i = 0; i < result.length; i ++){
	    	  
	    	 var picTemp = result[i].picture;
            
	    	  var temp = '<tbody id="'+ result[i].id +'"><input type="hidden" name="order-status-input" class="order-status-input" value="'+ result[i].id +'">'
                     + '<tr class="sep-row"><td colspan="5"></td></tr>'
                     + '<tr class="tr-th"><td colspan="5"><span class="gap"></span>'
                     + '<span class="dealtime" title="' + result[i].boughtDate + '">'
                     +  result[i].boughtDate + '</span>'
                     + '<span class="number"> 订单号：<a name="orderIdLinks"  target="_blank" href="#">'
                     +  result[i].id + '</a></span>'
                     + '<div class="tr-operate"><span class="order-shop"><span class="shop-txt">'
                     + result[i].shopName + '</span></span></div></td></tr>';
              
              var proItems = result[i].proItems;
              for(var j = 0; j < proItems.length; j ++){
            	  
            	  temp += '<tr class="tr-bd" ><td><div class="goods-item p-1817424920"><div class="p-img">'
                       +  '<a href="#"  target="_blank"><img class="" src="/TKBaas/imgs/' + proItems[j].product.picture 
                       + '" title="' + proItems[j].product.name + '" height="60" width="60"></a></div>'
                       + '<div class="p-msg"><div class="p-name"><a href="http://item.jd.com/1817424920.html" class="a-link" clstag="'
                       + ' target="_blank" title="' + proItems[j].product.name + '">'
                       + proItems[j].product.name + '</a></div></div></div>'
                       + '<div class="goods-number">x' + proItems[j].num + '</div><div class="clr"></div></td>';
                  if(j == 0){     
                   temp+= '<td rowspan="4"><div class="consignee tooltip"><span class="txt">'
                       + result[i].address.receiver + '</span><b></b>'
                       + '<div style="display: none;" class="prompt-01 prompt-02"><div class="pc"><strong>'
                       + result[i].address.receiver +'</strong><p>' + result[i].address.province + result[i].address.city + result[i].address.countyTown
                       + result[i].address.street + result[i].address.detailsAddress + '</p>'
                       + '<p>' + result[i].address.phone + '</p></div><div class="p-arrow p-arrow-left" ></div></div>'
                       + '</div></td>'
                       + '<td rowspan="4"><div class="amount"><span>总额 &#165;' + result[i].money + '</span><br><span class="ftx-13"> 在线支付</span></div></td>'
                       + '<td rowspan="4"><div class="status"></div><div><a href="#" class="tips-i-detail"> 订单详情</a></div></td>'
                       + '<td rowspan="4" ><input type="hidden" name="tips-operate-ID" value="' + result[i].id +'"><div class="operate"></div></td>';
                  }
                  temp += '</tr>';
              }
            
            temp += '</tbody>';
            el += temp;
		  }
	      el +=  '</table>'
	      el +=  '<input type="hidden"  id="pageSize" name="pageSize" value="'+ obj.pageSize+'"/>'
	         +   '<input type="hidden"  id="key"      name="key"      value="'+ obj.key+'"/>'
	         +   '<input type="hidden"  id="sort"     name="sort"     value="'+ obj.sort+'"/>'
	         +   '<input type="hidden"  id="region"   name="region"   value="'+ obj.region+'"/>'
	         +   '<input type="hidden"  id="left"     name="left"     value="'+ obj.left+'"/>'
	         +   '<input type="hidden"  id="right"    name="right"    value="'+ obj.right+'"/>'
	         +   '<input type="hidden"  id="totalPage" name="totalPage" value="'+ obj.totalPage+'"/>';
	      
	      var pageNum = '<div  class="page-ctrl-bar">';
	      if(obj.currentPage > 1){
	    	  pageNum += '<a class="up-page-1" href="javascript:getPageNumber('
	    	          +  (obj.currentPage - 1) 
	    	          + ')" ><上一页</a>';
	      }else{
	    	  pageNum += '<a class="page-ctrl-none"><上一页</a>'; 
	      }
	      
	      if(obj.currentPage < 6){
	    	  
	    	  var temp = 7;
	    	  
	    	  if(obj.totalPage < 7)temp = obj.totalPage;
	    	  
	    	  for(var j = 0; j < temp; j ++){
	    		  if(obj.currentPage != (j+1)){
	    			  pageNum += '<a href="javascript:getPageNumber('
	    		          + (j+1) +  ')" >' + (j+1) +'</a>';}
	    		  else{
	    			  pageNum += '<a class="page-num-none" >'
	    		              + (j+1) +'</a>';
	    		  }
	    	  }
	      }else{
	    	  pageNum += '<a href="javascript:getPageNumber(1)" >1</a>'
	                  +  '<a href="javascript:getPageNumber(2)" >2</a>'
	                  +  '<a class="page-num-none">...</a>';
	          
	          var temp = parseInt(obj.totalPage) - parseInt(obj.currentPage);
	    	  var iter = 5;
	          if(temp == 1)iter = 4;        
	          if(temp == 0)iter = 3;
	          
	    	  for(var j = 0; j < iter; j ++){
	    		  if(j == 2){
	    			  pageNum += '<a class="page-num-none" >' 
  		                  + (j + obj.currentPage -2) +'</a>';
	    		  }else{
	    			  pageNum += '<a href="javascript:getPageNumber('
	    			          + (j + obj.currentPage -2) +  ')" >' 
		                      + (j + obj.currentPage -2) +'</a>';
	    		  }
  		            
	    	  }
	    	  if(temp > 2)pageNum += '<a class="page-num-none">...</a>';
	      }
	      
	      if(parseInt(obj.currentPage) < parseInt(obj.totalPage)){
	    	  pageNum += '<a href="javascript:getPageNumber('
  	              + (obj.currentPage + 1)
  	              +  ')" >下一页></a>';
	      }else{
	    	  pageNum += '<a class="page-ctrl-none">下一页></a>';
	      }
	      pageNum += '</div>';
	      document.getElementById("order-list").innerHTML = el;
	      document.getElementById("pageNum").innerHTML = pageNum;
       }
    
    
    </script>
	<script>
	 window.onload = function(){
		 getOnLoadPage();
		 
	 };
	</script>
</body>     
</html>