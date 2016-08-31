package com.crm.domain.system;

/**
 * @ClassName Tabname.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月1日 下午5:28:54
 * @Version V1.0
 */
public class TabName implements java.io.Serializable {

	private static final long serialVersionUID = 2470587778946580666L;

	// Fields
	private String tableName;
	private Integer tableIndex;
	private String tableCaption;
	private String presere;
	private Integer tableVisible;
	private String relation;
	private Integer sourceRelationId;
	
	// Constructors
	/* default constructor */
	public TabName() {
		
	}
	
	/* minimal constructor */
	public TabName(Integer tableVisible) {
		this.tableVisible = tableVisible;
	}
	
	// Property accessors
	public String getTableName() {
		return tableName;
	}
	public Integer getTableIndex() {
		return tableIndex;
	}
	public String getTableCaption() {
		return tableCaption;
	}
	public String getPresere() {
		return presere;
	}
	public Integer getTableVisible() {
		return tableVisible;
	}
	public String getRelation() {
		return relation;
	}
	public Integer getSourceRelationId() {
		return sourceRelationId;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setTableIndex(Integer tableIndex) {
		this.tableIndex = tableIndex;
	}
	public void setTableCaption(String tableCaption) {
		this.tableCaption = tableCaption;
	}
	public void setPresere(String presere) {
		this.presere = presere;
	}
	public void setTableVisible(Integer tableVisible) {
		this.tableVisible = tableVisible;
	}
	public void setRelation(String relation) {
		this.relation = relation;
	}
	public void setSourceRelationId(Integer sourceRelationId) {
		this.sourceRelationId = sourceRelationId;
	}
	
}
