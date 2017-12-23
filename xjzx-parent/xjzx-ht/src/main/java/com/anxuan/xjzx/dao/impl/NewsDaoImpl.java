package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.dao.NewsDao;
@Repository(value="newsDao")
public class NewsDaoImpl extends BaseDaoImpl<News> implements NewsDao{

	@Override
	public void updateByIsTop(String ids,int isTop) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
	    getCurrentSession().createQuery("update News bean set bean.istop =:isTop where bean.id in (:ids)")
        .setInteger("isTop", isTop)
        .setParameterList("ids", idlists)
        .executeUpdate();
	    
	}

	@Override
	public void updateNewsByReview(String ids, int review) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update News bean set bean.review =:review where bean.id in (:ids)")
        .setInteger("review", review)
        .setParameterList("ids", idlists)
        .executeUpdate();
	}

	@Override
	public void deleteids(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		 getCurrentSession().createQuery("update News bean set bean.state =:state where bean.id in (:ids)")
        .setInteger("state", 2)
        .setParameterList("ids", idlists)
        .executeUpdate();
	}

	@Override
	public News getNewsid(int id) {
		return (News)qryCurrentSesion().get(News.class, id);
	}

}
