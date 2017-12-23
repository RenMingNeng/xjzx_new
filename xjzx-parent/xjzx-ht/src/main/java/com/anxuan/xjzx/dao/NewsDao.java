package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.News;

public interface NewsDao extends BaseDao<News>{

	void updateByIsTop(String ids, int isTop);

	void updateNewsByReview(String ids, int review);

	void deleteids(String ids);

	News getNewsid(int id);

}
