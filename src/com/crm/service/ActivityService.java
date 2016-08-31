package com.crm.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.ActivityMapper;
import com.crm.domain.Activity;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	ActivityServiceImpl.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:59:30
 * @Version 	V1.0    
 */
@Service
public class ActivityService {
	
	@Resource
	private ActivityMapper activityMapper;

	public int addActivity(Activity activity) throws Exception {
		return activityMapper.saveActivity(activity);
	}

	public int updateActivity(Activity activity) {
		return activityMapper.updateActivity(activity);
	}

	public Activity findById(String id) {
		return activityMapper.findById(id);
	}

	public Long getDatagridTotal(Activity activity) {
		return activityMapper.getDatagridTotal(activity);
	}

	public List<Activity> datagridActivity(PageHelper page, Activity activity) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return activityMapper.datagridActivity(page, activity);
	}

	public List<Activity> getActivityList(String conditionsql) {
		return activityMapper.getActivityList(conditionsql);
	}

	public int deleteActivity(String id) {
		return activityMapper.deleteActivity(id);
	}
	
	//根据指定条件查询
	public List<Activity> getDatagrid(@Param("conditionSql") String conditionSql) {
		return activityMapper.getDatagrid(conditionSql);
	}

}
 