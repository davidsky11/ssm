package com.crm.cache;

import java.util.List;

/**
 * 可更新的缓存
 * @author xj
 *
 */
@SuppressWarnings("serial")
public class UpdatableCache  extends GenericCache{
	
	public UpdatableCache(String cacheId,CachedObjectKeyDef[] keydefs,Boolean isCopy,RefreshCallBack refreshCallBack) throws CacheException  {
		   super(cacheId, keydefs, isCopy, refreshCallBack);
	}
	
	/**
	 * 添加缓存元素
	 * @param obj
	 */
	@SuppressWarnings("unchecked")
	public void  addCacheObject(Object obj){
		if(obj instanceof List){
			List list=(List) obj;
			if(list!=null && list.size()>0){
				for(Object item:list){
					updateCacheObject(item, false);
				}
				refreshCache(this.cache);
			}
		}else{
			addCacheObject(obj, true);
		}
	}
	
    /**
     * 删除缓存元素
     * @param obj
     */
	@SuppressWarnings("unchecked")
	public void  deleteCacheObject(Object obj){
		if(obj instanceof List){
			List list=(List) obj;
			if(list!=null && list.size()>0){
				for(Object item:list){
					deleteCacheObject(item, false);
				}
				refreshCache(this.cache);
			}
		}else{
			deleteCacheObject(obj, true);
		}
	}
	
	/**
	 * 更新缓存元素
	 */
	@SuppressWarnings("unchecked")
	public void   updateCacheObject(Object obj){
		if(obj instanceof List){
			List list=(List) obj;
			if(list!=null && list.size()>0){
				for(Object item:list){
					updateCacheObject(item, false);
				}
				refreshCache(this.cache);
			}
		}else{
			updateCacheObject(obj, true);
		}
	}
	/**
	 * 可能有问题    xj 2014-5-23
	 * @param list
	 */
	@SuppressWarnings("unchecked")
	private void   refreshCache(List list){
		if(list!=null && list.size()>0){
			this.getCallBackExecutor().executeRefreshCallBack();
			this.createIndexAndSort(list,this.keydefs);
		}
	}
	
	@SuppressWarnings("unchecked")
	private void  addCacheObject(Object obj,boolean isRefresh){
		List list=this.cache;
		if(list!=null && list.size()>0){
			list.add(obj);
			if(isRefresh){
				refreshCache(list);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	private void  deleteCacheObject(Object obj,boolean isRefresh){
		List list=this.cache;
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				Object temp=list.get(i);
				if(temp.equals(obj)){
					list.remove(i);
					break;
				}
			}
			if(isRefresh){
			   refreshCache(list);
			}
		}
	}
	
	private void   updateCacheObject(Object obj,boolean isRefresh){
		deleteCacheObject(obj,false);
		addCacheObject(obj,isRefresh);
	}
}
