package com.crm.domain;

/** 
 * @ClassName	ScanRecord.java
 * @Description 扫描记录实体
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:39:25
 * @Version 	V1.0    
 */
public class ScanRecord {

	private String id;
	private String accountId;  // 前端用户ID
	private String accountName;  // 前端用户名称
	private Double longitude;  // 经度
	private Double latitude;  // 纬度
	private String scanTime;  // 扫描时间
	
	public String getId() {
		return id;
	}
	public String getAccountId() {
		return accountId;
	}
	public String getAccountName() {
		return accountName;
	}
	public Double getLongitude() {
		return longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public String getScanTime() {
		return scanTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	
	@Override
	public String toString() {
		return "ScanRecord [id=" + id + ", accountId=" + accountId + ", accountName=" + accountName + ", longitude="
				+ longitude + ", latitude=" + latitude + ", scanTime=" + scanTime + "]";
	}
	
}
 