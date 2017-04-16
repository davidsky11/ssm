package com.crm.rest.controller;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.authorization.model.TokenModel;
import com.crm.authorization.service.TokenService;
import com.crm.common.constants.ConstantDBOperateResultTypes;
import com.crm.domain.User;
import com.crm.domain.UserRole;
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
@Controller
@RequestMapping("/v1")
@Api(value = "/v1", description = "用户相关的API")
public class CommonController {
	
	private final Logger log = LoggerFactory.getLogger(CommonController.class);
	
	private final AtomicLong counter = new AtomicLong();
	
	//@Resource(value = "ehcacheTokenService")
	//@Qualifier("ehcacheTokenService")
	@Resource(name = "ehcacheTokenService")
	private TokenService<String, String> tokenService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private ValidService validService;
	
	/**
	 * @Title:			accountLogin
	 * @Description:	前端用户注册
	 * @param phone		可以为手机号码
	 * @param password
	 * @param userType	用户类型 2-经销商，3-APP用户
	 * @param validCode	验证码
	 */
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	@ApiOperation(value = "用户注册", httpMethod = "POST", nickname="register", response = ApiResult.class, notes = "根据用户名密码登录", position = 1)
	public ApiResult register(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone, 
			@ApiParam(required = true, name = "password", value = "密码") @RequestParam(value = "password") String password,
			@ApiParam(required = true, name = "userType", value = "用户类型") @RequestParam(value = "userType") String userType,
			@ApiParam(required = true, name = "validCode", value = "验证码") @RequestParam(value = "validCode") String validCode) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户名: " + phone + " 密码: " + password);
		
		ApiResult result = new ApiResult();
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
		List<User> userList = this.userService.findByNameAndType(phone, userType);
		
		if (userList != null && userList.size() > 0) {  // 已经存在
			result.setCode(Const.ERROR_DUPLICATE);
			result.setMsg("已注册");
			result.setSuccess(false);
			user.setPassword(null);
			result.setData(user);
		} else {
			int res = 0;
			try {
				user.setLocked(Const.USER_UNLOCK);  // 未锁定
				res = this.userService.add(user);  // 注册成功返回1
				
				String userId = user.getId();
				UserRole userRole = new UserRole();
				userRole.setRoleId(userType);
				userRole.setUserId(userId);
				
				userService.saveUserRole(userRole);
			} catch (Exception e) {
				if (e instanceof DuplicateKeyException) {
					res = 2;
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
			case 2:
				result.setCode(Const.ERROR_DUPLICATE);
				result.setMsg("用户已存在");
				result.setSuccess(false);
				result.setData(null);
				break;
			default:
				result.setCode(Const.WARN_OPERATE_FAIL);
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
	@ResponseBody
	@ApiOperation(value = "用户登录", httpMethod = "POST", nickname="login", response = ApiResult.class, notes = "根据用户名密码登录", position = 3)
	public ApiResult appLogin(@ApiParam(required = false, name = "username", value = "用户名") @PathVariable("username") String username, 
			@ApiParam(required = false, name = "password", value = "密码") @RequestParam(required = false, value = "password") String password,
			@ApiParam(required = false, name = "userType", value = "密码") @RequestParam(required = false, value = "userType") String userType,
			@ApiParam(required = false, name = "userId", value = "用户编码") @RequestParam(required = false, value = "userId") String userId,
			@ApiParam(required = false, name = "flagCode", value = "设备码") @RequestParam(required = false, value = "flagCode") String flagCode) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户名: " + username + " 密码: " + password);
		
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_USER_LOGIN);
		User user = null;
		
		/**
		 * 1、首先判断用户id是否对应用户，如果对应，则直接修改用户登录次数，并返回token（针对APP自动登陆情况）
		 */
		user = this.userService.getUserById(userId);
		
		if (user != null) {
			String fc = user.getFlagCode();
			String[] fcArr = (fc == null ? new String[0] : fc.split(","));
			int size = fcArr.length;
			if (size > 0) {
				boolean flag = true;
				for (int i=0; i<size; i++) {
					if (fcArr[i] != null && fcArr[i].equals(flagCode)) {
						flag = false;
						continue;
					}
				}
				if (flag) {
					// 提示用户需要使用用户名+密码登录
					result.setCode(Const.WARN_AUTHORIZATION_FAIL);
					result.setMsg("登录失败，请使用用户名+密码登录");
					result.setSuccess(false);
					user.setPassword(null);
					result.setData(null);
					
					return result;
				}
			} else {
				user.setFlagCode(flagCode);
			}
			
			user.setLoginFrequency(user.getLoginFrequency() == null ? 1 : user.getLoginFrequency() + 1);
			userService.edit(user);
			
			TokenModel tm = tokenService.createToken(user);
			user.setToken(tm.getToken());
			
			result.setCode(Const.INFO_NORMAL);
			result.setMsg("登录成功");
			result.setSuccess(true);
			user.setPassword(null);
			result.setData(user);
			
			return result;
		} 
		
		/**
		 * 2、针对用户使用用户名、密码登陆情况
		 */
		List<User> userList = this.userService.findByNameAndType(username, userType);
		
		if (userList != null && userList.size() > 0) {  // 存在该用户
			user = userList.get(0);
			
			if (user != null) {
				if (!password.equals(user.getPassword())) {
					result.setCode(Const.ERROR_PARAM_MISS);
					result.setMsg("密码不正确");
					result.setSuccess(false);
					result.setData(null);
				} else {  // 密码正确
					if (user.getLocked() == Const.USER_LOCKED) {
						result.setCode(Const.WARN_ACCOUNT_LOCKED);
						result.setMsg("账号涉嫌违规，已被锁定");
						result.setSuccess(false);
						user.setPassword(null);
						result.setData(user);
					} else {
						TokenModel tm = tokenService.createToken(user);
						user.setToken(tm.getToken());
						
						String fc = user.getFlagCode();
						String[] fcArr = (fc == null ? new String[0] : fc.split(","));
						int size = fcArr.length;
						if (size > 0) {
							boolean flag = true;
							for (int i=0; i<size; i++) {
								if (fcArr[i] != null && fcArr[i].equals(flagCode)) {
									flag = false;
									continue;
								}
							}
							if (flag) {
								user.setFlagCode(fc + "," + flagCode);
							}
						} else {
							user.setFlagCode(flagCode);
						}
						
						user.setLoginFrequency(user.getLoginFrequency() == null ? 1 : user.getLoginFrequency() + 1);
						userService.edit(user);
						
						result.setCode(Const.INFO_NORMAL);
						result.setMsg("登录成功");
						result.setSuccess(true);
						user.setPassword(null);
						result.setData(user);
					}
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
	public ApiResult accountLogout(@ApiParam(required = true, name = "auth", value = "用户会话唯一编码") @RequestParam("auth") String auth) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户ID: " + auth);
	
		ApiResult result = new ApiResult();
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
	
	/**
	 * 第三方登录
	 * @Title:			thirdLogin
	 * @Description:	第三方登录
	 * @param username
	 * @param thirdType
	 * @param thirdOpenid
	 * @return
	 */
	@ResponseBody 
	@RequestMapping(value = "/thirdLogin", method = RequestMethod.POST)
	@ApiOperation(value = "第三方用户登录", httpMethod = "POST", produces =  MediaType.APPLICATION_JSON_VALUE, nickname="thirdLogin", response = ApiResult.class, notes = "根据openid和type登录", position = 5)
	public ApiResult thirdLogin(@ApiParam(required = false, name = "username", value = "第三方用户名") @RequestParam(value = "username", required = false) String username, 
			@ApiParam(required = true, name = "thirdType", value = "第三方登录类型") @RequestParam(value = "thirdType") String thirdType,
			@ApiParam(required = true, name = "thirdOpenid", value = "第三方登录Openid") @RequestParam(value = "thirdOpenid") String thirdOpenid) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户名: " + username + " 第三方类型: " + thirdType + " 第三openid： " + thirdOpenid);
		
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_THIRD_LOGIN);
		
		List<User> userList = this.userService.loginByThird(thirdType, thirdOpenid);
		
		if (userList != null && userList.size() > 0) {  // 存在该用户
			User user = userList.get(0);
			
			if (user != null) {
				if (user.getLocked() == Const.USER_LOCKED) {
					result.setCode(Const.WARN_ACCOUNT_LOCKED);
					result.setMsg("账号涉嫌违规，已被锁定");
					result.setSuccess(false);
					user.setPassword(null);
					result.setData(user);
				} else {
					TokenModel tm = tokenService.createToken(user);
					user.setToken(tm.getToken());
					
					user.setLoginFrequency(user.getLoginFrequency() == null ? 1 : user.getLoginFrequency() + 1);
					userService.edit(user);
					
					result.setCode(Const.INFO_NORMAL);
					result.setMsg("登录成功");
					result.setSuccess(true);
					user.setPassword(null);
					result.setData(user);
				}
			} 
		} else {
			// 没有该用户，注册一个用户
			User user = new User();
			user.setCreatorName("admin");  // 创建者
			user.setLastLoginTime(new Date());
			user.setLoginTime(new Date());
			user.setLocked(Const.USER_UNLOCK);  // 默认未锁定
			user.setLoginFrequency(1);
			user.setPassword(Const.DEFAULT_PASS);  // 设置一个默认密码，“888888”
			user.setRegTime(new Date());
			user.setThirdOpenid(thirdOpenid);
			user.setThirdType(thirdType);
			user.setUsername(username);
			user.setUserAlias(username);  // 昵称
			user.setUserType(Const.USERTYPE_APPUSER);
			
			boolean success = userService.add(user) > 0;
			
			if (success) {
				TokenModel tm = tokenService.createToken(user);  // 登录成功
				user.setToken(tm.getToken());
				
				result.setCode(Const.INFO_NORMAL);
				result.setMsg("登录成功");
				result.setSuccess(true);
				user.setPassword(null);
				result.setData(user);
			} else {
				result.setCode(Const.ERROR_SERVER);
				result.setMsg("登录失败");
				result.setSuccess(false);
				user.setPassword(null);
				result.setData(user);
			}
			
		}
		
		return result;
	}
	
}
 