package com.anxuan.power.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.anxuan.power.bean.User;
import com.anxuan.power.dao.UserDao;
import com.anxuan.power.service.UserService;
import com.anxuan.power.util.LoginMesage;



/**
* @Description : 描述
* @author liuhengfu
*@email 364105996@qq.com
* @date Aug 6, 2013 8:57:45 PM
*/
public class MyUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {
	private LoginMesage loginMesage = new LoginMesage();
	private UserService userService;
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// 定义从前台接收参数的 属性名称
	private String validationParameter = "validation";

	public void setValidationParameter(String validationParameter) {
		this.validationParameter = validationParameter;
	}

	private boolean openValidation = true;

	public void setOpenValidation(boolean openValidation) {
		this.openValidation = openValidation;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		if (!request.getMethod().equals("POST")) {
			throw new AuthenticationServiceException("传参方法不对 "+ request.getMethod());
		}
		String username = obtainUsername(request).trim();
		String password = obtainPassword(request).trim();
		//用户或密码验证
        if("".equals(username)||"请输入用户名".equals(username)){
//        	loginMesage.setCode("username");
//        	loginMesage.setMessage("用户名不能为空");
//        	request.getSession().setAttribute("loginMesage", loginMesage);
//        	throw new AuthenticationServiceException("用户名不能为空！！！");
        }if("".equals(password)||"请输入密码".equals(password)){
//        	loginMesage.setCode("password");
//        	loginMesage.setMessage("密码不能为空");
//        	loginMesage.setUserName(username);
//        	request.getSession().setAttribute("loginMesage", loginMesage);
        	throw new AuthenticationServiceException("密码不能为空！！！");
        }
		// 验证码validation是否正确
		if (openValidation) {
			checkValidateCode(request);
		}
		User user = userService.getUserByName(username); 
        if(user==null){
//        	loginMesage.setCode("usercnme");
//        	loginMesage.setMessage("用户名不存在！！！");
//        	loginMesage.setUserName(username);
//        	request.getSession().setAttribute("loginMesage", loginMesage);
        	throw new AuthenticationServiceException("用户名不存在！！！");
        }if(!password.equals(user.getPassword())){
//        	loginMesage.setCode("password");
//        	loginMesage.setMessage("密码不正确！！！");
//        	loginMesage.setUserName(username);
//        	request.getSession().setAttribute("loginMesage", loginMesage);
        	throw new AuthenticationServiceException("密码不正确！！！");
        }
		// 实现 Authentication,这里装进去的password会通过spring的MD5加密,然后实现校验
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
		// 允许子类设置详细属性
		setDetails(request, authRequest);
		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(getUsernameParameter());
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(getPasswordParameter());
		return null == obj ? "" : obj.toString();
	}

	protected String obtainValidationString(HttpServletRequest request) {
		Object obj = request.getParameter(validationParameter);
		return null == obj ? "" : obj.toString();
	}

	public void checkValidateCode(HttpServletRequest request) {

		String jcaptchaCode = obtainValidationString(request).trim();//request.getSession().getAttribute("verify_code").toString();		//获取前台的验证码输入值
		if (jcaptchaCode.equals("")||jcaptchaCode.equals("请输入验证码")){
//			loginMesage.setCode("code");
//        	loginMesage.setMessage("验证不能为空！！！");
//        	loginMesage.setUserName(obtainUsername(request));
//        	request.getSession().setAttribute("loginMesage", loginMesage);
			throw new AuthenticationServiceException("验证不能为空！！！");
		}
		if(!jcaptchaCode.equals(request.getSession().getAttribute("verify_code"))){
//			loginMesage.setCode("code");
//        	loginMesage.setMessage("验证码不匹配！！！");
//        	loginMesage.setUserName(obtainUsername(request));
//        	request.getSession().setAttribute("loginMesage", loginMesage);
			throw new AuthenticationServiceException("验证码不匹配！！！");
		}
	}

}
