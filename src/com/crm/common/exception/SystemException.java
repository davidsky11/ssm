package com.crm.common.exception;

/**
 * SystemException 系统异常
 */
public class SystemException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SystemException() {
		super();
	}

	public SystemException(final String code) {
		super(code);
	}

	public SystemException(final Throwable cause) {
		super(cause);
	}

	public SystemException(final String code, final Throwable cause) {
		super(code, cause);
	}

	public String getMessage() {
		return ExceptionCode.getMsgByCode(super.getMessage());
	}
}
