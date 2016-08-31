package com.crm.common.exception;

import org.apache.shiro.authc.CredentialsException;

/**
 * 验证码错误异常
 */
public class CaptchaIncorrectException extends CredentialsException {
	private static final long serialVersionUID = 1L;
	
	public CaptchaIncorrectException() {
		// TODO Auto-generated constructor stub
	}
	
	public CaptchaIncorrectException(String msg) {
		
	}
	
}
