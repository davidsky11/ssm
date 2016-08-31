package com.crm.rest.domain;

import com.crm.domain.Award;

/** 
 * @ClassName	AwardTotal.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月7日 下午3:27:49
 * @Version 	V1.0    
 */
public class AwardTotal {

	private String award;  // 奖项名称
	private Award detail;  // 奖项详情
	private int total;  // 奖项总个数
	private int charge;  // 已消费奖项个数
	
	public String getAward() {
		return award;
	}
	public Award getDetail() {
		return detail;
	}
	public int getTotal() {
		return total;
	}
	public int getCharge() {
		return charge;
	}
	public void setAward(String award) {
		this.award = award;
	}
	public void setDetail(Award detail) {
		this.detail = detail;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public void setCharge(int charge) {
		this.charge = charge;
	}
	
	@Override
	public String toString() {
		return "AwardTotal [award=" + award + ", detail=" + detail + ", total=" + total + ", charge=" + charge + "]";
	}
}
 