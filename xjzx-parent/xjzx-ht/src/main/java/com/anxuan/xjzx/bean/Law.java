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

//政策法规
@Entity
@Table(name = "tb_law")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Law implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="ltitle")
	private String tilte;// 标题
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length = 16777216)
	private String content;
	private int state;// 数据状态
	private String pubunit;// 发布单位
	private String pubno;// 发布文号
	private Date publishdate;// 法规颁布日期
	private Date carryoutdate;// 生效日期
	private Date adddate;//新增日期
	private String description;// 备注
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Dict.class)
	@JoinColumn(name = "s_id")
	private Dict lawType;// 法规类型
	private int ispass;//审核状态

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTilte() {
		return tilte;
	}

	public void setTilte(String tilte) {
		this.tilte = tilte;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getPubunit() {
		return pubunit;
	}

	public void setPubunit(String pubunit) {
		this.pubunit = pubunit;
	}

	public String getPubno() {
		return pubno;
	}

	public void setPubno(String pubno) {
		this.pubno = pubno;
	}

	public Date getPublishdate() {
		return publishdate;
	}

	public void setPublishdate(Date publishdate) {
		this.publishdate = publishdate;
	}

	public Date getCarryoutdate() {
		return carryoutdate;
	}

	public void setCarryoutdate(Date carryoutdate) {
		this.carryoutdate = carryoutdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Dict getLawType() {
		return lawType;
	}

	public void setLawType(Dict lawType) {
		this.lawType = lawType;
	}

	

	public Date getAdddate() {
		return adddate;
	}

	public void setAdddate(Date adddate) {
		this.adddate = adddate;
	}

	public int getIspass() {
		return ispass;
	}

	public void setIspass(int ispass) {
		this.ispass = ispass;
	}


}
