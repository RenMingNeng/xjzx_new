package com.anxuan.xjzx.service;

import java.util.List;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.SafetymanageCard;

public interface SafetymanageCardService extends BaseService<SafetymanageCard> {

	void addIndex();

	List<SafetymanageCard> getSafetymanageCardByIdcard(String idcard, int line);

	void quchong(int line);

	Integer deleteBatch(List<SafetymanageCard> safetymanageCards);

	Integer saveBatch(List<SafetymanageCard> safetymanageCards);

}
