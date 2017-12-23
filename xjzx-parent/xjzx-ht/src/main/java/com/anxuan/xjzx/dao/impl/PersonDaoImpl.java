package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.Person;
import com.anxuan.xjzx.dao.PersonDao;
@Repository(value="personDao")
public class PersonDaoImpl extends BaseDaoImpl<Person> implements PersonDao{

	@Override
	public void deleteByIds(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		Query query = getCurrentSession().createQuery("update Person bean set bean.is_valid = :is_valid where bean.id in (:ids)");
				query.setInteger("is_valid", 2);
				query.setParameterList("ids", idlists);
				query.executeUpdate();	
	}
	
}
