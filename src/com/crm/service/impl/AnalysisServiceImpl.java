package com.crm.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.AnalysisMapper;
import com.crm.domain.dto.PlaceAnalysis;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.AnalysisService;

/** 
 * @ClassName	AnalysisService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月2日 下午7:09:23
 * @Version 	V1.0    
 */
@Service
public class AnalysisServiceImpl implements AnalysisService {
	
	@Autowired
	private AnalysisMapper analysisMapper;

	public List<PlaceAnalysis> findPlaceAnalysis(String level, String publicCode, String province, String city, String userId) {
		return analysisMapper.findPlaceAnalysis(level, publicCode, province, city, userId);
	}
	
	public List<PlaceAnalysis> findPlaceAnalysisPage(String publicCode, String userId, PageHelper page) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getRows());
		return analysisMapper.findPlaceAnalysisPage(publicCode, userId, page);
	}
	
}
 