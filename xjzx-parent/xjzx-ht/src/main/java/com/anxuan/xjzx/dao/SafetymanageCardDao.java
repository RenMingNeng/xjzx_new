package com.anxuan.xjzx.dao;

import java.util.List;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.SafetymanageCard;

public interface SafetymanageCardDao extends BaseDao<SafetymanageCard> {

	void addIndex();

	List<SafetymanageCard> getSafetymanageCardByIdcard(String idcard, int line);

	void deleteSafetymanageCard(int key);
	
	Integer deleteBatch(List<SafetymanageCard> paramList);
	  
	Integer saveBatch(List<SafetymanageCard> paramList);
}
