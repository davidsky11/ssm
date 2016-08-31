package com.crm.cache;

/**
 * 支持分布式缓存
 * @author comm
 *
 */
public interface SynCache {
  /**
   * 刷新缓存调用方法，供轮询线程使用，区别于程序调用刷新，该方法不会调整同步计数器
   * @throws CacheException
   */
  public void refresh4PollerThread() throws CacheException;
}
