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
import com.crm.domain.Exchange;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.domain.dto.ExchangeDto;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.domain.system.SysDictionary;
import com.crm.service.ActivityService;
import com.crm.service.ExchangeService;
import com.crm.service.SysDictionaryService;
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
public class ExchangeController {
	
	private final Logger log = LoggerFactory.getLogger(ExchangeController.class);
	
	@Resource
	private ExchangeService exchangeService;
	@Resource
	private ActivityService activityService;
	@Resource
	private SysDictionaryService sysDictionaryService;

	/**
	 * @Title:			accountList
	 * @Description:	跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/exchange/list", method = RequestMethod.GET)
	public String exchangeList(Model model) throws IOException { 
		return "exchange/list";
	}
	
	/**
	 * 新增用户
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/addExchange",method = RequestMethod.POST)
    public Json addExchange(Exchange exchange) {
		Json j = new Json();
		
		try {
			exchangeService.saveExchange(exchange);
            j.setSuccess(true);
            j.setMsg("用户新增成功！");
            j.setObj(exchange);
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
    @RequestMapping(value = "/exchange/editExchange",method = RequestMethod.POST)
    public Json editExchange(Exchange exchange) {
        Json j = new Json();
        log.debug("穿过来的用户ID为：" + exchange.getId());
        try {
        	exchangeService.updateExchange(exchange);
            j.setSuccess(true);
            j.setMsg("编辑成功！");
            j.setObj(exchange);
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
	@RequestMapping(value = "/exchange/deleteExchange",method = RequestMethod.POST)
	public Json deleteExchange(Exchange exchange) {
		Json j = new Json();
        log.debug("穿过来的用户ID为："+exchange.getId());
        try {
        	exchangeService.deleteExchange(exchange.getId());
			j.setSuccess(true);
	        j.setMsg("删除成功！");
        } catch (Exception e) {
            j.setMsg(e.getMessage());
        }
        return j;
	}
	
	/**
	 * 前端用户表格
	 * 	仅获取当前用户对应的兑奖记录
	 */
	@ResponseBody
	@RequestMapping(value = "/exchange/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(HttpServletRequest request, PageHelper page) {
		User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);
		
		DataGrid dg = new DataGrid();
		
		if (user != null) {
			Exchange exchange = new Exchange();
			exchange.setUserId(user.getId());
			
			dg.setTotal(exchangeService.getDatagridTotal(exchange));
			List<Exchange> exchangeList = exchangeService.datagridExchange(page, exchange);
			List<ExchangeDto> list = new ArrayList<ExchangeDto>();
			for (Exchange e : exchangeList) {
				ExchangeDto dto = new ExchangeDto(e);
				list.add(dto);
			}
			
			dg.setRows(list);
		}
		return dg;
	}
	
	/////////////////////////////////////////////////////////////////////////////////
	
	@RequestMapping(value = "/exchange/exList", method = RequestMethod.GET)
	public String exList(Model model, HttpServletRequest request,
			@RequestParam(value="pageNumber",defaultValue="1") int pageNumber) {
		User user =  (User) request.getSession().getAttribute(Const.SESSION_USER);
		
		String publicCode = Tool.nvl(request.getParameter("publicCode"));
		String startDate = Tool.nvl(request.getParameter("startDate"));
		String endDate = Tool.nvl(request.getParameter("endDate"));
		
		model.addAttribute("userType", user.getUserType());
		Page<Exchange> page = new Page<Exchange>();
		page.setPage(pageNumber);
		page.setSort("exchangeTime");
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
			conditionSql.append(" and t.exchangeTime >= '").append(startDate).append("'");
			paramMap.put("startDate", startDate);
			model.addAttribute("startDate", startDate);
		}
		
		if (Tool.isNotNullOrEmpty(endDate)) {
			conditionSql.append(" and t.exchangeTime <= date_sub('").append(endDate).append("', interval -1 day)");
			paramMap.put("endDate", endDate);
			model.addAttribute("endDate", endDate);
		}
		
		if (user != null) {
			page = exchangeService.exPages(page, conditionSql.toString());
		}
		
		List<Activity> atyList = activityService.getActivityList("");
		model.addAttribute("atyList", atyList);
		
		/**
		 * 2、获取兑奖方式字典
		 */
		List<SysDictionary> dicList = sysDictionaryService.getDicList(" and classcode = 'DJLX' and parentid <> 0");
		model.addAttribute("dicList", dicList);
		
		List<Exchange> list = page.getContent();
		for (Exchange ex : list) {
			for (Activity aty : atyList) {
				if (ex.getPublicCode().equals(aty.getPublicCode())) {
					ex.setActivity(aty);
					continue;
				}
			}
		}
		
		model.addAttribute("searchParams", Tool.doneQueryParam(paramMap));
		model.addAttribute("page", page);
		model.addAttribute("exs", list);
		
		return "exchange/exList";
	}
	
	/**
	 * 兑奖详情
	 * @Title:			detailExchange
	 * @Description:	兑奖详情
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/exchange/detail/{id}", method=RequestMethod.GET)
	public String detailExchange(Model model, @PathVariable("id") String id) {
		Exchange exchange = exchangeService.findById(id);
		model.addAttribute("ex", exchange);
		
		return "exchange/detail";
	}
	
	/**
	 * 针对厂商
	 * @Title:			list4Vender
	 * @Description:	针对厂商的兑奖记录
	 * @param model
	 * @param request
	 * @param pageNumber
	 * @return
	 */
	@RequestMapping(value = "/exchange/list4Vender", method = RequestMethod.GET)
	public String list4Vender(Model model, HttpServletRequest request,
			@RequestParam(value="pageNumber",defaultValue="1") int pageNumber) {
		User user =  (User) request.getSession().getAttribute(Const.SESSION_USER);
		
		/**
		 * 1、获取的当前厂商发布的活动列表
		 */
		List<Activity> atyList = activityService.getActivityList(" and t.publisherId = '" + user.getId() + "'");
		model.addAttribute("atyList", atyList);
		
		/**
		 * 2、获取兑奖方式字典
		 */
		List<SysDictionary> dicList = sysDictionaryService.getDicList(" and classcode = 'DJLX' and parentid <> 0");
		model.addAttribute("dicList", dicList);
		
		String publicCode = Tool.nvl(request.getParameter("publicCode"));
		String startDate = Tool.nvl(request.getParameter("startDate"));
		String endDate = Tool.nvl(request.getParameter("endDate"));
		
		model.addAttribute("userType", user.getUserType());
		Page<Exchange> page = new Page<Exchange>();
		page.setPage(pageNumber);
		page.setSort("exchangeTime");
		page.setOrder("desc");
		
		StringBuffer conditionSql = new StringBuffer();
		
		Map<String, String> paramMap = new HashMap<String, String>();
		if (Tool.isNotNullOrEmpty(publicCode)) {
			conditionSql.append(" and t.publicCode = '").append(publicCode).append("'");
			paramMap.put("publicCode", publicCode);
			model.addAttribute("publicCode", publicCode);
		}
		
		if (Tool.isNotNullOrEmpty(startDate)) {
			conditionSql.append(" and t.exchangeTime >= '").append(startDate).append("'");
			paramMap.put("startDate", startDate);
			model.addAttribute("startDate", startDate);
		}
		
		if (Tool.isNotNullOrEmpty(endDate)) {
			conditionSql.append(" and t.exchangeTime <= date_sub('").append(endDate).append("', interval -1 day)");
			paramMap.put("endDate", endDate);
			model.addAttribute("endDate", endDate);
		}
		
		if (user != null) {
			page = exchangeService.selectByPublisher(page, conditionSql.toString(), user.getId());
		}
		
		model.addAttribute("searchParams", Tool.doneQueryParam(paramMap));
		model.addAttribute("page", page);
		model.addAttribute("exs", (page == null ? new ArrayList<Exchange>() : page.getContent()));
		
		
		return "exchange/list4Vender";
	}
	
}
 