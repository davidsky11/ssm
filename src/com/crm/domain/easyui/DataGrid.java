package com.crm.domain.easyui;

import java.util.List;

/**
 * 后台向前台返回JSON，用于easyui的datagrid
 * 
 * @author zh
 * 
 */
public class DataGrid implements java.io.Serializable {

	private Integer total;	// 总记录数
	private List rows;	// 每行记录
	
	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List getRows() {
		return rows;
	}

	public void setRows(List rows) {
		this.rows = rows;
	}

}
