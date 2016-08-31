package com.crm.domain; 

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
	private Double realPrice;  // 实际价格
	private String creater;  // 创建者
	private String publicCode;   // 公共编码
	private String privateCode;  // 瓶身码
	private String insideCode;  // 瓶盖码
	private String awardId;  // 对应的奖项
	private String number;  // 商品编号
	private String batch;  // 商品批次
	private String createTime;  // 创建时间
	private String image;  // 对应的图片
	private Double sellPrice;  // 零售价格
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Double getRealPrice() {
		return realPrice;
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
	public String getAwardId() {
		return awardId;
	}
	public String getNumber() {
		return number;
	}
	public String getBatch() {
		return batch;
	}
	public String getCreateTime() {
		return createTime;
	}
	public String getImage() {
		return image;
	}
	public Double getSellPrice() {
		return sellPrice;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setRealPrice(Double realPrice) {
		this.realPrice = realPrice;
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
	public void setAwardId(String awardId) {
		this.awardId = awardId;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setSellPrice(Double sellPrice) {
		this.sellPrice = sellPrice;
	}
	
	@Override
	public String toString() {
		return "Wares [id=" + id + ", name=" + name + ", realPrice=" + realPrice + ", creater=" + creater
				+ ", publicCode=" + publicCode + ", privateCode=" + privateCode + ", insideCode=" + insideCode
				+ ", awardId=" + awardId + ", number=" + number + ", batch=" + batch + ", createTime=" + createTime
				+ ", image=" + image + ", sellPrice=" + sellPrice + "]";
	}
	
}
 