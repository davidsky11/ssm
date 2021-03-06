package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Exchange;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AccountMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface CommonMapper {
	
	// 新增Account
	public int saveExchange(Exchange exchange) throws Exception;

	// 删除Account
	public int deleteExchange(@Param("id") String id);
	
	// 修改Account
	public int updateExchange(Exchange exchange);
	
	public Long getDatagridTotal(Exchange exchange);
	
	public Long getDatagridTotalByCondition(@Param("conditionSql") String conditionSql);
	
	public List<Exchange> datagridExchange(@Param("page") PageHelper page, @Param("exchange") Exchange exchange);
	
	// 查询所有活动
	public List<Exchange> getExchangeList(@Param("page") PageHelper page, @Param("conditionSql") String conditionsql); 
	
	// 根据用户名查询
	public List<Exchange> findByCondition(@Param("conditionSql") String conditionSql) ;

	// 根据用户ID查询
	public List<Exchange> findByUser(User user);
	
	public Exchange findById(@Param("id") String id);	
	
}
 