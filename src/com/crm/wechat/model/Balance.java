package com.crm.wechat.model;

import java.io.Serializable;

/** 
 * @ClassName	Balance.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:28:34
 * @Version 	V1.0    
 */
public class Balance implements Serializable {

	private static final long serialVersionUID = 5255636143734884287L;
	private Long redpackId;   
    private Integer total;     //红包总金额  单位分  
    private Integer balance;   //红包余额 单位分  
    private Integer send;      //已经发送红包金额 单位分 
    
	public Long getRedpackId() {
		return redpackId;
	}
	public Integer getTotal() {
		return total;
	}
	public Integer getBalance() {
		return balance;
	}
	public Integer getSend() {
		return send;
	}
	public void setRedpackId(Long redpackId) {
		this.redpackId = redpackId;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public void setSend(Integer send) {
		this.send = send;
	}
    
    
}
 