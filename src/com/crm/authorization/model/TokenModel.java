package com.crm.authorization.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName	TokenModel.java
 * @Description Token的Model类，可以增加字段提高安全性，例如时间戳、url签名等
 * 			服务端生成的Token一般为随机的非重复字符串，根据应用对安全性的不同要求，
 * 			会将其添加时间戳（通过时间判断Token是否被盗用）或url签名（通过请求地址
 * 			判断Token是否被盗用）后加密进行传输。
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午1:31:05
 * @Version 	V1.0    
 */
public class TokenModel implements Serializable, Cloneable {

	private static final long serialVersionUID = -4311567505328009807L;

	// 用户ID
	private String userId;
	
	// 随机生成的uuid
	private String token;   // token的生成规则为 MD5(username+password)
	
	// 创建时间
	private Date createTime;
	
	// 超期时间
	private Date expireTime;
	
	public TokenModel() {}

	public TokenModel(String userId, String token) {
		this.userId = userId;
		this.token = token;
		this.createTime = new Date();
	}

	public String getUserId() {
		return userId;
	}

	public String getToken() {
		return token;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}

	@Override
	public TokenModel clone() throws CloneNotSupportedException {
		TokenModel model = (TokenModel) super.clone();
		model.createTime = (Date) this.createTime.clone();
		model.expireTime = (Date) this.expireTime.clone();
		return model;
	}
	
}
 