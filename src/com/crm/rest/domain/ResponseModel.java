package com.crm.rest.domain; 

/** 
 * @ClassName	ResponseModel.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月16日 下午3:30:20
 * @Version 	V1.0    
 */
public class ResponseModel {
	
	private int status;
	private Object obj;
	
	public int getStatus() {
		return status;
	}
	public Object getObj() {
		return obj;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	
}
 