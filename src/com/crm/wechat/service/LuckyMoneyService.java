package com.crm.wechat.service;

import java.util.Map;

/** 
 * @ClassName	LuckyMoneyService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:32:15
 * @Version 	V1.0    
 */
public interface LuckyMoneyService {

	/** 
     *  
     * <p>Description: 根据用户填写的手机号发送红包</p>  
     * @author userwyh 
     * @param mobile 
     * @param openid 
     * @throws Exception 
     */  
     boolean sendLuckyMoney(String openid,String mobile) throws Exception;  
     /** 
      *  
      * <p>Description: 是否已经领取过红包</p>  
      * @author userwyh 
      * @param params 
      * @return 
      */  
     boolean existSendRecord(Map<String,Object> params);  
     
}
 