package com.crm.cache;
 
/**
 * 缓存相关信息
 * @author comm
 *
 */
public interface CacheInfo {
 
    
   /** 缓存标识 */
   public String getCacheId();
	
   /**
    * 	
    * @return 缓存中对象个数
    */
   public int getSize();
	
	
    /**
     * 获得内存大小单位byte
     */
    public Integer getMemorySize();
    
    /**
     * 创建时间
     */
    public long getCreateTime();
    
    /**
     * 获取刷新次数
     */
    public int getRefreshNumber();
    /**
     * 设置缓存刷新次数
     * @param number - 刷新次数
     * @return
     */
    public void setRefreshNumber(int number);
    
    /**
     * 
     * @return 缓存信息
     */
    public String toString();

 
}

 
