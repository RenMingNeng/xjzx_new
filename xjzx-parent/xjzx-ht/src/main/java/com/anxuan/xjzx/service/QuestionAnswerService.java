package com.anxuan.xjzx.service;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.QuestionAnswer;

public interface QuestionAnswerService extends BaseService<QuestionAnswer> {

	void deleteByIds(String ids);

	void answerReview(Map params);
}
