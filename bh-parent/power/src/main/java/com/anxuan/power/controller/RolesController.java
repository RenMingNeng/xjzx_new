package com.anxuan.power.controller;

import java.io.PrintWriter;
import java.util.Date;
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
import com.anxuan.power.bean.Roles;
import com.anxuan.power.service.RolesService;

@Controller
public class RolesController {
  @Resource
  private RolesService rolesService;
  @RequestMapping("/system/roles/showRolesList")
  public ModelAndView showRolesList(HttpServletRequest request,HttpServletResponse response){
	  JModelAndView mav = new JModelAndView("/roles/roles_list", "ADMIN", request, response);
	  return mav;
  }
  //加载所有数据
  @RequestMapping("/system/roles/showRolesJson")
  public void showRolesJson(Roles roles,EasyuiPage easyuiPage,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	 try {
		Map parms = new HashMap();
		parms.put("deleteStatus", false);
		String hql = "from Roles bean where bean.deleteStatus=:deleteStatus";
		if(!"".equals(roles.getRoleKey())&&roles.getRoleKey()!=null){
			hql +=" and bean.roleKey like :roleKey";
			parms.put("roleKey", "%"+roles.getRoleKey()+"%");
		}if(!"".equals(roles.getName())&&roles.getName()!=null){
			hql +=" and bean.name like :name";
			parms.put("name", "%"+roles.getName()+"%");
		}
		easyuiPage = rolesService.showListPage(hql, easyuiPage, parms);
		ret.setData(easyuiPage);
		ret.setCode("success");
		ret.setMessage("加载成功!");
	 } catch (Exception e) {
		e.printStackTrace();
		ret.setCode("failure");
		ret.setMessage(e.getMessage());
	 }finally {
		out.println(ret.toJsonString());
		out.flush();
		out.close();
	}
	  
  }
  //加载新增或修改页面
  @RequestMapping("/system/roles/rolesEdit")
  public ModelAndView rolesEdit(HttpServletRequest request,HttpServletResponse response){
	 JModelAndView mav = new JModelAndView("/roles/roles_edit", "ADMIN", request, response);
     return mav;
  }
  @RequestMapping("/system/roles/addRoles")
  public void addRoles(Roles roles,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		roles.setAddTime(new Date());
		rolesService.save(roles);
		ret.setCode("success");
		ret.setMessage("新增成功!");
	} catch (Exception e) {
		e.printStackTrace();
		ret.setCode("failure");
		ret.setMessage(e.getMessage());
	}finally {
		out.println(ret.toJsonString());
		out.flush();
		out.close();
	}
  }
  @RequestMapping("/system/roles/updateRoles")
  public void updateRoles(Roles roles,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		roles.setAddTime(new Date());
		rolesService.updateRoles(roles);
		ret.setCode("success");
		ret.setMessage("修改成功!");
	} catch (Exception e) {
		e.printStackTrace();
		ret.setCode("failure");
		ret.setMessage(e.getMessage());
	}finally {
		out.println(ret.toJsonString());
		out.flush();
		out.close();
	}
  }
  @RequestMapping("/system/roles/deleteRoles")
  public void deleteRoles(String ids,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		rolesService.deleteEntityState(ids, new Roles());
		ret.setCode("success");
		ret.setMessage("删除成功!");
	  } catch (Exception e) {
		e.printStackTrace();
	    ret.setCode("failure");
	    ret.setMessage(e.getMessage());
	 }finally {
		out.println(ret.toJsonString());
		out.flush();
		out.close();
	}
  }
  @RequestMapping("system/resource/updateSetResource")
  public void updaeSetResource(String resourceids,Long id,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		Roles roles = rolesService.modifyResource(resourceids,id);
		if(roles!=null){
			rolesService.update(roles);
		}
		ret.setCode("success");
		ret.setMessage("授权成功!");
	  } catch (Exception e) {
		e.printStackTrace();
		ret.setCode("failure");
		ret.setMessage(e.getMessage());
	 }finally {
	  out.println(ret.toJsonString());
	  out.flush();
	  out.close();
	}
  }
}
