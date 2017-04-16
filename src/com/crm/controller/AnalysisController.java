package com.crm.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import com.crm.domain.dto.AddressType;
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
		
		if (user != null) {
			Calendar cal = Calendar.getInstance();
			int year = cal.get(Calendar.YEAR);  // 年
			int month = cal.get(Calendar.MONTH);  // 月
			
			int prevYear = year - 1;  // 上年
			
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
		
		Calendar cal = Calendar.getInstance();
		
		Integer[] yearArr = new Integer[]{2016, 2017, 2018, 2019, 2020};
		Integer[] monthArr = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		
		List<AddressType> levelArr = new ArrayList<AddressType>();
		levelArr.add(new AddressType("", "请选择 "));
		levelArr.add(new AddressType("year", "年份"));
		levelArr.add(new AddressType("month", "月份"));
		
		model.addAttribute("yearArr", yearArr);
		model.addAttribute("monthArr", monthArr);
		model.addAttribute("levelArr", levelArr);
		
		String yearS = Tool.nvl(request.getParameter("year"));
		model.addAttribute("year", yearS);
		
		return "sale/statistic";
	}
	
	@RequestMapping(value = "/ajax_sale_charts.do", method = RequestMethod.POST)
	public void ajaxSaleCharts(HttpServletRequest request,
			HttpServletResponse response) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		List<AbstractChartDto> list = new ArrayList<AbstractChartDto>();
		String activityId = Tool.nvl(request.getParameter("activityId"));
		String yearS = Tool.nvl(request.getParameter("year"));
		String monthS = Tool.nvl(request.getParameter("month"));
		String level = Tool.nvl(request.getParameter("level"));
		
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);  // 年
		int month = cal.get(Calendar.MONTH) + 1;  // 月
		
		int prevYear = year - 1;  // 上年
		
		int daysOfMonth = 30;
		
		if (Tool.isNotNullOrEmpty(yearS) && Tool.isNotNullOrEmpty(monthS)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date(Integer.valueOf(yearS),
					Integer.valueOf(monthS), -1));
			daysOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  // 当月的天数
		}
		
		List<String> yearMList = new ArrayList<String>();
		if (Tool.isNotNullOrEmpty(yearS)) {
			for (int i=1; i<=12; i++) yearMList.add(yearS + "-" + i);
		} else {
			int begin = month;
			while (begin <= 12) {
				yearMList.add(prevYear + "-" + begin++);
			}
			int end = 1;
			while (end <= month) {
				yearMList.add(year + "-" + end++);
			}
		}
		
		StringBuffer conditionSql = new StringBuffer();
		
		List<Sale> saleList = new ArrayList<Sale>();
		
		switch (user.getUserType()) {
			case Const.USERTYPE_VENDER:  // 厂商
				
				if (Tool.isNotNullOrEmpty(yearS)) { // 选中了年份
					switch (level) {
						case "year":
							saleList = this.saleService.findSaleList("year, month", activityId, yearS, "", "");
							break;
						case "month":
							saleList = this.saleService.findSaleList("year, month, day", activityId, yearS, monthS, "");
							break;
						default:
							
							break;
					}
				} else {
					conditionSql
						.append(" and ((t.year = '").append(prevYear).append("' and t.month > '").append(month-1).append("') ")
						.append(" or (t.year = '").append(year).append("' and t.month < '").append(month+1).append("')) ");
					if (Tool.isNotNullOrEmpty(activityId)) {
						conditionSql.append(" and t.activityId = '").append(activityId).append("'");
					}
					
					conditionSql.append(" group by year, month");
					
					saleList = saleService.getSaleList(conditionSql.toString());
					Collections.sort(saleList);  // 对销售记录进行排序
				}
				
				switch (level) {
					case "month":
						
						for (int i=1; i<=daysOfMonth; i++) {
							AbstractChartDto dto = new AbstractChartDto();
							
							dto.setBar(i + "");
							
							for (Sale sale : saleList) {
								if (sale.getDay() != null && i == sale.getDay()) {
									dto.setCategory(sale.getAmount() + "");
									continue;
								}
							}
							list.add(dto);
						}
						break;
					case "year":
												
					default:
						int size = yearMList.size();
						for (int i=0; i<size; i++) {
							AbstractChartDto dto = new AbstractChartDto();
							
							String yearM = yearMList.get(i);
							dto.setBar(yearM);
							
							for (Sale sale : saleList) {
								if (yearM.equals(sale.getYear() + "-" + sale.getMonth())) {
									dto.setCategory(sale.getAmount() + "");
									continue;
								}
							}
							list.add(dto);
						}
						break;
				}
				
				break;
			case Const.USERTYPE_DEALER:  // 经销商
				if (Tool.isNotNullOrEmpty(yearS)) { // 选中了年份
					switch (level) {
						case "year":
							saleList = this.saleService.findSaleList("year, month", activityId, yearS, "", user.getId());
							break;
						case "month":
							saleList = this.saleService.findSaleList("year, month, day", activityId, yearS, monthS, user.getId());
							break;
						default:
							
							break;
					}
				} else {
					conditionSql
						.append(" and ((t.year = '").append(prevYear).append("' and t.month > '").append(month-1).append("') ")
						.append(" or (t.year = '").append(year).append("' and t.month < '").append(month+1).append("')) ")
						.append(" and t.userId = '").append(user.getId()).append("'");
					
					if (Tool.isNotNullOrEmpty(activityId)) {
						conditionSql.append(" and t.activityId = '").append(activityId).append("'");
					}
					
					conditionSql.append(" group by year, month");
					
					saleList = saleService.getSaleList(conditionSql.toString());
					Collections.sort(saleList);  // 对销售记录进行排序
				}
				
				switch (level) {
					case "month":
						
						for (int i=1; i<=daysOfMonth; i++) {
							AbstractChartDto dto = new AbstractChartDto();
							
							dto.setBar(i + "");
							
							for (Sale sale : saleList) {
								if (sale.getDay() != null && i == sale.getDay()) {
									dto.setCategory(sale.getAmount() + "");
									continue;
								}
							}
							list.add(dto);
						}
						break;
					case "year":
					
					default:
						int size = yearMList.size();
						for (int i=0; i<size; i++) {
							AbstractChartDto dto = new AbstractChartDto();
							
							String yearM = yearMList.get(i);
							dto.setBar(yearM);
							
							for (Sale sale : saleList) {
								if (yearM.equals(sale.getYear() + "-" + sale.getMonth())) {
									dto.setCategory(sale.getAmount() + "");
									continue;
								}
							}
							list.add(dto);
						}
						
						break;
				}
					
				break;
			default:
				
				break;
		}
		
		//将json数据返回给客户端
        response.setContentType("application/json; charset=utf-8");
		
        PrintWriter out = null;
		try {
			out = response.getWriter();
			Gson gson = new Gson();
			String json = gson.toJson(list);
			System.out.println(json);
			log.info(json);
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
		
		List<AddressType> levelArr = new ArrayList<AddressType>();
		levelArr.add(new AddressType("province", "省"));
		levelArr.add(new AddressType("city", "市/县"));
		levelArr.add(new AddressType("district", "区/街道"));
		
		model.addAttribute("atyList", atyList);
		model.addAttribute("levelArr", levelArr);
		model.addAttribute("level", Const.LEVEL_PLACE_PROVINCE);
		return "place/statistic";
	}
	
	@RequestMapping(value = "/ajax_place_charts.do", method = RequestMethod.POST)
	public void ajaxPlaceCharts(HttpServletRequest request,
			HttpServletResponse response) {
		User user = (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		String level = Const.LEVEL_PLACE_PROVINCE;  // 默认省
		List<AbstractChartDto> list = new ArrayList<AbstractChartDto>();
		String publicCode = Tool.nvl(request.getParameter("publicCode"));
		String province = Tool.nvl(request.getParameter("province"));
		String city = Tool.nvl(request.getParameter("city"));
		String tmp = Tool.nvl(request.getParameter("level"));
		
		if (Tool.isNotNullOrEmpty(province)) {
			level = Const.LEVEL_PLACE_CITY;  // 市级别
		}
		if (Tool.isNotNullOrEmpty(city)) {
			level = Const.LEVEL_PLACE_DISTANCE;  // 区级别
		}
		if (Tool.isNotNullOrEmpty(tmp)) {
			level = tmp;
			switch (level) {
			case Const.LEVEL_PLACE_DISTANCE:
				
				break;
			case Const.LEVEL_PLACE_CITY:
				city = "";
				
				break;
			case Const.LEVEL_PLACE_PROVINCE:
				province = "";
				city = "";
				
				break;
			}
		}
		
		List<PlaceAnalysis> paList = new ArrayList<PlaceAnalysis>();
		
		switch (user.getUserType()) {
			case Const.USERTYPE_VENDER:  // 厂商
				paList = analysisService.findPlaceAnalysis(level, publicCode, province, city, "");
				
				break;
			case Const.USERTYPE_DEALER:  // 经销商
				paList = analysisService.findPlaceAnalysis(level, publicCode, province, city, user.getId());
				
				break;
			default:
				
				break;
		}
		
		for (PlaceAnalysis pa : paList) {
			AbstractChartDto dto = new AbstractChartDto();
			dto.setCategory(pa.getCount() + "");
			
			if (level.equals(Const.LEVEL_PLACE_PROVINCE)) {
				dto.setBar(pa.getProvince() == null ? "未知区域" : pa.getProvince());
			}
			if (level.equals(Const.LEVEL_PLACE_CITY)) {
				dto.setBar(pa.getCity() == null ? "未知区域" : pa.getCity());
			}
			if (level.equals(Const.LEVEL_PLACE_DISTRICT)) {
				dto.setBar(pa.getDistrict() == null ? "未知区域" : pa.getDistrict());
			}
			
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

class YearMonth implements Comparable<YearMonth> {
	private Integer year;
	private Integer month;
	
	public YearMonth() {
		super();
	}
	public YearMonth(Integer year, Integer month) {
		super();
		this.year = year;
		this.month = month;
	}
	public Integer getYear() {
		return year;
	}
	public Integer getMonth() {
		return month;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public void setMonth(Integer month) {
		this.month = month;
	}
	
	@Override
	public int compareTo(YearMonth o) {
		YearMonth ym = (YearMonth) o;
		if (this.getYear() == null || this.getYear() <= 0) {
			return 1;
		}
		if (ym.getYear() == null || ym.getYear() <= 0) {
			return -1;
		}
		
		if (this.getYear() < ym.getYear()) {
			return 1;
		} else if (this.getYear() > ym.getYear()) {
			return -1;
		} else if (this.getYear() == ym.getYear()) {
			if (this.getMonth() == null || this.getMonth() <= 0) {
				return 1;
			}
			if (ym.getMonth() == null || ym.getMonth() <= 0) {
				return -1;
			}
			if (this.getMonth() < ym.getMonth()) {
				return 1;
			} else if (this.getMonth() > ym.getMonth()) {
				return -1;
			} else {
				return 0;
			}
		}
		
		return 1;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((month == null) ? 0 : month.hashCode());
		result = prime * result + ((year == null) ? 0 : year.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		YearMonth other = (YearMonth) obj;
		if (year == null) {
			if (other.year != null)
				return false;
		} else if (!year.equals(other.year))
			return false;
		if (month == null) {
			if (other.month != null)
				return false;
		} else if (!month.equals(other.month))
			return false;
		
		return true;
	}
	
}
