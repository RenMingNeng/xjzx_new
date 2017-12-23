package com.anxuan.xjzx.service;

import java.util.List;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.xjzx.bean.TranOrgan;

public interface TranOrganService extends BaseService<TranOrgan>{

	void deleteids(String ids);

	List<TreeNode> getAearTree(String dictCode);

	TranOrgan getTranOrganById(int id);

}
