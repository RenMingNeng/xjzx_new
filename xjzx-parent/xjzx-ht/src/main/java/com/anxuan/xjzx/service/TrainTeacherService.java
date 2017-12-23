package com.anxuan.xjzx.service;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.anxuan.beadhouse.service.BaseService;
import com.anxuan.xjzx.bean.TrainTeacher;

public interface TrainTeacherService extends BaseService<TrainTeacher>{

	void deleteByState(String ids);

	/** 
     * 读取excel中的数据,生成list 
     */
	Map readExcelFile(MultipartFile file, String type);

}
