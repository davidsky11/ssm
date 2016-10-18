package com.crm.util.recharge; 

/** 
 * @ClassName	ResultBean.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年10月18日 上午11:39:31
 * @Version 	V1.0    
 */
public class ResultBean {
	private String error_code;
	private String reason;
	
	public Result result;

	public String getError_code() {
		return error_code;
	}

	public String getReason() {
		return reason;
	}

	public Result getResult() {
		return result;
	}

	public void setError_code(String error_code) {
		this.error_code = error_code;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setResult(Result result) {
		this.result = result;
	}
	
}