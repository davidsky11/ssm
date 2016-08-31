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

import com.crm.domain.Role;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.RoleService;

/** 
 * @ClassName	RoleController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:41:33
 * @Version 	V1.0    
 */
@Controller
public class RoleController {
	
	private final Logger log = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private RoleService roleService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/role/list", method = RequestMethod.GET)
	public String roleList(Model model) throws IOException { 
		return "role/list";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/role/addRole",method = RequestMethod.POST)
    public Json addRole(HttpServletRequest request,HttpServletResponse response, Role role) {
		Json j = new Json();
		
		try {
			if (role != null) {
				roleService.addRole(role);
	            j.setSuccess(true);
	            j.setMsg("角色新增成功！");
	            j.setObj(role);
			} else {
				j.setSuccess(false);
				j.setMsg("角色新增失败!");
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
    @RequestMapping(value = "/role/editRole",method = RequestMethod.POST)
    public Json editRole(HttpServletRequest request,HttpServletResponse response, Role role) {
        Json j = new Json();
		
        try {
        	if (role != null) {
        		log.debug("穿过来的用户ID为：" + role.getId());
        		
	            roleService.updateRole(role);
	            j.setSuccess(true);
	            j.setMsg("角色编辑成功！");
	            j.setObj(role);
        	} else {
        		j.setSuccess(false);
				j.setMsg("角色信息有误!");
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
	@RequestMapping(value = "/role/deleteRole",method = RequestMethod.POST)
	public Json deleteRole(Role role) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+role.getId());
        try {
			roleService.deleteRole(role.getId());
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
	@RequestMapping(value = "/role/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page) {
		DataGrid dg = new DataGrid();
		
		Role role = new Role();
		dg.setTotal(roleService.getDatagridTotal(role));
		List<Role> roleList = roleService.datagridRole(page, role);
		dg.setRows(roleList);
		return dg;
	}
	
}
 