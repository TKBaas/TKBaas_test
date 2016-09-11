<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="zh_cn">
<head>
	<title>地址管理</title>
	<meta charset="utf-8">
	<link href="${pageContext.request.contextPath}/images/logo_ico_min.ico" type=image/x-icon rel="shortcut icon"/> 
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
	<%-- <link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css"> --%>
	<link rel="stylesheet" href="http://libs.baidu.com-fontawesome/4.0.3/css/font-awesome.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.public.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/address.css">
	<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/address-setting.css" />
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/geo.js"></script>


</head>
<body>
<div class="top-bg">
	<jsp:include page="/WEB-INF/jsp/share/head.jsp"></jsp:include>

	<div id="main">
	    <div class="user-info comWidth clearfix">
	    	<div class="user-pic-div" >
	            <img class='user-pic' src="${pageContext.request.contextPath}/images/head-portrait/1229.jpg" alt="${user.username }"/>
	    	</div><!-- .user-pic end -->
	    	<div class="info-1">
		        <h3 class="username clearfix"><span><i class="fa fa-user"></i>${user.username }</span>  </h3>
		        <span class='phonenumber' title=' 手机号码 '><i class="fa fa-phone"></i> 手机号码：<em>${user.phone }</em></span> 
	        </div>
	    	<div class="info-2">
		        <h3 class="wallet clearfix"><span title=' 钱包 '><i class="fa fa-credit-card"></i> 钱包：<em>${user.money }</em> 元 </span>  </h3>
		        <span class='Recharge' title=' 充值 '><i class="fa fa-plus-circle"></i><a href="recharge.html"> 我要充值</a></span> 
	        </div>
	        <div id="info-detial">
	        	<a href="#"><h4><i class="fa fa-list"></i> 我的订单</h4></a>
	        	<a href="/TKBaas/address/pc/getList"><h4><i class="fa fa-send"></i> 收货地址</h4></a>
	        	<a href="/TKBaas/user/pc/getInfo"><h4><i class="fa fa-cog"></i> 个人设置</h4></a>

	        </div>
	    </div><!-- .user-info end -->
	</div><!-- #main end -->
</div>



<!-- 
增加地址HTML
<div id='AddressSetting'>
			<div class='addCon'>
			<div class='addCon-title'>添加收货地址</div>
			<div id='addClose'><i class='fa fa-close'></i></div>
			    <form action=' method='post' >
		        <p> 
		        <label class='info-label' for='receiver' >*收货人： </label>
				<input class='input-text'  type='text' name='receiver' id='receiver' placeholder='请输入收货人姓名' required>
		        </p>
		        <p> 
		        <label class='info-label' for='location' >*所在地区： </label>
					<select class='select' name='province' id='s1'>
							<option></option>
							</select>
							<select class='select' name='city' id='s2'>
							  <option></option>
							</select>
							<select class='select' name='town' id='s3'>
							  <option></option>
							</select>
					<input id='locations' name='locations' type='hidden' value='' />
		        </p>
		        <p> 
		        <label class='info-label' for='locationdetail' >*详细地址： </label>
				<input class='input-text'  type='text' name='locationdetail' id='locationdetail' placeholder='请输入详细地址' required>
		        </p>				
		        <p> 
		        <label class='info-label' for='phonenum' >*手机号码： </label>
				<input class='input-text'  type='text' name='phonenum' id='phonenum' placeholder='请输入号码' required>
		        </p>	

		        <p> 
		        <label class='info-label' for='e-mail' >*邮箱： </label>
				<input class='input-text'  type='email' name='e-mail' id='e-mail' placeholder='请输入邮箱' required>
		        </p>
		         <p> 
		        <label class='info-label' for='usermail' >*地址标签：</label>
				<input class='input-text'  type='text' name='address-label' id='address-label' placeholder='请输入标签'>
		        </p>	
		        <p> 
		        <input type='submit' value='保存收货地址' id='save-address' class='save-address' />  
		        </p>	
		      </form>  
      		</div>
</div>
 -->

<!-- 修改地址HTML
<div id='AddressSetting'>
			<div class='addCon'>
			<div class='addCon-title'>修改收货地址</div>
			<div id='addClose'><i class='fa fa-close'></i></div>
			    <form action=' method='post' >
			    <input id='addressID' name='addressID' type='hidden' value='address10001' />
		        <p> 
		        <label class='info-label' for='receiver' >*收货人： </label>
				<input class='input-text' value='陈永华' type='text' name='receiver' id='receiver' placeholder='请输入收货人姓名' required>
		        </p>
		        <p> 
		        <label class='info-label' for='location' >*所在地区： </label>
					<select class='select' name='province' id='s1'>
							<option></option>
							</select>
							<select class='select' name='city' id='s2'>
							  <option></option>
							</select>
							<select class='select' name='town' id='s3'>
							  <option></option>
							</select>
					<input id='locations' value='陈永华' name='locations' type='hidden' value='' />
		        </p>
		        <p> 
		        <label class='info-label' for='locationdetail' >*详细地址： </label>
				<input class='input-text' value='广东工业大学 生活西区 三栋 440'  type='text' name='locationdetail' id='locationdetail' placeholder='请输入详细地址' required>
		        </p>				
		        <p> 
		        <label class='info-label' for='phonenum' >*手机号码： </label>
				<input class='input-text' value="18813290346"  type='text' name='phonenum' id='phonenum' placeholder='请输入号码' required>
		        </p>	

		        <p> 
		        <label class='info-label' for='e-mail' >*邮箱： </label>
				<input class='input-text' value="917029221@qq.com"  type='email' name='e-mail' id='e-mail' placeholder='请输入邮箱' required>
		        </p>
		         <p> 
		        <label class='info-label' for='usermail' >*地址标签：</label>
				<input class='input-text' value="学校" type='text' name='address-label' id='address-label' placeholder='请输入标签'>
		        </p>	
		        <p> 
		        <input type='submit' value='保存修改' id='save-address' class='save-address' />  
		        </p>	
		      </form>  
      		</div>
</div>
 -->
<!-- 删除HTML
<div id='deleteDIV'>
	<div class='deleteCon'>
			<div class='addCon-title'>删除</div>
			<div id='addClose'><i class='fa fa-close'></i></div>
			<p class='deletetext'><i class='fa fa-exclamation-triangle'></i>您确定要删除该收货地址吗？</p>
			<form action=' method='post' >
				<button id='deleteAddressSure'>确认</button>
				<button id='deleteAddressCancel'>取消</button>
			</form>
	</div>
</div> -->






	<!-- 地址 -->
	<div class="address">
		<div class="add-address" id="add-address">新增收货地址 <i class="fa fa-plus"></i>
		</div>
		<span>您已创建1 个收货地址，最多可创建10个</span>
	</div>

	<div address-box class="address" >
		
		<c:forEach items="${addressResultPage.result }" var="address">
			<div class="adressi" id="${address.id }">
				<p>
					<c:choose>
					      <c:when test="${address.defaultAddress==true}">
							<h4 class="moren"><span>默认地址</span></h4>
					      </c:when>
					      <c:otherwise>
							<h4 class="moren"><span><a href="${pageContext.request.contextPath}/address/pc/updateDefAdd?addressId=${address.id}">设为默认地址</a></span></h4>
					      </c:otherwise>
					</c:choose>
				</p>
				<p>
					<span>收货人：</span>
					<b >${address.receiver }</b>
				</p>
				<p>
					<span>所在地区：</span>
					<b>${address.province }</b>&nbsp;
					<b>${address.city }</b>&nbsp;
					<b>${address.countyTown }</b>
				</p>
				<p>
					<span>街道：</span>
					<b>${address.street }</b>
				</p>
				<p>
					<span>详细地址：</span>
					<b>${address.detailsAddress }</b>
				</p>
				<p>
					<span>手机：</span><b>${address.phone }</b>
				</p>				
				<div class="address-setting" id="address10001setting">
					<a href="#"><i class="fa fa-wrench"></i> 设置</a>
				</div>
				<div class="address-delete">
						<a  href="#none"><i class="fa fa-close"></i> </a>
	             </div>
		   	</div>
		</c:forEach>
	</div>

	<jsp:include page="/WEB-INF/jsp/share/footer.jsp"></jsp:include>
</body>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/address-setting.js"></script>

</html>