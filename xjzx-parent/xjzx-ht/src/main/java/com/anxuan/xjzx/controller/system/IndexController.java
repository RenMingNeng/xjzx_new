package com.anxuan.xjzx.controller.system;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;

@Controller
public class IndexController {
	@RequestMapping("/system/login")
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response){
	  boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
	  if(isAjax){
		  PrintWriter out = null;
		  ReturnValue ret = new ReturnValue();
		  ret.setCode("nosession");
		  ret.setMessage("您的账号超时,请重新登陆!");
		  try {
			  out = response.getWriter();
		  } catch (Exception e) {
			  ret.setCode("failure");
			  ret.setMessage("系统出现异常！");
		  }finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
		   return null;
		}else{
			JModelAndView mav = new JModelAndView("/login", "ADMIN", request, response);
			return mav;
		}
	}
	//session超时跳转
	@RequestMapping("/system/loginout")
	public ModelAndView loginout(HttpServletRequest request,HttpServletResponse response){
		boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		if(isAjax){
			  PrintWriter out = null;
			  ReturnValue ret = new ReturnValue();
			  ret.setCode("nosession");
			  ret.setMessage("您的账号超时,请重新登陆!");
			  try {
				  out = response.getWriter();
			  } catch (Exception e) {
				  ret.setCode("failure");
				  ret.setMessage("系统出现异常！");
			  }finally {
				out.print(ret.toJsonString());
				out.flush();
				out.close();
			}
		 return null;
		}else{
			JModelAndView mav = new JModelAndView("/login", "ADMIN", request, response);
			return mav;
		}
	}
}
