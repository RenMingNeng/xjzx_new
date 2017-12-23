package com.anxuan.xjzx.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.QuestionOption;
import com.anxuan.xjzx.bean.QuestionResult;
import com.anxuan.xjzx.dao.QuestionResultDao;
import com.anxuan.xjzx.service.QuestionResultService;

import net.sf.json.JSONArray;

@Service(value = "questionResultService")
@Transactional
public class QuestionResultServiceImpl extends BaseServiceImpl<QuestionResult> implements QuestionResultService {
	
	@Resource
	private QuestionResultDao questionResultDao;
	
	@Override
	public void deleteByQIds(String ids) {
		questionResultDao.deleteByQIds(ids);
	}

	@Override
	public void deleteByOptionId(String id) {
		questionResultDao.deleteByOptionId(id);
	}

	@Override
	public void updateCount(Map params) {
		questionResultDao.updateCount(params);
	}

	@Override
	public void batchInsert(Map map) {
		Integer question_id = Integer.valueOf(map.get("question_id").toString());
		String answer_option = map.get("options").toString();
		List<QuestionOption> optionList = JSONArray.toList(JSONArray.fromObject(answer_option), QuestionOption.class);
		QuestionResult questionResult;
		for (QuestionOption questionOption : optionList) {
			questionResult = new QuestionResult();
			questionResult.setQuestion_id(question_id);
			questionResult.setOption_id(questionOption.getKey());
			questionResult.setCount(0);
			questionResult.setCreate_time(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			questionResultDao.save(questionResult);
		}
	}

	@Override
	public Integer sumCountByQuestionId(Integer question_id) {
		
		return questionResultDao.sumCountByQuestionId(question_id);
	}

	@Override
	public void updateByParams(Map params) {
		questionResultDao.updateByParams(params);
	}
	
}
