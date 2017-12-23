package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Traindata;

public interface TraindataDao extends BaseDao<Traindata> {

	Traindata getTrainDataById(int id);


}
