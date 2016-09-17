package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Activity;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AccountMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface ActivityMapper {
	
	// 新增Account
	public int saveActivity(Activity activity) throws Exception;

	// 删除Account
	public int deleteActivity(@Param("id") String id);
	
	// 修改Account
	public int updateActivity(Activity activity);
	
	// 查询所有活动
	public List<Activity> getActivityList(@Param("conditionSql") String conditionsql); 
	
	// 根据用户名查询
	public Activity findByTitle(@Param("title") String title) ;

	// 根据用户ID查询
	public Activity findByPublisher(User user);

	public Activity findById(@Param("id") String id);

	public Integer getDatagridTotal(Activity activity);

	public List<Activity> datagridActivity(@Param("page") PageHelper page, @Param("activity") Activity activity);

	public List<Activity> getDatagrid(@Param("conditionSql") String conditionSql);
		
	public Integer atyPagesTotal(@Param("conditionSql") String conditionSql);

	public List<Activity> atyPages(@Param("page") Page page, @Param("conditionSql") String conditionSql);
	
}
 