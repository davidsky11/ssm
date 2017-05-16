package com.crm.rest.controller;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.authorization.annotation.Authorization;
import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Exchange;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.domain.po.Address;
import com.crm.domain.po.AddressComponent;
import com.crm.domain.po.Result;
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
import com.crm.util.Tool;
import com.crm.util.ValidUtil;
import com.crm.util.common.Const;
import com.crm.util.recharge.PhoneRecharge;
import com.crm.util.recharge.QbRecharge;
import com.crm.util.recharge.ResultBean;
import com.crm.wechat.pay.domain.request.WeixinNormalRedPackRequest;
import com.crm.wechat.pay.domain.response.WeixinRedPackResponse;
import com.crm.wechat.pay.service.IWeixinSendRedPackService;
import com.crm.wechat.pay.service.IWeixinVenderPayService;
import com.crm.wechat.pay.util.WeixinUtils;
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
@RequestMapping("/v1/nobuss/wx/")
@Api(value = "/v1/nobusswx/", description = "不需要用户权限的API")
public class NoAuthWxController {
	
//	private final Logger log = LoggerFactory.getLogger(NoAuthWxController.class);
	private final Integer exchangeRatio = 100; // 兑换比率
	
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
	 * @param exType      	兑奖类型， WECHAT——微信红包，PHONE——手机充值，Q_BILL——Q币
	 * @param recNo			兑奖账号， WECHAT——
	 * @return
	 */
	@RequestMapping(value = "/userWithInfoWx", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "扫码兑奖", httpMethod = "POST", response = ApiResult.class, notes = "先扫码，如果有奖，再进行兑奖操作")
	public ApiResult userWithInfoWx(@ApiParam(required = true, name = "userType", value = "用户类型") @RequestParam("userType") String userType, 
			@ApiParam(required = true, name = "username", value = "用户名称") @RequestParam("username") String username, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = true, name = "wechatCode", value = "微信编码") @RequestParam("wechatCode") String wechatCode, 
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam(value = "insideCode", required= false) String insideCode, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn, 
			@ApiParam(required = false, name = "exType", value = "兑奖类型")@RequestParam(value = "exType", required = false) String exType,
			@ApiParam(required = false, name = "recNo", value = "兑奖账号，如果不设置，就对应当前用户的对应账号")@RequestParam(value = "recNo", required = false) String recNo) {
    	
    	ApiResult result = new ApiResult();
    	result.setOperate(Const.OPERATE_USER_WITH_INFO);
    	
    	/**
		 * 1、查询该商品信息
		 */
    	Wares wares = waresService.findByWxCode(wechatCode);
    	
    	/**
		 * 2、添加扫码记录
		 */
		List<User> userList = userService.findByNameAndType(username, userType);  // 1.找用户
		User user = null;
		if (userList != null && userList.size() > 0) {
			user = userList.get(0);
		} else {
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
		
    	switch (userType) {
	    	case Const.USERTYPE_APPUSER:
	    		result.setOperate(Const.OPERATE_APP_SCAN);
	    		
	    		/**
	    		 * 3、检查参数合法性，不合法，直接返回
	    		 */
	    		/*if (insideCode == null || insideCode.equals("")) {
	    			result.setCode(Const.ERROR_PARAM_MISS);
	    			result.setSuccess(false);
	    			result.setMsg("参数缺失!");
	    			result.setData(null);
	    			
	    			return result;
	    		}*/
	    		
	    		if (wares == null) {
	        		result.setCode(Const.ERROR_NULL_POINTER);
	        		result.setMsg("该wechatCode对应的商品不存在");
	        		result.setSuccess(false);
	        		
	        		return result;
	        	}
	    		
	    		/**
	    		 * 4、活动信息
	    		 */
	    		Activity aty = null;
	        	List<Activity> atyList = activityService.getActivityList(" and t.publicCode = '" + wares.getPublicCode() + "'");
	        	if (atyList != null && atyList.size() > 0) {
	        		aty = atyList.get(0);
	        	} else {
	        		result.setCode(Const.ERROR_PARAM_MISS);
	        		result.setMsg("该publicCode对应的活动不存在");
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
    				if (Tool.isNotNullOrEmpty(exType) && (insideCode == null || !insideCode.equals(wares.getInsideCode()))) {
    					result.setCode(Const.INFO_NO_AWARD);
    					result.setSuccess(false);
    					result.setMsg("兑奖操作，请扫描匹配的瓶盖[瓶盖内码不匹配]");
    					
    					wares.setInsideCode(null);
    					result.setData(award);
    					return result;
    				}
					
					/**
					 * 如果不兑奖，直接返回
					 */
					if (!Tool.isNotNullOrEmpty(exType)) {
    					result.setCode(Const.INFO_NORMAL);
    					result.setSuccess(true);
    					
    					result.setMsg("您中了" + award.getTitle() + ", 奖金 " + award.getAmount());
    					result.setData(award);
    					return result;
					}
					
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
				    		result.setMsg("您中了" + award.getTitle() + "奖金：  " + award.getAmount() + ", 系统正在为您开奖！");
				    		result.setData(response.getMch_billno());
				    	}
				    	result.setMsg("您中了" + award.getTitle() + "奖金：  " + award.getAmount() + ", 系统正在为您开奖！");
						
						break;
					case Const.EX_PHONE:
						String phone = user.getTelephone();
						
						/**
						 * 设置正确的手机号码
						 */
						if (Tool.isNullOrEmpty(recNo) || Tool.isNullOrEmpty(phone) || !ValidUtil.isValidMobile(phone)) {
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
					case Const.EX_POINT:
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
					ex.setPublicCode(wares.getPublicCode());
					ex.setPrivateCode(wares.getPrivateCode());
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
					int remain = award.getRemain();
					if (remain > 0) {
				    	award.setRemain(remain - 1);
				    	awardService.updateAward(award);
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
						sr.setPublicCode(wares.getPublicCode());
						sr.setPrivateCode(wares.getPrivateCode());
						
						this.scanRecordService.saveScanRecord(sr);
						
						result.setCode(Const.INFO_NORMAL);
		    			result.setMsg("商户扫码 成功");
		    			result.setSuccess(true);
		    			result.setData(sr);
		    			
	    			} else {
		        		result.setCode(Const.ERROR_PARAM_MISS);
		        		result.setMsg("该wechatCode对应的商品不存在");
		        		result.setSuccess(false);
		        		
		        		return result;
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
	@RequestMapping(value = "/antiFakeWx", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "商品防伪", httpMethod = "POST", response = ApiResult.class, 
		notes = "wechatCode 在数据库中未兑奖返回为消费\nwechatcode 在数据库已兑奖返回已消费，不再数据库返回未生产\n")
	public ApiResult antiFakeWx(@ApiParam(required = true, name = "wechatCode", value = "微信编码") @RequestParam("wechatCode") String wechatCode,
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
		
		if (wechatCode != null && !wechatCode.equals("") ) {
			
			/**
			 * 1、获取商品信息
			 *		--> 如果没有记录，则返回“商品不存在”
			 * 		--> 如果有记录，则判断是否已兑奖
			 */
			Wares wares = waresService.findByWxCode(wechatCode);
			
			if (wares == null) {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setMsg("该商品未生产");
				result.setSuccess(false);
				result.setData(null);
				
				return result;
			} else {
				sr.setPublicCode(wares.getPublicCode());
				sr.setPrivateCode(wares.getPrivateCode());
				
				// 写入扫码记录
				if (Tool.isNotNullOrEmpty(writeIn)) {
					try {
						this.scanRecordService.saveScanRecord(sr);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				
				if (!wares.getStatus().equals(Const.EX_STATUS_UNEXCHANGE)) {  // 未中奖、已兑奖
					/* --> 返回结果2
					 */
					result.setCode(Const.INFO_NORMAL);
					result.setSuccess(true);
					result.setMsg("该商品已消费");
					result.setData(wares);
					
					return result;
				} else {  // 未消费
					result.setCode(Const.WARN_NO_JUDGE);
					result.setSuccess(true);
					result.setMsg("该商品未消费");
					
					return result;
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
	@RequestMapping(value = "/deliverGoodsWx", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "商品跟踪/商品信息", httpMethod = "POST", response = ApiResult.class, notes = "用户通过扫码将瓶身二维码信息传给后台识别")
	public ApiResult deliverGoodsWx(@ApiParam(required = true, name = "flag", value = "操作标识") @RequestParam("flag") String flag,
			@ApiParam(required = true, name = "wechatCode", value = "微信编码") @RequestParam("wechatCode") String wechatCode,
			@ApiParam(required = true, name = "userId", value = "用户ID") @RequestParam("userId") String userId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn){
		
		ApiResult result = new ApiResult();
		
		Wares wares = waresService.findByWxCode(wechatCode);
		
		String publicCode = "";
		String privateCode = "";
		
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
		
		switch(flag){
		case Const.OPERATE_PRODUCT_TRACE:                     //商品跟踪
			result.setOperate(Const.OPERATE_PRODUCT_TRACE);
			
			if (wares != null) {
				publicCode = wares.getPublicCode();
				privateCode = wares.getPrivateCode();
				
				sr.setPublicCode(publicCode);
				sr.setPrivateCode(privateCode);
			} else {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setSuccess(false);
				result.setMsg("该wechatCode对应的商品不存在");
				return result;
			}
			
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
			
			if (wares != null) {
				publicCode = wares.getPublicCode();
				
				sr.setPublicCode(wares.getPublicCode());
				sr.setPrivateCode(wares.getPrivateCode());
			} else {
				result.setCode(Const.ERROR_NULL_POINTER);
				result.setSuccess(false);
				result.setMsg("该wechatCode对应的商品不存在");
				return result;
			}
			
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
				sr.setUserType("3");
				sr.setScanTime(new Date());
				result.setData(aty.getInfoUrl());
			}
		 }
		
		// 写入扫码记录
		if (Tool.isNotNullOrEmpty(writeIn)) {
			try {
				this.scanRecordService.saveScanRecord(sr);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		 
		return result;
	}
	
	@RequestMapping(value = "/awardAnalysisWx", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "奖项统计", httpMethod = "POST", response = ApiResult.class, notes = "统计当前奖项的基本信息和数目信息")
	public ApiResult awardAnalysisWx(@ApiParam(required = true, name = "wechatCode", value = "微信编码") @RequestParam("wechatCode") String wechatCode,
			@ApiParam(required = true, name = "openId", value = "用户openID") @RequestParam("openId") String openId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn) {
		
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_AWARD_ANALYSIS);
		
		Wares wares = this.waresService.findByWxCode(wechatCode);
		String publicCode = "";
		if (wares != null) {
			publicCode = wares.getPublicCode();
		} else {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setMsg("该商品未生产");
			result.setSuccess(false);
			result.setData(null);
			
			return result;
		}
		
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
		sr.setPublicCode(wares.getPublicCode());
		sr.setPrivateCode(wares.getPrivateCode());
		
		sr.setScanTime(new Date());
		
		User user = this.userService.findByOpenId(openId);
		
		if (user != null) {
			sr.setUser(user);
			sr.setUserId(user.getId());
			sr.setUserName(user.getUsername());
		} else {
			sr.setUserType("3");
			if (openId.equalsIgnoreCase("IOS")) {
				sr.setUserName("IOS用户");
			}
			if (openId.equalsIgnoreCase("Android")) {
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
			sr.setDistrict(ac.getDistrict());
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
	
	@RequestMapping(value = "/setInsideCodeWx", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "设置瓶盖内码", httpMethod = "POST", response = ApiResult.class, notes = "根据publicCode、privateCode设置insideCode")
	public ApiResult setInsideCodeWx(@ApiParam(required = true, name = "wechatCode", value = "微信编码") @RequestParam("wechatCode") String wechatCode, 
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam("insideCode") String insideCode) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_SET_INSIDECODE);
		
		Wares wares = waresService.findByWxCode(wechatCode);
		
		if (wares != null) {
			wares.setInsideCode(insideCode);
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
			result.setMsg("设置瓶盖内码成功！");
			result.setData(wares);
		} else {
			result.setSuccess(false);
			result.setCode(Const.ERROR_SERVER);
			result.setMsg("设置瓶盖内码失败！");
			
		}
		
		return result;
	}

	@RequestMapping(value = "/boundPhoneWx", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "手机绑定_微信", httpMethod = "POST", response = ApiResult.class, notes = "微信公众号用户绑定手机")
	public ApiResult boundPhoneWx(@ApiParam(required = true, name = "phone", value = "手机号码") @RequestParam("phone") String phone, 
			@ApiParam(required = true, name = "validCode", value = "验证码") @RequestParam("validCode") String validCode,
			@ApiParam(required = true, name = "openId", value = "微信公众号openid") @RequestParam("openId") String openId) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_BOUND_PHONE);
		
		String code = this.validService.getValidCode(phone);
		if (!validCode.equals(code)) {
			result.setMsg("验证码不正确");
			result.setSuccess(false);
			result.setCode(Const.ERROR_PARAM_MISS);
			
			return result;
		}
		
		User user = this.userService.findByPhone(phone);
		
		User tmp = this.userService.findByOpenId(openId);
		if (tmp != null) {
			/*result.setCode(Const.ERROR_NOT_EQUALS);
			result.setSuccess(false);
			result.setMsg("该公众号用户openId已经绑定另一手机用户!");
			
			User t = new User();
			t.setUsername(tmp.getUsername());
			t.setUserAlias(tmp.getUserAlias());
			
			result.setData(t);
			return result;*/
			
			tmp.setWxOpenId(null);
			this.userService.edit(tmp);  // 解绑wxOpenId
		}
		
		if (user == null) { // 用户不存在
			List<User> userList = this.userService.findByNameAndType(phone, "3"); // 查询是否存在该phone对应的用户
			if (userList != null && userList.size() > 0) {
				user = userList.get(0);
				user.setWxOpenId(openId);
				user.setTelephone(phone);
				
				this.userService.edit(user);
				
				result.setCode(Const.INFO_NORMAL);
				result.setSuccess(true);
				result.setMsg("与已有APP用户手机绑定成功[公众号用户]");
			} else {
				user = new User();
				user.setUsername(phone);
				user.setPassword(phone);  // 密码默认为手机号
				user.setTelephone(phone);
				user.setWxOpenId(openId);
				user.setPoints(0);
				user.setUserType("3");
				user.setLocked(0);
				
				this.userService.add(user);
				
				result.setCode(Const.INFO_NORMAL);
				result.setSuccess(true);
				result.setMsg("新建用户，并绑定手机成功，密码为手机号[公众号用户]");
				result.setData(user);
			}
		} else {
			user.setWxOpenId(openId);
			this.userService.edit(user);
			
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("手机绑定成功[公众号用户]");
			result.setData(user);
		}
	
		return result;
	}
	
	@RequestMapping(value = "/pointQueryWx", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "积分查询", httpMethod = "POST", response = ApiResult.class, notes = "根据openId查询积分")
	public ApiResult pointQueryWx(@ApiParam(required = true, name = "openId", value = "公众号OpenId") @RequestParam("openId") String openId) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_POINTS_QUERY);
		
		User user = this.userService.findByOpenId(openId);
		
		if (user != null) {
			result.setCode(Const.INFO_NORMAL);
			result.setSuccess(true);
			result.setMsg("积分查询成功.");
			result.setData(user);
		} else {
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(false);
			result.setMsg("需要先绑定手机才能进行查询.");
		}
	
		return result;
	}
	
	@RequestMapping(value = "/pointExchangeWx", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "积分兑换", httpMethod = "POST", response = ApiResult.class, notes = "根据openId兑换积分")
	public ApiResult pointExchangeWx(@ApiParam(required = true, name = "openId", value = "公众号OpenId") @RequestParam("openId") String openId,
			@ApiParam(required = true, name = "num", value = "积分兑换数量，100积分=1元") @RequestParam("num") String num,
			@ApiParam(required = false, name = "exType", value = "积分兑换方式") @RequestParam("exType") String exType) {
	
		ApiResult result = new ApiResult();
		result.setOperate(Const.OPERATE_POINTS_EXCHANGE);
		
		Integer points = 0, usePoints = 0, noUsePoints = 0;
		
		User user = this.userService.findByOpenId(openId);
		
		if (user == null) {  // 用户不存在，则直接返回
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(false);
			result.setMsg("需要先绑定手机才能进行兑换.");
			
			return result;
		}
		
		points = user.getPoints();
		usePoints = user.getUsePoints();
		noUsePoints = user.getNoUsePoints();
		
		if (noUsePoints < Integer.parseInt(num)) {
			result.setCode(Const.ERROR_NO_ENOUGH);
			result.setSuccess(false);
			result.setMsg("所剩积分不够兑换.");
			result.setData(user);
			
			return result;
		}
			
		/**
		 * 兑奖处理
		 * 	先兑奖，再添加兑奖记录
		 */
		/**
		 * I 兑奖
		 */
		switch (exType) {
		case Const.EX_WECHAT:
			
			Integer amount = new Double(num).intValue();
			
			user.setNoUsePoints(noUsePoints - Integer.parseInt(num));
    		user.setUsePoints(usePoints + Integer.parseInt(num));
    		
    		this.userService.edit(user);
	    	
			/**
			 * 1、微信红包
			 */
			WeixinNormalRedPackRequest request = new WeixinNormalRedPackRequest();
	    	request.setRe_openid(openId);  // 使用openId
	    	request.setTotal_amount(amount);
	    	request.setTotal_num(1);
	    	request.setSend_name("快乐兑[积分兑换]");
	    	request.setAct_name("快乐兑[积分兑换]");
	    	request.setRemark("积分兑换红包");
	    	request.setWishing("恭喜使用积分兑换了这个微信红包！");
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
	    		result.setMsg("您使用积分兑换微信红包成功.");
	    		result.setData(response.getMch_billno());
	    	}
			
			break;
		case Const.EX_PHONE:
			String phone = user.getTelephone();
			
			/**
			 * 设置正确的手机号码
			 */
			if (Tool.isNullOrEmpty(phone) || !ValidUtil.isValidMobile(phone)) {
				result.setCode(Const.WARN_PHONE_ERROR);
				result.setSuccess(false);
				result.setMsg("手机号码不正确，请为用户绑定合适的手机号或者直接指定一个待充值的手机号码!");
				
				return result;
			}
			
			user.setNoUsePoints(noUsePoints - Integer.parseInt(num));
    		user.setUsePoints(usePoints + Integer.parseInt(num));
    		
    		this.userService.edit(user);
			
			String result2 = "";
			Integer amount2 = 0;
			try {
				amount2 = new Double(num).intValue() / exchangeRatio;
				result2 = PhoneRecharge.onlineOrder(phone, amount2);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (Tool.isNotNullOrEmpty(result2)) {
				ResultBean rb1 = GsonUtils.fromJson(result2, ResultBean.class);
				if (rb1.getError_code() != null && rb1.getError_code().equals("0")) {
					result.setSuccess(true);
					result.setMsg("[积分兑换]手机充值" + amount2 + "元，请稍后查询充值结果");
					result.setData(rb1 == null ? null : (rb1.getResult() == null ? null : rb1.getResult().getUorderid()));
				} else {
					result.setSuccess(false);
					result.setMsg(rb1.getReason() == null ? "[积分兑换]手机充值失败" : rb1.getReason());
					
					return result;
				}
			} else {
				result.setSuccess(false);
				result.setMsg("[积分兑换]充值失败");
				
				return result;
			}
			
			
			break;
		case Const.EX_Q_BILL:
			String qq = user.getQq();
			
			if (Tool.isNullOrEmpty(qq)) {
				result.setCode(Const.WARN_NO_QQ);
				result.setSuccess(false);
				result.setMsg("请为用户绑定合适的QQ号或者指定一个待充值的qq号码!");
				
				return result;
			}
			
			user.setNoUsePoints(noUsePoints - Integer.parseInt(num));
    		user.setUsePoints(usePoints + Integer.parseInt(num));
    		
    		this.userService.edit(user);
			
			String res = "";
			Integer amount3 = 0;
			try {
				amount3 = new Double(num).intValue() / exchangeRatio;
				res = QbRecharge.qbRecharge(qq, amount3);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			if (Tool.isNotNullOrEmpty(res)) {
				ResultBean rb2 = GsonUtils.fromJson(res, ResultBean.class);
				if (rb2.getError_code() != null && rb2.getError_code().equals("0")) {
					result.setSuccess(true);
					result.setMsg("[积分兑换]Q币充值" + amount3 + "元，请稍后查询充值结果");
					result.setData(rb2 == null ? null : (rb2.getResult() == null ? null : rb2.getResult().getUorderid()));
				
					user.setUsePoints(usePoints + Integer.parseInt(num));
		    		user.setNoUsePoints(noUsePoints - Integer.parseInt(num));
		    		userService.edit(user);
				} else {
					result.setSuccess(false);
					result.setMsg(rb2.getReason() == null ? "[积分兑换]Q币充值失败" : rb2.getReason());
				
					return result;
				}
			} else {
				result.setSuccess(false);
				result.setMsg("[积分兑换]充值失败");
				
				return result;
			}
			
			break;
		default:
			result.setCode(Const.ERROR_PARAM_MISS);
			result.setSuccess(false);
			result.setMsg("请选择合适的兑奖类型!");
			
			return result;
		}
		
		return result;
	}

	@RequestMapping(value = "/scanExPointWx", method = RequestMethod.POST)
	@ResponseBody
	@Authorization
	@ApiOperation(value = "扫码兑换积分", httpMethod = "POST", response = ApiResult.class, notes = "先扫码，如果有奖，再进行积分兑换")
	public ApiResult scanExPointWx(@ApiParam(required = true, name = "openId", value = "公众号openId") @RequestParam("openId") String openId, 
			@ApiParam(required = true, name = "flagCode", value = "硬件标识码") @RequestParam("flagCode") String flagCode,
			@ApiParam(required = true, name = "time", value = "客户端时间") @RequestParam("time") String time,
			@ApiParam(required = true, name = "longitude", value = "经度") @RequestParam("longitude") String longitude, 
			@ApiParam(required = true, name = "latitude", value = "纬度") @RequestParam("latitude") String latitude, 
			@ApiParam(required = true, name = "wechatCode", value = "微信编码") @RequestParam("wechatCode") String wechatCode, 
			@ApiParam(required = false, name = "insideCode", value = "内码") @RequestParam(value = "insideCode", required= false) String insideCode, 
			@ApiParam(required = false, name = "writeIn", value = "是否写入") @RequestParam(value = "writeIn", required= false) String writeIn) {
    	
    	ApiResult result = new ApiResult();
    	result.setOperate(Const.OPERATE_USER_WITH_INFO);
    	
    	User user = this.userService.findByOpenId(openId); //  查找用户
    	
    	if (user == null) {  // 用户不存在，则直接返回
			result.setCode(Const.ERROR_NULL_POINTER);
			result.setSuccess(false);
			result.setMsg("需要先绑定手机才能进行兑换.");
			
			return result;
		}
    	
    	/**
		 * 1、查询该商品信息
		 */
    	Wares wares = waresService.findByWxCode(wechatCode);
    	
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
    		result.setMsg("该wechatCode对应的商品不存在");
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
	 * @Title:			exchangeRecordWx
	 * @Description:	兑奖记录查询
	 * @param openId
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value = "/exchangeRecordWx", method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "兑奖记录_微信", httpMethod = "POST", response = ApiResult.class, notes = "根据用户名、用户类型、时间查询兑奖记录")
	public ApiResult exchangeRecordWx(@ApiParam(required = true, name = "openId", value = "用户openId") @RequestParam("openId") String openId, 
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
		
		conditionsql.append(" AND t.userId in (select id from sysuser where wxOpenId = '" + openId + "')")
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
 