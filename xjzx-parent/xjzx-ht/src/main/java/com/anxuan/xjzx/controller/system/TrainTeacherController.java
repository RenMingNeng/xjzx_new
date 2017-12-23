package com.anxuan.xjzx.controller.system;

import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream.GetField;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.TrainTeacher;
import com.anxuan.xjzx.service.TrainTeacherService;

@Controller
@RequestMapping("/system")
public class TrainTeacherController {
	 @Resource
	 private TrainTeacherService trainTeacherService;
 
 	//加载全省监考人员首页
	@RequestMapping("/proctor/index")
	public ModelAndView pIndex(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/proctor/proctor_index", "ADMIN", request, response);
		return mav;
	}
	
	 //全省安全培训师资名单
	@RequestMapping("/evaluate/index")
	public ModelAndView eIndex(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/trainTeacher/evaluate/evaluate_index", "ADMIN", request, response);
		return mav;
	}
	
	 //加载全省考评人员名单首页
	@RequestMapping("/teacher/index")
	public ModelAndView tIndex(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/teacher/teacher_index", "ADMIN", request, response);
		return mav;
	}
	
	@RequestMapping("/trainTeacher/getTrainTeacherPage")
	public void getTrainTeacherPage(TrainTeacher trainTeacher,EasyuiPage easyuiPage,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			String hql ="from TrainTeacher bean where bean.state=:state";
			Map params = new HashMap();
			params.put("state", 1);
			if(trainTeacher.getType()>0){
				hql+=" and bean.type=:type";
				params.put("type", trainTeacher.getType());
			}if(trainTeacher.getCertinum()!=null&&!"".equals(trainTeacher.getCertinum())){
				hql+=" and bean.certinum like:certinum";
				params.put("certinum", "%"+trainTeacher.getCertinum()+"%");
			}if(trainTeacher.getName()!=null&&!"".equals(trainTeacher.getName())){
				hql+=" and bean.name like:name";
				params.put("name", "%"+trainTeacher.getName()+"%");
			}
			easyuiPage = trainTeacherService.showListPage(hql, easyuiPage, params);
			ret.setData(easyuiPage);
			ret.setCode("success");
			ret.setMessage("加载成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	//加载监考人员新增页面
	@RequestMapping("/proctor/loadEdit")
	public JModelAndView loadAddProctor(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/proctor/proctor_edit", "ADMIN", request, response);
		return mav;
	}
	
	//加载监考人员修改页面
	@RequestMapping("/proctor/loadUpdate")
	public JModelAndView loadUpdateProctor(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/proctor/proctor_edit", "ADMIN", request, response);
		return mav;
	}
	
	//加载考评人员新增页面
	@RequestMapping("/evaluate/loadEdit")
	public JModelAndView loadAddEvaluate(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/evaluate/evaluate_edit", "ADMIN", request, response);
		return mav;
	}
	
	//加载考评人员修改页面
	@RequestMapping("/evaluate/loadUpdate")
	public JModelAndView loadUpdateEvaluate(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/evaluate/evaluate_edit", "ADMIN", request, response);
		return mav;
	}
	
	//加载师资人员新增页面
	@RequestMapping("/teacher/loadEdit")
	public JModelAndView loadAddTeacher(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/teacher/teacher_edit", "ADMIN", request, response);
		return mav;
	}
	
	//加载师资人员修改页面
	@RequestMapping("/teacher/loadUpdate")
	public JModelAndView loadUpdateTeacher(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/teacher/teacher_edit", "ADMIN", request, response);
		return mav;
	}
	
	//加载人员批量导入页面
	@RequestMapping("/trainTeacher/loadbatchImport")
	public JModelAndView loadbatchImport(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/trainTeacher/teacher_import", "ADMIN", request, response);
		String type = request.getParameter("type");
		mav.addObject("type", type);
		return mav;
	}
	
	// 模板下载
	@RequestMapping(value = "/trainTeacher/tempDownload")
	public ResponseEntity<byte[]> tempDownload(HttpServletRequest request,HttpServletResponse response){
		try {
			String tempName = "Train_Teacher.xlsx";
			String filePath = request.getSession().getServletContext().getRealPath("/resource/temp/"+tempName);
			File file = ResourceUtils.getFile(filePath);
			HttpHeaders headers = new HttpHeaders();
			headers.setContentDispositionFormData("attachment", new String(tempName.getBytes("UTF-8"), "iso-8859-1"));
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
			byte[] rs = FileUtils.readFileToByteArray(file);
			ResponseEntity<byte[]> responseEntity = new ResponseEntity<byte[]>(rs, headers, HttpStatus.OK);
			return responseEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@RequestMapping("/train_teacher/insertBatch")
	public void insertBatch(MultipartFile file,String type,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			Map map = trainTeacherService.readExcelFile(file,type);
			String msg = map.get("msg").toString();
			List<String> resultList = (List<String>) map.get("resultList");
			ret.setCode("success");
			ret.setMessage(msg);
			ret.setListData(resultList);
		 } catch (Exception e) {
			 ret.setCode("failure");
			 ret.setMessage(e.getMessage());
		 }finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("/trainTeacher/save")
	public void trainTeacherSave(TrainTeacher trainTeacher,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			trainTeacherService.save(trainTeacher);
			ret.setCode("success");
			ret.setMessage("新增成功!");
		 } catch (Exception e) {
			 ret.setCode("failure");
			 ret.setMessage(e.getMessage());
		 }finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("/trainTeacher/update")
	public void trainTeacherUpdate(TrainTeacher trainTeacher,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			trainTeacherService.update(trainTeacher);
			ret.setCode("success");
			ret.setMessage("修改成功!");
		 } catch (Exception e) {
			 ret.setCode("failure");
			 ret.setMessage(e.getMessage());
		 }finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	
	@RequestMapping("/trainTeacher/delete")
	public void trainTeacherDelete(String ids,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			trainTeacherService.deleteByState(ids);
			ret.setCode("success");
			ret.setMessage("删除成功!");
		 } catch (Exception e) {
			 ret.setCode("failure");
			 ret.setMessage(e.getMessage());
		 }finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
}
