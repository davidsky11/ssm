package com.crm.wechat.model; 

/** 
 * @ClassName	Token.java
 * @Description 凭证
 * @Author		kevin 
 * @CreateTime  2016年7月24日 下午5:11:20
 * @Version 	V1.0    
 */
public class Token {

	// 接口访问凭证
	private String accessToken;
	// 凭证有效期，单位：秒
	private int expiresIn;
	
	public String getAccessToken() {
		return accessToken;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
	
}
 