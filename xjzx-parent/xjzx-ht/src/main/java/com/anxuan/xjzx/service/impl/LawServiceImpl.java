package com.anxuan.xjzx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Law;
import com.anxuan.xjzx.dao.LawDao;
import com.anxuan.xjzx.service.LawService;

@Service(value = "lawService")
@Transactional
public class LawServiceImpl extends BaseServiceImpl<Law> implements LawService {
	@Resource
	private LawDao lawDao;

	@Override
	public void deleteids(String ids) {
		lawDao.deleteids(ids);

	}

	@Override
	public Law getLawById(int id) {
		// TODO Auto-generated method stub
		return lawDao.getLawById(id);
	}

	@Override
	public void updateLawByIspass(String ids, int ispass) {
		lawDao.updateLawByIspass(ids,ispass);
		
	}

}
