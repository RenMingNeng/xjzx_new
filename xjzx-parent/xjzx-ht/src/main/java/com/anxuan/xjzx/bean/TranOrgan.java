package com.anxuan.xjzx.bean;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.anxuan.beadhouse.bean.Dict;

@Entity
@Table(name = "train_organ")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TranOrgan implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;// 主键
	private String name; // 机构名
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length = 16777216)
	private String info; // 机构介绍
	private String website; //
	private int type; // 机构分类 1. 培训机构， 2. 考核机构
	private String address;// 地址
	private String contacts;// 联系人
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Dict.class)
	@JoinColumn(name = "area_id")
	private Dict area;// 区域
	private String parms1;// 参数1
	private String parms2;
	private String tel;// 电话
	private int state; // 数据状态

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContacts() {
		return contacts;
	}

	public void setContacts(String contacts) {
		this.contacts = contacts;
	}

	public String getParms1() {
		return parms1;
	}

	public void setParms1(String parms1) {
		this.parms1 = parms1;
	}

	public String getParms2() {
		return parms2;
	}

	public void setParms2(String parms2) {
		this.parms2 = parms2;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Dict getArea() {
		return area;
	}

	public void setArea(Dict area) {
		this.area = area;
	}

}
