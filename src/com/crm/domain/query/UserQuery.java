package com.crm.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class UserQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 用户表
    private String username; // 用户名
    private String realname; // 真实姓名
    private String userPass; // 密码
    private String idCardNum; // 身份证号
    private Integer gender; // 性别
    private Date birthday; // 出生日期
    private String cellphoneNumber; // 手机号
    private String telephoneNumber; // 电话号码
    private String email; // 邮箱
    private Date accountExpireTime; // 账户过期时间
    private Date credentialsExpireTime; // 凭证过期时间
    private Integer locked; // 是否锁定
    private Integer editable; // 是否可编辑
    private Integer enabled; // 是否可用
    private Date preLoginTime; // 上次登录时间
    private String preLoginIp; // 上次登录ip
    private Date lastLoginTime; // 最后登录时间
    private String lastLoginIp; // 最后登录ip
    private Date lastLogoutTime; // 最后登出时间
	public String getId() {
		return id;
	}
	public String getUsername() {
		return username;
	}
	public String getRealname() {
		return realname;
	}
	public String getUserPass() {
		return userPass;
	}
	public String getIdCardNum() {
		return idCardNum;
	}
	public Integer getGender() {
		return gender;
	}
	public Date getBirthday() {
		return birthday;
	}
	public String getCellphoneNumber() {
		return cellphoneNumber;
	}
	public String getTelephoneNumber() {
		return telephoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public Date getAccountExpireTime() {
		return accountExpireTime;
	}
	public Date getCredentialsExpireTime() {
		return credentialsExpireTime;
	}
	public Integer getLocked() {
		return locked;
	}
	public Integer getEditable() {
		return editable;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public Date getPreLoginTime() {
		return preLoginTime;
	}
	public String getPreLoginIp() {
		return preLoginIp;
	}
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public Date getLastLogoutTime() {
		return lastLogoutTime;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public void setUserPass(String userPass) {
		this.userPass = userPass;
	}
	public void setIdCardNum(String idCardNum) {
		this.idCardNum = idCardNum;
	}
	public void setGender(Integer gender) {
		this.gender = gender;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setCellphoneNumber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}
	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setAccountExpireTime(Date accountExpireTime) {
		this.accountExpireTime = accountExpireTime;
	}
	public void setCredentialsExpireTime(Date credentialsExpireTime) {
		this.credentialsExpireTime = credentialsExpireTime;
	}
	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	public void setEditable(Integer editable) {
		this.editable = editable;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	public void setPreLoginTime(Date preLoginTime) {
		this.preLoginTime = preLoginTime;
	}
	public void setPreLoginIp(String preLoginIp) {
		this.preLoginIp = preLoginIp;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public void setLastLogoutTime(Date lastLogoutTime) {
		this.lastLogoutTime = lastLogoutTime;
	}
    
}