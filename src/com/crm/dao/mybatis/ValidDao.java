package com.crm.dao.mybatis;

import java.util.concurrent.TimeUnit;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.google.common.base.Optional;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/** 
 * @ClassName	ValidDao.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月30日 上午11:46:01
 * @Version 	V1.0    
 */
@Repository("validDao")
@Scope("singleton")
public class ValidDao {
	
	Cache<String, String> codeCache = null;
	
	/*new CacheLoader<String, String>() {
	
		@Override
		public String load(String key) throws Exception {
			String str = getValueByKey(key);
			return Optional.fromNullable(str).or("");
		}
	}*/
	
	public void init() {
		if (codeCache == null) {
			codeCache = CacheBuilder.newBuilder()
					.concurrencyLevel(8)  // 最多8个并发
					.initialCapacity(1)  // 初始容量
					.maximumSize(100)  // 最大容量
					.expireAfterAccess(10, TimeUnit.MINUTES)  // 10分钟过期时间，不发生读写
					.build();
		}
	}
	
	public void putValidCode(final String phone, final String code) {
		init();
		codeCache.put(phone, code);  // 会覆盖已存在的值
	}
	
	public String getValidCode(final String key) {
		init();
		String code = codeCache.getIfPresent(key);
		return Optional.fromNullable(code).or("");
	}
	
	public void deleteCode(final String key) {
		if (codeCache != null) 
			codeCache.invalidate(key);
	}
	
	public void deleteAll() {
		if (codeCache != null) 
			codeCache.invalidateAll();
	}
	
}
 