package com.crm.common.exception;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.crm.common.util.lang.StringUtil;

/**
 * ExceptionConstants
 */
public class ExceptionCode {
	// 网络
	public static final String CODE_0000 = "0000";
	public static final String MSG_0000 = "网络异常！!";
	// 数据库操作
	public static final String CODE_1000 = "1000";
	public static final String MSG_1000 = "数据操作异常!";
	public static final String CODE_1001 = "1001";
	public static final String MSG_1001 = "字段已存在!";
	// 文件操作
	public static final String CODE_2000 = "2000";
	public static final String MSG_2000 = "文件操作异常!";
	// 其它错误
	public static final String CODE_9999 = "9999";
	public static final String MSG_9999 = "其它错误!";

	private static Map<String, String> returnCodeMap = new ConcurrentHashMap<String, String>();

	static {
		// 网络
		returnCodeMap.put(CODE_0000, MSG_0000);
		// 数据库操作
		returnCodeMap.put(CODE_1000, MSG_1000);
		returnCodeMap.put(CODE_1001, MSG_1001);
		// 文件操作
		returnCodeMap.put(CODE_2000, MSG_2000);
		// 其它错误
		returnCodeMap.put(CODE_9999, MSG_9999);
	}

	/**
	 * getMsgByCode
	 */
	public static String getMsgByCode(final String code) {
		if (StringUtil.isNullOrEmpty(code)) {
			return MSG_0000;
		} else {
			String msg = returnCodeMap.get(code);
			if (msg == null) {
				msg = code;
			}
			return msg;
		}
	}
}
