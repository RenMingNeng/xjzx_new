package com.anxuan.power.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.Roles;
import com.anxuan.power.dao.ResourceDao;
import com.anxuan.power.dao.RolesDao;
import com.anxuan.power.service.ResourceService;

@Service(value = "resourceService")
@Transactional
public class ResourceServiceImpl extends BaseServiceImpl<Resource> implements ResourceService {
	@javax.annotation.Resource
	private RolesDao rolesDao;
	@javax.annotation.Resource
	private ResourceDao resourceDao;

	@Override
	public List<Resource> findUserResourcess(String userId) {
		List<Long> rolesIds = new ArrayList<Long>();
		Map parms = new HashMap();
		List<Roles> rolesList = rolesDao.find(
				"select r from User u join u.reRoles r bean where bean.deleteStatus=:deleteStatus", parms, -1, -1);
		for (Roles roles : rolesList) {
			rolesIds.add(roles.getId());
		}
		parms.clear();
		parms.put("deleteStatus", false);
		parms.put("rolesIds", rolesIds);
		return resourceDao.find(
				"select distinct rs from Roles r join r.resources rs  where r.id(:rolesIds) and rs.deleteStatus=:deleteStatus",
				parms, -1, -1);
	}

	@Override
	public List<TreeNode> getSyncRosourceTree(List<Resource> resourceList) {
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		for (Resource resource : resourceList) {
			TreeNode treeNode = resourceToTreeNode(resource);
			treeNodes.add(treeNode);
		}
		return treeNodes;
	}

	private TreeNode resourceToTreeNode(Resource resource) {
		TreeNode treeNode = new TreeNode();
		treeNode.setAttributes(resource);
		treeNode.setText(resource.getName());
		treeNode.setId(resource.getId());
		treeNode.setChecked(false);
		List<Resource> resourcechildrenList = getResourceChildren(treeNode.getId());
		List<TreeNode> childrenNodeList = new ArrayList<TreeNode>();
		for (com.anxuan.power.bean.Resource resource2 : resourcechildrenList) {
			TreeNode childNode = resourceToTreeNode(resource2);
			childrenNodeList.add(childNode);
		}
		treeNode.setChildren(childrenNodeList == null || childrenNodeList.size() == 0 ? null : childrenNodeList);
		return treeNode;
	}

	public List<Resource> getResourceChildren(Long pid) {
		String hql = "from Resource bean where bean.parentResource.id = :pid and bean.deleteStatus=:deleteStatus";
		Map parms = new HashMap();
		parms.put("pid", pid);
		parms.put("deleteStatus", false);
		List<Resource> resourcechildrenList = resourceDao.find(hql, parms, -1, -1);
		return resourcechildrenList;
	}

	


}
