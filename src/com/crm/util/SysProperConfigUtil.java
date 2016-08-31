package com.crm.util;

import java.io.InputStream;
import java.util.Properties;

/** 
 * @ClassName	SysProperConfigUtil.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月25日 下午5:09:46
 * @Version 	V1.0    
 */
public class SysProperConfigUtil {

	private static String CFG_FILE = "configuration.properties";
	private static SysProperConfigUtil CONFIG = new SysProperConfigUtil();
	public static SysProperConfigUtil getInstance() {
		return CONFIG;
	}
	private Properties properties = null;
	private SysProperConfigUtil() {
		properties = new Properties();
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(CFG_FILE);
		try {
			properties.load(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public String getProperty(String key) {
		return properties.getProperty(key);
	}
	/*
	 * @return 全部属性
	 */
	public Properties getPropertys() {
		return properties;
	}
	
}
 