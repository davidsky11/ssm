package com.crm.domain; 

/** 
 * @ClassName	UserRole.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月1日 下午11:27:24
 * @Version 	V1.0    
 */
public class UserRole {
	
	private String id;
	private String userId;
	private String roleId;
	
	public String getId() {
		return id;
	}
	public String getUserId() {
		return userId;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
}
 