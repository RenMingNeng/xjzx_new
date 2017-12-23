package com.anxuan.power.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.power.bean.Resource;
import com.anxuan.power.dao.ResourceDao;

@Repository(value="resourceDao")
public class ResourceDaoImpl extends BaseDaoImpl<Resource> implements ResourceDao{
	public List<Resource> findAllResources() {
		return qryCurrentSesion()
				.createQuery("from Resource bean where bean.deleteStatus=:deleteStatus")
				.setBoolean("deleteStatus",false)
				.list();
	}
	public List<Resource> findResourceByRolesIds(List<Long> ids) {
		return qryCurrentSesion().createQuery("select distinct  rs from Roles r join r.resources rs where r.id in(:ids) and rs.deleteStatus=:deleteStatus and rs.parentResource is null order by rs.orderid asc")
				.setParameterList("ids", ids)
				.setBoolean("deleteStatus", false)
				.list();
	}

}
