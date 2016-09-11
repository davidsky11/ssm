package com.crm.domain.dto;

import com.crm.domain.Activity;

/** 
 * @ClassName	Config.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月11日 上午12:44:56
 * @Version 	V1.0    
 */
public class ConfigDto {
	
	private String activityName;  // 活动名称
	private String activityId;  // 活动ID
	private Activity activity;  // 活动
	private int count;  // 产生的编码数目
	private Double money;  // 奖项经费
	
	public String getActivityName() {
		return activityName;
	}
	public String getActivityId() {
		return activityId;
	}
	public Activity getActivity() {
		return activity;
	}
	public Double getMoney() {
		return money;
	}
	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "ConfigDto [activityName=" + activityName + ", activityId=" + activityId + ", activity=" + activity
				+ ", count=" + count + ", money=" + money + "]";
	}
	
}
 