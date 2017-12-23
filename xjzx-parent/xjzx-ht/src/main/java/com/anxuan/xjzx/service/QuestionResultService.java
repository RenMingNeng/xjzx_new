package com.anxuan.xjzx.service;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.QuestionResult;

public interface QuestionResultService extends BaseService<QuestionResult> {

	void deleteByQIds(String ids);

	void deleteByOptionId(String id);

	void updateCount(Map params);

	void batchInsert(Map map);

	Integer sumCountByQuestionId(Integer question_id);

	void updateByParams(Map params);

}
