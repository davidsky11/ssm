/**
 * 
 */
package com.crm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.RoleMapper;
import com.crm.dao.mybatis.UserMapper;
import com.crm.dao.mybatis.UserRoleMapper;
import com.crm.domain.Role;
import com.crm.domain.SysMenu;
import com.crm.domain.User;
import com.crm.domain.UserRole;
import com.crm.domain.easyui.PageHelper;

/**
 * @author zh
 * 2014-8-2
 */
/** 
 * Cacheable注解        负责将方法的返回值加入到缓存中 
 * CacheEvict注解     负责清除缓存(它的三个参数与@Cacheable的意思是一样的) 
 * ---------------------------------------------------------------------------------------------------------- 
 * value------缓存位置的名称,不能为空,若使用EHCache则其值为ehcache.xml中的<cache name="myCache"/> 
 * key--------缓存的Key,默认为空(表示使用方法的参数类型及参数值作为key),支持SpEL 
 * condition--只有满足条件的情况才会加入缓存,默认为空(表示全部都加入缓存),支持SpEL 
 * 该注解的源码位于spring-context-*.RELEASE-sources.jar中 
 * Spring针对Ehcache支持的Java源码位于spring-context-support-*.RELEASE-sources.jar中 
 * ---------------------------------------------------------------------------------------------------------- 
 */
@Service
public class UserService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private UserRoleMapper userRoleMapper;
	
	/**
	 * @param username
	 * @return
	 */
	public List<User> findUserByName(String username, String userType) {
		return userMapper.findUserByName(username, userType);
	}
	
	public List<User> findByConditionSql(String username, String userType) {
		return userMapper.findByConditionSql(" AND username = '" + username + "' AND userType = '" + userType + "'");
	}
	
	//将查询到的数据缓存到myCache中,并使用方法名称加上参数中的userNo作为缓存的key  
    //通常更新操作只需刷新缓存中的某个值,所以为了准确的清除特定的缓存,故定义了这个唯一的key,从而不会影响其它缓存值  
    @Cacheable(value="myCache", key="#id")  
    public String getUsernameById(String id){  
        System.out.println("数据库中查到此用户号[" + id + "]对应的用户名为[" + userMapper.getUsernameById(id) + "]");  
        return userMapper.getUsernameById(id);  
    }

	/**
	 * 获取该用户权限的菜单
	 * @param userId
	 * @return
	 */
	public List<SysMenu> getMenu(String userId) {
		return userMapper.getMenuByUserId(userId);  
	}

	/**
	 * 获取用户总数
	 * @param user
	 * @return
	 */
	public Long getDatagridTotal(User user,Integer sysid) {
		return userMapper.getDatagridTotal(user,sysid);  
	}

	/**
	 * 获取用户列表
	 * @param page
	 * @return
	 */
	public List<User> datagridUser(PageHelper page,Integer sysid) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return userMapper.datagridUser(page,sysid);  
	}

	/**
	 * 新增用户
	 * @param user
	 */
	public int add(User user) {
		return userMapper.addUser(user);  
	}

	/**
	 * 编辑用户
	 * @param user
	 */
	public void edit(User user) {
		userMapper.editUser(user);  
	}  
    
	/**
	 * 删除用户
	 * @param id
	 */
	public void deleteUser(String id){
		userMapper.deleteUser(id);
	}
	
	/**
	 * @Title:			getRoleByUserId
	 * @Description:	根据用户ID获取角色信息
	 * @param userId
	 * @return
	 */
	public List<Role> getRoleByUserId(String userId) {
		return userRoleMapper.findRoleByUserId(userId);
	}
    
	/**
	 * @Title:			updateUserRole
	 * @Description:	编辑用户信息
	 * @param userRole
	 * @return
	 */
    public int updateUserRole(UserRole userRole) {
    	return userRoleMapper.updateUserRole(userRole);
    }
    
    /**
     * @Title:			deleteUserRole
     * @Description:	根据指定的conditionSql删除
     * @param conditionsql
     * @return
     */
    public int deleteUserRole(String conditionsql) {
    	return userRoleMapper.deleteUserRoleBySQL(conditionsql);
    }
    
    /**
     * @Title:			getByUserId
     * @Description:	根据ID获取UserRole
     * @param userId
     * @return
     */
    public UserRole getById(String id) {
    	return userRoleMapper.getById(id);
    }
    
}
