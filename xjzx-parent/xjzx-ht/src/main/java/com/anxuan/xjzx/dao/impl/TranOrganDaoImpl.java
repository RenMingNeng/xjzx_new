package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.TranOrgan;
import com.anxuan.xjzx.dao.TranOrganDao;
@Repository(value="tranOrganDao")
public class TranOrganDaoImpl extends BaseDaoImpl<TranOrgan> implements TranOrganDao{

	@Override
	public void deleteids(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update TranOrgan bean set bean.state =:state where bean.id in (:ids)")
        .setInteger("state", 2)
        .setParameterList("ids", idlists)
        .executeUpdate();
	}

	@Override
	public TranOrgan getTranOrganById(int id) {
		return (TranOrgan)qryCurrentSesion().get(TranOrgan.class, id);
	}
 
}
