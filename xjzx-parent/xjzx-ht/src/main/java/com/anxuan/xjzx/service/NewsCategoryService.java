package com.anxuan.xjzx.service;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.xjzx.bean.NewsCategory;

public interface NewsCategoryService extends BaseService<NewsCategory>{

	void deleteState(String ids);

	List<TreeNode> getUnsyncTreeByPid(int pid);

	NewsCategory getNewsCategoryById(int categoryid);

	EasyuiPage showListPageNoOrder(String hql, EasyuiPage easyuiPage, Map params);

}
