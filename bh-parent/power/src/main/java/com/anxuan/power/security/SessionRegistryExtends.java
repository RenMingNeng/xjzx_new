/* Copyright 2004, 2005, 2006 Acegi Technology Pty Limited
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.anxuan.power.security;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.util.Assert;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/**
 * Default implementation of
 * {@link org.springframework.security.core.session.SessionRegistry
 * SessionRegistry} which listens for
 * {@link org.springframework.security.core.session.SessionDestroyedEvent
 * SessionDestroyedEvent}s published in the Spring application context.
 * <p>
 * For this class to function correctly in a web application, it is important
 * that you register an
 * {@link org.springframework.security.web.session.HttpSessionEventPublisher
 * HttpSessionEventPublisher} in the <tt>web.xml</tt> file so that this class is
 * notified of sessions that expire.
 * 
 */
/**
* @Description : 描述
* @author liuhengfu
* @email 479181871@qq.com
* @date Aug 18, 2015 11:21:42 PM
* ApplicationListener<SessionDestroyedEvent>
*/
public class SessionRegistryExtends implements SessionRegistry {
	// ~ Instance fields
	// ================================================================================================
	protected final Log logger = LogFactory
			.getLog(SessionRegistryExtends.class);
	/** <principal:Object,SessionIdSet> */
	/*
	 * private final ConcurrentMap<Object, Set<String>> principals = new
	 * ConcurrentHashMap<Object, Set<String>>();
	 */
	/** <sessionId:Object,SessionInformation> */

	private Cache cache2;
	private Cache cache3;

	public void setCache2(Cache cache2) {
		this.cache2 = cache2;
	}

	public void setCache3(Cache cache3) {
		this.cache3 = cache3;
	}

	// ~ Methods
	// ========================================================================================================

	@SuppressWarnings("unchecked")
	public List<Object> getAllPrincipals() {
		return new ArrayList<Object>((List<Object>) cache3.getKeys());
		/* return new ArrayList<Object>(principals.keySet()); */
	}

	@SuppressWarnings("unchecked")
	public List<SessionInformation> getAllSessions(Object principal,
			boolean includeExpiredSessions) {

		final Set<String> sessionsUsedByPrincipal = (Set<String>) (cache3
				.get(principal) == null ? null : cache3.get(principal)
				.getValue());
		/*
		 * final Set<String> sessionsUsedByPrincipal =
		 * principals.get(principal);
		 */

		if (sessionsUsedByPrincipal == null) {
			return Collections.emptyList();
		}

		List<SessionInformation> list = new ArrayList<SessionInformation>(
				sessionsUsedByPrincipal.size());

		for (String sessionId : sessionsUsedByPrincipal) {
			SessionInformation sessionInformation = getSessionInformation(sessionId);

			if (sessionInformation == null) {
				continue;
			}

			if (includeExpiredSessions || !sessionInformation.isExpired()) {
				list.add(sessionInformation);
			}
		}

		return list;
	}

	public SessionInformation getSessionInformation(String sessionId) {
		Assert.hasText(sessionId,
				"SessionId required as per interface contract");

		Element element = cache2.get(sessionId);
		if (element == null) {
			System.out.println("session nul in cache");
			return null;
		}
		System.out.println("--------sessionInformation------"
				+ element.getValue());

		return (SessionInformation) element.getValue();
	}
	public void onApplicationEvent(SessionDestroyedEvent event) {
		String sessionId = event.getId();
		removeSessionInformation(sessionId);
	}

	public void refreshLastRequest(String sessionId) {
		Assert.hasText(sessionId,
				"SessionId required as per interface contract");

		SessionInformation info = getSessionInformation(sessionId);

		if (info != null) {
			info.refreshLastRequest();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void registerNewSession(String sessionId, Object principal) {
		Assert.hasText(sessionId,
				"SessionId required as per interface contract");
		Assert.notNull(principal,
				"Principal required as per interface contract");

		if (logger.isDebugEnabled()) {
			logger.debug("Registering session " + sessionId
					+ ", for principal " + principal);
		}

		if (getSessionInformation(sessionId) != null) {
			removeSessionInformation(sessionId);
		}

		String cacheKey = sessionId;
		SessionInformation sessionInformation = new SessionInformation(
				principal, sessionId, new Date());
		Element element = new Element(cacheKey, sessionInformation);
		cache2.put(element);
		System.out.println("----" + sessionId
				+ "----------------------session set into the cache");

		Set<String> sessionsUsedByPrincipal = (Set<String>) (cache3
				.get(principal) == null ? null : cache3.get(principal)
				.getValue());

		/* Set<String> sessionsUsedByPrincipal = principals.get(principal); */

		if (sessionsUsedByPrincipal == null) {
			sessionsUsedByPrincipal = new CopyOnWriteArraySet<String>();

			Set<String> prevSessionsUsedByPrincipal = null;

			Object cachePrincipal = principal;
			List list = cache3.getKeys();
			if (!list.contains(cachePrincipal)) {
				Element element2 = new Element(cachePrincipal,
						sessionsUsedByPrincipal);
				cache3.put(element2);
			} else {
				prevSessionsUsedByPrincipal = (Set<String>) cache3.get(
						cachePrincipal).getValue();
			}

			/*
			 * if (!principals.containsKey(principal)) {
			 * principals.put(principal, sessionsUsedByPrincipal); }else {
			 * prevSessionsUsedByPrincipal = principals.get(principal); }
			 */

			/*
			 * Set<String> prevSessionsUsedByPrincipal = principals.putIfAbsent(
			 * principal, sessionsUsedByPrincipal);
			 */

			if (prevSessionsUsedByPrincipal != null) {
				sessionsUsedByPrincipal = prevSessionsUsedByPrincipal;
			}
		}

		sessionsUsedByPrincipal.add(sessionId);

		if (logger.isTraceEnabled()) {
			logger.trace("Sessions used by '" + principal + "' : "
					+ sessionsUsedByPrincipal);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void removeSessionInformation(String sessionId) {
		Assert.hasText(sessionId,
				"SessionId required as per interface contract");

		SessionInformation info = getSessionInformation(sessionId);

		if (info == null) {
			return;
		}

		if (logger.isTraceEnabled()) {
			logger.debug("Removing session " + sessionId
					+ " from set of registered sessions");
		}

		String cacheKey = sessionId;
		List keys = cache2.getKeys();
		for (int i = 0; i < keys.size(); i++) {
			String key = String.valueOf(keys.get(i));
			if (key.equals(cacheKey)) {
				cache2.remove(key);
			}
		}
		System.out.println("----" + sessionId
				+ "----------------------session remove from cache");

		Set<String> sessionsUsedByPrincipal = (Set<String>) (cache3.get(info
				.getPrincipal()) == null ? null : cache3.get(
				info.getPrincipal()).getValue());
		/*
		 * Set<String> sessionsUsedByPrincipal = principals.get(info
		 * .getPrincipal());
		 */

		if (sessionsUsedByPrincipal == null) {
			return;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("Removing session " + sessionId
					+ " from principal's set of registered sessions");
		}

		sessionsUsedByPrincipal.remove(sessionId);

		if (sessionsUsedByPrincipal.isEmpty()) {
			// No need to keep object in principals Map anymore
			if (logger.isDebugEnabled()) {
				logger.debug("Removing principal " + info.getPrincipal()
						+ " from registry");
			}

			cache3.remove(info.getPrincipal());
			/* principals.remove(info.getPrincipal()); */
		}

		if (logger.isTraceEnabled()) {
			logger.trace("Sessions used by '" + info.getPrincipal() + "' : "
					+ sessionsUsedByPrincipal);
		}
	}



}
