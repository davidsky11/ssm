package com.crm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ScanRecordService;
import com.crm.util.common.Const;

/** 
 * @ClassName	AccountController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年7月5日 下午3:41:33
 * @Version 	V1.0    
 */
@Controller
public class ScanRecordController /*extends BaseController*/ {
	
	private final Logger log = LoggerFactory.getLogger(ScanRecordController.class);
	
	@Autowired
	private ScanRecordService scanRecordService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/scanRecord/list", method = RequestMethod.GET)
	public String scanRecordList(Model model) throws IOException { 
		return "scanRecord/list";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/scanRecord/addScanRecord",method = RequestMethod.POST)
    public Json addScanRecord(ScanRecord scanRecord) {
		Json j = new Json();
		
		try {
            scanRecordService.saveScanRecord(scanRecord);
            j.setSuccess(true);
            j.setMsg("用户新增成功！");
            j.setObj(scanRecord);
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
    @RequestMapping(value = "/scanRecord/editScanRecord", method = RequestMethod.POST)
    public Json editScanRecord(ScanRecord scanRecord) {
        Json j = new Json();
        log.debug("穿过来的用户ID为：" + scanRecord.getId());
        try {
            scanRecordService.updateScanRecord(scanRecord);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            j.setObj(scanRecord);
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
	@RequestMapping(value = "/scanRecord/deleteScanRecord",method = RequestMethod.POST)
	public Json deleteScanRecord(ScanRecord scanRecord) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+scanRecord.getId());
        try {
			scanRecordService.deleteScanRecord(scanRecord.getId());
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
	@RequestMapping(value = "/scanRecord/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(HttpServletRequest request, PageHelper page) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		DataGrid dg = new DataGrid();
		
		if (user != null) {
			ScanRecord scanRecord = new ScanRecord();
			scanRecord.setAccountId(user.getId());
			
			dg.setTotal(scanRecordService.getDatagridTotal(scanRecord));
			List<ScanRecord> scanRecordList = scanRecordService.datagridScanRecord(page, scanRecord);
			dg.setRows(scanRecordList);
		}
		return dg;
	}
	
	/**
	 * 前端用户表格
	 */
	@ResponseBody
	@RequestMapping(value = "/scanRecord/searchScanRecord", method = RequestMethod.POST)
	public DataGrid datagrid(PageHelper page, String username) {
		DataGrid dg = new DataGrid();
		
		ScanRecord scanRecord = new ScanRecord();
		scanRecord.setAccountName(username);
		
		dg.setTotal(scanRecordService.getDatagridTotal(scanRecord));
		List<ScanRecord> scanRecordList = scanRecordService.datagridScanRecord(page, scanRecord);
		dg.setRows(scanRecordList);
		return dg;
	}
	
}
 