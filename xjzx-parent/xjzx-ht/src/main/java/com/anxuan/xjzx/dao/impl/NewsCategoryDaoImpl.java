package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.NewsCategory;
import com.anxuan.xjzx.dao.NewsCategoryDao;
@Repository(value="newsCategoryDao")
public class NewsCategoryDaoImpl extends BaseDaoImpl<NewsCategory> implements NewsCategoryDao{

	@Override
	public void deleteState(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update NewsCategory bean set bean.state =:state where bean.id in (:ids)")
        .setInteger("state", 2)
        .setParameterList("ids", idlists)
        .executeUpdate();
	}

	@Override
	public NewsCategory getNewsCategoryById(int categoryid) {
		
		return (NewsCategory)qryCurrentSesion().get(NewsCategory.class, categoryid);
	}
 
}
