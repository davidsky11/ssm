package com.crm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.Activity;
import com.crm.domain.User;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ActivityService;
import com.crm.util.common.Const;

/** 
 * @ClassName	ActivityController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:41:33
 * @Version 	V1.0    
 */
@Controller
public class ActivityController {
	
	private final Logger log = LoggerFactory.getLogger(ActivityController.class);
	
	@Autowired
	private ActivityService activityService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/activity/list", method = RequestMethod.GET)
	public String activityList(Model model) throws IOException { 
		return "activity/list";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/addActivity",method = RequestMethod.POST)
    public Json addActivity(HttpServletRequest request,HttpServletResponse response, Activity activity) {
		Json j = new Json();
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		
		try {
			if (activity != null) {
				activity.setPublisherId(user.getId());
				activity.setPublisherName(user.getUsername());
				activityService.addActivity(activity);
	            j.setSuccess(true);
	            j.setMsg("活动新增成功！");
	            j.setObj(activity);
			} else {
				j.setSuccess(false);
				j.setMsg("活动新增失败!");
			}
			
            
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
     * 修改用户
     * 
     * @param user
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/activity/editActivity",method = RequestMethod.POST)
    public Json editActivity(HttpServletRequest request,HttpServletResponse response, Activity activity) {
        Json j = new Json();
        User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		
        try {
        	if (activity != null) {
        		log.debug("穿过来的用户ID为：" + activity.getId());
        		
        		activity.setPublisherId(user.getId());
				activity.setPublisherName(user.getUsername());
	            activityService.updateActivity(activity);
	            j.setSuccess(true);
	            j.setMsg("活动编辑成功！");
	            j.setObj(activity);
        	} else {
        		j.setSuccess(false);
				j.setMsg("活动信息有误!");
        	}
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/deleteActivity",method = RequestMethod.POST)
	public Json deleteActivity(Activity activity) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+activity.getId());
        try {
			activityService.deleteActivity(activity.getId());
			j.setSuccess(true);
	        j.setMsg("删除成功！");
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
	}
	
	/**
	 * 前端用户表格
	 */
	@ResponseBody
	@RequestMapping(value = "/activity/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page) {
		DataGrid dg = new DataGrid();
		
		Activity activity = new Activity();
		dg.setTotal(activityService.getDatagridTotal(activity));
		List<Activity> activityList = activityService.datagridActivity(page, activity);
		dg.setRows(activityList);
		return dg;
	}
	
}
 