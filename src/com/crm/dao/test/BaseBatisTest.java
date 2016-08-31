package com.crm.dao.test;

import java.text.SimpleDateFormat;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/** 
 * @ClassName	BaseBatisTest.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月6日 上午11:50:22
 * @Version 	V1.0    
 */
@RunWith(SpringJUnit4ClassRunner.class) // 用于配置spring中测试的环境 
@ContextConfiguration(locations = { "classpath:config/spring-mybatis.xml"})
public abstract class BaseBatisTest extends AbstractJUnit4SpringContextTests {

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
}
 