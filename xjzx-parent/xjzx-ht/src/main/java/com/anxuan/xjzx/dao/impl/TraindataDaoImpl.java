package com.anxuan.xjzx.dao.impl;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.xjzx.bean.Traindata;
import com.anxuan.xjzx.dao.TraindataDao;
@Repository(value="traindataDao")
public class TraindataDaoImpl extends BaseDaoImpl<Traindata> implements TraindataDao{

	@Override
	public Traindata getTrainDataById(int id) {
		return (Traindata)qryCurrentSesion().get(Traindata.class, id);
	}

}
