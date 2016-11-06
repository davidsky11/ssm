package com.crm.dao.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crm.common.util.lang.DateUtil;
import com.crm.common.util.math.RandomUtil;
import com.crm.dao.mybatis.ActivityMapper;
import com.crm.dao.mybatis.AnalysisMapper;
import com.crm.dao.mybatis.AwardMapper;
import com.crm.dao.mybatis.ExchangeMapper;
import com.crm.dao.mybatis.SaleMapper;
import com.crm.dao.mybatis.ScanRecordMapper;
import com.crm.dao.mybatis.SysDictionaryMapper;
import com.crm.dao.mybatis.UserMapper;
import com.crm.dao.mybatis.WaresMapper;
import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Exchange;
import com.crm.domain.Page;
import com.crm.domain.Sale;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.domain.dto.PlaceAnalysis;
import com.crm.domain.dto.SrDto;
import com.crm.domain.easyui.PageHelper;
import com.crm.domain.po.Address;
import com.crm.domain.po.AddressComponent;
import com.crm.domain.po.Result;
import com.crm.domain.system.SysDictionary;
import com.crm.util.MapUtil;
import com.crm.util.Tool;
import com.crm.util.common.Const;

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
	private AwardMapper awardMapper;
	@Autowired
	private ActivityMapper activityMapper;
	@Autowired
	private ExchangeMapper exchangeMapper;
	@Autowired
	private ScanRecordMapper scanRecordMapper;
	@Autowired
	private WaresMapper waresMapper;
	@Autowired
	private AnalysisMapper analysisMapper;
	@Autowired
	private SaleMapper saleMapper;
	@Autowired
	private SysDictionaryMapper sysDictionaryMapper;

	@Test
	public void login() {
		List<User> user = userMapper.login("app", "3", "123456");
		for (User u : user) {
			System.out.println(u);
		}
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
		List<User> userList = userMapper.login("kevin", "2", "123456");
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
		//ac.setStartTime(new Date());
		//ac.setEndTime(new Date());
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
		
		String publicCode = "";
		if (publicCode != null && !publicCode.equals("")) {
			
		} else {
			List<Activity> atyList = activityMapper.getActivityList(" and publisherId = '9'");
			Set<String> set = new HashSet<String>();
			for (Activity aty : atyList) {
				set.add(aty.getPublicCode());
			}
			String[] publicCodeArr = set.toArray(new String[0]);
			publicCode = Tool.stringArrayToString(publicCodeArr, true, ",");
		}
		
		System.out.println(awardMapper.getDatagridTotal(publicCode, ""));
		
		List<Award> list = awardMapper.datagridAward(page, publicCode, "");
		for (Award a : list) {
			System.out.println(a.toString());
		}
	}
	
	@Test
	public void datagridScanRecord() {
		PageHelper page = new PageHelper();
		page.setPage(0);
		page.setRows(10);
		page.setStart(0);
		page.setEnd(10);
		
		ScanRecord sr = new ScanRecord();
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
		sr.setUserName("kevin");
		sr.setUserId("888");
		sr.setLatitude(123.3);
		sr.setLongitude(11.8);
		sr.setScanTime(new Date());
		
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
		e.setUserId("888");
		e.setExchangeTime(new Date());
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
		user.setUsername("tntntntt");
		user.setPassword("12346");
		user.setUserType("3");
		user.setLocked(Const.USER_UNLOCK);
		
		int i = userMapper.addUser(user);
		System.out.println(i);
	}
	
	@Test
	public void editUser() {
		User user = new User();
		user.setId("92cbfeec7ef511e6a36600ac1e83ba5a");
		user.setLocked(Const.USER_UNLOCK);
		
		System.out.println(user);
		
		int i = userMapper.editUser(user);
		System.out.println(i);
	}
	
	@Test
	public void addWares() throws Exception {
		Wares w = new Wares();
		w.setPublicCode("123");
		w.setPrivateCode("789");
		w.setStatus(Const.EX_STATUS_UNEXCHANGE);
		
		Object o = waresMapper.addWares(w);
		System.out.println(o + " id " + w.getId());
	}
	
	@Test
	public void findPlaceAnalysis() {
		List<PlaceAnalysis> paList = analysisMapper.findPlaceAnalysis("123", "2");
		for (PlaceAnalysis pa : paList) {
			System.out.println(pa.toString());
		}
	}
	
	@Test
	public void dataGridExchange() {
		PageHelper page = new PageHelper();
		page.setPage(0);
		page.setRows(10);
		page.setStart(0);
		page.setEnd(10);
		
		Exchange exchange = new Exchange();
		List<Exchange> list = exchangeMapper.datagridExchange(page, exchange);
		
		for (Exchange e : list) {
			System.out.println(e.toString());
		}
	}
	
	@Test
	public void findSaleListBy() {
		
		User user = new User();
		user.setId("2");

		List<Activity> atyList = activityMapper.getActivityList(" and publisherId = '" + user.getId() + "'");
		Set<String> set = new HashSet<String>();
		for (Activity aty : atyList) {
			set.add(aty.getPublicCode());
		}
		String[] publicCodeArr = set.toArray(new String[0]);
		String publicCode = Tool.stringArrayToString(publicCodeArr, true, ",");
		
//		String publicCode = "123";
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);  // 年
		int month = cal.get(Calendar.MONTH);  // 月
		
		int prevYear = year - 1;  // 上年
		int nextMonth = month + 1;
		
		String begin = prevYear + "-" + (month < 10 ? ("0" + month) : month);
		String end = year + "-" + (nextMonth < 10 ? "0" + nextMonth : nextMonth);
		
		StringBuffer conditionSql = new StringBuffer();
		conditionSql
			.append(" and ((t.year = '").append(prevYear).append("' and t.month > '").append(month-1).append("') ")
			.append(" or (t.year = '").append(year).append("' and t.month < '").append(month).append("')) ");
		
		PageHelper page = new PageHelper();
		page.setStart(0);
		page.setEnd(20);
		List<Sale> list = saleMapper.findSaleListPage(page, publicCode, conditionSql.toString());
		for (Sale s : list) {
			System.out.println(s);
		}
	}
	
	@Test
	public void addWaresBatch() {
		List<Map<String, String>> waresList = new ArrayList<Map<String, String>>();
		for (int i=0; i<3; i++) {
			Map<String,String> map = new HashMap<String,String>();  
			map.put("id", Tool.generateMajorKey());
			map.put("publicCode", "789");
			map.put("privateCode", RandomUtil.generateMixString(5));
			map.put("insideCode", RandomUtil.generateMixString(3));
			map.put("creater", "root");
			map.put("createTime", DateUtil.formatDate(new Date()));
			map.put("status", "0");
			
			waresList.add(map);
		}
		
		//waresMapper.addWaresBatch(waresList);
	}
	
	@Test
	public void addWaresBatch1() {
		List<Wares> waresList = new ArrayList<Wares>();
		for (int i=0; i<3; i++) {
			Wares w = new Wares();
			w.setId(Tool.generateMajorKey());
			w.setPublicCode("789");
			w.setPrivateCode(RandomUtil.generateMixString(5));
			w.setInsideCode(RandomUtil.generateMixString(3));
			w.setCreater("root");
			w.setCreateTime(new Date());
			w.setStatus("0");
			
			waresList.add(w);
		}
		
		waresMapper.addWaresBatch(waresList);
	}
	
	@Test
	public void findByConditionSql() {
		List<User> list = userMapper.findByConditionSql("");
		System.out.println(list.size());
	}
	
	@Test
	public void srPages() {
		Page<ScanRecord> page = new Page<ScanRecord>();
		
		page.setStart((page.getPage() - 1)*page.getRows());
		page.setEnd((page.getPage())*page.getRows());
		
		page.setTotal(scanRecordMapper.srPagesTotal(" and t.userId = '" + 2 + "'"));
		
		List<ScanRecord> list = scanRecordMapper.srPages(page, " and t.userId = '" + 2 + "'");
		for (ScanRecord sr : list) {
			System.out.println(sr);
		}
	}
	
	@Test
	public void exList() {
		String sql = " and t.userId = '" + 28 + "'";
		
		Page<Exchange> page = new Page<Exchange>();
		
		page.setStart((page.getPage() - 1)*page.getRows());
		page.setEnd((page.getPage())*page.getRows());
		
		//page.setTotal(exchangeMapper.exPagesTotal(sql));
		page.setContent(exchangeMapper.exPages(page, sql));
		
		List<Exchange> list = exchangeMapper.exPages(page, sql);
		for (Exchange ex : list) {
			System.out.println(ex);
		}
	}
	
	@Test
	public void srfindById() {
		ScanRecord sr = scanRecordMapper.findById("1");
		System.out.println(sr);
	}
	
	@Test
	public void getDicList() {
		List<SysDictionary> dicList = sysDictionaryMapper.getDicList(" and parentid = 'DJLX'");
		for (SysDictionary dic : dicList) {
			System.out.println(dic);
		}
	}
	
	@Test
	public void findByIdEx() {
		Exchange ex = exchangeMapper.findById("1");
		System.out.println(ex);
	}
	
	private ScanRecord pushAddress2SR(Address address) {
		ScanRecord sr = new ScanRecord();
		Result result = address.getResult();
		AddressComponent ac = null;
		
		if (result != null) {
			ac = result.getAddressComponent();
			sr.setSematicDescription(result.getSematic_description());
			sr.setFormattedAddress(result.getFormatted_address());
		}
		
		if (ac != null) {
			sr.setCountry(ac.getCountry());
			sr.setProvince(ac.getProvince());
			sr.setCity(ac.getCity());
			sr.setDistance(ac.getDistance());
			sr.setStreet(ac.getStreet());
		}
		
		return sr;
	}
	
	@Test
	public void addSR() throws Exception {
		Address address = MapUtil.location2Address(30.54, 114.35);
		ScanRecord sr = this.pushAddress2SR(address);
		sr.setLatitude(30.54);
		sr.setLongitude(114.35);
		sr.setScanTime(new Date());
		
		sr.setUserId("28");
		sr.setUserName("app");
		sr.setUserType("3");
		
		sr.setPublicCode("123");
		sr.setPrivateCode("0123029399108");
		sr.setInsideCode("0123621312");
		
		System.out.println(sr);
		
		scanRecordMapper.saveScanRecord(sr);
	}
	
	@Test
	public void addDic() {
		SysDictionary dic = new SysDictionary();
		dic.setParentid(1);
		dic.setEntryvalue("企业付款");
		dic.setEntrycode("COMP");
		
		sysDictionaryMapper.saveDic(dic);
	}
	
	@Test
	public void atyList() {
		List<Activity> atyList = activityMapper.getActivityList(" and t.publicCode = '" + "123" + "'");
    	System.out.println(atyList);
	}
	
	@Test
	public void srList() {
		String firstDay;
		String lastDay;
		
		Integer year = 2016;
    	Integer month = 01;
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		
		// 获取前月的第一天
    	Calendar cal_1=Calendar.getInstance();//获取当前日期 
    	
    	year = cal_1.get(Calendar.YEAR);  // 当年
    	month = cal_1.get(Calendar.MONTH);  // 上个月
    	
    	cal_1.add(Calendar.MONTH, -1);
    	cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
    	firstDay = format.format(cal_1.getTime());
    	System.out.println("-----1------firstDay:"+firstDay);
    	
    	//获取前月的最后一天
    	Calendar cale = Calendar.getInstance();   
    	cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天 
    	lastDay = format.format(cale.getTime());
    	System.out.println("-----2------lastDay:"+lastDay);
    	
    	StringBuffer conditionSql = new StringBuffer();
    	conditionSql.append(" and t.userType = '3'");
    	
    	conditionSql.append(" and t.scanTime >= '").append(firstDay).append("'");
    	conditionSql.append(" and t.scanTime <= date_sub('").append(lastDay).append("', interval -1 day)");
		
    	List<SrDto> srList = scanRecordMapper.findOnlyByUserAndWares(conditionSql.toString());
    	
    	/**
    	 * 获取所有的用户id
    	 */
    	Set<String> userIdSet = new HashSet<String>();
    	for (SrDto sd : srList) {
    		userIdSet.add(sd.getUserId());
    	}
    	
    	/**
    	 * 获取所有的活动列表
    	 */
    	List<Activity> atyList = activityMapper.getActivityList("");
    	
    	/**
    	 * 生成Sale列表
    	 */
    	List<Sale> saleList = new ArrayList<Sale>();
    	
    	for (String userId : userIdSet) {
    		for (Activity aty : atyList) {
    			Sale sale = new Sale();
	    		
	    		sale.setUserId(userId);
	    		sale.setYear(year);
	    		sale.setMonth(month);
	    		
	    		Integer amount = 0;
    		
	    		for (SrDto dto : srList) {
	    			if (dto.getPublicCode().equals(aty.getPublicCode()) && dto.getUserId().equals(userId)) {
	    				sale.setActivityId(aty.getId());
	    				amount += dto.getCount();
		    		}
	    		}
	    		
	    		sale.setAmount(amount + 0.0d);
	    		
	    		if (Tool.isNotNullOrEmpty(sale.getActivityId()))	saleList.add(sale);
    		}
    	}
    	
    	for (Sale s : saleList) {
    		System.out.println(s);
    	}
    	
    	/**
    	 * 将saleList数据保存到sale数据表中
    	 */
    	saleMapper.addSaleBatch(saleList);
	}
}
