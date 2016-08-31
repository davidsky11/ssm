package com.crm.cache;

import java.io.IOException;
import java.util.Date;
 

/**
 * 缓存相关信息 如创建时间，占用内存，刷新次数等
 * @author Administrator
 *
 */
public class CacheInfoImpl implements CacheInfo{
    
	/**创建时间*/
 	private Long createTime;
 	/** 刷新次数*/
	private Integer refreshNumber = -1;
	/** 对应缓存 one to one*/
	private Cache cache;
	
	/**
	 * 构造函数
	 * @param cache - 缓存实例
	 */
	public CacheInfoImpl(){
		this.createTime = System.currentTimeMillis();
	}
	
	
	public long getCreateTime() {
		return createTime;
	}

	/**
	 * 缓存大小 占用内存
	 * 注意： 要求缓存中包含的对象序列化 ，不推荐频繁使用
	 * @return  if null发生异常，否则返回占用内存大小 单位byte
	 */
	public Integer getMemorySize() {
		try {
			return ArrayAndCollectionUtil.size(cache);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 缓存大小，包含对象个数
	 */
	public int getSize() {
		return cache.getCacheSize();
	}

	 


	public int getRefreshNumber() {
		return refreshNumber;
	}


	public void setRefreshNumber(int refreshNumber) {
		this.refreshNumber = refreshNumber;
	}


	public Cache getCache() {
		return cache;
	}


	public void setCache(Cache cache) {
		this.cache = cache;
	}
	
	public String toString(){
		return "缓存< "+getCacheId()+" >信息. 创建时间: "+new Date(this.getCreateTime()).toLocaleString()+"大小(记录数):"+this.getSize()+"大小(内存占用byte):由于该方法比较耗费资源，请参照public Integer getMemorySize()"+"刷新次数:"+this.getRefreshNumber();
	}


	public String getCacheId() {
		return cache.getCacheID();
	}

}
