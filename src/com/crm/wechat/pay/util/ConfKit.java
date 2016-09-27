package com.crm.wechat.pay.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

/**
 * 
 * <dl>
 *  <dd>类/接口描述：配置文件工具
 *	<dd>	
 * <dl>
 * @author 李巍
 * @2014-12-9 下午09:20:07
 *
 */
public class ConfKit {

	private static Properties props = new Properties();

	static {
		try {
			props.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("wechat.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String get(String key) {
		return props.getProperty(key);
	}

    public static void setProps(Properties p){
        props = p;
    }
}
