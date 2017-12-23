package com.anxuan.xjzx.dao.impl;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.Law;
import com.anxuan.xjzx.dao.LawDao;
@Repository(value="lawDao")
public class LawDaoImpl extends BaseDaoImpl<Law> implements LawDao{

	@Override
	public void deleteids(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update Law bean set bean.state =:state where bean.id in (:ids)")
        .setInteger("state", 2)
        .setParameterList("ids", idlists)
        .executeUpdate();
	}

	@Override
	public Law getLawById(int id) {
		return (Law)qryCurrentSesion().get(Law.class,id);
	}

	@Override
	public void updateLawByIspass(String ids, int ispass) {
	String[] idList = CommUtil.splitByChar(ids, ",");
	List<Integer> idlists = new ArrayList<Integer>();
	for(String id:idList){
		idlists.add(CommUtil.null2Int(id));
	}
     Query query = getCurrentSession().createQuery("update Law bean set bean.ispass =:ispass where bean.id in(:ids)");
     query.setInteger("ispass",ispass);
     query.setParameterList("ids", idlists);
     query.executeUpdate();
	}

}
