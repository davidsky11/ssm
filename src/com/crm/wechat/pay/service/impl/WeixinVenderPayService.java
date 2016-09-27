package com.crm.wechat.pay.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.crm.wechat.pay.domain.WxConfig;
import com.crm.wechat.pay.domain.exception.WeixinMerchantsException;
import com.crm.wechat.pay.domain.request.WeixinVenderPayRequest;
import com.crm.wechat.pay.domain.response.WeixinVenderPayResponse;
import com.crm.wechat.pay.service.IWeixinVenderPayService;
import com.crm.wechat.pay.util.WeixinUtils;
import com.crm.wechat.pay.util.http.HttpClientUtil;

/** 
 * @ClassName	WeixinVenderPayService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月27日 下午12:17:30
 * @Version 	V1.0    
 */
@Service
public class WeixinVenderPayService implements IWeixinVenderPayService {

	private final static Logger LOGGER = LoggerFactory.getLogger(WeixinSendRedPackServiceImpl.class);
	
	@Override
	public WeixinVenderPayResponse venderPay(WeixinVenderPayRequest weixinVenderPayRequest) throws Exception {
		if(weixinVenderPayRequest.getAmount() < WxConfig.VENDER_PAY_MIN_PRICE){
			throw new WeixinMerchantsException("企业付款最小金额1.0元");
		}
		boolean isSucces = false; 
		String url = WxConfig.VENDER_PAY;
		//生成签名
		String sign = WeixinUtils.getSign(weixinVenderPayRequest);
		weixinVenderPayRequest.setSign(sign);
		
		if(weixinVenderPayRequest.getAmount() > WxConfig.VENDER_PAY_MAX_PRICE){
			throw new WeixinMerchantsException("给同一个非实名用户付款，单笔单日限额2000/2000");
		}
		WeixinVenderPayResponse response = HttpClientUtil.doPost(url, weixinVenderPayRequest, WeixinVenderPayResponse.class);
		
		System.err.println(response);
		if(null != response && "SUCCESS".equals(response.getReturn_code())){
			if("SUCCESS".equals(response.getResult_code())){
				LOGGER.info("企业付款成功");
				isSucces = true;
			}else{
				LOGGER.error("企业付款异常：{}",response.getReturn_msg());
			}
		}
		return response;
	}

}
 