package com.crm.wechat.dao;

import java.util.Map;

import com.crm.wechat.model.Balance;

/** 
 * @ClassName	LuckyMoneyMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:29:38
 * @Version 	V1.0    
 */
public interface LuckyMoneyMapper {

	/** 
     *  
     * <p>Description:是否存在成功发送红包的记录 </p> 
     * @author userwyh 
     * @param params 
     * @return 
     */  
    Integer existSendRecord(Map<String,Object> params);   
    /** 
     *  
     * <p>Description: 查询商户红包余额</p> 
     * @author userwyh 
     * @return 
     */  
    Balance selectCHongbao();
    
}
 