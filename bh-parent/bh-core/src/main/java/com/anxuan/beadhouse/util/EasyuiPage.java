package com.anxuan.beadhouse.util;

import java.io.Serializable;
import java.util.List;

public class EasyuiPage implements Serializable{
	private int page;
	private int rows;
	private List data;
	private int total;
	private String sort = "id";
	private String order = "asc";
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public List getData() {
		return data;
	}
	public void setData(List data) {
		this.data = data;
	}
	public int getTotle() {
		return total;
	}
	public void setTotle(int total) {
		this.total = total;
	}
	
	public int getFirsRow(){
		return (page-1)*rows;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	
}
