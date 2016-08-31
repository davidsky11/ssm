package com.crm.domain.query;

import java.io.Serializable;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据字典Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DictQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 数据字典
    private String dictType; // 字典类型
    private String dictName; // 字典名称
    private String dictValue; // 字典值
    private String description; // 描述
    private Integer sortOrder; // 排序
    private Integer editable; // 是否可编辑
    private Integer enabled; // 是否可用
	public String getId() {
		return id;
	}
	public String getDictType() {
		return dictType;
	}
	public String getDictName() {
		return dictName;
	}
	public String getDictValue() {
		return dictValue;
	}
	public String getDescription() {
		return description;
	}
	public Integer getSortOrder() {
		return sortOrder;
	}
	public Integer getEditable() {
		return editable;
	}
	public Integer getEnabled() {
		return enabled;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setDictType(String dictType) {
		this.dictType = dictType;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public void setDictValue(String dictValue) {
		this.dictValue = dictValue;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}
	public void setEditable(Integer editable) {
		this.editable = editable;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
    
}