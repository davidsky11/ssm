package com.crm.domain.query;

import java.io.Serializable;

import com.crm.common.bean.BaseQueryBean;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 角色Query
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RoleQuery extends BaseQueryBean implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id; // 角色表
    private String codeNum; // 编码
    private String roleName; // 角色名称
    private String description; // 描述
    private Integer editable; // 是否可编辑
    private Integer enabled; // 是否可用
	public String getId() {
		return id;
	}
	public String getCodeNum() {
		return codeNum;
	}
	public String getRoleName() {
		return roleName;
	}
	public String getDescription() {
		return description;
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
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setEditable(Integer editable) {
		this.editable = editable;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
    
}