package com.crm.service;

import java.util.List;

import com.crm.domain.Wares;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	WaresService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月7日 上午3:03:27
 * @Version 	V1.0    
 */
public interface WaresService {
	
	//添加
	public int addWares(Wares wares) throws Exception;
	
	//修改
	public int updateWares(Wares wares);
	
	//删除
	public int deleteWares(String id);

	//根据id查询
	public Wares findById(String id);
	
	//根据指定条件查询
	public List<Wares> getDatagrid(String conditionSql);
	
	//获取总数
	public Integer getDatagridTotal(Wares wares);
	
	public List<Wares> datagridWares(PageHelper page, Wares wares);
	
	public List<Wares> datagridWaresByCondition(PageHelper page, String conditionSql);
	
	public int addWaresBatch(List<Wares> waresList);
	
	public int deleteByCondition(String conditionSql);
}
 