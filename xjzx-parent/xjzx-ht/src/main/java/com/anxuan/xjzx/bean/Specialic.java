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
//特种作业
@Entity
@Table(name = "t_spelialic")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Indexed(index = "spelialic")
@Analyzer(impl = StandardAnalyzer.class)
public class Specialic implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Field(index = Index.YES, analyze = Analyze.YES, store = Store.YES, name = "cardnumber")
	private String cardnumber;// 证号
	private String name;// 姓名
	private String sex;// 性别
	private String education;// 文化程度
	private String workunit;// 工作单位
	private String worktype;// 单位类别
	private String address;// 通讯地址
	private String telephone;// 联系电话
	private String jobcategory;// 作业类别
	private String prepareproject;// 准操项目
	private String trainingunits;// 培训单位
	private String issuingorgan;// 发证机关
	private String lscores;// 理论成绩
	private String sscores;// 实操成绩
	private String badgestime;// 初领日期
	private String releastime;// 发证日期
	private String seniority;// 本工种工龄
	private String firstreview;// 一次复审期
	private String twotreview;// 二次复审期
	private String treviewrecord1;// 复审记录1
	private String treviewrecord2;// 复审记录2
	private String starttime;// 有效期从
	private String endtime;// 有效期到
	private String idcard;// 证件标识
	private String reviewid;// 复审标识
	private String firsttrial;// 一审培训
	private String twotrial;// 二审培训
	private String param3;// 备用3
	private String param4;// 备用4
	private String isserial;// 是否连续
	private String labotype;// 用工类型
	private String health;// 身体状况
	private String nameno;// 准考证号
	private String handleno;// 办理号
	private String recordno;// 记录号
	private String whereofrecord;// 违章记录
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCardnumber() {
		return cardnumber;
	}
	public void setCardnumber(String cardnumber) {
		this.cardnumber = cardnumber;
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
	public String getEducation() {
		return education;
	}
	public void setEducation(String education) {
		this.education = education;
	}
	public String getWorkunit() {
		return workunit;
	}
	public void setWorkunit(String workunit) {
		this.workunit = workunit;
	}
	public String getWorktype() {
		return worktype;
	}
	public void setWorktype(String worktype) {
		this.worktype = worktype;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getJobcategory() {
		return jobcategory;
	}
	public void setJobcategory(String jobcategory) {
		this.jobcategory = jobcategory;
	}
	public String getPrepareproject() {
		return prepareproject;
	}
	public void setPrepareproject(String prepareproject) {
		this.prepareproject = prepareproject;
	}
	public String getTrainingunits() {
		return trainingunits;
	}
	public void setTrainingunits(String trainingunits) {
		this.trainingunits = trainingunits;
	}
	public String getIssuingorgan() {
		return issuingorgan;
	}
	public void setIssuingorgan(String issuingorgan) {
		this.issuingorgan = issuingorgan;
	}
	public String getLscores() {
		return lscores;
	}
	public void setLscores(String lscores) {
		this.lscores = lscores;
	}
	public String getSscores() {
		return sscores;
	}
	public void setSscores(String sscores) {
		this.sscores = sscores;
	}
	public String getBadgestime() {
		return badgestime;
	}
	public void setBadgestime(String badgestime) {
		this.badgestime = badgestime;
	}
	public String getReleastime() {
		return releastime;
	}
	public void setReleastime(String releastime) {
		this.releastime = releastime;
	}
	public String getSeniority() {
		return seniority;
	}
	public void setSeniority(String seniority) {
		this.seniority = seniority;
	}
	public String getFirstreview() {
		return firstreview;
	}
	public void setFirstreview(String firstreview) {
		this.firstreview = firstreview;
	}
	public String getTwotreview() {
		return twotreview;
	}
	public void setTwotreview(String twotreview) {
		this.twotreview = twotreview;
	}
	public String getTreviewrecord1() {
		return treviewrecord1;
	}
	public void setTreviewrecord1(String treviewrecord1) {
		this.treviewrecord1 = treviewrecord1;
	}
	public String getTreviewrecord2() {
		return treviewrecord2;
	}
	public void setTreviewrecord2(String treviewrecord2) {
		this.treviewrecord2 = treviewrecord2;
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
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getReviewid() {
		return reviewid;
	}
	public void setReviewid(String reviewid) {
		this.reviewid = reviewid;
	}
	public String getFirsttrial() {
		return firsttrial;
	}
	public void setFirsttrial(String firsttrial) {
		this.firsttrial = firsttrial;
	}
	public String getTwotrial() {
		return twotrial;
	}
	public void setTwotrial(String twotrial) {
		this.twotrial = twotrial;
	}
	public String getParam3() {
		return param3;
	}
	public void setParam3(String param3) {
		this.param3 = param3;
	}
	public String getParam4() {
		return param4;
	}
	public void setParam4(String param4) {
		this.param4 = param4;
	}
	public String getIsserial() {
		return isserial;
	}
	public void setIsserial(String isserial) {
		this.isserial = isserial;
	}
	public String getLabotype() {
		return labotype;
	}
	public void setLabotype(String labotype) {
		this.labotype = labotype;
	}
	public String getHealth() {
		return health;
	}
	public void setHealth(String health) {
		this.health = health;
	}
	public String getNameno() {
		return nameno;
	}
	public void setNameno(String nameno) {
		this.nameno = nameno;
	}
	public String getHandleno() {
		return handleno;
	}
	public void setHandleno(String handleno) {
		this.handleno = handleno;
	}
	public String getRecordno() {
		return recordno;
	}
	public void setRecordno(String recordno) {
		this.recordno = recordno;
	}
	public String getWhereofrecord() {
		return whereofrecord;
	}
	public void setWhereofrecord(String whereofrecord) {
		this.whereofrecord = whereofrecord;
	}
	
}
