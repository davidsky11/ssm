package com.crm.service;

import java.util.List;

import com.crm.domain.Activity;
import com.crm.domain.Page;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	ActivityServiceImpl.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:59:30
 * @Version 	V1.0    
 */
public interface ActivityService {

	public int addActivity(Activity activity) throws Exception;

	public int updateActivity(Activity activity);

	public Activity findById(String id);

	public Integer getDatagridTotal(Activity activity);

	public List<Activity> datagridActivity(PageHelper page, Activity activity);

	public List<Activity> getActivityList(String conditionsql);

	public int deleteActivity(String id);
	
	//根据指定条件查询
	public List<Activity> getDatagrid(String conditionSql);

	public Page<Activity> atyPages(Page<Activity> page, String conditionSql);
	
}
 