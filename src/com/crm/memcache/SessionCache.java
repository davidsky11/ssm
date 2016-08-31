package com.crm.memcache;

import java.util.Date;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.authorization.model.TokenModel;
import com.crm.common.util.lang.DateUtil;
import com.crm.rest.domain.ApiResult;
import com.crm.util.common.Const;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/** 
 * @ClassName	MemCache.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月31日 下午8:57:02
 * @Version 	V1.0    
 */
@Component("sessionCache")
public class SessionCache {
	
	private static final String FIELD = "SESSION";

	@Autowired
	private Cache userEhCache;  // 这里引入的Cache是net.sf.ehcache.Cache
	
	/**
	 * @Title:			shutdown
	 * @Description:	关闭缓存管理器
	 */
	@PreDestroy
	protected void shutdown() {
		if (userEhCache != null) {
			userEhCache.getCacheManager().shutdown();
		}
	}
	
	/**
	 * @Title:			saveCache
	 * @Description:	将当前TokenModel加入缓存
	 * @param model
	 */
	public void saveTokenModel(TokenModel model) {
		Element element = new Element(model.getToken(), model);
		userEhCache.put(element);
	}
	
	/**
	 * @Title:			getTokenModel
	 * @Description:	根据userId获取对应的TokenModel
	 * @param userId
	 * @return
	 */
	public TokenModel getTokenModel(String token) {
		Element element = userEhCache.get(token);
		return (element == null ? null : (TokenModel) element.getValue());
	}
	
	/**
	 * @Title:			checkToken
	 * @Description:	根据userId检查
	 * @param userId
	 * @return
	 */
	public ApiResult<String> checkToken1(String token) {
		ApiResult<String> result = new ApiResult<String>();
		
		Element element = userEhCache.get(token);
		if (element == null) {

			result.setCode(Const.ERROR_NULL_POINTER);
			result.setData("checkToken [未找到userId对应的element信息]");
			return result;
		}
		
		TokenModel model = (element == null ? null : (TokenModel) element.getValue());
		if (model == null) {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setData("checkToken [未找到userId对应的token信息]");
			return result;
		}
		
		if (!model.getToken().equals(token)) {
			result.setCode(Const.ERROR_NOT_EQUALS);
			result.setData("checkToken [token信息不匹配]");
			return result;
		}
		
		Date endTime = model.getExpireTime();
		if (!endTime.before(new Date())) {  // 过期
			result.setCode(Const.WARN_TIMEOUT);
			result.setData("checkToken [token信息过期]");
			return result;
		}
		
		expire(token, Const.TOKEN_EXPIRES_HOUR);  // 延长过期时间
		
		result.setCode(Const.INFO_NORMAL);
		result.setData("checkToken [token信息正常]");
		return result;
	}
	
	/**
	 * @Title:			checkToken
	 * @Description:	根据userId检查
	 * @param userId
	 * @return
	 */
	public boolean checkToken(String token) {
		Element element = userEhCache.get(token);
		if (element == null) {
			return false;
		}
		
		TokenModel model = (element == null ? null : (TokenModel) element.getValue());
		if (model == null) {
			return false;
		}
		
		Date endTime = model.getExpireTime();
		if (endTime.before(new Date())) {  // 过期
			userEhCache.remove(model.getToken());
			return false;
		}
		
		//expire(token, Const.TOKEN_EXPIRES_HOUR);  // 延长过期时间
		
		return true;
	}
	
	/**
	 * @Title:			expire
	 * @Description:	延长过期时间
	 * @param userId
	 * @param expireTimes	单位：小时
	 */
	public boolean expire(String token, long expireTimes) {
		Element element = userEhCache.get(token);
		
		/**
		 * 先删除
		 */
		TokenModel tmp = null;
		if (element == null)	return false;
		else {
			TokenModel model = (TokenModel) element.getValue();
			try {
				tmp = model.clone();
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
			this.deleteTokenModel(token); // 删除
		}
		
		/**
		 * 再添加
		 */ 
		// 将过期时间延迟
		tmp.setExpireTime(DateUtil.getTime(new Date(), expireTimes, true));
		Element element_new = new Element(token, tmp);
		userEhCache.put(element_new);
		
		return true;
	}
	
	/**
	 * @Title:			deleteTokenModel
	 * @Description:	删除TokenModel
	 * @param userId
	 */
	public boolean deleteTokenModel(String token) {
		if (token != null) {
			userEhCache.remove(token);
			return true;
		} else {
			return false;
		}
	}
	
}
 