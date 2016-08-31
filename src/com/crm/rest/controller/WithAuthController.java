package com.crm.rest.controller;

import java.util.ArrayList;
import java.util.List;
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

import com.crm.authorization.annotation.Authorization;
import com.crm.authorization.service.TokenService;
import com.crm.common.util.lang.DateUtil;
import com.crm.controller.UserController;
import com.crm.domain.Award;
import com.crm.domain.CodeRepo;
import com.crm.domain.Exchange;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.rest.domain.ApiResult;
import com.crm.service.AwardService;
import com.crm.service.CodeRepoService;
import com.crm.service.ExchangeService;
import com.crm.service.UserService;
import com.crm.service.WaresService;
import com.crm.util.common.Const;
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
	private CodeRepoService codeRepoService;
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
	 * @Title:			userWithInfo
	 * @Description:	顾客扫码兑奖函数	上传的全部信息
	 * @param role			用户角色
	 * @param username		用户名
	 * @param flagCode		客户端硬件标识码
	 * @param time			客户端时间
	 * @param longitude		经度
	 * @param latitude		纬度
	 * @param publicCode	公共编码
	 * @param privateCode	瓶身编码
	 * @param insideCode	瓶盖内码
	 * @return
	 */
	@RequestMapping(value = "/userWithInfo1", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ApiResult<Object> userWithInfo1(@RequestParam("userType") String userType, 
			@RequestParam("username") String username, @RequestParam("flagCode") String flagCode,
			@RequestParam("time") String time, @RequestParam("longitude") String longitude, 
			@RequestParam("latitude") String latitude, @RequestParam("publicCode") String publicCode, 
			@RequestParam("privateCode") String privateCode, @RequestParam("insideCode") String insideCode) {
		System.out.println("loginWithMessage--userType: " + userType);
		System.out.println("loginWithMessage--username: " + username);
    	System.out.println("loginWithMessage--flagCode: " + flagCode);
    	System.out.println("loginWithMessage--time: " + time);
    	System.out.println("loginWithMessage--longitude: " + longitude);
    	System.out.println("loginWithMessage--latitude: " + latitude);
		System.out.println("loginWithMessage--publicCode: " + publicCode);
    	System.out.println("loginWithMessage--privateCode: " + privateCode);
    	System.out.println("loginWithMessage--insideCode: " + insideCode);
    	
    	ApiResult<Object> result = new ApiResult<Object>();
    	result.setOperate(Const.OPERATE_USER_WITH_INFO);
    	
    	switch (userType) {
	    	case Const.USERTYPE_APPUSER:
	    		List<CodeRepo> list = new ArrayList<CodeRepo>();
	    		
	    		if (publicCode != null && privateCode != null && insideCode != null
	    				&& !publicCode.equals("") && !privateCode.equals("") && !insideCode.equals("")) {
	    			
	    			// 根据publicCode/privateCode/insideCode 查询数据库
	    			StringBuffer conditionSql = new StringBuffer();
	    			conditionSql.append(" and publicCode = '").append(publicCode)
	    				.append("' and privateCode = '").append(privateCode)
	    				.append("' insideCode = '").append(insideCode).append("'");
	    			
	    			/**
	    			 * 1、获取商品信息
	    			 */
	    			Wares wares = null;
	    			List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
	    			if (waresList != null && waresList.size() > 0) {
	    				wares = waresList.get(0);
	    			}
	    			
	    			if (wares == null) {
	    				result.setCode(Const.ERROR_NULL_POINTER);
	    				result.setSuccess(false);
	    				result.setMsg("未找到有效的商品信息");
	    				result.setData(null);
	    			} else {
		    			list = codeRepoService.findByWaresId(wares.getId());
		    			
		    			CodeRepo cr = null;
		    			if (list != null && list.size() > 0) {
		    				cr = list.get(0);
		    			} 
		    			
		    			if (cr == null) {
		    				result.setCode(Const.ERROR_NULL_POINTER);
		    				result.setSuccess(false);
		    				result.setMsg("未找到有效记录");
		    				result.setData(null);
		    			} else {  // 有该记录
		    				result.setCode(Const.INFO_NORMAL);
		    				result.setSuccess(true);
		    				String awardId = cr.getAwardId();  // 获取对应的奖项编码
		    				
		    				if (awardId != null && !awardId.equals("")) {
		    					Award award = this.awardService.findById(awardId);
		    					result.setMsg("恭喜您获取 " + award.getTitle());
		    					result.setData(award);
		    					
		    					// TODO  添加兑奖记录，并兑奖
		    					List<User> userList = this.userService.findUserByName(username, userType);
		    					
		    					User user = null;
		    					if (userList != null && userList.size() > 0) {
		    						user = userList.get(0);
		    					}
		    					
		    					Exchange exchange = new Exchange();
		    					
		    					if (user != null)
		    						exchange.setAccountId(user.getId());
		    					exchange.setExchangeTime(DateUtil.now());
		    					exchange.setLongitude(Double.parseDouble(longitude));
		    					exchange.setLatitude(Double.parseDouble(latitude));
		    					exchange.setInsideCode(insideCode);
		    					exchange.setPrivateCode(privateCode);
		    					exchange.setPublicCode(publicCode);
		    					exchange.setFlagCode(flagCode);
		    					exchange.setStatus(Const.EX_STATUS_UNEXCHANGE);  // 设置状态为未兑奖
			    				
		    					int res = 0;
		    					try {
									res = this.exchangeService.saveExchange(exchange);
								} catch (Exception e) {
									res = 0;
									e.printStackTrace();
								}
		    					
		    					// TODO 定时任务  执行兑奖操作
		    				} else {
		    					result.setMsg("谢谢惠顾");
		    					result.setData(null);
		    				}
		    			}
	    			}
	    		} else {
	    			result.setCode(Const.ERROR_PARAM_MISS);
	    			result.setSuccess(false);
	    			result.setMsg("参数缺失!");
	    			result.setData(null);
	    		}
	    		
	    		break;
	    	case Const.USERTYPE_VENDER:
	    		/**
    			 * 1、获取商品信息
    			 */
	    		StringBuffer conditionSql = new StringBuffer();
    			conditionSql.append(" and publicCode = '").append(publicCode)
    				.append("' and privateCode = '").append(privateCode).append("'");
    			
    			Wares wares = null;
    			List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
    			if (waresList != null && waresList.size() > 0) {
    				wares = waresList.get(0);
    			}
    			
	    		CodeRepo cr = new CodeRepo();
	    		cr.setFlagCode(flagCode);
	    		cr.setScannerName(username);
	    		if (wares != null)
	    			cr.setWaresId(wares.getId());
	    		cr.setScanTime(time);
	    		cr.setLongitude(Double.parseDouble(longitude));
	    		cr.setLatitude(Double.parseDouble(latitude));
	    		
				int i = 0;
				try {
					i = this.codeRepoService.saveCodeRepo(cr);
				} catch (Exception e) {
					i= 0;
					e.printStackTrace();
				}
			
	    		if (i == 1) {
	    			result.setCode(Const.INFO_NORMAL);
	    			result.setMsg("商户扫码 成功");
	    			result.setSuccess(true);
	    			result.setData(cr);
	    			result.setOperate(Const.OPERATE_VENDER_SCAN);
	    		} else {
	    			result.setCode(Const.WARN_OPERATE_FAIL);
	    			result.setMsg("商户扫码 失败");
	    			result.setSuccess(false);
	    			result.setData(cr);
	    			result.setOperate(Const.OPERATE_VENDER_SCAN);
	    		}
	    		
	    		break;
    		default:
    			break;
    	}
		
		return result;
	}
	
	/**
	 * @Title:				antiFake
	 * @Description:		商品防伪函数
	 * 		privateCode 在数据库中不存在，返回该商品还未生产
	 * 		privateCode 对应的记录已经兑奖，返回该商品已消费
	 * 		privateCode与insideCode不匹配，返回该商品涉嫌伪造
	 * 		privateCode与insideCode匹配，返回该商品为正品，请扫码兑奖
	 * @param publicCode
	 * @param privateCode
	 * @param insideCode
	 * @return
	 */
	@RequestMapping(value = "/antiFake1", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ApiResult<Object> antiFake1(@RequestParam("publicCode") String publicCode, 
			@RequestParam("privateCode") String privateCode, @RequestParam("insideCode") String insideCode) {
		ApiResult<Object> result = new ApiResult<Object>();
		
		if (publicCode != null && privateCode != null && insideCode != null
				&& !publicCode.equals("") && !privateCode.equals("") && !insideCode.equals("")) {
			
			// 根据publicCode/privateCode/insideCode 查询数据库
			StringBuffer conditionSql = new StringBuffer();
			conditionSql.append(" and publicCode = '").append(publicCode)
				.append("' and privateCode = '").append(privateCode)
				.append("' and insideCode = '").append(insideCode).append("'");
			
			/**
			 * 1、获取商品信息
			 */
			Wares wares = null;
			List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
			if (waresList != null && waresList.size() > 0) {
				wares = waresList.get(0);
			} else {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setMsg("无法判断");
				result.setSuccess(false);
				result.setData(null);
				
				return result;
			}
			
			List<CodeRepo> list = this.codeRepoService.findByWaresId(wares.getId());
			CodeRepo cr = null;
			
			if (list != null && list.size() > 0) {
				result.setCode(Const.INFO_NORMAL);
				result.setSuccess(true);
				result.setMsg("该商品已消费");
				
				cr = list.get(0);
			} else {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setSuccess(true);
				result.setMsg("该商品还未生产!");
			}
			
			if (cr != null) {
				if (insideCode.equals(wares.getInsideCode())) {
					result.setMsg("该商品为正品，请扫码兑奖");
					result.setData(cr);
				} else {
					result.setCode(Const.ERROR_NOT_EQUALS);
					result.setMsg("该商品涉嫌伪造");
				}
			}
		} else {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("参数缺失!");
			result.setData(null);
		}
		
		return result;
	}
	
	/**
	 * @Title:				deliverGoods
	 * @Description:		为商品跟踪函数    商品信息公用
	 * 		用户通过扫码将瓶身二维码中的瓶身编码发往后台，后台返回信息在前台显示
	 * @param flag
	 * @param publicCode
	 * @param privateCode
	 * @return
	 */
	@RequestMapping(value = "/deliverGoods1", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<Object> deliverGoods1(@RequestParam("flag") String flag,
			@RequestParam("publicCode") String publicCode, @RequestParam("privateCode") String privateCode) {
		
		ApiResult<Object> result = new ApiResult<Object>();
		
		switch(flag){
		case Const.OPERATE_PRODUCT_TRACE:                     //商品跟踪
			result.setOperate(Const.OPERATE_PRODUCT_TRACE);
			
			/**
			 * 1、获取商品信息
			 */
    		StringBuffer conditionSql = new StringBuffer();
			conditionSql.append(" and publicCode = '").append(publicCode)
				.append("' and privateCode = '").append(privateCode).append("'");
			
			Wares wares = null;
			List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
			if (waresList != null && waresList.size() > 0) {
				wares = waresList.get(0);
			}
			
			List<CodeRepo> list = this.codeRepoService.findByWaresId(wares.getId());
			
			if (list != null && list.size() > 0) {
				result.setCode(Const.INFO_NORMAL);
				result.setSuccess(true);
				result.setMsg("获取 " + list.size() + "条商品追踪信息 ");
				result.setData(list);
			} else {
				result.setCode(Const.WARN_NO_MORE_DATA);
				result.setSuccess(true);
				result.setMsg("没有找到商品追踪信息");
				result.setData(null);
			}
			 
			break;
		case Const.OPERATE_PRODUCT_INFO:                   //商品信息
			result.setOperate(Const.OPERATE_PRODUCT_INFO);


		 }
		 
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
 