package com.anxuan.xjzx.util;

import java.util.List;

import com.anxuan.xjzx.bean.TranOrgan;

public class OrganResult {
	private String cityName;
	private List<TranOrgan> tranOrgans;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public List<TranOrgan> getTranOrgans() {
		return tranOrgans;
	}

	public void setTranOrgans(List<TranOrgan> tranOrgans) {
		this.tranOrgans = tranOrgans;
	}

}
