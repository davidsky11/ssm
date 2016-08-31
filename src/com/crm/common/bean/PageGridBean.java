package com.crm.common.bean;

import java.io.Serializable;
import java.util.List;
import com.github.pagehelper.Page;

/**
 * 分页表格bean
 */
public class PageGridBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final boolean SUCCESS = true;
	public static final boolean FAULT = false;
	private boolean success;
	private long totalPages; // 总页数
	private long totalRecords; // 总记录数
	private int draw; // dataTable用 ，接收和返回必须一样
	private long recordsTotal; // dataTable用
	private long recordsFiltered; // dataTable用
	private long pageSize; // 每页数据
	private long page; // 当前页数
	private long start; // 开始记录数
	private long end; // 结束记录数
	@SuppressWarnings("rawtypes")
	private List records;

	public PageGridBean() {
		this.success = true;
	}

	public PageGridBean(final boolean suc) {
		this.success = suc;
	}

	/**
	 * mybatis分页插件,不加pageHelper参数 会被List那个方法覆盖
	 */
	public PageGridBean(final Page<?> pageParm, boolean pageHelper) {
		this.success = true;
		this.records = pageParm.getResult();
		this.totalRecords = pageParm.getTotal();
		this.recordsTotal = pageParm.getTotal(); // dataTable用
		this.recordsFiltered = pageParm.getTotal(); // dataTable用
		this.pageSize = pageParm.getPageSize();
		this.totalPages = pageParm.getPages();
		this.page = pageParm.getPageNum();
		this.start = pageParm.getStartRow();
		this.end = pageParm.getEndRow();
	}

	/**
	 * mybatis分页插件
	 */
	public PageGridBean(final BaseQueryBean query, final Page<?> pageParm, boolean pageHelper) {
		this(pageParm, pageHelper);
		this.draw = query.getDraw();
	}

	public PageGridBean(final List<?> recordsList) {
		this.success = true;
		this.records = recordsList;
	}

	/**
	 * dataTable用
	 */
	public PageGridBean(final BaseQueryBean query, final List<?> recordsList) {
		this.draw = query.getDraw();
		this.success = true;
		this.records = recordsList;
	}

	public PageGridBean(final long totalRecordsNum, final List<?> recordsList) {
		this(recordsList);
		this.totalRecords = totalRecordsNum;
		this.recordsTotal = totalRecordsNum; // dataTable用
		this.recordsFiltered = totalRecordsNum; // dataTable用
	}

	/**
	 * dataTable用
	 */
	public PageGridBean(final BaseQueryBean query, final long totalRecordsNum, final List<?> recordsList) {
		this(query, recordsList);
		this.totalRecords = totalRecordsNum;
		this.recordsTotal = totalRecordsNum; // dataTable用
		this.recordsFiltered = totalRecordsNum; // dataTable用
	}

	public PageGridBean(final long totalRecordsNum, final long page, final List<?> recordsList) {
		this(totalRecordsNum, recordsList);
		this.page = page;
	}

	public PageGridBean(final long totalRecordsNum, final long page, final long pageSizeParm, final List<?> recordsList) {
		this(totalRecordsNum, page, recordsList);
		this.pageSize = pageSizeParm;
		this.totalPages = (totalRecordsNum % pageSizeParm) == 0 ? (totalRecordsNum / pageSizeParm) : (totalRecordsNum / pageSizeParm + 1);
	}

}
