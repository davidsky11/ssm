package com.crm.dao.redis.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import com.crm.dao.redis.UserDao;
import com.crm.domain.User;

/** 
 * @ClassName	UserDaoImpl.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月16日 下午4:05:09
 * @Version 	V1.0    
 */
@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	protected RedisTemplate<String, User> redisTemplate;  
	      
	/** 
	 * 获取 RedisSerializer 
	 * <br>------------------------------<br> 
	 */  
	protected RedisSerializer<String> getRedisSerializer() {  
	    return redisTemplate.getStringSerializer();  
	}  
	
	/**  
     * 新增 
     *<br>------------------------------<br> 
     * @param user 
     * @return 
     */  
    public boolean add(final User user) {  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(String.valueOf(user.getId()));  
                byte[] name = serializer.serialize(user.getUsername());  
                return connection.setNX(key, name);  
            }  
        });  
        return result;  
    }  
      
    /** 
     * 批量新增 使用pipeline方式   
     *<br>------------------------------<br> 
     *@param list 
     *@return 
     */  
    public boolean add(final List<User> list) {  
        Assert.notEmpty(list);  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                for (User user : list) {  
                	byte[] key  = serializer.serialize(String.valueOf(user.getId()));  
                    byte[] name = serializer.serialize(user.getUsername());   
                    connection.setNX(key, name);  
                }  
                return true;  
            }  
        }, false, true);  
        return result;  
    }  
      
    /**  
     * 删除 
     * <br>------------------------------<br> 
     * @param key 
     */  
    public void delete(String key) {  
        List<String> list = new ArrayList<String>();  
        list.add(key);  
        delete(list);  
    }  
  
    /** 
     * 删除多个 
     * <br>------------------------------<br> 
     * @param keys 
     */  
    public void delete(List<String> keys) {  
        redisTemplate.delete(keys);  
    }  
  
    /** 
     * 修改  
     * <br>------------------------------<br> 
     * @param user 
     * @return  
     */  
    public boolean update(final User user) {  
        int key = user.getId();
        if (get(key) == null) {  
            throw new NullPointerException("数据行不存在, key = " + key);  
        }  
        boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {  
            public Boolean doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key  = serializer.serialize(String.valueOf(user.getId()));  
                byte[] name = serializer.serialize(user.getUsername());   
                connection.set(key, name);  
                return true;  
            }  
        });  
        return result;  
    }  
  
    /**  
     * 通过key获取 
     * <br>------------------------------<br> 
     * @param keyId 
     * @return 
     */  
    public User get(final int keyId) {  
        User result = redisTemplate.execute(new RedisCallback<User>() {  
            public User doInRedis(RedisConnection connection)  
                    throws DataAccessException {  
                RedisSerializer<String> serializer = getRedisSerializer();  
                byte[] key = serializer.serialize(String.valueOf(keyId));  
                byte[] value = connection.get(key);  
                if (value == null) {  
                    return null;  
                }  
                String name = serializer.deserialize(value);  
                return new User(keyId, name);  
            }  
        });  
        return result;  
    }

}
 