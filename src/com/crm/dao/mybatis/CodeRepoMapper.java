package com.crm.dao.mybatis;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.crm.domain.CodeRepo;
import com.crm.domain.User;
import com.crm.domain.easyui.PageHelper;

/** 
 * @ClassName	CodeRepoMapper.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:10:05
 * @Version 	V1.0    
 */
public interface CodeRepoMapper {
	
	// 新增CodeRepo
	public int saveCodeRepo(CodeRepo codeRepo) throws Exception;

	// 删除CodeRepo
	public int deleteCodeRepo(@Param("id") String id);
	
	// 修改CodeRepo
	public int updateCodeRepo(CodeRepo codeRepo);
	
	public Long getDatagridTotal(CodeRepo codeRepo);
	
	public List<CodeRepo> datagridCodeRepo(@Param("page") PageHelper page, @Param("codeRepo") CodeRepo codeRepo);
	
	// 查询所有活动
	public List<CodeRepo> getCodeRepoList(@Param("page") PageHelper page, @Param("conditionSql") String conditionsql); 
	
	// 根据用户名查询
	public List<CodeRepo> findByCondition(@Param("conditionSql") String conditionSql) ;

	// 根据用户ID查询
	public List<CodeRepo> findByUser(User user);
	
	public CodeRepo findById(@Param("id") String id);	
	
	public List<CodeRepo> findByWaresId(@Param("waresId") String waresId);
	
}
 