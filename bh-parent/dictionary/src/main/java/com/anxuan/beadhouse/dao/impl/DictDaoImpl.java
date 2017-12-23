package com.anxuan.beadhouse.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.dao.DictDao;

@Repository(value = "dictDao")
public class DictDaoImpl extends BaseDaoImpl<Dict> implements DictDao {
	@SuppressWarnings("unchecked")
	public List<Dict> getChildDictByCode(String code) {
		String hql = "";
		if (code == null || "".equalsIgnoreCase(code)) {
			hql = "from Dict d where d.deleteStatus=0 and d.parent is null";
			return qryCurrentSesion().createQuery(hql).setCacheable(true).list();
		} else {
			hql = "from Dict d where d.deleteStatus=0 and d.parent.dictCode=:dicCode and d.dictType='t'";
			return qryCurrentSesion().createQuery(hql).setString("dicCode", code).setCacheable(true).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Dict> getDictValueByParentCode(String pid) {
		String hql = "";
		if (pid == null || "".equalsIgnoreCase(pid)) {
			return null;
		} else {
			hql = "from Dict d where d.deleteStatus=0 and d.parent.id=:pid";
			return qryCurrentSesion().createQuery(hql).setString("pid", pid).list();
		}
	}

	@SuppressWarnings("unchecked")
	public List<Dict> getDictValueCode(String code) {
		String hql = "from Dict bean where bean.parent.dictCode = :dictCode and bean.deleteStatus = 0";
		return qryCurrentSesion().createQuery(hql).setString("dictCode", code).list();
	}

	@SuppressWarnings("unchecked")
	public List<Dict> getDictionaryByPid(Long pid) {
		String hql = "from Dict bean where bean.parent.id= :pid and bean.deleteStatus = 0";
		return qryCurrentSesion().createQuery(hql).setLong("pid", pid).list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Dict getDictBycode(String code) {
		String hql = "from Dict bean where bean.dictCode= :dictCode and bean.deleteStatus = 0";
		List<Dict> dicts = qryCurrentSesion().createQuery(hql).setString("dictCode", code).list();
		if (dicts != null && dicts.size() > 0) {
			System.out.println(dicts.get(0));
			return dicts.get(0);
		} else {
			return null;
		}
	}
}
