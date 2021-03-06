package com.crm.domain;

/** 
 * @ClassName	Activity.java
 * @Description 活动实体/抽象商品
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:17:31
 * @Version 	V1.0    
 */
public class Activity {
	
	private String id;
	private String title;  // 活动标题
	private String description;  // 活动描述
	private String content;  // 活动内容
	private String startTime;  // 活动开始时间
	private String endTime;  // 活动结束时间
	private String publisherId;  // 活动的发布者，系统管理员
	private String publisherName;  
	private int enable;  // 活动是否激活
	private String publicCode;  // 公共编码
	private String image;  // 图片
	private Integer count;  // 对应生成的编码的个数
	private Double amount;  // 预算奖项金额
	private String infoUrl;  // 活动对应的内容 H5 url
	private String atyCode;  // 活动编码
	
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPublisherId() {
		return publisherId;
	}
	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}
	public String getDescription() {
		return description;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public int getEnable() {
		return enable;
	}
	public void setEnable(int enable) {
		this.enable = enable;
	}
	public String getPublicCode() {
		return publicCode;
	}
	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Double getAmount() {
		return amount;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Integer getCount() {
		return count;
	}
	
	public String getInfoUrl() {
		return infoUrl;
	}
	public void setInfoUrl(String infoUrl) {
		this.infoUrl = infoUrl;
	}
	
	public String getAtyCode() {
		return atyCode;
	}
	public void setAtyCode(String atyCode) {
		this.atyCode = atyCode;
	}
	@Override
	public String toString() {
		return "Activity [id=" + id + ", title=" + title + ", description=" + description + ", content=" + content
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", publisherId=" + publisherId
				+ ", publisherName=" + publisherName + ", enable=" + enable + ", publicCode=" + publicCode + ", image="
				+ image + ", count=" + count + ", amount=" + amount + "]";
	}
	
}
 