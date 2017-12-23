package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.Email;
import com.anxuan.xjzx.bean.Person;
import com.anxuan.xjzx.dao.EmailDao;
import com.anxuan.xjzx.dao.PersonDao;
@Repository(value="emailDao")
public class EmailDaoImpl extends BaseDaoImpl<Email> implements EmailDao{

	@Override
	public void deleteByIds(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		Query query = getCurrentSession().createQuery("delete from Email bean where bean.id in (:ids)");
		query.setParameterList("ids", idlists);
		query.executeUpdate();	
	}
	
}
