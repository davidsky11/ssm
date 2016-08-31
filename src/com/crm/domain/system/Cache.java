package com.crm.domain.system;

import java.sql.Timestamp;

/** 
 * @ClassName	Cache.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月25日 下午5:15:56
 * @Version 	V1.0    
 */
public class Cache {
	
	private String id;
	private String account;
	private String sessionId;
	private Timestamp lastTime;
	
	public String getId() {
		return id;
	}
	public String getAccount() {
		return account;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public Timestamp getLastTime() {
		return lastTime;
	}
	public void setLastTime(Timestamp lastTime) {
		this.lastTime = lastTime;
	}
	
}
 