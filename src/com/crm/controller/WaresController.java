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

import com.crm.domain.Wares;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.WaresService;

/** 
 * @ClassName	WaresController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:41:33
 * @Version 	V1.0    
 */
@Controller
public class WaresController {
	
	private final Logger log = LoggerFactory.getLogger(WaresController.class);
	
	@Autowired
	private WaresService waresService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/wares/list", method = RequestMethod.GET)
	public String waresList(Model model) throws IOException { 
		return "wares/list";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/wares/addWares",method = RequestMethod.POST)
    public Json addWares(Wares wares) {
		Json j = new Json();
		
		try {
            waresService.addWares(wares);
            j.setSuccess(true);
            j.setMsg("用户新增成功！");
            j.setObj(wares);
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
    @RequestMapping(value = "/wares/editWares",method = RequestMethod.POST)
    public Json editWares(Wares wares) {
        Json j = new Json();
        log.debug("穿过来的用户ID为：" + wares.getId());
        try {
            waresService.updateWares(wares);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            j.setObj(wares);
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
	@RequestMapping(value = "/wares/deleteWares",method = RequestMethod.POST)
	public Json deleteWares(Wares wares) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+wares.getId());
        try {
			waresService.deleteWares(wares.getId());
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
	@RequestMapping(value = "/wares/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page) {
		DataGrid dg = new DataGrid();
		
		Wares wares = new Wares();
		dg.setTotal(waresService.getDatagridTotal(wares));
		List<Wares> waresList = waresService.datagridWares(page, wares);
		dg.setRows(waresList);
		return dg;
	}
	
}
 