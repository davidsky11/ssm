package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	ScanRecordMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface ScanRecordMapper {
	
	// 新增ScanRecord
	public int saveScanRecord(ScanRecord scanRecord) throws Exception;

	// 删除ScanRecord
	public int deleteScanRecord(@Param("id") String id);
	
	// 修改ScanRecord
	public int updateScanRecord(ScanRecord scanRecord);
	
	// 查询所有扫描记录
	public List<ScanRecord> getScanRecordList(@Param("page") PageHelper page, @Param("conditionSql") String conditionSql); 
	
	// 根据用户名查询
	public List<ScanRecord> findByCondition(@Param("conditionSql") String conditionSql) ;

	// 根据用户ID查询
	public List<ScanRecord> findByUser(User user);
		
	public Integer getDatagridTotal(ScanRecord scanRecord);
	
	public Integer getDatagridTotalByCondition(@Param("conditionSql") String conditionSql);
	
	public List<ScanRecord> datagridScanRecord(@Param("page") PageHelper page, @Param("scanRecord") ScanRecord scanRecord);
	
}
 