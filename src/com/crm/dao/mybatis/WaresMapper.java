package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Wares;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	WaresMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:45:14
 * @Version 	V1.0    
 */
public interface WaresMapper {
	
	//添加
	public int addWares(Wares wares) throws Exception;
	
	//修改
	public int updateWares(Wares wares);
	
	//删除
	public int deleteWares(@Param("id") String id);
	
	public int deleteByCondition(@Param("conditionSql") String conditionSql);

	//根据id查询
	public Wares findById(@Param("id") String id);
	
	//根据指定条件查询
	public List<Wares> getDatagrid(@Param("conditionSql") String conditionSql);
	
	//获取总数
	public Integer getDatagridTotal(Wares wares);
	
	public List<Wares> datagridWares(@Param("page") PageHelper page, @Param("wares") Wares wares);
	
	public List<Wares> datagridWaresByCondition(@Param("page") PageHelper page, @Param("conditionSql") String conditionSql);
	
	public int addWaresBatch(@Param("waresList") List<Wares> waresList);
	
	//分页
	//public List<Award> datagridAward(PageHelper page);
	
}
 