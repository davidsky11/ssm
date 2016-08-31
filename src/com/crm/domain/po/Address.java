package com.crm.domain.po; 

/** 
 * @ClassName	Address.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月25日 下午11:21:33
 * @Version 	V1.0    
 */
public class Address {

	private String status;
	private Result result;
	
	public String getStatus() {
		return status;
	}
	public Result getResult() {
		return result;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "Address [status=" + status + ", result=" + result + "]";
	}
	
}