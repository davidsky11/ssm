package com.crm.wechat.pay.service;

import com.crm.wechat.pay.domain.request.WeixinVenderPayRequest;
import com.crm.wechat.pay.domain.response.WeixinVenderPayResponse;

/** 
 * @ClassName	IWeixinVenderPayService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月27日 下午12:16:05
 * @Version 	V1.0    
 */
public interface IWeixinVenderPayService {

	/**
	 * 企业付款
	 * @Title:			venderPay
	 * @Description:	企业付款
	 * @param weixinVenderPayRequest
	 * @return
	 * @throws Exception
	 */
	public WeixinVenderPayResponse venderPay(WeixinVenderPayRequest weixinVenderPayRequest) throws Exception;

}
 