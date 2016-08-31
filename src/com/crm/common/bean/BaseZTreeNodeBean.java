package com.crm.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * ztree 格式数据
 */
public class BaseZTreeNodeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id;
	private String pId;
	private String name;
	private Boolean checked;
	private Boolean open;
	private String url;
	private String click;
	private String target;
	private List<BaseZTreeNodeBean> children;
	public String getId() {
		return id;
	}
	public String getPId() {
		return pId;
	}
	public String getName() {
		return name;
	}
	public Boolean getChecked() {
		return checked;
	}
	public Boolean getOpen() {
		return open;
	}
	public String getUrl() {
		return url;
	}
	public String getClick() {
		return click;
	}
	public String getTarget() {
		return target;
	}
	public List<BaseZTreeNodeBean> getChildren() {
		return children;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setPId(String pId) {
		this.pId = pId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public void setOpen(Boolean open) {
		this.open = open;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public void setClick(String click) {
		this.click = click;
	}
	public void setTarget(String target) {
		this.target = target;
	}
	public void setChildren(List<BaseZTreeNodeBean> children) {
		this.children = children;
	}
	
}
