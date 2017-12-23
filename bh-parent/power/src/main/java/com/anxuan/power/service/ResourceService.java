package com.anxuan.power.service;

import java.util.List;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.Roles;

public interface ResourceService extends BaseService<Resource> {

	List<Resource> findUserResourcess(String userId);

	List<TreeNode> getSyncRosourceTree(List<Resource> resourceList);





}
