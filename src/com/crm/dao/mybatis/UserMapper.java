/**
 * 
 */
package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Page;
import com.crm.domain.SysMenu;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/**
 * @author zh
 * 2014-8-2
 */
public interface UserMapper {

	public List<User> login(@Param("username") String username, @Param("userType") String userType);
	
	public List<User> findByConditionSql(@Param("conditionSql") String conditionSql);
	
	public User getUserById(@Param("id") String id);

	public List<SysMenu> getMenuByUserId(@Param("userId") String userId);
	
	public List<User> getDatagrid();

	public Integer getDatagridTotal(@Param("user")User user,@Param("csysid")Integer sysid);

	public List<User> datagridUser(@Param("page")PageHelper page,@Param("csysid")Integer sysid);
	
	public Integer userPagesTotal(@Param("conditionSql") String conditionSql);

	public List<User> userPages(@Param("page") Page<User> page, @Param("conditionSql") String conditionSql);

	public int addUser(User user);

	public int editUser(User user);
	
	public int deleteUser(@Param("ids") String ids);
	
	public User findByPhone(@Param("phone") String phone);
	
	public User findByOpenId(@Param("openId") String openId);

}
