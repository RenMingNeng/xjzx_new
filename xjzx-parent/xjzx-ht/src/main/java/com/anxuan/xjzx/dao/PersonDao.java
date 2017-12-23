package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Person;

public interface PersonDao extends BaseDao<Person>{

	void deleteByIds(String ids);


}
