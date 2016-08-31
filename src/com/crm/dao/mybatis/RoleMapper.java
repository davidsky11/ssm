package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Role;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AccountMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface RoleMapper {
	
	// 新增Role
	public int saveRole(Role role) throws Exception;

	// 删除Role
	public int deleteRole(@Param("id") String id);
	
	// 修改Role
	public int updateRole(Role role);
	
	// 查询所有活动
	public List<Role> getRoleList(@Param("conditionSql") String conditionsql); 

	public Role findById(@Param("id") String id);
	
	public Long getDatagridTotal(Role role);

	public List<Role> datagridRole(@Param("page") PageHelper page, @Param("role") Role role);
	

}
 