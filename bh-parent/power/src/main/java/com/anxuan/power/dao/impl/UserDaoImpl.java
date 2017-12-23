package com.anxuan.power.dao.impl;



import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.anxuan.beadhouse.dao.impl.BaseDaoImpl;
import com.anxuan.power.bean.User;
import com.anxuan.power.dao.UserDao;
@Repository(value="userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{
 private static  String FIRSTACCOUT  = "1000000";
	public void saveUser(User user) {
		user.setNumber(getUserNunber());
		getCurrentSession().save(user);
	}
    public String getUserNunber(){
        Object object = qryCurrentSesion().createQuery("select max(bean.number) from User bean").uniqueResult();
        String userNumber= FIRSTACCOUT;
        if(object!=null){
        	int numberName = Integer.parseInt(object.toString())+1;
        	userNumber = String.valueOf(numberName);
        }
    	return userNumber;
    }

	public List<User> listAllUsers() {
		return qryCurrentSesion().createQuery("from User bean where bean.deleteStatus=：deleteStatus")
				.setBoolean("deleteStatus", false).list();
	}
	@Override
	public void updateUserLastTime(Long userid) {
		getCurrentSession().createQuery("update User bean set bean.lasttime =:lasttime where bean.id = :userid")
		.setDate("lasttime", new Date())
		.setLong("userid", userid)
		.executeUpdate();
		
	}
}
