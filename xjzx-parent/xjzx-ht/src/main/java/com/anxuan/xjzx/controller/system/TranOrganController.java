package com.anxuan.xjzx.controller.system;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.view.JModelAndView;

@Controller
@RequestMapping("/system")
public class TranOrganController {
	//机构
	@RequestMapping("/organ/index")
	public ModelAndView organIndex(HttpServletRequest request, HttpServletResponse response) {
	 JModelAndView mav = new JModelAndView("/trainOrgan/organ/organ_index", "ADMIN", request, response);
	 return mav;
	}

	// 考试中心
	@RequestMapping("/resume/index")
	public ModelAndView resumeIndex(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/trainOrgan/resume/resume_index", "ADMIN", request, response);
		return mav;
	}

	// 考试中心
	@RequestMapping("/exam/index")
	public ModelAndView examIndex(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/trainOrgan/exam/exam_index", "ADMIN", request, response);
		return mav;
	}
}
