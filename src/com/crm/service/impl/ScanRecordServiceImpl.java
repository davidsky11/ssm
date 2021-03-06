package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.ScanRecordMapper;
import com.crm.domain.Page;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ScanRecordService;

/** 
 * @ClassName	ScanRecordService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 下午8:52:19
 * @Version 	V1.0    
 */
@Service
public class ScanRecordServiceImpl implements ScanRecordService {
	
	@Autowired
	private ScanRecordMapper scanRecordMapper;

	// 新增ScanRecord
	public int saveScanRecord(ScanRecord scanRecord) throws Exception {
		return scanRecordMapper.saveScanRecord(scanRecord);
	}

	// 删除ScanRecord
	public int deleteScanRecord(String id) {
		return scanRecordMapper.deleteScanRecord(id);
	}
	
	// 修改ScanRecord
	public int updateScanRecord(ScanRecord scanRecord) {
		return scanRecordMapper.updateScanRecord(scanRecord);
	}
	
	// 查询所有扫描记录
	public List<ScanRecord> getScanRecordList(PageHelper page, String conditionsql) {
		return scanRecordMapper.getScanRecordList(page, conditionsql);
	}
	
	// 根据用户名查询
	public List<ScanRecord> findByCondition(String conditionSql) {
		return scanRecordMapper.findByCondition(conditionSql);
	}

	// 根据用户ID查询
	public List<ScanRecord> findByUser(User user) {
		return scanRecordMapper.findByUser(user);
	}
		
	public Integer getDatagridTotalByCondition(String conditionsql) {
		return scanRecordMapper.getDatagridTotalByCondition(conditionsql);
	}
	
	public Integer getDatagridTotal(ScanRecord scanRecord) {
		return scanRecordMapper.getDatagridTotal(scanRecord);
	}
	
	public List<ScanRecord> datagridScanRecord(PageHelper page, ScanRecord scanRecord) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		return scanRecordMapper.datagridScanRecord(page, scanRecord);
	}
	
	public Page<ScanRecord> srPages(Page<ScanRecord> page, String conditionSql) {
		page.setStart((page.getPage() - 1)*page.getRows());
		page.setEnd(page.getRows());
		
		page.setTotal(scanRecordMapper.srPagesTotal(conditionSql));
		page.setContent(scanRecordMapper.srPages(page, conditionSql));
		
		return page;
	}
	
	public ScanRecord findById(String id) {
		return scanRecordMapper.findById(id);
	}

	@Override
	public Page<ScanRecord> selectByPublisher(Page<ScanRecord> page, String conditionSql, String publisherId) {
		page.setStart((page.getPage() - 1)*page.getRows());
		page.setEnd(page.getRows());
		
		page.setTotal(scanRecordMapper.selectByPublisherTotal(conditionSql, publisherId));
		page.setContent(scanRecordMapper.selectByPublisher(page, conditionSql, publisherId));
		return page;
	}
	
}
 