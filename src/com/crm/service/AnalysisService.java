package com.crm.service;

import java.util.List;

import com.crm.domain.dto.PlaceAnalysis;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AnalysisService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:09:23
 * @Version 	V1.0    
 */
public interface AnalysisService {

	public List<PlaceAnalysis> findPlaceAnalysis(String publicCode, String userId);
	
	public List<PlaceAnalysis> findPlaceAnalysisPage(String publicCode, String userId, PageHelper page);
	
}
 