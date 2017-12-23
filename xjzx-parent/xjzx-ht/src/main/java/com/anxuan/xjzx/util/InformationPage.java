package com.anxuan.xjzx.util;

import java.util.List;

public class InformationPage<T> {
	public InformationPage(int pageSize,int curPage){
		this.cur_page = curPage;
		this.page_size = pageSize;
	}
	
	private int cur_page;
	private int page_size;
	private List<T> rows;
	private int count_all;
	private int count_page;
	private int before;
	private int next;
	private int pageListStart;
	private int pageListEnd;
	
	public int getPageListStart() {
		return pageListStart;
	}
	public void setPageListStart() {
		pageListStart = cur_page - 2;
		if(pageListStart<1) pageListStart = cur_page - 1;
		if(pageListStart<1) pageListStart = cur_page;
	}
	public int getPageListEnd() {
		return pageListEnd;
	}
	public void setPageListEnd() {
		pageListEnd = cur_page + 2;
		if(pageListEnd>this.count_page) pageListEnd = cur_page + 1;
		if(pageListEnd>this.count_page) pageListEnd = cur_page;
	}
	public int getBefore() {
		return cur_page - 1<1?1:cur_page - 1;
	}
	public void setBefore(int before) {
		this.before = before;
	}
	public int getNext() {
		return cur_page+1>count_page?cur_page:cur_page+1;
	}
	public void setNext(int next) {
		this.next = next;
	}
	public int getCount_all() {
		return count_all;
	}
	public void setCount_all(int countAll) {
		count_all = countAll;
		setCount_page();
		setPageListStart();
		setPageListEnd();
	}
	public int getCount_page() {
		return count_page;
	}
	public void setCount_page() {
		count_page = count_all % page_size!=0 ? count_all / page_size + 1: count_all / page_size;
	}
	public int getStartPoint(){
		return (cur_page-1)*page_size;
	}
	public int getEndPoint(){
		return cur_page*page_size-1;
	}
	public int getCur_page() {
		return cur_page;
	}
	public void setCur_page(int curPage) {
		cur_page = curPage;
	}
	public int getPage_size() {
		return page_size;
	}
	public void setPage_size(int pageSize) {
		page_size = pageSize;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
}
