package com.crm.wechat.pay.dto; 

/** 
 * @ClassName	ResponseDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月26日 下午1:45:02
 * @Version 	V1.0    
 */
public class ResponseDto {
	
	private String access_token;
	private Integer expires_in;
	private String openid;
	private String refresh_token;
	private String scope;
	
	public String getAccess_token() {
		return access_token;
	}
	public Integer getExpires_in() {
		return expires_in;
	}
	public String getOpenid() {
		return openid;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public String getScope() {
		return scope;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}
	public void setExpires_in(Integer expires_in) {
		this.expires_in = expires_in;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
	@Override
	public String toString() {
		return "ResponseDto [access_token=" + access_token + ", expires_in=" + expires_in + ", openid=" + openid
				+ ", refresh_token=" + refresh_token + ", scope=" + scope + "]";
	}
	
}
 