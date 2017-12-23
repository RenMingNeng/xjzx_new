package com.anxuan.beadhouse.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;

import com.anxuan.beadhouse.view.JModelAndView;

@Controller
public class LoginController {
	
	public JModelAndView index(HttpServletRequest request, HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/login","ADMIN",request,response);
		return mav;
	}
	
	
}
