package com.crm.domain;

import java.util.Date;

/** 
 * @ClassName	Product.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月7日 上午2:18:50
 * @Version 	V1.0    
 */
public class Wares {
	
	private String id;
	private String name;  // 商品名称
	private String creater;  // 创建者
	private String publicCode;   // 公共编码
	private String privateCode;  // 瓶身码
	private String insideCodeTmp;  // 瓶身内码（实验用）
	private String insideCode;  // 瓶盖码
	private String status;  // 兑奖状态
	private Date createTime;  // 创建时间
	private String awardId; // 对应的奖项ID，默认为无奖（空值）
	private Award award;
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCreater() {
		return creater;
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
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCreater(String creater) {
		this.creater = creater;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAwardId() {
		return awardId;
	}
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public Award getAward() {
		return award;
	}
	public void setAward(Award award) {
		this.award = award;
	}
	public String getInsideCodeTmp() {
		return insideCodeTmp;
	}
	public void setInsideCodeTmp(String insideCodeTmp) {
		this.insideCodeTmp = insideCodeTmp;
	}
	
	@Override
	public String toString() {
		return "Wares [id=" + id + ", name=" + name + ", creater=" + creater + ", publicCode=" + publicCode
				+ ", privateCode=" + privateCode + ", insideCode=" + insideCode + ", status=" + status + ", createTime="
				+ createTime + ", awardId=" + awardId + ", award=" + award + "]";
	}
	
}
 