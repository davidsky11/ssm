package com.crm.domain.http;

import com.crm.rest.config.ResultStatus;

/** 
 * @ClassName	ResultModel.java
 * @Description 自定义返回结果
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午2:19:05
 * @Version 	V1.0    
 */
public class ResultModel {

	/**
	 * 返回码
	 */
	private int code;
	
	/**
	 * 返回结果描述
	 */
	private String message;
	
	/**
	 * 返回内容
	 */
	private Object content;

	public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Object getContent() {
        return content;
    }

    public ResultModel(int code, String message) {
        this.code = code;
        this.message = message;
        this.content = "";
    }

    public ResultModel(int code, String message, Object content) {
        this.code = code;
        this.message = message;
        this.content = content;
    }

    public ResultModel(ResultStatus status) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = "";
    }

    public ResultModel(ResultStatus status, Object content) {
        this.code = status.getCode();
        this.message = status.getMessage();
        this.content = content;
    }

    public static ResultModel ok(Object content) {
        return new ResultModel(ResultStatus.SUCCESS, content);
    }

    public static ResultModel ok() {
        return new ResultModel(ResultStatus.SUCCESS);
    }

    public static ResultModel error(ResultStatus error) {
        return new ResultModel(error);
    }
    
}
 