package com.anxuan.organizational.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.bean.Dict;
import com.anxuan.beadhouse.service.DictService;
import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.organizational.bean.Organization;
import com.anxuan.organizational.serivce.OrganizationService;

import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

@Controller
@RequestMapping("/system")
public class OrganizationController {
	@Resource
	private OrganizationService organizationService;
	@Resource
	private DictService dictService;

	@RequestMapping("/organization/organization_list")
	public ModelAndView showOrganizationList(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/organization/organization_list", "ADMIN", request, response);
		
		return mav;
	}

	@RequestMapping("/organization/getOrganizationTree")
	public void getOrganizationTree(Long pid, boolean isseif, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			rv.setCode("success");
			rv.setMessage("加载成功！");
			rv.setData(organizationService.getOrganizationTree(pid,isseif));
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("failure");
			rv.setMessage(e.getMessage());
		} finally {
			out.print(rv.toJsonString(getConfig()));
			out.flush();
			out.close();
		}
	}

	// 加载新增页面
	@RequestMapping("/organization/organizationEdit")
	public ModelAndView organizationEdit(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/organization/organization_edit", "ADMIN", request, response);
		return mav;
	}
	// 加载修改页面
	@RequestMapping("/organization/organizationUpdate")
	public ModelAndView organizationUpdate(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/organization/organization_edit", "ADMIN", request, response);
		return mav;
	}
	@RequestMapping("/organization/add")
	public void add(Organization organization,String code, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			Organization temp = organizationService.getOrganizationByCode(code);
			if(temp!=null&&organization.getId()!=temp.getId()){
				//表示组织机构代码重复
				rv.setCode("flag");
				rv.setMessage("机构代码不能不重复!");
			}else{
				if(organization.getParentOrgan().getId() == 0){
					organization.setParentOrgan(null);
					organization.setCompanycode(organization.getDeptypecode());
				}else{
					organization.setParentOrgan(organizationService.getorganizationById(organization.getParentOrgan().getId()));
				}
				organization.setAddTime(new Date());
				organization.setLevel(dictService.getDictByid(organization.getLevel().getId()));
				if("JGJB-ZY".equals(organization.getLevel().getDictCode())){
					//总院
					String ompanycode = organization.getDeptypecode();
					organization.setCompanycode(ompanycode);
				}else if("JGJB-Y".equals(organization.getLevel().getDictCode())){
					String deptypecode = organization.getParentOrgan().getDeptypecode()+organization.getDeptypecode();
					organization.setCompanycode(deptypecode);
					organization.setDeptypecode(deptypecode);
				}else{
					String companycode = organization.getParentOrgan().getCompanycode();
					organization.setCompanycode(companycode);
					organization.setDeptypecode(code);
				}
				organizationService.saveOrganization(organization);
				rv.setCode("success");
				rv.setMessage("组织架构新增成功！");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage("组织架构新增失败！");
		} finally {
			out.print(rv.toJsonString());
			out.flush();
			out.close();
		}

	}

	@RequestMapping("/organization/update")
	public void update(Organization organization,String code, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			Organization temp = organizationService.getOrganizationByCode(code);
			if(temp!=null&&organization.getId()!=temp.getId()){
				//表示组织机构代码重复
				rv.setCode("flag");
				rv.setMessage("机构代码不能不重复!");
			}else{
				if(organization.getParentOrgan().getId() == 0){
					organization.setParentOrgan(null);
					organization.setCompanycode(organization.getDeptypecode());
				}else{
					organization.setParentOrgan(organizationService.getorganizationById(organization.getParentOrgan().getId()));
				}
				organization.setAddTime(new Date());
				organization.setLevel(dictService.getDictByid(organization.getLevel().getId()));
				if("JGJB-ZY".equals(organization.getLevel().getDictCode())){
					//总院
					String companycode = organization.getDeptypecode();
					organization.setCompanycode(companycode);
				}else if("JGJB-Y".equals(organization.getLevel().getDictCode())){
					String deptypecode = organization.getParentOrgan().getDeptypecode()+organization.getDeptypecode();
					organization.setCompanycode(deptypecode);
					organization.setDeptypecode(deptypecode);
				}else{
					String companycode = organization.getParentOrgan().getCompanycode();
					organization.setCompanycode(companycode);
					organization.setDeptypecode(code);
				}
				organizationService.updatemarger(organization);
				rv.setCode("success");
				rv.setMessage("组织架构修改成功！");
			}
		
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage("组织架构修改失败！");
		} finally {
			out.print(rv.toJsonString());
			out.flush();
			out.close();
		}
	}

	@RequestMapping("/organization/organizationSelect")
	public ModelAndView organizationSelect(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/organization/organizationSelect", "ADMIN", request, response);
		return mav;
	}

	private JsonConfig getConfig() {
		JsonConfig config = new JsonConfig();
		config.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (name.equals("parentOrgan")) {
					return true;
				}
				return false;
			}
		});
		return config;
	}

	@RequestMapping("/organization/deleteOrganization")
	public void deleteOrganization(String ids,PrintWriter out){
		ReturnValue rv = new ReturnValue();
		try {
			organizationService.deleteEntityState(ids, new Organization());
			rv.setCode("success");
			rv.setMessage("删除组织架构成功!");
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("failure");
			rv.setMessage(e.getMessage());
		}finally{
			out.print(rv.toJsonString());
			out.flush();
			out.close();
		}
	}
	//加载组织架构级别
	@RequestMapping("/organization/getDictByLevel")
	public void getDictByLevel(String code,PrintWriter out){
		ReturnValue rv = new ReturnValue();
		try {
			Dict dict = dictService.getDictBycode(code);
			List<Dict> dictList = null;
			if(dict!=null){
				dictList = new ArrayList<Dict>();
				String values[]  =  StringUtils.split(dict.getDictValue(),",");
				for (String value:values) {
					dictList.add(dictService.getDictBycode(value));
				}
			}
			rv.setCode("success");
			rv.setMessage("加载成功!");
			rv.setData(dictList);
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("failure");
			rv.setMessage(e.getMessage());
		}finally{
			out.print(rv.toJsonString());
			out.flush();
			out.close();
		}
		
	}
	@RequestMapping("/organization/getChildTreeNotBM")
	public void getChildTreeNotBM(Long pid ,boolean isseif,PrintWriter out){
		ReturnValue rv = new ReturnValue();
		try {
			rv.setCode("success");
			rv.setMessage("加载成功！");
			rv.setData(organizationService.getChildTreeNotBM(pid,isseif));
		} catch (Exception e) {
			rv.setCode("failure");
			rv.setMessage(e.getMessage());
		} finally {
			out.print(rv.toJsonString(getConfig()));
			out.flush();
			out.close();
		}
	}
	@RequestMapping("/organization/getChildOrganization")
	public void getChildOrganization(Long id,PrintWriter out){
		ReturnValue rv = new ReturnValue();
		try {
			rv.setCode("success");
			if(id!=null&&id!=0l){
				String hql = "from Organization bean where bean.deleteStatus=:deleteStatus and bean.parentOrgan.id = :organpid";
				Map parms = new HashMap();
				parms.put("deleteStatus", false);
				parms.put("organpid", id);
				List<Organization>  organizations = organizationService.find(hql, parms, -1, -1);
				rv.setData(true);
				if(organizations!=null&&organizations.size()>0){
					rv.setData(false);
					rv.setMessage("该架构下有子类,暂时无法修改");
				}
			}else{
				rv.setData(true);
				rv.setMessage("该架构下有子类,暂时无法修改");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("failure");
			rv.setData(false);
			rv.setMessage(e.getMessage());
		}finally {
			out.print(rv.toJsonString());
			out.flush();
			out.close();
		}
	}
	
}
