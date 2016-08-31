package com.crm.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

//import com.comm.common.cache.distribute.SynchroPollerThreadAllCache;

/**
 *  缓存工厂
 *     缓存访问的入口
 *     包含了缓存同步策略的配置
 *     当没有配置时: 默认将支持分布式，轮询周期为1000ms
 * @author comm
 *
 */

public class CacheFactory {


    /**  存放缓存实例  */
    private Map _cacheMap;
    /** 缓存配置*/
    CacheConfig CacheConfig = new CacheConfig();

	/**支持分布式的轮旬线程 */
    private static Thread thread = null;;
    

	/**
	 * @return the cacheConfig
	 */
	public CacheConfig getCacheConfig() {
		return CacheConfig;
	}

	/**
	 * @param cacheConfig the cacheConfig to set
	 */
	public void setCacheConfig(CacheConfig cacheConfig) {
		CacheConfig = cacheConfig;
	}


	/**
     * Singleton
     */
    private static final CacheFactory _cacheFactory = new CacheFactory();

// -----------------------------------------------------------------------------  

    /**
     *
     */
    private CacheFactory() {
        _cacheMap = new HashMap();
      //  thread =  new Thread(new SynchroPollerThreadAllCache());
        //thread.start();  xj  2011-5-17
    }

// ----------------------------------------------------------------------------- Public 

    /**
     * CacheFactory
     * @param Cache_SupportDistribute 是否支持分布式 true支持，false不支持
     */
    public static CacheFactory getInstance(){
        return _cacheFactory;
    }

    /**
     * 
     *
	 * 
	 * 
     * 缓存参数设置，提供该方法是为了便于业务系统的配置与缓存配置集成
     * @param in  java.util.Properties  提供属性 说明
     *       1    Cache_SupportDistribute=true
			 #is support distributed deploy    if <value>true<value> support ,else <value>false<value> not support ,the default value is true
			 
			 2    Cache_SynCycle=1000  
			 cache synchonized cycle that unit is millisecond please attention if <code>Cache_SupportDistribute<code>is false then this parameter will not means  
			 if this config not exist or wrong the value is 1000ms
			
     * @throws CacheException
     */
    public void setConfig(Properties properties) throws CacheException {
    	boolean isSupportDistribute = "false".equalsIgnoreCase(properties.getProperty(CacheCanstants.CACHE_SUPPORTDISTRIBUTE))?false:true;
    	this.getCacheConfig().setSupportDistribute(isSupportDistribute);
    	try{
    		this.getCacheConfig().setPollerCycle(Long.parseLong(properties.getProperty(CacheCanstants.CACHE_SYNCYCLE)));
    	}catch(Exception e){
    		// use default 1000
    	}
    	dealWithDistribute();
    }
    
    /**
     * 缓存参数配置
     * @param cache_SupportDistribute true支持分布式 false不支持
     * @param cache_SynCycle 同步周期单位ms
     * @throws CacheException
     */
    public void setConfig(boolean cache_SupportDistribute, long cache_SynCycle)  {
    	    this.getCacheConfig().setSupportDistribute( cache_SupportDistribute);
    		this.getCacheConfig().setPollerCycle(cache_SynCycle);
    		dealWithDistribute();
    	 
    }
    
    private  void dealWithDistribute(){
    	if(this.getCacheConfig().getIsSupportDistribute()){
    		//thread.resume();
    	}else{
    		//thread.suspend();
    	}
    }

    /**
     * 
     * @param cache 
     * @throws NullPointerException  if cache==null   cache.getCacheConfig()==null
     *   cache.getCacheConfig().getCacheId()==null
     * @throws CacheException 
     * ManagedCache
     */
    public void addCache(Cache cache) throws CacheException {
        if(cache==null){
            throw new NullPointerException("缓存为null");
        }
        /*if(cacheConfig==null) {
            throw new NullPointerException("cache config is null");
        }*/
        if(cache.getCacheID()==null) {
            throw new NullPointerException("cache.getCacheID() is null");
        }

        synchronized(_cacheMap){
            if(_cacheMap.containsKey(cache.getCacheID())) {
                throw new CacheException("Cache id:"+cache.getCacheID()+" exists");
            }
            _cacheMap.put(cache.getCacheID(), cache);
        }
    }

    /**
     *  
     * @param cacheId  
     * @throws NullPointerException  if cacheId==null
     */
    public Cache getCache(Object cacheId) throws CacheException {
        if(cacheId==null) {
            throw new NullPointerException("cacheId is null");
        }

        synchronized(_cacheMap){
            return (Cache)_cacheMap.get(cacheId);
        }
    }

    /**
     *  
     * @param cacheId 缓存标识
     * @throws NullPointerException if cacheId==null
     */
    public void removeCache(String cacheId) throws CacheException {
        if(cacheId==null) {
            throw new NullPointerException("cacheId is null");
        }

        synchronized(_cacheMap){
            _cacheMap.remove(cacheId);
        }
    }

    
    /**
     * 获取缓存标识数组 
     */
    public String[] getCacheIds(){
        synchronized(_cacheMap) {
        	String[] result =  new String[_cacheMap.size()];
            _cacheMap.keySet().toArray(result);
            return result;
        }
    }
    
    public boolean hasKey(String key) {
    	String[] keys = getCacheIds();
    	for (int i = 0; i < keys.length; i++) {
			if (keys[i].equals(key)) {
				return true;
			}
		}
    	return false;
    }

  
  

}
 
