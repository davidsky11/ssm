package com.crm.wechat.model; 

/** 
 * @ClassName	WechatUserInfo.java
 * @Description 微信用户的基本信息
 * @Author		kevin 
 * @CreateTime  2016年7月24日 下午4:33:53
 * @Version 	V1.0    
 */
public class WechatUserInfo {
	
	// 用户的标识
	private String openId;
	// 关注状态（0-未关注，1-关注），未关注时获取不到其余信息
	private int subscribe;
	// 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
	private String subscribeTime;
	// 呢称
	private String nickname;
	// 用户的性别（0-未知，1-男性，2-女性）
	private int sex;
	// 用户所在国家
	private String country;
	// 用户所在省份
	private String province;
	// 用户所在城市
	private String city;
	// 用户的语言，简体中文为zh_CN
	private String language;
	
	// 用户头像
	private String headImgUrl;
	public String getOpenId() {
		return openId;
	}
	public int getSubscribe() {
		return subscribe;
	}
	public String getSubscribeTime() {
		return subscribeTime;
	}
	public String getNickname() {
		return nickname;
	}
	public int getSex() {
		return sex;
	}
	public String getCountry() {
		return country;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public String getLanguage() {
		return language;
	}
	public String getHeadImgUrl() {
		return headImgUrl;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setSubscribe(int subscribe) {
		this.subscribe = subscribe;
	}
	public void setSubscribeTime(String subscribeTime) {
		this.subscribeTime = subscribeTime;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setSex(int sex) {
		this.sex = sex;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}
	
}
 