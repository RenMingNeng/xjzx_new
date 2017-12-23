package com.anxuan.xjzx.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import net.sf.json.JSONArray;

@Entity
@Table(name = "question")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String title;// 标题
	private String answer_option;// 选项 json
	private String feedback;   // 中心反馈
	private String back_valid; // 反馈是否显示   1、展示    2、不展示
	private String is_valid; // 是否前台展示     1、展示    2、不展示
	private String type; 	 // 调查类型     1单选； 2多选；3简单
	private String desc_no; // 排序号
	private String create_time;// 创建时间
	private String create_user;// 创建人
	private String operate_time;// 操作时间
	private String operate_user;// 操作人
	
	@Transient
	private List<QuestionOption> questionOptions = new ArrayList<QuestionOption>();
	
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
	public String getAnswer_option() {
		return answer_option;
	}
	public void setAnswer_option(String answer_option) {
		this.answer_option = answer_option;
	}
	public String getFeedback() {
		return feedback;
	}
	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}
	public String getBack_valid() {
		return back_valid;
	}
	public void setBack_valid(String back_valid) {
		this.back_valid = back_valid;
	}
	public String getIs_valid() {
		return is_valid;
	}
	public void setIs_valid(String is_valid) {
		this.is_valid = is_valid;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDesc_no() {
		return desc_no;
	}
	public void setDesc_no(String desc_no) {
		this.desc_no = desc_no;
	}
	public String getCreate_time() {
		return create_time;
	}
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	public String getCreate_user() {
		return create_user;
	}
	public void setCreate_user(String create_user) {
		this.create_user = create_user;
	}
	public String getOperate_time() {
		return operate_time;
	}
	public void setOperate_time(String operate_time) {
		this.operate_time = operate_time;
	}
	public String getOperate_user() {
		return operate_user;
	}
	public void setOperate_user(String operate_user) {
		this.operate_user = operate_user;
	}
	public Question() {
		super();
	}
	public List<QuestionOption> getQuestionOptions() {
		return questionOptions;
	}
	public void setQuestionOptions(List<QuestionOption> questionOptions) {
		this.questionOptions = questionOptions;
	}
	
	public Question(int id, String title, String answer_option, String feedback, String back_valid, String create_time, String create_user, 
			String operate_time, String operate_user, String is_valid, String type,String desc_no) {
		super();
		this.id = id;
		this.title = title;
		this.answer_option = answer_option;
		this.feedback = feedback;
		this.back_valid = back_valid;
		if(StringUtils.isNotEmpty(answer_option)) {
			this.questionOptions = (List<QuestionOption>)JSONArray.toList(JSONArray.fromObject(answer_option), QuestionOption.class);
		}
		this.is_valid = is_valid;
		this.type = type;
		this.desc_no = desc_no;
		this.create_time = create_time;
		this.create_user = create_user;
		this.operate_time = operate_time;
		this.operate_user = operate_user;
	}
	@Override
	public String toString() {
		return "Question [id=" + id + ", title=" + title + ", answer_option=" + answer_option + ", create_time=" + create_time + ", feedback=" + feedback + ", back_valid="
				+ back_valid + ", create_user=" + create_user + ", operate_time=" + operate_time + ", operate_user=" + operate_user + ", is_valid=" 
				+ is_valid + ", type=" + type +", desc_no=" + desc_no + ", questionOptions=" + questionOptions + "]";
	}
	
	
	
}
