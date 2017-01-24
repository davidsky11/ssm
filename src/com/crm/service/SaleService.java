package com.crm.service;

import java.util.List;

import com.crm.domain.Sale;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	SaleService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月3日 上午12:09:30
 * @Version 	V1.0    
 */
public interface SaleService {

	public int saveSale(Sale sale);

	// 删除Account
	public int deleteSale(String id);
	
	// 修改Account
	public int updateSale(Sale sale);
	
	// 查询所有活动
	public List<Sale> findSaleList(String level, String activityId, String year, String month, String userId);
	
	public Sale findById(String id);

	public Long getDatagridTotal(Sale sale);
	
	public List<Sale> datagridSale(PageHelper page, Sale sale);
	
	public List<Sale> findSaleListPage(PageHelper page, String publicCode, String conditionSql);
	
	public List<Sale> findSaleListBy(String atyIds, String conditionSql);
	
	public List<Sale> getSaleList(String conditionSql);

}
 