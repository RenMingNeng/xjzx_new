package com.anxuan.xjzx.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.FocusNews;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.service.FocusNewsService;
import com.anxuan.xjzx.service.NewsService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.StripHtml;

@Controller
public class FocusNewsFController {
	@Resource
	private FocusNewsService focusNewsService;
	@Resource
	private NewsService newsService;
	@RequestMapping("/september/list")
   public ModelAndView index(HttpServletRequest request,HttpServletResponse response,String page){
	  JModelAndView mav = new JModelAndView("/september_list", "USER", request, response);
	   int curPage = page==null||"".equals(page)?1:CommUtil.null2Int(page);
	   InformationPage<FocusNews> fnewsPage = new InformationPage<FocusNews>(5, curPage);
	   Map params = new HashMap();
	   params.put("state", 1);
	   fnewsPage.setCount_all(focusNewsService.find("from FocusNews bean where bean.state=:state", params, -1, -1).size());
	   fnewsPage.setRows(focusNewsService.find("from FocusNews bean where bean.state=:state", params,fnewsPage.getStartPoint() , fnewsPage.getPage_size()));
	   mav.addObject("fnewsPage", fnewsPage);
	   mav.addObject("page", curPage);
		//安全动态
		params.clear();
		params.put("state", 1);
		params.put("review", 1);
		params.put("cid", 24);
		List<News> dynamicList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0, 7); 
       mav.addObject("dynamicList", dynamicList);
       mav.addObject("html",new StripHtml());
	   return mav;
  }
	@RequestMapping("/september/detail")
	public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,int  id){
		JModelAndView mav = new JModelAndView("/september_detail", "USER", request, response);
	    mav.addObject("september", focusNewsService.getFocusNewsById(id));
	    return mav;
	}
}
