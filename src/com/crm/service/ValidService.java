package com.crm.service;

/** 
 * @ClassName	ValidService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月30日 下午1:32:12
 * @Version 	V1.0    
 */
public interface ValidService {
	
	public void putValidCode(final String phone, final String code);
	
	public String getValidCode(final String key);
	
	public void deleteCode(final String key);
	
	public void deleteAll();

}
 