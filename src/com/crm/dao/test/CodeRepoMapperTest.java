package com.crm.dao.test;

import java.util.Date;
import java.util.UUID;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.crm.dao.mybatis.CodeRepoMapper;
import com.crm.domain.CodeRepo;

/** 
 * @ClassName	CodeRepoMapperTest.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月6日 上午11:51:06
 * @Version 	V1.0    
 */
public class CodeRepoMapperTest extends BaseBatisTest {

	@Autowired
	private CodeRepoMapper codeRepoMapper;
	
	@Test
	public void saveCodeRepo() {
		CodeRepo cr = new CodeRepo();
		cr.setId(UUID.randomUUID().toString().replaceAll("-", ""));
		cr.setScanTime(sdf.format(new Date()));
		cr.setScanner("2");
		cr.setScannerName("kevin");
		cr.setWaresId("111");
		cr.setScanBatch("batch");
		cr.setLatitude(158.0);
		cr.setLongitude(58.8);
		cr.setFlagCode("TEWGFS2d23ds9826ss09");
		
		try {
			this.codeRepoMapper.saveCodeRepo(cr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void deleteCodeRepo() {
		int i = this.codeRepoMapper.deleteCodeRepo("086fe9c55b8a11e6bd453417eb90ce17");
		System.out.println(i);
	}
	
	@Test
	public void updateCodeRepo() {
		CodeRepo cr = new CodeRepo();
		cr.setId("2acf7c545b8a11e6bd453417eb90ce17");
		cr.setScanTime(sdf.format(new Date()));
		cr.setScanner("2");
		cr.setScannerName("kevin");
		cr.setWaresId("11");
		cr.setScanBatch("update");
		cr.setLatitude(78.0);
		cr.setLongitude(58.8);
		cr.setFlagCode("sssdfsss09");
		
		int i = this.codeRepoMapper.updateCodeRepo(cr);
		System.out.println(i);
	}
	
	@Test
	public void getDatagridTotal() {
		CodeRepo cr = new CodeRepo();
		cr.setScanner("2");
		long l = this.codeRepoMapper.getDatagridTotal(cr);
		System.out.println(l);
	}
	
}
 