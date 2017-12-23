package com.anxuan.xjzx.service;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Law;

public interface LawService extends BaseService<Law> {

	void deleteids(String ids);

	Law getLawById(int id);

	void updateLawByIspass(String ids, int ispass);

}
