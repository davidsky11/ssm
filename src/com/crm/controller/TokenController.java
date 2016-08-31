package com.crm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.authorization.annotation.CurrentAccount;
import com.crm.authorization.model.TokenModel;
import com.crm.authorization.service.TokenService;
import com.crm.dao.mybatis.AccountDao;
import com.crm.domain.Account;
import com.crm.domain.http.ResultModel;
import com.crm.rest.config.ResultStatus;

/**
 * @ClassName TokenController.java
 * @Description 获取和删除token的请求地址，在Restful设计中其实就对应着登录和退出登录的资源映射
 * @Author kevin
 * @CreateTime 2016年7月3日 上午2:08:33
 * @Version V1.0
 */
@RestController
@RequestMapping("/tokens")
public class TokenController {

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private TokenService<String, String> tokenService;

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity login(@RequestParam String username, @RequestParam String password) {
		Assert.notNull(username, "username can not be empty");
		Assert.notNull(password, "password can not be empty");

		Account account = accountDao.findByUserName(username);

		if (account == null || // 未注册
				!account.getPassword().equals(password)) { // 密码错误
			// 提示用户名或密码错误
			return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR),
					HttpStatus.NOT_FOUND);
		}
		
		// 生成一个token，保持用户登录状态
		TokenModel model = tokenService.createToken(account);
		return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
	}

	public ResponseEntity logout(@CurrentAccount Account account) {
		tokenService.deleteToken(account.getId());
		return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
	}
	
}
