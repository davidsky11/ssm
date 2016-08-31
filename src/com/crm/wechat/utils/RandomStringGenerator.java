package com.crm.wechat.utils;

import java.util.Random;

/** 
 * @ClassName	RandomStringGenerator.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:03:09
 * @Version 	V1.0    
 */
public class RandomStringGenerator {

	/**
	 * 
	 * 获取一定长度的随机字符串
	 * 
	 * @param length
	 *            指定字符串长度
	 * 
	 * @return 一定长度的字符串
	 * 
	 */

	public static String getRandomStringByLength(int length) {

		String base = "abcdefghijklmnopqrstuvwxyz0123456789";

		Random random = new Random();

		StringBuffer sb = new StringBuffer();

		for (int i = 0; i < length; i++) {

			int number = random.nextInt(base.length());

			sb.append(base.charAt(number));

		}

		return sb.toString();

	}
}
 