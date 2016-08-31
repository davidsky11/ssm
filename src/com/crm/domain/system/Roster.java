package com.crm.domain.system;

import java.util.List;

/** 
 * @ClassName	Roster.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月1日 下午5:27:14
 * @Version 	V1.0    
 */
public class Roster implements java.io.Serializable {

	private static final long serialVersionUID = -382327912629944270L;

	// Fields
	private Integer fieldId;
	private Integer fieldIndex;
	private String tableName;
	private String fieldName;
	private String fieldCaption;
	private String fieldLabel;
	private String fieldType;
	private Integer fieldWidth;
	private Integer fieldScale;
	private Integer fieldVisible;
	private Integer fieldMark;// 是否必填字段，'1' 是,'0' 不是
	private String fieldLookup;
	private String displayFormat;
	private String gridField;// 是否列表显示列，'1' 是,'0' 不是
	private String subField;// 是否分栏显示列，'1' 是,'0' 不是
	private List dicList;// 代码类型时，取出字典数据
	private String pGuid;
	private String isStatic;
	public Integer getFieldId() {
		return fieldId;
	}
	public Integer getFieldIndex() {
		return fieldIndex;
	}
	public String getTableName() {
		return tableName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public String getFieldCaption() {
		return fieldCaption;
	}
	public String getFieldLabel() {
		return fieldLabel;
	}
	public String getFieldType() {
		return fieldType;
	}
	public Integer getFieldWidth() {
		return fieldWidth;
	}
	public Integer getFieldScale() {
		return fieldScale;
	}
	public Integer getFieldVisible() {
		return fieldVisible;
	}
	public Integer getFieldMark() {
		return fieldMark;
	}
	public String getFieldLookup() {
		return fieldLookup;
	}
	public String getDisplayFormat() {
		return displayFormat;
	}
	public String getGridField() {
		return gridField;
	}
	public String getSubField() {
		return subField;
	}
	public List getDicList() {
		return dicList;
	}
	public String getpGuid() {
		return pGuid;
	}
	public String getIsStatic() {
		return isStatic;
	}
	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}
	public void setFieldIndex(Integer fieldIndex) {
		this.fieldIndex = fieldIndex;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public void setFieldCaption(String fieldCaption) {
		this.fieldCaption = fieldCaption;
	}
	public void setFieldLabel(String fieldLabel) {
		this.fieldLabel = fieldLabel;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}
	public void setFieldWidth(Integer fieldWidth) {
		this.fieldWidth = fieldWidth;
	}
	public void setFieldScale(Integer fieldScale) {
		this.fieldScale = fieldScale;
	}
	public void setFieldVisible(Integer fieldVisible) {
		this.fieldVisible = fieldVisible;
	}
	public void setFieldMark(Integer fieldMark) {
		this.fieldMark = fieldMark;
	}
	public void setFieldLookup(String fieldLookup) {
		this.fieldLookup = fieldLookup;
	}
	public void setDisplayFormat(String displayFormat) {
		this.displayFormat = displayFormat;
	}
	public void setGridField(String gridField) {
		this.gridField = gridField;
	}
	public void setSubField(String subField) {
		this.subField = subField;
	}
	public void setDicList(List dicList) {
		this.dicList = dicList;
	}
	public void setpGuid(String pGuid) {
		this.pGuid = pGuid;
	}
	public void setIsStatic(String isStatic) {
		this.isStatic = isStatic;
	}
	
}
 