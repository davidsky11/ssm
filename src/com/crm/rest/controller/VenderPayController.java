package com.crm.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.wechat.pay.domain.request.WeixinNormalRedPackRequest;
import com.crm.wechat.pay.domain.request.WeixinVenderPayRequest;
import com.crm.wechat.pay.domain.response.WeixinRedPackResponse;
import com.crm.wechat.pay.domain.response.WeixinVenderPayResponse;
import com.crm.wechat.pay.service.IWeixinSendRedPackService;
import com.crm.wechat.pay.service.IWeixinVenderPayService;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;

/** 
 * @ClassName	VenderPayAction.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月27日 下午2:52:57
 * @Version 	V1.0    
 */
@Controller
@RequestMapping(value = "/v1/pay")
@Api(value = "/v1/pay", description = "支付接口，慎重操作！")
public class VenderPayController {
	
	@Autowired
	private IWeixinVenderPayService iWeixinVenderPayService;
	@Autowired
	private IWeixinSendRedPackService iWeixinSendRedPackService;
	
	@RequestMapping(value = "/vender", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "企业付款", httpMethod = "GET", response = WeixinVenderPayResponse.class, notes = "指定openid进行企业付款")
	public WeixinVenderPayResponse payout(@ApiParam(required = true, name = "openid", value = "接收方openid") @RequestParam("openid") String openid, 
			@ApiParam(required = true, name = "amount", value = "付款金额，单位：分") @RequestParam("amount") Integer amount, 
			@ApiParam(required = true, name = "re_user_name", value = "接收方名称") @RequestParam("re_user_name") String re_user_name, 
			@ApiParam(required = true, name = "desc", value = "描述信息") @RequestParam("desc") String desc, 
			@ApiParam(required = true, name = "spbill_create_ip", value = "发送方ip") @RequestParam("spbill_create_ip") String spbill_create_ip){
		WeixinVenderPayRequest request = new WeixinVenderPayRequest();
    	request.setOpenid(openid);
    	request.setAmount(amount);
    	request.setRe_user_name(re_user_name);
    	request.setDesc(desc);
    	request.setSpbill_create_ip(spbill_create_ip);
    	
    	WeixinVenderPayResponse response = new WeixinVenderPayResponse();
    	try {
			response = iWeixinVenderPayService.venderPay(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}
	
	@RequestMapping(value = "/redpack", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "红包接口", httpMethod = "GET", response = WeixinRedPackResponse.class, notes = "指定openid发送红包")
	public WeixinRedPackResponse redpack(
			@ApiParam(required = true, name = "openid", value = "接收方openid") @RequestParam("openid") String openid, 
			@ApiParam(required = true, name = "amount", value = "红包金额，单位：分") @RequestParam("amount") Integer amount, 
			@ApiParam(required = true, name = "re_user_name", value = "接收方名称")@RequestParam("re_user_name") String re_user_name, 
			@ApiParam(required = true, name = "desc", value = "描述信息") @RequestParam("desc") String desc, 
			@ApiParam(required = true, name = "spbill_create_ip", value = "发送方ip") @RequestParam("spbill_create_ip") String spbill_create_ip){
		WeixinNormalRedPackRequest request = new WeixinNormalRedPackRequest();
    	request.setRe_openid(openid);
    	request.setTotal_amount(amount);
    	request.setTotal_num(1);
    	request.setWishing("恭喜你通过扫码抽中了这个微信红包！");
    	request.setClient_ip(spbill_create_ip);
    	request.setAct_name("快乐兑");
    	request.setRemark("扫越多得越多，快来扫吧！");
    	
    	WeixinRedPackResponse response = new WeixinRedPackResponse();
    	try {
			response = iWeixinSendRedPackService.sendRedPack(1, request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return response;
	}

}
 