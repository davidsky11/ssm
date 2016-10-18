package com.crm.domain.dto; 

/** 
 * @ClassName	SrDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年10月19日 上午12:11:56
 * @Version 	V1.0    
 */
public class SrDto {
	
	private String userId;
	private Integer count;
	private String waresId;
	private String publicCode;
	private String privateCode;
	private String insideCode;
	
	public String getUserId() {
		return userId;
	}
	public Integer getCount() {
		return count;
	}
	public String getWaresId() {
		return waresId;
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
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setWaresId(String waresId) {
		this.waresId = waresId;
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
		return "SrDto [userId=" + userId + ", count=" + count + ", waresId=" + waresId + ", publicCode=" + publicCode
				+ ", privateCode=" + privateCode + ", insideCode=" + insideCode + "]";
	}
	
}
 