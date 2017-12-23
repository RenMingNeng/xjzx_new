package com.anxuan.xjzx.controller.front;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.bean.NewsCategory;
import com.anxuan.xjzx.bean.Normal;
import com.anxuan.xjzx.bean.Person;
import com.anxuan.xjzx.bean.Question;
import com.anxuan.xjzx.bean.QuestionOption;
import com.anxuan.xjzx.service.NewsCategoryService;
import com.anxuan.xjzx.service.NewsService;
import com.anxuan.xjzx.service.PersonService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.MessageUtf8;
import com.anxuan.xjzx.util.StripHtml;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/news")
public class NewsFController {
	 @Resource
	 private NewsService newsService;
	 @Resource
	 private NewsCategoryService newsCategoryService;
	 @Resource
	 private PersonService personService;
 
	 @RequestMapping("detail")
	  public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,int id){
		JModelAndView  mav = new JModelAndView("/news_detail", "USER", request, response); 
		News news = newsService.getNewsById(id);
		if(news!=null&&news.getWjtype()!=null){
			news.setClicknumber(news.getClicknumber()+1);
			mav = new JModelAndView("/news_major", "USER", request, response);
		}else{
			news.setClicknumber(news.getClicknumber()+1);
			//点击量排行
			Map params = new HashMap();
			params.put("state", 1);
			params.put("review", 1);
			List<News> clickNumberList = newsService.find("from News bean where bean.state=:state and bean.review=:review order by bean.clicknumber desc ", params, 0, 10);
		    mav.addObject("clickNumberList", clickNumberList);
		    //推荐新闻
		    params.clear();
		    params.put("state", 1);
		    params.put("cid", news.getCategory().getId());
		    params.put("review", 1);
		    List<News> tjNewsList = newsService.find("from News bean where bean.state=:state and bean.category.id=:cid and bean.review=:review order by bean.time desc", params, 0, 10);
		    mav.addObject("tjNewsList", tjNewsList);
		    mav.addObject("html", new StripHtml());
		}
		mav.addObject("news", news);
		return mav;
	  }
	 
	 @RequestMapping("list")
	  public ModelAndView Newslist(HttpServletRequest request,HttpServletResponse response,int categoryid,String page){
		 JModelAndView mav = new JModelAndView("/news_category_list", "USER", request, response);
		 NewsCategory category = newsCategoryService.getNewsCategoryById(categoryid);
		 Map params = new HashMap();
		 params.put("state", 1);
		 params.put("cid", categoryid);
		 if(category!=null&&category.getParent()!=null){
			 params.put("cid", category.getParent().getId());
		 }
		 List<NewsCategory> newsCategoryList = newsCategoryService.find("from NewsCategory bean where bean.state=:state and bean.parent.id=:cid", params, -1, -1);
		 if(category!=null&&category.getParent()==null&&newsCategoryList!=null&&newsCategoryList.size()>0){
			 categoryid = newsCategoryList.get(0).getId();
		 }
		 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
		 InformationPage<News> newsPage = new InformationPage<News>(10, curPage);
		 params.clear();
		 params.put("state", 1);
		 params.put("review", 1);
		 params.put("categoryid", categoryid);
		 newsPage.setCount_all(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review order by bean.time desc", params, -1, -1).size());
		 newsPage.setRows(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review  order by bean.time desc", params, newsPage.getStartPoint(), newsPage.getPage_size()));
		 mav.addObject("newsCategoryList", newsCategoryList);
		 mav.addObject("categoryid", categoryid);
		 mav.addObject("newsPage", newsPage);
		 mav.addObject("category", newsCategoryService.getNewsCategoryById(categoryid));
		 mav.addObject("html", new StripHtml());
		 mav.addObject("page", curPage);
		 
		 return mav;
	  }
	 
	 /**
	  * 首页进入中心新闻列表
	  * @param request
	  * @param response
	  * @param page
	  * @return
	  */
	 @RequestMapping("centerNewsList")
	 public ModelAndView centerNewsList(HttpServletRequest request,HttpServletResponse response,String page){
		 int categoryid = 49;
		 JModelAndView mav = new JModelAndView("/center_news_list", "USER", request, response);
		 NewsCategory category = newsCategoryService.getNewsCategoryById(categoryid);
		 Map params = new HashMap();
		 params.put("state", 1);
		 params.put("cid", categoryid);
		 if(category!=null&&category.getParent()!=null){
			 params.put("cid", category.getParent().getId());
		 }
		 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
		 InformationPage<News> newsPage = new InformationPage<News>(15, curPage);
		 params.clear();
		 params.put("state", 1);
		 params.put("review", 1);
		 params.put("categoryid", categoryid);
		 newsPage.setCount_all(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review order by bean.time desc", params, -1, -1).size());
		 newsPage.setRows(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review  order by bean.time desc", params, newsPage.getStartPoint(), newsPage.getPage_size()));
		 mav.addObject("categoryid", categoryid);
		 mav.addObject("newsPage", newsPage);
		 mav.addObject("category", newsCategoryService.getNewsCategoryById(categoryid));
		 mav.addObject("html", new StripHtml());
		 mav.addObject("page", curPage);
		 return mav;
	 }
	 
	 /**
	  * 首页进入工作动态列表
	  * @param request
	  * @param response
	  * @param page
	  * @return
	  */
	 @RequestMapping("dynamicList")
	 public ModelAndView dynamicList(HttpServletRequest request,HttpServletResponse response,String page){
		 int categoryid = 45;
		 JModelAndView mav = new JModelAndView("/dynamic_list", "USER", request, response);
		 NewsCategory category = newsCategoryService.getNewsCategoryById(categoryid);
		 Map params = new HashMap();
		 params.put("state", 1);
		 params.put("cid", categoryid);
		 if(category!=null&&category.getParent()!=null){
			 params.put("cid", category.getParent().getId());
		 }
		 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
		 InformationPage<News> newsPage = new InformationPage<News>(15, curPage);
		 params.clear();
		 params.put("state", 1);
		 params.put("review", 1);
		 params.put("categoryid", categoryid);
		 newsPage.setCount_all(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review order by bean.time desc", params, -1, -1).size());
		 newsPage.setRows(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review  order by bean.time desc", params, newsPage.getStartPoint(), newsPage.getPage_size()));
		 mav.addObject("categoryid", categoryid);
		 mav.addObject("newsPage", newsPage);
		 mav.addObject("category", newsCategoryService.getNewsCategoryById(categoryid));
		 mav.addObject("html", new StripHtml());
		 mav.addObject("page", curPage);
		 return mav;
	 }
	 
	 
	 /**
	  * 首页进入公示公告列表
	  * @param request
	  * @param response
	  * @param page
	  * @return
	  */
	 @RequestMapping("publicNoticeList")
	 public ModelAndView publicNoticeList(HttpServletRequest request,HttpServletResponse response,String page){
		 int categoryid = 50;
		 JModelAndView mav = new JModelAndView("/public_notice_list", "USER", request, response);
		 NewsCategory category = newsCategoryService.getNewsCategoryById(categoryid);
		 Map params = new HashMap();
		 params.put("state", 1);
		 params.put("cid", categoryid);
		 if(category!=null&&category.getParent()!=null){
			 params.put("cid", category.getParent().getId());
		 }
		 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
		 InformationPage<News> newsPage = new InformationPage<News>(15, curPage);
		 params.clear();
		 params.put("state", 1);
		 params.put("review", 1);
		 params.put("categoryid", categoryid);
		 newsPage.setCount_all(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review order by bean.time desc", params, -1, -1).size());
		 newsPage.setRows(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review  order by bean.time desc", params, newsPage.getStartPoint(), newsPage.getPage_size()));
		 mav.addObject("categoryid", categoryid);
		 mav.addObject("newsPage", newsPage);
		 mav.addObject("category", newsCategoryService.getNewsCategoryById(categoryid));
		 mav.addObject("html", new StripHtml());
		 mav.addObject("page", curPage);
		 return mav;
	 }
	 
	 /**
	  * 首页进入资料下载列表
	  * @param request
	  * @param response
	  * @param page
	  * @return
	  */
	 @RequestMapping("materialList")
	 public ModelAndView materialList(HttpServletRequest request,HttpServletResponse response,String page){
		 int categoryid = 29;
		 JModelAndView mav = new JModelAndView("/material_list", "USER", request, response);
		 NewsCategory category = newsCategoryService.getNewsCategoryById(categoryid);
		 Map params = new HashMap();
		 params.put("state", 1);
		 params.put("cid", categoryid);
		 if(category!=null&&category.getParent()!=null){
			 params.put("cid", category.getParent().getId());
		 }
		 int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
		 InformationPage<News> newsPage = new InformationPage<News>(15, curPage);
		 params.clear();
		 params.put("state", 1);
		 params.put("review", 1);
		 params.put("categoryid", categoryid);
		 newsPage.setCount_all(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review order by bean.time desc", params, -1, -1).size());
		 newsPage.setRows(newsService.find("from News bean where bean.state=:state and bean.category.id=:categoryid and bean.review =:review  order by bean.time desc", params, newsPage.getStartPoint(), newsPage.getPage_size()));
		 mav.addObject("categoryid", categoryid);
		 mav.addObject("newsPage", newsPage);
		 mav.addObject("category", newsCategoryService.getNewsCategoryById(categoryid));
		 mav.addObject("html", new StripHtml());
		 mav.addObject("page", curPage);
		 return mav;
	 }
 
	 // 公开栏目下(中心概况、办证流程、科室职责)
	 @RequestMapping("newsContent")
	 public void newsContent(HttpServletRequest request,HttpServletResponse response){
		  Integer categoryId = Integer.valueOf(request.getParameter("categoryId"));
		  News news =null;
		  Map params = new HashMap();
		  params.put("categoryId", categoryId);
		  List<News> newsList = newsService.find("from News bean where bean.category.id=:categoryId order by bean.creatDate desc", params, -1, -1);
		  if(null != newsList && newsList.size() > 0){
			  news = newsList.get(0);   // 默认获取显示最新的一条数据
			  try {
				  MessageUtf8.writeMessageUft8(response, news.getContent());
			  } catch (Exception e) {
				  e.printStackTrace();
			  }
		  }
		  
	 }

	 // 公开栏目下(人员机构列表)
	 @RequestMapping("personList")
	 public void personList(PrintWriter out){
		 ReturnValue ret = new ReturnValue();
		try {
			  Map params = new HashMap();
			  params.put("is_valid", 1);
			  List<Person> personList = personService.find("from Person bean where bean.is_valid =:is_valid", params, -1, -1);
			  ret.setListData(personList);
			  ret.setCode("success");
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
}
