package com.crm.domain.dto;

import java.util.Date;

/** 
 * @ClassName	Valid.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月30日 下午1:12:01
 * @Version 	V1.0    
 */
public class Valid {
	
	private String key;
	private String value;
	private Date createTime;
	
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@Override
	public String toString() {
		return "Valid [key=" + key + ", value=" + value + ", createTime=" + createTime + "]";
	}
	
}
 