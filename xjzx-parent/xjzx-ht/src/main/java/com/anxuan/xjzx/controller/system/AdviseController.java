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
import com.anxuan.xjzx.bean.Advise;
import com.anxuan.xjzx.dao.AdviseDao;
import com.anxuan.xjzx.service.AdviseService;

@Controller
@RequestMapping("/system/advise")
public class AdviseController {
  @Resource
  private AdviseService adviseService;

  @RequestMapping("index")
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/advise/advise_index", "ADMIN", request, response);
		return mav;
	}
  
  
  @RequestMapping("loadUpdateAdvise")
  public ModelAndView loadUpdateAdvise(String ids,HttpServletRequest request,HttpServletResponse response){
	  JModelAndView mav = new JModelAndView("/advise/advise_edit", "ADMIN", request, response);
	  String adivseId = request.getParameter("id");
	  mav.addObject("adivseId", adivseId);
	  return mav;
  }
  
  @RequestMapping("updateAdvise")
  public void updateOrgan(Advise advise,PrintWriter out){
	ReturnValue ret = new ReturnValue();
	try {
		adviseService.update(advise);
		ret.setCode("success");
		ret.setMessage("修改成功!");
	} catch (Exception e) {
		e.printStackTrace();
		ret.setCode("failure");
		ret.setMessage(e.getMessage());
	}  finally {
		out.print(ret.toJsonString());
		out.flush();
		out.close();
	}
  }
  @RequestMapping("deleteAdvise")
  public void deleteAdvise(String ids,PrintWriter out){
	ReturnValue ret = new ReturnValue();
	try {
		
		adviseService.deleteids(ids);
		ret.setCode("success");
		ret.setMessage("删除成功!");
	} catch (Exception e) {
		e.printStackTrace();
		ret.setCode("failure");
		ret.setMessage(e.getMessage());
	}  finally {
		out.print(ret.toJsonString());
		out.flush();
		out.close();
	}
  }
  
  @RequestMapping("getAdvisePage")
  public void getAdvisePage(Advise singlead,EasyuiPage easyuiPage,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  String hql ="from Advise bean where bean.state=:state";
		  Map params = new HashMap();
		  params.put("state", 1);
		  if(singlead.getAdcontent()!=null&&!"".equals(singlead.getAdcontent())){
			  hql+=" and bean.adcontent like :adcontent";
			  params.put("adcontent", "%"+singlead.getAdcontent()+"%");
		  }if(singlead.getUsername()!=null&&!"".equals(singlead.getUsername())){
			  hql+=" and bean.username like :username";
			  params.put("username", "%"+singlead.getUsername()+"%");
		  }if(singlead.getPhone()!=null&&!"".equals(singlead.getPhone())){
			  hql+=" and bean.phone like :phone";
			  params.put("phone", "%"+singlead.getPhone()+"%");
		  }
		  easyuiPage.setOrder("desc");
	      easyuiPage.setSort("createdate");
	     
		  easyuiPage = adviseService.showListPage(hql, easyuiPage, params);
		  ret.setData(easyuiPage);
		  ret.setCode("success");
		  ret.setMessage("加载成功!");
	  } catch (Exception e) {
		  e.printStackTrace();
		  ret.setCode("success");
		  ret.setMessage(e.getMessage());
	 }finally {
		out.print(ret.toJsonString());
		out.flush();
		out.close();
	}
  }
}
