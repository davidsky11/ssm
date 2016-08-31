package com.crm.controller;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.Award;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.AwardService;

/** 
 * @ClassName	AccountController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:41:33
 * @Version 	V1.0    
 */
@Controller
public class AwardController {
	
	private final Logger log = LoggerFactory.getLogger(AwardController.class);
	
	@Autowired
	private AwardService awardService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/award/list", method = RequestMethod.GET)
	public String awardList(Model model) throws IOException { 
		return "award/list";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/award/addAward",method = RequestMethod.POST)
    public Json addAward(Award award) {
		Json j = new Json();
		
		try {
            awardService.addAward(award);
            j.setSuccess(true);
            j.setMsg("用户新增成功！");
            j.setObj(award);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
     * 修改用户
     * 
     * @param user
     * @return
     */
	@ResponseBody
    @RequestMapping(value = "/award/editAward",method = RequestMethod.POST)
    public Json editAward(Award award) {
        Json j = new Json();
        log.debug("穿过来的用户ID为：" + award.getId());
        try {
            awardService.updateAward(award);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            j.setObj(award);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
	 * 删除某个用户
	 * @param userId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/award/deleteAward",method = RequestMethod.POST)
	public Json deleteAward(Award award) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+award.getId());
        try {
			awardService.deleteAward(award.getId());
			j.setSuccess(true);
	        j.setMsg("删除成功！");
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
	}
	
	/**
	 * 前端用户表格
	 */
	@ResponseBody
	@RequestMapping(value = "/award/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page) {
		DataGrid dg = new DataGrid();
		
		Award award = new Award();
		dg.setTotal(awardService.getDatagridTotal(award));
		List<Award> awardList = awardService.datagridAward(page, award);
		dg.setRows(awardList);
		return dg;
	}
	
}
 