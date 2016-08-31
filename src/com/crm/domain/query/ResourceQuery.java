package com.crm.domain.query;

import java.io.Serializable;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 资源Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ResourceQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 资源表
    private String codeNum; // 编码
    private String resourceName; // 名称
    private String iconCls; // 图标
    private Integer type; // 资源类型 1菜单 2按钮 3方法
    private String code; // 权限代码
    private String permission; // 权限标识
    private String permValue; // 权限值
    private String description; // 描述
    private String parentId; // 父级资源
    private Integer sortOrder; // 排序
    private Integer editable; // 是否可编辑 1可编辑 2不可编辑
    private Integer enabled; // 是否可用
	public String getId() {
		return id;
	}
	public String getCodeNum() {
		return codeNum;
	}
	public String getResourceName() {
		return resourceName;
	}
	public String getIconCls() {
		return iconCls;
	}
	public Integer getType() {
		return type;
	}
	public String getCode() {
		return code;
	}
	public String getPermission() {
		return permission;
	}
	public String getPermValue() {
		return permValue;
	}
	public String getDescription() {
		return description;
	}
	public String getParentId() {
		return parentId;
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
	public void setCodeNum(String codeNum) {
		this.codeNum = codeNum;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setPermission(String permission) {
		this.permission = permission;
	}
	public void setPermValue(String permValue) {
		this.permValue = permValue;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
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