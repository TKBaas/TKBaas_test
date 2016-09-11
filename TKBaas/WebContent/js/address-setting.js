
function openNew1(AddressSettingHTML){
	//获取页面的高度和宽度
	var sWidth=document.body.scrollWidth;
	var sHeight=document.body.scrollHeight;
	//获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;
	
	var oMask=document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);
	var AddressSetting=document.createElement("div");
		AddressSetting.id="AddressSetting";
		AddressSetting.innerHTML=AddressSettingHTML;
		document.body.appendChild(AddressSetting);
	
	//获取显示框的宽和高
	var dHeight=AddressSetting.offsetHeight;
	var dWidth=AddressSetting.offsetWidth;
		//设置显示框的left和top
		AddressSetting.style.left=sWidth/2-dWidth/2+"px";
		AddressSetting.style.top=wHeight/2-dHeight/2+"px";
	//点击关闭按钮
	var oClose=document.getElementById("addClose");
	
		//点击登陆框以外的区域也可以关闭登陆框
		oClose.onclick=oMask.onclick=function(){
					document.body.removeChild(AddressSetting);
					document.body.removeChild(oMask);
					};
				setup();

			function promptinfo()
			{
				var locations = document.getElementById('locations');
				var s1 = document.getElementById('s1');
				var s2 = document.getElementById('s2');
				var s3 = document.getElementById('s3');
				locations.value = s1.value + s2.value + s3.value;
			}
					};
		

function openNew2(AddressSettingHTML){
	//获取页面的高度和宽度
	var sWidth=document.body.scrollWidth;
	var sHeight=document.body.scrollHeight;
	//获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;
	
	var oMask=document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);
	var AddressSetting=document.createElement("div");
		AddressSetting.id="AddressSetting";
		AddressSetting.innerHTML=AddressSettingHTML;
		document.body.appendChild(AddressSetting);
	
	//获取显示框的宽和高
	var dHeight=AddressSetting.offsetHeight;
	var dWidth=AddressSetting.offsetWidth;
		//设置显示框的left和top
		AddressSetting.style.left=sWidth/2-dWidth/2+"px";
		AddressSetting.style.top=wHeight/2-dHeight/2+"px";
	//点击关闭按钮
	var oClose=document.getElementById("addClose");
	
		//点击登陆框以外的区域也可以关闭登陆框
		oClose.onclick=oMask.onclick=function(){
					document.body.removeChild(AddressSetting);
					document.body.removeChild(oMask);
					};
				setup();

};

function openNew3(AddressSettingHTML){
	//获取页面的高度和宽度
	var sWidth=document.body.scrollWidth;
	var sHeight=document.body.scrollHeight;
	//获取页面的可视区域高度和宽度
	var wHeight=document.documentElement.clientHeight;
	
	var oMask=document.createElement("div");
		oMask.id="mask";
		oMask.style.height=sHeight+"px";
		oMask.style.width=sWidth+"px";
		document.body.appendChild(oMask);


	var deleteDIV=document.createElement("div");
		deleteDIV.id="deleteDIV";
		deleteDIV.innerHTML=AddressSettingHTML;
		document.body.appendChild(deleteDIV);
	
	//获取显示框的宽和高
	var dHeight=deleteDIV.offsetHeight;
	var dWidth=deleteDIV.offsetWidth;
		//设置显示框的left和top
		deleteDIV.style.left=sWidth/2-dWidth/2+"px";
		deleteDIV.style.top=wHeight/2-dHeight/2+"px";
	//点击关闭按钮
	var oClose=document.getElementById("addClose");
	var deleteAddressCancel=document.getElementById("deleteAddressCancel");	
	
		//点击登陆框以外的区域也可以关闭登陆框
		oClose.onclick=oMask.onclick=deleteAddressCancel.onclick=function(){
					document.body.removeChild(deleteDIV);
					document.body.removeChild(oMask);
					};

				};
		





	window.onload=function(){
	var addAddressHTML="<div class='addCon'><div class='addCon-title'>添加收货地址</div><div id='addClose'><i class='fa fa-close'></i></div><form action='/TKBaas/address/pc/add' method='post'><p><label class='info-label' for='receiver'>*收货人：</label><input class='input-text' type='text' name='receiver' id='receiver' placeholder='请输入收货人姓名' required='required'></p><p><label class='info-label' for='location'>*所在地区：</label><select class='select' name='province' id='s1'><option></option></select><select class='select' name='city' id='s2'><option></option></select><select class='select' name='countyTown' id='s3'><option></option></select></p><p><label class='info-label'>*街道：</label><input class='input-text' type='text' placeholder='请输入街道' name='street' required='required'></p><p><label class='info-label' for='locationdetail'>*详细地址：</label><input class='input-text' type='text' name='detailsAddress' id='detailsAddress' placeholder='请输入详细地址' required='required'></p><p><label class='info-label' for='phonenum'>*手机号码：</label><input class='input-text' maxlength='11' type='text' name='phone' id='phone' placeholder='请输入号码' required='required'></p><p><input type='submit' value='保存收货地址' id='save-address' class='save-address'></p></form></div>"
	
	var setAddressHTML="<div class='addCon'><div class='addCon-title'>修改收货地址</div><div id='addClose'><i class='fa fa-close'></i></div><form action='/TKBaas/address/pc/update' method='post'><input id='addressID' name='addressId' type='hidden' value='address10001'><p><label class='info-label' for='receiver'>*收货人：</label><input class='input-text' value='${address.name}' type='text' name='receiver' id='receiver' placeholder='请输入收货人姓名' required></p><p><label class='info-label' for='location'>*所在地区：</label><select class='select' name='province' id='s1'><option></option></select><select class='select' name='city' id='s2'><option></option></select><select class='select' name='countyTown' id='s3'><option></option></select></p><p><label class='info-label'>*街道：</label><input class='input-text' type='text' placeholder='请输入街道' required name='street'></p><p><label class='info-label' for='locationdetail'>*详细地址：</label><input class='input-text' value='广东工业大学 生活西区 三栋 440' type='text' name='detailsAddress' id='locationdetail' placeholder='请输入详细地址' required></p><p><label class='info-label' for='phonenum'>*手机号码：</label><input class='input-text' value='18813290346' maxlength='11' type='text' name='phone' id='phonenum' placeholder='请输入号码' required name='phone'></p><p></p><p><input type='submit' value='保存修改' id='save-address' class='save-address'></p></form></div>"
	var deleteDIVHTML="<div class='deleteCon'>	<div class='addCon-title'>删除</div>		<div id='addClose'><i class='fa fa-close'></i></div>		<p class='deletetext'><i class='fa fa-exclamation-triangle'></i>您确定要删除该收货地址吗？</p>		<form action='/TKBaas/address/pc/delete' method='post' ><input type='hidden' id='addId' name='addressId'/>			<input type='submit' id='deleteAddressSure' value='确认'/>			<span id='deleteAddressCancel'>取消</span>		</form></div>"
			// 给添加增加点击事件
			var oBtn=document.getElementById("add-address");
				//点击登录按钮
				oBtn.onclick=function(){
					openNew1(addAddressHTML);
					return false;
					}
			// 给设置添加增加点击事件
		 var AddressSettingList=document.getElementsByClassName('address-setting');
		 for (var i = 0; i < AddressSettingList.length; i++) {
			
		 	AddressSettingList[i].onclick=function(){
					openNew2(setAddressHTML);
					var addressi=this.parentNode;
					var blist=addressi.getElementsByTagName("b");
					var inputlist=AddressSetting.getElementsByTagName("input");  
					inputlist[0].value=addressi.id;
					inputlist[1].value=blist[0].innerHTML;
					inputlist[2].value=blist[4].innerHTML;
					inputlist[3].value=blist[5].innerHTML;
					inputlist[4].value=blist[6].innerHTML;
					preselect(blist[1].innerHTML,blist[2].innerHTML,blist[3].innerHTML);
					return false;
					}
		 }

			// 给删除添加增加点击事件
		 var AddressdeleteList=document.getElementsByClassName('address-delete');
		 for (var i = 0; i < AddressdeleteList.length; i++) {
		 	AddressdeleteList[i].onclick=function(){
					openNew3(deleteDIVHTML);
					var addressi=this.parentNode;
					document.getElementById("addId").value=addressi.id;
					return false;
					}
		 }
		}