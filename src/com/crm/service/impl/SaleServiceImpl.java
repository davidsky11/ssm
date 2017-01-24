package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.SaleMapper;
import com.crm.domain.Sale;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.SaleService;

/** 
 * @ClassName	SaleService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月3日 上午12:09:30
 * @Version 	V1.0    
 */
@Service
public class SaleServiceImpl implements SaleService {

	@Autowired
	private SaleMapper saleMapper;
	
	public int saveSale(Sale sale) {
		return saleMapper.saveSale(sale);
	}

	// 删除Account
	public int deleteSale(String id) {
		return saleMapper.deleteSale(id);
	}
	
	// 修改Account
	public int updateSale(Sale sale) {
		return saleMapper.updateSale(sale);
	}
	
	// 查询所有活动
	public List<Sale> findSaleList(String level, String activityId, String year, String month, String userId) {
		return saleMapper.findSaleList(level, activityId, year, month, userId);
	}
	
	public Sale findById(String id) {
		return saleMapper.findById(id);
	}

	public Long getDatagridTotal(Sale sale) {
		return saleMapper.getDatagridTotal(sale);
	}
	
	public List<Sale> datagridSale(PageHelper page, Sale sale) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		return saleMapper.datagridSale(page, sale);
	}
	
	public List<Sale> findSaleListPage(PageHelper page, String publicCode, String conditionSql) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		return saleMapper.findSaleListPage(page, publicCode, conditionSql);
	}
	
	public List<Sale> findSaleListBy(String atyIds, String conditionSql) {
		return saleMapper.findSaleListBy(atyIds, conditionSql);
	}
	
	public List<Sale> getSaleList(String conditionSql) {
		return saleMapper.getSaleList(conditionSql);
	}

}
 