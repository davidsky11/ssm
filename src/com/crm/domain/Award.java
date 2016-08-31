package com.crm.domain; 

/** 
 * @ClassName	Award.java
 * @Description 奖项实体
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:23:00
 * @Version 	V1.0    
 */
public class Award {
	
	private String id;  
	private String title;  // 奖项标题
	private String serialNo;  // 奖项编码
	private String description;  // 奖项描述
	private Integer hierarchy;  // 奖项层级，例如， 0-特等奖，1-一等奖，2-二等奖，3-三等奖，4-
	private Integer sort;  // 排序
	private Double amount;  // 奖项金额
	private String publicCode;  // 公共编码
	
	public Award() {
		
	}
	
	public Award(String title, String serialNo, String description, Integer hierarchy, Integer sort, Double amount) {
		super();
		this.title = title;
		this.serialNo = serialNo;
		this.description = description;
		this.hierarchy = hierarchy;
		this.sort = sort;
		this.amount = amount;
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public String getDescription() {
		return description;
	}

	public Integer getHierarchy() {
		return hierarchy;
	}

	public Integer getSort() {
		return sort;
	}

	public Double getAmount() {
		return amount;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setHierarchy(Integer hierarchy) {
		this.hierarchy = hierarchy;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}
	
	public String getPublicCode() {
		return publicCode;
	}

	public void setPublicCode(String publicCode) {
		this.publicCode = publicCode;
	}

	@Override
	public String toString() {
		return "Award [id=" + id + ", title=" + title + ", serialNo=" + serialNo + ", description=" + description
				+ ", hierarchy=" + hierarchy + ", sort=" + sort + ", amount=" + amount + ", publicCode=" + publicCode
				+ "]";
	}
	
}
 