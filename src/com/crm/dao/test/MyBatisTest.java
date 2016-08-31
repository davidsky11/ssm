package com.crm.dao.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crm.dao.mybatis.AccountMapper;
import com.crm.dao.mybatis.ActivityMapper;
import com.crm.dao.mybatis.AwardMapper;
import com.crm.dao.mybatis.ExchangeMapper;
import com.crm.dao.mybatis.ScanRecordMapper;
import com.crm.dao.mybatis.UserMapper;
import com.crm.domain.Account;
import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Exchange;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/**
 * @ClassName RedisTest.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月16日 下午3:57:29
 * @Version V1.0
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境 
@ContextConfiguration(locations = { "classpath:config/spring-mybatis.xml"})
public class MyBatisTest extends AbstractJUnit4SpringContextTests {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private AwardMapper awardMapper;
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private ExchangeMapper exchangeMapper;
	@Autowired
	private ScanRecordMapper scanRecordMapper;

	@Test
	public void findUserByName() {
		List<User> user = userMapper.findUserByName("admin", "-1");
		System.out.println(user.size());
	}
	
	@Test
	public void findByUsernameAndPass() {
		Account ac = accountMapper.findByNameAndPass("kevin", "123", "1");
		System.out.println(ac.toString());
	}
	
	@Test
	public void save() {
		Account ac = new Account();
		ac.setUsername("test");
		ac.setPassword("test");
		int i = 0;
		try {
			i = accountMapper.saveAccount(ac);
		} catch (Exception e) {
			if (e instanceof DuplicateKeyException) {
				
			}
			e.printStackTrace();
		}
		System.out.println(i + "  " + ac.getId());
	}

	@Test
	public void testAddAward() {
		Award award = new Award("test", "test_sn", "test_desc", 1, 1, 158.00);
		int result = 0;
		try {
			result = awardMapper.addAward(award);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(result + "   " + award.getId());
	}
	
	@Test
	public void testdeleteAward() {
		Award award = new Award();
		award.setId("123");
		
		int i = awardMapper.deleteAward("我的身份");
		System.out.println(i);
	}
	
	@Test
	public void testUpdateAward() {
//		Award ward = awardMapper.getAwardById("ca5d7ed04d5711e695f33417eb90ce17");
		List<Award> ward = awardMapper.findBySN("test");
		Award award = ward.get(0);
		award.setTitle("find");
		award.setAmount(111.00);
		
		int i = awardMapper.updateAward(award);
		System.out.println(i);
	}
	
	@Test
	public void testgetDatagrid() {
		List<Award> awardList = new ArrayList<Award>();
		try {
			awardList = awardMapper.getDatagrid(" and serialNo like '%test%' ");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (Award a : awardList) {
			System.out.println(a.toString());
		}
	}
	
	@Test
	public void testgetAccountByPublisher() {
		List<User> userList = userMapper.findUserByName("admin", "0");
		Activity ac = new Activity();
		
		if (userList != null && userList.size() > 0) {
			User user = userList.get(0);
			System.out.println(user.getId());
			ac = activityMapper.findByPublisher(user);

			if (ac != null)
			System.out.println(ac.toString());
		}
		
	}
	
	@Test
	public void addActivity() {
		Activity ac = new Activity();
		ac.setTitle("新增");
		ac.setDescription("新增描述");
		ac.setStartTime(sdf.format(new Date()));
		ac.setEndTime(sdf.format(new Date()));
		ac.setContent("新增内容");
		ac.setPublisherId("1");
		ac.setPublisherName("admin");
		
		try {
			activityMapper.saveActivity(ac);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void getExchangeByAccount() {
		
	}
	
	@Test
	public void findByAccount() {
		
	}
	
	@Test
	public void datagrid() {
		PageHelper page = new PageHelper();
		page.setPage(0);
		page.setRows(10);
		page.setStart(0);
		page.setEnd(10);
		//page.setSort("username");
		
		Account account = new Account();
		List<Account> list = accountMapper.datagridAccount(page, account);
		if (list != null) {
			System.out.println(list.size());
		}
	}
	
	@Test
	public void datagrid1() {
		PageHelper page = new PageHelper();
		page.setPage(0);
		page.setRows(10);
		page.setStart(0);
		page.setEnd(10);
		//page.setSort("username");
		
		List<User> list = userMapper.datagridUser(page, 1);
		if (list != null) {
			System.out.println(list.size());
		}
	}
	
	@Test
	public void datagridAward() {
		PageHelper page = new PageHelper();
		page.setPage(0);
		page.setRows(10);
		page.setStart(0);
		page.setEnd(10);
		
		System.out.println(awardMapper.getDatagridTotal(new Award()));
		
		List<Award> list = awardMapper.datagridAward(page, new Award());
		for (Award a : list) {
			System.out.println(a.toString());
		}
	}
	
	@Test
	public void datagridScanRecord() throws ParseException {
		PageHelper page = new PageHelper();
		page.setPage(0);
		page.setRows(10);
		page.setStart(0);
		page.setEnd(10);
		
		ScanRecord sr = new ScanRecord();
		//sr.setAccountId("885");
		//sr.setAccountName("kevin");
		//sr.setScanTime(new Timestamp(sdf.parse("2016-07-19").getTime()));
		List<ScanRecord> list = scanRecordMapper.datagridScanRecord(page, sr);
		for (ScanRecord s : list) {
			System.out.println(s.toString());
		}
	}
	
	@Test
	public void findByIdActivity() {
		Activity a = activityMapper.findById("test");
		System.out.println(a);
	}
	
	@Test
	public void saveScanRecord() {
		ScanRecord sr = new ScanRecord();
		sr.setAccountName("kevin");
		sr.setAccountId("888");
		sr.setLatitude(123.3);
		sr.setLongitude(11.8);
		sr.setScanTime(sdf.format(new Date()));
		
		int i = -1;
		try {
			i = scanRecordMapper.saveScanRecord(sr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(i);
	}
	
	@Test
	public void saveExchange() {
		Exchange e = new Exchange();
		e.setAccountId("888");
		e.setExchangeTime(sdf.format(new Date()));
		e.setInsideCode("123");
		e.setPrivateCode("234");
		
		int i = -1;
		try {
			i = exchangeMapper.saveExchange(e);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println(i);
	}
	
	@Test
	public void findByConditionUser() {
		String username = "admin";
		String password = "123456";
		String userType = "0";
				
		List<User> list = userMapper.findByConditionSql(" AND username = '" + username + " ' AND password = '" + password + "' AND userType = '" + userType + "'");
		System.out.println(list.size());
	}
	
	@Test
	public void addUser() {
		User user = new User();
		user.setUsername("kkk");
		user.setPassword("12346");
		user.setUserType("3");
		
		
		int i = userMapper.addUser(user);
		System.out.println(i);
	}
	
}
