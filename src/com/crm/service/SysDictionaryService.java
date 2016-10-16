package com.crm.service;

import java.util.List;

import com.crm.domain.system.SysDictionary;

/** 
 * @ClassName	SysDictionaryService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月20日 上午1:08:10
 * @Version 	V1.0    
 */
public interface SysDictionaryService {
	
	public int saveDic(SysDictionary dic);
	
	public int deleDic(Integer id);
	
	public int updateDic(SysDictionary dic);
	
	public List<SysDictionary> getDicList(String conditionSql);
	
	public SysDictionary findById(Integer id);
	
	public int deleDics(Integer[] ids);

}
 