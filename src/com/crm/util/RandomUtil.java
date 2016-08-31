package com.crm.util;

import java.util.Random;

import org.eclipse.jdt.internal.compiler.batch.Main;

/** 
 * @ClassName	RandomUtil.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月25日 下午9:28:04
 * @Version 	V1.0    
 */
public class RandomUtil {

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	} 
	
	public static String getRandomNumber(int length) { // length表示生成字符串的长度
		String base = "0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	} 
	
	public static void main(String[] args) {
		System.out.println(getRandomNumber(4));
	}
}
 