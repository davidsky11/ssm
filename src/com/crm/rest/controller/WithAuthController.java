package com.crm.rest.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.authorization.service.TokenService;
import com.crm.controller.UserController;
import com.crm.rest.domain.ApiResult;
import com.crm.service.AwardService;
import com.crm.service.ExchangeService;
import com.crm.service.UserService;
import com.crm.service.WaresService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/** 
 * @ClassName	SystemController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午12:34:24
 * @Version 	V1.0    
 */
@RestController
@RequestMapping("/r/buss")
@Api(value = "appuser", description = "User service api", position = 2)
public class WithAuthController {
	
	private static final String FIELD = "AppUser";
	
	private final Logger log = LoggerFactory.getLogger(UserController.class);
	
	private final AtomicLong counter = new AtomicLong();
	
	@Autowired
//	@Qualifier("redisTokenService")
	@Qualifier("ehcacheTokenService")
	private TokenService<String, String> tokenService;
	
	@Autowired
	private ExchangeService exchangeService;
	@Autowired
	private AwardService awardService;
	@Autowired
	private UserService userService;
	@Autowired
	private WaresService waresService;
	
	/**
	 * @Title:			scan
	 * @Description:	扫描
	 * @param username
	 * @param password
	 */
	@RequestMapping(value = "/scan", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "商品扫描", httpMethod = "POST", nickname="scan", response = ApiResult.class, notes = "扫描商品二维码", position = 5)
	public ApiResult<String> scan(@ApiParam(required = true, name = "sessionId", value = "会话编号") @RequestHeader("sessionId") String sessionId) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户名: " + sessionId);
	
		ApiResult<String> result = new ApiResult<String>(FIELD);
		result.setCode(200);
		result.setData("扫描成功");
		
		return result;
	}
	
	/**
	 * @Title:			exchange
	 * @Description:	兑奖
	 * @param username
	 * @param password
	 */
	@RequestMapping(value = "/exchange", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "兑奖", httpMethod = "POST", nickname="exchange", response = ApiResult.class, notes = "兑奖", position = 6)
	public ApiResult<String> exchange(@ApiParam(required = true, name = "sessionId", value = "会话编号") @RequestHeader("sessionId") String sessionId) {
		log.debug("记录【" + counter.getAndIncrement() + "】 用户名: " + sessionId);
	
		ApiResult<String> result = new ApiResult<String>(FIELD);
		result.setCode(200);
		result.setData("兑奖成功");
		
		return result;
	}
	
	/**
	 * @Title:			awardRecords2
	 * @Description:	该函数为经销商或者用户扫描记录、兑奖记录查询函数，经销商可以选择起始日期进行查询、普通用户的起始日期为空
	 * 		时间格式为： YYYY/MM/dd
	 * @param role
	 * @param username
	 * @param beginTime	用户选择的具体查询起始时间
	 * @param endTime	终止时间
	 * @return
	 */
	@RequestMapping(value = "/awardRecords2", method = RequestMethod.POST)
	@ResponseBody
	public String awardRecords2(@RequestParam("role") String role, @RequestParam("username") String username,
			@RequestParam("beginTime") String beginTime, @RequestParam("endTime") String endTime) {
		String jsonStr = "[";
		switch(role){
    	case "customer": 
    		jsonStr +=  "{\"time\":\"" + "2016年5月12号"+  "\",\"award\":\"" + "二等奖" +"\",\"detailAward\":\""+ "现金20元"+"\",\"state\":\""+ "已兑奖"+"\"}";
    		jsonStr +=","+ "{\"time\":\"" + "2016年6月13号"+  "\",\"award\":\"" + "一等奖" +"\",\"detailAward\":\""+ "现金30元" + "\"，\"state\":\""+ "未兑奖"+"\"}";
    		jsonStr +=","+ "{\"time\":\"" + "2016年7月2号"+  "\",\"award\":\"" + "三等奖" +"\",\"detailAward\":\""+ "再来一瓶" + "\"，\"state\":\""+ "未兑奖"+"\"}";
    		jsonStr +=","+ "{\"time\":\"" + "2016年7月22号"+  "\",\"award\":\"" + "三等奖" +"\",\"detailAward\":\""+ "再来一瓶" + "\"，\"state\":\""+ "已兑奖"+"\"}";
    	 jsonStr += "]";
    	     break;
    	case"dealer":
    		jsonStr +=  "{\"time\":\"" + "2016年5月12号"+  "\",\"address\":\"" + "湖北武汉" + "\"}";
    		jsonStr +=","+ "{\"time\":\"" + "2016年6月13号"+  "\",\"address\":\"" + "湖北武汉" + "\"}";
    		jsonStr +=","+ "{\"time\":\"" + "2016年7月2号"+  "\",\"address\":\"" + "湖北武汉" + "\"}";
    		jsonStr +=","+ "{\"time\":\"" + "2016年7月22号"+  "\",\"address\":\"" + "湖北武汉" + "\"}";
    		break;
		default:
			break;
		}
		jsonStr += "]";
	     
		return jsonStr;
	}
	
}
 