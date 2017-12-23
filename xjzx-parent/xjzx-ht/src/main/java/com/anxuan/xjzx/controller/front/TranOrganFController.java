package com.anxuan.xjzx.controller.front;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.TranOrgan;
import com.anxuan.xjzx.service.TranOrganService;
import com.anxuan.xjzx.util.OrganResult;

@Controller
public class TranOrganFController {
 @Resource
 private TranOrganService tranOrganService;
 @Resource
 private DictService dictService;
 @RequestMapping("/organ/list")
 public ModelAndView OrganList(HttpServletRequest request,HttpServletResponse response,String type){
	JModelAndView mav = new JModelAndView("/organ_list", "USER", request, response);
	List<OrganResult> organResultList = new ArrayList<OrganResult>();
	Map params = new HashMap();
	params.put("deleteStatus", false);
	params.put("pdictCode", "JGGS");
	List<Dict> dictList =dictService.find("from Dict bean where bean.parent.dictCode=:pdictCode and bean.deleteStatus=:deleteStatus ORDER BY addTime", params, -1, -1);
	params.clear();
	for(Dict dict:dictList){
		params.put("state", 1);
		params.put("type", 1);
		params.put("areaid", dict.getId());
		OrganResult result = new OrganResult();
		result.setCityName(dict.getDictName());
		result.setTranOrgans(tranOrganService.find("from TranOrgan bean where bean.state=:state and bean.type=:type and bean.area.parent.id=:areaid", params, -1, -1));
		organResultList.add(result);
	}
	mav.addObject("title", "全省培训机构");
	mav.addObject("organResultList", organResultList);
	return mav;
 }
 @RequestMapping("/resume/list")
 public ModelAndView ResumeList(HttpServletRequest request,HttpServletResponse response){
	 JModelAndView mav = new JModelAndView("/resume_list", "USER", request, response);
	 Map params = new  HashMap();
	 params.put("state", 1);
	 params.put("type", 2);
	 List<TranOrgan> resumeList =  tranOrganService.find("from TranOrgan bean where bean.state=:state and bean.type=:type", params, -1, -1);
	 mav.addObject("title", "全省考试中心");
	 mav.addObject("resumeList", resumeList);
	 return mav;
 }
 @RequestMapping("/exam/list")
 public ModelAndView ExamList(HttpServletRequest request,HttpServletResponse response){
	 JModelAndView mav = new JModelAndView("/exam_list", "USER", request, response);
	 List<OrganResult> examOrganList = new ArrayList<OrganResult>();
	 Map params = new HashMap();
	 params.put("deleteStatus", false);
	 params.put("pdictCode", "JGGS");
	 List<Dict> dictList =dictService.find("from Dict bean where bean.parent.dictCode=:pdictCode and bean.deleteStatus=:deleteStatus ORDER BY addTime", params, -1, -1);
	 params.clear();
	 for(Dict dict:dictList){
		params.put("state", 1);
		params.put("type", 3);
		params.put("areaid", dict.getId());
		OrganResult result = new OrganResult();
		result.setCityName(dict.getDictName());
		result.setTranOrgans(tranOrganService.find("from TranOrgan bean where bean.state=:state and bean.type=:type and bean.area.id=:areaid", params, -1, -1));
		examOrganList.add(result);
	 }
	 mav.addObject("title", "全省考试点");
	 mav.addObject("examOrganList", examOrganList);
	 return mav;
 }
 
 @RequestMapping("/tranOrgan/detail")
 public ModelAndView tranOrgan(HttpServletRequest request,HttpServletResponse response,int id){
	 JModelAndView mav = new JModelAndView("/organ_detail", "USER", request, response);
	 mav.addObject("organ", tranOrganService.getTranOrganById(id));
	 return mav;
 }
 

 @RequestMapping("/exam/detail")
 public ModelAndView exam(HttpServletRequest request,HttpServletResponse response,int id){
	 JModelAndView mav = new JModelAndView("/exam_detail", "USER", request, response);
	 mav.addObject("organ", tranOrganService.getTranOrganById(id));
	 return mav;
 }
}
