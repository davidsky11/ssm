package com.crm.wechat.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName	Redpack.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:27:31
 * @Version 	V1.0    
 */
public class Redpack implements Serializable {

	private static final long serialVersionUID = -1574978250342745535L;
	private Long hongBaoId;   
    private Date addTime;    //红包发送时间   
    private Integer amount;  //金额   单位分  
    private String billNo;   //订单号  
    private String openid;   //用户  
    private String remark;   //微信返回的内容  
    private String mobile;   //用户输入的手机号  
    private Integer result;  //发送结果  
    
	public Long getHongBaoId() {
		return hongBaoId;
	}
	public Date getAddTime() {
		return addTime;
	}
	public Integer getAmount() {
		return amount;
	}
	public String getBillNo() {
		return billNo;
	}
	public String getOpenid() {
		return openid;
	}
	public String getRemark() {
		return remark;
	}
	public String getMobile() {
		return mobile;
	}
	public Integer getResult() {
		return result;
	}
	public void setHongBaoId(Long hongBaoId) {
		this.hongBaoId = hongBaoId;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public void setBillNo(String billNo) {
		this.billNo = billNo;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
    
}
 