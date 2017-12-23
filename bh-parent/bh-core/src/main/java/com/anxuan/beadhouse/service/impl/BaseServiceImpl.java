package com.anxuan.beadhouse.service.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.search.FullTextSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.beadhouse.util.EasyuiPage;

@Transactional
public class BaseServiceImpl<T extends Serializable> implements BaseService<T> {
	@Autowired
	private BaseDao<T> baseDao;

	public void setBaseDao(BaseDao<T> baseDao) {
		this.baseDao = baseDao;
	}

	public BaseDao<T> getBaseDao() {
		return baseDao;
	}

	public void delete(T t) {
		baseDao.delete(t);
	}

	public void deleteById(String id) {
		baseDao.deleteById(id);
	}

	public T load(Long id) {
		return baseDao.load(id);
	}

	public void save(T t) {
		baseDao.save(t);
	}

	public void update(T t) {
		baseDao.update(t);
	}

	public List<T> find(String hql, Map parms, int begin, int max) {
		return baseDao.find(hql, parms, begin, max);
	}

	public EasyuiPage showListPage(String hql, EasyuiPage easyuiPage, Map parms) {
		return baseDao.showListPage(hql, easyuiPage, parms);
	}

	public void deleteEntityState(String id, T t) {
		baseDao.deleteEntityState(id, t);
	}

	public void updatemarger(T t) {
		baseDao.updatemarger(t);
	}

	public void deleteIndex(String ids,T t) {
		baseDao.deleteIndex(ids,t);
	}

	public FullTextSession getFullTextSession() {
		
		return baseDao.getFullTextSession();
	}

	@Override
	public EasyuiPage showListPageNoOrder(String hql, EasyuiPage easyuiPage, Map parms) {
		return baseDao.showListPageNoOrder(hql, easyuiPage, parms);
	}

}
