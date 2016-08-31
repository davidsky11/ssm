package com.crm.common.interfaces;

/**
 * 实体实现该接口表示想要逻辑删除
 */
public interface LogicDeleteable {
	public Boolean getDeleted();
	public void setDeleted(Boolean deleted);

	/**
	 * 标识为已删除
	 */
	public void markDeleted();
}
