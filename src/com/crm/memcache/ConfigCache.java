package com.crm.memcache; 

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/** 
 * @ClassName	ConfigCache.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月31日 下午2:01:52
 * @Version 	V1.0    
 */
//@Component("configCache")
public class ConfigCache implements ConfigService {

	private final static Log log = LogFactory.getLog(ConfigCache.class);
	 
    /**
     * 更新缓存时记录的时间
     */
    private volatile long time = 0L;
 
    /**
     * 正在更新缓存时的门闩，为 true 时表示当前没有更新缓存，为 true 时表示当前正在更新缓存
     */
    private volatile boolean updateGate = true;
 
    /**
     * 缓存容器
     */
    private Map<String, SysConfig> cache = new ConcurrentHashMap<String, SysConfig>();
 
    private CommonDao commonDao;
 
    //@Autowired
    public ConfigCache(CommonDao commonDao) {
        this.commonDao = commonDao;
        log.info("initializing cache...");
        refreshCache();
        time = System.currentTimeMillis();
        log.info("initialized cache finished, cache size: {" + cache.size() + "}, set cache time to current: { " + time + "}, cache timeout: { " + SysConfig.CACHE_TIMEOUT + "}ms");
    }
 
    /**
     * <p>根据配置的键名获取配置值</p>
     *
     * @param configKey
     * @return
     * @author frankiegao123
     * 2010-6-10 上午11:18:33
     */
    public SysConfig getSysConfig(String configKey) {
        long current = System.currentTimeMillis();
        if(updateGate && isTimeout(current)) {
            synchronized (this) {
                if(updateGate) {
                    timeoutSynRefresh(current);
                }
            }
        }
        return cache.get(configKey);
    }
 
    /**
     * <p>超时时更新缓存。该方法需要在同步环境中调用</p>
     * @param current
     * @author frankiegao123
     * 2010-6-10 上午11:16:30
     */
    private void timeoutSynRefresh(long current) {
        updateGate = false;
        log.info("refresh cache start..., time out: {" + (current - time) / 1000.0 + " }, size: { " +  cache.size() + "}, set updateGate to false");
        try {
            refreshCache();
            time = current;
            log.info("refresh cache finished, size after update: { " + cache.size() + "}, set cache time to current: { " + String.valueOf(time) + "}");
        } catch (Exception e) {
            log.error("refresh cache failed", e);
        } finally {
            updateGate = true;
            log.info("refresh cache finished, set updateGate to true");
        }
    }
 
    /**
     * <p>更新缓存数据</p>
     *
     * @author frankiegao123
     * 2010-6-10 上午11:15:55
     */
    private void refreshCache() {
        List<SysConfig> configs = commonDao.getSysConfigs();
        for(Iterator<SysConfig> i = configs.iterator(); i.hasNext(); ) {
            SysConfig config = i.next();
            cache.put(config.getKey(), config);
        }
        commonDao.clear();
        SysConfig config = cache.get(SysConfig.TEST_KEY);
        if(config == null) {
            log.error("refresh cache, cannot find TEST_KEY");
        } else {
            log.info("refresh cache, find TEST_KEY = [{" + config.getValue() + "}]");
        }
    }
 
    /**
     * <p>缓存是否超时</p>
     *
     * @param current
     * @return
     * @author frankiegao123
     * 2010-6-10 上午11:16:12
     */
    private boolean isTimeout(long current) {
        return (current - time >= SysConfig.CACHE_TIMEOUT);
    }
 
    Collection<SysConfig> getSysConfigs() {
        return Collections.unmodifiableCollection(cache.values());
    }
 
    int getSize() {
        return cache.size();
    }
 
    long getTime() {
        return time;
    }
 
    boolean isUpdateGate() {
        return updateGate;
    }
 
    void refresh() {
        time = 0L;
        log.info("refresh: reset cache time to 0");
        getSysConfig("none");
    }
    
}
 