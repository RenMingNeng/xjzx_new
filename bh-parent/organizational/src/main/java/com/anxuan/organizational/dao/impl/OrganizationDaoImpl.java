package com.anxuan.organizational.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.organizational.bean.Organization;
import com.anxuan.organizational.dao.OrganizationDao;

@Repository(value="organizationDao")
public class OrganizationDaoImpl extends BaseDaoImpl<Organization> implements OrganizationDao {
	@Override
	public List<Organization> getChildDictByCode(Long pid,boolean isself) {
		String hql = "";
		if (pid == null||pid==0l) {
			hql = "from Organization o where o.deleteStatus=0 and o.parentOrgan is null";
			return qryCurrentSesion().createQuery(hql).setCacheable(true).list();
		} else {
			hql = "from Organization o where o.deleteStatus=0 and o.parentOrgan.id=:pid ";
			if(isself){
				//讲自己加入到集合中
				hql = "from Organization o where o.deleteStatus=0 and o.id=:pid";
				return qryCurrentSesion().createQuery(hql).setLong("pid", pid).setCacheable(true).list();
			}else{
				return qryCurrentSesion().createQuery(hql).setLong("pid", pid).setCacheable(true).list();
			}
		}
	}
	public List<Organization> getChildTreeNotBM(Long pid,boolean isself){
		String hql = "";
		if (pid == null) {
			hql = "from Organization o where o.deleteStatus=0 and o.parentOrgan is null and o.level.dictCode!='JGJB-BM'";
			return qryCurrentSesion().createQuery(hql).setCacheable(true).list();
		} else {
			hql = "from Organization o where o.deleteStatus=0 and o.parentOrgan.id=:pid and o.level.dictCode!='JGJB-BM'";
			if(isself){
				//讲自己加入到集合中
				hql = "from Organization o where o.deleteStatus=0 and o.id=:pid and o.level.dictCode!='JGJB-BM'";
				return qryCurrentSesion().createQuery(hql).setLong("pid", pid).setCacheable(true).list();
			}else{
				return qryCurrentSesion().createQuery(hql).setLong("pid", pid).setCacheable(true).list();
			}
		}
	}
}
