package com.anxuan.beadhouse.interceptor.cache;

import java.lang.reflect.Method;
import java.util.List;

import net.sf.ehcache.Cache;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

public class MethodCacheAfterAdvice implements AfterReturningAdvice,
		InitializingBean {

	private static final Log logger = LogFactory.getLog(MethodCacheAfterAdvice.class);

	private Cache cache;

	public void setCache(Cache cache) {
		this.cache = cache;
	}

	/**
	 * @Description: 清除缓存（在目标方法执行之后，执行该方法）
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public void afterReturning(Object returnValue, Method method,
			Object[] args, Object target) throws Throwable {
		
		String className = target.getClass().getName();
		List<String> cacheKeys = cache.getKeys();
		logger.info("[清除缓存：]" + cacheKeys);
		for (String cacheKey : cacheKeys) {
			if (cacheKey.startsWith(className)) {
				cache.remove(cacheKey);
			}
		}
	}

	public void afterPropertiesSet() throws Exception {
		Assert.notNull(cache,"Need a cache. Please use setCache(Cache) create it.");
	}

}
