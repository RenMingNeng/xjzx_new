package com.anxuan.power.service;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.Roles;

public interface RolesService extends BaseService<Roles> {

	List<Roles> getNbindRoles(List<Long> rolesids);

	List<Roles> findRolesByUsersId(long userid);

	void updateRoles(Roles roles);

	Roles modifyResource(String resourceids, Long id);

	public Roles getRolseId(long id);

	List<Resource> findResourceByRolesIds(List<Long> ids);

	List<Resource> getRolseByuserid(String hql, Map parms, int i, int j);
}
