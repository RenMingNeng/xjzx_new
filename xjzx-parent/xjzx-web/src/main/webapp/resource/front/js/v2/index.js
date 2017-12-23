$(".lanmu-3 li").click(function(){
  var index = $(this).index();
  var $parent = $(this).parents('.lanmu-3');
  console.log( $parent );
  $(this).addClass('active').siblings("li").removeClass('active');
  $parent.find('.list div').eq(index).show().siblings("div").hide();
})
$(".lanmu-1").hover(function(){
  clearInterval(timer);
},function(){
  timer = setInterval(autoPlay, 3000);
})
$(".lanmu-1 ol li").click(function(){
  var index = $(this).index();
  num = index;
  $(this).addClass('active').siblings('li').removeClass('active');
  $(".lanmu-1 ul li").eq(index).show().siblings('li').hide();
})
var timer = null;
var num = 0;
var len = $(".lanmu-1 ol li").length;
timer = setInterval(autoPlay, 3000);
$('.xuanfu-03').click(function(){
  $(this).parent().slideUp();
})
function autoPlay(){
  num++;
  if(num > len-1){
    num = 0;
  }
  $(".lanmu-1 ol li").eq(num).addClass('active').siblings('li').removeClass('active');
  $(".lanmu-1 ul li").eq(num).show().siblings('li').hide();
}