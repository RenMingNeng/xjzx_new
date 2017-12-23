package com.anxuan.xjzx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Advise;
import com.anxuan.xjzx.dao.AdviseDao;
import com.anxuan.xjzx.service.AdviseService;

@Service(value = "adviseService")
@Transactional
public class AdviseServiceImpl extends BaseServiceImpl<Advise> implements AdviseService {

	@Resource
	private AdviseDao adviseDao;
	
	@Override
	public Advise getAdviseById(int id) {
		return adviseDao.getAdviseById(id);
	}

	@Override
	public void deleteids(String ids) {
		adviseDao.deleteids(ids);
	}

	
}
