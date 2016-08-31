package com.crm.rest.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.crm.authorization.annotation.Authorization;
import com.crm.common.util.lang.DateUtil;
import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.CodeRepo;
import com.crm.domain.Exchange;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.PageHelper;
import com.crm.rest.domain.ApiResult;
import com.crm.rest.domain.ExchangeQuery;
import com.crm.rest.domain.ScanQuery;
import com.crm.service.ActivityService;
import com.crm.service.AwardService;
import com.crm.service.CodeRepoService;
import com.crm.service.ExchangeService;
import com.crm.service.ScanRecordService;
import com.crm.service.UserService;
import com.crm.service.ValidService;
import com.crm.service.WaresService;
import com.crm.util.RandomUtil;
import com.crm.util.ValidUtil;
import com.crm.util.common.Const;
import com.crm.util.sms.HttpSender;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;

/** 
 * @ClassName	BussinessController.java
 * @Description 厂商接口
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午12:47:45
 * @Version 	V1.0    
 */
@RestController
@RequestMapping("/r/nobuss")
@Api(value = "appuser", description = "User service api", position = 2)
public class NoAuthController {
	
	private static PageHelper page = new PageHelper();

	@Autowired
	private ExchangeService exchangeSercie;
	@Autowired
	private ScanRecordService scanRecordService;
	@Autowired
	private WaresService waresService;
	@Autowired
	private AwardService awardService;
	@Autowired
	private ActivityService activityService;
	@Autowired
	private CodeRepoService codeRepoService;
	@Autowired
	private UserService userService;
	@Autowired
	private ExchangeService exchangeService;
	@Autowired
	private ValidService validService;
	
	@RequestMapping(value = "/codeT", method = RequestMethod.POST)
	public String code1(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone) {
		String validCode = RandomUtil.getRandomNumber(4);
		validService.putValidCode(phone, validCode);
		
		return validCode;
	}
	
	@RequestMapping(value = "/codeC", method = RequestMethod.POST)
	public String check(@RequestParam("phone") String phone, @RequestParam("validCode") String validCode) {
		String vCode = validService.getValidCode(phone);
		System.out.println(" vCode: " + vCode + "    - validCode: " + validCode);
		
		return validCode;
	}
	
	
		
	/**
	 * @Title:			code
	 * @Description:	获取短信验证码（每个手机号每天只能获取十次）
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/code", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<String> code(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone) {
		ApiResult<String> result = new ApiResult<String>();
		result.setOperate(Const.OPERATE_VALID_CODE);
		
		/**
		 * 1、先校验手机号码是否合法
		 */
		if (!ValidUtil.isValidMobile(phone)) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setMsg("手机号码有误，请仔细核对");
			result.setSuccess(false);
			return result;
		}
		
		/**
		 * 2、发送短信
		 */
		String url = Const.SMS_URL;// 应用地址
		String account = Const.SMS_ACCOUNT;// 账号
		String pswd = Const.SMS_PASS;// 密码
		String mobile = phone;// 手机号码，多个号码使用","分割
		
		String validCode = RandomUtil.getRandomNumber(4);
		
		// 发送短信
		String msg = Const.SMS_MSG_HEAD + validCode + Const.SMS_MSG_TAIL;// 短信内容
		boolean needstatus = true; // 是否需要状态报告，需要true，不需要false
		String extno = null;// 扩展码
	
		try {
			String returnString = HttpSender.batchSend(url, account, pswd, mobile, msg, needstatus, extno);
			System.out.println("返回： " + returnString);
			
			// TODO 处理返回值,参见HTTP协议文档
			/*
			 * 返回值类似：   	201608252209002,0
			 * 				1316546486
			 */
			String status = "0";
			String[] strArr = returnString.split("\n");
			if (strArr.length > 0 || strArr[0] != null) {
				status = strArr[0].split(",")[1];
			}
			
			switch (status) {
				case Const.INFO_SMS_SUCCESS:
					result.setCode(Const.INFO_NORMAL);
					result.setMsg("成功向"+phone+"发送验证码： " + validCode);
					result.setSuccess(true);
					result.setData(validCode);
					
					// 缓存  <手机号码 — 验证码>
					/*ValidModel model = new ValidModel();
					model.setPhone(phone);
					model.setValidCode(validCode);
					model.setCreateTime(new Date());
					model.setExpireTime(new Date(new Date().getTime() + Const.VALID_EXPIRES_SECONDS));
					tokenService.saveValidModel(model);*/
					
					validService.putValidCode(phone, validCode);
					
					break;
				case Const.INFO_SMS_TOO_FAST:
					result.setCode(Const.ERROR_REQUEST_TO_FAST);
					result.setMsg("请求太过频繁");
					result.setSuccess(false);
					break;
				case Const.INFO_SMS_PHONE_ERR:  // 107
					result.setCode(Const.ERROR_PARAM_MISS);
					result.setMsg("手机号码格式不对");
					result.setSuccess(false);
					break;
				case Const.INFO_SMS_PHONE_NUMBER_ERR:  // 108
					result.setCode(Const.ERROR_PARAM_MISS);
					result.setMsg("手机号码错误");
					result.setSuccess(false);
					break;
				case Const.INFO_SMS_OUT_TIME:
					result.setCode(Const.ERROR_PARAM_MISS);
					result.setSuccess(false);
					result.setMsg("短信猫发送账号出现问题");
					break;
				default:
					result.setCode(Const.ERROR_SERVER);
					result.setSuccess(false);
					result.setMsg("状态： " + status);
					break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.setCode(Const.ERROR_SERVER);
			result.setMsg("验证码发送出错");
			result.setSuccess(false);
		}
		
		return result;
	}

	/**
	 * @Title:			exchangeRecord
	 * @Description:	APP用户查询自己的兑奖记录
	 * @param userType	用户类型
	 * @param username	用户名
	 * @param beginTime	记录开始时间
	 * @param endTime	记录结束时间
	 * @return
	 */
	@RequestMapping(value = "/exchangeRecord", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<Object> exchangeRecord(@RequestParam("userType") String userType, 
			@RequestParam("username") String username, @RequestParam("beginTime") String beginTime,
			@RequestParam("endTime") String endTime) {
	
		ApiResult<Object> result = new ApiResult<Object>();
		result.setOperate(Const.OPERATE_EXCHANGE_RECORD);
		
		StringBuffer conditionsql = new StringBuffer("");
		
		if (beginTime != null && !beginTime.equals("")) {
			conditionsql.append(" and date(exchangeTime) > '").append(beginTime).append("'");
		}
		
		if (endTime != null && !endTime.equals("")) {
			conditionsql.append(" and date(exchangeTime) < '").append(endTime).append("'");
		}
		
		conditionsql.append(" and accountId in (select id from sysuser where username = '")
			.append(username).append("' and userType = '").append(userType).append("')")
			.append(" order by exchangeTime desc");
		
		List<Exchange> list = this.exchangeSercie.findByCondition(conditionsql.toString());
		
		// 获取所有活动信息
		List<Activity> activityList = this.activityService.getActivityList(null);
		
		List<ExchangeQuery> eqList = new ArrayList<ExchangeQuery>();
		if (list != null && list.size() > 0) {
			
			for (Exchange exchange : list) {
				ExchangeQuery eq = new ExchangeQuery(exchange);
				
				/**
				 * 获取活动信息
				 */
				String publicCode = exchange.getPublicCode();
				Activity activity = null;
				for (Activity act : activityList) {
					if (publicCode.equals(act.getPublicCode())) {
						activity = act;
						break;
					}
				}
				eq.setActivity(activity);
				
				/**
				 * 获取奖品信息
				 */
				String waresId = exchange.getWaresId();
				Wares wares = null;
				if (waresId != null && !waresId.equals(""))	{
					wares = this.waresService.findById(waresId);
					
					String awardId = wares.getAwardId();
					Award award = null;
					if (awardId != null && !awardId.equals("")) {
						award = this.awardService.findById(awardId);
						eq.setTitle(award.getTitle());
					}
					
					eq.setAward(award);
				}
				
				eqList.add(eq);
			}
			
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("获取到 " + list.size() + " 条兑奖记录!");
			result.setData(eqList);
		} else {
			result.setCode(Const.WARN_NO_MORE_DATA);
			result.setSuccess(false);
			result.setMsg("数据加载到头了...");
			result.setData(null);
		}
		
		return result;
	}
	
	/**
	 * @Title:			scanRecord
	 * @Description:	APP用户查询自己的扫描记录（分页）
	 * @param userType
	 * @param username
	 * @param beginTime
	 * @param endTime
	 * @param currentPage	从1开始
	 * @return
	 */
	@RequestMapping(value = "/scanRecord", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<Object> scanRecord(@RequestParam("userType") String userType, 
			@RequestParam("username") String username, @RequestParam("beginTime") String beginTime,
			@RequestParam("endTime") String endTime, @RequestParam("currentPage") int currentPage,
			@RequestParam("countPerPage") int countPerPage) {
	
		ApiResult<Object> result = new ApiResult<Object>();
		result.setOperate(Const.OPERATE_SCAN_RECORD);
		
		DataGrid dg = new DataGrid();
		
		page = new PageHelper();
		if (countPerPage > 0) {
			page.setRows(countPerPage);
		} else {
			page.setRows(Const.DEFAULT_COUNT_PER_PAGE);  // 每页20最多20条数据
		}
		page.setPage(currentPage);
		
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		page.setSort("scanTime");
		
		StringBuffer conditionsql = new StringBuffer("");
		
		if (beginTime != null && !beginTime.equals("")) {
			conditionsql.append(" and date(scanTime) > '").append(beginTime).append("'");
		}
		
		if (endTime != null && !endTime.equals("")) {
			conditionsql.append(" and date(scanTime) < '").append(endTime).append("'");
		}
		
		conditionsql.append(" and (accountId in (select id from sysuser where username = '")
			.append(username).append("' and userType = '").append(userType).append("')")
			.append(" or accountName = '").append(username).append("')");
		
		long total = scanRecordService.getDatagridTotalByCondition(conditionsql.toString());
		
		List<ScanQuery> sqList = new ArrayList<ScanQuery>();
		if (total <= Const.DEFAULT_COUNT_PER_PAGE * (currentPage-1)) {
			result.setCode(Const.WARN_NO_MORE_DATA);
			result.setSuccess(false);
			result.setMsg("数据加载到头了...");
			result.setData(null);
		} else {
			List<ScanRecord> scanRecordList = scanRecordService.getScanRecordList(page, conditionsql.toString());
			for (ScanRecord sr : scanRecordList) {
				
			}
			dg.setRows(scanRecordList);
			
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("获取到 " + scanRecordList.size() + " 条扫描记录!");
			result.setData(dg);
		}
		
		return result;
	}
	
	/**
	 * @Title:			userWithInfo
	 * @Description:	顾客扫码兑奖函数	上传的全部信息
	 * @param userType		用户类型
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
	@RequestMapping(value = "/userWithInfo", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ApiResult<Object> userWithInfo(@RequestParam("userType") String userType, 
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
	    				.append("' and insideCode = '").append(insideCode).append("'");
	    			
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
	@RequestMapping(value = "/antiFake", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	public ApiResult<Object> antiFake(@RequestParam("publicCode") String publicCode, 
			@RequestParam("privateCode") String privateCode, @RequestParam("insideCode") String insideCode) {
		ApiResult<Object> result = new ApiResult<Object>();
		
		if (publicCode != null && privateCode != null && !publicCode.equals("") && !privateCode.equals("") ) {
			
			// 根据publicCode/privateCode/insideCode 查询数据库
			StringBuffer conditionSql = new StringBuffer();
			conditionSql.append(" and publicCode = '").append(publicCode)
				.append("' and privateCode = '").append(privateCode).append("'");
//				.append("' and insideCode = '").append(insideCode).append("'");
			
			/**
			 * 1、获取商品信息
			 */
			Wares wares = null;
			List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
			if (waresList != null && waresList.size() > 0) {
				for (Wares ware : waresList) {
					if (ware.getInsideCode().equals(insideCode)) {
						
					}
				}
			} else {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setMsg("该商品不存在");
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
	@RequestMapping(value = "/deliverGoods", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<Object> deliverGoods(@RequestParam("flag") String flag,
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
			} else {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setSuccess(false);
				result.setMsg("未找到该商品信息 ");
				result.setData(null);
				
				return result;
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
	 * @Title:			awardAnalysis
	 * @Description:	奖项统计  针对经销商和APP用户
	 * @param userType
	 * @param username
	 * @param publicCode	公共编码，对应一个活动
	 * @return
	 * 		输出参数：  	award  --------------  奖项
	 * 					detail --------------  奖项的具体信息
	 * 					total  --------------  该奖项的总个数
	 * 					charge --------------  该奖项已消费的个数
	 */
	@RequestMapping(value = "/awardAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<Object> awardAnalysis( @RequestParam("publicCode") String publicCode) {
	
		ApiResult<Object> result = new ApiResult<Object>();
		result.setOperate(Const.OPERATE_AWARD_ANALYSIS);
		
		/**
		 * 1、获取所有对应publicCode（对应活动）的  可中奖纪录
		 */
		StringBuffer conditionSql = new StringBuffer();
		conditionSql.append(" and publicCode = '").append(publicCode).append("'");
		
		Activity activity = null;
		List<Activity> activityList = activityService.getDatagrid(conditionSql.toString());
		if (activityList != null && activityList.size() > 0) {
			activity = activityList.get(0);
		} else {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(true);
			result.setMsg("没有 " + publicCode + " 对应的活动");
			result.setData(null);
			
			return result;
		}
		
		/**
		 * 2、
		 */
		conditionSql.append(" and awardId <> null");
		Wares wares = null;
		List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
		int total = waresList.size();
		
		Map<String, Integer> map = new HashMap<String, Integer>();  // AwardId - number
		
		Set<String> set = new HashSet<String>();
		for (Wares w : waresList) {
			if (wares.getAwardId() != null && !wares.getAwardId().trim().equals(""))
				set.add(wares.getAwardId().trim());
		}
		
		for (String awardId : set) {
			int count = 0;
			int charge = 0;
			for (Wares w : waresList) {
				if (wares.getAwardId() != null && wares.getAwardId().equals(awardId)) {
					count ++;
				}
			}
			
			map.put(awardId, count);
		}
		
		return null;
	}
	
	/**
	 * @Title:			placeAnalysis
	 * @Description:	地域统计  针对经销商
	 * @param publicCode
	 * @return
	 */
	@RequestMapping(value = "/placeAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<Object> placeAnalysis(@RequestParam("publicCode") String publicCode) {
	
		return null;
	}
	
	/**
	 * @Title:			sellAnalysis
	 * @Description:	销售统计  针对经销商
	 * @param username
	 * @param publicCode
	 * @return
	 */
	@RequestMapping(value = "/sellAnalysis", method = RequestMethod.POST)
	@ResponseBody
	public ApiResult<Object> sellAnalysis(@RequestParam("username") String username, @RequestParam("publicCode") String publicCode) {
	
		return null;
	}
	
}
 