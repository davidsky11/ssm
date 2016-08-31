package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Account;
import com.crm.domain.SysMenu;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AccountMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface AccountMapper {
	
	// 新增Account
	public int saveAccount(Account account) throws Exception;

	// 删除Account
	public int deleteAccount(@Param("id") String id);
	
	// 修改Account
	public int updateAccount(Account account);
	
	public Long getDatagridTotal(Account account);
	
	public List<Account> datagridAccount(@Param("page") PageHelper page, @Param("account") Account account);
	
	// 查询所有用户信息
	public List<Account> getAccountList(@Param("conditionSql") String conditionsql); 
	
	// 根据用户名/密码查询
	public Account findByNameAndPass(@Param("username") String username, @Param("password") String password,
			@Param("userType") String userType);
	
	// 根据用户名查询
	public Account findByUserName(@Param("username") String username, @Param("userType") int userType) ;

	// 根据用户ID查询
	public Account findById(@Param("id")String id);
	
	public List<SysMenu> getMenuByAccountId(@Param("accountId") String accountId);
		
}
 