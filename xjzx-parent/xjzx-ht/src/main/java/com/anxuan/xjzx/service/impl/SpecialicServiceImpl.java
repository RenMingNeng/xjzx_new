package com.anxuan.xjzx.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Specialic;
import com.anxuan.xjzx.dao.SpecialicDao;
import com.anxuan.xjzx.service.SpecialicService;

@Service(value = "specialicService")
@Transactional
public class SpecialicServiceImpl extends BaseServiceImpl<Specialic> implements SpecialicService {
	@Resource
	private SpecialicDao specialicDao;

	@Override
	public List<Specialic> getspecialicByCardNumber(String cardNumber) {
		Map params = new HashMap();
		params.put("cardnumber", cardNumber);
		String hql = "select bean from Specialic bean where bean.cardnumber =:cardnumber group by jobcategory,prepareproject order by releastime desc";
		List<Specialic> specialicList = specialicDao.find(hql, params, -1, -1);
		return specialicList;
	}

	@Override
	public List<Specialic> findAll() {
		return specialicDao.findAll();
	}

	@Override
	public void addIndex() {
		specialicDao.addIndex();
	}

	@Override
	public List<Specialic> findSpecialicByCardnumber(String cardnumber) {
		return specialicDao.findSpecialicByCardnumber(cardnumber);
	}
	
	@Override
	public Integer deleteBatch(List<Specialic> list) {
		 return this.specialicDao.deleteBatch(list);
	}

	@Override
	public Integer saveBatch(List<Specialic> list) {
		return this.specialicDao.saveBatch(list);
	}

}
