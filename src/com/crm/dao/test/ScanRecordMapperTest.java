package com.crm.dao.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.crm.dao.mybatis.ScanRecordMapper;
import com.crm.domain.ScanRecord;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	CodeRepoMapperTest.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月6日 上午11:51:06
 * @Version 	V1.0    
 */
public class ScanRecordMapperTest extends BaseBatisTest {

	@Autowired
	private ScanRecordMapper scanRecordMapper;
	
	@Test
	public void getDatagridTotal() {
		String beginTime = "2016-07-01";
		String endTime = "2016-07-30";
		String username = "kevin";
		String userType = "2";
		
		StringBuffer conditionsql = new StringBuffer("");
		conditionsql.append(" and date(scanTime) > '").append(beginTime)
			.append("' and date(scanTime) < '").append(endTime)
			.append("' and (accountId in (select id from sysuser where username = '")
			.append(username).append("' and userType = '").append(userType).append("')")
			.append(" or accountName = '").append(username).append("')");
		
		System.out.println(conditionsql.toString());
		long total = scanRecordMapper.getDatagridTotalByCondition(conditionsql.toString());
		
		System.out.println(total);
		
		PageHelper page = new PageHelper();
		page.setRows(20);  // 每页20最多20条数据
		page.setPage(1);
		
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		page.setSort("scanTime");
		
		List<ScanRecord> scanRecordList = scanRecordMapper.getScanRecordList(page, conditionsql.toString());
		
		System.out.println(scanRecordList);
		
	}
	
	@Test
	public void deleteCodeRepo() {
		
	}
	
	@Test
	public void updateCodeRepo() {
		
	}
	
	@Test
	public void getDatagridTotal1() {
		
	}
	
}
 