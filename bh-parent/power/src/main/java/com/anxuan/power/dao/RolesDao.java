package com.anxuan.power.dao;

import java.util.List;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.power.bean.Roles;

public interface RolesDao extends BaseDao<Roles> {

	List<Roles> findRolesByResourcesId(long id);


	List<Roles> getNbindRoles(List<Long> rolesids);

}
