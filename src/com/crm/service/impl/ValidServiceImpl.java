package com.crm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.ValidDao;
import com.crm.service.ValidService;

/** 
 * @ClassName	ValidService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月30日 下午1:32:12
 * @Version 	V1.0    
 */
@Service("validService")
@Scope("singleton")
public class ValidServiceImpl implements ValidService {
	
	@Autowired
	private ValidDao validDao;
	
	public void putValidCode(final String phone, final String code) {
		validDao.putValidCode(phone, code);
	}
	
	public String getValidCode(final String key) {
		return validDao.getValidCode(key);
	}
	
	public void deleteCode(final String key) {
		validDao.deleteCode(key);
	}
	
	public void deleteAll() {
		validDao.deleteAll();
	}

}
 