package com.crm.memcache;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.crm.authorization.model.ValidModel;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

/** 
 * @ClassName	MemCache.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月31日 下午8:57:02
 * @Version 	V1.0    
 */
@Component
public class ValidCache {
	
	@Autowired
	private Cache validCache;  // 验证码缓存
	
	/**
	 * @Title:			saveValidCode
	 * @Description:	将当前ValidModel加入缓存
	 * @param model
	 */
	public void saveValidModel(ValidModel model) {
		Element element = new Element(model.getPhone(), model);
		validCache.put(element);
	}

	/**
	 * @Title:			getTokenModel
	 * @Description:	根据userId获取对应的TokenModel
	 * @param userId
	 * @return
	 */
	public ValidModel getValidModel(String phone) {
		Element element = validCache.get(phone);
		return (element == null ? null : (ValidModel) element.getValue());
	}
	
	/**
	 * @Title:			checkValidToken
	 * @Description:	根据phone检查
	 * @param userId
	 * @return
	 */
	public boolean checkValidToken(String phone) {
		Element element = validCache.get(phone);
		if (element == null) {
			return false;
		}
		
		ValidModel model = (element == null ? null : (ValidModel) element.getValue());
		if (model == null) {
			return false;
		}
		
		Date endTime = model.getExpireTime();
		if (endTime.before(new Date())) {  // 过期
			validCache.remove(phone);
			return false;
		}
		
		return true;
	}
	
	/**
	 * @Title:			deleteValidModel
	 * @Description:	删除TokenModel
	 * @param userId
	 */
	public boolean deleteValidModel(String phone) {
		if (phone != null) {
			validCache.remove(phone);
			return true;
		} else {
			return false;
		}
	}
	
}
 