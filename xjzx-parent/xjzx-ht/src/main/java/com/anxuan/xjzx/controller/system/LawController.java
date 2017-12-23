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
import com.anxuan.xjzx.bean.Law;
import com.anxuan.xjzx.service.LawService;

@Controller
@RequestMapping("/system/law")
public class LawController {
	@Resource
	private LawService lawService;

	@RequestMapping("index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/law/law_index", "ADMIN", request, response);
		return mav;
	}

	@RequestMapping("loadAddLaw")
	public ModelAndView loadAddLaw(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/law/law_edit", "ADMIN", request, response);
		return mav;
	}

	@RequestMapping("loadUpdateLaw")
	public ModelAndView loadUpdateLaw(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/law/law_edit", "ADMIN", request, response);
		return mav;
	}

	@RequestMapping("getLawsPage")
	public void getLawsPage(Law law, EasyuiPage easyuiPage, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			String hql = "from Law bean where bean.state=:state";
			Map params = new HashMap();
			params.put("state", 1);
			if(law.getTilte()!=null&&!"".equals(law.getTilte())){
				hql+=" and bean.tilte like:tilte";
				params.put("tilte", "%"+law.getTilte()+"%");
			}if(law.getContent()!=null&&!"".equals(law.getContent())){
				hql+=" and bean.content like:content";
				params.put("content", "%"+law.getContent()+"%");
			} 
			easyuiPage.setOrder("desc");
			easyuiPage.setSort("adddate");
			easyuiPage = lawService.showListPage(hql, easyuiPage, params);
			ret.setData(easyuiPage);
			ret.setCode("success");
			ret.setMessage("加载成功!");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}

	@RequestMapping("addLaw")
	public void addLaw(Law law, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			lawService.save(law);
			ret.setCode("success");
			ret.setMessage("新增成功!");
		} catch (Exception e) {
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	@RequestMapping("updateLaw")
	public void updateLaw(Law law, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			lawService.update(law);
			ret.setCode("success");
			ret.setMessage("修改成功!");
		} catch (Exception e) {
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	@RequestMapping("deleteLaw")
	public void deleteLaw(String ids , PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			lawService.deleteids(ids);
			ret.setCode("success");
			ret.setMessage("删除成功!");
		} catch (Exception e) {
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	@RequestMapping("updateLawByIspass")
	public void updateLawByIspass(String ids,PrintWriter out,int ispass){
		ReturnValue ret = new ReturnValue();
		try {
			lawService.updateLawByIspass(ids,ispass);
			ret.setCode("success");
			ret.setMessage("审核处理成功！");
		} catch (Exception e) {
			e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
}
