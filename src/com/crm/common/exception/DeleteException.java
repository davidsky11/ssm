package com.crm.common.exception;

/**
 * DeleteException 删除异常
 */
public class DeleteException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DeleteException() {
		super();
	}

	public DeleteException(String s) {
		super(s);
	}

	public DeleteException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteException(Throwable cause) {
		super(cause);
	}
}
