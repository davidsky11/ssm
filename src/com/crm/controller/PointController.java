package com.crm.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.crm.domain.User;
import com.crm.service.UserService;
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
    public String list(HttpServletRequest request, Model model) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);  // 当前用户
		user = this.userService.getUserById(user.getId());
		
		model.addAttribute("user", user);
        return "point/list";
    }
	
}
