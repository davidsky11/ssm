package com.crm.common.bean;

import java.io.Serializable;

/**
 * 分组
 */
public class SqlGroupBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String property; // 分组字段
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	
}
