package com.anxuan.xjzx.dao;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.xjzx.bean.Law;

public interface LawDao extends BaseDao<Law> {

	void deleteids(String ids);

	Law getLawById(int id);

	void updateLawByIspass(String ids, int ispass);



}
