var addrswitch = document.getElementsByClassName("addr-switch");
var uiscrollbarwrap = document.getElementsByClassName("ui-scrollbar-wrap")[0];
var displaceDIV = document.getElementsByClassName("displaceDIV")[0];


lineHeightselected();
setdefaultADDR();
seleteAddr();

//uiscrollbarwrap.getElementsByClassName("item-selected")[0].id;



//对默认地址进行选中并显示
	function addrdefaultselected () {
		 var addrdefault = document.getElementsByClassName("addr-default");
		for (var i = 0; i < addrdefault.length; i++) {
			if (addrdefault[i].innerHTML=="默认地址") {
				((addrdefault[i].parentNode).parentNode).getElementsByClassName("consignee-item")[0].className="consignee-item item-selected";
				uiscrollbarwrap.getElementsByClassName("item-selected")[0].parentNode.style.display = "list-item";
			return true;
			}
		}
			// 没有默认地址
			return false;
	}

//默认地址处理
	function setdefaultADDR () {
		if (uiscrollbarwrap.getElementsByClassName("item-selected").length==0) {
			return false;
		}
		 var PP = (uiscrollbarwrap.getElementsByClassName("item-selected")[0]).parentNode;
		 var setdefaultconsignee = uiscrollbarwrap.getElementsByClassName("setdefault-consignee");
		 for (var i = 0; i < setdefaultconsignee.length; i++) {
		 	setdefaultconsignee[i].style.display="inline-block";
		 }
		 PP.getElementsByClassName("setdefault-consignee")[0].style.display="none";
		 return true;
	}



// 使选中地址高亮和显示,如果没有就选默认，没有默认，就选第一个
	function lineHeightselected () {
		if (checkListLength()==0) {
			return false;
		}
		if(uiscrollbarwrap.getElementsByClassName("item-selected").length>0){
			uiscrollbarwrap.getElementsByClassName("item-selected")[0].parentNode.style.display = "list-item";
		}else {
			// 没有选中地址、对默认地址进行选中
			if (addrdefaultselected()==false) {
				//默认地址选中失败、选第一个地址
				var li0 = uiscrollbarwrap.getElementsByClassName("ui-switchable-panel")[0];
				    li0.getElementsByClassName("consignee-item")[0].className="consignee-item item-selected";
				    uiscrollbarwrap.getElementsByClassName("item-selected")[0].parentNode.style.display = "list-item";
			}
		}
	}

	// //判断是否有地址，如果没有，则显示新建
	function checkListLength () {
		var lilist = uiscrollbarwrap.getElementsByClassName("ui-switchable-panel");
		if (lilist.length==0) {
			displaceDIV.style.display = "block";
			addrswitch[0].className = "addr-switch switch-on hide";
			addrswitch[1].className = "addr-switch switch-0ff hide";	
			return 0;
		}else if (lilist.length==1) {
			displaceDIV.style.display = "none";
			addrswitch[0].className = "addr-switch switch-on hide";
			addrswitch[1].className = "addr-switch switch-0ff hide";
			return 1;
		}else {
			displaceDIV.style.display = "none";
			addrswitch[0].className = "addr-switch switch-on";
			addrswitch[1].className = "addr-switch switch-0ff hide";		
			return 2;
		}

	}

ADDRESS();



//地址选中函数
	function seleteAddr () {
		var AddrName = uiscrollbarwrap.getElementsByClassName("consignee-item");
		for (var i = 0; i < AddrName.length; i++) {
			AddrName[i].onclick = function () {
				uiscrollbarwrap.getElementsByClassName("item-selected")[0].className="consignee-item";
				this.className="consignee-item item-selected";
				ADDRESS();

			}
		}	
	}


addrswitch[0].onclick = function () {
	//显示与切换
	var lilist = document.getElementsByClassName("ui-switchable-panel");
	 addrswitch[0].className = "addr-switch switch-on hide";
	 addrswitch[1].className = "addr-switch switch-0ff";
	 uiscrollbarwrap.className = "ui-scrollbar-wrap changeaddr";
	
	for (var i = 0; i < lilist.length; i++) {
		lilist[i].style.display = "list-item";
	}

		 // 使选中地址显示在中间
		if (uiscrollbarwrap.getElementsByClassName("item-selected")[0].parentNode.offsetTop>80) {
			uiscrollbarwrap.scrollTop = document.getElementsByClassName("item-selected")[0].parentNode.offsetTop-60;
		}else {
			uiscrollbarwrap.scrollTop = 0;
		}
	return true;
}

	
addrswitch[1].onclick = function () {
	//显示与切换	 
	 var lilist = document.getElementsByClassName("ui-switchable-panel");
	 addrswitch[0].className = "addr-switch switch-on";
	 addrswitch[1].className = "addr-switch switch-0ff hide";
	 uiscrollbarwrap.className = "ui-scrollbar-wrap";
	
	for (var i = 0; i < lilist.length; i++) {
		lilist[i].style.display = "none";
	}

	uiscrollbarwrap.getElementsByClassName("item-selected")[0].parentNode.style.display = "list-item";

}

//地址联动函数
function ADDRESS () {
	var aa = uiscrollbarwrap.getElementsByClassName("item-selected")[0]
	var bb=aa.parentNode.getElementsByTagName("b");
	var bbs = new String();
		bbs = "寄送至："
	var addrtel = aa.parentNode.getElementsByClassName("addr-tel")[0].innerHTML;
	for (var i = 1; i < bb.length; i++) {
		bbs=bbs+" "+(bb[i].innerHTML);
	}
	document.getElementById("sendAddr").innerHTML = bbs;
	document.getElementById("sendMobile").innerHTML = "收货人："+aa.innerHTML +" "+addrtel;

}

				



// //数据联动
// //获取单价->数量—>总价->运费->需要的总共>
var pricei = document.getElementsByClassName("priceii");//单价数组
var quantityi = document.getElementsByClassName("quantityi");//数量数组
var goodsCount = document.getElementById("goodsCount");//总数
var warePriceId = document.getElementById("warePriceId");//总价
var sumPayPriceId = document.getElementById("sumPayPriceId");//总价+运费
var freightPrice = Number(document.getElementById("freightPrice").innerHTML.split("￥")[1]);//运费
var SUM = 0;
var NUM = 0;
for (var i = 0; i < pricei.length; i++) {
	SUM += Number(pricei[i].innerHTML)*Number(quantityi[i].innerHTML);
	NUM += Number(quantityi[i].innerHTML);
}
goodsCount.innerHTML = NUM;
warePriceId.innerHTML = "￥"+SUM.toFixed(2);
sumPayPriceId.innerHTML = "￥"+(SUM+freightPrice).toFixed(2);


// 新建地址弹窗



// 删除地址弹窗



// 编辑地址弹窗



