package com.anxuan.xjzx.controller.system;

import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.TranOrgan;
import com.anxuan.xjzx.service.TranOrganService;

@Controller
@RequestMapping("/system/exam")
public class ExamController {
	  @Resource
	  private TranOrganService tranOrganService;
	  @RequestMapping("loadAddExam")
	  public ModelAndView loadAddOrgan(HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/trainOrgan/exam/exam_edit", "ADMIN", request, response);
		  return mav;
	  }
	  @RequestMapping("loadUpdateExam")
	  public ModelAndView loadUpdateOrgan(HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/trainOrgan/exam/exam_edit", "ADMIN", request, response);
		  return mav;
	  }
	  @RequestMapping("addExam")
	  public void addOrgan(TranOrgan tranOrgan,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			tranOrganService.save(tranOrgan);
			ret.setCode("success");
			ret.setMessage("新增成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	  }
	  @RequestMapping("updateExam")
	  public void updateOrgan(TranOrgan tranOrgan,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			tranOrganService.update(tranOrgan);
			ret.setCode("success");
			ret.setMessage("新增成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	  }
	  @RequestMapping("deleteExam")
	  public void deleteOrgan(String ids,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			tranOrganService.deleteids(ids);
			ret.setCode("success");
			ret.setMessage("新增成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}  finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	  }
}
