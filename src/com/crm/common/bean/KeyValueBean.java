package com.crm.common.bean;

import java.io.Serializable;

/**
 * 简单keyValue对象
 */
public class KeyValueBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String key;
	private Object value;

	public KeyValueBean(final String k, final Object v) {
		this.key = k;
		this.value = v;
	}

	/**
	 * 取值
	 */
	public final Object getValueByKey(final String k) {
		return this.value;
	}

	public String getKey() {
		return key;
	}

	public Object getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(Object value) {
		this.value = value;
	}
	
}
