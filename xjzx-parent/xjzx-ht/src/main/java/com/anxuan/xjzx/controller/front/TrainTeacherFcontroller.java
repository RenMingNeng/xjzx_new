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
import com.anxuan.xjzx.bean.News;
import com.anxuan.xjzx.bean.TrainTeacher;
import com.anxuan.xjzx.bean.Traindata;
import com.anxuan.xjzx.service.TrainTeacherService;
import com.anxuan.xjzx.service.TraindataService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.StripHtml;

@Controller
public class TrainTeacherFcontroller {
@Resource
private TrainTeacherService trainTeacherService;
 //监牢人员列表
 @RequestMapping("/proctor/list")
 public ModelAndView proctor(HttpServletRequest request,HttpServletResponse response,String id,String page,String textSeacher){
	JModelAndView mav = new JModelAndView("/proctor_list", "USER", request, response);
	if(null == textSeacher){textSeacher = ""; }
	Map params = new HashMap();
	int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
	 InformationPage<TrainTeacher> proctorPage = new InformationPage<TrainTeacher>(20, curPage);
	params.put("state", 1);
	params.put("type",1);
	params.put("textSeacher","%"+textSeacher+"%");
	proctorPage.setCount_all(trainTeacherService.find("from TrainTeacher bean where bean.state=:state and (bean.certinum like:textSeacher or bean.name like:textSeacher) and bean.type=:type", params, -1, -1).size());
	proctorPage.setRows(trainTeacherService.find("from TrainTeacher bean where bean.state=:state and (bean.certinum like:textSeacher or bean.name like:textSeacher) and bean.type=:type order by bean.id desc", params, proctorPage.getStartPoint(), proctorPage.getPage_size()));
	//List<TrainTeacher> trainTeachers = trainTeacherService.find("from TrainTeacher bean where bean.state=:state and bean.type=:type", params, -1, -1);
	mav.addObject("id", CommUtil.null2Int(id));
	mav.addObject("title", "全省监考人员名单");
	//mav.addObject("trainTeachers", trainTeachers);
	mav.addObject("proctorPage", proctorPage);
	mav.addObject("textSeacher", textSeacher);
	mav.addObject("html", new StripHtml());
	mav.addObject("page", curPage);
	return mav;
 }
 @RequestMapping("/evaluate/list")
 public ModelAndView evaluate(HttpServletRequest request,HttpServletResponse response,String id,String page,String textSeacher){
	JModelAndView mav = new JModelAndView("/evaluate_list", "USER", request, response);
	if(null == textSeacher){textSeacher = ""; }
	Map params = new HashMap();
	int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
	 InformationPage<TrainTeacher> evaluatePage = new InformationPage<TrainTeacher>(20, curPage);
	params.put("state", 1);
	params.put("type",3);
	params.put("textSeacher","%"+textSeacher+"%");
	evaluatePage.setCount_all(trainTeacherService.find("from TrainTeacher bean where bean.state=:state and (bean.certinum like:textSeacher or bean.name like:textSeacher) and bean.type=:type", params, -1, -1).size());
	evaluatePage.setRows(trainTeacherService.find("from TrainTeacher bean where bean.state=:state and (bean.certinum like:textSeacher or bean.name like:textSeacher) and bean.type=:type order by bean.id desc", params, evaluatePage.getStartPoint(), evaluatePage.getPage_size()));
	//List<TrainTeacher> trainTeachers = trainTeacherService.find("from TrainTeacher bean where bean.state=:state and bean.type=:type", params, -1, -1);
	mav.addObject("id", CommUtil.null2Int(id));
	mav.addObject("title", "全省考评人员名单");
	//mav.addObject("trainTeachers", trainTeachers);
	mav.addObject("evaluatePage", evaluatePage);
	mav.addObject("textSeacher", textSeacher);
	mav.addObject("html", new StripHtml());
	mav.addObject("page", curPage);
	return mav;
 }
 
 @RequestMapping("/teacher/list")
 public ModelAndView teacher(HttpServletRequest request,HttpServletResponse response,String id,String page,String textSeacher){
	JModelAndView mav = new JModelAndView("/teacher_list", "USER", request, response);
	if(null == textSeacher){textSeacher = ""; }
	Map params = new HashMap();
	int curPage =page==null||"".equals(page)?1:CommUtil.null2Int(page);
	 InformationPage<TrainTeacher> teacherPage = new InformationPage<TrainTeacher>(20, curPage);
	params.put("state", 1);
	params.put("type",2);
	params.put("textSeacher","%"+textSeacher+"%");
	//List<TrainTeacher> trainTeachers = trainTeacherService.find("from TrainTeacher bean where bean.state=:state and bean.type=:type", params, -1, -1);
	teacherPage.setCount_all(trainTeacherService.find("from TrainTeacher bean where bean.state=:state and (bean.certinum like:textSeacher or bean.name like:textSeacher) and bean.type=:type", params, -1, -1).size());
	teacherPage.setRows(trainTeacherService.find("from TrainTeacher bean where bean.state=:state and (bean.certinum like:textSeacher or bean.name like:textSeacher) and bean.type=:type order by bean.id desc", params, teacherPage.getStartPoint(), teacherPage.getPage_size()));
	mav.addObject("id", CommUtil.null2Int(id));
	//mav.addObject("trainTeachers", trainTeachers);
	mav.addObject("title", "全省安全培训师资名单");
	mav.addObject("teacherPage", teacherPage);
	mav.addObject("textSeacher", textSeacher);
	mav.addObject("html", new StripHtml());
	mav.addObject("page", curPage);
	return mav;
 }
}
