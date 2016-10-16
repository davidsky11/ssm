package com.crm.service;

import java.util.List;

import com.crm.domain.Page;
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
public interface ScanRecordService {

	// 新增ScanRecord
	public int saveScanRecord(ScanRecord scanRecord) throws Exception;

	// 删除ScanRecord
	public int deleteScanRecord(String id);
	
	// 修改ScanRecord
	public int updateScanRecord(ScanRecord scanRecord);
	
	// 查询所有扫描记录
	public List<ScanRecord> getScanRecordList(PageHelper page, String conditionsql);
	
	// 根据用户名查询
	public List<ScanRecord> findByCondition(String conditionSql);

	// 根据用户ID查询
	public List<ScanRecord> findByUser(User user);
		
	public Integer getDatagridTotalByCondition(String conditionsql);
	
	public Integer getDatagridTotal(ScanRecord scanRecord);
	
	public List<ScanRecord> datagridScanRecord(PageHelper page, ScanRecord scanRecord);
	
	public Page<ScanRecord> srPages(Page<ScanRecord> page, String conditionSql);
	
	public ScanRecord findById(String id);
	
	public Page<ScanRecord> selectByPublisher(Page<ScanRecord> page, String conditionSql, String publisherId);
	
}
 