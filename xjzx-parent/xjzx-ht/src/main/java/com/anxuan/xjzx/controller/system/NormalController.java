package com.anxuan.xjzx.controller.system;

import java.io.PrintWriter;
import java.util.HashMap;
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
import com.anxuan.xjzx.bean.Normal;
import com.anxuan.xjzx.service.NormalService;

@Controller
@RequestMapping("/system/normal")
public class NormalController {
  @Resource
  private NormalService normalService;
  @RequestMapping("index")
  public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
	  JModelAndView mav = new JModelAndView("/normal/normal_index", "ADMIN", request, response);
	  return mav;
  }
  @RequestMapping("getPage")
  public void getPage(Normal normal,EasyuiPage easyuiPage,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  String hql = "from Normal bean where bean.state=:state";
		  Map params = new HashMap();
		  params.put("state", 1);
		  if(normal.getTitle()!=null&&!"".equals(normal.getTitle())){
			  hql+=" and bean.title like:title";
			  params.put("title", "%"+normal.getTitle()+"%");
		  }if(normal.getContent()!=null&&!"".equals(normal.getContent())){
			  hql+=" and bean.content like:content";
			  params.put("content", "%"+normal.getContent()+"%");
		  }
		  easyuiPage =  normalService.showListPage(hql, easyuiPage, params);
		  ret.setData(easyuiPage);
		  ret.setCode("success");
		  ret.setMessage("加载成功!");
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
  @RequestMapping("loadAddNormal")
  public ModelAndView loadAdd(HttpServletRequest request,HttpServletResponse response){
	  JModelAndView mav = new JModelAndView("/normal/normal_edit", "ADMIN", request, response);
	  return mav;
  }
  @RequestMapping("loadUpdateNormal")
  public ModelAndView loadUpdate(HttpServletRequest request,HttpServletResponse response){
	  JModelAndView mav = new JModelAndView("/normal/normal_edit", "ADMIN", request, response);
	  return mav;
  }
  @RequestMapping("addNormal")
  public void addNormal(Normal normal,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  normalService.save(normal);
		  ret.setCode("success");
		  ret.setMessage("新增成功!");
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
  @RequestMapping("updateNormal")
  public void updateNormal(Normal normal,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  normalService.update(normal);
		  ret.setCode("success");
		  ret.setMessage("修改成功!");
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
  @RequestMapping("deleteNormal")
  public void deleteNormal(String ids,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  normalService.deleteByIds(ids);
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
}
