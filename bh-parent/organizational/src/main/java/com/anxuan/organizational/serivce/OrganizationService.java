package com.anxuan.organizational.serivce;

import java.util.List;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.organizational.bean.Organization;

public interface OrganizationService extends BaseService<Organization>{

	List<TreeNode> getOrganizationTree(Long pid,boolean isself);

	Organization getorganizationById(Long id);

	boolean verifyDeptypeCode(String deptypecode);

	Organization getOrganizationByCompaycodeAndStru_level(String compaycode);

	List<TreeNode> getChildTreeNotBM(Long pid, boolean isseif);

	void saveOrganization(Organization organization);

	Organization getOrganizationByCode(String deptypecode);


}
