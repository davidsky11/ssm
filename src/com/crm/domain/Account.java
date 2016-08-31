package com.crm.domain;

import java.io.Serializable;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/** 
 * @ClassName	Account.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:33:52
 * @Version 	V1.0    
 */
@ApiModel(value="Account")
public class Account implements Serializable {

	private static final long serialVersionUID = -8488659208672501392L;

	@ApiModelProperty
	private String id;
	
	@ApiModelProperty
	private String username;
	
	@ApiModelProperty
	private String password;
	
	// 用户类型 0-系统，1-商户，2-APP用户
	private int userType;
	
	private String generateName;
	
	@ApiModelProperty(allowableValues="range[1,100]")
	private Integer age;
	
	@ApiModelProperty(allowableValues = "F,M")
	private String sex;
	
	@ApiModelProperty
	private String sessionId;
	
	private String token;
	
	public Account() {}
	
	public Account(String id, String username) {
		this.id = id;
		this.username = username;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		/*if (id.equals("")) {
			id = UUID.randomUUID().toString().replace("-", "");
		}*/
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	public int getUserType() {
		return userType;
	}

	public String getGenerateName() {
		return generateName;
	}

	public void setUserType(int userType) {
		this.userType = userType;
	}

	public void setGenerateName(String generateName) {
		this.generateName = generateName;
	}

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", age=" + age + ", sex="
				+ sex + ", sessionId=" + sessionId + ", token=" + token + "]";
	}

}
 