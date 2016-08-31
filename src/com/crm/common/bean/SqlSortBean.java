package com.crm.common.bean;

import java.io.Serializable;

/**
 * 排序
 */
public class SqlSortBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String property; // 排序字段
	private String direction; // 排序方式 大写的ASC or DESC
	public String getProperty() {
		return property;
	}
	public String getDirection() {
		return direction;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	
}
