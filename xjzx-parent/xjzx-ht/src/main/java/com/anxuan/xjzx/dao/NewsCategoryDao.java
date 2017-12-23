package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.NewsCategory;

public interface NewsCategoryDao extends BaseDao<NewsCategory>{

	void deleteState(String ids);

	NewsCategory getNewsCategoryById(int categoryid);

}
