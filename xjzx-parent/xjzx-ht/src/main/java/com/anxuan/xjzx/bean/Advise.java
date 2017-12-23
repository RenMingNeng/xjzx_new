package com.anxuan.xjzx.bean;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 建议
 */
@Entity
@Table(name = "normal_advise")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Advise implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;// 建议标题
	private String adcontent;// 建议内容
	private String replay;// 回复内容
	private Date time;// 回复日期
	private String username;// 用户名称
	private String email; // 电子邮箱
	private String phone; // 电话号码
	private String address; // 地址
	private Date createdate;// 发布时间
	private int islook;// 状态(显示与否  1:显示 、0:不显示)
	private int state; // 数据状态
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getReplay() {
		return replay;
	}
	public void setReplay(String replay) {
		this.replay = replay;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getAdcontent() {
		return adcontent;
	}
	public void setAdcontent(String Adcontent) {
		this.adcontent = Adcontent;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String Username) {
		this.username = Username;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String Phone) {
		this.phone = Phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String Email) {
		this.email = Email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String Address) {
		this.address = Address;
	}
	public Date getCreatedate() {
		return createdate;
	}
	public void setCreatedate(Date Createdate) {
		this.createdate = Createdate;
	}
	public int getIslook() {
		return islook;
	}
	public void setIslook(int islook) {
		this.islook = islook;
	}
	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}
}
