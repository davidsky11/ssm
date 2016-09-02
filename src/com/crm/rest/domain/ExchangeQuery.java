package com.crm.rest.domain;

import com.crm.domain.Award;
import com.crm.domain.Exchange;

/** 
 * @ClassName	ExchangeQuery.java
 * @Description 兑奖信息
 * @Author		kevin 
 * @CreateTime  2016年8月7日 下午3:28:28
 * @Version 	V1.0    
 */
public class ExchangeQuery extends Exchange {
	
	private String title;  // 奖项信息
	private Award award;  // 奖品信息
	
	public ExchangeQuery(Exchange exchange) {
		this.setId(exchange.getId());
		this.setUserId(exchange.getUserId());
		this.setExchangeTime(exchange.getExchangeTime());
		this.setFlagCode(exchange.getFlagCode());
		this.setLatitude(exchange.getLatitude());
		this.setLongitude(exchange.getLongitude());
		this.setWaresId(exchange.getWaresId());
		this.setId(exchange.getId());
		this.setPublicCode(exchange.getPublicCode());
		this.setPrivateCode(exchange.getPrivateCode());
		this.setInsideCode(exchange.getInsideCode());
	}
	
	public String getTitle() {
		return title;
	}
	public Award getAward() {
		return award;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setAward(Award award) {
		this.award = award;
	}
	
	@Override
	public String toString() {
		return "ExchangeQuery [title=" + title + ", award=" + award + "]";
	}
	
}
 