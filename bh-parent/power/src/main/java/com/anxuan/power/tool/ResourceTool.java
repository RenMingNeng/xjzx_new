package com.anxuan.power.tool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.User;
import com.anxuan.power.service.ResourceService;
import com.anxuan.power.service.RolesService;

@Component
public class ResourceTool {
  @Autowired
  private RolesService rolesService;
  public List<Resource> findResourceByParentId(long pid){
	  User user = (User) SecurityContextHolder.getContext()  
			  .getAuthentication().getPrincipal();  
	  String hql = "select distinct rs from User u join u.reRoles r join r.resources rs where u.id =:userid and rs.parentResource.id =:pid and rs.deleteStatus =:deleteStatus";
	  Map parms = new HashMap();
	  parms.put("deleteStatus", false);
	  parms.put("pid", pid);
	  parms.put("userid", user.getId());
	  return rolesService.getRolseByuserid(hql, parms, -1, -1);
  }
  public boolean ischilResource(Long pid){
	  User user = (User) SecurityContextHolder.getContext()  
			  .getAuthentication().getPrincipal();  
	  boolean flag = false ;
	  String hql = "select distinct rs from User u join u.reRoles r join r.resources rs where u.id =:userid and rs.parentResource.id =:pid and rs.deleteStatus =:deleteStatus and rs.classType=:classType";
	  Map parms = new HashMap();
	  parms.put("deleteStatus", false);
	  parms.put("pid", pid);
	  parms.put("userid", user.getId());
	  parms.put("classType", "class");
	  List<Resource> resources = rolesService.getRolseByuserid(hql, parms, -1, -1);
	  if(resources!=null&&resources.size()>0) flag = true;
	  return flag;
  }
}
