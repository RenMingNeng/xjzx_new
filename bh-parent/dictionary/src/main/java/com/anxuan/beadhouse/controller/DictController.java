package com.anxuan.beadhouse.controller;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.MessageUtf8;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.sun.org.apache.xalan.internal.xsltc.cmdline.getopt.GetOptsException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;


@Controller
@RequestMapping("/system")
public class DictController {
	@Resource
	private DictService dictService;

	@RequestMapping("/dictionary/showDictionaryList")
	public ModelAndView dictManage(HttpServletRequest request,
		HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/dictionnary/dictManage", "ADMIN",request, response);
		return mav;
	}
    //加载子类新增页面
	@RequestMapping("/dictionary/dictEdit")
	public ModelAndView dictEdit(HttpServletRequest request,
			HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/dictionnary/dictEdit", "ADMIN",
				request, response);
		return mav;
	}

	@RequestMapping("/dictionary/dictSelect")
	public ModelAndView dictSelect(HttpServletRequest request,
			HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/dictionnary/dictSelect", "ADMIN",
				request, response);
		return mav;
	}

	@RequestMapping("/dictionary/add")
	public void add(PrintWriter out, Dict dict) {
		ReturnValue rv = new ReturnValue();
		try {
			dict.setAddTime(new Date());
			if (dict.getParent().getId() == 0) {
				dict.setParent(null);
			}
			String sql = "from Dict bean where bean.deleteStatus=false and bean.dictCode = '"+dict.getDictCode()+"'";
			List<Dict> list = dictService.find(sql, null, -1, -1);
			if(list.size()>0){
				rv.setCode("fail");
				rv.setMessage("Code不能重复！");
			}else{
				dictService.save(dict);
				rv.setCode("success");
				rv.setMessage("新增成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage("新增失败！");
		}
		out.print(rv.toJsonString());
		out.flush();
	}

	@RequestMapping("/dictionary/update")
	public void update(PrintWriter out, Dict dict) {
		ReturnValue rv = new ReturnValue();
		try {
			if (dict.getParent().getId() == 0) {
				dict.setParent(null);
			}
			String sql = "from Dict bean where bean.deleteStatus=false and bean.dictCode = '"+dict.getDictCode()+"'";
			List<Dict> list = dictService.find(sql, null, -1, -1);
			if(list.size()>0 && dict.getId()!=list.get(0).getId()){
				rv.setCode("fail");
				rv.setMessage("Code不能重复！");
			}else if(list.size()>0 && dict.getId()==list.get(0).getId()){
				Dict n = list.get(0);
				n.setDictCode(dict.getDictCode());
				n.setDictName(dict.getDictName());
				n.setDictType(dict.getDictType());
				n.setDictValue(dict.getDictValue());
				n.setOthers(dict.getOthers());
				n.setParent(dict.getParent());
				dictService.update(n);
				rv.setCode("success");
				rv.setMessage("修改成功！");
			}else{
				dictService.update(dict);
				rv.setCode("success");
				rv.setMessage("修改成功！");
			}
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage("修改失败！");
		}
		out.print(rv.toJsonString());
		out.flush();
	}

	@RequestMapping("/dictionary/delete")
	public void delete(PrintWriter out, String ids) {
		ReturnValue rv = new ReturnValue();
		try {
			dictService.deleteEntityState(ids,new Dict());
			rv.setCode("success");
			rv.setMessage("删除成功！");

		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage("删除失败！");
		}
		out.print(rv.toJsonString());
		out.flush();
	}

	@RequestMapping("/dictionary/getUnsyncTree")
	public void getUnsyncTree(String code, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			rv.setCode("success");
			rv.setMessage("加载成功！");
			rv.setData(dictService.getUnsyncTreeByCode(code));
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		}
		out.print(rv.toJsonString(getConfig()));
		out.flush();
	}

	@RequestMapping("/dictionary/getDictValue")
	public void getDictValue(String pid, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			rv.setCode("success");
			rv.setMessage("加载成功！");
			rv.setData(dictService.getDictValueByParentCode(pid));
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		} finally {
			out.print(rv.toJsonString(getConfig()));
			out.close();
		}
	}
	@RequestMapping("/dictionary/getDictValueByParentCode")
	public void getDicValueByParentCode(String code, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			rv.setCode("success");
			rv.setMessage("加载成功！");
			rv.setData(dictService.getDicValueByParentCode(code));
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		} finally {
			out.print(rv.toJsonString(getConfig()));
			out.close();
		}
	}

	@RequestMapping("/dictionary/getPage")
	public void getPage(EasyuiPage pageinfo, Dict dict, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			Map parms = new HashMap();
			parms.put("deleteStatus", false);
			parms.put("pdictCode", dict.getParent().getDictCode());
			String hql = "from Dict bean where bean.deleteStatus=:deleteStatus and bean.parent.dictCode = :pdictCode";
			if(dict.getDictCode()!=null&&!"".equals(dict.getDictCode())){
				hql += " and bean.dictCode = :dictCode" ;
				parms.put("dictCode", dict.getDictCode());
			}else if(dict.getDictValue()!=null&&!"".equals(dict.getDictValue())){
				hql += " and bean.dictValue=:dictValue";
				parms.put("dictValue", dict.getDictValue());
			}else if(dict.getDictName()!=null&&!"".equals(dict.getDictName())){
				hql += " and bean.dictName like :dictName";
				parms.put("dictName", "%"+dict.getDictName()+"%");
			}
			rv.setData(dictService.showListPage(hql,pageinfo, parms));
			rv.setCode("success");
			rv.setMessage("加载成功！");

		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		}
		out.print(rv.toJsonString(getConfig()));
		out.flush();
	}
	
	@RequestMapping(value = "/organ/dictByorgan")
	public void dictByorgan(HttpServletResponse response,String dictCode) throws Exception{
		ReturnValue ret = new ReturnValue();
		try {
			// 查询list
			List<Dict> list = dictService.getJGGSList(dictCode);
			ret.setData(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
		 MessageUtf8.writeMessageUft8(response, ret.toJsonString());
	}


	/**
	 * spring mvc 日期转换
	 * 
	 * @param binder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Date.class, new CustomDateEditor(
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"), true));
	}

	private JsonConfig getConfig() {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("parent")) {
					return true;
				}
				return false;
			}
		});
		return config;
	}
	
	@RequestMapping("/dictionary/getDictionaryByCode")
	public void getDictionaryByCode(String code,PrintWriter out){
		String strJson = "";
		try {
			List<Dict> dicts = dictService.getDictValueCode(code);
			JSONArray object = JSONArray.fromObject(dicts, getConfig());
			strJson = object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.print(strJson);
			out.close();
		}
	}
	@RequestMapping("/dictionary/getDictionaryByPid")
	public void getDictionaryByPid(Long pid,PrintWriter out){
		String strJson = "";
		try {
			List<Dict> dicts = dictService.getDictionaryByPid(pid);
			JSONArray object = JSONArray.fromObject(dicts, getConfig());
			strJson = object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.print(strJson);
			out.close();
		}
	}
	
	@RequestMapping("/dictionary/getDictByPcode")
	public void getDictByPcode(String code,PrintWriter out){
		String strJson = "";
		try {
			Map parms = new HashMap();
			parms.put("deleteStatus", false);
			List<Dict> dicts = dictService.find("from Dict bean where bean.deleteStatus = :deleteStatus and bean.parent.dictCode='"+code+"'",parms,-1,-1);
			JSONArray object = JSONArray.fromObject(dicts, getConfig());
			strJson = object.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.print(strJson);
			out.close();
		}
	}
	@RequestMapping("/dictionary/getDictValueByType")
	public void getDictValueByType(String code,PrintWriter out){
	  ReturnValue ret = new ReturnValue();
	  try {
		String hql = "from Dict bean where bean.deleteStatus=:deleteStatus and bean.dictType='t' and bean.parent.dictCode=:dictCode";
		Map parms = new HashMap();
		parms.put("deleteStatus", false);
		parms.put("dictCode", code);
		ret.setCode("success");
		ret.setData(dictService.find(hql, parms, -1, -1));
	 } catch (Exception e) {
		 e.printStackTrace();
		ret.setCode("failure");
		ret.setMessage(e.getMessage());
	 }finally {
		out.println(ret.toJsonString());
		out.flush();
		out.close();
	}
	}
	@RequestMapping("/dictionary/getDictValueList")
	public void getDictValueList(PrintWriter out){
		ReturnValue rv = new ReturnValue();
		try {
			rv.setCode("success");
			rv.setMessage("加载成功！");
			rv.setData(dictService.getDictValueList());
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		} finally {
			out.print(rv.toJsonString(getConfig()));
			out.close();
		}
	}
	@RequestMapping("/dictionary/findDictValue")
	public void findDictValue(PrintWriter out){
		  ReturnValue ret = new ReturnValue();
		  try {
			String hql = "from Dict bean where bean.deleteStatus=:deleteStatus and bean.dictType='l'";
			Map parms = new HashMap();
			parms.put("deleteStatus", false);
			ret.setCode("success");
			ret.setData(dictService.find(hql, parms, -1, -1));
		 } catch (Exception e) {
			 e.printStackTrace();
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		 }finally {
			out.println(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	//加载子类修改页面
	@RequestMapping("/dictionary/dictUpdate")
	public ModelAndView dictUpdate(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/dictionnary/dictEdit", "ADMIN", request, response);
		return mav;
	} 
	//加载新增页面
	@RequestMapping("/dictionary/dictSave")
	public ModelAndView dictSave(String dictPid,HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/dictionnary/dictEdit", "ADMIN", request, response);
		mav.addObject("dictPid", dictPid);
		return mav;
	}
	//加载新增修改
	@RequestMapping("/dictionary/dictModify")
	public ModelAndView dictModify(String dictPid,HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/dictionnary/dictEdit", "ADMIN", request, response);
		mav.addObject("dictPid", dictPid);
		return mav;
	} 
	@RequestMapping("/dictionary/deleteDict")
	public void deleteDict(PrintWriter out, String ids) {
		ReturnValue rv = new ReturnValue();
		try {
			dictService.deleteEntityState(ids,new Dict());
			rv.setCode("success");
			rv.setMessage("删除成功！");

		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage("删除失败！");
		}
		out.print(rv.toJsonString());
		out.flush();
	}
}
