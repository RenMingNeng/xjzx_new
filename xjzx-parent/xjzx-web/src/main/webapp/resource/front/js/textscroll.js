function AutoScroll(obj, timeout){

	var tt = 'doscroll("#scrollDiv")';

	var jdt_set_times = setInterval(tt,timeout);

	$(obj).bind({
		mouseenter : function(e) {
		clearInterval(jdt_set_times);
	},
	mouseleave : function(e) {
		jdt_set_times = setInterval(tt,timeout);
	},
	mousemove : function(e){
		
	},
	click : function(e) {
		
	}
	});
}

function doscroll(obj){
    $(obj).find("ul:first").animate({
        marginTop:"-38px"
    },500, function(){
    	//alert("OK");
        $(this).css({marginTop:"0px"}).find("li:first").appendTo(this);
    });
}