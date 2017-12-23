package com.anxuan.xjzx.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.beadhouse.util.TreeNode;
import com.anxuan.xjzx.bean.TranOrgan;
import com.anxuan.xjzx.dao.TranOrganDao;
import com.anxuan.xjzx.service.TranOrganService;

@Service(value = "tranOrganService")
public class TranOrganServiceImpl extends BaseServiceImpl<TranOrgan> implements TranOrganService {
	@Resource
	private DictService dictService;
	@Resource
	private TranOrganDao tranOrganDao;

	@Override
	public void deleteids(String ids) {
		tranOrganDao.deleteids(ids);

	}

	@Override
	public List<TreeNode> getAearTree(String dictCode) {
		List<Dict> dictList = getDictByAear(dictCode);
		return dictListTreeNode(dictList);
	}

	private List<TreeNode> dictListTreeNode(List<Dict> dictList) {
		List<TreeNode> treeNodeList = new ArrayList<TreeNode>();
		for (Dict dict : dictList) {
			treeNodeList.add(dictTreeNode(dict));
		}
		return treeNodeList;
	}

	private TreeNode dictTreeNode(Dict dict) {
		TreeNode treeNode = new TreeNode();
		treeNode.setId(dict.getId());
		treeNode.setText(dict.getDictName());
		treeNode.setChecked(false);
		List<Dict> chidDictList = getChidlrenByarea(dict.getId());
		treeNode.setChildren(getChildTreeNode(chidDictList));
		treeNode.setAttributes(dict);
		return treeNode;
	}

	private List<TreeNode> getChildTreeNode(List<Dict> chidList) {
		List<TreeNode> treeNodeChildList = new ArrayList<TreeNode>();
		for (Dict dict : chidList) {
			TreeNode treeNode = dictTreeNode(dict);
			treeNodeChildList.add(treeNode);
		}
		return treeNodeChildList;
	}

	private List<Dict> getChidlrenByarea(long id) {
		String hql = "from Dict bean where bean.deleteStatus=:deleteStatus and bean.parent.id=:pid";
		Map params = new HashMap();
		params.put("deleteStatus", false);
		params.put("pid", id);
		return dictService.find(hql, params, -1, -1);
	}

	private List<Dict> getDictByAear(String dictCode) {
		String hql = "from Dict bean where bean.deleteStatus=:deleteStatus and bean.parent.dictCode=:pdictCode";
		Map params = new HashMap();
		params.put("deleteStatus", false);
		params.put("pdictCode", dictCode);
		return dictService.find(hql, params, -1, -1);
	}

	@Override
	public TranOrgan getTranOrganById(int id) {
		return tranOrganDao.getTranOrganById(id);
	}

}
