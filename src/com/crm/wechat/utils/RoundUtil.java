package com.crm.wechat.utils;

import java.util.UUID;

/**
 * @ClassName RoundUtil.java
 * @Description 随机数生成工具类
 * @Author kevin
 * @CreateTime 2016年7月15日 上午12:12:55
 * @Version V1.0
 */
public class RoundUtil {

	/**
	 * @Title: getUUID @Description: 获取UUID @param @return 设定文件 @return String
	 * 返回类型 @throws
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
		return uuid;
	}

	/**
	 * @Title: getCode @Description: 随机生成4位验证码 @param @return 设定文件 @return
	 * String 返回类型 @throws
	 */
	public static String getCode() {
		int i = (int) (Math.random() * 9000 + 1000);
		return i + "";
	}

}
 