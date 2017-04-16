package com.crm.rest.controller;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.authorization.annotation.Authorization;
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
import com.crm.domain.system.SysDictionary;
import com.crm.rest.domain.ApiResult;
import com.crm.service.ActivityService;
import com.crm.service.AnalysisService;
import com.crm.service.AwardService;
import com.crm.service.ExchangeService;
import com.crm.service.SaleService;
import com.crm.service.ScanRecordService;
import com.crm.service.SysDictionaryService;
import com.crm.service.UserService;
import com.crm.service.ValidService;
import com.crm.service.WaresService;
import com.crm.util.GsonUtils;
import com.crm.util.MapUtil;
import com.crm.util.RandomUtil;
import com.crm.util.Tool;
import com.crm.util.ValidUtil;
import com.crm.util.common.Const;
import com.crm.util.recharge.PhoneRecharge;
import com.crm.util.recharge.QbRecharge;
import com.crm.util.recharge.ResultBean;
import com.crm.util.sms.HttpSender;
import com.crm.wechat.pay.domain.request.WeixinNormalRedPackRequest;
import com.crm.wechat.pay.domain.response.WeixinRedPackResponse;
import com.crm.wechat.pay.service.IWeixinSendRedPackService;
import com.crm.wechat.pay.service.IWeixinVenderPayService;
import com.crm.wechat.pay.util.WeixinUtils;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

import net.sf.json.JSONObject;

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
	
	private final Logger log = LoggerFactory.getLogger(NoAuthController.class);
	
	private final Integer exchangeRatio = 100; // 兑换比率
	private static PageHelper page = new PageHelper();

	@Resource
	private ExchangeService exchangeSercie;
	@Resource
	private ScanRecordService scanRecordService;
	@Resource
	private WaresService waresService;
	@Resource
	private AwardService awardService;
	@Resource
	private ActivityService activityService;
	@Resource
	private UserService userService;
	@Resource
	private ExchangeService exchangeService;
	@Resource
	private ValidService validService;
	@Resource
	private AnalysisService analysisService;
	@Resource
	private SaleService saleService;
	@Resource
	private SysDictionaryService sysDictionaryService;
	@Resource
	private IWeixinVenderPayService iWeixinVenderPayService;
	@Resource
	private IWeixinSendRedPackService iWeixinSendRedPackService;
	
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

	@RequestMapping(value = "/scanExPoint", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "扫码兑换积分", httpMethod = "POST", response = ApiResult.class, notes = "先扫码，如果有奖，再进行积分兑换")
	public ApiResult scanExPoint(@ApiParam(required = true, name = "userId", value = "公众号userId") @RequestParam("userId") String userId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode, 
			@ApiParam(required = true, name = "privateCode", value = "唯一码") @RequestParam("privateCode") String privateCode,
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam(value = "insideCode", required= false) String insideCode, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn) {
    	
    	ApiResult result = new ApiResult();
    	result.setOperate(Const.OPERATE_USER_WITH_INFO);
    	
    	User user = this.userService.getUserById(userId);  // 查找用户
    	
    	if (user == null) {  // 用户不存在，则直接返回
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(false);
			result.setMsg("需要先绑定手机才能进行兑换.");
			
			return result;
		}
    	
    	/**
		 * 1、查询该商品信息
		 */
		StringBuffer conditionSql = new StringBuffer();
		conditionSql.append(" and publicCode = '").append(publicCode).append("' ")
			.append(" and privateCode = '").append(privateCode).append("'");
	
		// 查询商品信息
		Wares wares = null;
		List<Wares> waresList = waresService.getDatagrid(conditionSql.toString());
		if (waresList != null && waresList.size() > 0) {
			wares = waresList.get(0);
		}
    	
    	/**
		 * 2、添加扫码记录
		 */
		ScanRecord sr = new ScanRecord();
		Address address = null;
		try {
			address = MapUtil.location2Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
			
			sr = this.pushAddress2SR(address);
			sr.setLatitude(Double.parseDouble(latitude));
			sr.setLongitude(Double.parseDouble(longitude));
		} catch (NumberFormatException e) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("经纬度数据错误!");
			result.setData(null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		sr.setScanTime(new Date());
		if (user != null) {
			sr.setUserId(user.getId());
			sr.setUserName(user.getUsername());
			sr.setUserType(user.getUserType());
		}
		
		result.setOperate(Const.OPERATE_APP_SCAN);
		
		/**
		 * 3、检查参数合法性，不合法，直接返回
		 */
		if (wares == null) {
    		result.setCode(Const.ERROR_NULL_POINTER);
    		result.setMsg("该商品不存在");
    		result.setSuccess(false);
    		
    		return result;
    	}
		
		sr.setInsideCode(insideCode);
		sr.setWaresId(wares.getId());
		sr.setPublicCode(wares.getPublicCode());
		sr.setPrivateCode(wares.getPrivateCode());
		try {
			if (writeIn != null && !writeIn.equals("")) {  // “写入”参数非空，则写入数据库
				this.scanRecordService.saveScanRecord(sr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 已消费未中奖
		if (wares.getStatus().equals(Const.EX_STATUS_NO_AWARD)) {  
			result.setCode(Const.INFO_NO_AWARD);
			result.setSuccess(false);
			result.setMsg("该商品已消费[未中奖]!");
			result.setData(wares);
			
			return result;
		}
		
		// 已消费已兑奖
		if (wares.getStatus().equals(Const.EX_STATUS_EXCHANGED)) {  
			result.setCode(Const.INFO_EXCHANGED);
			result.setSuccess(false);
			result.setMsg("该商品已消费[已兑奖]!");
			result.setData(wares);
			
			return result;
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
			result.setCode(Const.INFO_NO_AWARD);
			result.setSuccess(false);
			result.setMsg("该商品未中奖，欢迎下次惠顾！");
			result.setData(wares);
		} else {
			/* --> 返回值3  中奖  【200 + false】
			 */
			Award award = awardService.findById(awardId);
			
			/**
			 * 判断insideCode是否匹配
			 */
			if (insideCode == null || !insideCode.equals(wares.getInsideCode())) {
				result.setCode(Const.INFO_NO_AWARD);
				result.setSuccess(false);
				result.setMsg("兑奖操作，请扫描匹配的瓶盖[瓶盖内码不匹配]");
				
				wares.setInsideCode(null);
				result.setData(wares);
				return result;
			}

			/**
			 * 兑奖处理
			 * 	先兑奖，再添加兑奖记录
			 */
			/**
			 * I 积分兑奖
			 */
			Double amount = award.getAmount();  // 奖项金额
			Integer point = (int) (amount * exchangeRatio);
			
			user.setPoints(user.getPoints() + point);
			user.setNoUsePoints(user.getNoUsePoints() + point);
			
			this.userService.edit(user);
			
			/**
			 * III 添加兑奖记录
			 */
			Exchange ex = this.pushAddress2Ex(address);
			ex.setUserId(user.getId());
			ex.setExchangeTime(new Date());
			ex.setWaresId(wares.getId());
			ex.setLongitude(Double.parseDouble(longitude));
			ex.setLatitude(Double.parseDouble(latitude));
			ex.setFlagCode(flagCode);
			ex.setPublicCode(wares.getPublicCode());
			ex.setPrivateCode(wares.getPrivateCode());
			ex.setInsideCode(insideCode);
			ex.setAward(award);
			ex.setAwardId(awardId);
			ex.setExchangeType("POINT_EXCHANGE");
			
			try {
				exchangeSercie.saveExchange(ex);
			} catch (Exception e) {
				e.printStackTrace();
				
				result.setCode(Const.ERROR_SERVER);
				result.setSuccess(false);
				result.setMsg("新增兑奖记录失败!");
				
				return result;
			}
			
			/**
			 * 修改商品的兑奖状态
			 */
			wares.setStatus(Const.EX_STATUS_EXCHANGED);
			waresService.updateWares(wares);
				
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setData(ex);
		}
		
		return result;
	}
	
	/**
	 * @Title:			scanRecord
	 * @Description:	APP用户查询自己的扫码记录（分页）
	 * @param userType
	 * @param userId
	 * @param beginTime
	 * @param endTime
	 * @param currentPage	从1开始
	 * @return
	 */
	@RequestMapping(value = "/scanRecord", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "扫码记录", httpMethod = "POST", response = ApiResult.class, notes = "根据用户名、用户类型、时间查询分页查询扫码记录")
	public ApiResult scanRecord(@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId, 
			@ApiParam(required = false, name = "beginTime", value = "开始时间") @RequestParam(value = "beginTime", required = false) String beginTime,
			@ApiParam(required = false, name = "endTime", value = "结束时间") @RequestParam(value = "endTime", required = false) String endTime, 
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
		
		conditionsql.append(" and t.userId = '").append(userId).append("'");
		
		long total = scanRecordService.getDatagridTotalByCondition(conditionsql.toString());
		
		/*List<ScanQuery> sqList = new ArrayList<ScanQuery>();*/
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
				if (aty.getPublicCode() != null)
					atyMap.put(aty.getPublicCode().trim(), aty);
			}
			
			Map<String, Award> awMap = new HashMap<String, Award>();
			for (Award aw : awardList) {
				awMap.put(aw.getId(), aw);
			}
			
			List<ScanRecord> scanRecordList = scanRecordService.getScanRecordList(page, conditionsql.toString());
			List<ScanRecordDto> srdList = new ArrayList<ScanRecordDto>();
			
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
	 * @param userId		用户ID
	 * @param flagCode		客户端硬件标识码
	 * @param time			客户端时间
	 * @param longitude		经度
	 * @param latitude		纬度
	 * @param publicCode	公共编码
	 * @param privateCode	瓶身编码
	 * @param insideCode	瓶盖内码
	 * @param exType      	兑奖类型， WECHAT——微信红包，PHONE——手机充值，Q_BILL——Q币
	 * @param recNo			兑奖账号， WECHAT——
	 * @return
	 */
	@RequestMapping(value = "/userWithInfo", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "扫码兑奖", httpMethod = "POST", response = ApiResult.class, notes = "先扫码，如果有奖，再进行兑奖操作")
	public ApiResult userWithInfo(@ApiParam(required = true, name = "userType", value = "用户类型") @RequestParam("userType") String userType, 
			@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode, 
			@ApiParam(required = true, name = "privateCode", value = "唯一码") @RequestParam("privateCode") String privateCode,
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam(value = "insideCode", required= false) String insideCode, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn, 
			@ApiParam(required = false, name = "exType", value = "兑奖类型")@RequestParam(value = "exType", required = false) String exType,
			@ApiParam(required = false, name = "recNo", value = "兑奖账号，如果不设置，就对应当前用户的对应账号")@RequestParam(value = "recNo", required = false) String recNo) {
    	StringBuffer conditionSql = new StringBuffer();
    	ApiResult result = new ApiResult();
    	result.setOperate(Const.OPERATE_USER_WITH_INFO);
    	
    	Activity aty = null;
    	List<Activity> atyList = activityService.getActivityList(" and t.publicCode = '" + publicCode + "'");
    	if (atyList != null && atyList.size() > 0) {
    		aty = atyList.get(0);
    	} else {
    		result.setCode(Const.ERROR_PARAM_MISS);
    		result.setMsg("该publicCode对应的活动不存在");
    		result.setSuccess(false);
    		
    		return result;
    	}
    	
    	/**
		 * 0、添加扫码记录
		 */
    	User user = userService.getUserById(userId);
		if (user == null) {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(false);
			result.setMsg("用户不存在");
			return result;
		}
		
		ScanRecord sr = new ScanRecord();
		Address address = null;
		try {
			address = MapUtil.location2Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
			
			sr = this.pushAddress2SR(address);
			sr.setLatitude(Double.parseDouble(latitude));
			sr.setLongitude(Double.parseDouble(longitude));
		} catch (NumberFormatException e) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("经纬度数据错误!");
			result.setData(null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		sr.setScanTime(new Date());
		if (user != null) {
			sr.setUserId(user.getId());
			sr.setUserName(user.getUsername());
			sr.setUserType(user.getUserType());
		}
		sr.setPublicCode(publicCode);
		sr.setPrivateCode(privateCode);
		
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
	    		sr.setInsideCode(insideCode);
	    		result.setOperate(Const.OPERATE_APP_SCAN);
	    		
	    		/**
	    		 * 2、检查参数合法性，不合法，直接返回
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
    					if (writeIn != null && !writeIn.equals("")) {  // “写入”参数非空，则写入数据库
    						this.scanRecordService.saveScanRecord(sr);
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    				}
    				
    				// 已消费未中奖
    				if (wares.getStatus().equals(Const.EX_STATUS_NO_AWARD)) {  
    					result.setCode(Const.INFO_NO_AWARD);
    					result.setSuccess(false);
    					result.setMsg("该商品已消费[未中奖]!");
    					result.setData(wares);
    					
    					return result;
					}
    				
    				// 已消费已兑奖
    				if (wares.getStatus().equals(Const.EX_STATUS_EXCHANGED)) {  
    					result.setCode(Const.INFO_EXCHANGED);
    					result.setSuccess(false);
    					result.setMsg("该商品已消费[已兑奖]!");
    					result.setData(wares);
    					
    					return result;
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
    					result.setCode(Const.INFO_NO_AWARD);
    					result.setSuccess(false);
    					result.setMsg("该商品未中奖，欢迎下次惠顾！");
    					result.setData(wares);
    				} else {
    					/* --> 返回值3  中奖  【200 + false】
    					 */
    					Award award = awardService.findById(awardId);
    					
    					if (insideCode == null || !insideCode.equals(wares.getInsideCode())) {
    						/**
            				 * 判断insideCode是否匹配
            				 */
        					result.setCode(Const.INFO_NO_AWARD);
        					result.setSuccess(false);
        					result.setMsg("兑奖操作，请扫描匹配的瓶盖[瓶盖内码不匹配]");
        					
        					wares.setInsideCode(null);
        					result.setData(wares);
        					return result;
        				}
    					
    					/**
    					 * 如果不兑奖，直接返回
    					 */
    					if (!Tool.isNotNullOrEmpty(exType)) {
	    					result.setCode(Const.INFO_NORMAL);
	    					result.setSuccess(true);
	    					
	    					result.setMsg("您中了" + award.getTitle() + ", " + award.getDescription());
	    					result.setData(award);
	    					return result;
    					}
    					
    					int remain = award.getRemain();  // 该奖项剩余数目
    					
    					/**
						 * 兑奖处理
						 * 	先兑奖，再添加兑奖记录
						 */
						/**
						 * I 兑奖
						 */
    					switch (exType) {
    					case Const.EX_WECHAT:
    						String openid = user.getWxOpenId();

    						if (Tool.isNullOrEmpty(recNo) && Tool.isNullOrEmpty(openid)) {
    							result.setCode(Const.WARN_NO_WECHAT);
    							result.setSuccess(false);
    							result.setMsg("请为用户绑定合适的微信号或设置一个接收红包的微信openid!");
    							
    							return result;
    						}
    						
    						Integer amount1 = new Double(award.getAmount() * 100).intValue();
    						
    						/**
    						 * 1、微信企业付款
    						 */
    						/*WeixinVenderPayRequest request = new WeixinVenderPayRequest();
    				    	request.setOpenid(Tool.isNotNullOrEmpty(recNo) ? recNo : openid);  // 如果recNo没有设置，则使用openid
    				    	request.setAmount(amount1);
    				    	request.setRe_user_name(user.getWeixin() + "_0001");
    				    	request.setDesc("企业兑奖给" + user.getWeixin());
    				    	request.setSpbill_create_ip(WeixinUtils.getHostIp());
    				    	
    				    	WeixinVenderPayResponse response = new WeixinVenderPayResponse();
    				    	try {
    							response = iWeixinVenderPayService.venderPay(request);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}*/
    				    	
    						/**
    						 * 2、微信红包
    						 */
    						WeixinNormalRedPackRequest request = new WeixinNormalRedPackRequest();
    				    	request.setRe_openid(Tool.isNotNullOrEmpty(recNo) ? recNo : openid);  // 如果recNo没有设置，则使用openid
    				    	request.setTotal_amount(amount1);
    				    	request.setTotal_num(1);
    				    	request.setSend_name(aty != null ? aty.getTitle() : "快乐兑");
    				    	request.setAct_name(aty != null ? aty.getTitle() : "快乐兑");
    				    	request.setWishing("恭喜您在活动【" + (aty != null ? aty.getTitle() : "快乐兑") + "】中扫码抽中了这个微信红包！");
    				    	request.setRemark(user.getWeixin());
    				    	request.setClient_ip(WeixinUtils.getHostIp());
    				    	
    				    	WeixinRedPackResponse response = new WeixinRedPackResponse();
    				    	try {
    							response = iWeixinSendRedPackService.sendRedPack(1, request);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
    				    	
    				    	/**
    				    	 * 兑奖成功
    				    	 */
    				    	if (null != response && Tool.isNotNullOrEmpty(response.getReturn_code()) && response.getReturn_code().equals("SUCCESS") 
    				    			&& Tool.isNotNullOrEmpty(response.getResult_code()) && response.getResult_code().equals("SUCCESS")) {
    				    		result.setMsg("您中了" + award.getTitle() + ", 系统正在为您开奖！");
    				    		result.setData(response.getMch_billno());
    				    	}
    				    	result.setMsg("您中了" + award.getTitle() + ", 系统正在为您开奖！");
    				    	
    						break;
    					case Const.EX_PHONE:
    						String phone = user.getTelephone();
    						
    						/**
    						 * 设置正确的手机号码
    						 */
    						if (Tool.isNullOrEmpty(recNo) && Tool.isNullOrEmpty(phone) && ValidUtil.isValidMobile(phone)) {
    							result.setCode(Const.WARN_PHONE_ERROR);
    							result.setSuccess(false);
    							result.setMsg("手机号码不正确，请为用户绑定合适的手机号或者直接指定一个待充值的手机号码!");
    							
    							return result;
    						}
    						
    						String result2 = "";
    						Integer amount2 = 0;
    						try {
    							amount2 = new Double(award.getAmount()).intValue();
    							result2 = PhoneRecharge.onlineOrder(Tool.isNotNullOrEmpty(recNo) ? recNo : phone, 
    									amount2);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
    						
    						System.out.println(result2);
    						if (Tool.isNotNullOrEmpty(result2)) {
    							ResultBean rb1 = GsonUtils.fromJson(result2, ResultBean.class);
    							if (rb1.getError_code() != null && rb1.getError_code().equals("0")) {
	    							result.setSuccess(true);
	    							result.setMsg("手机充值" + amount2 + "元，请稍后查询充值结果");
	    							result.setData(rb1 == null ? null : (rb1.getResult() == null ? null : rb1.getResult().getUorderid()));
    							} else {
    								result.setSuccess(false);
    								result.setMsg(rb1.getReason() == null ? "手机充值失败" : rb1.getReason());
    								
    								return result;
    							}
    					
    						} else {
    							result.setSuccess(false);
    							result.setMsg("充值失败");
    							
    							return result;
    						}
    						
    						
    						break;
    					case Const.EX_Q_BILL:
    						String qq = user.getQq();
    						
    						// TODO 设置正确的手机号
    						if (Tool.isNullOrEmpty(recNo) && Tool.isNullOrEmpty(qq)) {
    							result.setCode(Const.WARN_NO_QQ);
    							result.setSuccess(false);
    							result.setMsg("请为用户绑定合适的QQ号或者指定一个待充值的qq号码!");
    							
    							return result;
    						}
    						
    						String res = "";
    						Integer amount3 = 0;
    						try {
    							amount3 = new Double(award.getAmount()).intValue();
    							res = QbRecharge.qbRecharge(Tool.isNotNullOrEmpty(recNo) ? recNo : qq, amount3);
    						} catch (Exception e) {
    							e.printStackTrace();
    						}
    						
    						System.out.println(res);
    						if (Tool.isNotNullOrEmpty(res)) {
    							ResultBean rb2 = GsonUtils.fromJson(res, ResultBean.class);
    							if (rb2.getError_code() != null && rb2.getError_code().equals("0")) {
	    							result.setSuccess(true);
	    							result.setMsg("Q币充值" + amount3 + "元，请稍后查询充值结果");
	    							result.setData(rb2 == null ? null : (rb2.getResult() == null ? null : rb2.getResult().getUorderid()));
    							} else {
    								result.setSuccess(false);
    								result.setMsg(rb2.getReason() == null ? "Q币充值失败" : rb2.getReason());
    							
    								return result;
    							}
    						} else {
    							result.setSuccess(false);
    							result.setMsg("充值失败");
    							
    							return result;
    						}
    						
    						break;
    					case Const.EX_POINT:  // 积分兑换
    						/**
    						 * 积分兑奖
    						 */
    						Double amount = award.getAmount();  // 奖项金额
    						Integer point = (int) (amount * exchangeRatio);
    						
    						user.setPoints(user.getPoints() + point);
    						user.setNoUsePoints(user.getNoUsePoints() + point);
    						
    						this.userService.edit(user);
    						
    						break;
    					default:
    						result.setCode(Const.ERROR_PARAM_MISS);
							result.setSuccess(false);
							result.setMsg("请选择合适的兑奖类型!");
							
							return result;
    					}
    					
						/**
						 * II 添加兑奖记录
						 */
						Exchange ex = this.pushAddress2Ex(address);
						ex.setUserId(user.getId());
						ex.setExchangeTime(new Date());
						ex.setWaresId(wares.getId());
						ex.setLongitude(Double.parseDouble(longitude));
						ex.setLatitude(Double.parseDouble(latitude));
						ex.setFlagCode(flagCode);
						ex.setPublicCode(publicCode);
						ex.setPrivateCode(privateCode);
						ex.setInsideCode(insideCode);
						ex.setAward(award);
						ex.setAwardId(awardId);
						ex.setExchangeType(exType);
						ex.setBeneficiary(recNo);
						
    					//result.setData(ex);
						result.setData(award);
    					
						try {
							exchangeSercie.saveExchange(ex);
						} catch (Exception e) {
							e.printStackTrace();
							
							result.setCode(Const.ERROR_SERVER);
							result.setSuccess(false);
							result.setMsg("新增兑奖记录失败!");
							
							return result;
						}
						
						/**
    					 * 修改商品的兑奖状态
    					 */
    					wares.setStatus(Const.EX_STATUS_EXCHANGED);
    					waresService.updateWares(wares);
    					
    					/**
    					 * 修改奖项剩余数目
    					 */
    					if (remain > 0) {
    				    	award.setRemain(remain - 1);
    				    	awardService.updateAward(award);
				    	}
    						
    				}
    				
    			}
	    		
	    		break;
	    	case Const.USERTYPE_DEALER:
	    		result.setOperate(Const.OPERATE_VENDER_SCAN);
	    		
	    		// 商户扫码
	    		try {
	    			if (wares != null) {
	    				/**
			    		 * 4、添加扫码记录
			    		 */
						sr.setWaresId(wares.getId());
						this.scanRecordService.saveScanRecord(sr);
						
						result.setCode(Const.INFO_NORMAL);
		    			result.setMsg("商户扫码 成功");
		    			result.setSuccess(true);
		    			result.setData(sr);
		    			
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
			@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn, 
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam(value = "insideCode", required = false) String insideCode) {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_ANTI_FAKE);
		
		ScanRecord sr = new ScanRecord();
		Address address = null;
		try {
			address = MapUtil.location2Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
			
			sr = this.pushAddress2SR(address);
			sr.setLatitude(Double.parseDouble(latitude));
			sr.setLongitude(Double.parseDouble(longitude));
		} catch (NumberFormatException e) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("经纬度数据错误!");
			result.setData(null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		sr.setScanTime(new Date());
		sr.setInsideCode(insideCode);
		
		User user = this.userService.getUserById(userId);

		if (user != null) {
			sr.setUser(user);
			sr.setUserId(user.getId());
			sr.setUserName(user.getUsername());
		} else {
			sr.setUserType("3");
			if (userId.equalsIgnoreCase("IOS")) {
				sr.setUserName("IOS用户");
			}
			if (userId.equalsIgnoreCase("Android")) {
				sr.setUserName("Android用户");
			}
		}
		
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
			
			sr.setPublicCode(publicCode);
			sr.setPrivateCode(privateCode);
			
			// 写入扫码记录
			if (Tool.isNotNullOrEmpty(writeIn)) {
				try {
					this.scanRecordService.saveScanRecord(sr);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if (wares != null) {
				sr.setWares(wares);
				sr.setWaresId(wares.getId());
				
				if (!wares.getStatus().equals(Const.EX_STATUS_UNEXCHANGE)) {  // 未中奖、已兑奖
					/* --> 返回结果2
					 */
					result.setCode(Const.INFO_NORMAL);
					result.setSuccess(true);
					result.setMsg("该商品已消费");
					result.setData(wares);
					
					return result;
				} else {  // 未消费
					// 如果没有传insideCode，直接返回“无法判断”
					if (insideCode == null || insideCode.equals("")) {
						result.setCode(Const.WARN_NO_JUDGE);
						result.setSuccess(true);
						result.setMsg("该商品未消费");
						
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
		
		if (Tool.isNotNullOrEmpty(writeIn)) {
			try {
				scanRecordService.saveScanRecord(sr);
			} catch (Exception e) {
				e.printStackTrace();
			}
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
			@ApiParam(required = true, name = "privateCode", value = "唯一码") @RequestParam("privateCode") String privateCode,
			@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn) {
		
		ApiResult result = new ApiResult();
		
		ScanRecord sr = new ScanRecord();
		Address address = null;
		try {
			address = MapUtil.location2Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
			
			sr = this.pushAddress2SR(address);
			sr.setLatitude(Double.parseDouble(latitude));
			sr.setLongitude(Double.parseDouble(longitude));
		} catch (NumberFormatException e) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("经纬度数据错误!");
			result.setData(null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		sr.setScanTime(new Date());
		
		User user = this.userService.getUserById(userId);
		
		if (user != null) {
			sr.setUser(user);
			sr.setUserId(user.getId());
			sr.setUserName(user.getUsername());
		} else {
			sr.setUserType("3");
			if (userId.equalsIgnoreCase("IOS")) {
				sr.setUserName("IOS用户");
			}
			if (userId.equalsIgnoreCase("Android")) {
				sr.setUserName("Android用户");
			}
		}
		
		sr.setPublicCode(publicCode);
		sr.setPrivateCode(privateCode);
		
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

			Activity aty = null;
	    	List<Activity> atyList = activityService.getActivityList(" and t.publicCode = '" + publicCode + "'");
	    	if (atyList != null && atyList.size() > 0) {
	    		aty = atyList.get(0);
	    	}
			
			result.setOperate(Const.OPERATE_PRODUCT_INFO);
			
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("获取活动海报页面URL");
			
			if (aty == null) {
				result.setData(Const.ROOT_HTML_URL + "template.html");
			} else {
				sr.setActivity(aty);
				sr.setActivityName(aty.getTitle());
				result.setData(aty.getInfoUrl());
			}
			
		 }
		
		if (Tool.isNotNullOrEmpty(writeIn)) {
			try {
				this.scanRecordService.saveScanRecord(sr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		 return result;
	}
	
	/**
	 * @Title:			awardAnalysis
	 * @Description:	奖项统计  针对经销商和APP用户
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
	public ApiResult awardAnalysis(@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode,
			@ApiParam(required = true, name = "privateCode", value = "唯一码") @RequestParam("privateCode") String privateCode, 
			@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn) {
		
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
		 * 1.1、添加扫码记录
		 */
		ScanRecord sr = new ScanRecord();
		Address address = null;
		try {
			address = MapUtil.location2Address(Double.parseDouble(latitude), Double.parseDouble(longitude));
			
			sr = this.pushAddress2SR(address);
			sr.setLatitude(Double.parseDouble(latitude));
			sr.setLongitude(Double.parseDouble(longitude));
		} catch (NumberFormatException e) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("经纬度数据错误!");
			result.setData(null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		sr.setActivity(activity);
		sr.setScanTime(new Date());
		sr.setPublicCode(publicCode);
		sr.setPrivateCode(privateCode);
		
		sr.setScanTime(new Date());
		
		User user = this.userService.getUserById(userId);
		
		if (user != null) {
			sr.setUser(user);
			sr.setUserId(user.getId());
			sr.setUserName(user.getUsername());
		} else {
			sr.setUserType("3");
			if (userId.equalsIgnoreCase("IOS")) {
				sr.setUserName("IOS用户");
			}
			if (userId.equalsIgnoreCase("Android")) {
				sr.setUserName("Android用户");
			}
		}
		
		if (Tool.isNotNullOrEmpty(writeIn)) {
			try {
				this.scanRecordService.saveScanRecord(sr);
			} catch (Exception e) {
				
			}
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
	public ApiResult placeAnalysis(@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode,
			@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId) {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_PLACE_ANALYSIS);
		
		List<PlaceAnalysis> paList = analysisService.findPlaceAnalysis(Const.LEVEL_PLACE_PROVINCE, publicCode, "", "", userId);
		result.setSuccess(true);
		result.setMsg("查询到" + paList.size() + "条数据.");
		result.setData(paList);
		
		return result;
	}
	
	/**
	 * @Title:			saleAnalysis
	 * @Description:	销售统计  针对经销商
	 * @param userId
	 * @param publicCode
	 * @return
	 */
	@RequestMapping(value = "/saleAnalysis", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "销售统计", httpMethod = "POST", response = ApiResult.class, notes = "统计当前商品各个月份的的销售情况（向前推算一年）")
	public ApiResult saleAnalysis(@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId,
			@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode) {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_SALE_ANALYSIS);
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);  // 年
		int month = cal.get(Calendar.MONTH);  // 月  月份数字会-1
		
		int prevYear = year - 1;  // 上年
		int nextMonth = month + 1;
		
		String begin = prevYear + "-" + (month < 10 ? ("0" + month) : month);
		String end = year + "-" + (nextMonth < 10 ? "0" + nextMonth : nextMonth);
		
		List<Activity> atyList = activityService.getActivityList(" and t.publicCode = '" + publicCode + "'");
		Set<String> atyIdSet = new HashSet<String>();
		
		for (Activity aty : atyList) {
			atyIdSet.add(aty.getId().trim());
		}
		String[] atyIdArr = atyIdSet.toArray(new String[0]);
		String atyIds = Tool.stringArrayToString(atyIdArr, true, ",");
		
		StringBuffer conditionSql = new StringBuffer();
		conditionSql
			.append(" and ((t.year = '").append(prevYear).append("' and t.month > '").append(month).append("') ")
			.append(" or (t.year = '").append(year).append("' and t.month <= '").append(month+1).append("')) ")
			.append(" and t.userId = '").append(userId).append("'");
		
		/**
		 * 1、直接从销售统计表里获取数据
		 */
		List<Sale> saleList = saleService.findSaleListBy(atyIds, conditionSql.toString());
		//List<ScanRecord> srList = this.scanRecordService.findByCondition(conditionSql.toString());
		
		Collections.sort(saleList);
		
		result.setCode(Const.INFO_NORMAL);
		result.setSuccess(true);
		result.setMsg("获取" + begin + "到" + end + " 的销售数据，共计" + saleList.size() + "条！");
		result.setData(saleList);
		
		return result;
	}
	
	/**
	 * 获取兑奖方式列表
	 * @Title:			saleAnalysis
	 * @Description:	获取兑奖方式列表
	 * @param userId
	 * @param publicCode
	 * @return
	 */
	@RequestMapping(value = "/exchangeType", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "获取兑奖类型", httpMethod = "POST", response = ApiResult.class, notes = "获取系统当前支持的兑奖类型列表")
	public ApiResult exchangeType() {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_EXCHANGE_TYPE);
		List<SysDictionary> dicList = sysDictionaryService.getDicList(" and parentid = 1");
		
		List<ExchargeType> typeList = new ArrayList<ExchargeType>();
		for (SysDictionary dic : dicList) {
			ExchargeType type = new ExchargeType();
			type.setCode(dic.getEntrycode());
			type.setName(dic.getEntryvalue());
			
			typeList.add(type);
		}
		
		result.setCode(Const.INFO_NORMAL);
		result.setSuccess(true);
		result.setMsg("获取到" + dicList.size() + "条兑奖类别信息");
		result.setData(typeList);
		
		return result;
	}
	
	private ScanRecord pushAddress2SR(Address address) {
		ScanRecord sr = new ScanRecord();
		if (address == null)	return sr;
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
	
	private Exchange pushAddress2Ex(Address address) {
		Exchange ex = new Exchange();
		if (address == null)	return ex;
		Result result = address.getResult();
		AddressComponent ac = null;
		
		if (result != null) {
			ac = result.getAddressComponent();
			ex.setSematicDescription(result.getSematic_description());
			ex.setFormattedAddress(result.getFormatted_address());
		}
		
		if (ac != null) {
			ex.setCountry(ac.getCountry());
			ex.setProvince(ac.getProvince());
			ex.setCity(ac.getCity());
			ex.setDistance(ac.getDistance());
			ex.setStreet(ac.getStreet());
		}
		
		return ex;
	}
	
	/**
	 * 兑奖操作
	 * @Title:			exchange
	 * @Description:	兑奖操作
	 * @param type		兑奖类型
	 * @param receiver	接收方
	 * @param amount	金额（单位：分） > 100  --> 微信/手机
	 * 					个数（单位：个）               --> Q币
	 * @return
	 */
	/*private boolean exchange(String type, String receiver, Integer amount) {
		boolean flag = false;
		switch (type) {
		case Const.EX_WECHAT:  // 微信兑奖
			WeixinVenderPayRequest request = new WeixinVenderPayRequest();
	    	request.setOpenid(receiver);
	    	request.setAmount(amount);
	    	request.setRe_user_name(receiver + "_0001");
	    	request.setDesc("企业兑奖给" + receiver);
	    	request.setSpbill_create_ip(WeixinUtils.getHostIp());
	    	
	    	WeixinVenderPayResponse response = new WeixinVenderPayResponse();
	    	try {
				response = iWeixinVenderPayService.venderPay(request);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    	
	    	if (response.getReturn_code().equals("SUCCESS") 
	    			&& response.getResult_code().equals("SUCCESS")) {
	    		flag = true;
	    	}
			
			break;
		case Const.EX_PHONE:  // 手机充值
			String result = "";
			
			try {
				result = PhoneRecharge.onlineOrder(receiver, amount);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// TODO 解析result，返回结果
			
			break;
		case Const.EX_Q_BILL:  // Q币充值
			String res = "";
			try {
				res = QbRecharge.qbRecharge(receiver, amount);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			// TODO 解析res，返回结果
			
			break;
		default:
			break;
		}
		
		return flag;
	}
	*/
	/**
	 * 充值结果查询
	 * @Title:			orderQuery
	 * @Description:	充值结果查询
	 * @param type
	 * @param orderId
	 * @return
	 */
	@RequestMapping(value = "/orderQuery", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "充值结果查询", httpMethod = "POST", response = ApiResult.class, notes = "充值接口查询")
	public ApiResult orderQuery(@ApiParam(required = true, name = "type", value = "充值类型") @RequestParam("type") String type,
			@ApiParam(required = true, name = "orderId", value = "订单号") @RequestParam("orderId") String orderId) {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_EXCHANGE_QUERY);
		
		String res = "";
		
		switch (type) {
			case Const.EX_PHONE:
				try {
					res = PhoneRecharge.orderSta(orderId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case Const.EX_Q_BILL:
				try {
					res = QbRecharge.orderSta(orderId);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			default:
					
				break;
		}
		
		int error_code = JSONObject.fromObject(res).getInt("error_code");
		switch (error_code) {
		case 0:
			result.setMsg("充值中");
			break;
		case 1:
			result.setMsg("充值成功");
			break;
		case 9:
			result.setMsg("充值失败");
			break;
		}
		
		return result;
	}
	
	/**
	 * @Title:			forgetPass
	 * @Description:	忘记密码（每个手机号每天只能获取十次）
	 * @param phone
	 * @return
	 */
	@RequestMapping(value = "/forgetPass", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "忘记密码", httpMethod = "POST", response = ApiResult.class, notes = "忘记密码")
	public ApiResult forgetPass(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone) {
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_FORGET_PASS);
		
		/**
		 * 0、查询是否存在该用户
		 */
		List<User> userList = userService.findByNameAndType(phone, "3");
		if (userList == null || userList.size() <= 0) {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setMsg("该手机号没有对应的注册用户");
			result.setSuccess(false);
			
			return result;
		}
		
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
	 * 重置密码
	 * @Title:			resetPass
	 * @Description:	重置密码
	 * @param phone
	 * @param validCode
	 * @param pass
	 * @return
	 */
	@RequestMapping(value = "/resetPass", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "重置密码", httpMethod = "POST", response = ApiResult.class, notes = "重置密码")
	public ApiResult resetPass(@ApiParam(required = false, name = "phone", value = "手机号码") @RequestParam(required = false, value = "phone") String phone,
			@ApiParam(required = false, name = "validCode", value = "验证码") @RequestParam(required = false, value = "validCode") String validCode,
			@ApiParam(required = false, name = "pass", value = "密码") @RequestParam(required = false, value = "pass") String pass,
			@ApiParam(required = false, name = "userId", value = "用户编码") @RequestParam(required = false, value = "userId") String userId,
			@ApiParam(required = false, name = "flagCode", value = "设备码") @RequestParam(required = false, value = "flagCode") String flagCode) {
		log.info("手机号为 " + phone + " 的用户正在修改密码...");
		
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_FORGET_PASS);
		
		/**
		 * 1、校验验证码
		 */
		String vCode = validService.getValidCode(phone);
		if (!vCode.equals(validCode)) {
			result.setMsg("验证码不正确");
			result.setSuccess(false);
			result.setCode(Const.ERROR_PARAM_MISS);
			
			return result;
		}
		
		/**
		 * 2、
		 */
		User user = null;
		List<User> userList = null;
		
		user = this.userService.getUserById(userId);
		if (user == null) {
			userList = userService.findByNameAndType(phone, "3");
		
			if (userList == null || userList.size() <= 0) {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setMsg("该手机号没有对应的注册用户");
				result.setSuccess(false);
				
				return result;
			} else {
				user = userList.get(0);
				// 重置密码
				user.setPassword(pass);
				user.setFlagCode(flagCode);
				user.setLoginFrequency(user.getLoginFrequency() == null ? 1 : user.getLoginFrequency() + 1);
			}
		} else {
			// 重置密码
			user.setPassword(pass);
			user.setFlagCode(flagCode);
			user.setLoginFrequency(user.getLoginFrequency() == null ? 1 : user.getLoginFrequency() + 1);
		}
		
		/**
		 * 3、更新用户密码
		 */
		boolean success = userService.edit(user) > 0;
		if (success) {
			result.setCode(Const.INFO_NORMAL);
			result.setMsg("重置密码成功");
			result.setSuccess(true);
			
			user.setPassword(null);
			result.setData(user);
			
			return result;
		} else {
			result.setCode(Const.ERROR_SERVER);
			result.setMsg("重置密码失败");
			result.setSuccess(false);
		}
	
		return result;
	}
	
	@RequestMapping(value = "/setInsideCode", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "设置瓶盖内码", httpMethod = "POST", response = ApiResult.class, notes = "根据publicCode、privateCode设置insideCode")
	public ApiResult setInsideCode(@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode, 
			@ApiParam(required = true, name = "privateCode", value = "瓶身码") @RequestParam("privateCode") String privateCode,
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam("insideCode") String insideCode) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_SET_INSIDECODE);
		
		Wares wares = null;
		List<Wares> waresList = waresService.getDatagrid(" and t.publicCode = '" + publicCode + "' and t.privateCode = '" + privateCode + "'");
		if (waresList != null && waresList.size() > 0) {
			wares = waresList.get(0);
		}
		
		if (wares != null) {
			wares.setInsideCode(insideCode);
		} else {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(false);
			result.setMsg("没有找到该商品！");
		}
		
		boolean flag = waresService.updateWares(wares) > 0;
		
		if (flag) {
			result.setSuccess(true);
			result.setCode(Const.INFO_NORMAL);
			result.setMsg("设置瓶盖内码成功！");
			result.setData(wares);
		} else {
			result.setSuccess(false);
			result.setCode(Const.ERROR_SERVER);
			result.setMsg("设置瓶盖内码失败！");
			
			return result;
		}
		
		return result;
	}

	@RequestMapping(value = "/setWechatCode", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "设置微信编码", httpMethod = "POST", response = ApiResult.class, notes = "根据publicCode、privateCode设置wechatCode")
	public ApiResult setWechatCode(@ApiParam(required = true, name = "publicCode", value = "公共编码") @RequestParam("publicCode") String publicCode, 
			@ApiParam(required = true, name = "privateCode", value = "瓶身码") @RequestParam("privateCode") String privateCode,
			@ApiParam(required = false, name = "wechatCode", value = "微信编码") @RequestParam("wechatCode") String wechatCode) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_SET_WECHATCODE);
		
		Wares wares = null;
		List<Wares> waresList = waresService.getDatagrid(" and t.publicCode = '" + publicCode + "' and t.privateCode = '" + privateCode + "'");
		if (waresList != null && waresList.size() > 0) {
			wares = waresList.get(0);
		}
		
		if (wares != null) {
			wares.setWechatCode(wechatCode);
		} else {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(false);
			result.setMsg("没有找到该商品！");
			
			return result;
		}
		
		boolean flag = waresService.updateWares(wares) > 0;
		
		if (flag) {
			result.setSuccess(true);
			result.setCode(Const.INFO_NORMAL);
			result.setMsg("设置微信编码成功！");
			result.setData(wares);
		} else {
			result.setSuccess(false);
			result.setCode(Const.ERROR_SERVER);
			result.setMsg("设置微信编码失败！");
		}
		
		return result;
	}
	
	@RequestMapping(value = "/map", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "百度地址转化测试", httpMethod = "POST", response = ApiResult.class, notes = "先扫码，如果有奖，再进行兑奖操作")
	public ApiResult map(@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude) {
    	ApiResult result = new ApiResult();
    	result.setOperate("LOC2ADDR");
    	
		Address address = null;
		try {
			String url = "http://api.map.baidu.com/geocoder/v2/";
			
			address = new Address();
			String json = "";
			
			HttpClient client = new HttpClient(new HttpClientParams(), new SimpleHttpConnectionManager(true));
			GetMethod method = new GetMethod();
			try {
				URI base = new URI(url, false);
				method.setURI(new URI(base, "", false));
				method.setQueryString(new NameValuePair[] { 
						new NameValuePair("ak", "47TS2lczgTxANcKZepTmQVrQuhMWHPVK"),
						new NameValuePair("location", latitude +"," +longitude),
						new NameValuePair("output", "json"), 
						new NameValuePair("pois", "0")
					});
				int result1 = client.executeMethod(method);
				if (result1 == HttpStatus.SC_OK) {
					InputStream in = method.getResponseBodyAsStream();
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = in.read(buffer)) != -1) {
						baos.write(buffer, 0, len);
					}
					//json = URLDecoder.decode(baos.toString("UTF-8"), "UTF-8");
					json = baos.toString("UTF-8");
				} else {
					throw new Exception("HTTP ERROR Status: " + method.getStatusCode() + ":" + method.getStatusText());
				}
			} catch (UnsupportedEncodingException e) {
				
			} finally {
				method.releaseConnection();
			}
			
			System.out.println(json);
			log.info("[map] -- json: " + json);
			
			//Gson gson = new Gson();
			address = GsonUtils.fromJson(json, Address.class);
			if (address != null)
				System.out.println(address.toString());
			
		} catch (NumberFormatException e) {
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("经纬度数据错误!");
			result.setData(null);
			return result;
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		result.setCode(Const.INFO_NORMAL);
		result.setSuccess(true);
		result.setMsg("转换成功");
		result.setData(address);
		return result;
	}
	
	@RequestMapping(value = "/exchangeRecord", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "兑奖记录", httpMethod = "POST", response = ApiResult.class, notes = "根据用户名、用户类型、时间查询兑奖记录")
	public ApiResult exchangeRecord(@ApiParam(required = true, name = "userId", value = "用户userId") @RequestParam("userId") String userId, 
			@ApiParam(required = true, name = "beginTime", value = "开始时间") @RequestParam(value = "beginTime", required = false) String beginTime,
			@ApiParam(required = true, name = "endTime", value = "结束时间") @RequestParam(value = "endTime", required = false) String endTime) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_EXCHANGE_RECORD);
		
		StringBuffer conditionsql = new StringBuffer("");
		
		if (beginTime != null && !beginTime.equals("")) {
			conditionsql.append(" and date(exchangeTime) > '").append(beginTime).append("'");
		}
		
		if (endTime != null && !endTime.equals("")) {
			conditionsql.append(" and date(exchangeTime) < '").append(endTime).append("'");
		}
		
		conditionsql.append(" AND t.userId = '" + userId + "'")
			.append(" order by exchangeTime desc");
		
		List<Exchange> list = this.exchangeSercie.findByCondition(conditionsql.toString());
		
		if (list != null && list.size() > 0) {
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("获取到 " + list.size() + " 条兑奖记录!");
			result.setData(list);
		} else {
			result.setCode(Const.WARN_NO_MORE_DATA);
			result.setSuccess(false);
			result.setMsg("数据到头了...");
			result.setData(null);
		}
		
		return result;
	}
}

class ExchargeType {
	private String code;
	private String name;
	
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
 