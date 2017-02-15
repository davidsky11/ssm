package com.crm.domain.dto;

import java.util.Date;

/** 
 * @ClassName	WaresDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年11月4日 下午8:11:34
 * @Version 	V1.0    
 */
public class WaresDto {
	
	private String id;
	private String publicCode;
	private String privateCode;
	private String insideCode;
	private Date createTime;
	private String userName;
	private String title;
	private Date scanTime;
	private String formattedAddress;
	private String sematicDescription;
	private String status;
	
	public String getId() {
		return id;
	}
	public String getPublicCode() {
		return publicCode;
	}
	public String getPrivateCode() {
		return privateCode;
	}
	public String getInsideCode() {
		return insideCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public String getUserName() {
		return userName;
	}
	public Date getScanTime() {
		return scanTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}
	public void setPrivateCode(String privateCode) {
		this.privateCode = privateCode;
	}
	public void setInsideCode(String insideCode) {
		this.insideCode = insideCode;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setScanTime(Date scanTime) {
		this.scanTime = scanTime;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getFormattedAddress() {
		return formattedAddress;
	}
	public String getSematicDescription() {
		return sematicDescription;
	}
	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}
	public void setSematicDescription(String sematicDescription) {
		this.sematicDescription = sematicDescription;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Override
	public String toString() {
		return "WaresDto [id=" + id + ", publicCode=" + publicCode + ", privateCode=" + privateCode + ", insideCode="
				+ insideCode + ", createTime=" + createTime + ", userName=" + userName + ", title=" + title
				+ ", scanTime=" + scanTime + ", formattedAddress=" + formattedAddress + ", sematicDescription="
				+ sematicDescription + ", status=" + status + "]";
	}

}
 