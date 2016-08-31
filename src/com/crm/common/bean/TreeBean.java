package com.crm.common.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 树
 */
public class TreeBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private BaseTreeNodeBean root; // 根节点

	// 暂时不好用
	// public TreeBean(BaseTreeNodeBean parentNode, List<BaseTreeNodeBean>
	// nodeList) {
	// this.root = parentNode;
	// createTree(parentNode, nodeList);
	// }

	/**
	 * 创建树
	 */
	public static BaseTreeNodeBean createTree(BaseTreeNodeBean parentNode, List<? extends BaseTreeNodeBean> nodeList, String chirldrenNodeName) {
		if ((nodeList != null) && (nodeList.size() > 0)) {
			for (int i = 0; i < nodeList.size(); ++i) {
				BaseTreeNodeBean child = (BaseTreeNodeBean) nodeList.get(i);
				if (child.getParentId().equals(parentNode.getId())) {
					if (parentNode.isLeaf()) {
						parentNode.setLeaf(false);
					}

					if ((chirldrenNodeName == null) || ("children".equals(chirldrenNodeName))) {
						if (parentNode.getChildren() == null) {
							parentNode.setChildren(new ArrayList<BaseTreeNodeBean>());
						}
						parentNode.getChildren().add(createTree(child, nodeList, chirldrenNodeName));
					} else {
						if (parentNode.getNodes() == null) {
							parentNode.setNodes(new ArrayList<BaseTreeNodeBean>());
						}
						parentNode.getNodes().add(createTree(child, nodeList, chirldrenNodeName));
					}
				}
			}
		}
		return parentNode;
	}

	/**
	 * 创建树表格
	 */
	public static void createTreeTable(List<BaseTreeNodeBean> result, int nodeLevel, final BaseTreeNodeBean parentNode, final List<? extends BaseTreeNodeBean> nodeList) {
		if (result == null) {
			result = new ArrayList<BaseTreeNodeBean>();
		}
		if (nodeList != null && nodeList.size() > 0) {
			nodeLevel++;
			for (int i = 0; i < nodeList.size(); i++) {
				BaseTreeNodeBean child = nodeList.get(i);
				if (child.getParentId().equals(parentNode.getId())) {
					child.setNodeLevel(nodeLevel);
					result.add(child);
					createTreeTable(result, nodeLevel, child, nodeList);
				}
			}
		}
	}

	public BaseTreeNodeBean getRoot() {
		return root;
	}

	public void setRoot(BaseTreeNodeBean root) {
		this.root = root;
	}
	
}
