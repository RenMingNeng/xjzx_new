package com.anxuan.power.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.power.bean.Roles;
import com.anxuan.power.bean.User;
import com.anxuan.power.dao.UserDao;
import com.anxuan.power.service.UserService;

@Service(value = "userService")
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
	@Resource
	private UserDao userDao;

	@Override
	public void saveUser(User user) {
		userDao.saveUser(user);
	}

	@Override
	public User getUserById(Long id) {
		return (User) userDao.get(id);
	}

	@Override
	public User getUserByName(String name) {
		Map parms = new HashMap();
		parms.put("deleteStatus", false);
		parms.put("name", name);
		List<User> users = userDao.find("from User bean where bean.deleteStatus=:deleteStatus and bean.name=:name",parms, -1, -1);
		if (users != null && users.size() > 0) {
			updateUserLastTime(users.get(0).getId());
			return users.get(0);
		} else {
			return null;
		}
	}

	

	@Override
	public void updatebindRoles(Long userid, String rolesids) {
		 User user = (User) userDao.get(userid);
		 List<Roles> roles = new ArrayList<Roles>();
		 String rolesidList[] = CommUtil.splitByChar(rolesids, ",");
		 if(rolesidList!=null&&rolesidList.length>0){
			 for(String rolesid :rolesidList){
				 if(rolesid!=null&&!"".equals(rolesid)){
					 Roles roles2 = new Roles();
					 roles2.setId(CommUtil.null2Int(rolesid));
					 roles.add(roles2);
				 }
			 }
		 }
		 user.setReRoles(roles);
		 userDao.update(user);
	}
	
	public void updateUserLastTime(Long userid){
		userDao.updateUserLastTime(userid);
	}
}
