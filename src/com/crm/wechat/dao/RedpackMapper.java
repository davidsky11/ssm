package com.crm.wechat.dao;

import com.crm.wechat.model.Redpack;

/** 
 * @ClassName	RedpackMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月14日 上午1:30:33
 * @Version 	V1.0    
 */
public interface RedpackMapper {

	int insertSelective(Redpack record);  
    int updateByPrimaryKeySelective(Redpack record); 
    
}
 