package com.anxuan.xjzx.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Store;

@Entity
@Table(name = "t_safetymanagecard")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed(index = "safetymanagecard")
@Analyzer(impl = StandardAnalyzer.class)
public class SafetymanageCard implements Serializable {
	// 安全生产管理
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int key;// 主键
	private String id;// 证件号(2016版序号)
	private String name;// 姓名
	private String sex;// 性别
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, name = "idcard")
	private String idcard;// 身份证
	private String unit;// 单位名称
	private String job;// 职务
	private String starttime;// 起时间(2016版初领时间)
	private String endtime;// 终时间(2016版理结束时间)
	private String ftime;// 发证时间(2016版出生年月)
	private String wfzd;// 证件类类型
	private String zc;// 职称
	private String dwlx;// 单位类型(2016版考试科目)
	private String xs1;// 学时1(2016版理论知识成绩)
	private String cj1;// 成绩1(2016版管理知识成绩)
	private String xs2;// 学时2(2016版图片信息)
	private String cj2;// 成绩2(2016版学历)
	private String pxlx;// 培训类型
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.NO, name = "line")
	private int line;// 年线

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getFtime() {
		return ftime;
	}

	public void setFtime(String ftime) {
		this.ftime = ftime;
	}

	public String getWfzd() {
		return wfzd;
	}

	public void setWfzd(String wfzd) {
		this.wfzd = wfzd;
	}

	public String getZc() {
		return zc;
	}

	public void setZc(String zc) {
		this.zc = zc;
	}

	public String getDwlx() {
		return dwlx;
	}

	public void setDwlx(String dwlx) {
		this.dwlx = dwlx;
	}

	public String getXs1() {
		return xs1;
	}

	public void setXs1(String xs1) {
		this.xs1 = xs1;
	}

	public String getCj1() {
		return cj1;
	}

	public void setCj1(String cj1) {
		this.cj1 = cj1;
	}

	public String getXs2() {
		return xs2;
	}

	public void setXs2(String xs2) {
		this.xs2 = xs2;
	}

	public String getCj2() {
		return cj2;
	}

	public void setCj2(String cj2) {
		this.cj2 = cj2;
	}

	public String getPxlx() {
		return pxlx;
	}

	public void setPxlx(String pxlx) {
		this.pxlx = pxlx;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

}
