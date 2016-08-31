package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.Award;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AwardMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 上午12:45:14
 * @Version 	V1.0    
 */
public interface AwardMapper {
	
	//添加
	public int addAward(Award award) throws Exception;
	
	//修改
	public int updateAward(Award award);
	
	//删除
	public int deleteAward(String id);

	//根据奖项编码查询
	public List<Award> findBySN(@Param("serialNo") String serialNo);
	
	//根据id查询
	public Award findById(@Param("id") String id);
	
	//根据指定条件查询
	public List<Award> getDatagrid(@Param("conditionSql") String conditionSql);
	
	//获取总数
	public Long getDatagridTotal(Award award);
	
	public List<Award> datagridAward(@Param("page") PageHelper page, @Param("award") Award award);
	
	//分页
	//public List<Award> datagridAward(PageHelper page);
	
}
 