package com.anxuan.beadhouse.view;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.view.AbstractView;

import net.sf.json.JSONObject;


public class JsonView extends AbstractView{
	protected void renderMergedOutputModel(Map<String, Object> object, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		    PrintWriter out = response.getWriter();
		    JSONObject jb = new JSONObject();
	        out.print(jb.fromObject(object).toString());             

		
	}
}
