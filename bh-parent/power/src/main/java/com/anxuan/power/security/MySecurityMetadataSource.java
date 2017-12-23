package com.anxuan.power.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.AntPathRequestMatcher;
import org.springframework.security.web.util.RegexRequestMatcher;
import org.springframework.security.web.util.RequestMatcher;

import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.Roles;
import com.anxuan.power.dao.ResourceDao;
import com.anxuan.power.dao.RolesDao;

/**
 * @Description : 描述
 * @author Liuhengfu
 * @email 479181871@qq.com
 * @date Aug 6, 2015 8:56:44 PM
 */
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

	public MySecurityMetadataSource(ResourceDao resourceDao, RolesDao rolesDao) {
		this.resourceDao = resourceDao;
		this.rolesDao = rolesDao;
		this.loadResourceDefine();
	}

	private ResourceDao resourceDao;
	private RolesDao rolesDao;
	private RequestMatcher requestMatcher;
	private String matcher = "ant";

	public ResourceDao getResourceDao() {
		return resourceDao;
	}

	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	public RolesDao getRolesDao() {
		return rolesDao;
	}

	public void setRolesDao(RolesDao rolesDao) {
		this.rolesDao = rolesDao;
	}

	public void setMatcher(String matcher) {
		this.matcher = matcher;
	}

	// 返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		HttpServletRequest request = ((FilterInvocation) object).getRequest();
		if (resourceMap == null) {
			loadResourceDefine();
		}
		Set<String> urlMatch = resourceMap.keySet();
		for (String url : urlMatch) {
			if (matcher.toLowerCase().equals("ant")) {
				requestMatcher = new AntPathRequestMatcher(url);
			}
			if (matcher.toLowerCase().equals("regex")) {
				requestMatcher = new RegexRequestMatcher(url, request.getMethod(), true);
			}
			if (requestMatcher.matches(request)) {
				return resourceMap.get(url);
			}
		}
		return null;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	private void loadResourceDefine() {
		if (resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Resource> resources = this.resourceDao.findAllResources();
			for (Resource resource : resources) {
				List<Roles> roles = this.rolesDao.findRolesByResourcesId(resource.getId());
				Collection<ConfigAttribute> configAttributes = new HashSet<ConfigAttribute>();
				for (Roles role : roles) {
					configAttributes.add(new SecurityConfig(role.getRoleKey()));
				}
				resourceMap.put(resource.getValue(), configAttributes);
			}
			
		}
	}
}
