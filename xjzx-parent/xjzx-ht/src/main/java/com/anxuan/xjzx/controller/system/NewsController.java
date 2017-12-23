package com.anxuan.xjzx.controller.system;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.bean.NewsCategory;
import com.anxuan.xjzx.service.NewsCategoryService;
import com.anxuan.xjzx.service.NewsService;

@Controller
@RequestMapping("/system/news")
public class NewsController {
	@Resource
	private NewsService newsService;
	@Resource
	private NewsCategoryService newsCategoryService;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/news/news_index", "ADMIN", request, response);
		return mav;
	}

	// 加载新增页面
	@RequestMapping("/loadAddNew")
	public ModelAndView loadAdd(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/news/news_edit", "ADMIN", request, response);
		String categoryId = request.getParameter("categoryId");
		mav.addObject("categoryId", categoryId);
		return mav;
	}

	// 加载修改页面
	@RequestMapping("/loadUpdateNew")
	public ModelAndView loadUpdate(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/news/news_edit", "ADMIN", request, response);
		String categoryId = request.getParameter("categoryId");
		mav.addObject("categoryId", categoryId);
		return mav;
	}
	
	// 加载文档归类页面
		@RequestMapping("/toSelectType")
		public ModelAndView toSelectType(HttpServletRequest request, HttpServletResponse response,String ids) {
			JModelAndView mav = new JModelAndView("/news/news_reType", "ADMIN", request, response);
			mav.addObject("newsIds", ids);
			return mav;
		}
	// 加载文档推送页面
		@RequestMapping("/toAnotherShow")
		public ModelAndView toAnotherShow(HttpServletRequest request, HttpServletResponse response,String id) {
			JModelAndView mav = new JModelAndView("/news/news_anotherShow", "ADMIN", request, response);
			mav.addObject("newsId", id);
			return mav;
		}
	
	// 视频预览 页面
	@RequestMapping("/videoView")
	public ModelAndView videoView(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/news/video_view", "ADMIN", request, response);
		String url = request.getParameter("url");
		mav.addObject("url", url);
		return mav;
	}

	@RequestMapping("/addNews")
	public void addNews(News news, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			if (news.getWjtype().getId() == 0) {
				news.setWjtype(null);
			}
			news.setCreatDate(new Date());
			newsService.save(news);
			ret.setCode("success");
			ret.setMessage("新增成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}

	@RequestMapping("updateNews")
	public void updateNews(News news, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			if (news.getWjtype().getId() == 0) {
				news.setWjtype(null);
			}
			newsService.update(news);
			ret.setCode("success");
			ret.setMessage("修改成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	

	/**
	 * 移动文档
	 * @param news
	 * @param out
	 */
	@RequestMapping("reTypeNews")
	public void reTypeNews(String categoryId, String newsIds, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			NewsCategory category = new NewsCategory();
			category.setId(Integer.valueOf(categoryId));
			List<String> news_ids = Arrays.asList(newsIds.split(","));
			News news = null;
			for (String newsId : news_ids) {
				news = newsService.getNewsById(Integer.valueOf(newsId));
				news.setCategory(category);
				newsService.update(news);
			}
			ret.setCode("success");
			ret.setMessage("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	/**
	 * 推送显示
	 * @param news
	 * @param out
	 */
	@RequestMapping("anotherShow")
	public void anotherShowNews(String categoryIds, String newsId, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			// 复制数据，修改cid后重新插入 
			News news = newsService.getNewsById(Integer.valueOf(newsId));
			// 获取最大的id
			//int id = newsService.getMaxId();
			List<String> category_ids = Arrays.asList(categoryIds.split(","));
			for (String categoryId : category_ids) {
				NewsCategory category = new NewsCategory();
				category.setId(Integer.valueOf(categoryId));
				News _news = new News();
				_news.setTitle(news.getTitle()); _news.setRid(news.getRid());
				_news.setContent(news.getContent()); _news.setAuthor(news.getAuthor());
				_news.setUrl(news.getUrl()); _news.setVideoUrl(news.getVideoUrl());
				_news.setFroms(news.getFroms()); _news.setTime(news.getTime());
				_news.setCreatDate(news.getCreatDate()); _news.setCategory(category);
				_news.setKeywords(news.getKeywords()); _news.setReview(news.getReview());
				_news.setState(news.getState()); _news.setIstop(news.getIstop());
				_news.setTypes(news.getTypes()); _news.setClicknumber(news.getClicknumber());
				_news.setIsimp(news.getIsimp()); _news.setWjtype(news.getWjtype());
				_news.setPubno(news.getPubno());
				newsService.save(_news);
			}
			ret.setCode("success");
			ret.setMessage("保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
    @RequestMapping("getNewsPage")
	public void getNewsPage(News news,EasyuiPage easyuiPage,PrintWriter out) {
    	ReturnValue ret = new ReturnValue();
	    try {
	    	String hql ="from News bean where bean.state=:state";
	    	Map params = new HashMap();
	    	params.put("state", 1);
	    	if(news.getCategory()!=null&&news.getCategory().getId()>0){
	    		hql+=" and bean.category.id=:cid";
	    		params.put("cid", news.getCategory().getId());
	    	}if(news.getTitle()!=null&&!"".equals(news.getTitle())){
	    		hql+=" and bean.title like:title";
	    		params.put("title", "%"+news.getTitle()+"%");
	    	}
	    	easyuiPage.setOrder("desc");
	    	easyuiPage.setSort("time");
	    	easyuiPage = newsService.showListPage(hql, easyuiPage, params);
	    	ret.setData(easyuiPage);
	    	ret.setCode("success");
	    	ret.setMessage("加载成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setData(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
    
    @RequestMapping("deleteNews")
    public void deleteNews(String ids,PrintWriter out){
    	ReturnValue ret = new ReturnValue();
    	try {
    		newsService.deleteids(ids);
    		ret.setCode("success");
    		ret.setMessage("删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
    }
    @RequestMapping("updateNewsByIsTop")
    public void updateNewsByIsTop(String ids,PrintWriter out,int isTop){
    	ReturnValue ret = new ReturnValue();
    	try {
			newsService.updateByIsTop(ids,isTop);
			ret.setCode("success");
			ret.setMessage("数据置顶成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
    }
    @RequestMapping("updateNewsByReview")
    public void updateNewsByReview(String ids,PrintWriter out,int review){
    	ReturnValue ret = new ReturnValue();
    	try {
    		newsService.updateNewsByReview(ids,review);
			ret.setCode("success");
			ret.setMessage("修改成功!");
		} catch (Exception e) {
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
    }
    
}
