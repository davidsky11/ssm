package com.crm.dao.mybatis;

import java.util.List;

import com.crm.domain.Systemdef;
import com.crm.domain.easyui.PageHelper;

public interface SystemdefMapper {
	
	public List<Systemdef> getAll(PageHelper page);

	public Systemdef getSystemdefById(Integer id);
	
	public void insertSystemdef(Systemdef systemdef);
	
	public void updateSystemdef(Systemdef systemdef);
	
	public void deleteSystemdefById(Integer id);
	
}
