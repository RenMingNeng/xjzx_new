package com.anxuan.beadhouse.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.search.FullTextSession;

import com.anxuan.beadhouse.util.EasyuiPage;

public interface BaseDao<T extends Serializable> {
	public T load(Long id);

	public T get(Long id);

	public void update(T t);

	public void save(T t);

	public void delete(T t);

	public void deleteById(String id);

	public EasyuiPage showListPage(String hql, EasyuiPage easyuiPage, Map parms);

	public List<T> find(String hql, Map parms, int begin, int max);

	public void deleteEntityState(String id, T t);

	public void updatemarger(T t);

	public void deleteIndex(String ids, T t);

	public FullTextSession getFullTextSession();

	public EasyuiPage showListPageNoOrder(String hql, EasyuiPage easyuiPage, Map parms);
}
