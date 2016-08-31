package com.crm.util;

import java.util.UUID;

/** 
 * @ClassName	Tool.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午2:46:45
 * @Version 	V1.0    
 */
public class Tool {

	/**
	 * 得到一个唯一标识的UUID
	 * 
	 * @return
	 * @author kevin.xia
	 */
	public static String getUUID() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString();
	}
	
	
	public static String generateMajorKey() {
		return getUUID().trim().replace("-", "");
	}
	
}
 