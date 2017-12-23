package com.anxuan.power.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.Roles;
import com.anxuan.power.service.ResourceService;
import com.anxuan.power.service.RolesService;
@Controller
@RequestMapping("/system/resource")
public class ResourceController {
	@javax.annotation.Resource
	private ResourceService resourceService;
	@javax.annotation.Resource
	private RolesService rolesService;
	@RequestMapping("setResource")
	public ModelAndView setResource(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/resource/set_resource", "ADMIN", request, response);
		return mav;
	}
	@RequestMapping("resourceTree")
	public void resourceTree(PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			String hql = "from Resource bean where bean.parentResource is null and  bean.deleteStatus=:deleteStatus";
			Map parms = new HashMap();
			parms.put("deleteStatus", false);
			List<Resource> resourceList = resourceService.find(hql, parms, -1, -1);
			List<TreeNode> treeNodes = resourceService.getSyncRosourceTree(resourceList);
			ret.setData(treeNodes);
			ret.setCode("success");
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
	@RequestMapping("selectRolesResource")
	public void selectRolesResource(Long rolesid,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			Roles roles = rolesService.getRolseId(rolesid);
			ret.setData(roles.getResources());
			ret.setCode("success");
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
