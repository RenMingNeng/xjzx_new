package com.anxuan.power.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.power.bean.User;


public class AccessDeniedHandlerImpl  implements AccessDeniedHandler{
	private String accessDeniedUrl;
	
	public String getAccessDeniedUrl() {
		return accessDeniedUrl;
	}

	public void setAccessDeniedUrl(String accessDeniedUrl) {
		this.accessDeniedUrl = accessDeniedUrl;
	}

	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		boolean isAjax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
		if(isAjax){
			response.setContentType("text/html; charset=utf-8");
			ReturnValue ret = new ReturnValue();
			ret.setCode("nosession");
			ret.setMessage("您的账号超时,请重新登陆!");
			User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			if(user!=null){
			  ret.setCode("noroles");
			  ret.setMessage("未授权无法操作,请联系管理员!");
			}
			 PrintWriter out = response.getWriter();
			 out.print(ret.toJsonString());
			 out.flush();
			 out.close();
//		    String jsonObject = "{\"message\":\"You are not privileged to request this resource.\","+  
//		            "\"access-denied\":true,\"cause\":\"AUTHORIZATION_FAILURE\"}";  
//		            String contentType = "application/json";  
//		    		response.setContentType(contentType);  
//		         //   String jsonObject="noright";  
//		            PrintWriter out = response.getWriter();  
//		            out.print(jsonObject);  
//		            out.flush();  
//		            out.close();
//			 String path = request.getContextPath();
//			 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
// 			 response.sendRedirect(basePath+accessDeniedUrl);
//			 RequestDispatcher dispatcher = request.getRequestDispatcher(basePath+accessDeniedUrl);
//			 dispatcher .forward(request, response);
		}else{
			 String path = request.getContextPath();
			 String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
			 response.sendRedirect(basePath+accessDeniedUrl);
		}
	}

}
