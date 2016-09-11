$(function(){
	//动态索引条件
	$("#search-content #select1 dd").on('click',function(){
		//第一个索引条件
		$(this).addClass('selected').siblings().removeClass('selected');
		if($(this).hasClass('select-all')) {
			$("#selectA").remove();
		} else{
			var clonethisA = $(this).clone();
			if($("#selectA").length > 0) {
				$("#selectA a").html($(this).text());
				$("#selectA a").append(' <span><i class="fa fa-close"></i></span');
				
			} else{
				$("#search-content .select-result dl").append(clonethisA.attr("id","selectA"));
				$("#selectA a").append(' <span><i class="fa fa-close"></i></span');
			}
		}
	});
	
	$("#search-content #select2 dd").on('click',function(){
		//第二个索引条件
		$(this).addClass('selected').siblings().removeClass('selected');
		

		if($(this).hasClass('select-all')) {
			$("#selectB").remove();
		} else{
			var clonethisB = $(this).clone();
			if($("#selectB").length > 0) {
				$("#selectB a").html($(this).text());
				$("#selectB a").append(' <span><i class="fa fa-close"></i></span');
			} else{
				$("#search-content .select-result dl").append(clonethisB.attr("id","selectB"));
				$("#selectB a").append(' <span><i class="fa fa-close"></i></span');
			}
		}
	});

	//删除全部筛选操作
	for(var i = 0; i < $("#search-content .clear-all dd").length;i++){
		alert("");
	}
	$("#search-content .clear-all").on('click',function(){
		$("#selectA").remove();
		$("#selectB").remove();
		$(".select-all").addClass("selected").siblings().removeClass("selected");

	});


	//删除筛选条件一节点操作
	$("#search-content .select-result").on('click','#selectA span',function(){
		$(this).parent().parent().remove();
		$("#search-content #select1 .select-all").addClass('selected').siblings().removeClass('selected');
		
	});
	//删除筛选条件二节点操作
	$("#search-content .select-result").on('click','#selectB span',function(){
		$(this).parent().parent().remove();
		$("#search-content #select2 .select-all").addClass('selected').siblings().removeClass('selected');
	});

	//排序结果事件
	$("#search-content ul.sorts li.sort").on('click',function(){
		var _this = $(this); //	保存当前li
		console.log(_this);
		$(this).addClass('active').siblings().removeClass('active');
	});
});

	