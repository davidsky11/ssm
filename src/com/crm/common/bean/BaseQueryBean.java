package com.crm.common.bean;

import java.io.Serializable;
import java.util.List;

import com.crm.common.util.io.JsonUtil;
import com.crm.common.util.lang.StringUtil;


/**
 * 基本查询属性
 */
public abstract class BaseQueryBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer draw = 1; // dataTable用,保证异步请求的返回值是同一次请求的
	private String queryType;
	private Integer exportType;
	private String simpleQueryParam;
	private Boolean queryByPage = false;
	private Long start = 0L; // 开始记录
	private Long length = 20L; // 数量, datatTable用
	private Long limit = 20L; // 数量
	private Long end; // 结束记录
	private Long page = 1L;  // 当前页数
	private String requestUrl; // 当前请求路径
	private List<SqlSortBean> sorters;
	private String sortCondition; // 拼接好的排序sql
	private List<SqlGroupBean> groupers;
	private String groupCondition; // 拼接好的分组sql
	private List<SqlFilterBean> filters;
	private String filterCondition; // 拼接好的过滤条件sql

	public void setStart(Long startParm) {
		if (startParm != null && startParm > 0) {
			this.start = startParm;
			this.page = start / limit + 1;
		}
	}

	public void setLength(Long lengthParm) {
		if (lengthParm != null && lengthParm > 0) {
			this.length = lengthParm;
			this.limit = lengthParm;
		}
	}

	public Long getStart() {
		if (start != null && start > 0) {
			return start;
		} else if (page != null) {
			return (page - 1) * limit;
		} else {
			return start;
		}
	}

	/**
	 * 设置排序字段
	 */
	public final void setSort(final Object sortStr) {
		sorters = JsonUtil.json2List(sortStr.toString(), SqlSortBean.class);
		SqlSortBean sort;
		if (sorters != null && sorters.size() > 0) {
			sortCondition = "";
			for (int i = 0, length = sorters.size(); i < length; i++) {
				sort = sorters.get(length - i - 1);
				if (i == 0) {
					sortCondition += sort.getProperty() + " " + sort.getDirection() + " ";
				} else {
					sortCondition += ", " + sort.getProperty() + " " + sort.getDirection() + " ";
				}
			}
		}
	}

	/**
	 * 设置分组字段
	 */
	public void setGroup(final Object groupStr) {
		groupers = JsonUtil.json2List(groupStr.toString(), SqlGroupBean.class);
	}

	/**
	 * 设置过滤条件
	 */
	public void setFilter(final Object filterStr) {
		filters = JsonUtil.json2List(filterStr.toString(), SqlFilterBean.class);
		SqlFilterBean filter;
		if (filters != null && filters.size() > 0) {
			filterCondition = "";
			for (int i = 0, length = filters.size(); i < length; i++) {
				filter = filters.get(length - i - 1);
				String operator = filter.getOperator();
				String value = filter.getValue().toString();
				if ("in".equals(filter.getOperator())) {
					if (!StringUtil.isNullOrBlank(value)) {
						value = "(" + value.substring(1, value.length() - 1) + ")";
					}
				} else if ("lt".equals(filter.getOperator())) {
					operator = "<";
					value = "'" + value + "'";
				} else if ("gt".equals(filter.getOperator())) {
					operator = ">";
					value = "'" + value + "'";
				} else if ("eq".equals(filter.getOperator())) {
					operator = "=";
					value = "'" + value + "'";
				} else if ("like".equals(filter.getOperator())) {
					if (value != null) {
						value = "'%" + value + "%'";
					}
				}
				if (i == 0) {
					filterCondition += filter.getProperty() + " " + operator + " " + value + " ";
				} else {
					filterCondition += " AND " + filter.getProperty() + " " + operator + " " + value + " ";
				}
			}
		}
	}

	public void setSorters(final List<SqlSortBean> sortersParm) {
		this.sorters = sortersParm;
	}

	public void setGroupers(final List<SqlGroupBean> groupersParm) {
		this.groupers = groupersParm;
	}

	public void setFilters(List<SqlFilterBean> filters) {
		this.filters = filters;
	}

	public Integer getDraw() {
		return draw;
	}

	public String getQueryType() {
		return queryType;
	}

	public Integer getExportType() {
		return exportType;
	}

	public String getSimpleQueryParam() {
		return simpleQueryParam;
	}

	public Boolean getQueryByPage() {
		return queryByPage;
	}

	public Long getLength() {
		return length;
	}

	public Long getLimit() {
		return limit;
	}

	public Long getEnd() {
		return end;
	}

	public Long getPage() {
		return page;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public List<SqlSortBean> getSorters() {
		return sorters;
	}

	public String getSortCondition() {
		return sortCondition;
	}

	public List<SqlGroupBean> getGroupers() {
		return groupers;
	}

	public String getGroupCondition() {
		return groupCondition;
	}

	public List<SqlFilterBean> getFilters() {
		return filters;
	}

	public String getFilterCondition() {
		return filterCondition;
	}

	public void setDraw(Integer draw) {
		this.draw = draw;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public void setExportType(Integer exportType) {
		this.exportType = exportType;
	}

	public void setSimpleQueryParam(String simpleQueryParam) {
		this.simpleQueryParam = simpleQueryParam;
	}

	public void setQueryByPage(Boolean queryByPage) {
		this.queryByPage = queryByPage;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	public void setEnd(Long end) {
		this.end = end;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public void setSortCondition(String sortCondition) {
		this.sortCondition = sortCondition;
	}

	public void setGroupCondition(String groupCondition) {
		this.groupCondition = groupCondition;
	}

	public void setFilterCondition(String filterCondition) {
		this.filterCondition = filterCondition;
	}
	
}
