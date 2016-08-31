package com.crm.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.crm.domain.Account;
import com.crm.service.AccountService;

/** 
 * @ClassName	AuthenticateAPI.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:57:55
 * @Version 	V1.0    
 */
@Controller
@RequestMapping("/admin/v1/")
public class AuthenticateAPI {

	@Autowired
	private AccountService accountService;
	
	public String authenticate(@RequestBody String param) {
		try {
			Account account = JSON.parseObject(param, Account.class);
			/*Token token = accountService.login(account);
			
			if (token == null) {
				throw new CustomizedException(HttpStatus.UNAUTHORIZED, "User authenticate failed.");
			}
			
			TokenResponseEntity tokenResponseEntity = new TokenResponseEntity();*/
		} catch (Exception e) {
			
		}
		
		return null;
	}
	
}
 