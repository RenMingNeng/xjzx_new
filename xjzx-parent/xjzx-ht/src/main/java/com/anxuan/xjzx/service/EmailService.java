package com.anxuan.xjzx.service;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Email;

public interface EmailService extends BaseService<Email> {

	void deleteByIds(String ids);
}
