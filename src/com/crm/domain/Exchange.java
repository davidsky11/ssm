package com.crm.domain;

import java.util.Date;

/** 
 * @ClassName	Exchange.java
 * @Description 兑奖实体/兑奖记录
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:26:31
 * @Version 	V1.0    
 */
public class Exchange {
	
	private String id;
	private String userId;  // 兑奖用户
	private User user;
	private Date exchangeTime;  // 兑奖时间
	private String insideCode;  // 瓶盖内码
	private String privateCode;  // 瓶身码
	private String publicCode;  // 公共编码
	private Double longitude;  // 经度
	private Double latitude;  // 纬度
	private String flagCode;  // 硬件标识码，例如MAC地址
	private String waresId;  // 商品ID
	private String exchangeType;  // 兑奖类型
	private String beneficiary;  // 受益人
	private Wares wares;
	private String awardId;
	private Award award;
	private Activity activity;
	
	public String getId() {
		return id;
	}
	public Date getExchangeTime() {
		return exchangeTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}
	public String getPublicCode() {
		return publicCode;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public String getFlagCode() {
		return flagCode;
	}
	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setFlagCode(String flagCode) {
		this.flagCode = flagCode;
	}
	public String getInsideCode() {
		return insideCode;
	}
	public String getPrivateCode() {
		return privateCode;
	}
	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}
	public void setPrivateCode(String privateCode) {
		this.privateCode = privateCode;
	}
	public String getWaresId() {
		return waresId;
	}
	public void setWaresId(String waresId) {
		this.waresId = waresId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public Wares getWares() {
		return wares;
	}
	public void setWares(Wares wares) {
		this.wares = wares;
	}
	public Award getAward() {
		return award;
	}
	public void setAward(Award award) {
		this.award = award;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public String getExchangeType() {
		return exchangeType;
	}
	public String getBeneficiary() {
		return beneficiary;
	}
	public void setExchangeType(String exchangeType) {
		this.exchangeType = exchangeType;
	}
	public void setBeneficiary(String beneficiary) {
		this.beneficiary = beneficiary;
	}
	
	@Override
	public String toString() {
		return "Exchange [id=" + id + ", userId=" + userId + ", user=" + user + ", exchangeTime=" + exchangeTime
				+ ", insideCode=" + insideCode + ", privateCode=" + privateCode + ", publicCode=" + publicCode
				+ ", longitude=" + longitude + ", latitude=" + latitude + ", flagCode=" + flagCode + ", waresId="
				+ waresId + ", exchangeType=" + exchangeType + ", beneficiary=" + beneficiary + ", awardId=" + awardId
				+ "]";
	}
	
}
 