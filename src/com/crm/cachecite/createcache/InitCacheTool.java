package com.crm.cachecite.createcache;

import com.crm.cache.CacheException;
import com.crm.cache.CacheFactory;
import com.crm.cache.CachedObjectKeyDef;
import com.crm.cache.UpdatableCache;
import com.crm.cachecite.keydef.Dictionary_Key;
import com.crm.cachecite.keydef.Dictionary_ParendId_Key;
import com.crm.cachecite.refreshcache.Dictionary_Refresh;
import com.crm.util.SysProperConfigUtil;
import com.crm.util.common.Const;

/**
 * 初始化缓存工具
 * @author xiajun
 */
public class InitCacheTool {
	
	public void initCache() throws CacheException {
		CacheFactory.getInstance().setConfig(SysProperConfigUtil.getInstance().getPropertys());
		 
		//数据字典
		CachedObjectKeyDef[] dicKeys = {new Dictionary_Key(true), new Dictionary_ParendId_Key(false)};
		UpdatableCache dicCache = new UpdatableCache(Const.CACHE_SESSION, dicKeys, false, new Dictionary_Refresh());
		CacheFactory.getInstance().addCache(dicCache);
	}
	
}
