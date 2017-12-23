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

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.TranOrgan;
import com.anxuan.xjzx.service.TranOrganService;

@Controller
@RequestMapping("/system/organ")
public class OrganController {
  @Resource
  private TranOrganService tranOrganService;
  @RequestMapping("loadAddOrgan")
  public ModelAndView loadAddOrgan(HttpServletRequest request,HttpServletResponse response){
	  JModelAndView mav = new JModelAndView("/trainOrgan/organ/organ_edit", "ADMIN", request, response);
	  return mav;
  }
  @RequestMapping("loadUpdateOrgan")
  public ModelAndView loadUpdateOrgan(HttpServletRequest request,HttpServletResponse response){
	  JModelAndView mav = new JModelAndView("/trainOrgan/organ/organ_edit", "ADMIN", request, response);
	  return mav;
  }
  @RequestMapping("addOrgan")
  public void addOrgan(TranOrgan tranOrgan,PrintWriter out){
	ReturnValue ret = new ReturnValue();
	try {
		tranOrganService.save(tranOrgan);
		ret.setCode("success");
		ret.setMessage("新增成功!");
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
  @RequestMapping("updateOrgan")
  public void updateOrgan(TranOrgan tranOrgan,PrintWriter out){
	ReturnValue ret = new ReturnValue();
	try {
		tranOrganService.update(tranOrgan);
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
  @RequestMapping("deleteOrgan")
  public void deleteOrgan(String ids,PrintWriter out){
	ReturnValue ret = new ReturnValue();
	try {
		tranOrganService.deleteids(ids);
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
 
  @RequestMapping("getOrganPage")
  public void getOrganPage(TranOrgan tranOrgan,EasyuiPage easyuiPage,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		  String hql ="from TranOrgan bean where bean.state=:state";
		  Map params = new HashMap();
		  params.put("state", 1);
		  if(tranOrgan.getName()!=null&&!"".equals(tranOrgan.getName())){
			  hql+=" and bean.name like :name";
			  params.put("name", "%"+tranOrgan.getName()+"%");
		  }if(tranOrgan.getContacts()!=null&&!"".equals(tranOrgan.getContacts())){
			  hql+=" and bean.contacts like :contacts";
			  params.put("contacts", "%"+tranOrgan.getContacts()+"%");
		  }if(tranOrgan.getType()>0){
			  hql+=" and bean.type=:type";
			  params.put("type", tranOrgan.getType());
		  }
		  easyuiPage.setOrder("desc");
	      easyuiPage.setSort("id");
		  easyuiPage = tranOrganService.showListPage(hql, easyuiPage, params);
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
