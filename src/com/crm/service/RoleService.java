package com.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.RoleMapper;
import com.crm.domain.Role;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	RoleService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月1日 下午2:32:57
 * @Version 	V1.0    
 */
@Service
public class RoleService {
	
	@Autowired
	private RoleMapper roleMapper;
	
	public int addRole(Role role) throws Exception {
		return roleMapper.saveRole(role);
	}

	public int updateRole(Role role) {
		return roleMapper.updateRole(role);
	}

	public Role findById(String id) {
		return roleMapper.findById(id);
	}

	public Integer getDatagridTotal(Role role) {
		return roleMapper.getDatagridTotal(role);
	}

	public List<Role> datagridRole(PageHelper page, Role role) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return roleMapper.datagridRole(page, role);
	}

	public List<Role> getRoleList(String conditionsql) {
		return roleMapper.getRoleList(conditionsql);
	}

	public int deleteRole(String id) {
		return roleMapper.deleteRole(id);
	}

}
 