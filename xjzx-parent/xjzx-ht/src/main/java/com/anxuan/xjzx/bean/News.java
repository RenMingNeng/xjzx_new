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

@Entity
@Table(name = "news")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class News implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;// 新闻标题
	private int rid;// 会员等级查看
	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(length = 16777216)
	private String content;// 新闻内容
	private String author;// 发布人ty
	private String url;//  图片新闻地址
	private String videoUrl;// 视频新闻地址
	private Integer video_isvalid;//视频是否显示
	private String video_info;  //视频信息
	private String froms;// 新闻来源
	private Date time;// 发布时间
	private Date creatDate;// 创建时间
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = NewsCategory.class)
	@JoinColumn(name = "cid")
	private NewsCategory category;// 新闻所属分类
	private String keywords;// 新闻关键字
	private int review;// 审核状态 0.待审核,1.审核通过,2审核不通过
	private int state;// 状态
	private int istop;// 是否制定
	private int types;// 新闻类型
	private int clicknumber; // 点击量
	private int isimp; // 是否为红头文件新闻 0 否 1是
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, targetEntity = Dict.class)
	@JoinColumn(name = "wjtype")
	private Dict wjtype; // 红头文件类型
	private String pubno; // 文号

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

	public int getRid() {
		return rid;
	}

	public void setRid(int rid) {
		this.rid = rid;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFroms() {
		return froms;
	}

	public void setFroms(String froms) {
		this.froms = froms;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public int getReview() {
		return review;
	}

	public void setReview(int review) {
		this.review = review;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getIstop() {
		return istop;
	}

	public void setIstop(int istop) {
		this.istop = istop;
	}

	public int getTypes() {
		return types;
	}

	public void setTypes(int types) {
		this.types = types;
	}

	public int getClicknumber() {
		return clicknumber;
	}

	public void setClicknumber(int clicknumber) {
		this.clicknumber = clicknumber;
	}

	public int getIsimp() {
		return isimp;
	}

	public void setIsimp(int isimp) {
		this.isimp = isimp;
	}

	public Dict getWjtype() {
		return wjtype;
	}

	public void setWjtype(Dict wjtype) {
		this.wjtype = wjtype;
	}

	public String getPubno() {
		return pubno;
	}

	public void setPubno(String pubno) {
		this.pubno = pubno;
	}

	public NewsCategory getCategory() {
		return category;
	}

	public void setCategory(NewsCategory category) {
		this.category = category;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getVideo_isvalid() {
		return video_isvalid;
	}

	public void setVideo_isvalid(Integer video_isvalid) {
		this.video_isvalid = video_isvalid;
	}

	public String getVideo_info() {
		return video_info;
	}

	public void setVideo_info(String video_info) {
		this.video_info = video_info;
	}

}
