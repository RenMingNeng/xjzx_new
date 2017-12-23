package com.anxuan.xjzx.controller.front;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.xmlbeans.impl.xb.xsdschema.impl.ListDocumentImpl.ListImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.FocusNews;
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.bean.Normal;
import com.anxuan.xjzx.bean.TrainTeacher;
import com.anxuan.xjzx.bean.Traindata;
import com.anxuan.xjzx.bean.TranOrgan;
import com.anxuan.xjzx.service.FocusNewsService;
import com.anxuan.xjzx.service.NewsService;
import com.anxuan.xjzx.service.NormalService;
import com.anxuan.xjzx.service.TrainTeacherService;
import com.anxuan.xjzx.service.TraindataService;
import com.anxuan.xjzx.service.TranOrganService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.StripHtml;

@Controller
public class FrontController {
	@Resource
	private NewsService newsService;
	@Resource
	private TranOrganService tranOrganService;
	@Resource
	private TrainTeacherService trainTeacherService;
	@Resource
	private TraindataService traindataService;
	@Resource
	private FocusNewsService focusNewsService;
	@Resource
	private NormalService normalService;
	@Resource
	private DictService dictService;
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/index", "USER", request, response);
		//轮播新闻(从中心新闻倒叙中提取有三条有图片的新闻)
		Map params = new HashMap();
		params.put("state", 1);
		params.put("review", 1);
		params.put("cid", 49);  //新闻类别为中心新闻
		params.put("url", "%FileUpload%");
		List<News> jdNewsList = newsService.find("from News bean where bean.state=:state and bean.url like :url and bean.review=:review and bean.category.id=:cid order by bean.time desc ", params, 0,3);
		mav.addObject("jdList", jdNewsList);
		mav.addObject("html", new StripHtml());
		//安监要闻
		params.clear();
		params.put("state", 1);
		params.put("review", 1);
		params.put("cid", 52);
		List<News> ajywNewsList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0,6); 
		mav.addObject("ajywNewsList", ajywNewsList);
		//中心新闻
		params.clear();
		params.put("state", 1);
		params.put("review", 1);
		params.put("cid", 49);
		List<News> dynamicList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0, 6); 
        mav.addObject("dynamicList", dynamicList);
       //公告公示
  		params.clear();
  		params.put("state", 1);
  		params.put("review", 1);
  		params.put("cid", 50);
  		List<News> ggNewsList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0,6); 
  		mav.addObject("ggNewsList", ggNewsList);
  		//通知公告(工作动态)
  		params.clear();
  		params.put("state", 1);
  		params.put("review", 1);
  		params.put("cid", 45);
  		List<News> tzNewsList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0, 6); 
        mav.addObject("tzNewsList", tzNewsList);
        /*//全省培训机构
        params.clear();
  		params.put("state", 1);
  		params.put("type", 1);
        List<TranOrgan> organList = tranOrganService.find("from TranOrgan bean where bean.state=:state and bean.type=:type order by bean.area.id desc", params, 0, 8);
        mav.addObject("organList", organList);
        //全省考试中心
        params.clear();
 	    params.put("state", 1);
 	    params.put("type", 2);
        List<TranOrgan> resumeList = tranOrganService.find("from TranOrgan bean where bean.state=:state and bean.type=:type", params, 0, 8);
        mav.addObject("resumeList", resumeList);
        //全省考试点
        params.clear();
	    params.put("state", 1);
	    params.put("type", 3);
        List<TranOrgan> examList = tranOrganService.find("from TranOrgan bean where bean.state=:state and bean.type=:type", params, 0, 8);
        mav.addObject("examList", examList);
        //全省监考人员名单
        params.clear();
        params.put("state", 1);
        params.put("type", 1);
        List<TrainTeacher> proctorList =  trainTeacherService.find("from TrainTeacher bean where bean.state=:state and bean.type=:type", params, 0, 6);
        mav.addObject("proctorList", proctorList);
        //全省考评人员名单
        params.clear();
        params.put("state", 1);
        params.put("type", 3);
        List<TrainTeacher> evaluateList =  trainTeacherService.find("from TrainTeacher bean where bean.state=:state and bean.type=:type", params, 0, 6);
        mav.addObject("evaluateList", evaluateList);
       //全省安全培训师资名单
        params.clear();
        params.put("state", 1);
        params.put("type", 2);
        List<TrainTeacher> teacherList =  trainTeacherService.find("from TrainTeacher bean where bean.state=:state and bean.type=:type", params, 0, 6);
        mav.addObject("teacherList", teacherList);*/
        //培训资料
        params.clear();
        params.put("state", 1);
        List<Traindata> traindataList = traindataService.find("from Traindata bean where bean.state=:state", params, -1, -1);
		mav.addObject("traindataList", traindataList);
		//安全教育
  		params.clear();
  		params.put("state", 1);
  		params.put("review", 1);
  		params.put("cid", 25);
  		List<News> aqjyList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.istop,bean.time desc", params, 0, 6); 
        mav.addObject("aqjyList", aqjyList);
		//安全生产
		params.clear();
		params.put("state", 1);
		List<FocusNews> focusNewList = focusNewsService.find("from FocusNews bean where bean.state=:state", params, 0, 2);
        mav.addObject("focusNewList", focusNewList);
        /*//党员工作
        params.clear();
		params.put("state", 1);
        List<Normal> normalList =   normalService.find("from Normal bean where bean.state=:state order by bean.time desc", params, 0, 3);
		mav.addObject("normalList", normalList);*/
		//安全文化
		params.clear();
		params.put("state", 1);
		params.put("review", 1);
		params.put("cid1", 42);
		params.put("cid2", 44);
		List<News> aqwhList = newsService.find("from News bean where bean.state=:state and bean.review=:review and (bean.category.id =:cid1 or bean.category.id =:cid2) order by bean.istop,bean.time desc", params, 0,6); 
		mav.addObject("aqwhList", aqwhList);
		// 案例示警
		params.clear();
		params.put("state", 1);
		params.put("review", 1);
		params.put("cid", 44);
		List<News> sgalList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.istop,bean.time desc", params, 0,6); 
		mav.addObject("sgalList", sgalList);
		// 案例示警（视频）
		params.clear();
		params.put("state", 1);
		params.put("review", 1);
		params.put("video_isvalid", 1);
		params.put("cid", 44);
		params.put("videoUrl", "%FileUpload%"); 
		List<News> aqwhVideo = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid and bean.video_isvalid =:video_isvalid and bean.videoUrl like :videoUrl order by bean.istop,bean.time desc", params, 0,1);
		if(aqwhVideo.size()>0){
			mav.addObject("aqwhVideo", aqwhVideo.get(0));
		}
		//安全知识 
		params.clear();
		params.put("state", 1);
		params.put("review", 1);
		params.put("cid", 42);
		List<News> aqzsList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.istop,bean.time desc", params, 0,6); 
		mav.addObject("aqzsList", aqzsList);
		// 宣传报道
  		params.clear();
  		params.put("state", 1);
  		params.put("review", 1);
  		params.put("cid", 74);
  		List<News> xcbdcList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0, 6); 
        mav.addObject("xcbdcList", xcbdcList);
        // 宣传报道（视频）
 		params.clear();
 		params.put("state", 1); 
 		params.put("review", 1);
 		params.put("video_isvalid", 1);
 		params.put("cid", 74);
 		params.put("videoUrl", "%FileUpload%");
 		List<News> xcbdVideo = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid and bean.video_isvalid =:video_isvalid and bean.videoUrl like :videoUrl order by bean.istop,bean.time desc", params, 0,1);
 		if(xcbdVideo.size()>0){
 			mav.addObject("xcbdVideo", xcbdVideo.get(0));
 		}
        // 政策解读
  		params.clear();
  		params.put("state", 1);
  		params.put("review", 1);
  		params.put("cid", 75);
  		List<News> zcjdList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0, 6); 
        mav.addObject("zcjdList", zcjdList);
        // 政策解读（视频）
  		params.clear();
  		params.put("state", 1);
  		params.put("review", 1);
  		params.put("video_isvalid", 1);
  		params.put("cid", 75);
  		params.put("videoUrl", "%FileUpload%");
  		List<News> zcjdVideo = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid and bean.video_isvalid =:video_isvalid and bean.videoUrl like :videoUrl order by bean.istop,bean.time desc", params, 0,1);
  		if(zcjdVideo.size()>0){
  			mav.addObject("zcjdVideo", zcjdVideo.get(0));
  		}
        // 资料下载
        params.clear();
  		params.put("state", 1);
  		params.put("review", 1);
  		params.put("cid", 29);
  		List<News> zlxzList = newsService.find("from News bean where bean.state=:state and bean.review=:review and bean.category.id =:cid order by bean.time desc", params, 0, 5); 
        mav.addObject("zlxzList", zlxzList);
        // 主题栏目
  		params.clear();
  		params.put("dictValue", "1");
  		params.put("pid", (long)143);
  		List<Dict> ztlmList = dictService.find("from Dict bean where bean.dictValue=:dictValue and bean.parent.id =:pid", params, 0,3); 
  		mav.addObject("ztlmList", ztlmList);
        return mav;
	}
	
	/**
	 * 整站搜索
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/search")
	public ModelAndView search(HttpServletRequest request, HttpServletResponse response, String page, String textfield) {
		JModelAndView mav = new JModelAndView("/news_search_list", "USER", request, response);
		Map params = new HashMap();
		params.put("state", 1);
		params.put("review", 1);
		params.put("textfield", "%"+textfield+"%");
		int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
	    InformationPage<News> newsPage = new InformationPage<News>(10, curPage);
	    newsPage.setCount_all(newsService.find("from News bean where bean.state=:state and (bean.title like:textfield or bean.content like:textfield) and bean.review =:review order by bean.time desc", params, -1, -1).size());
	    newsPage.setRows(newsService.find("from News bean where bean.state=:state and (bean.title like:textfield or bean.content like:textfield) and bean.review =:review  order by bean.time desc", params, newsPage.getStartPoint(), newsPage.getPage_size()));
	    mav.addObject("newsPage", newsPage);
	    mav.addObject("html", new StripHtml());
	    mav.addObject("textfield", textfield);
	    mav.addObject("page", curPage);
	    return mav;
	}

	@RequestMapping("/top")
	public ModelAndView top(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/top", "USER", request, response);
		return mav;
	}

	@RequestMapping("/foot")
	public ModelAndView foot(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/foot", "USER", request, response);
		return mav;
	}
}
