package com.anxuan.xjzx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Normal;
import com.anxuan.xjzx.dao.NormalDao;
import com.anxuan.xjzx.service.NormalService;

@Service(value = "normalService")
@Transactional
public class NormalServiceImpl extends BaseServiceImpl<Normal> implements NormalService {
	@Resource
	private NormalDao normalDao;

	@Override
	public void deleteByIds(String ids) {
		normalDao.deleteByIds(ids);
	}

	@Override
	public Normal getNormalById(int id) {
		return normalDao.getNormalById(id);
	}

	
}
