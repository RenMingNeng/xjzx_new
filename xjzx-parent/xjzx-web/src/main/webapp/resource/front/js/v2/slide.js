
$(function() {
	var timer = null,
		num = 0,
		width = $("#peixun li").eq(0).width(),
		len = $("#peixun li").length;
	
	//右侧点击
	$('.left').click(function(){
		pevrPlay();
	});
	$('.right').click(function(){
		nextPlay();
	});
	$(".peixun-051").hover(function(){
		clearInterval(timer);
	},function(){
		timer = setInterval(nextPlay,5000);
	})
	timer = setInterval(nextPlay,5000);
	function nextPlay(){
		
		if( (num-7) == (-len) ){
			num = 1;
		}
		num--;
		$('#peixun').stop().animate({
			left : width * num
		});
	}
	function pevrPlay(){
		if( num == 0 ){
			num = -len + 6;
		}
		num++;
		$('#peixun').stop().animate({
			left : width * num
		});
	}
});
