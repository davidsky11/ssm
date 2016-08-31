package com.crm.cache;

import java.util.List;

/**
 * 执行回调方法
 * @author comm
 *
 */
public class RefreshCallBackExecutor {
	private RefreshCallBack refreshCallBack = null;
	
	/**
	 * 设置回调
	 * @param refreshCallBack
	 */
	public void setRefreshCallBack(RefreshCallBack refreshCallBack){
		this.refreshCallBack = refreshCallBack;
	}
	
	/**
	 * 执行回调
	 */
	public List executeRefreshCallBack(){
		return refreshCallBack.setSourceList();
	}
	


}
