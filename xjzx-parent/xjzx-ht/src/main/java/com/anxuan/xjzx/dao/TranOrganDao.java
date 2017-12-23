package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.TranOrgan;

public interface TranOrganDao extends BaseDao<TranOrgan>{

	void deleteids(String ids);

	TranOrgan getTranOrganById(int id);

}
