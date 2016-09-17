package com.crm.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	Page.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月16日 上午1:32:49
 * @Version 	V1.0    
 */
public class Page<T> extends PageHelper implements Serializable {

	private static final long serialVersionUID = -4103166464275833773L;

	private Integer total;			// 共记录条数
	private List<T> content = new ArrayList<T>();
	
	public Integer getTotal() {
		return total;
	}
	public List<T> getContent() {
		return content;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public Integer getPageSize() {
		Integer pageSize = 0;
		if (this.getTotal() % this.getRows() == 0) {
			pageSize = this.getTotal() / this.getRows();
		} else {
			pageSize = this.getTotal() / this.getRows() + 1;
		}
		return pageSize;
	}
	
	public Boolean hasPrevious() {
		if (this.getPage() > 0)
			return true;
		else 
			return false;
	}
	
	public Boolean hasNext() {
		if (this.getTotal() > this.getRows() * this.getPage()) {
			return true;
		} else {
			return false;
		}
	}
	
}
 