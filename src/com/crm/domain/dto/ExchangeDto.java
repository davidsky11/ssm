package com.crm.domain.dto;

import java.util.Date;

import com.crm.domain.Exchange;

/** 
 * @ClassName	ExchangeDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月4日 下午1:26:39
 * @Version 	V1.0    
 */
public class ExchangeDto {

	private String username;
	private String awardName;
	private String awardDesc;
	private String awardAmount;
	private Date exchangeTime;
	private String publicCode;
	private String privateCode;
	private String insideCode;
	
	public ExchangeDto(Exchange e) {
		if (e != null) {
			this.exchangeTime = e.getExchangeTime();
			this.publicCode = e.getPublicCode();
			this.privateCode = e.getPrivateCode();
			this.insideCode = e.getInsideCode();
			
			if (e.getUser() != null) {
				this.username = e.getUser().getUsername();
			}
			
			if (e.getAward() != null) {
				this.awardName = e.getAward().getTitle();
				this.awardDesc = e.getAward().getDescription();
				this.awardAmount = e.getAward().getAmount() + "";
			}
		}
	}
	
	public String getUsername() {
		return username;
	}
	public String getAwardName() {
		return awardName;
	}
	public String getAwardDesc() {
		return awardDesc;
	}
	public String getAwardAmount() {
		return awardAmount;
	}
	public Date getExchangeTime() {
		return exchangeTime;
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
	public void setUsername(String username) {
		this.username = username;
	}
	public void setAwardName(String awardName) {
		this.awardName = awardName;
	}
	public void setAwardDesc(String awardDesc) {
		this.awardDesc = awardDesc;
	}
	public void setAwardAmount(String awardAmount) {
		this.awardAmount = awardAmount;
	}
	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
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
	
	@Override
	public String toString() {
		return "ExchangeDto [username=" + username + ", awardName=" + awardName + ", awardDesc=" + awardDesc
				+ ", awardAmount=" + awardAmount + ", exchangeTime=" + exchangeTime + ", publicCode=" + publicCode
				+ ", privateCode=" + privateCode + ", insideCode=" + insideCode + "]";
	}
	
}
 