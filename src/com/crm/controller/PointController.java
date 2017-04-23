package com.crm.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.domain.Exchange;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.service.UserService;
import com.crm.util.Tool;
import com.crm.util.common.Const;

/**
 * @author zh
 * 2014-8-23
 */
@Controller
public class PointController {
	
	private final Logger log = LoggerFactory.getLogger(PointController.class);
	
	@Resource
	private UserService userService;

	/**
	 * 积分兑换记录
	 * @return
	 */
	@RequestMapping(value = "/point/statistic", method = RequestMethod.GET)
    public String statistic(HttpServletRequest request, Model model) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);  // 当前用户
		user = this.userService.getUserById(user.getId());
		
		model.addAttribute("user", user);
        return "point/statistic";
    }
	
	/**
	 * 积分兑换记录
	 * @Title:			list
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/point/list", method = RequestMethod.GET)
    public String list(HttpServletRequest request, Model model,
    		@RequestParam(value="pageNumber",defaultValue="1") int pageNumber) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);  // 当前用户
		user = this.userService.getUserById(user.getId());
		
		Page<Exchange> page = new Page<Exchange>();
		page.setPage(pageNumber);
		page.setSort("t.scanTime");
		page.setOrder("desc");
		
		Map<String, String> paramMap = new HashMap<String, String>();
		
		model.addAttribute("user", user);
		
		model.addAttribute("searchParams", Tool.doneQueryParam(paramMap));
		model.addAttribute("page", page);
        return "point/list";
    }
	
}
