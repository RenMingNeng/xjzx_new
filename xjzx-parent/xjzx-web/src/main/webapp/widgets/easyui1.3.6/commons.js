(function ($) {

  function S4() {
    return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
  }
  function nextId() {
    return "UUID-" + (S4() + S4() + "-" + S4() + "-" + S4() + "-" + S4() + "-" + S4() + S4() + S4());
  }
  function destroy(target) {
    $(target).dialog("destroy");
  }
  function getWindow(target) {
    if (typeof target == "string") {
      return document.getElementById(target);
    } else {
      return $(target).closest(".window-body");
    }
  }
  
  function showMask(){
    if($.fn.mask)$.mask();
  }
  
  function hideMask(){
    if($.fn.mask)$.mask.hide();
  }

  function getFrameWindow(frame) {
    return frame && typeof(frame) == 'object' && frame.tagName == 'IFRAME' && frame.contentWindow;
  }

  function getTpl(id, ctxjq) {
    try {
      var tpl = ctxjq("#" + id);
      if (tpl.length > 0) {
        var TEMPLATE = $.trim(tpl.html()).replace(/[\r\n]/g, "").replace(/\s+/g, " ");
        var reg = new RegExp("^(<!--)(.*)(-->)$");
        var match = TEMPLATE.match(reg);
        return match[2];
      }
    } catch (e) {
      throw new Error("获取模板错误！");
    }
  }

  function ajaxSuccess(dialog, options, rsp) {
    var dgContent = dialog.find("div.dialog-content");
    if (options.isIframe && !rsp) {
      dgContent.css("overflow", "hidden");
      var iframe = document.createElement("iframe");
      iframe.src = options.url;
      iframe.id = 'ifrm-' + options.winId;
      iframe.name = options.winId;
      iframe.style.width = "100%";
      iframe.style.height = "100%";
      iframe.style.border = "none";
      $(iframe).attr("frameborder","0");
      if (iframe.attachEvent) {
        iframe.attachEvent("onload", function () {
          hideMask();
          options.onComplete.call(dialog, window.top.jQuery, iframe);
        });
      } else {
        iframe.onload = function () {
          hideMask();
          options.onComplete.call(dialog, window.top.jQuery, iframe);
        };
      }
      dgContent[0].appendChild(iframe);
    } else {
      hideMask();
      dgContent.html(rsp);
      $.parser.parse(dialog);
      options.onComplete.call(dialog, window.top.jQuery);
      
    }
    if (!options.height) {
      dialog.dialog("center");
    }
  }

  $.window = function (options,jq) {
	if(window.top != self){
      if(window.top.jQuery && window.top.jQuery.window){
        return window.top.jQuery.window(options,window.jQuery);
      }
      throw "父窗口没有导入jQuery或定义window插件！";
    }
    
    if (!options.url && !options.contents && !options.tplRef) {
      top.$.messager.alert("提示", "缺少必要参数!(url or contents or tplRef)");
      return false;
    }
    //获取模板内容
    if (options.tplRef) {
      options.contents = getTpl(options.tplRef,jq||window.jQuery);
    }
    
    var windowId = nextId();
    if (options.winId) {
      windowId = options.winId;
    } else {
      options.winId = windowId;
    }
    options = $.extend({}, $.window.defaults, options || {});
    if (options.isMax) {
      options.draggable = false;
      options.closed = true;
    }
    var dialog = top.$('<div/>');
    if (options.target != 'body') {
      options.inline = true;
    }
    
    showMask();
    
    dialog.appendTo($(options.target));
    dialog.dialog($.extend({}, options, {
        onClose : function () {
          if (typeof options.onClose == "function") {
            options.onClose.call(dialog, $);
          }
          destroy(this);
        }
      }));

    if (options.contents) {
      ajaxSuccess(dialog, options, options.contents);
    } else {
      if (!options.isIframe) {
        $.ajax({
          url : options.url,
          type : options.ajaxType || "POST",
          data : options.data == null ? "" : options.data,
          success : function (rsp) {
            ajaxSuccess(dialog, options, rsp);
          }
        });
      } else {
        ajaxSuccess(dialog, options);
      }
    }

    dialog.attr("id", windowId);

    dialog.destroy = function () {
      destroy(this);
    };

    return dialog;
  };

/*  //一些工具方法
  $.window.util = {
    getTpl : getTpl, //获取模板内容
    getFrameWindow : getFrameWindow //根据iframe的dom对象获取iframe的window对象
  };*/

  $.window.defaults = $.extend({}, $.fn.dialog.defaults, {
      url : '',
      data : '',
      contents : '',
      isIframe:false,
      tplRef : '',
      ajaxType : "POST",
      target : 'body',
      height : 550,
      width : 700,
      collapsible : false,
      minimizable : false,
      maximizable : false,
      closable : true,
      modal : true,
      shadow : false,
      onComplete : function (topjQuery) {}
    });
})(jQuery);


/*-------------------------------------------------
 * easyui扩展类
 * -----------------------------------------------
 */
EasyUIExt = (function($,window){
  var thisObj = this;
  this.changed = false;
  function showMask(win){
    if(win && win.jQuery.fn.mask){
      win.jQuery.mask();
    }else{
      top.jQuery.mask();
    }
  };
  
  function hideMask(win){
    if(win && win.jQuery.fn.mask){
      win.jQuery.mask.hide();
    }else{
      top.jQuery.mask.hide();
    }
  };
  
  var ifmArr = window.top.$(window.top.document).data('_ifmArr');//(window.top._ifmArr && (window.top._ifmArr.length > 0)) ? window.top._ifmArr[window.top._ifmArr.length - 1] : {};
  if(!ifmArr){
    ifmArr = [];
    window.top.$(window.top.document).data('_ifmArr',ifmArr);
  }
  
  this.myOpener = (ifmArr && ifmArr.length > 0) ? ifmArr[ifmArr.length - 1] : {};

  this.iframeDialog = function(options){
    if(!options || !options.url){
      throw "缺少必要参数:'url'";
    }

    var dialog = null;
    options.isIframe = true;
    
    try{
      dialog = $.window($.extend({}, options, {
        onClose : function (jq) {
          var frame = $('iframe', dialog);
          
    	  try {
    		  if (frame.length > 0) {
    			  for ( var i = 0; i < frame.length; i++) {
    				  frame[i].src = '';
    				  frame[i].contentWindow.document.write('');
    				  frame[i].contentWindow.close();
    			  }
    			  frame.remove();
    			  if (navigator.userAgent.indexOf("MSIE") > 0) {// IE特有回收内存方法
    				  try {
    					  CollectGarbage();
    				  } catch (e) {
    				  }
    			  }
    		  }
    	  } catch (e) {
    	  }
          
          if(ifmArr.length > 0){
            ifmArr.pop();
          }

          if (typeof options.onClose == "function") {
            options.onClose.call(dialog, jq);
          }
        }
      }));
    }catch(e){
      alert(e);
      return null;
    }
    
    var item = {
      param : options.param || {},
      window : window,
      dialog : dialog
    };
    
    ifmArr.push(item);
    
    return dialog;
  };
  
  this.closeDialog = function(){
		var dialog = myOpener && myOpener.dialog;
		dialog && dialog.dialog('close');
	}
  
  //获取easyui控件类型
	this.getComponentType = function(component){
		var componentType = null;
		if(!component || component.length == 0){
			return null;
		}
		if( $.data(component[0],"datetimebox") ){
			componentType = "datetimebox";
		}else if( $.data(component[0],"datebox") ){
			componentType = "datebox";
		}else if( $.data(component[0],"combotree") ){
			componentType = "combotree";
		}else if( $.data(component[0],"combogrid") ){
			componentType = "combogrid";
		}else if( $.data(component[0],"combobox") ){
			componentType = "combobox";
		}else if( $.data(component[0],"numberspinner") ){
			componentType = "numberspinner";
		}else if( $.data(component[0],"timespinner") ){
			componentType = "timespinner";
		}else if( $.data(component[0],"searchbox") ){
			componentType = "searchbox";
		}else if( $.data(component[0],"messager") ){
			componentType = "messager";
		}else if( $.data(component[0],"numberbox") ){
			componentType = "numberbox";
		}else if( $.data(component[0],"spinner") ){
			componentType = "spinner";
		}else if( $.data(component[0],"combo") ){
			componentType = "combo";
		}else if( $.data(component[0],"propertygrid") ){
			componentType = "propertygrid";
		}else if( $.data(component[0],"treegrid") ){
			componentType = "treegrid";
		}else if( $.data(component[0],"dialog") ){
			componentType = "dialog";
		}else if( $.data(component[0],"slider") ){
			componentType = "slider";
		}else if( $.data(component[0],"splitbutton") ){
			componentType = "splitbutton";
		}else if( $.data(component[0],"menubutton") ){
			componentType = "menubutton";
		}else if( $.data(component[0],"tabs") ){
			componentType = "tabs";
		}else if( $.data(component[0],"validatebox") ){
			componentType = "validatebox";
		}else if( $.data(component[0],"accordion") ){
			componentType = "accordion";
		}else if( $.data(component[0],"datagrid") ){
			componentType = "datagrid";
		}else if( $.data(component[0],"tree") ){
			componentType = "tree";
		}else if( $.data(component[0],"window") ){
			componentType = "window";
		}else if( $.data(component[0],"calendar") ){
			componentType = "calendar";
		}else if( $.data(component[0],"menu") ){
			componentType = "menu";
		}else if( $.data(component[0],"linkbutton") ){
			componentType = "linkbutton";
		}else if( $.data(component[0],"progressbar") ){
			componentType = "progressbar";
		}else if( $.data(component[0],"tooltip") ){
			componentType = "tooltip";
		}else if( $.data(component[0],"panel") ){
			componentType = "panel";
		}else{
			componentType = component.attr("type");;
		}
		
		return componentType;
	};
	
	//easyui控件 通用赋值方法
	this.setComponentValue = function(component,value){
		var componentType = this.getComponentType(component);
		
		if( componentType == "combo" ){ //combo赋值
			component.combo("setValue",value);
		}else if( componentType == "combobox" ){ //combobox赋值
			component.combobox("setValue",value);
		}else if( componentType == "combotree" ){ //combotree赋值
			component.combotree("setValues",value.split(","));
		}else if( componentType == "combogrid" ){ //combogrid赋值
			component.combogrid("setValue",value);
		}else if( componentType == "datebox" ){ //datebox赋值
			component.datebox("setValue",value);
		}else if( componentType == "datetimebox" ){ //datetimebox赋值
			component.datetimebox("setValue",value);
		}else if( componentType == "numberbox" ){ //numberbox赋值
			component.numberbox("setValue",value);
		}else if( componentType == "spinner" ){ //spinner赋值
			component.spinner("setValue",value);
		}else if( componentType == "numberspinner" ){ //numberspinner赋值
			component.numberspinner("setValue",value);
		}else if( componentType == "timespinner" ){ //timespinner赋值
			component.timespinner("setValue",value);
		}else if( componentType == "slider" ){ //slider赋值
			component.slider("setValue",value);
		}else{
			component.val(value);
		}
	};
	
	//easyui控件 通用取值方法
	this.getComponentValue = function(component){
		var value = null;
		
		var componentType = this.getComponentType(component);
		
		if( componentType == "combo" ){ //combo取值
			value = component.combo("getValue");
		}else if( componentType == "combobox" ){ //combobox取值
			value = component.combobox("getValue");
		}else if( componentType == "combotree" ){ //combotree取值
			value = component.combotree("getValue");
		}else if( componentType == "combogrid" ){ //combogrid取值
			value = component.combogrid("getValue");
		}else if( componentType == "datebox" ){ //datebox取值
			value = component.datebox("getValue");
		}else if( componentType == "datetimebox" ){ //datetimebox取值
			value = component.datetimebox("getValue");
		}else if( componentType == "numberbox" ){ //numberbox取值
			value = component.numberbox("getValue");
		}else if( componentType == "spinner" ){ //spinner取值
			value = component.spinner("getValue");
		}else if( componentType == "numberspinner" ){ //numberspinner取值
			value = component.numberspinner("getValue");
		}else if( componentType == "timespinner" ){ //timespinner取值
			value = component.timespinner("getValue");
		}else if( componentType == "slider" ){ //slider取值
			value = component.slider("getValue");
		}else if(componentType == "checkbox"){
            var s='';
            if(component.is(":checked")){ 
                 s = component.val();
            }     
            value = s;
		}else{
			value = component.val();
		}
		
		return value;
	};
	
	this.initForm = function(param){
		//表单赋值
		param = param || {};
		var entity = param.entity || {};
		for(var p in entity){
			if(typeof(entity[p])!="function" && $("#form-"+p)){
				if(p=="addTime" || p == "birthday" || p.indexOf("date")>-1 || p.indexOf("time")>-1){
					EasyUIExt.setComponentValue($("#form-"+p),formateDateTime(entity[p]));
				}else{
					if(entity[p] && typeof(entity[p]) == "object" && entity[p].id){
						EasyUIExt.setComponentValue($("#form-"+p),entity[p].id);
					}else{
						EasyUIExt.setComponentValue($("#form-"+p),entity[p]);
					};
				}
			}
		}
	}
	
  this.formateDateTime = function(value){
	  function add0(val){
		  return val<10?"0"+val:val;
	  }
	  try{
		  var date = new Date(value.time);
		  return date.getFullYear() + "-"
	  		+ add0(date.getMonth()+1) +"-"
	  		+ add0(date.getDate()) +" "
	  		+ add0(date.getHours()) +":"
	  		+ add0(date.getMinutes())+":"
	  		+ add0(date.getSeconds());
	  }catch(e){
		  return "";
	  }
  }
  
  this.formateDate = function(value){
	  function add0(val){
		  return val<10?"0"+val:val;
	  }
	  try{
		  var date = new Date(value.time);
		  return date.getFullYear() + "-"
	  		+ add0(date.getMonth()+1) +"-"
	  		+ add0(date.getDate());
	  }catch(e){
		  return "";
	  }
  }

   //秒转换为时分秒
	this.secondToTime = function formatSeconds(value,h,m,s) {
		function add0(val){
		  return val<10?"0"+val:val;
	    }
		h = h ||"小时";
		m = m ||"分";
		s = s ||"秒";
		
	    var theTime = parseInt(value);// 秒
	    var theTime1 = 0;// 分
	    var theTime2 = 0;// 小时
	
	    if(theTime >= 60) {
	        theTime1 = parseInt(theTime/60);
	        theTime = isNaN(parseInt(theTime%60))?0:parseInt(theTime%60);
            if(theTime1 >= 60) {
	            theTime2 = parseInt(theTime1/60);
	            theTime1 = parseInt(theTime1%60);
            }
	    }
        var result = ""+add0(parseInt(theTime))+s;
        //if(theTime1 > 0) {
        	result = ""+add0(parseInt(theTime1))+m+result;
        //}
        //if(theTime2 > 0) {
        	result = ""+add0(parseInt(theTime2))+h+result;
       // }
	    return result;
	}
	
	this.timeToSecond = function(value){
		try{
			var hms = value.split(":");
			var h = parseInt(hms[0]);
			var m = parseInt(hms[1]);
			var s = parseInt(hms[2]);
			return h*60*60 + m*60 + s;
		}catch(e){
			alert(e);
			return 0;
		}
	}
	this.alerd = null;
//	this.alertDiv = function(options){
//		if(alerd != null) return;
//		options =options || {};
//		options.text = options.text || "请输入弹出的内容";
//		options.time = options.time  || 1000;
//		options.speed = options.speed  || "slow";
//		options.style = options.style  || {};
//		options.style.width = options.style.width || "200px";
//		options.style.height = options.style.height || "40px";
//		
//		alerd = $("<div></div>");
//		var top = (document.body.clientHeight - parseInt(options.style.height))/2;
//		var left = (document.body.clientWidth - parseInt(options.style.width))/2;
//		var css = $.extend({
//			position:"absolute",
//			top:top,
//			left:left,
//			display:"none",
//			width:"200px", 
//			height:"40px", 
//			textAlign:"center",
//			backgroundColor:"#555",
//			color:"white",
//			fontSize:"14px",
//			fontWeight:"700",
//			borderRadius:"8px",
//			zIndex:100000
//		}, options.style);
//		alerd.html("<p>"+options.text+"</p>").css(css);
//		alerd.find("p").css({
//			lineHeight:css.height
//		});
//		$("body").append(alerd);
//		addMask({zIndex:css.zIndex-1,speed:options.speed});
//		alerd.fadeIn();
//		window.setTimeout(function(){
//			removeMask();
//			removeAlertDiv();
//		},options.time)
//	}
	
	this.alertHtml = function(options){
		if(alerd != null) return;
		options =options || {};
		options.text = options.text || "请输入弹出的内容";
		options.speed = options.speed  || "slow";
		options.style = options.style  || {};
		options.style.width = options.style.width || "200px";
		options.style.height = options.style.height || "40px";
		options.success = options.success || function(){};
		options.initHtml = options.initHtml || function(){};
		options.validate = options.validate || function(){return true;};
		alerd = $("<div></div>");
		var top = (document.body.clientHeight - parseInt(options.style.height))/2;
		var left = (document.body.clientWidth - parseInt(options.style.width))/2;
		var css = $.extend({
			position:"absolute",
			top:top,
			left:left,
			display:"none",
			width:"200px", 
			height:"40px", 
			zIndex:100000
		}, options.style);
		alerd.html(options.text).css(css);
		var btn = $("<a></a>");
		btn.linkbutton({
			text:"确定",
			iconCls:'icon-ok',
			onClick:function(){
				if(options.validate()){
					options.success();
					removeMask();
					removeAlertDiv();
				}
			}
		});
		var btn1 = $("<a></a>");
		btn1.linkbutton({
			text:"取消",
			iconCls:'icon-cancel',
			onClick:function(){
				EasyUIExt.removeAlertDiv();
				EasyUIExt.removeMask();
			}
		});
		alerd.append(btn);
		alerd.append(btn1);
		$("body").append(alerd);
		options.initHtml();
		addMask({zIndex:css.zIndex-1,speed:options.speed});
		alerd.fadeIn();
	}
	
	this.maskDiv = null;
	
	this.addMask = function(options){
		if(maskDiv!=null){
			return;
		}
		// 扩展参数
		var op = $.extend({
			opacity: 0.3,
			zIndex: 10000,
			bgcolor: 'black',
			speed:"slow"
		}, options);
		
		// 创建一个 Mask 层，追加到 document.body
		maskDiv = $('<div></div>').css({
			position: 'absolute',
			top: '0px',
			left: '0px',
			'z-index': op.zIndex,
			width: document.body.clientWidth,
			height: document.body.clientHeight,
			'background-color': op.bgcolor,
			opacity: op.opacity
		});
		$("body").append(maskDiv);
		maskDiv.fadeIn("slow");
	}
	this.removeMask = function(){
		if(maskDiv != null){
			maskDiv.remove();
			maskDiv = null;
		}
	}
	this.removeAlertDiv = function(){
		if(alerd != null){
			alerd.remove();
			alerd = null;
		}
	}
	this.loadCombobox = function(){
		$(".dictCombobox").each(function(){
			var pid = $(this).attr("dict-pid");
			var type = $(this).attr("dict-default");
			if(pid && pid!=""){
				$(this).combobox({
					url:top.baseUrl + "/system/dictionary/getDictValue.htm?pid="+pid,
					valueField:"id",
					textField:"dictName",
					editable:false,
					required:true,
					loadFilter:function(data){
						if(type){
							data.data.splice(0,0,{id:"0",dictName:type,selected:true});
						}
						return data.data;
					}
				});
			}
		});
	}
  return this;
  
})(jQuery,window);

//easyui默认form提交加遮罩
(function($){
	$.fn.form.defaults.onSubmit = function(){
		 
	}
	$.fn.form.defaults.onLoadSuccess = function(){
		EasyUIExt.removeAlertDiv();
		EasyUIExt.removeMask();
	}
	$.fn.form.defaults.onLoadError = function(){
		EasyUIExt.removeAlertDiv();
		EasyUIExt.removeMask();
	}
	
})(jQuery);

/***easyui扩展身份证验证****/
var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"}   
function isCardID(sId){   
    var iSum=0 ;  
    var info="" ;  
    if(!/^\d{17}(\d|x)$/i.test(sId)) return "你输入的身份证长度或格式错误";   
    sId=sId.replace(/x$/i,"a");   
    if(aCity[parseInt(sId.substr(0,2))]==null) return "你的身份证地区非法";   
    sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));   
    var d=new Date(sBirthday.replace(/-/g,"/")) ;  
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))return "身份证上的出生日期非法";   
    for(var i = 17;i>=0;i --) iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11) ;  
    if(iSum%11!=1) return "你输入的身份证号非法";   
    return true;//aCity[parseInt(sId.substr(0,2))]+","+sBirthday+","+(sId.substr(16,1)%2?"男":"女")   
}   
function isMoney(s){ 
	var regu ="^[0-9]$|^[0-9]+[^a-z]$|^[0-9]+[^a-z][0-9]$|[0-9]$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) {
		return true;
		} else {
			return false;
		}
	}
function isDecimal(s){
	var regu ="^[0-9]+([.][0-9]{1}){0,1}$"; 
	var re = new RegExp(regu); 
	if (re.test(s)) {
		return true;
		} else {
			return false;
		}
}
$.extend($.fn.validatebox.defaults.rules, {     
    idcared: {     
        validator:function(value,param){    
            var flag= isCardID(value); 
            return flag==true?true:false;    
        },     
        message: '不是有效的身份证号码'    
    },wnumber:{
    	validator: function(value,param){
    		  var regex =/^[\da-z]+$/i;
    		if(!regex.test(value)){ 
    	        return false; 
    	   } 
    		 return true; 
    	},
    	message:'不能有中文或特殊字符'
    },equals:{
    	validator: function(value,param){
    		return value == $(param[0]).val();   
  	},
  	message:'两次输入密码不一致!'
  },isMoney:{
	  validator:function(value,param){
  		 return isMoney(value); 
	  },message:'货币格式错误,重新输入!',
  },isDecimal:{
	  validator:function(value,param){
	  		 return isDecimal(value); 
		  },message:'天数格式错误,重新输入!',
	  }       
}); 

/******JSON*******/
if (typeof JSON !== 'object') {
    JSON = {};
}

(function () {
    'use strict';

    function f(n) {
        // Format integers to have at least two digits.
        return n < 10 ? '0' + n : n;
    }

    if (typeof Date.prototype.toJSON !== 'function') {

        Date.prototype.toJSON = function () {

            return isFinite(this.valueOf())
                ? this.getUTCFullYear()     + '-' +
                    f(this.getUTCMonth() + 1) + '-' +
                    f(this.getUTCDate())      + 'T' +
                    f(this.getUTCHours())     + ':' +
                    f(this.getUTCMinutes())   + ':' +
                    f(this.getUTCSeconds())   + 'Z'
                : null;
        };

        String.prototype.toJSON      =
            Number.prototype.toJSON  =
            Boolean.prototype.toJSON = function () {
                return this.valueOf();
            };
    }

    var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
        gap,
        indent,
        meta = {    // table of character substitutions
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
        },
        rep;


    function quote(string) {

        escapable.lastIndex = 0;
        return escapable.test(string) ? '"' + string.replace(escapable, function (a) {
            var c = meta[a];
            return typeof c === 'string'
                ? c
                : '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
        }) + '"' : '"' + string + '"';
    }


    function str(key, holder) {

        var i,          // The loop counter.
            k,          // The member key.
            v,          // The member value.
            length,
            mind = gap,
            partial,
            value = holder[key];


        if (value && typeof value === 'object' &&
                typeof value.toJSON === 'function') {
            value = value.toJSON(key);
        }

        if (typeof rep === 'function') {
            value = rep.call(holder, key, value);
        }

        switch (typeof value) {
        case 'string':
            return quote(value);

        case 'number':

            return isFinite(value) ? String(value) : 'null';

        case 'boolean':
        case 'null':

            return String(value);

        case 'object':

            if (!value) {
                return 'null';
            }

            gap += indent;
            partial = [];

            if (Object.prototype.toString.apply(value) === '[object Array]') {

                length = value.length;
                for (i = 0; i < length; i += 1) {
                    partial[i] = str(i, value) || 'null';
                }

                v = partial.length === 0
                    ? '[]'
                    : gap
                    ? '[\n' + gap + partial.join(',\n' + gap) + '\n' + mind + ']'
                    : '[' + partial.join(',') + ']';
                gap = mind;
                return v;
            }

            if (rep && typeof rep === 'object') {
                length = rep.length;
                for (i = 0; i < length; i += 1) {
                    if (typeof rep[i] === 'string') {
                        k = rep[i];
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            } else {

                for (k in value) {
                    if (Object.prototype.hasOwnProperty.call(value, k)) {
                        v = str(k, value);
                        if (v) {
                            partial.push(quote(k) + (gap ? ': ' : ':') + v);
                        }
                    }
                }
            }

            v = partial.length === 0
                ? '{}'
                : gap
                ? '{\n' + gap + partial.join(',\n' + gap) + '\n' + mind + '}'
                : '{' + partial.join(',') + '}';
            gap = mind;
            return v;
        }
    }

    if (typeof JSON.stringify !== 'function') {
        JSON.stringify = function (value, replacer, space) {

            var i;
            gap = '';
            indent = '';

// If the space parameter is a number, make an indent string containing that
// many spaces.

            if (typeof space === 'number') {
                for (i = 0; i < space; i += 1) {
                    indent += ' ';
                }

// If the space parameter is a string, it will be used as the indent string.

            } else if (typeof space === 'string') {
                indent = space;
            }

// If there is a replacer, it must be a function or an array.
// Otherwise, throw an error.

            rep = replacer;
            if (replacer && typeof replacer !== 'function' &&
                    (typeof replacer !== 'object' ||
                    typeof replacer.length !== 'number')) {
                throw new Error('JSON.stringify');
            }

// Make a fake root object containing our value under the key of ''.
// Return the result of stringifying the value.

            return str('', {'': value});
        };
    }


// If the JSON object does not yet have a parse method, give it one.

    if (typeof JSON.parse !== 'function') {
        JSON.parse = function (text, reviver) {

// The parse method takes a text and an optional reviver function, and returns
// a JavaScript value if the text is a valid JSON text.

            var j;

            function walk(holder, key) {

// The walk method is used to recursively walk the resulting structure so
// that modifications can be made.

                var k, v, value = holder[key];
                if (value && typeof value === 'object') {
                    for (k in value) {
                        if (Object.prototype.hasOwnProperty.call(value, k)) {
                            v = walk(value, k);
                            if (v !== undefined) {
                                value[k] = v;
                            } else {
                                delete value[k];
                            }
                        }
                    }
                }
                return reviver.call(holder, key, value);
            }

            text = String(text);
            cx.lastIndex = 0;
            if (cx.test(text)) {
                text = text.replace(cx, function (a) {
                    return '\\u' +
                        ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
                });
            }

            if (/^[\],:{}\s]*$/
                    .test(text.replace(/\\(?:["\\\/bfnrt]|u[0-9a-fA-F]{4})/g, '@')
                        .replace(/"[^"\\\n\r]*"|true|false|null|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?/g, ']')
                        .replace(/(?:^|:|,)(?:\s*\[)+/g, ''))) {

                j = eval('(' + text + ')');

                return typeof reviver === 'function'
                    ? walk({'': j}, '')
                    : j;
            }

            throw new SyntaxError('JSON.parse');
        };
    }
}());
                    
$(function(){
	$("input[readonly]").on("focus",function(){
		$(this).blur();
	});
	$("textarea[readonly]").on("focus",function(){
		$(this).blur();
	});
});