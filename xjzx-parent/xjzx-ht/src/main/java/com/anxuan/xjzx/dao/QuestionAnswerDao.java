package com.anxuan.xjzx.dao;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.QuestionAnswer;

public interface QuestionAnswerDao extends BaseDao<QuestionAnswer>{

	void deleteByIds(String ids);

	void answerReview(Map params);

}
