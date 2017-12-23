package com.anxuan.xjzx.service;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Normal;

public interface NormalService extends BaseService<Normal>{

	void deleteByIds(String ids);

	Normal getNormalById(int pid);



}
