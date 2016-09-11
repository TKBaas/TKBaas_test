
function $(id){
    return typeof id==='string'?document.getElementById(id):id;
}

  // 获取所要切换的内容
  var lis=$('tabsul').getElementsByTagName('li');
  var div0=$('base-info-setting');
  var div1=$('count-info-setting');
  var infosetup=$('info-setup');
  var portraitproview=$('portrait-proview');
  var changepic=$('changepic');
window.onload=tab;
function tab(){
	var newPassword = document.getElementById("newPassword");
	var confword = document.getElementById("confword");
	var Msg = document.getElementById("message");
		newPassword.onkeyup=confword.onkeyup = function () {
			 if (confword.value!=newPassword.value&&newPassword.value) {
					Msg.innerHTML = "<div class='tipdiv'>输入不一致</div>";
			  } else{
					Msg.innerHTML = "";
			  }
		}
	
	
lis[0].onclick=function(){  
	lis[0].className="info-on";
	div0.className='info-appear';
		lis[1].className="";
	div1.className='info-hide';
    }

lis[1].onclick=function(){  
	lis[1].className="info-on";
	div1.className='info-appear';
	lis[0].className="";
	div0.className='info-hide';
    }
}


function seleteF(sss){
	
	var select=sss;
	if(select==0){
		lis[0].className="info-on";
		div0.className='info-appear';
			lis[1].className="";
		div1.className='info-hide';
	}else{
		lis[1].className="info-on";
		div1.className='info-appear';
		lis[0].className="";
		div0.className='info-hide';
	}
	
}

function changePassword(){
	   
	   var oldt = document.getElementById("oldPassword").value;
	   var newt = document.getElementById("newPassword").value;
	
	   var url = "/TKBaas/user/pc/changePasswd";
	   var param = "json={'oldPassword':'" + oldt + "'"
		         + ",'newPassword':'"      + newt    + "'"
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
    var error = '<div class="tipdiv">' + obj.error + '</div>';
    document.getElementById("oldPassword").value = "";
    document.getElementById("newPassword").value = "";
    document.getElementById("confword").value = "";
    document.getElementById("message").innerHTML = error;
}



//图片上传
$('upload').onchange=function(){
	var upload = document.getElementById('upload');
	var portrait = document.getElementById('portrait-proview');
	portrait.src = window.URL.createObjectURL(upload.files[0]);
}
