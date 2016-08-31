package com.crm.dao.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/** 
 * @ClassName	AbstractBaseRedisDao.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月16日 下午3:55:59
 * @Version 	V1.0    
 */
public abstract class AbstractBaseRedisDao<K, V> {

	 protected RedisTemplate<K, V> redisTemplate;  
	  
	/** 
	 * 设置redisTemplate 
	 * @param redisTemplate the redisTemplate to set 
	 */  
	//@Autowired
	public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {  
	    this.redisTemplate = redisTemplate;  
	}  
	      
	/** 
	 * 获取 RedisSerializer 
	 * <br>------------------------------<br> 
	 */  
	protected RedisSerializer<String> getRedisSerializer() {  
	    return redisTemplate.getStringSerializer();  
	}  
	    
}
 