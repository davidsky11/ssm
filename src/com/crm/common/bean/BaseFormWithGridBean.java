package com.crm.common.bean;

import java.io.Serializable;

/**
 * 基本带批量提交的表单
 */
public abstract class BaseFormWithGridBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String romovedIds;
	private String mdifiedObjs;
	private String newObjs;
	
	public String getRomovedIds() {
		return romovedIds;
	}
	public String getMdifiedObjs() {
		return mdifiedObjs;
	}
	public String getNewObjs() {
		return newObjs;
	}
	public void setRomovedIds(String romovedIds) {
		this.romovedIds = romovedIds;
	}
	public void setMdifiedObjs(String mdifiedObjs) {
		this.mdifiedObjs = mdifiedObjs;
	}
	public void setNewObjs(String newObjs) {
		this.newObjs = newObjs;
	}
	
}
