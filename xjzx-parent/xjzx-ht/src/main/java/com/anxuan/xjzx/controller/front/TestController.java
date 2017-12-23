package com.anxuan.xjzx.controller.front;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.view.JModelAndView;

@Controller
public class TestController {
	@RequestMapping("/certificate/123")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/test", "USER", request, response);
		
		return mav;
	}
	 @RequestMapping("/certificate/aaa")
     public String loing(String name,String pwd,Model model){
    	  System.out.println(name+pwd);
    	  if(name.equals("admin")&&pwd.equals("123")){
    		  List list=new ArrayList();
    		  list.add("张三");
    		  list.add("张三2");
    		  list.add("张三3");
    		  model.addAttribute("list", list);
    		  return "view/front/test2";
    	  }else{
    		  
    		  return "view/front/test";
    	  }    	      	     	  
      }  
}
