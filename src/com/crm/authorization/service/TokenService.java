package com.crm.authorization.service;

import com.crm.authorization.model.TokenModel;
import com.crm.authorization.model.ValidModel;
import com.crm.domain.Account;
import com.crm.domain.User;

/** 
 * @ClassName	TokenManager.java
 * @Description 对Token进行操作的接口
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午1:43:48
 * @Version 	V1.0    
 */
public interface TokenService<K, V> {

	/**
	 * @Title:			createToken
	 * @Description:	创建一个Token关联上某个指定用户
	 * @param userId
	 * @return
	 */
	public TokenModel createToken(Account account);
	
	/**
	 * @Title:			createToken
	 * @Description:	创建一个Token关联上某个指定用户
	 * @param userId
	 * @return
	 */
	public TokenModel createToken(User user);
	
	/**
	 * @Title:			expire
	 * @Description:	延长有效期
	 * @param model	
	 * @param time		延长的时间
	 */
	public void expire(TokenModel model, long time);
	
	/**
	 * @Title:			checkToken
	 * @Description:	检查Token是否有效
	 * @param model
	 * @param delay		是否延长有效期
	 * @return
	 */
	public boolean checkToken(TokenModel model, boolean delay);
	
	/**
	 * @Title:			getToken
	 * @Description:	从字符串中解析Token
	 * @param authentication
	 * @return
	 */
	public TokenModel getToken(String authentication);
	
	/**
	 * @Title:			deleteToken
	 * @Description:	清除Token
	 * @param userId
	 */
	public void deleteToken(String token);
	
	/**
	 * @Title:			saveValidCode
	 * @Description:	将当前ValidModel加入缓存
	 * @param model
	 */
	public void saveValidModel(ValidModel model);
	
	/**
	 * @Title:			getTokenModel
	 * @Description:	根据userId获取对应的TokenModel
	 * @param userId
	 * @return
	 */
	public ValidModel getValidModel(String phone);
	
	/**
	 * @Title:			checkValidToken
	 * @Description:	根据phone检查
	 * @param userId
	 * @return
	 */
	public boolean checkValidToken(String phone);
	
	/**
	 * @Title:			deleteValidModel
	 * @Description:	删除TokenModel
	 * @param userId
	 */
	public boolean deleteValidModel(String phone);
	
}
 