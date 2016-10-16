package com.crm.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.SysDictionaryMapper;
import com.crm.domain.system.SysDictionary;
import com.crm.service.SysDictionaryService;

/** 
 * @ClassName	SysDictionaryService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月20日 上午1:08:10
 * @Version 	V1.0    
 */
@Service
public class SysDictionaryServiceImpl implements SysDictionaryService {
	
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

	@Override
	public SysDictionary findById(Integer id) {
		return sysDictionaryMapper.findById(id);
	}

	@Override
	public int deleDics(Integer[] ids) {
		return sysDictionaryMapper.deleDics(ids);
	}

}
 