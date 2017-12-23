package com.anxuan.xjzx.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.QuestionAnswer;
import com.anxuan.xjzx.dao.QuestionAnswerDao;
import com.anxuan.xjzx.dao.QuestionResultDao;
import com.anxuan.xjzx.service.QuestionAnswerService;

@Service(value = "questionAnswerService")
@Transactional
public class QuestionAnswerServiceImpl extends BaseServiceImpl<QuestionAnswer> implements QuestionAnswerService {
	
	@Resource
	private QuestionAnswerDao questionAnswerDao;

	@Override
	public void deleteByIds(String ids) {
		questionAnswerDao.deleteByIds(ids);
	}

	@Override
	public void answerReview(Map params) {
		questionAnswerDao.answerReview(params);
	}

}
