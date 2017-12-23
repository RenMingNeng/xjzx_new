package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.Advise;
import com.anxuan.xjzx.dao.AdviseDao;
@Repository(value="AdviseDao")
public class AdviseDaoImpl extends BaseDaoImpl<Advise> implements AdviseDao{

	@Override
	public void deleteids(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update Advise bean set bean.state =:state where bean.id in (:ids)")
        .setInteger("state", 2)
        .setParameterList("ids", idlists)
        .executeUpdate();
	}

	@Override
	public Advise getAdviseById(int id) {
		return (Advise) qryCurrentSesion().get(Advise.class, id);
	}
}
