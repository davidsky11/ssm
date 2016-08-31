/**
 * 
 */
package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.SysMenu;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/**
 * @author zh
 * 2014-8-2
 */
public interface UserMapper {

	public List<User> findUserByName(@Param("username") String username, @Param("userType") String userType);
	
	public List<User> findByConditionSql(@Param("conditionSql") String conditionSql);
	
	public String getUsernameById(@Param("id") String id);

	public List<SysMenu> getMenuByUserId(@Param("userId") String userId);
	
	public List<User> getDatagrid();

	public Long getDatagridTotal(@Param("user")User user,@Param("csysid")Integer sysid);

	public List<User> datagridUser(@Param("page")PageHelper page,@Param("csysid")Integer sysid);

	public int addUser(User user);

	public void editUser(User user);
	
	public int deleteUser(String id);

}
