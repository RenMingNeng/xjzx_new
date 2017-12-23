package com.anxuan.xjzx.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.xjzx.bean.Question;
import com.anxuan.xjzx.dao.QuestionDao;
@Repository(value="questionDao")
public class QuestionDaoImpl extends BaseDaoImpl<Question> implements QuestionDao{

	@Override
	public Question selectById(Integer id) {
		return (Question) qryCurrentSesion().get(Question.class, id);
	}

	@Override
	public void deleteByIds(String ids) {
		String[] idList = CommUtil.splitByChar(ids, ",");
		List<Integer> idlists = new ArrayList<Integer>();
		for(String id:idList){
			idlists.add(CommUtil.null2Int(id));
		}
		Query query = getCurrentSession().createQuery("delete from Question bean where bean.id in (:ids)");
				query.setParameterList("ids", idlists);
				query.executeUpdate();
	}

	@Override
	public void editFeedBack(Question question) {
		Map<String, Object> param = new HashMap<String, Object>();
		Query query = getCurrentSession().createQuery("update Question bean set bean.feedback=:feedback,bean.back_valid=:back_valid where bean.id=:id");
		query.setInteger("id", question.getId());
		query.setParameter("feedback", question.getFeedback());
		query.setParameter("back_valid", question.getBack_valid());
		query.executeUpdate();
	}

	@Override
	public void updateOptionCount(Question question) {
		Query query = getCurrentSession().createQuery("update Question bean set bean.answer_option=:answer_option where bean.id=:id");
		query.setInteger("id", question.getId());
		query.setParameter("answer_option", question.getAnswer_option());
		query.executeUpdate();
	}
}
