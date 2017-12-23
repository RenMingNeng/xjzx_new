package com.anxuan.xjzx.controller.system;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.xjzx.bean.Email;
import com.anxuan.xjzx.service.EmailService;

@Controller
@RequestMapping("/system/email")
public class EmailController {
  @Resource
  private EmailService emailService;

   @RequestMapping("index")
 	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
 		JModelAndView mav = new JModelAndView("/email/email_index", "ADMIN", request, response);
 		return mav;
 	}
   
    // 加载修改页面
    @RequestMapping("loadUpdateEmail")
    public ModelAndView loadUpdateAdvise(String ids,HttpServletRequest request,HttpServletResponse response){
	 	JModelAndView mav = new JModelAndView("/email/email_edit", "ADMIN", request, response);
	 	return mav;
    }
  
  	//加载新增页面
	@RequestMapping("loadAddEmail")
	public ModelAndView loadAddQuestion(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/email/email_edit", "ADMIN", request, response);
		return mav;
	}
  
  	/*// 加载修改页面
	@RequestMapping("loadUpdateEmail")
	public ModelAndView loadUpdateEmail(HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/email/email_update", "ADMIN", request, response);
		  String questionId = request.getParameter("id");
		  Map parms = new HashMap();
		  parms.put("id", Integer.valueOf(questionId));
		  Email email = emailService.find("from Email bean where bean.id = :id", parms, -1, -1).get(0);
		  mav.addObject("email", email);
		  return mav;
	}*/
	
	// 新增(保存)
	@RequestMapping("addEmail")
	public void addEmail(Email email,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			emailService.save(email);
			ret.setCode("success");
			ret.setMessage("保存成功!");
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
	
	// 修改(保存)
	@RequestMapping("updateEmail")
	public void updateEmail(Email email,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			emailService.update(email);
			ret.setCode("success");
			ret.setMessage("保存成功!");
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
	
	// 删除
	@RequestMapping("deleteEmail")
	public void deleteEmail(String ids,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			// 删除
			emailService.deleteByIds(ids);
			ret.setCode("success");
			ret.setMessage("删除成功!");
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
		
	// 邮箱列表页
	@RequestMapping("getEmailPage")
	  public void getEmailPage(Email email,EasyuiPage easyuiPage,PrintWriter out){
		  ReturnValue ret = new ReturnValue();
		  try {
			  String hql ="from Email bean where 1=1";
			  Map params = new HashMap();
			  if(email.getName()!=null && !"".equals(email.getName())){
				  hql+=" and bean.name like :name";
				  params.put("name", "%"+email.getName()+"%");
			  }
			  easyuiPage = emailService.showListPage(hql, easyuiPage, params);
			  ret.setData(easyuiPage);
			  ret.setCode("success");
			  ret.setMessage("加载成功!");
		  } catch (Exception e) {
			  e.printStackTrace();
			  ret.setCode("success");
			  ret.setMessage(e.getMessage());
		 }finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	  }

}
