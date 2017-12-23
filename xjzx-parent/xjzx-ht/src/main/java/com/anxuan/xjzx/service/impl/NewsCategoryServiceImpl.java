package com.anxuan.xjzx.service.impl;

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
import com.anxuan.xjzx.bean.NewsCategory;
import com.anxuan.xjzx.dao.NewsCategoryDao;
import com.anxuan.xjzx.service.NewsCategoryService;

@Service(value = "newsCategoryService")
@Transactional
public class NewsCategoryServiceImpl extends BaseServiceImpl<NewsCategory> implements NewsCategoryService {
	@Resource
	NewsCategoryDao newsCategoryDao;

	@Override
	public void deleteState(String ids) {
		newsCategoryDao.deleteState(ids);
	}

	@Override
	public List<TreeNode> getUnsyncTreeByPid(int pid) {
		List<NewsCategory> categoryList = getNewsCategoryByPid(pid);
		if (pid == 0) {
			TreeNode root = new TreeNode();
			root.setText("信息类型");
			root.setId(0);
			root.setChildren(category2TreeNode(categoryList));
			root.setState("open");
			NewsCategory categoryroot = new NewsCategory();
			categoryroot.setId(0);
			categoryroot.setName("根节点");
			root.setAttributes(categoryroot);
			List<TreeNode> roots = new ArrayList<TreeNode>();
			roots.add(root);
			return roots;
		}
		return category2TreeNode(categoryList);
	}

	public List<NewsCategory> getNewsCategoryByPid(int pid) {
		Map params = new HashMap();
		params.put("state", 1);
		String hql = "from NewsCategory bean where bean.state=:state and bean.parent is null order by bean.orderNo ASC";
        if(pid>0){
    	 hql ="from NewsCategory bean where bean.state=:state and bean.parent.id = :pid order by bean.orderNo ASC";
    	 params.put("pid", pid);
        }
        
		return newsCategoryDao.find(hql, params, -1, -1);
	}

	private List<TreeNode> category2TreeNode(List<NewsCategory> dicts) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		Iterator<NewsCategory> it = dicts.iterator();
		while (it.hasNext()) {
			list.add(Category2TreeNode(it.next()));

		}
		return list;
	}

	private TreeNode Category2TreeNode(NewsCategory category) {
		TreeNode node = new TreeNode();
		node.setId(category.getId());
		node.setText(category.getName());
		node.setAttributes(category);
		return node;
	}

	@Override
	public NewsCategory getNewsCategoryById(int categoryid) {
		
		return newsCategoryDao.getNewsCategoryById(categoryid);
	}
}
