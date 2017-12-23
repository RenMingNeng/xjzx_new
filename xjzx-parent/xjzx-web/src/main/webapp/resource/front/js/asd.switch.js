	var currentPos = 0;
	var wrapLength = 0;
	var time_out = 10000;
	var autoPlay = true;
	
	var jdt_set_times;

	function switchPlay(){
		if(autoPlay){
			jdt_set_times = setInterval(right,time_out);
		}
	}
	
	function setupSwitcher(options){
		var json = jQuery.parseJSON(options);
		time_out = json.TimeOut;
		autoPlay = json.autoPlay;
		if(json.hasOwnProperty("currentPos")){
			currentPos = json.currentPos;
		}
		switchPlay();
	}
	
	function stepToPosition(position){
		jQuery(".switch-con").stop();
		jQuery(".switch-con").animate({"left": -wrapLength*position});
	}
	
	function setSelect(position){
		var des = jQuery('.swich-ul-indicator');
			des.children().each(function(index) {
				if (position === index) {
					des.children().eq(index).find('span').addClass('indicator-selected');
				} else {
					des.children().eq(index).find('span').removeClass('indicator-selected');
				}
			});
		currentPos = position;
	}
	
   jQuery(document).ready(function(){
	   wrapLength = jQuery(".swicth-wrap").width();
	   
       jQuery("#btn_r").click(function(){
			right();
        });
  
       jQuery("#btn_l").click(function(){
			left();
       });
	  
		jQuery('.swicth-wrap').bind( {
				mouseenter : function(e) {
				clearInterval(jdt_set_times);
			},
			mouseleave : function(e) {
				switchPlay();
			},
			mousemove : function(e){

			},
			click : function(e) {
				
			}
			});
			
		jQuery('.swich-ul-indicator li').bind( {
			mouseenter : function(e) {
				var aIndex = jQuery(this).index();
				setSelect(aIndex);
				stepToPosition(aIndex);
			},
			mouseleave : function(e) {
				
			},
			mousemove : function(e){
				
			},
			click : function(e) {
				
			}
			});
			
		stepToPosition(currentPos);
    });
	
	function right(){
		if(currentPos == jQuery(".switch-con").children().length - 1){
			currentPos = 0;
			jQuery(".switch-con").animate({"left": currentPos});
		}else{
			jQuery(".switch-con").animate({"left": -wrapLength*(currentPos + 1)});
			currentPos = currentPos + 1;
		}
		setSelect(currentPos);
	}
	
	function left(){
		if(currentPos > 0){
			jQuery(".switch-con").animate({"left": -wrapLength*(currentPos - 1)});
			currentPos = currentPos - 1;
		}
		setSelect(currentPos);
	}