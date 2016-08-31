package com.crm.controller;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.Account;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.AccountService;

/** 
 * @ClassName	AccountController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:41:33
 * @Version 	V1.0    
 */
@Controller
public class AccountController {
	
	private final Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@Resource
	private AccountService accountService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/account/list", method = RequestMethod.GET)
	public String accountList(Model model) throws IOException { 
		return "account/list";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/account/addAccount",method = RequestMethod.POST)
    public Json addUser(Account account) {
		Json j = new Json();
		
		try {
            accountService.register(account);
            j.setSuccess(true);
            j.setMsg("用户新增成功！");
            j.setObj(account);
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
    @RequestMapping(value = "/account/editAccount",method = RequestMethod.POST)
    public Json editUser(Account account) {
        Json j = new Json();
        log.debug("穿过来的用户ID为：" + account.getId());
        try {
            accountService.updateAccount(account);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            j.setObj(account);
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
	@RequestMapping(value = "/account/deleteAccount",method = RequestMethod.POST)
	public Json deleteUser(Account account) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+account.getId());
        try {
			accountService.deleteAccount(account.getId());
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
	@RequestMapping(value = "/account/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page) {
		DataGrid dg = new DataGrid();
		
		Account account = new Account();
		dg.setTotal(accountService.getDatagridTotal(account));
		List<Account> accountList = accountService.datagridAccount(page, account);
		dg.setRows(accountList);
		return dg;
	}
	
}
 