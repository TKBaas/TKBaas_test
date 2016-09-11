$(function(){



	//轮播效果
	var i = 0;
	var clone = $(".img li").first().clone();	//克隆第一张置于最后li之后
	$(".img").append(clone);
	var size = $(".img li").length;

	for(var j = 0;j<size-1;j++){
		$(".nav .num").append('<li></li>');//动态加载索引图标
	}

	$(".num li").first().addClass("num_active");
	var imgWidth = $(".img li img").width();
	/*自动播放*/
	var timer = setInterval(function(){
		i++;
		move();
	},2000);

	$(".nav").hover(function(){
		clearInterval(timer);
	},function(){
		timer = setInterval(function(){
			i++;
			move();
		},2000);
	});

	function move(){
		if(i==-1){
			$(".img").css({"left":-(size-1)*imgWidth});
			i = size-2;
 		}
 		if(i==size){
			$(".img").css({left:0});
			i = 1;
		}
		$(".img").stop().animate({"left":-i*imgWidth},500);
		if(i==size-1){
			$(".nav .num li").eq(0).addClass('num_active').siblings().removeClass('num_active');
		}else{
			$(".num li").eq(i).addClass('num_active').siblings().removeClass('num_active');
		}
	}

	/*左右切换按钮事件*/
	$(".btn_r").click(function(){
		i++;
		move();
	});
	$(".btn_l").click(function(){
		i--;
		move();
	});
	/*鼠标划入索引点事件*/
	$(".nav .num li").hover(function(){
		var index = $(this).index();
	
		i = index;
 		$(".img").stop().animate({"left":-index*imgWidth},500);	
 		$(this).addClass("num_active").siblings().removeClass("num_active");
	});
	$(".nav").hover(function(){
		$('.nav .btn').fadeIn(500);
	},function(){
		$('.nav .btn').fadeOut(500);
	});

	/*轮播右侧手风琴广告*/
	$('#carousel .right-ad-list>li').hover(function(){
		$(this).stop().animate({
			height:210,
			opacity:1
		},500).siblings('li').stop().animate({
			height:85,
			opacity:0.7
		},500);
	},function(){
		$(this).stop().animate({
			height:210,
			opacity:1
		},500);
	});

	//滑动到一定距离时出现商品
	$(window).scroll(function(){
		if($(window).scrollTop() >= 1100){
			$('#main-content-two').animate({opacity:1},500);
		}
	    if($(window).scrollTop() >= 1700){
			$('#main-content-three').animate({opacity:1},500);
		}
	    if($(window).scrollTop() >= 2200){
			$('#main-content-four').animate({opacity:1},500);
		}
	    if($(window).scrollTop() >= 2600){
			$('#footer').animate({opacity:1},700);
		}
		console.log($(window).scrollTop());
	});

});

