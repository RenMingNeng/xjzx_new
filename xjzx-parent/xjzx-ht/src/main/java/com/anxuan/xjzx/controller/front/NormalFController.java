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

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.Normal;
import com.anxuan.xjzx.service.NormalService;
import com.anxuan.xjzx.util.InformationPage;
import com.anxuan.xjzx.util.MessageUtf8;
import com.anxuan.xjzx.util.StripHtml;

@Controller
public class NormalFController {
	@Resource
	private NormalService normalService;
	@Resource
	private DictService dictService;
	
  @RequestMapping("/normal/detail")
  public ModelAndView detail(HttpServletRequest request,HttpServletResponse response,long pid){
	  JModelAndView mav = new JModelAndView("/normal_detail", "USER", request, response);
	  Normal normal =null;
	  Map params = new HashMap();
	  params.put("state", 1);
	  params.put("pid", pid);
	  List<Normal> normals = normalService.find("from Normal bean where bean.state=:state and bean.normalType.id=:pid", params, -1, -1);
	  if(normals!=null){
		  normal = normals.get(0);
	  }
	  mav.addObject("normal",normal);
	  return mav;
  }
  
  @RequestMapping("/normal/list")
  public JModelAndView list(HttpServletRequest request,HttpServletResponse response,long pid,String page){
	  JModelAndView mav = new JModelAndView("/normal_list", "USER", request, response);
	  int curPage = page==null||"".equals(page)?1:CommUtil.null2Int(page);
	  InformationPage<Normal> normalPage = new InformationPage<Normal>(10, curPage);
	  Map params = new HashMap();
	  params.put("state", 1);
	  params.put("pid", pid);
	  normalPage.setCount_all(normalService.find("from Normal bean where state=:state and  bean.normalType.id=:pid", params, -1, -1).size());
	  normalPage.setRows(normalService.find("from Normal bean where state=:state and  bean.normalType.id=:pid order by bean.time desc", params, normalPage.getStartPoint(),normalPage.getPage_size()));
	  Dict dict = null;
	  params.clear();
	  params.put("id", pid);
	  List<Dict> dicts = dictService.find("from Dict bean where bean.id=:id", params, -1, -1);
	  if(dicts != null){
		 dict =  dicts.get(0);
	  }
	  mav.addObject("normalPage", normalPage);
	  mav.addObject("page", curPage);
	  mav.addObject("pid", pid);
	  mav.addObject("dict",dict);
	  mav.addObject("html", new StripHtml());
	  return mav;
  }
  
  @RequestMapping("/normal/edit")
  public ModelAndView edit(HttpServletRequest request,HttpServletResponse response,int id){
	  JModelAndView mav = new JModelAndView("/normal_edit", "USER", request, response);
	  mav.addObject("normal", normalService.getNormalById(id));
	  return mav;
  }

}
