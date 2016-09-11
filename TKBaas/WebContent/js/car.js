
window.onload =appset();

function appset() {

//获取两个全选框
var selectallup = document.getElementById("toggle-checkboxes");
var selectalldown = document.getElementById("toggle-checkboxes_down");
// 获取所有选择框
var checkboxlist = document.getElementsByClassName("jdcheckbox");
//获取店铺
var shoplist = document.getElementsByClassName("cart-item-list");
//获取所有店铺的选择框列表
var shopicheckboxlist = new Array();
	for (var i = 0; i < shoplist.length; i++) {
		shopicheckboxlist[i] = shoplist[i].getElementsByClassName("jdcheckbox");
	}

var itemsingle =document.getElementsByClassName("item-single");
var sumPrice = document.getElementById("sumPrice");
var amountSum = document.getElementById("amountSum");	
var allthecount = document.getElementsByClassName("allthecount")[0];

    // 计数
	function SumSum () {
		var sum = 0;
		var sum2 = 0;
		 for (var i = 0; i < itemsingle.length; i++) {
		   	if (itemsingle[i].getElementsByClassName("jdcheckbox")[0].checked) {
		   		sum += Number(QuantityInput[i].value)*PerPrice[i];
		   		sum2=Number(QuantityInput[i].value)+sum2;
		   		}
		  	 }  
		sumPrice.innerHTML = sum.toFixed(2); 
		amountSum.innerHTML = sum2; 
		        return sum2;
		}




	//店铺内的选择
	for (var i = 0; i < shoplist.length; i++) {
		for (var j = 0; j < shopicheckboxlist[i].length; j++) {
			shopicheckboxlist[i][j].id=i+"and"+j;
			if (j==0) {
					 shopicheckboxlist[i][j].onchange = function() {
					 var iandj = this.id.split("and");
					 for (var i = 0; i < shopicheckboxlist[iandj[0]].length; i++) {
					 	shopicheckboxlist[iandj[0]][i].checked=this.checked;
					 }
				
					 goodsBG();

					}
				}else {
					shopicheckboxlist[i][j].onchange = function() {
					 var iandj = this.id.split("and");
					 var i;
					 for (i = 1; i < shopicheckboxlist[iandj[0]].length; i++) {
					 	if (shopicheckboxlist[iandj[0]][i].checked==false) {
								shopicheckboxlist[iandj[0]][0].checked = false;
								break;
							}
					 	}

					 if (i==shopicheckboxlist[iandj[0]].length) {
							shopicheckboxlist[iandj[0]][0].checked = selectalldown.checked = true;
					 	}
					
					 goodsBG();
					}
				}
		}
	}


//数量控件
var QuantityInput = document.getElementsByClassName("itxt");
var decrement = document.getElementsByClassName("decrement");
var increment = document.getElementsByClassName("increment");
var PerPriceDiv = document.getElementsByClassName("p-price");
var PerSumDiv = document.getElementsByClassName("p-sum");
var PerPrice = new Array();//单价num
var PerSum = new Array();//span

function Quantitystyle (thisindex) {
	if (Number(QuantityInput[thisindex].value)==1) {
		decrement[thisindex].className="decrement disabled";
		increment[thisindex].className="increment";
	}else if (Number(QuantityInput[thisindex].value)==200) {
		decrement[thisindex].className="decrement";
		increment[thisindex].className="increment disabled";
	}else{
		decrement[thisindex].className="decrement";
		increment[thisindex].className="increment";
	}
}

for (var i = 0; i < PerPriceDiv.length; i++) {
	PerPrice[i]=Number(PerPriceDiv[i].getElementsByTagName("span")[0].innerHTML);
	PerPriceDiv[i].getElementsByTagName("span")[0].innerHTML = PerPrice[i].toFixed(2);
	PerSum[i] = PerSumDiv[i].getElementsByTagName("span")[0];
	PerSum[i].innerHTML=(Number(QuantityInput[i].value)*PerPrice[i]).toFixed(2);
	
}

			


	for (var i = 0; i < QuantityInput.length; i++) {
		QuantityInput[i].id="QuantityInput"+i;
		decrement[i].id="decrement"+i;
		increment[i].id="increment"+i;
		PerPrice[i].id="PerPrice"+i;
		PerSum[i].id="PerSum"+i;
		decrement[i].onclick = function () {
			var thisindex = this.id.split("decrement")[1];
			if(Number(QuantityInput[thisindex].value)>1){
				QuantityInput[thisindex].value=QuantityInput[thisindex].value-1;
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);
			}else {
				QuantityInput[thisindex].value=1;
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);
				// alert("不能少于1");
			}
			SumSum();
		}

		increment[i].onclick = function () {
			var thisindex = this.id.split("increment")[1];
			if(Number(QuantityInput[thisindex].value)<200){
				QuantityInput[thisindex].value=Number(QuantityInput[thisindex].value)+1;
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);

			}else {
				QuantityInput[thisindex].value=200;
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);
			}

			SumSum();

		}

		QuantityInput[i].onchange = function () {
			var thisindex = this.id.split("QuantityInput")[1];
			if(Number(QuantityInput[thisindex].value)>=200){
				QuantityInput[thisindex].value=200;
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);
			}
			else if(Number(QuantityInput[thisindex].value)<200&&Number(QuantityInput[thisindex].value)>1){
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);
			}else if(Number(QuantityInput[thisindex].value)<=1){
				QuantityInput[thisindex].value=1;
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);
			}else {
				alert("请输入数字");
				QuantityInput[thisindex].value=1;
				PerSum[thisindex].innerHTML=(Number(QuantityInput[thisindex].value)*PerPrice[thisindex]).toFixed(2);
				Quantitystyle(thisindex);
			}
			SumSum();

		}
	}

goodsBG();


	// 全选
	selectallup.onclick = selectalldown.onclick = function() {
			for (var i = 0; i < checkboxlist.length; i++) {
				checkboxlist[i].checked=this.checked;
			}
					var sum = 0;
					var sum2 = 0;
					 for (var i = 0; i < itemsingle.length; i++) {
					 	
					   	if (itemsingle[i].getElementsByClassName("jdcheckbox")[0].checked) {
					   		itemsingle[i].style.backgroundColor="#fff4e8";
					   		sum += Number(QuantityInput[i].value)*PerPrice[i];
					   		sum2=Number(QuantityInput[i].value)+sum2;
					   		}else {
					   		itemsingle[i].style.backgroundColor="#FFF";
					   		}
					  	 }  
					sumPrice.innerHTML = sum.toFixed(2); 
					amountSum.innerHTML = sum2;
					// if(sum2==itemsingle.length){
					// 	// selectallup.checked = selectalldown.checked = true;
					// }else {
					// 	// selectallup.checked = selectalldown.checked = false;
					// }  
	}

	// 定义一个商品选中的样式and计数
	function goodsBG () {
		var sum = 0;
		var sum2 = 0;
		 for (var i = 0; i < itemsingle.length; i++) {
		 	
		   	if (itemsingle[i].getElementsByClassName("jdcheckbox")[0].checked) {
		   		itemsingle[i].style.backgroundColor="#fff4e8";
		   		sum += Number(QuantityInput[i].value)*PerPrice[i];
		   		sum2=Number(QuantityInput[i].value)+sum2;
		   		}else {
		   		itemsingle[i].style.backgroundColor="#FFF";
		   		}
		  	 }  
		sumPrice.innerHTML = sum.toFixed(2); 
		amountSum.innerHTML = sum2;
		if(sum2==itemsingle.length){
			selectallup.checked = selectalldown.checked = true;
		}else {
			selectallup.checked = selectalldown.checked = false;
		}  
		}

// 弹窗

//删除地址弹窗

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
		
		var html1 = "<div class='deleteCon'>		<div class='addCon-title'>删除</div>		<div id='goodsClose'>&times;</div>		<div class='deleteText'>删除商品？</div>		<form action=' method='post' >			<input id='deleteAddressSure' value='删除'>		</form>"
		var html2 = "<div class='deleteCon'><div class='addCon-title'>删除</div><div id='goodsClose'>&times;</div><div class=deleteText>删除所选商品？</div><form action=' method='' >	<input id='deleteAddressSure' value=删除></form>"
			// 给删除添加增加点击事件
		 var cartremove=document.getElementsByClassName('cart-remove');
		 var removebatch=document.getElementsByClassName("remove-batch")[0];
		 for (var i = 0; i < cartremove.length; i++) {
		 	cartremove[i].onclick = function(){
					openNew(html1);
					var ele = ((this.parentNode).parentNode).parentNode;//获取商品
					var deleteAddressSure = document.getElementById("deleteAddressSure");
						deleteAddressSure.onclick = function () {
							
							 deletegoods(ele);
							document.body.removeChild(document.getElementById("deleteDIV"));
							document.body.removeChild(document.getElementById("mask"));
							appset();
						}
					return false;
					}
		 }
		 	removebatch.onclick = function(){
		 			if (SumSum()==0) {
		 				alert("你没有选择任何商品");
		 				return false;
		 			}
					openNew(html2);
					var deleteAddressSure = document.getElementById("deleteAddressSure");
						deleteAddressSure.onclick = function () {
							for (var i = 0; i < itemsingle.length; i++) {
							   	if (itemsingle[i].getElementsByClassName("jdcheckbox")[0].checked) {
							   		deletegoods(itemsingle[i]);
							   		i--;
							   		}
							  	 }  
							document.body.removeChild(document.getElementById("deleteDIV"));
							document.body.removeChild(document.getElementById("mask"));
							appset();
						}


					return false;
					}



	function addlist () {
		var sssss=new String();
		 for (var i = 0; i < itemsingle.length; i++) {
		   	if (itemsingle[i].getElementsByClassName("jdcheckbox")[0].checked) {
		   		sssss+="<li class='ui-switchable-panel' style='float: left; display: list-item;'><div class='s-item'>"+Pimg[i].innerHTML+"</div></li>"
		   		}
		  	 }  
		  	 Ulswitchable.innerHTML=sssss;
			 allthecount.innerHTML=amountSum.innerHTML;
		}



// 底部预览
var amountsumText = document.getElementsByClassName("amount-sum")[0];
var Pimg = document.getElementsByClassName("p-img");
var KK=0;
var bb = amountsumText.getElementsByClassName("down")[0];
var Ulswitchable = document.getElementsByClassName("ui-switchable-panel-main")[0];
	amountsumText.onclick = function () {
		if (KK==0){
		addlist();
		 bb.className="up";
		 document.getElementsByClassName("selected-item-list")[0].style.display="block";

		 KK=1;
		}else {
		 bb.className="down";
		 document.getElementsByClassName("selected-item-list")[0].style.display="none";
		 KK=0;	
		}

	}

	var yourDiv = document.getElementById('ShowList');

	document.body.onclick = function(e){
		// if (yourDiv.style.display!= none) {
			 e = e || window.event;
			  var target = e.target || e.srcElement;
			  if(target != yourDiv&&amountsumText!= target){
			     yourDiv.style.display = 'none';
				 bb.className="down";
			  }
			}
	// }


var jdcart = document.getElementById("jd-cart");
var carttoolbar = document.getElementsByClassName("cart-toolbar")[0];
document.getElementsByTagName("body")[0].onscroll=function(){
        	 var a1 = window.scrollY;
        	 var a2 = document.documentElement.clientHeight ;
        	 var b1 = jdcart.offsetHeight;
        	 var b2 = jdcart.offsetTop;
        	 var b3 = 50;
        	 if ((a1+a2)<(b1+b2+b3)) {
        	 	carttoolbar.className ="cart-toolbar fixed-bottom";	
        	 }else {
        	 	carttoolbar.className ="cart-toolbar";	
        	 }
        }


}


	

	function deletegoods (ele) {
		
		deletePro(ele.id);
		var elePPP = ((ele.parentNode).parentNode).parentNode;//获得店铺
		// alert(elePPP.getElementsByClassName("item-single").length);
		 if(elePPP.getElementsByClassName("item-single").length==1){
		 		if (elePPP!= null){
	          elePPP.parentNode.removeChild(elePPP); 
				}
		 }else {
		 		if (elePPP!= null){
	          		ele.parentNode.removeChild(ele); 
				}
		 	
		 }
		 
		 
	}