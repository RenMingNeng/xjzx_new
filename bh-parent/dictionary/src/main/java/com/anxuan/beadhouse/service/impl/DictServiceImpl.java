package com.anxuan.beadhouse.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.dao.DictDao;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.util.TreeNode;

@Service(value = "dictService")
@Transactional
public class DictServiceImpl extends BaseServiceImpl<Dict> implements
		DictService {
	@Resource
	private DictDao dictDao;

	public List<TreeNode> getUnsyncTreeByCode(String code) {
		List<Dict> dicts = dictDao.getChildDictByCode(code);
		if (code == null || "".equals(code)) {
			TreeNode root = new TreeNode();
			root.setText("数据字典");
			root.setId(0);
			root.setChildren(dict2TreeNode(dicts));
			root.setState("open");
			Dict dictroot = new Dict();
			dictroot.setId(0);
			dictroot.setDictName("根节点");
			root.setAttributes(dictroot);
			List<TreeNode> roots = new ArrayList<TreeNode>();
			roots.add(root);
			return roots;
		}
		return dict2TreeNode(dicts);
	}

	private List<TreeNode> dict2TreeNode(List<Dict> dicts) {
		List<TreeNode> list = new ArrayList<TreeNode>();
		Iterator<Dict> it = dicts.iterator();
		while (it.hasNext()) {
			list.add(dict2TreeNode(it.next()));

		}
		return list;
	}

	private TreeNode dict2TreeNode(Dict dict) {
		TreeNode node = new TreeNode();
		node.setId(dict.getId());
		node.setText(dict.getDictName());
		node.setAttributes(dict);
		return node;
	}

	public List<Dict> getDictValueByParentCode(String pids) {
		List<String> pids_ = Arrays.asList(pids.split(","));
		 List<Dict> dicts = new ArrayList<Dict>();
		for (String pid : pids_) {
			List<Dict> list = dictDao.getDictValueByParentCode(pid);
			dicts.addAll(list);
		}
		return dicts;
	}


	public List<TreeNode> getDicValueByParentCode(String pid) {
		return  dict2TreeNode(getDictValueByParentCode(pid));
	}

	public List<Dict> getDictValueCode(String code) {
		return dictDao.getDictValueCode(code);
		//return findEntityByParms("from Dict bean where bean.parent.dictCode = ? and bean.deleteStatus = 0", new Object[]{code});
	}

	public List<Dict> getDictionaryByPid(Long pid) {
		return dictDao.getDictionaryByPid(pid);
		//return findEntityByParms("from Dict bean where bean.parent.id= ? and bean.deleteStatus = 0",new Object[]{pid});
	}

	@Override
	public Dict getDictBycode(String code) {
		return dictDao.getDictBycode(code);
	}

	@Override
	public Dict getDictByid(Long id) {
		return dictDao.get(id);
	}

	@Override
	public List<TreeNode> getDictValueList() {
		return dict2TreeNode(findDictValueList());
	}
    private List<Dict> findDictValueList(){
    	String queryStr= "from Dict bean where bean.dictType='l' and bean.deleteStatus=:deleteStatus";
    	Map param = new HashMap();
    	param.put("deleteStatus", false);
    	return dictDao.find(queryStr, param, -1, -1);
    }

	@Override
	public List<TreeNode> getSyncTreeByPCode(String code,List<Long> ids) {
		List<Dict> list = getDictValueCode(code);
		List<TreeNode> nodelist = new ArrayList<TreeNode>();
		Iterator<Dict> it = list.iterator();
		while(it.hasNext()){
			nodelist.add(dict2SyncTreeNode(it.next(),ids));
		}
		
		return nodelist;
	}
	
	private TreeNode dict2SyncTreeNode(Dict dict,List<Long> ids) {
		TreeNode node = new TreeNode();
		node.setId(dict.getId());
		node.setText(dict.getDictName());
		node.setAttributes(dict);
		if(ids!=null && ids.contains(dict.getId())){
			node.setChecked(true);
		}
		List<Dict> list = getDictValueCode(dict.getDictCode());
		if(list.size()>0){
			Iterator<Dict> it = list.iterator();
			List<TreeNode> childlist = new ArrayList<TreeNode>();
			while(it.hasNext()){
				childlist.add(dict2SyncTreeNode(it.next(),ids));
			}
			node.setChildren(childlist);
			node.setState("open");
		}
		return node;
	}

	@Override
	public List<Dict> getJGGSList(String dictCode) {
		List<Map<String, Object>> listMap = new ArrayList<Map<String, Object>>();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("dictCode", dictCode+"%");
		param.put("pid", 18L);
		String queryStr= "from Dict bean where bean.dictCode like :dictCode and bean.parent.id>=:pid";
		List<Dict> list = dictDao.find(queryStr, param, -1, -1);
    	return list;
	}
	
}
