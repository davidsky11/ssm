package com.crm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.SaleMapper;
import com.crm.domain.Sale;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	SaleService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月3日 上午12:09:30
 * @Version 	V1.0    
 */
@Service
public class SaleService {

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
	public List<Sale> getSaleList(String conditionSql) {
		return saleMapper.getSaleList(conditionSql);
	}
	
	public Sale findById(String id) {
		return saleMapper.findById(id);
	}

	public Long getDatagridTotal(Sale sale) {
		return saleMapper.getDatagridTotal(sale);
	}
	
	public List<Sale> datagridSale(@Param("page") PageHelper page, @Param("sale") Sale sale) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return saleMapper.datagridSale(page, sale);
	}

}
 