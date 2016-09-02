package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.dto.PlaceAnalysis;

/** 
 * @ClassName	AnalysisMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:02:46
 * @Version 	V1.0    
 */
public interface AnalysisMapper {

	public List<PlaceAnalysis> findPlaceAnalysis(@Param("publicCode") String publicCode, @Param("userType") String userType);
	
}
 