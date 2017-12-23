package com.anxuan.organizational.serivce.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.organizational.bean.Organization;
import com.anxuan.organizational.dao.OrganizationDao;
import com.anxuan.organizational.serivce.OrganizationService;

@Service(value = "organizationService")
@Transactional
public class OrganizationServiceImpl extends BaseServiceImpl<Organization> implements OrganizationService {
	@Resource
	private OrganizationDao organizationDao;

	@Override
	public List<TreeNode> getOrganizationTree(Long pid, boolean isseif) {
		List<Organization> organizations = organizationDao.getChildDictByCode(pid, isseif);
		if (pid == null) {
			TreeNode root = new TreeNode();
			root.setText("组织架构");
			root.setId(0);
			root.setChildren(dict2TreeNode(organizations));
			root.setState("open");
			Organization organization = new Organization();
			organization.setId(0);
			organization.setOrganName("根节点");
			Dict level = new Dict();
			level.setDictCode("JGJB-G");
			organization.setLevel(level);
			root.setAttributes(organization);
			List<TreeNode> roots = new ArrayList<TreeNode>();
			roots.add(root);
			return roots;
		}
		return dict2TreeNode(organizations);
	}

	private List<TreeNode> dict2TreeNode(List<Organization> organizations) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		Iterator<Organization> it = organizations.iterator();
		while (it.hasNext()) {
			list.add(dict2TreeNode(it.next()));

		}
		return list;
	}

	private TreeNode dict2TreeNode(Organization organization) {
		TreeNode node = new TreeNode();
		node.setId(organization.getId());
		node.setText(organization.getOrganName());
		node.setAttributes(organization);
		return node;
	}

	@Override
	public Organization getorganizationById(Long id) {
		return (Organization) organizationDao.get(id);
	}

	@Override
	public boolean verifyDeptypeCode(String deptypecode) {
		boolean flag = true;
		Map parms = new HashMap();
		parms.put("deleteStatus", false);
		parms.put("deptypecode", deptypecode);
		String hql = "from Organization bean where bean.deleteStatus = :deleteStatus and  bean.deptypecode = :deptypecode";
		List<Organization> organizations = organizationDao.find(hql, parms, -1, -1);
		if (organizations != null && organizations.size() > 0) {
			flag = false;
		}
		return flag;
	}

	@Override
	public Organization getOrganizationByCompaycodeAndStru_level(String compaycode) {
		String hql = "from Organization bean where bean.deleteStatus=:deleteStatus and bean.companycode =:companycode and (bean.level.dictCode='JGJB-Y' or bean.level.dictCode='JGJB-ZY')";
		Map parms = new HashMap();
		parms.put("deleteStatus", false);
		parms.put("companycode", compaycode);
		List<Organization> organizations = organizationDao.find(hql, parms, -1, -1);
		if (organizations != null && organizations.size() > 0) {
			return organizations.get(0);
		} else {
			return null;
		}
	}
	@Override
	public List<TreeNode> getChildTreeNotBM(Long pid, boolean isseif) {
		List<Organization> organizations = organizationDao.getChildTreeNotBM(pid, isseif);
		if (pid == null) {
			TreeNode root = new TreeNode();
			root.setText("组织架构");
			root.setId(0);
			root.setChildren(dict2TreeNode(organizations));
			root.setState("open");
			Organization organization = new Organization();
			organization.setId(0);
			organization.setOrganName("根节点");
			Dict level = new Dict();
			level.setDictCode("JGJB-G");
			organization.setLevel(level);
			root.setAttributes(organization);
			List<TreeNode> roots = new ArrayList<TreeNode>();
			roots.add(root);
			return roots;
		}
		return dict2TreeNode(organizations);
	}

	@Override
	public void saveOrganization(Organization organization) {
          organizationDao.save(organization);		
	}

	@Override
	public Organization getOrganizationByCode(String deptypecode) {
		String hql ="from Organization bean where bean.deleteStatus=:deleteStatus and bean.deptypecode=:deptypecode";
		Map parms = new HashMap();
		parms.put("deleteStatus", false);
		parms.put("deptypecode", deptypecode);
		List<Organization> organizations = organizationDao.find(hql, parms, -1, -1);
		if(organizations!=null&&organizations.size()>0){
			return organizations.get(0);
		}
		return null;
	}
 
}
