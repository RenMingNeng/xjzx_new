package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.Normal;
import com.anxuan.xjzx.dao.NormalDao;
@Repository(value="normalDao")
public class NormalDaoImpl extends BaseDaoImpl<Normal> implements NormalDao{

	@Override
	public void deleteByIds(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update Normal bean set bean.state =:state where bean.id in (:ids)")
        .setInteger("state", 2)
        .setParameterList("ids", idlists)
        .executeUpdate();
		
	}

	@Override
	public Normal getNormalById(int id) {
		return (Normal)qryCurrentSesion().get(Normal.class, id);
	}



}
