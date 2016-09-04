/**
 * 
 */
package com.crm.domain;

/**
 * @author zh
 * 2014-8-1
 */
public class User {
	private String id;
	private String username;
	private String password;
	private String email;
	
	private String idcard;
	private String gender;
	private String qq;
	private String weixin;
	private String regtime;
	
	private String sysname;
	private String psysname;
	
	private String userType;
	
	private String generateName;
	
	private String token;
	
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
	public String getRegtime() {
		return regtime;
	}
	public void setRegtime(String regtime) {
		this.regtime = regtime;
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

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", idcard="
				+ idcard + ", gender=" + gender + ", qq=" + qq + ", weixin=" + weixin + ", regtime=" + regtime
				+ ", sysname=" + sysname + ", psysname=" + psysname + ", userType=" + userType + ", generateName="
				+ generateName + ", token=" + token + "]";
	}
	
}
