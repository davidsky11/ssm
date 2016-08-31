package com.crm.domain.query;

import java.io.Serializable;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 自定义字段值Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class CustomFieldValueQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id; // 自定义字段值
    private String keyStr; // 名称
    private String valueStr; // 值
    private String ownerType; // 类型
    private String ownerId; // 外表id
	public Integer getId() {
		return id;
	}
	public String getKeyStr() {
		return keyStr;
	}
	public String getValueStr() {
		return valueStr;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public String getOwnerId() {
		return ownerId;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setKeyStr(String keyStr) {
		this.keyStr = keyStr;
	}
	public void setValueStr(String valueStr) {
		this.valueStr = valueStr;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
    
}