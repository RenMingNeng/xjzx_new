package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.QuestionResult;
import com.anxuan.xjzx.dao.QuestionResultDao;
@Repository(value="questionResultDao")
public class QuestionResultDaoImpl extends BaseDaoImpl<QuestionResult> implements QuestionResultDao{

	@Override
	public void deleteByQIds(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		Query query = getCurrentSession().createQuery("delete from QuestionResult bean where bean.question_id in (:ids)");
				query.setParameterList("ids", idlists);
				query.executeUpdate();
	}

	@Override
	public void deleteByOptionId(String id) {
		Query query = getCurrentSession().createQuery("delete from QuestionResult bean where bean.option_id =:option_id");
		query.setParameter("option_id", id);
		query.executeUpdate();
	}

	@Override
	public void updateCount(Map params) {
		String question_id = params.get("question_id").toString();
		String ids = params.get("option_ids").toString();
		List<String> idlists = Arrays.asList(ids.split(","));
		Query query = getCurrentSession().createQuery("update QuestionResult bean set bean.count=bean.count+1 where bean.question_id=:question_id and bean.option_id in (:ids)");
		query.setParameterList("ids", idlists);
		query.setInteger("question_id", Integer.valueOf(question_id));
		query.executeUpdate();
	}

	@Override
	public Integer sumCountByQuestionId(Integer question_id) {
		Query query = qryCurrentSesion().createQuery("select SUM(bean.count) from QuestionResult bean where bean.question_id=:question_id");
		query.setInteger("question_id", question_id);
		Long count = (Long) query.uniqueResult()==null?0:(Long) query.uniqueResult();
		return Integer.valueOf(count.toString());
	}

	@Override
	public void updateByParams(Map params) {
		String question_id = params.get("question_id").toString();
		String option_id = params.get("option_id").toString();
		String count = params.get("count").toString();
		Query query = getCurrentSession().createQuery("update QuestionResult bean set bean.count=:count where bean.question_id=:question_id and bean.option_id=:option_id");
		query.setParameter("question_id",Integer.valueOf(question_id));
		query.setParameter("option_id",option_id);
		query.setInteger("count", Integer.valueOf(count));
		query.executeUpdate();
	}

}
