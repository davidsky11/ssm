package com.crm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Award;
import com.crm.domain.Page;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AwardService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 下午5:29:59
 * @Version 	V1.0    
 */
public interface AwardService {
	
	//添加
	public int addAward(Award award) throws Exception;
		
	//修改
	public int updateAward(Award award);
		
	//删除
	public int deleteAward(String id);

	//根据奖项编码查询
	public List<Award> findBySN(String serialNo);
	
	//根据id查询
	public Award findById(String id);
	
	//根据指定条件查询
	public List<Award> getDatagrid(String conditionSql);
	
	//获取总数
	public Integer getDatagridTotal(String activityId, String conditionSql);
	
	public List<Award> datagridAward(PageHelper page, String activityId, String conditionSql);
	
	public Page<Award> awdPages(@Param("page") Page<Award> page, @Param("conditionSql") String conditionSql);
	
}
 