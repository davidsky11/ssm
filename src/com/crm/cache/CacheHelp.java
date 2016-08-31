package com.crm.cache;

import java.util.List;

import com.crm.cachecite.keydef.Dictionary_Key;
import com.crm.cachecite.keydef.Dictionary_ParendId_Key;
import com.crm.util.common.Const;

/**
 * 缓存的助手类
 * @author xiajun
 * 
 */

@SuppressWarnings("all")
public class CacheHelp {

    private CacheFactory cacheFactory = CacheFactory.getInstance();

    /**
     * 通过className查找所有对象
     * 
     * @param memberClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getAllObjectInCache(Class classObj) {
        List objectList = null;
        try {
            objectList = cacheFactory.getCache(classObj.getName())
                    .getAllObject();
        } catch (CacheException e) {
            e.printStackTrace();
        }
        return objectList;
    }

    /**
     * 通过 cache id 查找所有对象
     * 
     * @param memberClass
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getAllObjectInCache(String cacheId) {
        List objectList = null;
        try {
            objectList = cacheFactory.getCache(cacheId).getAllObject();
        } catch (CacheException e) {
            e.printStackTrace();
        }
        return objectList;
    }

    /**
     * 从缓存中获取指定的缓存标识，缓存索引，缓存键的对象
     * 
     * @param classObj
     * @param keyDef
     * @param key
     * @return
     * @throws CacheException
     */
    @SuppressWarnings("unchecked")
    public Object getObjectInCache(Class classObj, CachedObjectKeyDef keyDef,
            Object key) throws CacheException {
        return cacheFactory.getCache(classObj.getName()).getObjectByKey(keyDef,
                key);
    }

    /**
     * 从缓存中获取指定的缓存标识，缓存索引，缓存键的对象
     * 
     * @param classObj
     * @param keyDef
     * @param key
     * @return
     * @throws CacheException
     */
    public Object getObjectInCache(String keyName, CachedObjectKeyDef keyDef,
            Object key) throws CacheException {
        return cacheFactory.getCache(keyName).getObjectByKey(keyDef, key);
    }

    /**
     * 刷新缓存
     * 
     * @param cacheKey
     */
    public void refreshCache(String cacheKey) {
        try {
            CacheFactory.getInstance().getCache(cacheKey).refresh();
        } catch (CacheException e) {
            e.printStackTrace();
            //throw new CommRuntimeException(e.getMessage(), e);
        }
    }
    /**
     * 通过parendId从缓存中查找所有数据字典
     * @param
     * @return
     */
    public List<Cache> getDicByParendId(String code) throws CacheException {
        List<Cache> cacheList = null;
        try {
        	cacheList = (List<Cache>) cacheFactory.getCache(Const.CACHE_SESSION)
                    .getObjectByKey(new Dictionary_ParendId_Key(false), code);
        } catch (CacheException e) {
            e.printStackTrace();
        }
        return cacheList;
    }
    
    
    /**
     * 通过id获取数据字典
     * 
     * @param code
     * @return
     */
    public Cache getSysDictionaryByCode(String id) {
    	Cache cache = null;
        try {
        	cache = (Cache) cacheFactory.getCache(Const.CACHE_SESSION)
                    .getObjectByKey(new Dictionary_Key(true), id);
        } catch (CacheException e) {
            e.printStackTrace();
        }
        return cache;
    }
    
    /**
     * 通过id删除缓存中的数据字典
     * @param id
     */
    public void removeDictionary(String id){
        try {
            UpdatableCache dictionaryCache = (UpdatableCache) cacheFactory.getCache(Const.CACHE_SESSION);
            
            Object obj = getSysDictionaryByCode(id);
            dictionaryCache.deleteCacheObject(obj);
        } catch (CacheException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    /**
     * 通过id更新缓存中的数据字典
     * @param id
     */
    public void updateDictionary(Cache cache){
        try {
            UpdatableCache sysDictionaryCache = (UpdatableCache) cacheFactory.getCache(Const.CACHE_SESSION);
            sysDictionaryCache.updateCacheObject(cache);
        } catch (CacheException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
    
    /**
     * 添加数据字典到缓存中
     * @param id
     */
    public void addDictionary(Cache cache){
        try {
            UpdatableCache dictionaryCache = (UpdatableCache) cacheFactory.getCache(Const.CACHE_SESSION);
            dictionaryCache.addCacheObject(cache);
        } catch (CacheException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    } 
    
}
