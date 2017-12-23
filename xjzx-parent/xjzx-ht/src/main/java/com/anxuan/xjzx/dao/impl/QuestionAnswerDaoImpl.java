package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.QuestionAnswer;
import com.anxuan.xjzx.dao.QuestionAnswerDao;
@Repository(value="questionAnswerDao")
public class QuestionAnswerDaoImpl extends BaseDaoImpl<QuestionAnswer> implements QuestionAnswerDao{

	@Override
	public void deleteByIds(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		Query query = getCurrentSession().createQuery("delete from QuestionAnswer bean where bean.id in (:ids)");
			query.setParameterList("ids", idlists);
			query.executeUpdate();
	}

	@Override
	public void answerReview(Map params) {
		String ids = params.get("ids").toString();
		String valid = params.get("review").toString();
		Integer is_valid = CommUtil.null2Int(valid);
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		Query query = getCurrentSession().createQuery("update QuestionAnswer bean set bean.is_valid=:is_valid where bean.id in (:ids)");
			query.setParameterList("ids", idlists);
			query.setInteger("is_valid", is_valid);
			query.executeUpdate();
	}

}
