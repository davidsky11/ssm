package com.crm.cache;
/**
 * 
 * 缓存基类
 *    设置缓存是否支持分布式
 * @author comm
 *
 */
public class AbstractCache {
	
	/** 缓存标识（要求唯一）*/
	String cacheId = null;
	/** 缓存是否支持分布式*/
	Boolean isSupportDistribute= true;
    /** 缓存信息 */
    CacheInfoImpl cacheInfo = new CacheInfoImpl();
	
	/**
	 * 
	 * 
	 * @param isSupportDistribute - 是否支持分布式 true 支持 false不支持
	 */
	/*public AbstractCache(Boolean isSupportDistribute,Cache cache) {
		super();
		this.isSupportDistribute = isSupportDistribute;
		cacheInfo = new CacheInfoImpl(cache);
	}*/

	/**
	 * 
	 * @return -  true 支持分布式
	 */
	public Boolean getIsSupportDistribute() {
		return isSupportDistribute;
	}
    
	/**
	 * 
	 * @param isSupportDistribute if true支持分布式
	 */
	public void setIsSupportDistribute(Boolean isSupportDistribute) {
		this.isSupportDistribute = isSupportDistribute;
	}

	/**
	 * 
	 * @return 缓存信息
	 */
	public CacheInfo getCacheInfo(Cache cache) {
		this.cacheInfo.setCache(cache);
		return this.cacheInfo;
	}
    
	/**
	 * 设置缓存信息
	 * @param cacheInfo
	 */
	public void setCacheInfo(Cache cache) {
		
	}
    
	
	
	
	
	
}
