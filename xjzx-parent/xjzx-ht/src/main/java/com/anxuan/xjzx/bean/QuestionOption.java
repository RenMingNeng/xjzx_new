package com.anxuan.xjzx.bean;

import java.io.Serializable;

public class QuestionOption implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String key;		// 选项 key
	private String value;	// 选项 value
	
	private Integer count;	// 票数
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "QuestionOption [key=" + key + ", value=" + value + "]";
	}
	
	
}
