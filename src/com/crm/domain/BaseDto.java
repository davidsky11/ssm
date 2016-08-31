package com.crm.domain; 

/** 
 * @ClassName	BaseDto.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月1日 下午5:21:23
 * @Version 	V1.0    
 */
public class BaseDto implements java.io.Serializable {

	private static final long serialVersionUID = 5729450443247949152L;

	public final static String DIRECTION_DESC = "DESC";
	public final static String DIRECTION_ASC = "ASC";
	
	private int start;
	private int limit = 10;// 每页数量
	private String sort;// 排序字段名
	private String dir;// 排序方向
	private boolean needCount;
	private int totalCount;// 总条数

	final public int getTotalCount() {
		return totalCount;
	}

	final public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	final public void calStart() {
		if (start >= totalCount) {
			start = ((int) ((totalCount - 1) / limit)) * limit;
		}
	}

	final public void setPgNumber(int pgNumber) {
		if (pgNumber < 1)
			pgNumber = 1;

		start = (pgNumber - 1) * limit;
	}

	final public int getPgNumber() {
		return start / limit + 1;
	}

	final public int getEnd() {
		return this.start + this.limit;
	}

	final public int getStart() {
		return start;
	}

	final public void setStart(int start) {
		this.start = start;
	}

	final public int getLimit() {
		return limit;
	}

	final public void setLimit(int limit) {
		this.limit = limit;
	}

	final public boolean isNeedCount() {
		return needCount;
	}

	final public void setNeedCount(boolean needCount) {
		this.needCount = needCount;
	}

	final public String getSort() {
		return sort;
	}

	final public void setSort(String sort) {
		this.sort = sort;
	}

	final public String getDir() {
		return dir;
	}

	final public void setDir(String dir) {
		this.dir = dir;
	}

}
 