package com.crm.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.service.ActivityService;
import com.crm.service.AwardService;
import com.crm.util.Tool;
import com.crm.util.common.Const;

/**
 * @ClassName AccountController.java
 * @Description
 * @Author kevin
 * @CreateTime 2016年7月5日 下午3:41:33
 * @Version V1.0
 */
@Controller
public class AwardController {

	@Resource
	private AwardService awardService;
	@Resource
	private ActivityService activityService;

	//////////////////////////////////////////////////////////////////////

	/**
	 * 跳转到用户表格页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/awd/list", method = RequestMethod.GET)
	public String awdList(Model model, HttpServletRequest request, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		List<Activity> atyList = activityService.getActivityList(" and t.publisherId = '" + user.getId() + "'");
		model.addAttribute("atyList", atyList);
		
		Page<Award> page = new Page<Award>();
		page.setPage(pageNumber);

		page = awardService.awdPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("awds", page.getContent());

		return "award/list";
	}

	/**
	 * @Title: prepareAddAty
	 * @Description: 跳转到活动新增页面
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/awd/prepareAdd", method = RequestMethod.GET)
	public String prepareAddAwd(Model model) {
		List<Activity> atyList = activityService.getActivityList("");
		model.addAttribute("atyList", atyList);
		
		return "award/add";
	}

	/**
	 * @Title: addAty
	 * @Description: 添加厂商
	 * @param model
	 * @param user
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/awd/add", method = RequestMethod.POST)
	public String addAwd(Model model, Award award, HttpSession session) {
		User user = (User) session.getAttribute(Const.SESSION_USER);

		try {
			String activityId = award.getActivityId();
			Activity aty = activityService.findById(activityId);
			
			award.setActivity(aty);
			award.setActivityName(aty.getTitle());
			award.setRemain(award.getTotal());
			award.setPublisherId(user.getId());

			awardService.addAward(award);
		} catch (Exception e) {

		}

		Page<Award> page = new Page<Award>();
		page = awardService.awdPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("awds", page.getContent());

		return "award/list";
	}

	@RequestMapping(value = "/awd/delete", method = RequestMethod.POST)
	public String deleteAwss(Model model, HttpSession session, @RequestParam("deleteIds[]") String[] deleteIds) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		String ids = Tool.stringArrayToString(deleteIds, true, ",");

		awardService.deleteAward("(" + ids + ")");

		Page<Award> page = new Page<Award>();
		page = awardService.awdPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("awds", page.getContent());
		return "award/list";
	}

	@RequestMapping(value = "/awd/detail/{id}", method = RequestMethod.GET)
	public String detailAwd(Model model, @PathVariable("id") String id) {
		Award award = awardService.findById(id);
		model.addAttribute("awd", award);

		return "award/detail";
	}

	@RequestMapping(value = "/awd/edit/{id}", method = RequestMethod.GET)
	public String prepareUpdateAward(Model model, @PathVariable("id") String id) {
		List<Activity> atyList = activityService.getActivityList("");
		model.addAttribute("atyList", atyList);
		
		Award award = awardService.findById(id);
		model.addAttribute("awd", award);
		
		return "award/edit";
	}

	@RequestMapping(value = "/awd/update", method = RequestMethod.POST)
	public String updateAwd(Model model, HttpSession session, Award award) {
		User user = (User) session.getAttribute(Const.SESSION_USER);
		awardService.updateAward(award);

		Page<Award> page = new Page<Award>();
		page = awardService.awdPages(page, " and a.publisherId = '" + user.getId() + "'");

		model.addAttribute("page", page);
		model.addAttribute("awds", page.getContent());
		return "award/list";
	}
	
	@RequestMapping(value = "/awd/search", method = RequestMethod.POST)
	public String awdListPost(Model model, HttpServletRequest request, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber,
			@RequestParam("atyId") String atyId) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		List<Activity> atyList = activityService.getActivityList(" and t.publisherId = '" + user.getId() + "'");
		model.addAttribute("atyList", atyList);
		
		StringBuffer conditionSql = new StringBuffer();
		
		Page<Award> page = new Page<Award>();
		if (Tool.isNotNullOrEmpty(atyId)) {
			model.addAttribute("atyId", atyId);
			conditionSql.append(" and a.activityId = '").append(atyId).append("'");
		}
		
		page = awardService.awdPages(page, conditionSql.toString());

		model.addAttribute("page", page);
		model.addAttribute("awds", page.getContent());
	
		return "award/list";
	}

}
