package com.crm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.AccountMapper;
import com.crm.domain.Account;
import com.crm.domain.SysMenu;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AccountServiceImpl.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:59:30
 * @Version 	V1.0    
 */
@Service
public class AccountService {
	
	@Resource
	private AccountMapper accountMapper;

	public int register(Account account) throws Exception {
		return accountMapper.saveAccount(account);
	}

	public Account login(String username, String password, String userType) {
		return accountMapper.findByNameAndPass(username, password, userType);
	}

	public int updateAccount(Account account) {
		return accountMapper.updateAccount(account);
	}

	public Account findByUserName(String username, int userType) {
		return accountMapper.findByUserName(username, userType);
	}

	public Account findById(String id) {
		return accountMapper.findById(id);
	}

	public Long getDatagridTotal(Account account) {
		return accountMapper.getDatagridTotal(account);
	}

	public List<Account> datagridAccount(PageHelper page, Account account) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return accountMapper.datagridAccount(page, account);
	}

	public List<Account> getAccountList(String conditionsql) {
		return accountMapper.getAccountList(conditionsql);
	}

	public int deleteAccount(String id) {
		return accountMapper.deleteAccount(id);
	}

	public List<SysMenu> getMenu(String id) {
		return accountMapper.getMenuByAccountId(id);
	}

}
 