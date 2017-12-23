package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Advise;

public interface AdviseDao extends BaseDao<Advise>{

	void deleteids(String ids);
	
	Advise getAdviseById(int id);
}
