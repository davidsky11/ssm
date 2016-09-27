package com.crm.wechat.pay.action;

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

/** 
 * @ClassName	VenderPayAction.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月27日 下午2:52:57
 * @Version 	V1.0    
 */
@Controller
@RequestMapping(value = "/pay")
public class VenderPayAction {
	
	@Autowired
	private IWeixinVenderPayService iWeixinVenderPayService;
	@Autowired
	private IWeixinSendRedPackService iWeixinSendRedPackService;
	
	@RequestMapping(value = "/vender", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public WeixinVenderPayResponse payout(@RequestParam("openid") String openid, @RequestParam("amount") Integer amount, 
    		@RequestParam("re_user_name") String re_user_name, @RequestParam("desc") String desc, 
    		@RequestParam("spbill_create_ip") String spbill_create_ip){
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
    public WeixinRedPackResponse redpack(@RequestParam("redPackType") String redPackType, 
    		@RequestParam("openid") String openid, @RequestParam("amount") Integer amount, 
    		@RequestParam("re_user_name") String re_user_name, @RequestParam("desc") String desc, 
    		@RequestParam("spbill_create_ip") String spbill_create_ip){
		WeixinNormalRedPackRequest request = new WeixinNormalRedPackRequest();
    	request.setRe_openid(openid);
    	request.setTotal_amount(amount);
    	request.setTotal_num(1);
    	request.setWishing("恭喜你通过测试");
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
 