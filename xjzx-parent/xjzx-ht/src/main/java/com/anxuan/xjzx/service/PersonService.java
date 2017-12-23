package com.anxuan.xjzx.service;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Person;

public interface PersonService extends BaseService<Person> {

	void deleteByIds(String ids);


}
