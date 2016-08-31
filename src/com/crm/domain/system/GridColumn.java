package com.crm.domain.system; 

/** 
 * @ClassName	GridColumn.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月1日 下午5:23:43
 * @Version 	V1.0    
 */
public class GridColumn implements java.io.Serializable {

	private static final long serialVersionUID = -4616514964439448137L;

	// Fields
	private String id; //
	private String gridCode; //
	private String gridName; //
	private String moduleName; //
	private String pGuid; //
	private String isStatic; //
	private String memo; //
	
	public String getId() {
		return id;
	}
	public String getGridCode() {
		return gridCode;
	}
	public String getGridName() {
		return gridName;
	}
	public String getModuleName() {
		return moduleName;
	}
	public String getpGuid() {
		return pGuid;
	}
	public String getIsStatic() {
		return isStatic;
	}
	public String getMemo() {
		return memo;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setGridCode(String gridCode) {
		this.gridCode = gridCode;
	}
	public void setGridName(String gridName) {
		this.gridName = gridName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public void setpGuid(String pGuid) {
		this.pGuid = pGuid;
	}
	public void setIsStatic(String isStatic) {
		this.isStatic = isStatic;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
}
 