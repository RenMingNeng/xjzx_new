package com.anxuan.beadhouse.interceptor.cache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

//处理对象缓存
public class MethodCacheInterceptor implements MethodInterceptor,
		InitializingBean {
	private static final Log logger = LogFactory
			.getLog(MethodCacheInterceptor.class);
	private Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	/**
	 * @Description: 检验缓存是否为空
	 * @throws Exception
	 */
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache,"Need a cache. Please use setCache(Cache) create it!");
	}

	/**
	 * @Description: 拦截切入点中的方法，如果存在该结果，则返回cache中的值, 否则,则从数据库中查询返回并放入cache中
	 * @param invocation
	 * @throws Throwable
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		// 获取类名
		String targetName = invocation.getThis().getClass().getName();
		// 获取方法名
		String methodName = invocation.getMethod().getName();
		// 获取方法里的参数
		Object[] arguments = invocation.getArguments();
		Object result = null;
		String cacheKey = getCacheKey(targetName, methodName, arguments);
		Element element = null;
		synchronized (this) {
			element = cache.get(cacheKey);
			if (element == null) {
				// 执行完方法的返回值：调用proceed()方法，就会触发切入点方法执行
				result = invocation.proceed();
				System.out.println("第一次调用方法并缓存其值");
				logger.info("第一次调用方法并缓存其值:" + result);
				element = new Element(cacheKey, (Serializable) result);
				cache.put(element);
			} else {
				logger.info("在缓存获取其值:" + element.getValue());
				System.out.println("在缓存获取其值:");
				result = element.getValue();
			}
		}
		return result;
	}
	/**
	 * @Description: 返回具体的方法全路径名称参数
	 * @param targetName
	 *            类名
	 * @param methodName
	 *            方法名
	 * @param arguments
	 *            参数
	 * @return 缓存的Key值(Key为：包名.类名.方法名)
	 */
	private String getCacheKey(String targetName, String methodName,
			Object[] arguments) {
		StringBuffer sb = new StringBuffer();
		sb.append(targetName).append(".").append(methodName);
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				sb.append(".").append(arguments[i]);
			}
		}
		return sb.toString();
	}

	
}
