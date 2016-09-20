package com.crm.util.tree;

import java.util.Map;

public interface ITreeNode {
	
	/**
	 * 获取父节点编号
	 * 
	 * @return
	 */
	public String parentIdGet();

	/**
	 * 获取自身节点编号
	 * 
	 * @return
	 */
	public String idGet();

	/**
	 * 获取一个Tree对象的属性Map
	 *
	 * @return
	 */
	public Map attributeForTreeGet();
}
