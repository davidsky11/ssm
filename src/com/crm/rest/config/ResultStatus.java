package com.crm.rest.config; 

/** 
 * @ClassName	ResultStatus.java
 * @Description 自定义请求状态码
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午2:34:11
 * @Version 	V1.0    
 */
public enum ResultStatus {

	SUCCESS(100, "成功"),
    USERNAME_OR_PASSWORD_ERROR(-1001, "用户名或密码错误"),
    USER_NOT_FOUND(-1002, "用户不存在"),
    USER_NOT_LOGIN(-1003, "用户未登录");
	
	/**
	 * 返回码
	 */
	private int code;
	
	/**
	 * 返回结果描述
	 */
	private String message;
	
	ResultStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
 