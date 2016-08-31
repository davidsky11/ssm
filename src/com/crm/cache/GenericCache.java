package com.crm.cache;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

//import com.comm.common.cache.distribute.CacheDao;
 



/**
 *
 * 通用缓存
 * 基本策略：
 *     无单独添加修改删除缓存对象方法，
 *     当缓存需要同步时整体刷新
 *     支持自定义key索引
 *     支持索引排序
 * 注意：
 *     当构造函数参数isCopy属性设置为true时，缓存将拷贝需要缓存的list集合，这时需要List<bean>的bean 
 *     实现接口 java.io.Serializable以保证对象可序列化，当要缓存的集合数量较大时不推荐设置为true,但当
 *     isCopy属性设置为false时要注意缓存也将持有传入对对象集合的引用，所以对传入对象集合的修改将影响到
 *     缓存,也可以认为isCopy=true缓存为只读缓存
 *     
 * 
 * @author comm
 *
 */
public class GenericCache extends AbstractCache implements Cache,SynCache,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5328439129145751378L;
	/**是否拷贝传入集合 */
    private boolean isCopy = false; 
	/**存放需要缓存对象的集合*/
	List cache ;
	/**索引key数组*/
	CachedObjectKeyDef[] keydefs ;
	/**保存索引的索引*/
	private Map<Class<CachedObjectKeyDef>,Map> index = new HashMap(2);
	/**用于保存CachedObjectKeyDef指定了排序规则*/
	private Map<Class<CachedObjectKeyDef>,List> sortCache = new HashMap(2); 
	/**刷新回调 */
	private RefreshCallBackExecutor callBackExecutor = new RefreshCallBackExecutor();
	/**
	 * @return the callBackExecutor 返回刷新回调执行者
	 */
	public RefreshCallBackExecutor getCallBackExecutor() {
		return callBackExecutor;
	}
	
	public GenericCache(){}

	/**
	 * @param cacheId - 缓存标识
	 * @param keydefs-key定义数组(当数组中存在重复时，重复的key定义将被合并成一个（这种情况当真实key也形同时将造成数据覆盖问题，
	 *                           解决办法是不使用相同的key定义但可以使用不同的key定义生成相同的key）)
	 * @param isCopy   true，缓存将拷贝需要缓存的list集合，这时需要List<bean>的bean 实现接口 java.io.Serializable
     *                 false 缓存也将持有传入对象集合的引用，所以对传入对象集合的修改将影响到缓存
     * @param refreshCallBack 缓存回调方法，用于刷新 ，该方法==构造函数中传入list（list的获取方法）
	 * @throws CacheException-缓存异常
	 */
	public GenericCache(String cacheId,CachedObjectKeyDef[] keydefs,Boolean isCopy,RefreshCallBack refreshCallBack) throws CacheException  {
		if(refreshCallBack==null){
			throw new CacheException("必须指定参数RefreshCallBack refreshCallBack ，否则缓存将无法刷新");
		}
		callBackExecutor.setRefreshCallBack(refreshCallBack);
		List list = callBackExecutor.executeRefreshCallBack();
		//callBackExecutor.rearrangeObject(list);
		this.setCacheID(cacheId);
		/*if(list==null||list.size()<1){
			throw new CacheException("被缓存的对象集合成员不能小于1");
		}*/
		if(keydefs==null||keydefs.length<1){
			throw new CacheException("缓存必须制定key");
		}
		/*if(isSupportDistribute==true&&refreshCallBack==null){
			throw new CacheException("当前缓存设置支持分布式，但没有指定用于缓存刷新的回调类");
		}*/
		
		
		this.isCopy = isCopy;
		if(isCopy==false){
			this.cache = list;
		}else{
			try {
				this.cache =(List)ArrayAndCollectionUtil.listCopy(list);
			}catch(java.io.NotSerializableException e){
				throw new CacheException("当构造函数参数isCopy属性设置为true时，缓存将拷贝需要缓存的list集合，这时需要List<bean>的bean 实现接口 java.io.Serializable以保证对象可序列化");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		//Collections.sort(list, c);
		if(list!=null)Collections.unmodifiableList(this.cache);
		this.keydefs = new CachedObjectKeyDef[keydefs.length];
		System.arraycopy(keydefs, 0, this.keydefs , 0, keydefs.length);
		createIndexAndSort(list,this.keydefs);
		//////////////设置抽象类
		super.setIsSupportDistribute(isSupportDistribute);//设置是否支持分布式
	
	}
	
	/**
	 * 创建索引
	 * @param list
	 * @param keys
	 */
	public  void createIndexAndSort(List list,CachedObjectKeyDef[] keydefs){
		//初始化索引的索引
		for(int i=0; i< keydefs.length; i++){
			Map map = new HashMap();
			index.put((Class<CachedObjectKeyDef>) keydefs[i].getClass(), map);
		}
		if(list==null){
			return;
		}
		//分别处理各个索引
		for(Iterator it = list.iterator();it.hasNext();){
			Object obj = it.next();
			for(int i=0; i< keydefs.length; i++){
				addObject2Index((Map)index.get(keydefs[i].getClass()),keydefs[i],obj);
		    }
		}
		sort( keydefs);
	}
	
	/**
	 * 为对象建立索引
	 * @param m
	 * @param key
	 * @param value
	 */
	private void addObject2Index(Map m,CachedObjectKeyDef keydef,Object value){

        if ( m != null && keydef != null )
        {
            if (keydef.isUnique())
            {
                Object objectKey = keydef.createKey(value);
                if (objectKey != null)
                	 m.put(objectKey, value);
            }
            else
            {
                Object objectKey = keydef.createKey(value);
                if (objectKey != null && value != null)
                {
                    List container = (List)m.get(objectKey);
                    if (container == null)
                    {
                        container = new ArrayList();
                        m.put(objectKey, container);
                    }
                    container.add(value);
                }
            }
        }
	}
	
	/**
	 * 获取缓存大小(包含对象个数)
	 * @return
	 */
	public int getCacheSize(){
		return cache==null?0:cache.size();
	}
	
	
	/**
	 * 查询  根据对象定义和用于创建key的对象
	 * @param keydef - key定义对象
	 * @param source - key对应值对象， CachedObjectKeyDef(key定义对象)将根据该对象或属性创建真正的key
	 * @return 返回要查询的对象           当keydef(指定unique true )惟一时返回值对象
	 *                            当keydef(指定unique false)不唯一时返回ArrayList<值对象>
	 * @throws CacheException
	 */
	public Object getObject(CachedObjectKeyDef keydef,Object source) throws CacheException{
		Object m = index.get(keydef.getClass());
		if(m==null){
			throw new CacheException("key ["+ keydef.getClass()+"]不存在 ");
		}else{
			Map map = (Map)m;
			Object obj = map.get(keydef.createKey(source));
			/*if(obj!=null&&obj instanceof List ){
				 if(keydef.getComparator()!=null){
                 	Collections.sort((List)obj, keydef.getComparator());
                 }
			}*/
			return obj;
		}
	}
	
	/**
	 * 查询  根据对象定义和key
	 * @param keydef - key定义对象
	 * @param key - 事实key( 等同于<code>CachedObjectKeyDef.createKey(Object object)<code>返回对象)
	 * @return 返回要查询的对象           当keydef(指定unique true )惟一时返回值对象
	 *                            当keydef(指定unique false)不唯一时返回ArrayList<值对象>
	 * @throws CacheException
	 */
	public Object getObjectByKey(CachedObjectKeyDef keydef,Object key) throws CacheException{
		Object m = index.get(keydef.getClass());
		if(m==null){
			throw new CacheException("key ["+ keydef.getClass()+"]不存在 ");
		}else{
			Map map = (Map)m;
			Object obj = map.get(key);
			return obj;
		}
	}
	
	/**
	 * 查询   key 当前场景缓存并不知道key定义
	 * 所以遍历所有key定义，取出对应值，当有符合条件即返回
	 * @param key - 事实key( 等同于<code>CachedObjectKeyDef.createKey(Object object)<code>返回对象)
	 * @return 返回要查询的对象           当keydef(指定unique true )惟一时返回值对象
	 *                            当keydef(指定unique false)不唯一时返回ArrayList<值对象>
	 * @throws CacheException
	 */
	public Object getObjectByKey(Object key) throws CacheException{
		Object result = null;
		if(index!=null&&index.size()>0){
			Iterator<Class<CachedObjectKeyDef>> it = index.keySet().iterator();
			while(it.hasNext()){
				Object map = index.get(it.next()); 
					if(map!=null){
						Object obj = ((Map)map).get(key);
						if(obj!=null){
							result = obj;
							break;
					}	
			    }
			} 
        }
		return result;
	}
	
	
	/**
	 * 获取全部对象，将根据CachedObjectKeyDef决定是否排序
	 * @parameter keydef  CachedObjectKeyDef
	 * @return 全部对象
	 */
	public List getAllObject(CachedObjectKeyDef keydef){
		if(keydef.getComparator()==null){
			return this.cache;
		}else{
			return sortCache.get(keydef.getClass());
		}
	}
	/**
	 * 刷新缓存 依据初始建立缓存时的设置（参见构造函数）
	 * @param list - 要缓存的集合
	 * @throws CacheException
	 */
	public synchronized void refresh() throws CacheException{
		refresh4PollerThread();
		//缓存信息中刷新次数+1
		this.getCacheInfo().setRefreshNumber(this.getCacheInfo().getRefreshNumber()+1);


		 
	}
	
	/**
	 * 刷新缓存 依据初始建立缓存时的设置（参见构造函数）
	 * @param list - 要缓存的集合
	 * @throws CacheException
	 */
	public synchronized void refresh4PollerThread() throws CacheException{
		List list = callBackExecutor.executeRefreshCallBack();
		//callBackExecutor.rearrangeObject(list);
		/*if(list==null||list.size()<1){
			throw new CacheException("被缓存的对象集合成员不能小于1");
		}*/
		this.clear();
		/*if(list==null||list.size()<1){
			return;
		}*/
		if(isCopy==false){
			this.cache = list;
		}else{

			try {
				this.cache =(List)ArrayAndCollectionUtil.listCopy(list);
			}catch(java.io.NotSerializableException e){
				throw new CacheException("当构造函数参数isCopy属性设置为true时，缓存将拷贝需要缓存的list集合，这时需要List<bean>的bean 实现接口 java.io.Serializable以保证对象可序列化");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			/*try {
				this.cache =(List)Utils.copy(list);
			}catch(java.io.NotSerializableException e){
				throw new CacheException("当构造函数参数isCopy属性设置为true时，缓存将拷贝需要缓存的list集合，这时需要List<bean>的bean 实现接口 java.io.Serializable以保证对象可序列化");
			}
			catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}*/
		}
		createIndexAndSort(list,this.keydefs);
	}
	 
	
	/**
	 *  @param keydefs - key定义数组
	 * 将对keydefs中设置排序属性的CachedObjectKeyDef进行排序
	 */
	private void sort( CachedObjectKeyDef[] keydefs){
		for( int i=0;i<keydefs.length;i++){
			if(keydefs[i].getComparator()!=null){
			  dealSort(keydefs[i]);
			}
		}
	}
	
	/**
	 * 处理当前CachedObjectKeyDef的排序
	 * 
	 * @param keydef -key定义
	 */
	private void dealSort(final CachedObjectKeyDef keydef ){
		
			Map map = index.get(keydef.getClass()); 
			/*List List =   new List<Map.Entry<CachedObjectKeyDef,Object>>(map.entrySet());
			Collections.sort(List, new Comparator<Map.Entry<CachedObjectKeyDef,Object>>(){
				   public int compare(Map.Entry<CachedObjectKeyDef,Object> mapping1,Map.Entry<CachedObjectKeyDef,Object> mapping2){
				    return  keydef.getComparator().compare(mapping1.getKey(),mapping2.getKey());
				   }

				 
				  });*/
			//Collections.sort(List, keydef.getComparator());
			if(keydef.isUnique()==false){//当key对应值不唯一时排序key对应的值
				Iterator<Map.Entry<CachedObjectKeyDef, List>> it = map.entrySet().iterator();
				while(it.hasNext()){
					List List = it.next().getValue();
					if(List.size()>1){
						Collections.sort(List, keydef.getComparator());
					}
				}
			}
			
			//为整个缓存克隆一个ArrayList用于满足当前key定义对应排序以便快速查询
			List list = null;
			try {
				list = ArrayAndCollectionUtil.listCopy(this.cache);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			if(list!=null){
				Collections.sort(list, keydef.getComparator());
				sortCache.put((Class<CachedObjectKeyDef>) keydef.getClass(), list);
			}
			
		
	
	}
	

	/**
	 * 查询缓存信息
	 * @return CacheInfo  缓存信息
	 */
	public CacheInfo getCacheInfo(){
		return super.getCacheInfo(this);
	}
	
	public void clear(){
		if(this.cache!=null)this.cache.clear();
		if(this.index!=null)this.index.clear();
		if(this.sortCache!=null)this.sortCache.clear();
	}

	/**
     * 
     * @return 缓存标识
     */
	public String getCacheID() {
		return cacheId;
	}
    /**
     * 设置缓存标识
     * @param cacheId 缓存标识
     */
	public void setCacheID(String cacheId) {
		this.cacheId = cacheId;
	}

	public List getAllObject() {
		return this.cache;
	}
	
	
}
