package com.crm.domain.system; 

/** 
 * @ClassName	Grid.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月1日 下午5:25:07
 * @Version 	V1.0    
 */
public class Grid implements java.io.Serializable {

	private static final long serialVersionUID = -1086867390717762666L;

	// Fields
	private String id;
	private String gridCode;
	private String gridName;
	private String moduleName;
	private String userAccount;
	private String isGridField;
	private String pGuid;
	private String isStatic;
	private String fieldType;
	private String tableName;
	private String fieldLookup;
	
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
	public String getUserAccount() {
		return userAccount;
	}
	public String getIsGridField() {
		return isGridField;
	}
	public String getpGuid() {
		return pGuid;
	}
	public String getIsStatic() {
		return isStatic;
	}
	public String getFieldType() {
		return fieldType;
	}
	public String getTableName() {
		return tableName;
	}
	public String getFieldLookup() {
		return fieldLookup;
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
	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}
	public void setIsGridField(String isGridField) {
		this.isGridField = isGridField;
	}
	public void setpGuid(String pGuid) {
		this.pGuid = pGuid;
	}
	public void setIsStatic(String isStatic) {
		this.isStatic = isStatic;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setFieldLookup(String fieldLookup) {
		this.fieldLookup = fieldLookup;
	}
	
}
 