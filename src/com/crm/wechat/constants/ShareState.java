package com.crm.wechat.constants;

/**
 * @ClassName: ShareState
 * @Description: 微信分享state
 * @Author: kevin
 * @CreateDte: 2016年7月14日 上午1:39:44
 *
 */
public enum ShareState {
	LUCKYMONEY("luckymoney");

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	ShareState(String desc) {
		this.desc = desc;
	}
}
