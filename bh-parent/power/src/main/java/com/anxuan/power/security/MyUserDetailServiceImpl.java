package com.anxuan.power.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.anxuan.power.bean.Roles;
import com.anxuan.power.bean.User;
import com.anxuan.power.dao.RolesDao;
import com.anxuan.power.dao.UserDao;
import com.anxuan.power.service.RolesService;
import com.anxuan.power.service.UserService;

/**
 * @Description : 描述
 * @author Liuhengfu
 * @email 479181871@qq.com
 * @date Aug 6, 2013 8:58:30 PM
 */
public class MyUserDetailServiceImpl implements UserDetailsService {
	private UserService userService;
	private RolesService rolesService;
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public RolesService getRolesService() {
		return rolesService;
	}

	public void setRolesService(RolesService rolesService) {
		this.rolesService = rolesService;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User users = this.userService.getUserByName(username);
		Set<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
		users.setAuthorities(grantedAuths);
		return users;
	}

	private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {
		Set<GrantedAuthority> grantedAuthorities = new HashSet<GrantedAuthority>();
		List<Roles> roles = rolesService.findRolesByUsersId(user.getId());
		for (Roles role : roles) {
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleKey()));
		}
		return grantedAuthorities;
	}
}