package com.crm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.AnalysisMapper;
import com.crm.domain.dto.PlaceAnalysis;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	AnalysisService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:09:23
 * @Version 	V1.0    
 */
@Service
public class AnalysisService {
	
	@Autowired
	private AnalysisMapper analysisMapper;

	public List<PlaceAnalysis> findPlaceAnalysis(String publicCode, String userType) {
		return analysisMapper.findPlaceAnalysis(publicCode, userType);
	}
	
	public List<PlaceAnalysis> findPlaceAnalysisPage(String publicCode, String userType, PageHelper page) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return analysisMapper.findPlaceAnalysisPage(publicCode, userType, page);
	}
	
}
 