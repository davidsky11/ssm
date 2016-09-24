package com.crm.service;

import java.util.List;

import com.crm.domain.Role;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	RoleService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月1日 下午2:32:57
 * @Version 	V1.0    
 */
public interface RoleService {
	
	public int addRole(Role role) throws Exception ;

	public int updateRole(Role role) ;

	public Role findById(String id);

	public Integer getDatagridTotal(Role role);

	public List<Role> datagridRole(PageHelper page, Role role) ;

	public List<Role> getRoleList(String conditionsql);

	public int deleteRole(String id) ;

}
 