package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.dto.PlaceAnalysis;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AnalysisMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:02:46
 * @Version 	V1.0    
 */
public interface AnalysisMapper {

	public List<PlaceAnalysis> findPlaceAnalysis(@Param("level") String level, @Param("publicCode") String publicCode, 
			@Param("province") String province, @Param("city") String city,	@Param("userId") String userId);
	
	public List<PlaceAnalysis> findPlaceAnalysisPage(@Param("publicCode") String publicCode, @Param("userId") String userId,
			@Param("page") PageHelper page);
	
	public Long getPalceAnalysisTotal(@Param("publicCode") String publicCode, @Param("userId") String userId);
	
}
 