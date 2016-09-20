package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.system.SysDictionary;

/** 
 * @ClassName	SysDictionaryMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月20日 上午12:46:52
 * @Version 	V1.0    
 */
public interface SysDictionaryMapper {
	
	public int saveDic(SysDictionary dic);
	
	public int deleDic(@Param("id") Integer id);
	
	public int updateDic(SysDictionary dic);
	
	public List<SysDictionary> getDicList(@Param("conditionSql") String conditionSql);

}
 