package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.FocusNews;

public interface FocusNewsDao extends BaseDao<FocusNews>{

	FocusNews getFocusNewsById(int id);

}
