package com.anxuan.power.service;

import java.util.List;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.power.bean.User;

public interface UserService extends BaseService<User>{

	void saveUser(User user);

	User getUserById(Long id);

	User getUserByName(String name);

	void updatebindRoles(Long userid, String rolesids);

}
