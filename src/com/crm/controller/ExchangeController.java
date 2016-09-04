package com.crm.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.Exchange;
import com.crm.domain.User;
import com.crm.domain.dto.ExchangeDto;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
import com.crm.service.ExchangeService;
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
	
}
 