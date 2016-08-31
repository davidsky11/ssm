package com.crm.dao.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.crm.dao.mybatis.ExchangeMapper;
import com.crm.domain.Exchange;

/** 
 * @ClassName	CodeRepoMapperTest.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月6日 上午11:51:06
 * @Version 	V1.0    
 */
public class ExchangeMapperTest extends BaseBatisTest {

	@Autowired
	private ExchangeMapper exchangeMapper;
	
	@Test
	public void findByCondition() {
		String beginTime = "2016-07-01";
		String endTime = "2016-07-30";
		String username = "kevin";
		String userType = "2";
		
		StringBuffer conditionsql = new StringBuffer("");
		conditionsql.append(" and date(exchangeTime) > '").append(beginTime)
			.append("' and date(exchangeTime) < '").append(endTime)
			.append("' and accountId in (select id from sysuser where username = '")
			.append(username).append("' and userType = '").append(userType).append("')");
		
		List<Exchange> list = this.exchangeMapper.findByCondition(conditionsql.toString());
		
		System.out.println(list);
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
 