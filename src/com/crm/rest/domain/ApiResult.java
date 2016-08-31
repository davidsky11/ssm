package com.crm.rest.domain; 

/** 
 * @ClassName	ApiResult.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月17日 下午11:47:46
 * @Version 	V1.0    
 */
public class ApiResult<T> {

	private String operate;  // 进行的操作
	private boolean success = false;  // 是否成功
	private int code;  // 代码
	private String msg;  // 带回的消息
	private T data;  // 附带数据
	
	public ApiResult() {}
	
	public ApiResult(String field) {
		this.operate = field;
	}
	
	public String getOperate() {
		return operate;
	}
	public void setOperate(String operate) {
		this.operate = operate;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
 