package com.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.ScanRecordMapper;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	ScanRecordService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月19日 下午8:52:19
 * @Version 	V1.0    
 */
@Service
public class ScanRecordService {
	
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
		
	public Long getDatagridTotalByCondition(String conditionsql) {
		return scanRecordMapper.getDatagridTotalByCondition(conditionsql);
	}
	
	public Long getDatagridTotal(ScanRecord scanRecord) {
		return scanRecordMapper.getDatagridTotal(scanRecord);
	}
	
	public List<ScanRecord> datagridScanRecord(PageHelper page, ScanRecord scanRecord) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return scanRecordMapper.datagridScanRecord(page, scanRecord);
	}
	
}
 