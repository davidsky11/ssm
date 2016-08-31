package com.crm.authorization.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.authorization.model.TokenModel;
import com.crm.authorization.model.ValidModel;
import com.crm.authorization.service.TokenService;
import com.crm.common.util.lang.ConcurrentDateUtil;
import com.crm.common.util.lang.DateUtil;
import com.crm.domain.Account;
import com.crm.domain.User;
import com.crm.memcache.SessionCache;
import com.crm.util.common.Const;
import com.crm.wechat.utils.MD5Util;

/** 
 * @ClassName	RedisTokenManager.java
 * @Description 通过Redis存储和验证Token的实现类
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午1:49:39
 * @Version 	V1.0    
 */
@Service("ehcacheTokenService")
public class EhcacheTokenService implements TokenService<String, String> {
	
	@Autowired
	private SessionCache sessionCache;
	//@Autowired
	//private ValidCache validCache;
	
	@Override
	public TokenModel createToken(Account account) {
		if (account == null)	return null;
		String username = account.getUsername();
		String pass = account.getPassword();
		
		String timeStr = "";
		
		try {
			timeStr = ConcurrentDateUtil.formatDate(new Date());
		} catch (ParseException e) {
			timeStr = UUID.randomUUID().toString().replaceAll("-", "");
		}
		
		/**
		 * 生成TokenModel
		 */
		String token = MD5Util.MD5(username + "_" + pass + "_" + timeStr);  // username_pass MD5
		
		TokenModel model = new TokenModel();
		model.setUserId(account.getId());
		model.setToken(token);
		model.setCreateTime(new Date());
		model.setExpireTime(DateUtil.getTime(new Date(), Const.TOKEN_EXPIRES_HOUR, true));
		
		// 将TokenModel加入缓存
		sessionCache.saveTokenModel(model);
		
		return model;
	}

	@Override
	public boolean checkToken(TokenModel model, boolean delay) {
		if (model == null) {
			return false;
		}
		
		if (sessionCache.checkToken(model.getToken())) {
			// 如果验证成功，说明此用户进行了一次有效操作，延迟token的过期时间
			if (delay)
				sessionCache.expire(model.getToken(), Const.TOKEN_EXPIRES_HOUR);
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void expire(TokenModel model, long time) {
		sessionCache.expire(model.getToken(), time);
	}

	@Override
	public TokenModel getToken(String authentication) {
		if (authentication == null || authentication.length() == 0) {
			return null;
		}
		
		TokenModel model = sessionCache.getTokenModel(authentication);
		
		return model;
	}

	@Override
	public void deleteToken(String token) {
		sessionCache.deleteTokenModel(token);
	}

	@Override
	public TokenModel createToken(User user) {
		if (user == null)	return null;
		String username = user.getUsername();
		String password = user.getPassword();
		
		String timeStr = "";
		
		try {
			timeStr = ConcurrentDateUtil.formatDate(new Date());
		} catch (ParseException e) {
			timeStr = UUID.randomUUID().toString().replaceAll("-", "");
		}
		
		/**
		 * 生成TokenModel
		 */
		String token = MD5Util.MD5(username + "_" + password + "_" + timeStr);  // username_pass MD5
		
		TokenModel model = new TokenModel();
		model.setUserId(user.getId());
		model.setToken(token);
		model.setCreateTime(new Date());
		model.setExpireTime(DateUtil.getTime(new Date(), Const.TOKEN_EXPIRES_HOUR, true));
		
		// 将TokenModel加入缓存
		sessionCache.saveTokenModel(model);
		
		return model;
	}

	@Override
	public void saveValidModel(ValidModel model) {
//		validCache.saveValidModel(model);
	}

	@Override
	public ValidModel getValidModel(String phone) {
		//return validCache.getValidModel(phone);
		return null;
	}

	@Override
	public boolean checkValidToken(String phone) {
//		return validCache.checkValidToken(phone);
		return true;
	}

	@Override
	public boolean deleteValidModel(String phone) {
//		return validCache.deleteValidModel(phone);
		return true;
	}

}
 