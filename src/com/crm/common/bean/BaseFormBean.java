package com.crm.common.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

/**
 * 基本form属性
 */
public abstract class BaseFormBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id = UUID.randomUUID().toString();
	private Integer priority = 1; // 优先级
	private Integer editable = 1; // 是否可编辑
	private Integer available = 1; // 是否可用
	private Date createDataTime = new Date(); // 数据创建时间
	private String createDataUsername; // 创建者username
	private Date updateDataTime = new Date(); // 数据修改时间
	private String updateDataUsername; // 修改者username
	
	public String getId() {
		return id;
	}
	public Integer getPriority() {
		return priority;
	}
	public Integer getEditable() {
		return editable;
	}
	public Integer getAvailable() {
		return available;
	}
	public Date getCreateDataTime() {
		return createDataTime;
	}
	public String getCreateDataUsername() {
		return createDataUsername;
	}
	public Date getUpdateDataTime() {
		return updateDataTime;
	}
	public String getUpdateDataUsername() {
		return updateDataUsername;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public void setEditable(Integer editable) {
		this.editable = editable;
	}
	public void setAvailable(Integer available) {
		this.available = available;
	}
	public void setCreateDataTime(Date createDataTime) {
		this.createDataTime = createDataTime;
	}
	public void setCreateDataUsername(String createDataUsername) {
		this.createDataUsername = createDataUsername;
	}
	public void setUpdateDataTime(Date updateDataTime) {
		this.updateDataTime = updateDataTime;
	}
	public void setUpdateDataUsername(String updateDataUsername) {
		this.updateDataUsername = updateDataUsername;
	}
	
}
