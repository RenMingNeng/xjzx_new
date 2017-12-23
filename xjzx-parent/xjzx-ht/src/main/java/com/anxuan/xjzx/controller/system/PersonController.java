package com.anxuan.xjzx.controller.system;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.anxuan.xjzx.bean.Person;
import com.anxuan.xjzx.service.PersonService;

@Controller
@RequestMapping("/system/person")
public class PersonController {
  @Resource
  private PersonService personService;

  	//加载新增页面
	@RequestMapping("loadAddPerson")
	public ModelAndView loadAddQuestion(HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/person/person_add", "ADMIN", request, response);
		  return mav;
	}
  
  	// 加载修改页面
	@RequestMapping("loadUpdatePerson")
	public ModelAndView loadUpdatePerson(HttpServletRequest request,HttpServletResponse response){
		  JModelAndView mav = new JModelAndView("/person/person_update", "ADMIN", request, response);
		  String questionId = request.getParameter("id");
		  Map parms = new HashMap();
		  parms.put("id", Integer.valueOf(questionId));
		  Person person = personService.find("from Person bean where bean.id = :id", parms, -1, -1).get(0);
		  mav.addObject("person", person);
		  return mav;
	}
	
	// 新增(保存)
	@RequestMapping("addPerson")
	public void addPerson(Person person,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			person.setCreateDate(new Date());
			personService.save(person);
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
	@RequestMapping("updatePerson")
	public void updatePerson(Person person,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			person.setCreateDate(new Date());
			personService.update(person);
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
	@RequestMapping("deletePerson")
	public void deletePerson(String ids,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			// 删除
			personService.deleteByIds(ids);
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
		
	// 调查列表页
	@RequestMapping("getPersonPage")
	  public void getPersonPage(Person person,EasyuiPage easyuiPage,PrintWriter out){
		  ReturnValue ret = new ReturnValue();
		  try {
			  String hql ="from Person bean where is_valid=1";
			  Map params = new HashMap();
			  if(person.getName()!=null && !"".equals(person.getName())){
				  hql+=" and bean.name like :name";
				  params.put("name", "%"+person.getName()+"%");
			  }
			  hql+=" order by bean.createDate desc";
		     
			  easyuiPage = personService.showListPage(hql, easyuiPage, params);
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
