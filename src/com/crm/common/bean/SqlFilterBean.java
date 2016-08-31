package com.crm.common.bean;

import java.io.Serializable;

/**
 * 过滤条件
 */
public class SqlFilterBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String property; // 属性
	private String operator; // 操作方式
	private Object value; // 值
	
	public String getProperty() {
		return property;
	}
	public String getOperator() {
		return operator;
	}
	public Object getValue() {
		return value;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
}
