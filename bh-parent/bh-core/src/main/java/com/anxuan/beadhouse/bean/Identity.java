package com.anxuan.beadhouse.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.OrderBy;


@MappedSuperclass
public class Identity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;// 主键
	private boolean deleteStatus;// 删除状态
	private Date addTime;// 新增时间
	@OrderBy("orderid desc")
	private int orderid;
    
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public boolean isDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(boolean deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public Date getAddTime() {
		return addTime;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Identity(long id, boolean deleteStatus, Date addTime) {
		super();
		this.id = id;
		this.deleteStatus = deleteStatus;
		this.addTime = addTime;
	}

	public int getOrderid() {
		return orderid;
	}

	public void setOrderid(int orderid) {
		this.orderid = orderid;
	}

	public Identity() {
		super();
	}

}
