package com.crm.common.exception;

import org.apache.shiro.authc.AccountException;

/**
 * 账户异常，需要验证码
 */
public class CaptchaRequiredException extends AccountException {
	private static final long serialVersionUID = 1L;
	
	public CaptchaRequiredException() {}
	
	public CaptchaRequiredException(final String msg) {
		super();
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}

	@Override
	public synchronized Throwable getCause() {
		return super.getCause();
	}
	
}
