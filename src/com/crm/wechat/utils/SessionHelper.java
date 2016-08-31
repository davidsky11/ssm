package com.crm.wechat.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class SessionHelper {
	private static HttpServletRequest getRequest(){
		return ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
	}
	
	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static void setAttribute(String name, Object obj) {
		getSession().setAttribute(name, obj);
	}

	public static void setAttribute(String name, String value) {
		getSession().setAttribute(name, value);
	}
	
	public static Object getAttribute(String name) {
		 return getSession().getAttribute(name);
	}

	public static void removeSession(String name) {
		getSession().removeAttribute(name);
	}
}
