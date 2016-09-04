package com.crm.rest.controller;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.authorization.model.TokenModel;
import com.crm.authorization.service.TokenService;
import com.crm.common.constants.ConstantDBOperateResultTypes;
import com.crm.domain.User;
import com.crm.rest.domain.ApiResult;
import com.crm.service.UserService;
import com.crm.service.ValidService;
import com.crm.util.ValidUtil;
import com.crm.util.common.Const;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/** 
 * @ClassName	CommonController.java
 * @Description 通用接口 [主要是用户的注册、登录、登出]
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午12:47:45
 * @Version 	V1.0    
 */
@RestController
@RequestMapping("/v1")
@Api(value = "buss", description = "用户相关的API", position = 1)
public class CommonController {
	
	private final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
	@Qualifier("ehcacheTokenService")
	private TokenService<String, String> tokenService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ValidService validService;
	
	/**
	 * @Title:			accountLogin
	 * @Description:	前端用户注册
	 * @param phone		可以为手机号码
	 * @param password
	 * @param userType	用户类型 2-经销商，3-APP用户
	 * @param validCode	验证码
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "用户注册", httpMethod = "POST", nickname="register", response = ApiResult.class, notes = "根据用户名密码登录", position = 1)
	public ApiResult<User> register(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone, 
			@ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password") String password,
			@ApiParam(required = true, name = "userType", value = "用户类型") @RequestParam(value = "userType") String userType,
			@ApiParam(required = true, name = "validCode", value = "验证码") @RequestParam(value = "validCode") String validCode) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户名: " + phone + " 密码: " + password);
		
		ApiResult<User> result = new ApiResult<User>();
		result.setOperate(Const.OPERATE_USER_REGISTER);
		
		/**
		 * 0、验证手机号是否合法
		 */
		if (!ValidUtil.isValidMobile(phone)) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setMsg("手机号码有误，请仔细核对");
			result.setSuccess(false);
			return result;
		}
		
		/**
		 * 1、验证 验证码有效性
		 */
		/*ValidModel model = tokenService.getValidModel(phone);
		String vCode = model == null ? "" : model.getValidCode();*/
		
		String vCode = validService.getValidCode(phone);
		
		if (!vCode.equals(validCode)) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setMsg("验证码有误");
			result.setSuccess(false);
			return result;
		}
		
		User user = new User();
		user.setUsername(phone);
		user.setPassword(password);
		user.setUserType(userType);
		
		/**
		 * 1、查询是否已经注册过
		 */
		List<User> userList = this.userService.findUserByName(phone, userType);
		
		if (userList != null && userList.size() > 0) {  // 已经存在
			result.setCode(Const.ERROR_DUPLICATE);
			result.setMsg("已注册");
			result.setSuccess(false);
			user.setPassword(null);
			result.setData(user);
		} else {
			int res = 0;
			try {
				res = this.userService.add(user);  // 注册成功返回1
			} catch (Exception e) {
				if (e instanceof DuplicateKeyException) {
					res = 0;
				}
			}
			
			switch (res) {
			case ConstantDBOperateResultTypes.INSERT_SUCCESS:
				result.setCode(200);
				result.setMsg("注册成功");
				result.setSuccess(true);
				
				// 注册成功直接登陆
				TokenModel tm = tokenService.createToken(user);
				user.setToken(tm.getToken());
				user.setPassword(null);
				
				result.setData(user);
				break;
			default:
				result.setCode(Const.ERROR_DUPLICATE);
				result.setMsg("注册失败");
				result.setSuccess(false);
				result.setData(null);
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * @Title:			accountLogin
	 * @Description:	前端用户登录
	 * @param username
	 * @param password
	 */
	@RequestMapping(value = "/login/{username}", method = RequestMethod.POST)
	@ApiOperation(value = "用户登录", httpMethod = "POST", nickname="login", response = ApiResult.class, notes = "根据用户名密码登录", position = 3)
	public ApiResult<User> appLogin(@ApiParam(required = true, name = "username", value = "用户名") @PathVariable("username") String username, 
			@ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password") String password,
			@ApiParam(required = true, name = "userType", value = "密码") @RequestParam(value = "userType") String userType) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户名: " + username + " 密码: " + password);
		
		ApiResult<User> result = new ApiResult<User>();
		result.setOperate(Const.OPERATE_USER_LOGIN);
		
		List<User> userList = this.userService.findByConditionSql(username, userType);
		
		if (userList != null && userList.size() > 0) {  // 存在该用户
			User user = userList.get(0);
			
			if (user != null) {
				if (!password.equals(user.getPassword())) {
					result.setCode(Const.ERROR_PARAM_MISS);
					result.setMsg("密码不正确");
					result.setSuccess(false);
					result.setData(null);
				} else {  // 密码正确
					TokenModel tm = tokenService.createToken(user);
					user.setToken(tm.getToken());
					user.setPassword(null);
					
					result.setCode(Const.INFO_NORMAL);
					result.setMsg("登录成功");
					result.setSuccess(true);
					user.setPassword(null);
					result.setData(user);
				}
			} else {  // 用户不存在
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setMsg("该用户还未注册");
				result.setSuccess(false);
				result.setData(null);
			}
			
		} else {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setMsg("该用户还未注册");
			result.setSuccess(false);
			result.setData(null);
		}
		
		return result;
	}
	
	/**
	 * @Title:			accountLogin
	 * @Description:	前端用户登出
	 * @param username
	 * @param password
	 */
	@ResponseBody 
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ApiOperation(value = "用户注销", httpMethod = "POST", produces =  MediaType.APPLICATION_JSON_VALUE, nickname="logout", response = ApiResult.class, notes = "根据sessionId注销", position = 4)
	public ApiResult<String> accountLogout(@ApiParam(required = true, name = "auth", value = "用户会话唯一编码") @RequestParam("auth") String auth) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户ID: " + auth);
	
		ApiResult<String> result = new ApiResult<String>();
		result.setOperate(Const.OPERATE_USER_LOGOUT);
		
		TokenModel tm = this.tokenService.getToken(auth);
		if (tm == null) {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setData("无效的会话编码");
			return result;
		}
		
		boolean isValid = this.tokenService.checkToken(tm, false);  // 当前注销的userId 和 sessionId 是否有效
		if (isValid) {
			this.tokenService.deleteToken(tm.getUserId());
			result.setCode(Const.INFO_NORMAL);
			result.setData("注销成功");
		} else {
			result.setCode(Const.WARN_OPERATE_FAIL);
			result.setData("注销失败");
		}
		
		return result;
	}
	
}
 