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
import com.anxuan.xjzx.bean.NewsCategory;
import com.anxuan.xjzx.service.NewsCategoryService;

@Controller
@RequestMapping("/system/category")
public class SysNewsCategoryController {
	@Resource
	private NewsCategoryService newsCategoryService;

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/newscategory/category_index", "ADMIN", request, response);
		return mav;
	}

	// 加载新增页面
	@RequestMapping("/CategorySave")
	public ModelAndView CategorySave(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/newscategory/category_edit", "ADMIN", request, response);
		return mav;
	}

	@RequestMapping("/add")
	public void add(NewsCategory newsCategory, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			if (newsCategory.getParent().getId() == 0) {
				newsCategory.setParent(null);
			}
			newsCategoryService.save(newsCategory);
			ret.setCode("success");
			ret.setMessage("新增成功!");
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

	@RequestMapping("/update")
	public void update(NewsCategory newsCategory, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			if(newsCategory.getParent().getId()==0){
				newsCategory.setParent(null);
			}
			newsCategoryService.update(newsCategory);
			ret.setCode("success");
			ret.setMessage("修改成功!");
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

	@RequestMapping("/delete")
	public void delete(String ids, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			newsCategoryService.deleteState(ids);
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

	// 选择父类
	@RequestMapping("/categorySelect")
	public ModelAndView categorySelect(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/newscategory/category_select", "ADMIN", request, response);
		return mav;
	}

	// 异步类型tree
	@RequestMapping("/getUnsyncTree")
	public void getUnsyncTree(int pid, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			ret.setData(newsCategoryService.getUnsyncTreeByPid(pid));
			ret.setCode("success");
			ret.setMessage("加载成功!");
		} catch (Exception e) {
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		} finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}

	@RequestMapping("/getPage")
	public void newsCategoryListJson(EasyuiPage easyuiPage, NewsCategory newsCategory, PrintWriter out) {
		ReturnValue ret = new ReturnValue();
		try {
			ret.setCode("sucess");
			ret.setMessage("加载成功!");
			String hql = "from NewsCategory bean where bean.state =:state";
			Map params = new HashMap();
			params.put("state", 1);
			if(newsCategory.getName()!=null&&!"".equals(newsCategory.getName())){
				hql+=" and bean.name like :name";
				params.put("name", "%"+newsCategory.getName()+"%");
			}
			hql += " order by bean.orderNo asc";
			easyuiPage = newsCategoryService.showListPageNoOrder(hql, easyuiPage, params);
			ret.setData(easyuiPage);
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
	@RequestMapping("/categoryModify")
	public ModelAndView categoryModify(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/newscategory/category_edit", "ADMIN", request, response);
		return mav;
	}
}
