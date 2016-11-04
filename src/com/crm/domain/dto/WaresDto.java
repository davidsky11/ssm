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
	private Date scanTime;
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
	
	@Override
	public String toString() {
		return "WaresDto [id=" + id + ", publicCode=" + publicCode + ", privateCode=" + privateCode + ", insideCode="
				+ insideCode + ", createTime=" + createTime + ", userName=" + userName + ", scanTime=" + scanTime
				+ ", status=" + status + "]";
	}

}
 