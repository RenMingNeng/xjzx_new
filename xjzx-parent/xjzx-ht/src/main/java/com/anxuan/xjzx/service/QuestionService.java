package com.anxuan.xjzx.service;

import java.util.Map;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Question;

public interface QuestionService extends BaseService<Question> {

	Question selectById(Integer questionId);

	void deleteByIds(String ids);

	void editFeedBack(Question question);

}
