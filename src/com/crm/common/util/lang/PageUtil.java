package com.crm.common.util.lang;

import java.util.List;

/**
 * 
 * 传统分页工具类
 *
 */
public class PageUtil {
	// 每页有多少条记录
	private int pageSize;
	// 当前页码数
	private int pageNum;
	// 数据库总记录数
	private int rowsCount;
	// 从多少条记录开始查询
	private int rowStart;
	// 总共有多少页
	private int pageCount;
	// 每页可以显示的页码数
	private int everyPageCount;
	// 每页从多少页码开始
	private int everyPageStart;
	// 每页从多少页码结束
	private int everyPageEnd;
	// 判断 是否有上一页
	private boolean hasPrevious = false;
	private int firstPageNum = 1;
	private int previousPageNum;
	// 判断 是否有下一页
	private boolean hasNext = false;
	private int nextPageNum;
	private int lastPageNum;

	// 页面数据提交路径
	private String linkAddress;
	// 查询出来的记录
	private List list;

	public PageUtil() {

	}

	/**
	 * 创建分页对象
	 * 
	 * @param pageNumString
	 * @param pageSizeString
	 * @param rowsCount
	 */
	public PageUtil(String pageNumString, String pageSizeString, int rowsCount, String type, String linkAddress) {
		// 每页有多少条记录
		if (type.equals("liveshow")) {
			pageSize = pageSizeString == null ? 10 : Integer.parseInt(pageSizeString);
		}
		// 数据库总记录数
		this.rowsCount = rowsCount;
		// 总共页码数
		pageCount = (int) Math.ceil(rowsCount * 1.0 / pageSize);
		// 当前页码数
		pageNum = (pageNumString == null || pageNumString.equals("")) ? 1 : Integer.parseInt(pageNumString);
		if (pageNum > pageCount) {
			pageNum = pageCount;
		}
		if (pageNum == 0) {
			pageNum = 1;
		}
		// 从多少条记录开始查询
		this.rowStart = (pageNum - 1) * pageSize;
		// 每页可以显示的页码数 默认为6
		everyPageCount = 6;
		// 每页从多少页码开始
		everyPageStart = (pageNum - (everyPageCount / 2)) <= 0 ? 1 : (pageNum - (everyPageCount / 2));
		// 每页从多少页码结束
		everyPageEnd = pageNum + (everyPageCount / 2 - 1) >= pageCount ? pageCount : pageNum + (everyPageCount / 2 - 1);
		// 是否有上一页
		if (this.pageNum > 1) {
			this.hasPrevious = true;
			this.previousPageNum = this.pageNum - 1;
			this.firstPageNum = 1;
		}
		// 是否有下一页
		if (this.pageNum < this.pageCount) {
			this.hasNext = true;
			this.nextPageNum = this.pageNum + 1;
			this.lastPageNum = this.pageCount;
		}
		// 连接路径
		this.linkAddress = linkAddress;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getRowsCount() {
		return rowsCount;
	}

	public void setRowsCount(int rowsCount) {
		this.rowsCount = rowsCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getEveryPageCount() {
		return everyPageCount;
	}

	public void setEveryPageCount(int everyPageCount) {
		this.everyPageCount = everyPageCount;
	}

	public int getEveryPageStart() {
		return everyPageStart;
	}

	public void setEveryPageStart(int everyPageStart) {
		this.everyPageStart = everyPageStart;
	}

	public int getEveryPageEnd() {
		return everyPageEnd;
	}

	public void setEveryPageEnd(int everyPageEnd) {
		this.everyPageEnd = everyPageEnd;
	}

	public int getRowStart() {
		return rowStart;
	}

	public void setRowStart(int rowStart) {
		this.rowStart = rowStart;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public int getFirstPageNum() {
		return firstPageNum;
	}

	public void setFirstPageNum(int firstPageNum) {
		this.firstPageNum = firstPageNum;
	}

	public int getPreviousPageNum() {
		return previousPageNum;
	}

	public void setPreviousPageNum(int previousPageNum) {
		this.previousPageNum = previousPageNum;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public int getNextPageNum() {
		return nextPageNum;
	}

	public void setNextPageNum(int nextPageNum) {
		this.nextPageNum = nextPageNum;
	}

	public int getLastPageNum() {
		return lastPageNum;
	}

	public void setLastPageNum(int lastPageNum) {
		this.lastPageNum = lastPageNum;
	}

	public String getLinkAddress() {
		return linkAddress;
	}

	public void setLinkAddress(String linkAddress) {
		this.linkAddress = linkAddress;
	}

}
