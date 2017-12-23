package com.anxuan.power.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.anxuan.beadhouse.bean.Identity;

@Entity
@Table(name = "tb_rolse")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Roles extends Identity {
	private String roleKey;// 角色名称
	private String name;// 名称
	private String mark;// 描述
	@ManyToMany(targetEntity = Resource.class, cascade = { CascadeType.PERSIST })
	@Fetch(FetchMode.SELECT)
	@JoinTable(name = "tb_role_resouce", joinColumns = @JoinColumn(name = "rose_id") , inverseJoinColumns = @JoinColumn(name = "resouceid_id") )
	private List<Resource> resources;

	public String getRoleKey() {
		return roleKey;
	}

	public void setRoleKey(String roleKey) {
		this.roleKey = roleKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMark() {
		return mark;
	}

	public void setMark(String mark) {
		this.mark = mark;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

}
