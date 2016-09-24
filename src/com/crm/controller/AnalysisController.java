package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crm.domain.Activity;
import com.crm.domain.Sale;
import com.crm.domain.User;
import com.crm.domain.dto.AbstractChartDto;
import com.crm.domain.dto.PlaceAnalysis;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ActivityService;
import com.crm.service.AnalysisService;
import com.crm.service.SaleService;
import com.crm.util.Tool;
import com.crm.util.common.Const;
import com.google.gson.Gson;

/** 
 * @ClassName	AnalysisController.java
 * @Description 
 * @Author		kevin 
 * @CreateTime  2016年9月6日 上午11:14:56
 * @Version 	V1.0    
 */
@Controller
public class AnalysisController {

	private final Logger log = LoggerFactory.getLogger(ScanRecordController.class);
	
	@Resource
	private ActivityService activityService;
	@Resource
	private AnalysisService analysisService;
	@Resource
	private SaleService saleService;
	
	/**
	 * @Title:			placeList
	 * @Description:	跳转前端区域统计
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/place/list", method = RequestMethod.GET)
	public ModelAndView placeList(Model model) throws IOException { 
		ModelAndView mv = new ModelAndView("place/list");
		
		List<Activity> atyList = this.activityService.getActivityList("");
		mv.addObject("atyList", atyList);
		
		return mv;
	}
	
	/**
	 * @Title:			saleList
	 * @Description:	跳转前端销售统计
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/sale/list", method = RequestMethod.GET)
	public ModelAndView saleList(Model model) throws IOException { 
		ModelAndView mv = new ModelAndView("sale/list");
		
		List<Activity> atyList = this.activityService.getActivityList("");
		mv.addObject("atyList", atyList);
		
		return mv;
	}
	
	/**
	 * @Title:			placeAnalysis
	 * @Description:	地域统计
	 * @param request
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/place/datagrid", method = RequestMethod.POST)
	public DataGrid placeDatagrid(HttpServletRequest request, PageHelper page) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		DataGrid dg = new DataGrid();
		page.setSort("scanTime");
		page.setOrder("desc");
		
		String publicCode = request.getParameter("publicCode");
		/*if (publicCode == null || publicCode.equals("")) {
			List<Activity> atyList = this.activityService.getActivityList("");
			Activity aty = (atyList != null && atyList.size() > 0) ? atyList.get(0) : new Activity();
			publicCode = aty.getPublicCode();
		}*/
		
		if (publicCode != null && !publicCode.equals("")) {
			
		} else {
			List<Activity> atyList = activityService.getActivityList(" and publisherId = '" + user.getId() + "'");
			Set<String> set = new HashSet<String>();
			for (Activity aty : atyList) {
				set.add(aty.getPublicCode());
			}
			String[] publicCodeArr = set.toArray(new String[0]);
			publicCode = Tool.stringArrayToString(publicCodeArr, true, ",");
		}

		if (user != null) {
			List<PlaceAnalysis> paList = analysisService.findPlaceAnalysisPage(publicCode, Const.USERTYPE_VENDER, page);
			
			dg.setTotal(paList.size());
			dg.setRows(paList);
		}
		return dg;
	}
	
	/**
	 * @Title:			saleAnalysis
	 * @Description:	销售统计
	 * @param request
	 * @param page
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/sale/datagrid", method = RequestMethod.POST)
	public DataGrid saleDatagrid(HttpServletRequest request, PageHelper page) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		DataGrid dg = new DataGrid();
		//page.setSort("scanTime");
		//page.setOrder("desc");
		
		String publicCode = request.getParameter("publicCode");
		
		if (publicCode != null && !publicCode.equals("")) {
			
		} else {
			List<Activity> atyList = activityService.getActivityList(" and publisherId = '" + user.getId() + "'");
			Set<String> set = new HashSet<String>();
			for (Activity aty : atyList) {
				set.add(aty.getPublicCode());
			}
			String[] publicCodeArr = set.toArray(new String[0]);
			publicCode = Tool.stringArrayToString(publicCodeArr, true, ",");
		}
		
		/*if (publicCode == null || publicCode.equals("")) {
			List<Activity> atyList = this.activityService.getActivityList("");
			Activity aty = (atyList != null && atyList.size() > 0) ? atyList.get(0) : new Activity();
			publicCode = aty.getPublicCode();
		}*/
		
		if (user != null) {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);  // 年
			int month = cal.get(Calendar.MONTH);  // 月
			
			int prevYear = year - 1;  // 上年
			int nextMonth = month + 1;
			
			String begin = prevYear + "-" + (month < 10 ? ("0" + month) : month);
			String end = year + "-" + (nextMonth < 10 ? "0" + nextMonth : nextMonth);
			
			StringBuffer conditionSql = new StringBuffer();
			conditionSql
				.append(" and (t.year = '").append(prevYear).append("' and t.month > '").append(month-1).append("') ")
				.append(" or (t.year = '").append(year).append("' and t.month < '").append(month).append("') ");
			
			/**
			 * 1、直接从销售统计表里获取数据
			 */
			List<Sale> saleList = saleService.findSaleListPage(page, publicCode, conditionSql.toString());
			
			dg.setTotal(saleList.size());
			dg.setRows(saleList);
		}
		return dg;
	}
	
	//////////////////////////////////////////////////////////
	@RequestMapping(value = "/sale/statistic", method = RequestMethod.GET)
	public String saleDatagrid(Model model, HttpServletRequest request) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		List<Activity> atyList = new ArrayList<Activity>();

		switch (user.getUserType()) {
			case Const.USERTYPE_VENDER:  // 厂商
				atyList = activityService.getActivityList(" and t.publisherId = '" + user.getId() + "'"); // 发布的活动
				break;
			case Const.USERTYPE_DEALER: // 经销商
				atyList = activityService.getActivityList("");  // 所有活动
				break;
		}
		
		model.addAttribute("atyList", atyList);
		return "sale/statistic";
	}
	
	@RequestMapping(value = "/ajax_sale_charts.do", method = RequestMethod.POST)
	public void ajaxSaleCharts(HttpServletRequest request,
			HttpServletResponse response) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		List<AbstractChartDto> list = new ArrayList<AbstractChartDto>();
		String activityId = Tool.nvl(request.getParameter("activityId"));
		
		List<Activity> atyList = new ArrayList<Activity>();
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);  // 年
		int month = cal.get(Calendar.MONTH);  // 月
		
		int prevYear = year - 1;  // 上年
		int nextMonth = month + 1;
		
		String begin = prevYear + "-" + (month < 10 ? ("0" + month) : month);
		String end = year + "-" + (nextMonth < 10 ? "0" + nextMonth : nextMonth);
		
		StringBuffer conditionSql = new StringBuffer();
		
		List<Sale> saleList = new ArrayList<Sale>();
		
		switch (user.getUserType()) {
			case Const.USERTYPE_VENDER:  // 厂商
				conditionSql
					.append(" and (t.year = '").append(prevYear).append("' and t.month > '").append(month-1).append("') ")
					.append(" or (t.year = '").append(year).append("' and t.month < '").append(month).append("') ");
				
				if (Tool.isNotNullOrEmpty(activityId)) {
					conditionSql.append(" and t.activityId = '").append(activityId).append("'");
				}
				
				break;
			case Const.USERTYPE_DEALER:  // 经销商
				conditionSql
					.append(" and ((t.year = '").append(prevYear).append("' and t.month > '").append(month).append("') ")
					.append(" or (t.year = '").append(year).append("' and t.month <= '").append(month+1).append("')) ")
					.append(" and t.userId = '").append(user.getId()).append("'");
				
				if (Tool.isNotNullOrEmpty(activityId)) {
					conditionSql.append(" and t.activityId = '").append(activityId).append("'");
				}
				
				break;
			default:
				
				break;
		}
		
		/**
		 * 1、直接从销售统计表里获取数据
		 */
		saleList = saleService.findSaleList(conditionSql.toString());
		
		Collections.sort(saleList);  // 对销售记录进行排序
		
		for (Sale sale : saleList) {
			AbstractChartDto dto = new AbstractChartDto();
			dto.setCategory(sale.getAmount() + "");
			dto.setBar(sale.getYear() + "-" + sale.getMonth());
			
			list.add(dto);
		}
		
		//将json数据返回给客户端
        response.setContentType("application/json; charset=utf-8");
		
        PrintWriter out = null;
		try {
			out = response.getWriter();
			Gson gson = new Gson();
			String json = gson.toJson(list);
			System.out.println(json);
			out.write(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
	@RequestMapping(value = "/place/statistic", method = RequestMethod.GET)
	public String placeDatagrid(Model model, HttpServletRequest request) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		List<Activity> atyList = new ArrayList<Activity>();

		switch (user.getUserType()) {
			case Const.USERTYPE_VENDER:  // 厂商
				atyList = activityService.getActivityList(" and t.publisherId = '" + user.getId() + "'"); // 发布的活动
				break;
			case Const.USERTYPE_DEALER: // 经销商
				atyList = activityService.getActivityList("");  // 所有活动
				break;
		}
		
		model.addAttribute("atyList", atyList);
		return "place/statistic";
	}
	
	@RequestMapping(value = "/ajax_place_charts.do", method = RequestMethod.POST)
	public void ajaxPlaceCharts(HttpServletRequest request,
			HttpServletResponse response) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		List<AbstractChartDto> list = new ArrayList<AbstractChartDto>();
		String publicCode = Tool.nvl(request.getParameter("publicCode"));
		
		List<PlaceAnalysis> paList = new ArrayList<PlaceAnalysis>();
		
		switch (user.getUserType()) {
			case Const.USERTYPE_VENDER:  // 厂商
				paList = analysisService.findPlaceAnalysis(publicCode, "");
				
				break;
			case Const.USERTYPE_DEALER:  // 经销商
				paList = analysisService.findPlaceAnalysis(publicCode, user.getId());
				
				break;
			default:
				
				break;
		}
		
		for (PlaceAnalysis pa : paList) {
			AbstractChartDto dto = new AbstractChartDto();
			dto.setCategory(pa.getCount() + "");
			dto.setBar(pa.getProvince());
			
			list.add(dto);
		}
		
		//将json数据返回给客户端
        response.setContentType("application/json; charset=utf-8");
		
        PrintWriter out = null;
		try {
			out = response.getWriter();
			Gson gson = new Gson();
			String json = gson.toJson(list);
			System.out.println(json);
			out.write(json);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
	
}
 