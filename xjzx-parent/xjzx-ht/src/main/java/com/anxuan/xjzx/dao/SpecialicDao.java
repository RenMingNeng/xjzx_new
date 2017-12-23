package com.anxuan.xjzx.dao;

import java.util.List;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Specialic;

public interface SpecialicDao extends BaseDao<Specialic>{
	public void addIndex();
	
	public List<Specialic> findAll();
		
	public List<Specialic> findSpecialicByCardnumber(String cardnumber);
		
	Integer deleteBatch(List<Specialic> paramList);
		
	Integer saveBatch(List<Specialic> paramList);
}
