package com.anxuan.power.controller;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.anxuan.beadhouse.util.CommUtil;
import com.anxuan.beadhouse.util.EasyuiPage;
import com.anxuan.beadhouse.util.ReturnValue;
import com.anxuan.beadhouse.view.JModelAndView;
import com.anxuan.power.bean.Resource;
import com.anxuan.power.bean.Roles;
import com.anxuan.power.bean.User;
import com.anxuan.power.service.ResourceService;
import com.anxuan.power.service.RolesService;
import com.anxuan.power.service.UserService;
import com.anxuan.power.tool.ResourceTool;

@Controller
public class UserController {
	@javax.annotation.Resource
	private ResourceService resourceService;
	@Autowired
	private ResourceTool resourceTool;
	@javax.annotation.Resource
	private RolesService rolesService;
	@javax.annotation.Resource
	private UserService userService;

	@RequestMapping("/system/verify")
	public void verify(HttpServletRequest request, HttpServletResponse response, String name) {
		ServletOutputStream responseOutputStream = null;
		try {
			response.setContentType("image/jpeg");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0L);
			HttpSession session = request.getSession();
			int width = 73;
			int height = 32;
			BufferedImage image = new BufferedImage(width, height, 1);
			Graphics g = image.getGraphics();
			Random random = new Random();
			g.setColor(getRandColor(200, 250));
			g.fillRect(0, 0, width, height);
			g.setFont(new Font("Times New Roman", 0, 24));
			g.setColor(getRandColor(160, 200));
			for (int i = 0; i < 155; i++) {
				int x = random.nextInt(width);
				int y = random.nextInt(height);
				int xl = random.nextInt(12);
				int yl = random.nextInt(12);
				g.drawLine(x, y, x + xl, y + yl);
			}

			String sRand = "";
			for (int i = 0; i < 4; i++) {
				String rand = CommUtil.randomInt(1).toUpperCase();
				sRand = sRand + rand;
				g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
				g.drawString(rand, 13 * i + 6, 24);
			}
			if (CommUtil.null2String(name).equals(""))
				session.setAttribute("verify_code", sRand);
			else {
				session.setAttribute(name, sRand);
			}
			g.dispose();
			responseOutputStream = response.getOutputStream();
			ImageIO.write(image, "JPEG", responseOutputStream);
			responseOutputStream.flush();
			responseOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255)
			fc = 255;
		if (bc > 255)
			bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	@RequestMapping("/system/index")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
	       try {
	    	   JModelAndView mav = new JModelAndView("/index", "ADMIN", request, response);
	    	    User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
				Map parms = new HashMap();
				parms.put("deleteStatus", false);
				parms.put("userid", user.getId());
				List<Roles> roleList = rolesService.find("select r from User u join u.reRoles r where u.id =:userid and r.deleteStatus=:deleteStatus", parms, -1,-1);
				List<Long> ids = new ArrayList<Long>();
				for (Roles roles : roleList) {
					ids.add(roles.getId());
				}
				List<Resource> resourceList = rolesService.findResourceByRolesIds(ids);
				mav.addObject("resourceTool", resourceTool);
				mav.addObject("resourceList", resourceList);
				return mav;
			} catch (Exception e) {
				ModelAndView mav = new ModelAndView("redirect:/system/login.htm");
				return mav;
			}
	}

	@RequestMapping("/system/user/index")
	public ModelAndView userIndex(HttpServletRequest request, HttpServletResponse response) {
		JModelAndView mav = new JModelAndView("/user/user_index", "ADMIN", request, response);
		return mav;
	}

	// 查询所有用户信息
	@RequestMapping("/system/user/findUserJson")
  	public void findUserJson(PrintWriter out,User user,EasyuiPage easyuiPage){
  		ReturnValue ret = new ReturnValue();
  		try {
			ret.setCode("success");
			ret.setMessage("加载成功！");
			String hql = "from User bean where bean.deleteStatus=:deleteStatus";
			Map params = new HashMap();
			params.put("deleteStatus", false);
			if(user.getNumber()!=null&&!"".equals(user.getNumber())){
				hql += " and bean.number like :number";
				params.put("number", "%"+user.getNumber()+"%");
			}if(user.getUname()!=null&&!"".equals(user.getUname())){
				hql += " and bean.uname like :uname";
				params.put("uname", "%"+user.getUname()+"%");
			}
			easyuiPage = userService.showListPage(hql, easyuiPage, params);
			ret.setData(easyuiPage);
		} catch (Exception e) {
		   ret.setCode("failure");
		   e.printStackTrace();
		   ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
  	}
	//加载新增页面
	@RequestMapping("/system/user/userEdit")
	public ModelAndView userEdit(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav  = new JModelAndView("/user/user_edit", "ADMIN", request, response);
		return mav;
	}
	@RequestMapping("/system/user/addUser")
	public void addUser(User user,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			ret.setCode("success");
			ret.setMessage("用户新增成功!");
			user.setAddTime(new Date());
			userService.saveUser(user);
		} catch (Exception e) {
			ret.setCode("failure");
			ret.setMessage(e.getMessage());
		}finally {
			out.print(ret.toJsonString());
			out.flush();
			out.close();
		}
	}
	//修改
	@RequestMapping("/system/user/userUpdate")
	public void userUpdate(User user, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			User tempUser = userService.getUserById(user.getId());
			tempUser.setUname(user.getUname());
			tempUser.setName(user.getName());
			tempUser.setMark(user.getMark());
			userService.updatemarger(tempUser);
			rv.setCode("success");
			rv.setMessage("修改用户成功!");
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		} finally {
			out.print(rv.toJsonString());
			out.flush();
			out.close();
		}
	}
    //删除
	@RequestMapping("/system/user/deleteUser")
	public void deleteUser(String ids, PrintWriter out) {
		ReturnValue rv = new ReturnValue();
		try {
			userService.deleteEntityState(ids, new User());
			rv.setCode("success");
			rv.setMessage("用户删除成功!");
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		} finally {
			out.print(rv.toJsonString());
			out.flush();
			out.close();
		}
	}
	//加载修改信息
	@RequestMapping("/system/user/userModify")
	public ModelAndView userModify(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav  = new JModelAndView("/user/user_edit", "ADMIN", request, response);
		return mav;
	}
	//session失效
	@RequestMapping("/system/access_denied")
	public ModelAndView access_denied(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/access_denied", "ADMIN", request, response);
		return mav;
	}
	//加载修改密码页面
	@RequestMapping("/system/user/userPassword")
	public ModelAndView userPassword(HttpServletRequest request,HttpServletResponse response){
		JModelAndView mav = new JModelAndView("/user/user_password", "ADMIN", request, response);
		return mav;
	}
	//修改密码
	   @RequestMapping("/system/user/updateUserPassword")
	   public void updateUserPassword(User user,PrintWriter out){
		   ReturnValue rv = new ReturnValue();
		   try {
			     User tempUser = userService.getUserById(user.getId());
			     tempUser.setPassword(user.getPassword());
			     userService.updatemarger(tempUser);
			     rv.setCode("success");
			     rv.setMessage("密码修改成功!");
			} catch (Exception e) {
				e.printStackTrace();
				rv.setCode("failure");
				rv.setMessage(e.getMessage());
			}finally {
				out.print(rv.toJsonString());
				out.flush();
				out.close();
			}
	   }
	@RequestMapping("/system/user/bindRoles")
	public ModelAndView bindRoles(HttpServletRequest request,HttpServletResponse response,long userid){
		JModelAndView mav = new JModelAndView("/user/bind_roles", "ADMIN", request, response);
		List<Long> rolesids = new ArrayList<Long>();
		User user = userService.getUserById(userid);
		List<Roles> bindRoles = user.getReRoles();
		mav.addObject("bindRoles", bindRoles);
		for(Roles roles:bindRoles){
			rolesids.add(roles.getId());
		}
		List<Roles> nbindRoles = rolesService.getNbindRoles(rolesids);
		mav.addObject("nbindRoles", nbindRoles);
		return mav;
	}
	@RequestMapping("/system/user/bindingRoles")
	public void bindingRoles(Long userid,String rolesids,PrintWriter out){
		ReturnValue ret = new ReturnValue();
		try {
			userService.updatebindRoles(userid,rolesids);
			ret.setCode("success");
			ret.setMessage("绑定权限成功!");
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
	@RequestMapping("/system/user/getUserPage")
	public void getUserPage(EasyuiPage easyuiPage, User user, PrintWriter out, Long organId) {
		ReturnValue rv = new ReturnValue();
		try {
			String hql = "from User bean where bean.deleteStatus=:deleteStatus and bean.organization.id =:organid";
			Map parms = new HashMap();
			parms.put("deleteStatus", false);
			parms.put("organid", organId);
			if (user.getName() != null && !"".equals(user.getName())) {
				hql += " and bean.name like:name";
				parms.put("name", "%" + user.getName() + "%");
			} else if (user.getUname() != null && !"".equals(user.getUname())) {
				hql += " and bean.uname like:uname";
				parms.put("uname", "%" + user.getUname() + "%");
			}
			easyuiPage = userService.showListPage(hql, easyuiPage, parms);
			rv.setCode("success");
			rv.setData(easyuiPage);
		} catch (Exception e) {
			e.printStackTrace();
			rv.setCode("fail");
			rv.setMessage(e.getMessage());
		} finally {
			out.println(rv.toJsonString());
			out.flush();
			out.close();
		}
	}
}
