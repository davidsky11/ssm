package com.crm.service;

import java.util.List;

import com.crm.domain.Systemdef;
import com.crm.domain.easyui.PageHelper;

public interface SystemdefService {
	
	/**
	 * 获取所有列表
	 * @param page
	 * @return
	 */
	public List<Systemdef> getAll(PageHelper page) ;

	public void deleteSystemdefById(Integer systemdefId);

	public Systemdef getSystemdefById(Integer systemdefId);

	/**
	 * 保存、更新
	 * @param systemdef
	 */
	public void saveSystemdef(Systemdef systemdef);

}
