package com.anxuan.power.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.Roles;
import com.anxuan.power.dao.ResourceDao;
import com.anxuan.power.dao.RolesDao;
import com.anxuan.power.service.RolesService;

@Service(value = "rolesService")
@Transactional
public class RolesServiceImpl extends BaseServiceImpl<Roles> implements RolesService {
	@javax.annotation.Resource
	private RolesDao rolesDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;

	@Override
	public List<Roles> getNbindRoles(List<Long> rolesids) {
		List<Roles> roles = null;
		if (rolesids != null && rolesids.size() > 0) {
			roles = rolesDao.getNbindRoles(rolesids);
		} else {
			String hql = "from Roles bean where bean.deleteStatus =:deleteStatus";
			Map parms = new HashMap();
			parms.put("deleteStatus", false);
			roles = rolesDao.find(hql, parms, -1, -1);
		}
		return roles;
	}

	public List<Roles> findRolesByUsersId(long userid) {
		Map params = new HashMap();
		params.put("deleteStatus", false);
		params.put("userid", userid);
		return rolesDao.find(
				"select r from User u join u.reRoles r  where u.id=:userid and r.deleteStatus=:deleteStatus", params,
				-1, -1);
	}

	@Override
	public void updateRoles(Roles roles) {
		Roles tempRoles = (Roles) rolesDao.get(roles.getId());
		if (tempRoles != null) {
			tempRoles.setMark(roles.getMark());
			tempRoles.setName(roles.getName());
			tempRoles.setRoleKey(roles.getRoleKey());
			rolesDao.updatemarger(tempRoles);
		}
	}

	@Override
	public Roles modifyResource(String resourceids, Long id) {
		Roles roles = rolesDao.get(id);
		String resourceList[] = CommUtil.splitByChar(resourceids, ",");
		List<com.anxuan.power.bean.Resource> resources = new ArrayList<com.anxuan.power.bean.Resource>();
		for (String rolesid : resourceList) {
			if (rolesid != "" && rolesid != null) {
				com.anxuan.power.bean.Resource resource = new com.anxuan.power.bean.Resource();
				resource.setId(CommUtil.null2Long(rolesid));
				resources.add(resource);
			}
		}
		roles.setResources(resources);
		return roles;
	}

	@Override
	public Roles getRolseId(long id) {
		return rolesDao.get(id);
	}

	public List<Resource> findResourceByRolesIds(List<Long> ids) {
		return resourceDao.findResourceByRolesIds(ids);
	}

	@Override
	public List<Resource> getRolseByuserid(String hql, Map parms, int begin, int max) {
		return resourceDao.find(hql, parms, begin, max);
	}
}
