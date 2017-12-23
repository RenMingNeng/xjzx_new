package com.anxuan.xjzx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.FocusNews;
import com.anxuan.xjzx.dao.FocusNewsDao;
import com.anxuan.xjzx.service.FocusNewsService;

@Service(value = "focusNewsService")
@Transactional
public class FocusNewsServiceImpl extends BaseServiceImpl<FocusNews> implements FocusNewsService {
	@Resource
	private FocusNewsDao focusNewsDao;

	@Override
	public FocusNews getFocusNewsById(int id) {
		return focusNewsDao.getFocusNewsById(id);
	}

}
