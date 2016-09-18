package com.crm.rest.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.authorization.annotation.Authorization;
import com.crm.common.util.lang.DateUtil;
import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Exchange;
import com.crm.domain.Sale;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.domain.dto.PlaceAnalysis;
import com.crm.domain.dto.ScanRecordDto;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.PageHelper;
import com.crm.domain.po.Address;
import com.crm.domain.po.AddressComponent;
import com.crm.domain.po.Result;
import com.crm.rest.domain.ApiResult;
import com.crm.rest.domain.ExchangeQuery;
import com.crm.rest.domain.ScanQuery;
import com.crm.service.ActivityService;
import com.crm.service.AnalysisService;
import com.crm.service.AwardService;
import com.crm.service.ExchangeService;
import com.crm.service.SaleService;
import com.crm.service.ScanRecordService;
import com.crm.service.UserService;
import com.crm.service.ValidService;
import com.crm.service.WaresService;
import com.crm.util.MapUtil;
import com.crm.util.RandomUtil;
import com.crm.util.Tool;
import com.crm.util.ValidUtil;
import com.crm.util.common.Const;
import com.crm.util.sms.HttpSender;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/** 
 * @ClassName	BussinessController.java
 * @Description 厂商接口
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午12:47:45
 * @Version 	V1.0    
 */
@Controller
@RequestMapping("/v1/nobuss")
@Api(value = "/v1/nobuss", description = "不需要用户权限的API")
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
	private UserService userService;
	@Autowired
	private ExchangeService exchangeService;
	@Autowired
	private ValidService validService;
	@Autowired
	private AnalysisService analysisService;
	@Autowired
	private SaleService saleService;
	
	/**
	 * 测试用
	 * @Title:			code1
	 * @Description:	向缓存中放入一堆 phone - validCode
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/codeT", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "[测试] 向缓存中加入一对 phone-valid 码", httpMethod = "POST", response = ApiResult.class, notes = "向缓存中加入一对 phone-valid 码，valid自动生成")
	public ApiResult code1(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone) {
		ApiResult result = new ApiResult();
		result.setOperate("CODE_PUSH");
		
		String validCode = RandomUtil.getRandomNumber(4);
		validService.putValidCode(phone, validCode);
		
		result.setCode(Const.INFO_NORMAL);
		result.setMsg("向缓存中插入数据成功!");
		result.setSuccess(true);
		result.setData(validCode);
		
		return result;
	}
	
	/**
	 * 测试用
	 * @Title:			check
	 * @Description:	检验验证码
	 * @param phone
	 * @param validCode
	 * @return
	 */
	@RequestMapping(value = "/codeC", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "[测试] 校验 phone-valid 是否能对应上", httpMethod = "POST", response = ApiResult.class, notes = "校验 phone-valid 码正确性")
	public ApiResult check(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone, 
			@ApiParam(required = true, name = "validCode", value = "验证码") @RequestParam("validCode") String validCode) {
		ApiResult result = new ApiResult();
		result.setOperate("CODE_CHECK");
		
		String vCode = validService.getValidCode(phone);
		
		result.setCode(Const.INFO_NORMAL);
		result.setMsg("验证码验证： " + vCode.equals(validCode));
		result.setSuccess(true);
		result.setData(vCode.equals(validCode));
		
		return result;
	}
	
	/**
	 * @Title:			code
	 * @Description:	获取短信验证码（每个手机号每天只能获取十次）
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/code", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取手机验证码", httpMethod = "POST", response = ApiResult.class, notes = "根据手机号码获取四位数的手机验证码")
	public ApiResult code(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone) {
		ApiResult result = new ApiResult();
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
	@ApiOperation(value = "兑奖记录", httpMethod = "POST", response = ApiResult.class, notes = "根据用户名、用户类型、时间查询兑奖记录")
	public ApiResult exchangeRecord(@ApiParam(required = true, name = "userType", value = "用户类型") @RequestParam("userType") String userType, 
			@ApiParam(required = true, name = "username", value = "用户类型") @RequestParam("username") String username, 
			@ApiParam(required = true, name = "beginTime", value = "开始时间") @RequestParam("beginTime") String beginTime,
			@ApiParam(required = true, name = "endTime", value = "结束时间") @RequestParam("endTime") String endTime) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_EXCHANGE_RECORD);
		
		StringBuffer conditionsql = new StringBuffer("");
		
		if (beginTime != null && !beginTime.equals("")) {
			conditionsql.append(" and date(exchangeTime) > '").append(beginTime).append("'");
		}
		
		if (endTime != null && !endTime.equals("")) {
			conditionsql.append(" and date(exchangeTime) < '").append(endTime).append("'");
		}
		
		conditionsql.append(" and userId in (select id from sysuser where username = '")
			.append(username).append("' and userType = '").append(userType).append("')")
			.append(" order by exchangeTime desc");
		
		List<Exchange> list = this.exchangeSercie.findByCondition(conditionsql.toString());
		
		// 获取所有活动信息
		//List<Activity> activityList = this.activityService.getActivityList(null);
		
		/**
		 * 获取当前兑奖记录对应的商品信息
		 */
		Set<String> waresIdSet = new HashSet<String>();
		for (Exchange ex : list) {
			waresIdSet.add(ex.getWaresId().trim());
		}
		
		String[] arr = waresIdSet.toArray(new String[0]);
		String sql = Tool.stringArrayToString(arr, true, ",");
		List<Wares> waresList = new ArrayList<Wares>();
		if (sql != null && !sql.equals("")) {
			waresList = waresService.getDatagrid(" and id in (" + sql + ")");
		}
		
		/**
		 * 获取所有的奖品信息
		 */
		List<Award> awardList = awardService.getDatagrid(null);
		for (Wares w : waresList) {
			for (Award aw : awardList) {
				if (w.getAwardId().equals(aw.getId())) {
					w.setAward(aw);
					continue;
				}
			}
		}
		
		List<ExchangeQuery> eqList = new ArrayList<ExchangeQuery>();
		if (list != null && list.size() > 0) {
			
			for (Exchange exchange : list) {
				ExchangeQuery eq = new ExchangeQuery(exchange);
				
				/**
				 * 获取奖品信息
				 */
				for (Wares wa : waresList) {
					if (exchange.getWaresId().equals(wa.getId())) {
						eq.setAward(wa.getAward());
					}
				}
				
				eqList.add(eq);
			}
			
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("获取到 " + eqList.size() + " 条兑奖记录!");
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
	 * @Description:	APP用户查询自己的扫码记录（分页）
	 * @param userType
	 * @param username
	 * @param beginTime
	 * @param endTime
	 * @param currentPage	从1开始
	 * @return
	 */
	@RequestMapping(value = "/scanRecord", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "扫码记录", httpMethod = "POST", response = ApiResult.class, notes = "根据用户名、用户类型、时间查询分页查询扫码记录")
	public ApiResult scanRecord(@ApiParam(required = true, name = "userType", value = "用户类型") @RequestParam("userType") String userType, 
			@ApiParam(required = true, name = "username", value = "用户名称") @RequestParam("username") String username, 
			@ApiParam(required = true, name = "beginTime", value = "开始时间") @RequestParam("beginTime") String beginTime,
			@ApiParam(required = true, name = "endTime", value = "结束时间") @RequestParam("endTime") String endTime, 
			@ApiParam(required = true, name = "currentPage", value = "当前页数") @RequestParam("currentPage") int currentPage,
			@ApiParam(required = true, name = "countPerPage", value = "每页记录数") @RequestParam("countPerPage") int countPerPage) {
	
		ApiResult result = new ApiResult();
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
			conditionsql.append(" and date(t.scanTime) > '").append(beginTime).append("'");
		}
		
		if (endTime != null && !endTime.equals("")) {
			conditionsql.append(" and date(t.scanTime) < '").append(endTime).append("'");
		}
		
		/*conditionsql.append(" and (t.userId in (select id from sysuser where username = '")
			.append(username).append("' and userType = '").append(userType).append("')")
			.append(" or userName = '").append(username).append("')");*/
		
		conditionsql.append(" and t.userName = '").append(username).append("' and t.userType = '")
			.append(userType).append("'");
		
		long total = scanRecordService.getDatagridTotalByCondition(conditionsql.toString());
		
		List<ScanQuery> sqList = new ArrayList<ScanQuery>();
		if (total <= Const.DEFAULT_COUNT_PER_PAGE * (currentPage-1)) {
			result.setCode(Const.WARN_NO_MORE_DATA);
			result.setSuccess(false);
			result.setMsg("数据加载到头了...");
			result.setData(null);
		} else {
			List<Activity> activityList = this.activityService.getActivityList("");  // 获取所有的活动记录
			List<Award> awardList = this.awardService.getDatagrid("");  // 获取所有的奖项信息
			
			Map<String, Activity> atyMap = new HashMap<String, Activity>();
			for (Activity aty : activityList) {
				atyMap.put(aty.getPublicCode().trim(), aty);
			}
			
			Map<String, Award> awMap = new HashMap<String, Award>();
			for (Award aw : awardList) {
				awMap.put(aw.getId(), aw);
			}
			
			List<ScanRecord> scanRecordList = scanRecordService.getScanRecordList(page, conditionsql.toString());
			List<ScanRecordDto> srdList = new ArrayList<ScanRecordDto>();
			
			// TODO 向扫描记录里添加奖项信息 和 活动信息
			Activity a = null;
			Wares w = null;
			Award aw = null;
			for (ScanRecord sr : scanRecordList) {
				ScanRecordDto srd = new ScanRecordDto(sr);
				
				a = atyMap.get(sr.getPublicCode());
				srd.setActivityName(a == null ? "" : a.getTitle());
				
				w = sr.getWares();
				aw = awMap.get(w == null ? null : w.getAwardId());
				srd.setAwardName(aw == null ? "" : aw.getTitle());
				
				srdList.add(srd);
			}
			
			dg.setRows(srdList);
			
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
	 * @param exchange      是否兑奖
	 * @return
	 */
	@RequestMapping(value = "/userWithInfo", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "扫码兑奖", httpMethod = "POST", response = ApiResult.class, notes = "先扫码，如果有奖，再进行兑奖操作")
	public ApiResult userWithInfo(@ApiParam(required = true, name = "userType", value = "用户类型") @RequestParam("userType") String userType, 
			@ApiParam(required = true, name = "username", value = "用户名称") @RequestParam("username") String username, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode, 
			@ApiParam(required = true, name = "privateCode", value = "唯一码") @RequestParam("privateCode") String privateCode,
			@ApiParam(required = true, name = "insideCode", value = "内码") @RequestParam("insideCode") String insideCode, 
			@ApiParam(required = false, name = "exchange", value = "是否立即兑奖")@RequestParam(value = "exchange", required = false) String exchange) {
		System.out.println("loginWithMessage--userType: " + userType);
		System.out.println("loginWithMessage--username: " + username);
    	System.out.println("loginWithMessage--flagCode: " + flagCode);
    	System.out.println("loginWithMessage--time: " + time);
    	System.out.println("loginWithMessage--longitude: " + longitude);
    	System.out.println("loginWithMessage--latitude: " + latitude);
		System.out.println("loginWithMessage--publicCode: " + publicCode);
    	System.out.println("loginWithMessage--privateCode: " + privateCode);
    	System.out.println("loginWithMessage--insideCode: " + insideCode);
    	
    	StringBuffer conditionSql = new StringBuffer();
    	ApiResult result = new ApiResult();
    	result.setOperate(Const.OPERATE_USER_WITH_INFO);
    	
    	/**
		 * 0、添加扫码记录
		 */
		List<User> userList = userService.findByConditionSql(username, userType);  // 1.找用户
		User user = null;
		if (userList != null && userList.size() > 0) {
			user = userList.get(0);
		}
		
		Address address = null;
		try {
			address = MapUtil.location2Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
		} catch (NumberFormatException e) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("经纬度数据错误!");
			result.setData(null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		ScanRecord sr = this.pushAddress2SR(address);
		sr.setLatitude(Double.parseDouble(latitude));
		sr.setLongitude(Double.parseDouble(longitude));
		sr.setScanTime(new Date());
		if (user != null) {
			sr.setUserId(user.getId());
			sr.setUserName(user.getUsername());
			sr.setUserType(user.getUserType());
		}
		sr.setPublicCode(publicCode);
		sr.setPrivateCode(privateCode);
		sr.setInsideCode(insideCode);
		
		/**
		 * 1、查询该商品信息
		 */
		conditionSql.setLength(0);
		conditionSql.append(" and publicCode = '").append(publicCode).append("' ")
			.append(" and privateCode = '").append(privateCode).append("'");
	
		// 查询商品信息
		Wares wares = null;
		List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
		if (waresList != null && waresList.size() > 0) {
			wares = waresList.get(0);
		}
		
    	switch (userType) {
	    	case Const.USERTYPE_APPUSER:
	    		result.setOperate(Const.OPERATE_APP_SCAN);
	    		
	    		/**
	    		 * 3、检查参数合法性，不合法，直接返回
	    		 */
	    		if (publicCode == null || privateCode == null || insideCode == null
	    				|| publicCode.equals("") || privateCode.equals("") || insideCode.equals("")) {
	    			result.setCode(Const.ERROR_PARAM_MISS);
	    			result.setSuccess(false);
	    			result.setMsg("参数缺失!");
	    			result.setData(null);
	    			
	    			return result;
	    		}
    			
    			if (wares == null) {
    				/* --> 返回值1
    				 */
    				result.setCode(Const.ERROR_NULL_POINTER);
    				result.setSuccess(false);
    				result.setMsg("未找到有效的商品信息");
    				result.setData(null);
    			} else {
    				sr.setWaresId(wares.getId());
    				try {
    					this.scanRecordService.saveScanRecord(sr);
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				
	    			/**
	    			 * 3、判断是否中奖
	    			 */
    				String awardId = wares.getAwardId(); // 获取对应的奖项位ID
    				if (awardId == null || awardId.equals("")) {  // 没有奖
    					if (!wares.getStatus().equals(Const.EX_STATUS_NO_AWARD)) {
	    					wares.setStatus(Const.EX_STATUS_NO_AWARD);
	    					waresService.updateWares(wares);
    					}
    					
    					/* --> 返回值2  未中奖  【200 + false】
    					 */
    					result.setCode(Const.INFO_NORMAL);
    					result.setSuccess(false);
    					result.setMsg("该商品未中奖，欢迎下次惠顾！");
    					result.setData(wares);
    				} else {
    					/* --> 返回值3  中奖  【200 + false】
    					 */
    					result.setCode(Const.INFO_NORMAL);
    					result.setSuccess(true);
    					
    					Award award = awardService.findById(awardId);
    					
    					result.setMsg("您中了" + award.getTitle() + ", " + award.getDescription());
    					result.setData(award);
    					
    					if (exchange != null && !exchange.equals("")) {  // 兑奖
    						/*
    						 * TODO  兑奖处理
    						 * 	先兑奖，再添加兑奖记录
    						 */
    						/**
    						 * I 兑奖
    						 */
    						// TODO 
    						
    						/**
    						 * II 添加兑奖记录
    						 */
    						Exchange ex = new Exchange();
    						ex.setUserId(user.getId());
    						ex.setExchangeTime(DateUtil.formatDate(new Date()));
    						ex.setWaresId(wares.getId());
    						ex.setLongitude(Double.parseDouble(longitude));
    						ex.setLatitude(Double.parseDouble(latitude));
    						ex.setFlagCode(flagCode);
    						ex.setPublicCode(publicCode);
    						ex.setPrivateCode(privateCode);
    						ex.setInsideCode(insideCode);
    						ex.setAward(award);
    						
    						result.setMsg("您中了" + award.getTitle() + ", 系统正在为您开奖！");
        					result.setData(ex);
        					
    						try {
								exchangeSercie.saveExchange(ex);
							} catch (Exception e) {
								e.printStackTrace();
								
								result.setCode(Const.ERROR_SERVER);
								result.setSuccess(false);
								result.setMsg("新增兑奖记录失败!");
							}
    						
    					}
    				}
    				
    			}
	    		
	    		break;
	    	case Const.USERTYPE_VENDER:
	    		result.setOperate(Const.OPERATE_VENDER_SCAN);
	    		
	    		// TODO  商户扫码
	    		try {
		    		/**
		    		 * 1、添加商品数据
		    		 */
		    		/*Wares w = new Wares();
		    		w.setPublicCode(publicCode);
		    		w.setPrivateCode(privateCode);
		    		if (user != null) {
		    			w.setCreater(user.getUsername());
		    			w.setCreater(DateUtil.formatDate(new Date()));
		    		}
		    		w.setStatus(Const.EX_STATUS_UNEXCHANGE);
				
					String waresId = "";
					int i = waresService.addWares(w);
					if (i == 1) {
						waresId = w.getId();
					}*/
	    			
	    			if (wares != null) {
	    				/**
			    		 * 4、添加扫码记录
			    		 */
						sr.setWaresId(wares.getId());
						this.scanRecordService.saveScanRecord(sr);
						
						result.setCode(Const.INFO_NORMAL);
		    			result.setMsg("商户扫码 成功");
		    			result.setSuccess(true);
		    			result.setData(null);
		    			
	    			} else {
	    				result.setCode(Const.ERROR_NULL_POINTER);
	    				result.setSuccess(false);
	    				result.setMsg("未找到有效的商品信息");
	    				result.setData(null);
	    			}
	    			
				} catch (Exception e) {
					e.printStackTrace();
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
	@ApiOperation(value = "商品防伪", httpMethod = "POST", response = ApiResult.class, 
		notes = "privateCode 在数据库中不存在，返回该商品还未生产\nprivateCode 对应的记录已经兑奖，返回该商品已消费\nprivateCode与insideCode不匹配，返回该商品涉嫌伪造\nprivateCode与insideCode匹配，返回该商品为正品，请扫码兑奖")
	public ApiResult antiFake(@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode, 
			@ApiParam(required = true, name = "privateCode", value = "唯一码") @RequestParam("privateCode") String privateCode, 
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam(value = "insideCode", required = false) String insideCode) {
		ApiResult result = new ApiResult();
		
		if (publicCode != null && privateCode != null && !publicCode.equals("") && !privateCode.equals("") ) {
			
			/**
			 * 1、根据publicCode/privateCode 查询数据库， 
			 * 		--> 如果没有记录，则返回“商品不存在”
			 * 		--> 如果有记录，则判断是否已兑奖
			 */
			StringBuffer conditionSql = new StringBuffer();
			conditionSql.append(" and publicCode = '").append(publicCode)
				.append("' and privateCode = '").append(privateCode).append("'");
			
			/**
			 * 1、获取商品信息
			 */
			Wares wares = null;
			List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
			
			if (waresList != null && waresList.size() > 0) {
				wares = waresList.get(0);
			} else {
				/* --> 返回结果1
				 */
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setMsg("该商品不存在");
				result.setSuccess(false);
				result.setData(null);
				
				return result;
			}
			
			if (wares != null) {
				if (wares.getStatus().equals(Const.EX_STATUS_EXCHANGED)) {  // 已兑奖
					/* --> 返回结果2
					 */
					result.setCode(Const.INFO_NORMAL);
					result.setSuccess(true);
					result.setMsg("该商品已消费");
					result.setData(wares);
					
					return result;
				} else {  // 未消费
					/*conditionSql.setLength(0);  // 清空conditionSql
					conditionSql.append(" and publicCode = '").append(publicCode).append("'")
						.append(" and privateCode = '").append(privateCode).append("'")
						.append(" and insideCode = '").append(insideCode).append("'");
					waresList = waresService.getDatagrid(conditionSql.toString());*/
					
					// 如果没有传insideCode，直接返回“无法判断”
					if (insideCode == null || insideCode.equals("")) {
						result.setCode(Const.INFO_NORMAL);
						result.setSuccess(true);
						result.setCode(Const.WARN_NO_JUDGE);
						result.setMsg("无法判断");
						
						return result;
					}
					
					boolean isWaster = true;  // 是否正品，默认true
					// 方法1
					if (wares.getInsideCode().equals(insideCode)) {
						isWaster = false;
					}
					
					// 方法2
					/*for (Wares w : waresList) {
						if (w.getInsideCode().equals(insideCode)) {
							isWaster = false;
							break;
						}
 					}*/
					
					if (isWaster) {
						/* --> 返回结果3
						 */
						result.setCode(Const.INFO_NORMAL);
						result.setSuccess(true);
						result.setCode(Const.ERROR_NOT_EQUALS);
						result.setMsg("该商品涉嫌伪造");
					} else {
						/* --> 返回结果4
						 */
						result.setCode(Const.INFO_NORMAL);
						result.setSuccess(true);
						result.setMsg("该商品为正品，请扫码兑奖");
					}
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
	@ApiOperation(value = "商品跟踪/商品信息", httpMethod = "POST", response = ApiResult.class, notes = "用户通过扫码将瓶身二维码信息传给后台识别")
	public ApiResult deliverGoods(@ApiParam(required = true, name = "flag", value = "操作标识") @RequestParam("flag") String flag,
			@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode,
			@ApiParam(required = true, name = "privateCode", value = "唯一码") @RequestParam("privateCode") String privateCode) {
		
		ApiResult result = new ApiResult();
		
		switch(flag){
		case Const.OPERATE_PRODUCT_TRACE:                     //商品跟踪
			result.setOperate(Const.OPERATE_PRODUCT_TRACE);
			
			/**
			 * 1、获取商品信息
			 */
    		StringBuffer conditionSql = new StringBuffer();
			conditionSql.append(" and publicCode = '").append(publicCode)
				.append("' and privateCode = '").append(privateCode).append("'")
				.append(" ORDER BY scanTime DESC ");  // 按时间逆序
			
			List<ScanRecord> list = scanRecordService.findByCondition(conditionSql.toString());
			
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
			
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("获取商品信息页面URL");
			result.setData("http://www.hclinks.cn/crmnew/static/wares_info.html");
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
	@ApiOperation(value = "奖项统计", httpMethod = "POST", response = ApiResult.class, notes = "统计当前奖项的基本信息和数目信息")
	public ApiResult awardAnalysis(@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_AWARD_ANALYSIS);
		
		/**
		 * 1、获取所有对应publicCode（对应活动）的  活动信息
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
		 * 2、根据活动信息获取奖项信息
		 */
		conditionSql.setLength(0);
		conditionSql.append(" and activityId = '").append(activity.getId()).append("'")
			.append(" order by hierarchy ");
		List<Award> awardList = this.awardService.getDatagrid(conditionSql.toString());
		
		result.setCode(Const.INFO_NORMAL);
		result.setSuccess(true);
		result.setMsg("找到" + awardList.size() + "条奖项信息");
		result.setData(awardList);
		
		return result;
	}
	
	/**
	 * @Title:			placeAnalysis
	 * @Description:	地域统计  针对经销商
	 * @param publicCode
	 * @return
	 */
	@RequestMapping(value = "/placeAnalysis", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "地域统计", httpMethod = "POST", response = ApiResult.class, notes = "统计当前商品在各个地域的销售情况")
	public ApiResult placeAnalysis(@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode) {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_PLACE_ANALYSIS);
		
		List<PlaceAnalysis> paList = analysisService.findPlaceAnalysis(publicCode, Const.USERTYPE_VENDER);
		result.setSuccess(true);
		result.setMsg("查询到" + paList.size() + "条数据.");
		result.setData(paList);
		
		return result;
	}
	
	/**
	 * @Title:			saleAnalysis
	 * @Description:	销售统计  针对经销商
	 * @param username
	 * @param publicCode
	 * @return
	 */
	@RequestMapping(value = "/saleAnalysis", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "销售统计", httpMethod = "POST", response = ApiResult.class, notes = "统计当前商品各个月份的的销售情况（向前推算一年）")
	public ApiResult saleAnalysis(@ApiParam(required = true, name = "username", value = "用户名称") @RequestParam("username") String username,
			@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode) {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_SALE_ANALYSIS);
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);  // 年
		int month = cal.get(Calendar.MONTH);  // 月
		
		int prevYear = year - 1;  // 上年
		int nextMonth = month + 1;
		
		String begin = prevYear + "-" + (month < 10 ? ("0" + month) : month);
		String end = year + "-" + (nextMonth < 10 ? "0" + nextMonth : nextMonth);
		
		StringBuffer conditionSql = new StringBuffer();
		conditionSql
			.append(" and (t.year = '").append(prevYear).append("' and t.month > '").append(month-1).append("') ")
			.append(" or (t.year = '").append(year).append("' and t.month < '").append(month).append("') ");
		
		/**
		 * 1、直接从销售统计表里获取数据
		 */
		List<Sale> saleList = saleService.findSaleListBy(publicCode, conditionSql.toString());
		//List<ScanRecord> srList = this.scanRecordService.findByCondition(conditionSql.toString());
		
		result.setCode(Const.INFO_NORMAL);
		result.setSuccess(true);
		result.setMsg("获取" + begin + "到" + end + " 的销售数据，共计" + saleList.size() + "条！");
		result.setData(saleList);
		
		return result;
	}
	
	private ScanRecord pushAddress2SR(Address address) {
		ScanRecord sr = new ScanRecord();
		Result result = address.getResult();
		AddressComponent ac = null;
		
		if (result != null) {
			ac = result.getAddressComponent();
			sr.setSematicDescription(result.getSematic_description());
			sr.setFormattedAddress(result.getFormatted_address());
		}
		
		if (ac != null) {
			sr.setCountry(ac.getCountry());
			sr.setProvince(ac.getProvince());
			sr.setCity(ac.getCity());
			sr.setDistance(ac.getDistance());
			sr.setStreet(ac.getStreet());
		}
		
		return sr;
	}
}
 