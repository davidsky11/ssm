package com.crm.exception;

import org.springframework.http.HttpStatus;

/** 
 * @ClassName	CutomizedException.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午1:04:17
 * @Version 	V1.0    
 */
public class CustomizedException extends Exception {

	private static final long serialVersionUID = 3413229854872650771L;

	public CustomizedException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CustomizedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public CustomizedException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public CustomizedException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public CustomizedException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	public CustomizedException(HttpStatus status, String message) {
		super(message);
	}

}
 