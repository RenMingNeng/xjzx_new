package com.anxuan.organizational.bean;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.bean.Identity;

@Entity
@Table(name = "th_organization")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Organization extends Identity {
	private String organName;// 主治架构名称
	@ManyToOne
	@JoinColumn(name = "pid")
	private Organization parentOrgan;// 父类
	private String deptypecode;// 编号
	private String mark;// 表述
	@ManyToOne
	@JoinColumn(name = "level")
	private Dict level;
	private String companycode;// 公司代码

	public String getOrganName() {
		return organName;
	}

	public void setOrganName(String organName) {
		this.organName = organName;
	}

	public Organization getParentOrgan() {
		return parentOrgan;
	}

	public void setParentOrgan(Organization parentOrgan) {
		this.parentOrgan = parentOrgan;
	}

	public String getDeptypecode() {
		return deptypecode;
	}

	public void setDeptypecode(String deptypecode) {
		this.deptypecode = deptypecode;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public Dict getLevel() {
		return level;
	}

	public void setLevel(Dict level) {
		this.level = level;
	}

	public String getCompanycode() {
		return companycode;
	}

	public void setCompanycode(String companycode) {
		this.companycode = companycode;
	}

}
