package com.anxuan.xjzx.service;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Traindata;

public interface TraindataService extends BaseService<Traindata>{

	Traindata getTrainDataById(int id);

}
