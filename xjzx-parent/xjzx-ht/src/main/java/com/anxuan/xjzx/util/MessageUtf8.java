package com.anxuan.xjzx.util;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

public class MessageUtf8 {

	public static void writeMessageUft8(HttpServletResponse response, Comparable str) throws Exception{
		try{
			JSONObject jsonObject = new JSONObject();
			response.setContentType("text/html;charset=UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print(str);
		}finally{
			response.getWriter().close();
		}
	}
}
