package com.crm.common.bean;

import java.io.Serializable;

/**
 * 基本复选树节点
 */
public class BaseTreeNodeCheckBean extends BaseTreeNodeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private boolean checked;

	public final boolean isChecked() {
		return checked;
	}

	public final void setChecked(final boolean check) {
		this.checked = check;
	}

}
