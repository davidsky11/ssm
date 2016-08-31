package com.crm.authorization.service.impl;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import com.crm.authorization.model.TokenModel;
import com.crm.authorization.service.TokenService;
import com.crm.domain.Account;
import com.crm.util.common.Const;

/** 
 * @ClassName	RedisTokenManager.java
 * @Description 通过Redis存储和验证Token的实现类
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午1:49:39
 * @Version 	V1.0    
 */
@Service("redisTokenService")
public class RedisTokenService implements TokenService<String, String> {

	private RedisTemplate<String, String> redisTemplate;
	
	@Autowired
	public void setRedis(RedisTemplate<String, String> redisTemplate) {
		this.redisTemplate = redisTemplate;
		// 泛型设置成Long后必须更改对应的序列化方案
//		redisTemplate.setKeySerializer(new StringRedisSerializer());
//		redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
	}
	
	@Override
	public TokenModel createToken(Account account) {
		// 使用uuid作为源token
		String token = UUID.randomUUID().toString().replace("-", "");
		TokenModel model = new TokenModel(account.getId(), token);
		// 存储到Redis并设置过期时间
		redisTemplate.boundValueOps(account.getId()).set(token, Const.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
		return model;
	}

	@Override
	public boolean checkToken(TokenModel model) {
		if (model == null) {
			return false;
		}
		String token = (String) redisTemplate.boundValueOps(model.getUserId()).get();
		if (token == null || !token.equals(model.getToken())) {
			return false;
		}
		// 如果验证成功，说明此用户进行了一次有效操作，延迟token的过期时间
		redisTemplate.boundValueOps(model.getUserId()).expire(Const.TOKEN_EXPIRES_HOUR, TimeUnit.HOURS);
		return true;
	}

	@Override
	public TokenModel getToken(String authentication) {
		if (authentication == null || authentication.length() == 0) {
			return null;
		}
		String[] param = authentication.split("-");
		if (param.length != 2) {
			return null;
		}
		
		// 使用userId和源token简单拼接成的token，可以增加加密措施
		String userId = param[0];
		String token = param[1];
		return new TokenModel(userId, token);
	}

	@Override
	public void deleteToken(String userId) {
		redisTemplate.delete(userId);
	}

}
 