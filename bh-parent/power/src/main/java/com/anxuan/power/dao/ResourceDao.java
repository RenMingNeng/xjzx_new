package com.anxuan.power.dao;

import java.util.List;

import com.anxuan.beadhouse.dao.BaseDao;
import com.anxuan.power.bean.Resource;

public interface ResourceDao extends BaseDao<Resource> {

	List<Resource> findAllResources();

	List<Resource> findResourceByRolesIds(List<Long> ids);

}
