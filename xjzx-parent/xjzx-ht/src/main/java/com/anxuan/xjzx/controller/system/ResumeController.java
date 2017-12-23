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
//考试中心
@Controller
@RequestMapping("/system/resume")
public class ResumeController {
	  @Resource
	  private TranOrganService tranOrganService;
	  @RequestMapping("loadAddResume")
	  public ModelAndView loadAddResume(HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/trainOrgan/resume/resume_edit", "ADMIN", request, response);
		  return mav;
	  }
	  @RequestMapping("loadUpdateResume")
	  public ModelAndView loadUpdateResume(HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/trainOrgan/resume/resume_edit", "ADMIN", request, response);
		  return mav;
	  }
	  @RequestMapping("addResume")
	  public void addResume(TranOrgan tranOrgan,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			tranOrgan.setArea(null);
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
	  @RequestMapping("updateResume")
	  public void updateResume(TranOrgan tranOrgan,PrintWriter out){
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
	  @RequestMapping("deleteResume")
	  public void deleteResume(String ids,PrintWriter out){
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
