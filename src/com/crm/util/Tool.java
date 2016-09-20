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
	
	/**
	 * 字符窜数组转换成以某成分割符分割的字符窜,适用SQL中in的批量条件,如: in ('a','b','c')
	 * 
	 * @param str		字符窜数组
	 * @param isString	是否字符串
	 * @param splitFlag 成分割符
	 * @return 			字符窜
	 */
	public static String stringArrayToString(String[] str, boolean isString,
			String splitFlag) {
		StringBuffer sb = new StringBuffer();
		if (str.length <= 0) {
			return "";
		}
		for (int i = 0; i < str.length; i++) {
			if (isString)
				sb.append("'");
			sb.append(str[i]);
			if (isString)
				sb.append("'");
			if (i + 1 != str.length)
				sb.append(splitFlag);
		}
		return sb.toString();
	}
	
	/**
	 * 验证字符串是否为空
	 * 
	 * @param s
	 * @return
	 */
	public static boolean isNotNullOrEmpty(String s) {
		if (null == s) {
			return false;
		}
		if (s.trim().equals("")) {
			return false;
		}
		return true;
	}
	
	public static void main(String[] args) {
		String[] str = new String[]{"123", "234"};
		System.out.println(Tool.stringArrayToString(str, true, ","));
	}
}
 