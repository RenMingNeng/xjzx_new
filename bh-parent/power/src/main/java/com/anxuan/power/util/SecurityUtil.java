package com.anxuan.power.util;

import java.lang.reflect.Method;

import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;

import com.anxuan.power.security.MySecurityMetadataSource;

/**
* @Description : 描述
* @author Liuhengfu
 * @email 479181871@qq.com
* @date Aug 18, 2015 11:20:06 PM
*/
public class SecurityUtil implements AfterReturningAdvice
 {
	@Override
	public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) throws Throwable {
		MySecurityMetadataSource.resourceMap = null;
	}
	

}
