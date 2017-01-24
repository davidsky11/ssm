package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Sale;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AccountMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface SaleMapper {
	
	// 新增Account
	public int saveSale(Sale sale);

	// 删除Account
	public int deleteSale(@Param("id") String id);
	
	// 修改Account
	public int updateSale(Sale sale);
	
	// 查询所有活动
	public List<Sale> getSaleList(@Param("conditionSql") String conditionSql); 
	
	public Sale findById(@Param("id") String id);

	public Long getDatagridTotal(Sale sale);
	
	public List<Sale> datagridSale(@Param("page") PageHelper page, @Param("sale") Sale sale);
	
	public List<Sale> findSaleListPage(@Param("page") PageHelper page, @Param("publicCode") String publicCode, @Param("conditionSql") String conditionSql);
	
	public List<Sale> findSaleListBy(@Param("atyIds") String atyIds, @Param("conditionSql") String conditionSql);

	public List<Sale> findSaleList(@Param("level") String level, @Param("activityId") String activityId, @Param("year") String year,
			@Param("month") String month, @Param("userId") String userId);

	public int addSaleBatch(@Param("saleList") List<Sale> saleList);
	
}
 