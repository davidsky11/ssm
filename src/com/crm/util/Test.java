package com.crm.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/** 
 * @ClassName	Test.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月25日 下午10:09:03
 * @Version 	V1.0    
 */
public class Test {

	public static final String msg = "201608252209002,0";
			//+ "3164644646";
	
	public void guavaCache() {
		LoadingCache<String, String> cache = CacheBuilder.newBuilder()
				.maximumSize(100)
				.expireAfterWrite(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, String>() {
					public String load(String phone) {
						return phone;
					}
				});
	}
	
	public static void main(String[] args) {
		System.out.println(msg);
		if ("\r\n".equals(msg) || "\n".equals(msg)) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
		
		String[] arr = msg.split("\n");
		for (String a: arr) {
			System.out.println(a);
		}
	}
}
 