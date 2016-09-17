package com.crm.controller;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.crm.domain.Activity;
import com.crm.domain.Award;
import com.crm.domain.Page;
import com.crm.domain.User;
import com.crm.domain.easyui.DataGrid;
import com.crm.domain.easyui.Json;
import com.crm.domain.easyui.PageHelper;
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

	private final Logger log = LoggerFactory.getLogger(AwardController.class);

	@Autowired
	private AwardService awardService;
	@Autowired
	private ActivityService activityService;

	/**
	 * @Title: accountList
	 * @Description: 跳转前端用户列表
	 * @param req
	 * @param resp
	 * @throws IOException
	 */
	@RequestMapping(value = "/award/list", method = RequestMethod.GET)
	public ModelAndView awardList(Model model) throws IOException {
		ModelAndView mv = new ModelAndView("award/list");

		List<Activity> atyList = this.activityService.getActivityList("");
		mv.addObject("atyList", atyList);

		return mv;
	}

	/**
	 * 新增用户
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/award/addAward", method = RequestMethod.POST)
	public Json addAward(HttpServletRequest request, Award award) {
		Json j = new Json();

		String activityIdA = request.getParameter("activityIdA");
		if (activityIdA == null || activityIdA.equals("")) {
			j.setSuccess(false);
			j.setMsg("请指定活动！");
			j.setObj("");
		} else {
			try {
				award.setActivityId(activityIdA);
				award.setRemain(award.getTotal());
				awardService.addAward(award);
				j.setSuccess(true);
				j.setMsg("用户新增成功！");
				j.setObj(award);
			} catch (Exception e) {
				j.setMsg(e.getMessage());
			}
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
	@RequestMapping(value = "/award/editAward", method = RequestMethod.POST)
	public Json editAward(Award award) {
		Json j = new Json();
		log.debug("穿过来的用户ID为：" + award.getId());
		try {
			awardService.updateAward(award);
			j.setSuccess(true);
			j.setMsg("编辑成功！");
			j.setObj(award);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		return j;
	}

	/**
	 * 删除某个用户
	 * 
	 * @param userId
	 * @param out
	 */
	@ResponseBody
	@RequestMapping(value = "/award/deleteAward", method = RequestMethod.POST)
	public Json deleteAward(Award award) {
		Json j = new Json();
		log.debug("穿过来的用户ID为：" + award.getId());
		try {
			awardService.deleteAward(award.getId());
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
	@RequestMapping(value = "/award/datagrid", method = RequestMethod.POST)
	public DataGrid datagrid(HttpServletRequest request, PageHelper page) {
		User user = (User) request.getSession().getAttribute(Const.SESSION_USER);
		DataGrid dg = new DataGrid();

		String activityId = request.getParameter("activityId");

		if (activityId != null && !activityId.equals("")) {
			activityId = "'" + activityId + "'";
		} else {
			List<Activity> atyList = activityService.getActivityList(" and publisherId = '" + user.getId() + "'");
			Set<String> set = new HashSet<String>();
			for (Activity aty : atyList) {
				set.add(aty.getId());
			}
			String[] activityIdArr = set.toArray(new String[0]);
			activityId = Tool.stringArrayToString(activityIdArr, true, ",");
		}

		Integer total = awardService.getDatagridTotal(activityId, "");
		dg.setTotal(total);
		List<Award> awardList = awardService.datagridAward(page, activityId, "");
		dg.setRows(awardList);
		return dg;
	}

	////////////////////////////////////////////////////

	//////////////////////////////////////////////////////////////////////

	/**
	 * 跳转到用户表格页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/awd/list", method = RequestMethod.GET)
	public String awdList(Model model, @RequestParam(value = "pageNumber", defaultValue = "1") int pageNumber) {
		List<Activity> atyList = activityService.getActivityList("");
		model.addAttribute("atyList", atyList);
		
		Page<Award> page = new Page<Award>();
		page.setPage(pageNumber);

		page = awardService.awdPages(page, "");

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
		User curUser = (User) session.getAttribute(Const.SESSION_USER);

		try {
			String activityId = award.getActivityId();
			Activity aty = activityService.findById(activityId);
			
			award.setActivity(aty);
			award.setActivityName(aty.getTitle());
			award.setRemain(award.getTotal());

			awardService.addAward(award);
		} catch (Exception e) {

		}

		Page<Award> page = new Page<Award>();
		page = awardService.awdPages(page, "");

		model.addAttribute("page", page);
		model.addAttribute("awds", page.getContent());

		return "award/list";
	}

	@RequestMapping(value = "/awd/delete", method = RequestMethod.POST)
	public String deleteAwss(Model model, @RequestParam("deleteIds[]") String[] deleteIds) {

		String ids = Tool.stringArrayToString(deleteIds, true, ",");
		/*
		 * StringBuffer sql = new StringBuffer();
		 * sql.append("and id in (").append(ids).append(")");
		 */

		awardService.deleteAward("(" + ids + ")");

		Page<Award> page = new Page<Award>();
		page = awardService.awdPages(page, "");

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
	public String updateAws(Model model, Award award) {
		awardService.updateAward(award);

		Page<Award> page = new Page<Award>();
		page = awardService.awdPages(page, "");

		model.addAttribute("page", page);
		model.addAttribute("awds", page.getContent());
		return "award/list";
	}

}
