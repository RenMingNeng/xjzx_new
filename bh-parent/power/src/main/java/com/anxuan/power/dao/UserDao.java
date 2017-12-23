package com.anxuan.power.dao;

import java.util.List;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.power.bean.User;

public interface UserDao extends BaseDao<User>{

	void saveUser(User user);

	public List<User> listAllUsers();
    //修改最后的时间
	void updateUserLastTime(Long userid);

}
