package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Question;

public interface QuestionDao extends BaseDao<Question>{

	Question selectById(Integer id);

	void deleteByIds(String ids);

	void editFeedBack(Question question);

	void updateOptionCount(Question question);

}
