package com.anxuan.beadhouse.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.usertype.UserCollectionType;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.http.HttpInclude;
import com.anxuan.beadhouse.util.CommUtil;

public class JModelAndView extends ModelAndView {
	public JModelAndView(String viewName) {
		super.setViewName(viewName);
	}

	public JModelAndView(String viewName, String role,
			HttpServletRequest request, HttpServletResponse response) {
		super.setViewName(viewName);
		String  webPath = CommUtil.getURL(request);
		if("ADMIN".equals(role)){
			super.setViewName("/system"+viewName);
		}else if("USER".equals(role)){
			super.setViewName("/front"+viewName);
			super.addObject("httpInclude", new HttpInclude(request,response));
		}
		   super.addObject("webPath", webPath);
	}

}
