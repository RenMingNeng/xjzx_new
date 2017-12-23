package com.anxuan.xjzx.bean;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "question_ip")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuestionIp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;				// 主键
	private int question_id;	// 问卷id
	private String cliant_ip;	// 用户ip地址
	private String create_time; // 创建时间
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuestion_id() {
		return question_id;
	}
	public void setQuestion_id(int question_id) {
		this.question_id = question_id;
	}
	public String getCliant_ip() {
		return cliant_ip;
	}
	public void setCliant_ip(String cliant_ip) {
		this.cliant_ip = cliant_ip;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	
	
}
