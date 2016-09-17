package com.crm.domain; 

/** 
 * @ClassName	Role.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月1日 下午2:16:06
 * @Version 	V1.0    
 */
public class Role {
	
	private String id;
	private String name;
	private String roleDesc;
	private Short enabled;
	public String getId() {
		return id;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public Short getEnabled() {
		return enabled;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Role [id=" + id + ", name=" + name + ", roleDesc=" + roleDesc + ", enabled=" + enabled + "]";
	}
	
}
 