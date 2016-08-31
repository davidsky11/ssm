package com.crm.domain;

/** 
 * @ClassName	Exchange.java
 * @Description 兑奖实体
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:26:31
 * @Version 	V1.0    
 */
public class Exchange {
	
	private String id;
	private String accountId;  // 兑奖用户
	private String exchangeTime;  // 兑奖时间
	private String insideCode;  // 瓶盖内码
	private String privateCode;  // 瓶身码
	private String publicCode;  // 公共编码
	private Double longitude;  // 经度
	private Double latitude;  // 纬度
	private String flagCode;  // 硬件标识码，例如MAC地址
	private String status;   // 兑奖状态
	private String waresId;  // 商品编码
	
	public String getId() {
		return id;
	}
	public String getAccountId() {
		return accountId;
	}
	public String getExchangeTime() {
		return exchangeTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setExchangeTime(String exchangeTime) {
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getWaresId() {
		return waresId;
	}
	public void setWaresId(String waresId) {
		this.waresId = waresId;
	}
	
	@Override
	public String toString() {
		return "Exchange [id=" + id + ", accountId=" + accountId + ", exchangeTime=" + exchangeTime + ", insideCode="
				+ insideCode + ", privateCode=" + privateCode + ", publicCode=" + publicCode + ", longitude="
				+ longitude + ", latitude=" + latitude + ", flagCode=" + flagCode + ", status=" + status + ", waresId="
				+ waresId + "]";
	}
	
}
 