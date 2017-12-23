package com.anxuan.xjzx.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.Person;
import com.anxuan.xjzx.dao.PersonDao;
import com.anxuan.xjzx.service.PersonService;

@Service(value = "personService")
@Transactional
public class PersonServiceImpl extends BaseServiceImpl<Person> implements PersonService {

	@Resource
	private PersonDao personDao;

	@Override
	public void deleteByIds(String ids) {
		personDao.deleteByIds(ids);
	}
	
}
