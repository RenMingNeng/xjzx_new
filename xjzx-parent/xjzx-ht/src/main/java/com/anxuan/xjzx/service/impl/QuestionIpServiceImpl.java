package com.anxuan.xjzx.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.QuestionIp;
import com.anxuan.xjzx.service.QuestionIpService;

@Service(value = "questionIpService")
@Transactional
public class QuestionIpServiceImpl extends BaseServiceImpl<QuestionIp> implements QuestionIpService {
	

}
