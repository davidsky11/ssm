package com.crm.util.cache; 

/** 
 * @ClassName	ILocalCache.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月30日 下午12:46:01
 * @Version 	V1.0    
 */
public interface ILocalCache<K, V> {

	/** 
     * 从缓存中获取数据 
     * @param key 
     * @return value 
     */  
    public V get(K key);  
    
}
 