package com.crm.common.bean;

import java.io.Serializable;

import com.crm.common.util.lang.StringUtil;

/**
 * 分页bean
 */
public class PageBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private int totalPages; // 总页数
	private int totalRecords; // 总记录数
	private int pageSize; // 每页数据
	private int page; // 当前页数
	private int start; // 开始记录
	private int limit; // 截止记录
	private int end; // 结束记录
	public static final int DEFAULT_PAGESIZE = 25;
	/**
	 * 大写的ASC or DESC
	 */
	private String dir;
	/**
	 * 排序的字段
	 */
	private String sort;
	private String actionURI; // 当前action的URI

	{
		totalRecords = 1;
		/*
		 * pageSize = Integer.parseInt(PropsUtil.getValue("psize", "system.properties"));
		 */
		pageSize = DEFAULT_PAGESIZE;
		totalPages = 1;
		page = 1;
		start = 0;
		end = 0;
	}

	public PageBean() {}

	public PageBean(final int startNum, final int limitNum) {
		this.start = startNum;
		this.limit = limitNum;
		// this.pageSize = limit;
	}

	public PageBean(final int total, final String pageindex) {
		computePage(total, pageSize);
		if (!StringUtil.isNullOrEmpty(pageindex)) {
			page = Integer.parseInt(pageindex);
		}
		if (page < 1) {
			page = 1;
		} else if (page > totalPages) {
			page = totalPages;
		}
	}

	public PageBean(final int total, final String pageindex, final String url) {
		computePage(total, pageSize);
		if (!StringUtil.isNullOrEmpty(pageindex)) {
			page = Integer.parseInt(pageindex);
		}
		if (page < 1) {
			page = 1;
		} else if (page > totalPages) {
			page = totalPages;
		}
		actionURI = url;
	}

	/**
	 * 计算页数
	 */
	public final void computePage(final int total, final int size) {
		totalRecords = total;
		pageSize = size;
		totalPages = (totalRecords % pageSize) == 0 ? (totalRecords / pageSize) : (totalRecords / pageSize + 1);
	}

	public int getTotalPages() {
		return totalPages;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getPage() {
		return page;
	}

	public int getStart() {
		return start;
	}

	public int getLimit() {
		return limit;
	}

	public int getEnd() {
		return end;
	}

	public String getDir() {
		return dir;
	}

	public String getSort() {
		return sort;
	}

	public String getActionURI() {
		return actionURI;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public void setTotalRecords(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setActionURI(String actionURI) {
		this.actionURI = actionURI;
	}
	
}
