package com.anxuan.xjzx.service.impl;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.anxuan.beadhouse.service.impl.BaseServiceImpl;
import com.anxuan.xjzx.bean.TrainTeacher;
import com.anxuan.xjzx.dao.TrainTeacherDao;
import com.anxuan.xjzx.service.TrainTeacherService;
import com.anxuan.xjzx.util.ReadExcel;
@Service
@Transactional
public class TrainTeacherServiceImpl extends BaseServiceImpl<TrainTeacher> implements TrainTeacherService{
    @Resource
	private TrainTeacherDao trainTeacherDao; 
	@Override
	public void deleteByState(String ids) {
		trainTeacherDao.deleteByState(ids);
		
	}
	
	@Override
	public Map readExcelFile(MultipartFile file, String type) {
		Map map = new HashMap();  
		String msg = "";
		List<String> resultList = new ArrayList<String>();
        //创建处理EXCEL的类  
        ReadExcel readExcel=new ReadExcel();  
        //解析excel，获取excel中的数据集合  
        List<TrainTeacher> dataList = readExcel.getExcelInfo(file,type); 
        List<TrainTeacher> teacherList_yes = new ArrayList<TrainTeacher>();
        List<TrainTeacher> teacherList_no = new ArrayList<TrainTeacher>();
        if(null != dataList && dataList.size() > 0){
        	// 处理数据(根据证书号去重)
        	Map params = new HashMap();
        	params.put("type", type);
        	List<String> certinumList = trainTeacherDao.selectCertinumByParams(params);
        	for (TrainTeacher trainTeacher : dataList) {
        		if(certinumList.contains(trainTeacher.getCertinum())){
        			teacherList_no.add(trainTeacher);
        		}else{
        			teacherList_yes.add(trainTeacher);
        		}
			}
        	
        }
        // 批量插入
        if(teacherList_yes.size()>0){
        	int count = trainTeacherDao.saveBatch(teacherList_yes);
        	int no = dataList.size()-count;
        	msg = "<span style=\"color: green;font-weight: bolder;\">"+count+"</span>条成功；<span style=\"color: red;font-weight: bolder;\">"+no+"</span>条失败";  
        } else{
        	msg = "<span style=\"color: red;font-weight: bolder;\">"+dataList.size()+"</span>条数据上传失败！"; 
        }
        // 上传失败的证书编号集合
        for (TrainTeacher trainTeacher : teacherList_no) {
        	resultList.add(trainTeacher.getCertinum());
		}
    	map.put("resultList", resultList);
        map.put("msg", msg);
        return map;
	}


}
