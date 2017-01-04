package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.WaresMapper;
import com.crm.domain.Page;
import com.crm.domain.Wares;
import com.crm.domain.dto.WaresDto;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.WaresService;

/** 
 * @ClassName	WaresService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月7日 上午3:03:27
 * @Version 	V1.0    
 */
@Service
public class WaresServiceImpl implements WaresService {
	
	@Autowired
	private WaresMapper waresMapper;
	
	//添加
	public int addWares(Wares wares) throws Exception {
		return waresMapper.addWares(wares);
	}
	
	//修改
	public int updateWares(Wares wares) {
		return waresMapper.updateWares(wares);
	}
	
	//删除
	public int deleteWares(String id) {
		return waresMapper.deleteWares(id);
	}

	//根据id查询
	public Wares findById(String id) {
		return waresMapper.findById(id);
	}
	
	//根据指定条件查询
	public List<Wares> getDatagrid(String conditionSql) {
		return waresMapper.getDatagrid(conditionSql);
	}
	
	//获取总数
	public Integer getDatagridTotal(Wares wares) {
		return waresMapper.getDatagridTotal(wares);
	}
	
	public List<Wares> datagridWares(PageHelper page, Wares wares) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		return waresMapper.datagridWares(page, wares);
	}
	
	public List<Wares> datagridWaresByCondition(PageHelper page, String conditionSql) {
		return waresMapper.datagridWaresByCondition(page, conditionSql);
	}
	
	public int addWaresBatch(List<Wares> waresList) {
		return waresMapper.addWaresBatch(waresList);
	}
	
	public int deleteByCondition(String conditionSql) {
		return waresMapper.deleteByCondition(conditionSql);
	}
	
	public List<Wares> getListByAtyId(String activityId) {
		return waresMapper.getListByAtyId(activityId);
	}

	@Override
	public Page<WaresDto> searchListByCondition(Page<WaresDto> page, String conditionSql) {
		page.setStart((page.getPage() - 1)*page.getRows());
		page.setEnd(page.getRows());
		
		page.setTotal(waresMapper.searchListByConditionTotal(conditionSql));
		page.setContent(waresMapper.searchListByCondition(page, conditionSql));
		
		return page;
	}

	@Override
	public Wares findByWxCode(String wechatCode) {
		return waresMapper.findByWxCode(wechatCode);
	}
	
}
 