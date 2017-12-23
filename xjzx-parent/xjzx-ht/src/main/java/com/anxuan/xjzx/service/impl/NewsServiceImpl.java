package com.anxuan.xjzx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.dao.NewsDao;
import com.anxuan.xjzx.service.NewsService;
@Service(value="newsService")
public class NewsServiceImpl extends BaseServiceImpl<News> implements NewsService{
    @Resource
    private NewsDao newsDao;
	@Override
	public void updateByIsTop(String ids,int isTop) {
		newsDao.updateByIsTop(ids,isTop);
	}
	@Override
	public void updateNewsByReview(String ids, int review) {
		newsDao.updateNewsByReview(ids,review);
		
	}
	@Override
	public void deleteids(String ids) {
		newsDao.deleteids(ids);
	}
	@Override
	public List<News> getNewsByList(String idStr) {
		String categoryList[] = CommUtil.splitByChar(idStr,",");
		List<News> newsList = new ArrayList<News>();
		for(String categoryid:categoryList){
			News news = getNewsBycategoryId(CommUtil.null2Int(categoryid));
			if(news!=null)newsList.add(news);
		}
		return newsList;
	}
  public News getNewsBycategoryId(int categoryid){
	  News news = null;
	  Map params = new HashMap();
	  params.put("cid", categoryid);
	  params.put("state", 1);
	  params.put("review", 1);
	  List<News> newsList = newsDao.find("from News bean where bean.category.parent.id =:cid and bean.state=:state and bean.review=:review order by bean.istop,bean.time desc", params, 0, 1);
	  if(newsList==null||newsList.size()==0){
		  newsList = newsDao.find("from News bean where bean.category.id =:cid and bean.state=:state and bean.review=:review order by bean.istop,bean.time desc", params, 0, 1);
	  }
	  if(newsList!=null&&newsList.size()>0){
		  news = newsList.get(0);
		  return news;
	  }
	  return news;
  }
	@Override
	public News getNewsById(int id) {
		News news = newsDao.getNewsid(id);
		if(news!=null){
			news.setClicknumber(news.getClicknumber()+1);
			newsDao.update(news);
		}
		return news;
	}
	
}
