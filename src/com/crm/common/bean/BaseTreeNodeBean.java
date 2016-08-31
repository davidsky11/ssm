package com.crm.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树
 */
public abstract class BaseTreeNodeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String id; // id
	private String text;
	private String parentId;
	private boolean expanded; // 是否展开
	private boolean leaf = true; // 是否叶子节点
	private int nodeLevel = 0;
	private List<BaseTreeNodeBean> children = new ArrayList<BaseTreeNodeBean>(); // 子节点
	private List<BaseTreeNodeBean> nodes = null;
	public String getId() {
		return id;
	}
	public String getText() {
		return text;
	}
	public String getParentId() {
		return parentId;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public int getNodeLevel() {
		return nodeLevel;
	}
	public List<BaseTreeNodeBean> getChildren() {
		return children;
	}
	public List<BaseTreeNodeBean> getNodes() {
		return nodes;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public void setNodeLevel(int nodeLevel) {
		this.nodeLevel = nodeLevel;
	}
	public void setChildren(List<BaseTreeNodeBean> children) {
		this.children = children;
	}
	public void setNodes(List<BaseTreeNodeBean> nodes) {
		this.nodes = nodes;
	}
	
}
