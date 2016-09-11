// 状态联动
// unpaid\unsent\unreceived\uncommented\commented\canceled

// statusString储存状态字段
var statusString = new Array();
	statusString[0] = 'unpaid';//待付款
	statusString[1] = "unsent";//待发货
	statusString[2] = "unreceived";//待收货
	statusString[3] = "uncommented";//等待评价
	statusString[4] = "commented";//已评价
	statusString[5] = "canceled";//已取消
	

//状态框的显示	
var statusHTML = new Array();
	statusHTML[0] = "<span class='order-status ftx-04'>等待付款</span><br>";
	statusHTML[1] = "<span class='order-status ftx-04'>待发货</span><br>";
	statusHTML[2] = "<span class='order-status ftx-04'>待收货</span><br>";
	statusHTML[3] = "<span class='order-status ftx-04'>等待评价</span> <br>";
	statusHTML[4] = "<span class='order-status ftx-03'>已评价</span><br>";
	statusHTML[5] = "<span class='order-status ftx-03'>已取消</span><br>";
var s1 = "<a href='javascript:void(0)' class='operate-bnt btn-pay'  >付款</a><br>";
var s2 = "<a href='javascript:void(0)'  class='operate-bnt btn-cancel  btn-again btn-again-show'  >取消订单</a><br>"
var s3 = "<a href='javascript:void(0)'  class='operate-bnt btn-comment btn-again btn-again-show'  >评价</a><br>";	
var s4 = "<a href='javascript:void(0)'  class='operate-bnt btn-delete btn-again btn-again-show'  >删除</a><br>";
	//操作框的显示	
var statusOperateHTML = new Array();
	statusOperateHTML[0] = s1 + s2;//待付款
	statusOperateHTML[1] = "";//待发货
	statusOperateHTML[2] = "";//待收货
	statusOperateHTML[3] = s3;//等待评价
	statusOperateHTML[4] = s4;//已评价
	statusOperateHTML[5] = s4;//已取消

appset ();
function appset () {

	//遍历获取订单状态值
	var statusinput = document.getElementsByClassName("order-status-input");
	// 获取状态框
	var statusDIV = document.getElementsByClassName("status");
	// 获取状态操作框
	var operateDIV = document.getElementsByClassName("operate");
		for (var i = 0; i < statusinput.length; i++) {
			for (var j = 0; j < statusString.length; j++) {
				if (statusString[j] == statusinput[i].value) {
					statusDIV[i].innerHTML = statusHTML[j];
					operateDIV[i].innerHTML = statusOperateHTML[j];
					break;
				}
			}

		}
}



// 弹窗
function openNew(htmlw){
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
		deleteDIV.innerHTML=htmlw;
		document.body.appendChild(deleteDIV);
	
	//获取显示框的宽和高
	var dHeight=deleteDIV.offsetHeight;
	var dWidth=deleteDIV.offsetWidth;
		//设置显示框的left和top
		deleteDIV.style.left=sWidth/2-dWidth/2+"px";
		deleteDIV.style.top=wHeight/2-dHeight/2+"px";
	//点击关闭按钮
	var oClose=document.getElementById("goodsClose");
	
		//点击登陆框以外的区域也可以关闭登陆框
		oClose.onclick=oMask.onclick=function(){
					document.body.removeChild(deleteDIV);
					document.body.removeChild(oMask);
					};

				};

// 给按钮提供事件


		var html1 = "<div class='deleteCon'>		<div class='addCon-title'>取消订单</div>		<div id='goodsClose'>&times;</div>		<div class='deleteText'>取消订单？</div>		<form action='’ method='post' type = 'submit' >	<input type='hidden' id='IDsubmit' />		<input id='deleteAddressSure' value='确认'>		</form>"
		var html2 = "<div class='deleteCon'><div class='addCon-title'>删除</div><div id='goodsClose'>&times;</div><div class=deleteText>删除所选订单？</div><form action=‘’ method='' > <input type='hidden' id='IDsubmit' />	<input id='deleteAddressSure' type = 'submit' value='删除'></form>"
			// 给删除添加增加点击事件


		 var BntCancel=document.getElementsByClassName('btn-cancel');
		 var BntDelete=document.getElementsByClassName("btn-delete");

		// 取消订单
		 for (var i = 0; i < BntCancel.length; i++) {
		 	BntCancel[i].onclick = function(){
						openNew(html1);
					// 把订单地址传给隐藏的input
							var IDsubmit = document.getElementById("IDsubmit");
							IDsubmit.value = (this.parentNode).parentNode.getElementsByTagName("input")[0].value;	
						var deleteAddressSure = document.getElementById("deleteAddressSure");
							deleteAddressSure.onclick = function () {
							var IDsubmit = document.getElementById("IDsubmit");
							var eee = document.getElementById(IDsubmit.value);
							eee.getElementsByTagName("input")[0].value = "canceled";
							appset();
							document.body.removeChild(document.getElementById("deleteDIV"));
							document.body.removeChild(document.getElementById("mask"));
								aaa();

									}
							return false;
					}
		 }
		
		function aaa(){
		 for (var i = 0; i < BntDelete.length; i++) {
		 	BntDelete[i].onclick = function(){
					openNew(html2);
					// 把订单地址传给隐藏的input
							var IDsubmit = document.getElementById("IDsubmit");
							IDsubmit.value = (this.parentNode).parentNode.getElementsByTagName("input")[0].value;						
							var deleteAddressSure = document.getElementById("deleteAddressSure");
							deleteAddressSure.onclick = function () {
								var IDsubmit = document.getElementById("IDsubmit");
								var eee = document.getElementById(IDsubmit.value);	
								deletegoods (eee);					
								document.body.removeChild(document.getElementById("deleteDIV"));
								document.body.removeChild(document.getElementById("mask"));
							
						}

					return false;
					}
		 }
        }
	aaa();
	function deletegoods (ele) {

		 		if (ele!= null){
	          		ele.parentNode.removeChild(ele); 
				}
		 	}



                   
                   
                      