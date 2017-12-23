package com.anxuan.organizational.dao;

import java.util.List;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.organizational.bean.Organization;

public interface OrganizationDao extends BaseDao<Organization>{

	List<Organization> getChildDictByCode(Long pid,boolean isseif);

	List<Organization> getChildTreeNotBM(Long pid, boolean isseif);


}
