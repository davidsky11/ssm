package com.crm.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crm.dao.mybatis.CodeRepoMapper;
import com.crm.domain.CodeRepo;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	CodeRepoService.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年8月6日 下午12:05:53
 * @Version 	V1.0    
 */
@Service
public class CodeRepoService {
	
	@Autowired
	private CodeRepoMapper codeRepoMapper;
	
	// 新增CodeRepo
	public int saveCodeRepo(CodeRepo codeRepo) throws Exception {
		return codeRepoMapper.saveCodeRepo(codeRepo);
	}

	// 删除CodeRepo
	public int deleteCodeRepo(@Param("id") String id) {
		return codeRepoMapper.deleteCodeRepo(id);
	}
	
	// 修改CodeRepo
	public int updateCodeRepo(CodeRepo codeRepo) {
		return codeRepoMapper.updateCodeRepo(codeRepo);
	}
	
	public Long getDatagridTotal(CodeRepo codeRepo) {
		return codeRepoMapper.getDatagridTotal(codeRepo);
	}
	
	public List<CodeRepo> datagridCodeRepo(@Param("page") PageHelper page, @Param("codeRepo") CodeRepo codeRepo) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return codeRepoMapper.datagridCodeRepo(page, codeRepo);
	}
	
	// 查询所有活动
	public List<CodeRepo> getCodeRepoList(@Param("page") PageHelper page, @Param("conditionSql") String conditionsql) {
		page.setStart((page.getPage()-1)*page.getRows());
		page.setEnd(page.getPage()*page.getRows());
		return codeRepoMapper.getCodeRepoList(page, conditionsql);
	}
	
	// 根据用户名查询
	public List<CodeRepo> findByCondition(@Param("conditionSql") String conditionSql) {
		return codeRepoMapper.findByCondition(conditionSql);
	}

	// 根据用户ID查询
	public List<CodeRepo> findByUser(User user) {
		return codeRepoMapper.findByUser(user);
	}
	
	public CodeRepo findById(@Param("id") String id) {
		return codeRepoMapper.findById(id);
	}
	
	public List<CodeRepo> findByWaresId(@Param("waresId") String waresId){
		return codeRepoMapper.findByWaresId(waresId);
	}
	

}
 