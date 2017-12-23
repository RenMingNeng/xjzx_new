package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Email;

public interface EmailDao extends BaseDao<Email>{

	void deleteByIds(String ids);


}
