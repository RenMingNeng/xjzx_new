package com.anxuan.xjzx.service;

import java.util.List;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.Specialic;


public interface SpecialicService extends BaseService<Specialic>{

	List<Specialic> getspecialicByCardNumber(String cardNumber);

	List<Specialic> findAll();

    public void addIndex();

	List<Specialic> findSpecialicByCardnumber(String cardnumber);

	Integer deleteBatch(List<Specialic> specialics);

	Integer saveBatch(List<Specialic> specialics);

}
