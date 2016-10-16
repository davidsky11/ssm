package com.crm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Page;
import com.crm.domain.ScanRecord;
import com.crm.domain.User;
import com.crm.domain.Wares;
import com.crm.domain.dto.ScanRecordDto;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ActivityService;
import com.crm.service.AwardService;
import com.crm.service.ScanRecordService;
import com.crm.util.Tool;
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
	
	@Resource
	private ScanRecordService scanRecordService;
	@Resource
	private ActivityService activityService;
	@Resource
	private AwardService awardService;

	/**
	 * @Title:			scanRecordList
	 * @Description:	跳转扫码记录列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/scanRecord/list", method = RequestMethod.GET)
	public String scanRecordList(Model model) throws IOException { 
		return "scanRecord/list";
	}
	
	/**
	 * 新增扫码记录
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/scanRecord/addScanRecord",method = RequestMethod.POST)
    public Json addScanRecord(ScanRecord scanRecord) {
		Json j = new Json();
		
		try {
            scanRecordService.saveScanRecord(scanRecord);
            j.setSuccess(true);
            j.setMsg("扫码记录新增成功！");
            j.setObj(scanRecord);
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
    }
	
	/**
     * 修改扫码记录
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
	 * 删除某个扫码记录
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
		page.setSort("scanTime");
		page.setOrder("desc");
		
		if (user != null) {
			ScanRecord scanRecord = new ScanRecord();
			scanRecord.setUserId(user.getId());
			
			dg.setTotal(scanRecordService.getDatagridTotal(scanRecord));
			List<ScanRecord> scanRecordList = scanRecordService.datagridScanRecord(page, scanRecord);
			
			List<Activity> actList = activityService.getActivityList("");
			HashMap<String, Activity> actMap = new HashMap<String, Activity>();
			for (Activity act : actList) {
				actMap.put(act.getPublicCode(), act);
			}
			
			List<Award> awardList = awardService.getDatagrid("");
			HashMap<String, Award> awardMap = new HashMap<String, Award>();
			for (Award award : awardList) {
				awardMap.put(award.getId(), award);
			}
			
			List<ScanRecordDto> list = new ArrayList<ScanRecordDto>();
			for (ScanRecord sr : scanRecordList) {
				ScanRecordDto srd = new ScanRecordDto(sr);
				srd.setActivityName(actMap.get(sr.getPublicCode()) == null ? "" :
					actMap.get(sr.getPublicCode()).getTitle());
				
				Wares wares = sr.getWares();
				if (wares != null) {
					String awardId = wares.getAwardId();
					srd.setAwardName(awardMap.get(awardId) == null ? "" 
							: awardMap.get(awardId).getTitle());
					
				}
				
				list.add(srd);
			}
			
			
			dg.setRows(list);
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
		scanRecord.setUserName(username);
		
		dg.setTotal(scanRecordService.getDatagridTotal(scanRecord));
		List<ScanRecord> scanRecordList = scanRecordService.datagridScanRecord(page, scanRecord);
		dg.setRows(scanRecordList);
		return dg;
	}
	
	
	///////////////////////////////////////// NEW DASHBOARD  ////////////////////////////
	
	/**
	 * 针对APP用户和商户
	 * @Title:			srList
	 * @Description:	TODO(这里用一句话描述这个方法的作用)
	 * @param model
	 * @param request
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/scanRecord/srList", method = RequestMethod.GET)
	public String srList(Model model, HttpServletRequest request,
			@RequestParam(value="pageNumber",defaultValue="1") int pageNumber) {
		User user =  (User) request.getSession().getAttribute(Const.SESSION_USER);
		
		String publicCode = Tool.nvl(request.getParameter("publicCode"));
		String startDate = Tool.nvl(request.getParameter("startDate"));
		String endDate = Tool.nvl(request.getParameter("endDate"));
		
		model.addAttribute("userType", user.getUserType());
		Page<ScanRecord> page = new Page<ScanRecord>();
		page.setPage(pageNumber);
		page.setSort("t.scanTime");
		page.setOrder("desc");
		
		StringBuffer conditionSql = new StringBuffer();
		conditionSql.append(" and t.userId = '" + user.getId() + "'");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if (Tool.isNotNullOrEmpty(publicCode)) {
			conditionSql.append(" and t.publicCode = '").append(publicCode).append("'");
			paramMap.put("publicCode", publicCode);
			model.addAttribute("publicCode", publicCode);
		}
		
		if (Tool.isNotNullOrEmpty(startDate)) {
			conditionSql.append(" and t.scanTime >= '").append(startDate).append("'");
			paramMap.put("startDate", startDate);
			model.addAttribute("startDate", startDate);
		}
		
		if (Tool.isNotNullOrEmpty(endDate)) {
			conditionSql.append(" and t.scanTime <= date_sub('").append(endDate).append("', interval -1 day)");
			paramMap.put("endDate", endDate);
			model.addAttribute("endDate", endDate);
		}
		
		if (user != null) {
			page = scanRecordService.srPages(page, conditionSql.toString());
		}
		
		List<Activity> atyList = activityService.getActivityList("");
		model.addAttribute("atyList", atyList);
		
		List<Award> awardList = awardService.getDatagrid("");
		List<ScanRecord> list = page.getContent();
		for (ScanRecord sr : list) {
			if (sr.getWares() != null) {
				for (Award aw : awardList) {
					if (aw.getId().equals(sr.getWares().getAwardId())) {
						sr.setAward(aw);
						continue;
					}
				}
			}
			
			for (Activity aty : atyList) {
				if (sr.getPublicCode() != null 
						&& sr.getPublicCode().equals(aty.getPublicCode())) {
					sr.setActivity(aty);
					continue;
				}
			}
		}
		
		model.addAttribute("searchParams", Tool.doneQueryParam(paramMap));
		model.addAttribute("page", page);
		model.addAttribute("srs", list);
		
		return "scanRecord/srList";
	}
	
	@RequestMapping(value="/scanRecord/detail/{id}", method=RequestMethod.GET)
	public String detailScanRecord(Model model, @PathVariable("id") String id) {
		ScanRecord sr = scanRecordService.findById(id);
		model.addAttribute("sr", sr);
		
		return "scanRecord/detail";
	}
	
	/**
	 * 针对厂商
	 * @Title:			srList
	 * @Description:	针对厂商的扫码记录
	 * @param model
	 * @param request
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/scanRecord/list4Vender", method = RequestMethod.GET)
	public String list4Vender(Model model, HttpServletRequest request,
			@RequestParam(value="pageNumber",defaultValue="1") int pageNumber) {
		User user =  (User) request.getSession().getAttribute(Const.SESSION_USER);
		
		/**
		 * 1、获取的当前厂商发布的活动列表
		 */
		List<Activity> atyList = activityService.getActivityList(" and t.publisherId = '" + user.getId() + "'");
		model.addAttribute("atyList", atyList);
		
		/**
		 * 2、根据条件查询
		 */
		String publicCode = Tool.nvl(request.getParameter("publicCode"));
		String startDate = Tool.nvl(request.getParameter("startDate"));
		String endDate = Tool.nvl(request.getParameter("endDate"));
		
		model.addAttribute("userType", user.getUserType());
		Page<ScanRecord> page = new Page<ScanRecord>();
		page.setPage(pageNumber);
		page.setSort("scanTime");
		page.setOrder("desc");
		
		StringBuffer conditionSql = new StringBuffer();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if (Tool.isNotNullOrEmpty(publicCode)) {
			conditionSql.append(" and t.publicCode = '").append(publicCode).append("'");
			paramMap.put("publicCode", publicCode);
			model.addAttribute("publicCode", publicCode);
		}
		
		if (Tool.isNotNullOrEmpty(startDate)) {
			conditionSql.append(" and t.scanTime >= '").append(startDate).append("'");
			paramMap.put("startDate", startDate);
			model.addAttribute("startDate", startDate);
		}
		
		if (Tool.isNotNullOrEmpty(endDate)) {
			conditionSql.append(" and t.scanTime <= date_sub('").append(endDate).append("', interval -1 day)");
			paramMap.put("endDate", endDate);
			model.addAttribute("endDate", endDate);
		}
		
		if (user != null) {
			page = scanRecordService.selectByPublisher(page, conditionSql.toString(), user.getId());
		}
		
		model.addAttribute("searchParams", Tool.doneQueryParam(paramMap));
		model.addAttribute("page", page);
		model.addAttribute("srs", (page == null ? new ArrayList<ScanRecord>() : page.getContent()));
		
		return "scanRecord/list4Vender";
	}
	
}
 