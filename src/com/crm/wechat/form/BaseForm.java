package com.crm.wechat.form;

/**
 * @ClassName:		BaseForm
 * @Description:	基础参数
 * @Author:    		kevin
 * @CreateDte:		2016年7月14日 上午1:40:55
 *
 */
public class BaseForm {
	//第几页
	private Integer index = 1;
	//一页的大小
	private Integer pageSize = 15;
	
	
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
}
