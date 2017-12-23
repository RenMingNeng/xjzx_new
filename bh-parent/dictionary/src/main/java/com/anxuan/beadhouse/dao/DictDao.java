package com.anxuan.beadhouse.dao;

import java.util.List;

import com.anxuan.beadhouse.bean.Dict;

public interface DictDao extends BaseDao<Dict> {

	List<Dict> getChildDictByCode(String code);

	List<Dict> getDictValueByParentCode(String pid);

	List<Dict> getDictValueCode(String code);

	List<Dict> getDictionaryByPid(Long pid);

	Dict getDictBycode(String code);

}
