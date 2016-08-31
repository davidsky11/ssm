package com.crm.cache;

import java.util.List;

/**
 *
 * 缓存接口
 * @author comm
 *
 */
public interface Cache {
	
	/**
	 * 获取缓存大小(包含对象个数)
	 * @return
	 */
	public int getCacheSize();
	
	
	/**
	 * 查询  根据对象定义和用于创建key的对象
	 * @param keydef - key定义对象
	 * @param sourceKey -通常为key对应值对象， CachedObjectKeyDef(key定义对象)将根据该对象或属性创建真正的key
	 * @return 返回要查询的对象           当keydef(指定unique true )惟一时返回值对象
	 *                            当keydef(指定unique false)不唯一时返回ArrayList<值对象>
	 * @throws CacheException
	 */
	public Object getObject(CachedObjectKeyDef keydef,Object sourceKey) throws CacheException;
	/**
	 * 查询  根据对象定义和key
	 * @param keydef - key定义对象
	 * @param key - 事实key( 等同于<code>CachedObjectKeyDef.createKey(Object object)<code>返回对象)
	 * @return 返回要查询的对象           当keydef(指定unique true )惟一时返回值对象
	 *                            当keydef(指定unique false)不唯一时返回ArrayList<值对象>
	 * @throws CacheException
	 */
	public Object getObjectByKey(CachedObjectKeyDef keydef,Object key) throws CacheException;
	/**
	 * 查询   key 当前场景缓存并不知道key定义
	 * 所以遍历所有key定义，取出对应值，当有符合条件即返回
	 * @param key - 事实key( 等同于<code>CachedObjectKeyDef.createKey(Object object)<code>返回对象)
	 * @return 返回要查询的对象           当keydef(指定unique true )惟一时返回值对象
	 *                            当keydef(指定unique false)不唯一时返回ArrayList<值对象>
	 * @throws CacheException
	 */
	public Object getObjectByKey(Object key) throws CacheException;
	/**
	 * 获取全部对象，将根据CachedObjectKeyDef决定是否排序
	 * @parameter keydef  CachedObjectKeyDef
	 * @return 全部对象
	 */
	public List getAllObject(CachedObjectKeyDef keydef);
	
	/**
	 * 获取全部对象  与调用者实现的回调方法一致
	 */
	public List getAllObject();
	
	/**
	 * 刷新缓存 依据初始建立缓存时的设置（参见构造函数）
	 * @throws CacheException
	 */
	public void refresh() throws CacheException;
	
	
	/**
	 * 清空缓存，包括数据.索引
	 */
	public void clear();
	
	/**
     * 
     * @return 缓存标识
     */
	public String getCacheID();
	/*
     * 设置缓存标识
     * @param cacheID
     */
	public void setCacheID(String cacheID) ;
	
	/**
	 * 查询缓存信息
	 * @return CacheInfo  缓存信息
	 */
	public CacheInfo getCacheInfo();

	
}
