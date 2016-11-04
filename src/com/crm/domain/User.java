/**
 * 
 */
package com.crm.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * @author zh
 * 2014-8-1
 */
@ApiModel(value = "用户对象")
public class User {
	
	@ApiModelProperty(value = "用户编码")
	private String id;
	@ApiModelProperty(value = "用户名")
	private String username;
	@ApiModelProperty(value = "密码")
	private String password;
	@ApiModelProperty(value = "电子邮箱")
	private String email;
	@ApiModelProperty(value = "身份证号码")
	private String idcard;
	@ApiModelProperty(value = "性别")
	private String gender;
	@ApiModelProperty(value = "QQ号")
	private String qq;
	@ApiModelProperty(value = "微信号")
	private String weixin;
	@ApiModelProperty(value = "注册时间")
	private Date regTime;
	private String sysname;
	private String psysname;
	@ApiModelProperty(value = "用户类型")
	private String userType;
	@ApiModelProperty(value = "自动生成的用户名")
	private String generateName;
	@ApiModelProperty(value = "token值")
	private String token;
	@ApiModelProperty(value = "别名")
	private String userAlias;
	private Date loginTime;
	private Date lastLoginTime;
	private Role role;  // 根据userType来获取
	private String roleName;
	private String creatorId;
	private String creatorName;
	@ApiModelProperty(value = "是否锁定")
	private Integer locked = 0; // 是否锁定
	
	private String address;  // 用户地址
	private String wxOpenId;  // 微信用户对应的openid
	private String merchant;  // 商户名
	private Integer loginFrequency;  // 用户登录的频次
	private String telephone;  // 手机号码
	private String flagCode;  // 设备码
	
	/**
	 * 第三方登录需要
	 */
	private String thirdType;  // 第三方登陆类型
	private String thirdOpenid;  // 第三方登录openid
	
	List<SysMenu> menuList = new ArrayList<SysMenu>();
	
	public User() {}
	
	public User(String id, String username) {
		super();
		this.id = id;
		this.username = username;
	}
	
	public String getSysname() {
		return sysname;
	}
	public void setSysname(String sysname) {
		this.sysname = sysname;
	}
	public String getPsysname() {
		return psysname;
	}
	public void setPsysname(String psysname) {
		this.psysname = psysname;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getWeixin() {
		return weixin;
	}
	public void setWeixin(String weixin) {
		this.weixin = weixin;
	}
	public Date getRegTime() {
		return regTime;
	}
	public void setRegTime(Date regTime) {
		this.regTime = regTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserType() {
		return userType;
	}

	public String getGenerateName() {
		return generateName;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public void setGenerateName(String generateName) {
		this.generateName = generateName;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
	public List<SysMenu> getMenuList() {
		return menuList;
	}
	public void setMenuList(List<SysMenu> menuList) {
		this.menuList = menuList;
	}
	public String getUserAlias() {
		return userAlias;
	}
	public void setUserAlias(String userAlias) {
		this.userAlias = userAlias;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}
	
	public String getWxOpenId() {
		return wxOpenId;
	}

	public String getMerchant() {
		return merchant;
	}

	public Integer getLoginFrequency() {
		return loginFrequency;
	}

	public void setWxOpenId(String wxOpenId) {
		this.wxOpenId = wxOpenId;
	}

	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}

	public void setLoginFrequency(Integer loginFrequency) {
		this.loginFrequency = loginFrequency;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getThirdType() {
		return thirdType;
	}

	public String getThirdOpenid() {
		return thirdOpenid;
	}

	public void setThirdType(String thirdType) {
		this.thirdType = thirdType;
	}

	public void setThirdOpenid(String thirdOpenid) {
		this.thirdOpenid = thirdOpenid;
	}
	
	public String getFlagCode() {
		return flagCode;
	}

	public void setFlagCode(String flagCode) {
		this.flagCode = flagCode;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", idcard="
				+ idcard + ", gender=" + gender + ", qq=" + qq + ", weixin=" + weixin + ", regTime=" + regTime
				+ ", sysname=" + sysname + ", psysname=" + psysname + ", userType=" + userType + ", generateName="
				+ generateName + ", token=" + token + ", userAlias=" + userAlias + ", loginTime=" + loginTime
				+ ", lastLoginTime=" + lastLoginTime + ", role=" + role + ", roleName=" + roleName + ", creatorId="
				+ creatorId + ", creatorName=" + creatorName + ", locked=" + locked + "]";
	}
	
}
