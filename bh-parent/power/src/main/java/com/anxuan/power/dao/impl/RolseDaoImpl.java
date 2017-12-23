package com.anxuan.power.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.power.bean.Roles;
import com.anxuan.power.dao.RolesDao;

@Repository(value = "rolesDao")
public class RolseDaoImpl extends BaseDaoImpl<Roles> implements RolesDao {

	public List<Roles> findRolesByResourcesId(long id) {
		return qryCurrentSesion()
				.createQuery("select r from Roles r join r.resources rs where r.deleteStatus=:deleteStatus and rs.id=:rid")
				.setBoolean("deleteStatus", false)
				.setLong("rid", id).list();
	}

	

	public List<Roles> getNbindRoles(List<Long> rolesids) {
		String hql = "from Roles bean where bean.id not in(:rolesids) and bean.deleteStatus =:deleteStatus";
		return qryCurrentSesion().createQuery(hql)
				.setParameterList("rolesids", rolesids)
				.setBoolean("deleteStatus", false).list();
		
		
	}

}
