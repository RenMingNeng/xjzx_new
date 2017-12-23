package com.anxuan.beadhouse.util;

import java.util.List;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 请求返回结果
 * @author asd_lx
 *
 */
public class ReturnValue {
	private String code;
	private String message;
	private List listData;
	private Object data;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List getListData() {
		return listData;
	}
	public void setListData(List listData) {
		this.listData = listData;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
	public String toJsonString(){
		return JSONObject.fromObject(this).toString();
	}
	public String toJsonString(JsonConfig config){
		return JSONObject.fromObject(this,config).toString();
	}
	public String toArryString(){
		return JSONObject.fromObject(this).toString();
	}
	public String toArryString(JsonConfig config){
		return JSONObject.fromObject(this,config).toString();
	}
}
