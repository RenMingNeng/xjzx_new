package com.anxuan.xjzx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Email;
import com.anxuan.xjzx.dao.EmailDao;
import com.anxuan.xjzx.service.EmailService;

@Service(value = "emailService")
@Transactional
public class EmailServiceImpl extends BaseServiceImpl<Email> implements EmailService {

	@Resource
	private EmailDao emailDao;

	@Override
	public void deleteByIds(String ids) {
		emailDao.deleteByIds(ids);
	}
	
}
