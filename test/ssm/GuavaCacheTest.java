package ssm;

import java.util.concurrent.TimeUnit;

import org.eclipse.jdt.internal.compiler.batch.Main;

import com.crm.util.RandomUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/** 
 * @ClassName	GuavaCacheTest.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月31日 下午3:58:29
 * @Version 	V1.0    
 */
public class GuavaCacheTest {

	static Cache<String, String> codeCache = null;
	
	public static void main(String[] args) {
		
		String key = "15071493575";
		String value = RandomUtil.getRandomNumber(4);
		
		codeCache = CacheBuilder.newBuilder()
				.concurrencyLevel(8)  // 最多8个并发
				.initialCapacity(1)  // 初始容量
				.maximumSize(100)  // 最大容量
				.expireAfterAccess(10, TimeUnit.MINUTES)  // 10分钟过期时间，不发生读写
				.build();
		
		codeCache.put(key, value);
		System.out.println(codeCache.asMap().get(key));
		
		System.out.println(codeCache.getIfPresent(key));
	}
}
 