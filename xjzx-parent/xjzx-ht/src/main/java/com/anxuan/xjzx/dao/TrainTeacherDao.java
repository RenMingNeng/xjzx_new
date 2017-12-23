package com.anxuan.xjzx.dao;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.TrainTeacher;

public interface TrainTeacherDao extends BaseDao<TrainTeacher>{

	void deleteByState(String ids);

	int saveBatch(List<TrainTeacher> teacherList);

	List<String> selectCertinumByParams(Map params);

}
