package com.anxuan.xjzx.dao.impl;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.xjzx.bean.FocusNews;
import com.anxuan.xjzx.dao.FocusNewsDao;

@Repository(value = "focusNewsDao")
public class FocusNewsDaoImpl extends BaseDaoImpl<FocusNews> implements FocusNewsDao {

	@Override
	public FocusNews getFocusNewsById(int id) {
		return (FocusNews) qryCurrentSesion().get(FocusNews.class, id);
	}

}
