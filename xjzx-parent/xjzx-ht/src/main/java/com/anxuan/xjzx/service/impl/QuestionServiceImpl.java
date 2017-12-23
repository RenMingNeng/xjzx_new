package com.anxuan.xjzx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Question;
import com.anxuan.xjzx.bean.QuestionOption;
import com.anxuan.xjzx.dao.QuestionDao;
import com.anxuan.xjzx.service.QuestionService;

import net.sf.json.JSONArray;

@Service(value = "questionService")
@Transactional
public class QuestionServiceImpl extends BaseServiceImpl<Question> implements QuestionService {

	@Resource
	private QuestionDao questionDao;
	
	@Override
	public Question selectById(Integer id) {
		return questionDao.selectById(id);
	}

	@Override
	public void deleteByIds(String ids) {
		questionDao.deleteByIds(ids);		
	}

	@Override
	public void editFeedBack(Question question) {
		questionDao.editFeedBack(question);
	}
}
