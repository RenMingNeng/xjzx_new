package com.anxuan.beadhouse.service;

import java.util.List;
import java.util.Map;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.util.TreeNode;

public interface DictService extends BaseService<Dict> {

	List<TreeNode> getUnsyncTreeByCode(String code);

	List<Dict> getDictValueByParentCode(String pid);

	List<TreeNode> getDicValueByParentCode(String code);

	List<Dict> getDictValueCode(String code);

	List<Dict> getDictionaryByPid(Long pid);

	public Dict getDictBycode(String code);

	Dict getDictByid(Long id);

	List<TreeNode> getDictValueList();
	/**
	 * 根据ParentCode获取同步数
	 * @param code parent Code
	 * @param ids 需要默认check的ID集合，如果不需要Check传入null
	 * @return
	 */
	List<TreeNode> getSyncTreeByPCode(String code,List<Long> ids);

	List<Dict> getJGGSList(String dictCode);
}
