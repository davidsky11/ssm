package com.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.AwardMapper;
import com.crm.domain.Award;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AwardService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 下午5:29:59
 * @Version 	V1.0    
 */
@Service
public class AwardService {
	
	@Autowired
	private AwardMapper awardMapper;

	//添加
	public int addAward(Award award) throws Exception {
		return awardMapper.addAward(award);
	}
		
	//修改
	public int updateAward(Award award) {
		return awardMapper.updateAward(award);
	}
		
	//删除
	public int deleteAward(String id) {
		return awardMapper.deleteAward(id);
	}

	//根据奖项编码查询
	public List<Award> findBySN(String serialNo) {
		return awardMapper.findBySN(serialNo);
	}
	
	//根据id查询
	public Award findById(String id) {
		return awardMapper.findById(id);
	}
	
	//根据指定条件查询
	public List<Award> getDatagrid(String conditionSql) {
		return awardMapper.getDatagrid(conditionSql);
	}
	
	//获取总数
	public Long getDatagridTotal(String activityId, String conditionSql) {
		return awardMapper.getDatagridTotal(activityId, conditionSql);
	}
	
	public List<Award> datagridAward(PageHelper page, String activityId, String conditionSql) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return awardMapper.datagridAward(page, activityId, conditionSql);
	}
	
}
 