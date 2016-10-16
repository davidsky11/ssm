package com.crm.service;

import java.util.List;

import com.crm.domain.Exchange;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	ExchangeService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:59:30
 * @Version 	V1.0    
 */
public interface ExchangeService {
	
	public int saveExchange(Exchange exchange);

	public int updateExchange(Exchange exchange);

	public List<Exchange> findByUser(User user);

	public Exchange findById(String id) ;
	
	public List<Exchange> findByCondition(String conditionSql);

	public Integer getDatagridTotal(Exchange exchange);

	public List<Exchange> datagridExchange(PageHelper page, Exchange exchange);

	public List<Exchange> getExchangeList(PageHelper page, String conditionsql);
	
	public int deleteExchange(String id);
	
	public Page<Exchange> exPages(Page<Exchange> page, String conditionSql);

	public Page<Exchange> selectByPublisher(Page<Exchange> page, String conditionSql, String publisherId);
		
}
 