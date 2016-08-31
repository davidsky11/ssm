package com.crm.domain.query;

import java.io.Serializable;
import java.util.Date;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 日志信息Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class LogInfoQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 操作日志
    private String logType; // 类型(操作日志,登录日志)
    private String message; // 消息
    private String exception; // 异常
    private String ipAddress; // IP
    private Date eventDate; // 时间
    private String username; // 用户名
    private Date startTime; // 开始时间
    private Date endTime; // 结束时间
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
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
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
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
    
}