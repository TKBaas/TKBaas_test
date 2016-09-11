<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html lang="zh_cn">
<head>
    <title>鲜果-搜索结果</title>
    <meta charset="utf-8">
    <link href="/TKBaas/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
    <link rel="stylesheet" href="/TKBaas/css/bootstrap.min.css">
    <link href="http://libs.baidu.com/fontawesome/4.0.3/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="/TKBaas/css/style.public.css">
    <link rel="stylesheet" href="/TKBaas/css/search.css">
    <style type="text/css">

</style>
</head>
<body onload="getOnLoadPage(1)">

    <jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>

    <div id="search-nav">
        <div class="comWidth clearfix">
            <div class="nav-brand fl"><a href="index.html"></a></div>
            <form method="post" action="/TKBaas/product/pc/getListByAll">
                <input type="text" name="key" placeholder="搜索商品" id="key" value="${key}">
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
                            <div class="container-box-title fl"><a href="#">首页</a></div><!-- 标题 -->
                            <div class="container-item-box fl">
                                <div class="menu-item-brand menu-item">
                                    <a href="#"><i class="fa fa-fire"></i> 种类</a>
                                    <div class="menu-content">
                                        <ul class="menu-sub-list">
                                            <li class="menu-sub-item-op"><a href="###">苹果</a></li>
                                            <li class="menu-sub-item-op"><a href="###">柠檬</a></li>
                                            <li class="menu-sub-item-op"><a href="###">梨</a></li>
                                            <li class="menu-sub-item-op"><a href="###">桃</a></li>
                                            <li class="menu-sub-item-op"><a href="###">火龙果</a></li>
                                            <li class="menu-sub-item-op"><a href="###">热带水果</a></li>
                                            <li class="menu-sub-item-op"><a href="###">奇异果猕猴桃</a></li>
                                        </ul>
                                    </div>
                                </div>
                                <div class="menu-item-fresh menu-item">
                                    <a href="#"><i class="fa fa-plane"></i> 产地</a>
                                    <div class="menu-content">
                                        <ul class="menu-sub-list">
                                            <li class="menu-sub-item-op"><a href="###">新疆</a></li>
                                            <li class="menu-sub-item-op"><a href="###">四川</a></li>
                                            <li class="menu-sub-item-op"><a href="###">山东</a></li>
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

    <div id="search-content">
        <div class="comWidth clearfix">
            <div class="search-box clearfix">
                <div class="filter-box">
                    <ul class="select">
                        <li class="select-result clearfix">
                            <dl>
                                <dt>已选：</dt>
                                <dd class="select-no"></dd> 
                                <span class="clear-all">清除</span>
                            </dl>
                        </li>
                        <li class="select-list">
                            <dl id="select1" class="clearfix">
                                <dt>品种：</dt>
                                <div class="dd-content" >
                                    <dd class="select-all selected"><a href="#">不限</a></dd>
                                    <dd><a href="#">苹果</a></dd>
                                    <dd><a href="#">梨</a></dd>
                                    <dd><a href="#">猕猴桃</a></dd>
                                    <dd><a href="#">樱桃</a></dd>
                                    <dd><a href="#">榴莲</a></dd>
                                    <dd><a href="#">石榴</a></dd>
                                    <dd><a href="#">葡萄</a></dd>
                                    <dd><a href="#">香蕉</a></dd>
                                    <dd><a href="#">西瓜</a></dd>
                                    <dd><a href="#">哈密瓜</a></dd>
                                    <dd><a href="#">柠檬</a></dd>
                                    <dd><a href="#">芒果</a></dd>
                                    <dd><a href="#">菠萝</a></dd>
                                    <dd><a href="#">橘子</a></dd>
                                    <dd><a href="#">桔子</a></dd>
                                    <dd><a href="#">橙子</a></dd>
                                    <dd><a href="#">龙眼</a></dd>
                                    <dd><a href="#">荔枝</a></dd>
                                    <dd><a href="#">山竹</a></dd>
                                </div>
                            </dl>
                        </li>
                        <li class="select-list">
                            <dl id="select2"  class="clearfix">
                                <dt>产地：</dt>
                                <div class="dd-content">
                                    <dd class="select-all selected"><a href="#">不限</a></dd>
                                    <dd><a href="#">广东</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">新疆</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">江西</a></dd>
                                    <dd><a href="#">广东</a></dd>
                                    <dd><a href="#">广东</a></dd>
                                    <dd><a href="#">广东</a></dd>
                                    <dd><a href="#">广东</a></dd>
                                    <dd><a href="#">广东
                                    </a></dd>
                                </div>
                            </dl>
                        </li>
                    </ul>
                </div><!-- 条件筛选结束 -->
                <div class="sort-box">
                    <div class="sort-row">
                        <ul class="sorts clearfix">
                            <li class="sort active"><a href="#">综合</a></li>
                            <li class="sort"><a href="javascript:getSort('sales desc')">销量</a></li>
                            <li class="sort"><a href="javascript:getSort('price')">价格从低到高</a></li>
                            <li class="sort"><a href="javascript:getSort('price desc')">价格从高到低</a></li>
                        </ul>
                    </div>
                </div><!-- 排序条件结束 -->
				
                <div  id="product-list_1" class="search-list-box">
                     <div id="prodcut-list_2" class="search-list">
                        
                    </div>
                </div><!-- 列表展示结束 -->

            </div>
        </div>
    </div><!-- 商品展列结束 -->

    <div id="pageNum">
	<!-- 显示分页 -->
	</div>


    <jsp:include page="/WEB-INF/jsp/share/footer.jsp"></jsp:include>


    <script type="text/javascript" src="/TKBaas/js/jquery.min.js"></script>
    <!-- 引入分页插件 -->
    <script type="text/javascript" src="/TKBaas/js/jquery.pagination.js"></script>
    <script type="text/javascript" src="/TKBaas/js/index.public.js"></script>
    <script type="text/javascript" src="/TKBaas/js/search.js"></script>
    <script type="text/javascript" src="/TKBaas/js/prototype-1.7.js"></script>
    <script type="text/javascript" src="/TKBaas/js/json2.js"></script>
    
    <script type="text/javascript">
      function getOnLoadPage(value){
 	   
       var key = document.getElementById("key").value;	  
 	   var url = "/TKBaas/product/pc/showListByAll";
 	   var param = "json={'pageSize':'30'"
 		         + ",'key':'"+ key +"'"
 		         + ",'sort':''"
 		         + ",'region':''"
 		         + ",'left':''"
 		         + ",'right':''"
 		         + ",'currentPage':''"
 		         + ",'jumpPage':'-1'"
 		         + "}";

 	   sendRequest(param , url);
    }
    
       function getSort(value){
    	   
    	   var url = "/TKBaas/product/pc/showListByAll";
     	   var param = "json={'pageSize':'30'"
     		         + ",'key':''"
     		         + ",'sort':'"+ value +"'"
     		         + ",'region':''"
     		         + ",'left':''"
     		         + ",'right':''"
     		         + ",'currentPage':''"
     		         + ",'jumpPage':'-1'"
     		         + "}";

     	   sendRequest(param , url);
       }
       
       function getPageNumber(value1){
    	   
    	   var pageSize = document.getElementById("pageSize").value;
    	   var key      = document.getElementById("key").value;
    	   var sort     = document.getElementById("sort").value;
    	   var region   = document.getElementById("region").value;
    	   var left     = document.getElementById("left").value;
    	   var right    = document.getElementById("right").value;
    	   
    	   var url = "/TKBaas/product/pc/showListByAll";
    	   var param = "json={'pageSize':'"   + pageSize + "'"
    		         + ",'key':'"    + key    + "'"
    		         + ",'sort':'"   + sort   + "'"
    		         + ",'region':'" + region + "'"
    		         + ",'left':'"   + left   + "'"
    		         + ",'right':'"  + right  + "'"
    		         + ",'currentPage':'" + value1 + "'"
    		         + ",'jumpPage':'-1'"
    		         + "}";

    	   sendRequest(param , url);
       } 
    
       function getJumpPage(){
    	  
    	   var pageSize = document.getElementById("pageSize").value;
    	   var key      = document.getElementById("key").value;
    	   var sort     = document.getElementById("sort").value;
    	   var region   = document.getElementById("region").value;
    	   var left     = document.getElementById("left").value;
    	   var right    = document.getElementById("right").value;
    	   var jumpPage = document.getElementById("jumpPage").value;
    	   var totalPage = document.getElementById("totalPage").value;
    	   
    	   if(parseInt(totalPage) < parseInt(jumpPage))jumpPage = totalPage;
    	   var url = "/TKBaas/product/pc/showListByAll";
    	   var param = "json={'pageSize':'"   + pageSize + "'"
    		         + ",'key':'"    + key    + "'"
    		         + ",'sort':'"   + sort   + "'"
    		         + ",'region':'" + region + "'"
    		         + ",'left':'"   + left   + "'"
    		         + ",'right':'"  + right  + "'"
    		         + ",'currentPage':'" + jumpPage + "'"
    		         + ",'jumpPage':'1'"
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
              var el = '';
    	      for(var i = 0; i < result.length; i ++){
    	    	  
    	    	  var picTemp = result[i].picture;
                  if(picTemp.length > 0) temp1 = picTemp[0].pictureUrl;
                  
    	    	  var temp = '<div class="item-list-model"><div class="item-list-content">'
                           + '<a href="##" target="_blank"><img src="/TKBaas/imgs/' + temp1 + '"></a>'
                           + '<div><h4>'     + result[i].name  + '</h4>'
                           + '<p><i>¥</i>'   + result[i].price + '</p>'
                           + '<p>销量:<span>' + result[i].sales  + '</span></p>'
                           + '</div></div><a href="##" target="_blank"><i class="fa fa-shopping-cart"></i>'
                           + '<span>加入购物车</span></a></div>';
                  el += temp;
    		  }
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
    	      pageNum += '<a class="page-num-none">共 '+obj.totalPage+' 页</a>';
    	      pageNum += '到第<input type="text" id="jumpPage" value="' 
    	              +  obj.jumpPage + '"/>页'
    	              +  '<a class="up-page-1" href="javascript:getJumpPage()" >确定</a>';
    	    	          
    	      document.getElementById("prodcut-list_2").innerHTML = el;
    	      document.getElementById("pageNum").innerHTML = pageNum;
       }
       
        $(function(){
            //插件获取分页内容，这里使用了jquery.pagination插件 详细使用可参考http://www.zhangxinxu.com/wordpress/2010/01/jquery-pagination-ajax%E5%88%86%E9%A1%B5%E6%8F%92%E4%BB%B6%E4%B8%AD%E6%96%87%E8%AF%A6%E8%A7%A3/

            var initPagination = function() {
                var num_entries = $(".search-list-box div.search-list div.item-list-model").length;
                // 创建分页
                $("#Pagination").pagination(num_entries, {
                    num_edge_entries: 1, //边缘页数
                    num_display_entries: 4, //主体页数
                    /*callback: pageselectCallback,*/
                    items_per_page:1 //每页显示1项
                });
             }();
     
            /*function pageselectCallback(page_index, jq){
                var new_content = $("#hiddenresult div.result:eq("+page_index+")").clone();
                $("#Searchresult").empty().append(new_content); //装载对应分页的内容
                return false;
            }*/

        });
    </script>
</body>
</html>
