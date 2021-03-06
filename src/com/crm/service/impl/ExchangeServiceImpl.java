package com.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.ExchangeMapper;
import com.crm.domain.Exchange;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ExchangeService;

/** 
 * @ClassName	ExchangeService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月3日 上午12:59:30
 * @Version 	V1.0    
 */
@Service
public class ExchangeServiceImpl implements ExchangeService {
	
	@Resource
	private ExchangeMapper exchangeMapper;

	public int saveExchange(Exchange exchange) {
		return exchangeMapper.saveExchange(exchange);
	}

	public int updateExchange(Exchange exchange) {
		return exchangeMapper.updateExchange(exchange);
	}

	public List<Exchange> findByUser(User user) {
		return exchangeMapper.findByUser(user);
	}

	public Exchange findById(String id) {
		return exchangeMapper.findById(id);
	}
	
	public List<Exchange> findByCondition(String conditionSql) {
		return exchangeMapper.findByCondition(conditionSql);
	}

	public Integer getDatagridTotal(Exchange exchange) {
		return exchangeMapper.getDatagridTotal(exchange);
	}

	public List<Exchange> datagridExchange(PageHelper page, Exchange exchange) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		return exchangeMapper.datagridExchange(page, exchange);
	}

	public List<Exchange> getExchangeList(PageHelper page, String conditionsql) {
		return exchangeMapper.getExchangeList(page, conditionsql);
	}
	
	public int deleteExchange(String id) {
		return exchangeMapper.deleteExchange(id);
	}
	
	public Page<Exchange> exPages(Page<Exchange> page, String conditionSql) {
		page.setStart((page.getPage() - 1)*page.getRows());
		page.setEnd(page.getRows());
		
		page.setTotal(exchangeMapper.exPagesTotal(conditionSql));
		page.setContent(exchangeMapper.exPages(page, conditionSql));
		
		return page;
	}
	
	@Override
	public Page<Exchange> selectByPublisher(Page<Exchange> page, String conditionSql, String publisherId) {
		page.setStart((page.getPage() - 1)*page.getRows());
		page.setEnd(page.getRows());
		
		page.setTotal(exchangeMapper.selectByPublisherTotal(conditionSql, publisherId));
		page.setContent(exchangeMapper.selectByPublisher(page, conditionSql, publisherId));
		return page;
	}

}
 