<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="jstl" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<link href="http://apps.bdimg.com/libs/fontawesome/4.4.0/css/font-awesome.min.css" rel="stylesheet">
<style type="text/css">
	body,ul,ol,li,p,h1,h2,h3,h4,h5,h6,form,fieldset,table,td,img,div,dl,dt,dd{margin:0;padding: 0;border: 0;}
	body{font-size: 14px;overflow-x: hidden;min-width: 1197px;font-family: "Helvetica Neue", Helvetica, Microsoft Yahei, Hiragino Sans GB, WenQuanYi Micro Hei, sans-serif;}
	img{border:none;}
	ul,li{
		list-style: none;
	}
	a{
		text-decoration:none;
		color: #fff; 
	}
	/*头部*/
	.manage-header{
		width: 100%;
		height: 64px;
		line-height: 64px;
		background: #2a3b4c;
	}
	.header-container{
		width: 1200px;
		margin: 0 auto;
		display: flex;
		flex-flow: row nowrap;
		justify-content: space-between;
	}
	.logo{
		display: flex;
		flex-flow: row nowrap;
	}
	.header-container .logo img{
		width: 175px;
		height: 64px;
		margin-right: 30px;
	}
	.header-container .logo p{
		font-size: 30px;
		color: #fff;
	}
	.header-container .signIn ul{
		width: 120px;
		display: flex;
		flex-flow: row nowrap;
		justify-content: space-between;
		align-items: center;

	}

	/*内容部分*/
	.manage-content{
		width: 1200px;
		margin:0 auto;
		padding-top: 40px;
		display: flex;
		flex-flow: row;
		justify-content: space-between;
	}


	.leftBar>div>ul>li>p{
		width: 196px;
		height: 40px;
		box-sizing: border-box;
		padding-left: 10px;
		background: #f2f2f2;
		border-radius: 4px;
		line-height: 40px;
		color: #4a515b;
		border: 1px solid #d5d5d5;
		cursor: pointer;
	}
	.leftBar>div>ul>li>ul{
		overflow: hidden;
		
	}
	.leftBar>div>ul>li>ul>li{
		width: 196px;
		height: 38px;
		line-height: 38px;
		border-radius: 4px;
		text-align: center;
	}
	.li_active{
		background: #6f7782;
	}
	.a_active{
		color: #fff !important;		
	}
	.leftBar>div>ul>li>ul>li:hover{
		background: #6f7782;
		
	}
	.leftBar>div>ul>li>ul>li:hover>a{
		color: #fff;
	}
	.leftBar>div>ul>li>ul>li>a{
		color: #4a515b;
	}
	.leftBar>div>ul>li>ul>li>a>i{
		padding-right: 4px;
	}
	.leftBar>div>ul>li>p>i:nth-child(1){
		padding-right: 8px;
	}
	.leftBar>div>ul>li>p:hover, .p_active{
		background: #464c56 !important;
		color: #fff !important;
	}

	.change_height{
		height: 0 !important;
		transition: 1s all ease-in-out;
	}
	.caret{
		margin-left: 80px;
		font-size: 20px;
	}

	
	.manage-content .rightContent{
		width: 80%;
		background: #f2f2f2;
		border-radius: 6px;
		overflow: hidden;
	}
	.rightContent{
		padding-bottom: 40px;
	}
	.bread-nav{
		width: 100%;
		height: 50px;
		background: #488c6c;
		font-size: 30px;
		line-height: 50px;
		padding-left: 40px;
		color: #fff;
		margin-bottom: 20px;
	}
	.orders-nav{
		width: 100%;
		height: 50px;
		background: #ccc;
		margin-top: -20px;

	}
	.orders-nav>ul{
		width: 70%;
		height: 50px;
		padding-left: 40px;
		display: flex;
		flex-flow: row nowrap;
		justify-content: space-between;
		align-items: center;
	}

	.right-content{
		width: 92%;
		box-sizing: border-box;
		margin: 20px auto 0;
		padding: 20px;
		background: #fff;
	}
	.right-content #uploadForm label ,.right-content #uploadForm div{
		display: block;
		margin-bottom: 20px;
	}
	.right-content #uploadForm span{
		display: inline-block;
		width: 100px;
		text-align: right;
		padding-right: 20px;
	}
	.right-content #uploadForm .file,.right-content #uploadForm .files{
		width: 72px;

	}
	.file_view{
		width: 160px;
		height: 160px;
		padding: 8px;
		box-sizing: border-box;
		border: 1px solid #ccc;
		margin-left: 150px;
		margin-top: 20px;
	}
	.file_view img,.files_view img{
		width: 144px;
		height: 144px;


	}
	.files_list{
		margin-left: 150px;
	}
	.files_view{
		display: inline-block !important;
		width: 160px;
		height: 160px;
		padding: 8px;
		box-sizing: border-box;
		border: 1px solid #ccc;
		margin-right: 10px;
	}
</style>
</head>
<body>
	<header class="manage-header" >
		<div class="header-container">
			<div class="logo">
				<img src="${pageContext.request.contextPath}/images/logo.png" alt="logo" title="logo">
				<p>后台管理系统</p>
			</div>
			<div class="signIn">
				<ul>
					<li><a href="">商家名字</a></li>
					<li><a href="">退出</a></li>
				</ul>
			</div>
		</div>	
	</header>
	<section class="manage-content">
		<div class="leftBar">
			<div>
				<ul>
					<li>
						<p class="ul-control"><i class="fa fa-home"></i>店铺总览</p>
					</li>
					<li>
						<p  class="ul-control"><i class="fa fa-envelope"></i>店铺信息<i class="fa fa-caret-down caret"></i></p>
						<ul class="change_height">
							<li><a href=""><i class="fa fa-user"></i>详细信息</a></li>
							<li><a href=""><i class="fa fa-newspaper-o"></i>我的钱包</a></li>
						</ul>
					</li>
					<li>
						<p  class="ul-control"><i class="fa fa-shopping-cart"></i>交易管理<i class="fa fa-caret-down caret"></i></p>
						<ul class="change_height">
							<li><a href=""><i class="fa fa-server"></i>订单管理</a></li>
							<li><a href=""><i class="fa fa-th"></i>评价管理</a></li>
						</ul>
					</li>
					<li>
						<p  class="ul-control p_active"><i class="fa fa-th-large"></i>商品管理<i class="fa fa-caret-down caret"></i></p>
						<ul class="">
							<li class="li_active"><a href="" class="a_active"><i class="fa fa-columns"></i>发布商品</a></li>
							<li><a href=""><i class="fa fa-star-half-o"></i>出售中商品</a></li>
						</ul>
					</li>
					
				</ul>
			</div>
		</div>
		<div class="rightContent">
			<div class="bread-nav">
				发布商品
			</div>
			<div class="right-content">
				<form:form commandName="product" id="uploadForm" action="${pageContext.request.contextPath}/product/pc/addProduct" method="post" enctype="multipart/form-data">
                	<label><span>商品名：</span><form:input type="text" name="goodsName" path="name" /></label>
                	<label><span>商品价格：</span><form:input type="text" name="goodsPrice" path="price" /></label>
                	<label><span>商品库存：</span><form:input type="text" name="goodsNum" path="store" /></label>
                	水果种类：
                	<form:select name="type" path="type">
                		<option value="苹果">苹果</option>
                		<option value="柠檬">柠檬</option>
                		<option value="猕猴桃">猕猴桃</option>
                		<option value="其他">其他</option>
                	</form:select>
                	
                	水果场地：
                	<form:select name="city" path="city">
                		<option value="南果缤纷">南果缤纷</option>
                		<option value="北果风光">北果风光</option>
                		<option value="西域国情">西域国情</option>
                	</form:select>
                	
                	<label><span>商品描述：</span><form:textarea cols="60" rows="4" path="description" /></label>
                	
                	
                	
                	<div><span>商品主页图一：</span><input type="file" class="file" name="pictures"></div>
                	<div><span>商品主页图二：</span><input type="file" class="file" name="pictures"></div>  
                	<div><span>商品主页图三：</span><input type="file" class="file" name="pictures"></div>
                	<div><span>商品主页图四：</span><input type="file" class="file" name="pictures"></div>
                	<div><span>商品主页图五：</span><input type="file" class="file" name="pictures"></div>
                	
                	<div><span>商品详情图一：</span><input type="file" class="file" name="pictures"></div>
                	<div><span>商品详情图二：</span><input type="file" class="file" name="pictures"></div>  
                	<div><span>商品详情图三：</span><input type="file" class="file" name="pictures"></div>
                	<div><span>商品详情图四：</span><input type="file" class="file" name="pictures"></div>
                	<div><span>商品详情图五：</span><input type="file" class="file" name="pictures"></div>
                	
                	<div class="files_list"></div>
                	<!-- <button>发布商品</button> -->
                	<input type="submit" value="发布商品" />
		        </form:form>     
			</div>
		</div>
	</section>


<script type="${pageContext.request.contextPath}/text/javascript" src="js/jquery.min.js"></script>
<script type="${pageContext.request.contextPath}/text/javascript">
	//left_bar
	$('.ul-control').click(function(){
		$(this).next().toggleClass('change_height');
	});

	//input files
	$('.file').on('change',function(){
		var str = "<div class='file_view'><img src='"+window.URL.createObjectURL($(this).get(0).files[0])+"'></div>";
		$(this).parent().append(str);
	});
	$('.files').on('change',function(){
		var fileList = this.files;
		var str = "";
		for( var i = 0 ; i < fileList.length ; i++ ){
			console.log(fileList[i]);
			str +="<div class='files_view'><img src='"+window.URL.createObjectURL(fileList[i])+"'></div>";
		}
		$('.files_list').append(str);

	});
</script>
</body>
</html>