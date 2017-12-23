package com.anxuan.xjzx.dao;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.QuestionOption;
import com.anxuan.xjzx.bean.QuestionResult;

public interface QuestionResultDao extends BaseDao<QuestionResult>{


	void deleteByQIds(String ids);

	void deleteByOptionId(String id);

	void updateCount(Map params);

	Integer sumCountByQuestionId(Integer question_id);

	void updateByParams(Map params);

}
