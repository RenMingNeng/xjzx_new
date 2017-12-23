package com.anxuan.xjzx.bean;

import java.io.Serializable;
import java.util.Date;

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

//中介介绍
@Entity
@Table(name = "normal_artical")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Normal implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;// 主键
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Dict.class)
	@JoinColumn(name = "pid")
	private Dict normalType;
	private String title;
	private String description;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length = 16777216)
	private String content;
	private int state;// 数据状态
	private Date time;// 新增时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Dict getNormalType() {
		return normalType;
	}

	public void setNormalType(Dict normalType) {
		this.normalType = normalType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

}
