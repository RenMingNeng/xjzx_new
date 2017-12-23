package com.anxuan.xjzx.service;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Advise;

public interface AdviseService extends BaseService<Advise>{

	Advise getAdviseById(int id);

	void deleteids(String ids);

}
