package com.anxuan.xjzx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.SafetymanageCard;
import com.anxuan.xjzx.dao.SafetymanageCardDao;
import com.anxuan.xjzx.service.SafetymanageCardService;

@Service(value = "safetymanageCardService")
@Transactional
public class SafetymanageCardServiceImpl extends BaseServiceImpl<SafetymanageCard> implements SafetymanageCardService {
	@Resource
	private SafetymanageCardDao safetymanageCardDao;

	@Override
	public void addIndex() {
		safetymanageCardDao.addIndex();
	}

	public List<SafetymanageCard> getSafetymanageCardByIdcard(String idcard, int line) {
		List<SafetymanageCard> safetymanageCards = safetymanageCardDao.getSafetymanageCardByIdcard(idcard, line);
		for (int i = 0; i < safetymanageCards.size(); i++) {
			 for (int j = safetymanageCards.size()-1; j > i; j--) {
				 safetymanageCards.remove(j);
			}
		}
		return safetymanageCards;
	}

	@Override
	public void quchong(int line) {
		Map<String, Object> params = new HashMap<String, Object>();
		String hql ="from SafetymanageCard bean where bean.line=:line group by bean.id having count(bean.id)>1 ";
		params.put("line", line);
		List<SafetymanageCard> safetymanageCards = safetymanageCardDao.find(hql, params, -1, -1);
		for(SafetymanageCard safetymanageCard:safetymanageCards){
			safetymanageCardDao.deleteSafetymanageCard(safetymanageCard.getKey());
		}
		
	}

	@Override
	public Integer deleteBatch(List<SafetymanageCard> list) {
	
		return this.safetymanageCardDao.deleteBatch(list);
	}

	@Override
	public Integer saveBatch(List<SafetymanageCard> list) {
		
		return this.safetymanageCardDao.saveBatch(list);
	}
}
