package com.crm.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.SysDictionaryMapper;
import com.crm.domain.system.SysDictionary;

/** 
 * @ClassName	SysDictionaryService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月20日 上午1:08:10
 * @Version 	V1.0    
 */
@Service
public class SysDictionaryService {
	
	@Resource
	private SysDictionaryMapper sysDictionaryMapper;
	
	public int saveDic(SysDictionary dic) {
		return sysDictionaryMapper.saveDic(dic);
	}
	
	public int deleDic(Integer id) {
		return sysDictionaryMapper.deleDic(id);
	}
	
	public int updateDic(SysDictionary dic) {
		return sysDictionaryMapper.updateDic(dic);
	}
	
	public List<SysDictionary> getDicList(String conditionSql) {
		return sysDictionaryMapper.getDicList(conditionSql);
	}

}
 