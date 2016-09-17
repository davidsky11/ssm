package com.crm.domain.dto;

import com.crm.domain.ScanRecord;

/** 
 * @ClassName	ScanRecordDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月4日 下午1:59:00
 * @Version 	V1.0    
 */
public class ScanRecordDto {
	
	private String username;
	private String scanTime;
	private String longitude;
	private String latitude;
	private String formattedAddress;
	private String sematicDescription;
	private String activityName;
	private String awardName;
	
	public ScanRecordDto(ScanRecord sr) {
		this.scanTime = sr.getScanTime();
		this.longitude = sr.getLongitude() + "";
		this.latitude = sr.getLatitude() + "";
		this.formattedAddress = sr.getFormattedAddress();
		this.sematicDescription = sr.getSematicDescription();
		
		if (sr.getUser() != null) {
			this.username = sr.getUser().getUsername();
		} else {
			this.username = sr.getUserName();
		}
	}
	
	public String getUsername() {
		return username;
	}
	public String getScanTime() {
		return scanTime;
	}
	public String getLongitude() {
		return longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public String getSematicDescription() {
		return sematicDescription;
	}
	public String getActivityName() {
		return activityName;
	}
	public String getAwardName() {
		return awardName;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setScanTime(String scanTime) {
		this.scanTime = scanTime;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public void setSematicDescription(String sematicDescription) {
		this.sematicDescription = sematicDescription;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	
	@Override
	public String toString() {
		return "ScanRecordDto [username=" + username + ", scanTime=" + scanTime + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", formattedAddress=" + formattedAddress + ", sematicDescription="
				+ sematicDescription + ", activityName=" + activityName + ", awardName=" + awardName + "]";
	}
	
}
 