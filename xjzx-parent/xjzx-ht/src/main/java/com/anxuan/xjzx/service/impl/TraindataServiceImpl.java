package com.anxuan.xjzx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Traindata;
import com.anxuan.xjzx.dao.TraindataDao;
import com.anxuan.xjzx.service.TraindataService;
@Service(value="traindataService")
@Transactional
public class TraindataServiceImpl extends BaseServiceImpl<Traindata> implements TraindataService{
   @Resource
   private TraindataDao traindataDao;
	@Override
	public Traindata getTrainDataById(int id) {
		return traindataDao.getTrainDataById(id);
	}

}
