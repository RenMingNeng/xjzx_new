package com.anxuan.beadhouse.bean;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tb_dict")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Dict extends Identity {
	private String dictName;
	private String dictCode;
	private String dictValue; //主题栏目此字段用作是否在前天显示主题的标志   0不显示，1显示
	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE}, targetEntity=Dict.class )
    @JoinColumn(name="pid") 
	private Dict parent;
	private String others;   //主题栏目用此字段存主题图片的url
	private char dictType;

	public String getDictName() {
		return dictName;
	}

	public void setDictName(String dictName) {
		this.dictName = dictName;
	}

	public String getDictCode() {
		return dictCode;
	}

	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}

	public String getDictValue() {
		return dictValue;
	}

	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}

	public String getOthers() {
		return others;
	}

	public Dict getParent() {
		return parent;
	}

	public void setParent(Dict parent) {
		this.parent = parent;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public char getDictType() {
		return dictType;
	}

	public void setDictType(char dictType) {
		this.dictType = dictType;
	}
}
