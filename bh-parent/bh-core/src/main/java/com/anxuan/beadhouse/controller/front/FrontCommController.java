package com.anxuan.beadhouse.controller.front;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.anxuan.beadhouse.view.JModelAndView;

@Controller
public class FrontCommController {
	
	@RequestMapping("/index1")
	public JModelAndView index(HttpServletRequest request, HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/index","USER",request,response);
		mav.addObject("selective","index");
		return mav;
	}
	@RequestMapping("/pinggu")
	public JModelAndView pinggu(HttpServletRequest request, HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/pinggu","USER",request,response);
		mav.addObject("selective","pinggu");
		return mav;
	}
}
