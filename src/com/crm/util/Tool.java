package com.crm.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
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
	
	public static String nvl(String nvlString) {
		return nvlString == null ? "" : nvlString;
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
	
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}
	
	public static String doneQueryParam(Map<String, String> paramMap) {
		StringBuffer str = new StringBuffer();
		Set<String> keySet = paramMap.keySet();
		for (String key : keySet) {
			if (Tool.isNotNullOrEmpty(key.trim())) {
				str.append("&").append(key).append("=").append(paramMap.get(key).trim());
			}
		}
		
		return str.toString();
	}
	
	public static void main(String[] args) {
		String[] str = new String[]{"123", "234"};
		System.out.println(Tool.stringArrayToString(str, true, ","));
	}
}
 