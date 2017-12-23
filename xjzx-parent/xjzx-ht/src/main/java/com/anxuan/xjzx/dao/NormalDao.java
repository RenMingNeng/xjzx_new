package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Normal;

public interface NormalDao extends BaseDao<Normal>{

	void deleteByIds(String ids);

	Normal getNormalById(int id);

 
}
