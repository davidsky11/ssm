package com.crm.authorization.model;

import java.io.Serializable;
import java.util.Date;

/** 
 * @ClassName	ValidModel.java
 * @Description 验证码缓存
 * @Author		kevin 
 * @CreateTime  2016年8月25日 下午9:17:25
 * @Version 	V1.0    
 */
public class ValidModel implements Serializable, Cloneable {

	private static final long serialVersionUID = -7942585295880386748L;

	// 手机号码
	private String phone;
	
	// 验证码
	private String validCode;
	
	// 创建时间
	private Date createTime;
	
	// 超期时间
	private Date expireTime;

	public String getPhone() {
		return phone;
	}

	public String getValidCode() {
		return validCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getExpireTime() {
		return expireTime;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setValidCode(String validCode) {
		this.validCode = validCode;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@Override
	public ValidModel clone() throws CloneNotSupportedException {
		ValidModel model = (ValidModel) super.clone();
		model.createTime = (Date) this.createTime.clone();
		model.expireTime = (Date) this.expireTime.clone();
		return model;
	}
	
}
 