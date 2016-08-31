package com.crm.domain;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 日志信息
 */
@Data
public class LogInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 操作日志
    private String logType; // 类型(操作日志,登录日志)
    private String message; // 消息
    private String exception; // 异常
    private String ipAddress; // IP
    private Date eventDate; // 时间
    private String username; // 用户名
	public String getId() {
		return id;
	}
	public String getLogType() {
		return logType;
	}
	public String getMessage() {
		return message;
	}
	public String getException() {
		return exception;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public Date getEventDate() {
		return eventDate;
	}
	public String getUsername() {
		return username;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setLogType(String logType) {
		this.logType = logType;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setException(String exception) {
		this.exception = exception;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}
	public void setUsername(String username) {
		this.username = username;
	}
    
}