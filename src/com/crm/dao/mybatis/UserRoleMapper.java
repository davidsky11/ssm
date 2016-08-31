package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Role;
import com.crm.domain.User;
import com.crm.domain.UserRole;

/** 
 * @ClassName	UserRoleMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface UserRoleMapper {
	
	// 新增UserRole
	public int saveUserRole(UserRole userRole) throws Exception;

	// 删除UserRole
	public int deleteUserRole(@Param("id") String id);
	
	// 获取UserRole
	public UserRole getById(@Param("id") String id);
	
	// 删除UserRole
	public int deleteUserRoleBySQL(@Param("conditionSql") String conditionSql);
	
	// 修改UserRole
	public int updateUserRole(UserRole userRole);
	
	// 根据userId查找role
	public List<Role> findRoleByUserId(@Param("userId") String userId);
	
	// 根据roleId查找所有的user
	public List<User> findUserByRole(Role role);
	
}
 