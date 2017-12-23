package com.anxuan.beadhouse.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.annotation.Autowired;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.GenericsUtils;

public class BaseDaoImpl<T extends Serializable> implements BaseDao<T> {
	@SuppressWarnings("unchecked")
	protected Class<T> domainClass = GenericsUtils.getSuperClassGenricType(this.getClass());
	@Autowired
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session getCurrentSession() {
		return sessionFactory.getCurrentSession();// 增删改使用的session
	}

	public Session qryCurrentSesion() {
		Session session = null;
		try {
			session = getCurrentSession();
			System.out.println("close");
		} catch (Exception e) {
		}
		if(session!=null){
			return session;
		}
		System.out.println("open");
		return sessionFactory.openSession();// 查询使用的session
	}

	public T load(Long id) {
		return (T) qryCurrentSesion().load(this.domainClass, id);
	}

	public void update(T t) {
		getCurrentSession().update(t);
	}

	public void save(T t) {
		getCurrentSession().save(t);
	}

	public void delete(T t) {
		getCurrentSession().delete(t);
	}

	public void deleteById(String id) {
		Object obj = load(CommUtil.null2Long(id));
		getCurrentSession().delete(obj);
	}

	public Query createQuery(String hql, Object... values) {
		Query query = qryCurrentSesion().createQuery(hql);
		for (int i = 0; i < values.length; i++) {
			query.setParameter(i, values[i]);
		}
		return query;
	}

	public EasyuiPage showListPage(String hql, EasyuiPage easyuiPage, Map params) {
		if (easyuiPage.getOrder().equalsIgnoreCase("asc")) {
			if (hql.indexOf("order by") == -1 && hql.indexOf("ORDER BY") == -1) {
				hql += " order by "+easyuiPage.getSort() + " asc";
			} else{
				hql += " , "+easyuiPage.getSort() + " asc";
			}
			
		} else if (easyuiPage.getOrder().equalsIgnoreCase("desc")) {
			if (hql.indexOf("order by") == -1 && hql.indexOf("ORDER BY") == -1) {
				hql += " order by "+easyuiPage.getSort() + " desc";
			} else{
				hql += " , "+easyuiPage.getSort() + " desc";
			}
		}
		Query query = qryCurrentSesion().createQuery(hql);
		if ((params != null) && (params.size() > 0)) {
			for (Iterator localIterator = params.keySet().iterator(); localIterator.hasNext();) {
				Object key = localIterator.next();
				if (params.get(key) instanceof Collection<?>) {
					query.setParameterList(key.toString(), (Collection<?>) params.get(key));
				} else if (params.get(key) instanceof Object[]) {
					query.setParameterList(key.toString(), (Object[])params.get(key));
				} else {
					query.setParameter(key.toString(), params.get(key));
				}
			}
			query.setCacheable(true);
		}
		easyuiPage.setTotal(query.list().size());
		query.setMaxResults(easyuiPage.getRows());
		query.setFirstResult(easyuiPage.getFirsRow());
		easyuiPage.setData(query.list());
		return easyuiPage;
	}

	public List<T> find(String hql, Map params, int begin, int max) {
		Query query = qryCurrentSesion().createQuery(hql);
		if ((params != null) && (params.size() > 0)) {
			for (Iterator localIterator = params.keySet().iterator(); localIterator.hasNext();) {
				Object key = localIterator.next();
				if (params.get(key) instanceof Collection<?>) {
					query.setParameterList(key.toString(), (Collection<?>) params.get(key));
				} else if (params.get(key) instanceof Object[]) {
					query.setParameterList(key.toString(), (Object[])params.get(key));
				} else {
					query.setParameter(key.toString(), params.get(key));
				}
			}
		}
		if ((begin >= 0) && (max > 0)) {
			query.setFirstResult(begin);
			query.setMaxResults(max);
		}
		return query.list();
	}

	public void deleteEntityState(String id, T t) {
		String ids[] = CommUtil.splitByChar(id, ",");
		List<Long> lids = new ArrayList<Long>();
		for (String sid : ids) {
			lids.add(CommUtil.null2Long(sid));
		}
		Query query = getCurrentSession().createQuery("update " + t.getClass().getName() + " set deleteStatus = 1 where id in(:ids)");
		query.setParameterList("ids", lids);
		query.executeUpdate();
	}

	public void updatemarger(T t) {
		getCurrentSession().merge(t);
	}

	@Override
	public T get(Long id) {
		return (T) qryCurrentSesion().get(this.domainClass, id);
	}

	@Override
	public void deleteIndex(String id, T t) {
		FullTextSession fullTextSession = Search.getFullTextSession(qryCurrentSesion());
		String ids[] = CommUtil.splitByChar(id, ",");
		List<Long> lids = new ArrayList<Long>();
		for (String sid : ids) {
			T entity = (T) qryCurrentSesion().get(t.getClass().getName(), CommUtil.null2Long(sid));
			fullTextSession.delete(t);
		}
	}

	@Override
	public FullTextSession getFullTextSession() {
		FullTextSession fullTextSession = Search.getFullTextSession(qryCurrentSesion());
		return fullTextSession;
	}

	@Override
	public EasyuiPage showListPageNoOrder(String hql, EasyuiPage easyuiPage, Map params) {
		Query query = qryCurrentSesion().createQuery(hql);
		if ((params != null) && (params.size() > 0)) {
			for (Iterator localIterator = params.keySet().iterator(); localIterator.hasNext();) {
				Object key = localIterator.next();
				if (params.get(key) instanceof Collection<?>) {
					query.setParameterList(key.toString(), (Collection<?>) params.get(key));
				} else if (params.get(key) instanceof Object[]) {
					query.setParameterList(key.toString(), (Object[])params.get(key));
				} else {
					query.setParameter(key.toString(), params.get(key));
				}
			}
			query.setCacheable(true);
		}
		easyuiPage.setTotal(query.list().size());
		query.setMaxResults(easyuiPage.getRows());
		query.setFirstResult(easyuiPage.getFirsRow());
		easyuiPage.setData(query.list());
		return easyuiPage;
	}

}
