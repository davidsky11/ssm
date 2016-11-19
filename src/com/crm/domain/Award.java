package com.crm.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName	Award.java
 * @Description 奖项实体
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:23:00
 * @Version 	V1.0    
 */
@ApiModel(value = "奖项")
public class Award {
	
	@ApiModelProperty(value = "奖项编号")
	private String id;  
	@ApiModelProperty(value = "名称")
	private String title;  // 奖项标题
	@ApiModelProperty(value = "编码")
	private String serialNo;  // 奖项编码
	@ApiModelProperty(value = "描述信息")
	private String description;  // 奖项描述
	private Integer hierarchy;  // 奖项层级，例如， 0-特等奖，1-一等奖，2-二等奖，3-三等奖，4-
	private Integer sort;  // 排序
	private Double amount;  // 奖项金额
	private String activityId;  // 活动ID
	private Integer total;  // 总数量
	private Integer remain;  // 剩余数量
	private Activity activity;
	private String activityName;
	public String publisherId; // 发布者
	
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

	public String getActivityId() {
		return activityId;
	}

	public Integer getTotal() {
		return total;
	}

	public Integer getRemain() {
		return remain;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public void setRemain(Integer remain) {
		this.remain = remain;
	}
	public Activity getActivity() {
		return activity;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
		if (activity != null) {
			this.activityName = activity.getTitle();
		}
	}
	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(String publisherId) {
		this.publisherId = publisherId;
	}

	@Override
	public String toString() {
		return "Award [id=" + id + ", title=" + title + ", serialNo=" + serialNo + ", description=" + description
				+ ", hierarchy=" + hierarchy + ", sort=" + sort + ", amount=" + amount + ", activityId=" + activityId
				+ ", total=" + total + ", remain=" + remain + ", activity=" + activity + ", activityName="
				+ activityName + "]";
	}
	
}
 