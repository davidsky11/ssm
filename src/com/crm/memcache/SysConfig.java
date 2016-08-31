package com.crm.memcache; 

/** 
 * @ClassName	SysConfig.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月31日 下午2:07:37
 * @Version 	V1.0    
 */
public class SysConfig {
	
	public static int CACHE_TIMEOUT = 3000;
	public static String TEST_KEY = "test";
	
	private String key;
	private String value;
	
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
 