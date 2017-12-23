package com.anxuan.xjzx.service;

import java.util.List;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.News;

public interface NewsService extends BaseService<News> {

	void updateByIsTop(String ids, int isTop);

	void updateNewsByReview(String ids, int review);

	void deleteids(String ids);

	List<News> getNewsByList(String string);

	News getNewsById(int id);

}
