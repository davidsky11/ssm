package com.crm.cache;
/**
 * 缓存异常
 * @author comm
 *
 */
public class CacheException extends Exception{
	public CacheException(String msg){
		super("系统数据缓存服务异常，信息如下：" +msg);
	}
}
