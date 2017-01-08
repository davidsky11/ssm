package com.crm.domain.dto; 

/** 
 * @ClassName	AddressType.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2017年1月4日 下午4:15:21
 * @Version 	V1.0    
 */
public class AddressType {

	private String key;
	private String value;
	
	public AddressType() {}
	
	public AddressType(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public String getValue() {
		return value;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
 