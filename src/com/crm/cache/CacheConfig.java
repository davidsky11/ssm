package com.crm.cache;

/**
 * 缓存配置
 * @author comm
 *
 */
public class CacheConfig {
	 /**是否支持分布式*/
    private static  boolean isSupportDistribute = true;
    /**分布式线程轮询时间*/
    private static long pollerCycle = 1000;
	/**
	 * @return the isSupportDistribute
	 */
	public static boolean getIsSupportDistribute() {
		return isSupportDistribute;
	}
	/**
	 * @param isSupportDistribute the isSupportDistribute to set
	 */
	public static void setSupportDistribute(boolean isSupportDistribute) {
		CacheConfig.isSupportDistribute = isSupportDistribute;
	}
	/**
	 * @return the pollerCycle
	 */
	public static long getPollerCycle() {
		return pollerCycle;
	}
	/**
	 * @param pollerCycle the pollerCycle to set
	 */
	public static void setPollerCycle(long pollerCycle) {
		CacheConfig.pollerCycle = pollerCycle;
	}
}
